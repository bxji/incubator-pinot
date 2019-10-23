package org.apache.pinot.thirdeye.detection.cache;

import com.couchbase.client.java.AsyncBucket;
import com.couchbase.client.java.AsyncCluster;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;
import com.couchbase.client.java.query.SimpleN1qlQuery;
import com.couchbase.client.java.subdoc.DocumentFragment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.pinot.thirdeye.auto.onboard.AutoOnboardUtility;
import org.apache.pinot.thirdeye.common.time.TimeGranularity;
import org.apache.pinot.thirdeye.common.time.TimeSpec;
import org.apache.pinot.thirdeye.dataframe.util.MetricSlice;
import org.apache.pinot.thirdeye.datasource.MetricFunction;
import org.apache.pinot.thirdeye.datasource.RelationalThirdEyeResponse;
import org.apache.pinot.thirdeye.datasource.ThirdEyeRequest;
import org.apache.pinot.thirdeye.datasource.ThirdEyeResponse;
import org.apache.pinot.thirdeye.rootcause.impl.MetricEntity;
import org.apache.pinot.thirdeye.util.CacheUtils;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;


public class CouchbaseCacheDAO {

  private static final Logger LOG = LoggerFactory.getLogger(AutoOnboardUtility.class);

  // these will all be moved to config files
  //private static final String HOSTNAME = "localhost";
  private static final String AUTH_USERNAME = "thirdeye";
  private static final String AUTH_PASSWORD = "thirdeye";
  private static final String BUCKET_NAME = "travel-sample";

  private static final String BASE_QUERY = "SELECT time, `dims`.$dimensionKey FROM `$bucket` WHERE metricId = $metricId AND time BETWEEN $start AND $end ORDER BY time ASC";
  private static final String BUCKET = "bucket";
  private static final String DIMENSION_KEY = "dimensionKey";
  private static final String METRIC_ID = "metricId";
  private static final String START = "start";
  private static final String END = "end";

  private static final String TIME = "time";

  private static final int TIMEOUT = 36000;

  private Bucket bucket;

  public CouchbaseCacheDAO() {
    this.createDataStoreConnection();
  }

  private void createDataStoreConnection() {
    Cluster cluster = CouchbaseCluster.create();
    cluster.authenticate(AUTH_USERNAME, AUTH_PASSWORD);
    this.bucket = cluster.openBucket(BUCKET_NAME);
  }

  public ThirdEyeCacheResponse tryFetchExistingTimeSeries(ThirdEyeCacheRequest request) {

    String dimensionKey = request.getDimensionKey();

    // NOTE: we subtract one from the end date because Couchabase's BETWEEN clause is inclusive on both sides
    JsonObject parameters = JsonObject.create()
        .put(BUCKET, BUCKET_NAME)
        .put(DIMENSION_KEY, dimensionKey)
        .put(METRIC_ID, request.getMetricId())
        .put(START, request.getStartTimeInclusive())
        .put(END, request.getEndTimeExclusive() - 1);

    N1qlQueryResult queryResult = bucket.query(N1qlQuery.parameterized(BASE_QUERY, parameters));

    if (!queryResult.finalSuccess()) {
      LOG.error("cache error occurred for window startTime = {} to endTime = {}", request.getStartTimeInclusive(), request.getEndTimeExclusive());
      return null;
    }

    List<TimeSeriesDataPoint> timeSeriesRows = new ArrayList<>();

    for (N1qlQueryRow row : queryResult) {
      long timestamp = row.value().getLong(TIME);
      String dataValue = row.value().getString(dimensionKey);
      timeSeriesRows.add(new TimeSeriesDataPoint(request.getMetricUrn(), timestamp, request.getMetricId(), dataValue));
    }

    return new ThirdEyeCacheResponse(request, timeSeriesRows);
  }

  public void insertTimeSeriesDataPoint(TimeSeriesDataPoint point) {

    // get or getAndTouch?
    //JsonDocument doc = bucket.getAndTouch(point.getDocumentKey(), TIMEOUT);
    JsonDocument doc = bucket.get(point.getDocumentKey());

    if (doc == null) {
      JsonObject documentBody = CacheUtils.buildDocumentStructure(point);
      doc = JsonDocument.create(point.getDocumentKey(), TIMEOUT, documentBody);
    } else {
      JsonObject dimensions = ((JsonObject)doc.content().get("dims"));
      if (dimensions.containsKey(point.getMetricUrnHash()))
        return;
      dimensions.put(point.getMetricUrnHash(), point.getDataValue());
    }

    bucket.upsert(doc);
  }
}

package org.apache.pinot.thirdeye.detection.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.IOException;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Helper methods to load cache config from
 */
public class CacheConfigLoader {
  private static final Logger LOG = LoggerFactory.getLogger(CacheConfigLoader.class);
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper(new YAMLFactory());

  /**
   * Returns cache config from yml file
   * @param cacheConfigUrl URL to cache config
   * @return cacheConfig
   */
  public static CacheConfig fromCacheConfigUrl(URL cacheConfigUrl) {
    CacheConfig cacheConfig = null;
    try {
      cacheConfig = OBJECT_MAPPER.readValue(cacheConfigUrl, CacheConfig.class);
    } catch (IOException e) {
      LOG.error("Exception in reading cache config {}", cacheConfigUrl, e);
    }
    return cacheConfig;
  }


}

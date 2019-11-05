package org.apache.pinot.thirdeye.detection.cache;

/**
 * Config for a single centralized cache data source.
 * For example, this class could be for Couchbase, or Redis, or Cassandra, etc.
 */
public class CacheDataSource {

  /**
   * authentication stuff.
   */
  private String authUsername;
  private String authPassword;
  private String bucketName;

  // left blank
  public CacheDataSource() {}

  public String getAuthUsername() { return authUsername; }
  public String getAuthPassword() { return authPassword; }
  public String getBucketName() { return bucketName; }

  public void setAuthUsername(String authUsername) { this.authUsername = authUsername; }
  public void setAuthPassword(String authPassword) { this.authPassword = authPassword; }
  public void setBucketName(String bucketName) { this.bucketName = bucketName; }
}

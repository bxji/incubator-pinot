package org.apache.pinot.thirdeye.detection.cache;

public class CouchbaseDataSource {
  private String authUsername;
  private String authPassword;
  private String bucketName;

  public CouchbaseDataSource(String authUsername, String authPassword, String bucketName) {
    this.authUsername = authUsername;
    this.authPassword = authPassword;
    this.bucketName = bucketName;
  }

  public String getAuthUsername() {
    return authUsername;
  }

  public String getAuthPassword() {
    return authPassword;
  }

  public String getBucketName() {
    return bucketName;
  }

  public void setAuthUsername(String authUsername) {
    this.authUsername = authUsername;
  }

  public void setAuthPassword(String authPassword) {
    this.authPassword = authPassword;
  }

  public void setBucketName(String bucketName) {
    this.bucketName = bucketName;
  }
}

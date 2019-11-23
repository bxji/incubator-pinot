/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.pinot.thirdeye.detection.cache;

import java.util.List;


/**
 * Config for a single centralized cache data source.
 * For example, this class could be for Couchbase, or Redis, or Cassandra, etc.
 */
public class CacheDataSource {

  /**
   * Authentication type
   */
  private boolean useCertificateBasedAuthentication;

  /**
   * Cluster information
   */
  private List<String> hosts;
  private String bucketName;

  /**
   * Username/password based authentication fields
   */
  private String authUsername;
  private String authPassword;

  /**
   * Certificate-based Authentication fields
   */
  private String keyStoreFilePath;
  private String keyStorePassword;
  private String trustStoreFilePath;
  private String trustStorePassword;

  // left blank
  public CacheDataSource() {}

  public boolean useCertificateBasedAuthentication() { return useCertificateBasedAuthentication; }

  public List<String> getHosts() { return hosts; }
  public String getBucketName() { return bucketName; }
  public String getAuthUsername() { return authUsername; }
  public String getAuthPassword() { return authPassword; }
  public String getKeyStoreFilePath() { return keyStoreFilePath; }
  public String getKeyStorePassword() { return keyStorePassword; }
  public String getTrustStoreFilePath() { return trustStoreFilePath; }
  public String getTrustStorePassword() { return trustStorePassword; }

  public void setUseCertificateBasedAuthentication(boolean useCertificateBasedAuthentication) { this.useCertificateBasedAuthentication = useCertificateBasedAuthentication; }
  public void setHosts(List<String> hosts) { this.hosts = hosts; }
  public void setBucketName(String bucketName) { this.bucketName = bucketName; }
  public void setAuthUsername(String authUsername) { this.authUsername = authUsername; }
  public void setAuthPassword(String authPassword) { this.authPassword = authPassword; }
  public void setKeyStoreFilePath(String keyStoreFilePath) { this.keyStoreFilePath = keyStoreFilePath; }
  public void setKeyStorePassword(String keyStorePassword) { this.keyStorePassword = keyStorePassword; }
  public void setTrustStoreFilePath(String trustStoreFilePath) { this.trustStoreFilePath = trustStoreFilePath; }
  public void setTrustStorePassword(String trustStorePassword) { this.trustStorePassword = trustStorePassword; }
}

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

import java.util.ArrayList;
import java.util.List;


/**
 * Config file for cache-related stuff.
 * Mapped from cache-config.yml
 */
public class CacheConfig {

  public static CacheConfig instance = new CacheConfig();

  /**
   * flags for which cache to use; recommended to only use one at a time
   */
  private static boolean useInMemoryCache;
  private static boolean useCentralizedCache;

  /**
   * flag for which authentication type to use
   */
  private static boolean useCertificateBasedAuthentication;

  /**
   * config values for accessing the centralized cache. should be set from CentralizedCacheConfig values.
   * these are added here for ease-of-access and making code a little more readable.
   */
  private static List<String> hosts;
  private static String authUsername;
  private static String authPassword;
  private static String bucketName;
  private static String keyStoreFilePath;
  private static String keyStorePassword;
  private static String trustStoreFilePath;
  private static String trustStorePassword;

  /**
   * settings for centralized cache.
   */
  private static CentralizedCacheConfig centralizedCacheSettings;

  // left blank
  public CacheConfig() {}

  public static CacheConfig getInstance() { return instance; }

  public boolean useCentralizedCache() { return useCentralizedCache; }
  public boolean useInMemoryCache() { return useInMemoryCache; }
  public CentralizedCacheConfig getCentralizedCacheSettings() { return centralizedCacheSettings; }
  public boolean useCertificateBasedAuthentication() { return useCertificateBasedAuthentication; }

  public List<String> getHosts() { return hosts; }
  public String getAuthUsername() { return authUsername; }
  public String getAuthPassword() { return authPassword; }
  public String getBucketName() { return bucketName; }

  public String getKeyStoreFilePath() { return keyStoreFilePath; }
  public String getKeyStorePassword() { return keyStorePassword; }
  public String getTrustStoreFilePath() { return trustStoreFilePath; }
  public String getTrustStorePassword() { return trustStorePassword; }

  public void setUseCentralizedCache(boolean toggle) { useCentralizedCache = toggle; }
  public void setUseInMemoryCache(boolean toggle) { useInMemoryCache = toggle; }
  public void setCentralizedCacheSettings(CentralizedCacheConfig centralizedCacheConfig) { centralizedCacheSettings = centralizedCacheConfig; }

  public void setUseCertificateBasedAuthentication(boolean useCertificateBasedAuthentication) { CacheConfig.useCertificateBasedAuthentication = useCertificateBasedAuthentication; }
  // note that we are making a copy since input array is not static.
  public void setHosts(List<String> hosts) {
    CacheConfig.hosts = new ArrayList<>();
    CacheConfig.hosts.addAll(hosts);
  }
  public void setAuthUsername(String authUsername) { CacheConfig.authUsername = authUsername; }
  public void setAuthPassword(String authPassword) { CacheConfig.authPassword = authPassword; }
  public void setBucketName(String bucketName) { CacheConfig.bucketName = bucketName; }

  public void setKeyStoreFilePath(String keyStoreFilePath) { CacheConfig.keyStoreFilePath = keyStoreFilePath; }
  public void setKeyStorePassword(String keyStorePassword) { CacheConfig.keyStorePassword = keyStorePassword; }
  public void setTrustStoreFilePath(String trustStoreFilePath) { CacheConfig.trustStoreFilePath = trustStoreFilePath; }
  public void setTrustStorePassword(String trustStorePassword) { CacheConfig.trustStorePassword = trustStorePassword; }
}

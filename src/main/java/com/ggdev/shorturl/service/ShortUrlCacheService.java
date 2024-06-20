package com.ggdev.shorturl.service;

public interface ShortUrlCacheService {

    String getLongUrl(String urlKey);

    String putShortUrl(String urlKey, String url);

    void evictShortUrl(String urlKey);

}

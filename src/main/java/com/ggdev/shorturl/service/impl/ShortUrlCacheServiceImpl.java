package com.ggdev.shorturl.service.impl;

import com.ggdev.shorturl.config.CacheKey;
import com.ggdev.shorturl.dto.ShortUrlEntity;
import com.ggdev.shorturl.mapper.ShortUrlMapper;
import com.ggdev.shorturl.service.ShortUrlCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ShortUrlCacheServiceImpl implements ShortUrlCacheService {

    final private ShortUrlMapper shortUrlMapper;

    @Autowired
    public ShortUrlCacheServiceImpl(ShortUrlMapper shortUrlMapper) {

        this.shortUrlMapper = shortUrlMapper;

    }

    @CachePut(value = CacheKey.SHORTURL, key = "#urlKey", cacheManager = CacheKey.REDIS_CACHE_MANAGER, unless = "#result == null")
    public String putShortUrl(String urlKey, String url) {
        return url;
    }

    @Cacheable(value = CacheKey.SHORTURL, key = "#urlKey", cacheManager = CacheKey.REDIS_CACHE_MANAGER, unless = "#result == null")
    public String getLongUrl(String urlKey) {
        ShortUrlEntity shortUrlEntity = shortUrlMapper.getShortUrlUse(urlKey);
        return shortUrlEntity.getLongUrl();
    }

    @CacheEvict(value = CacheKey.SHORTURL, key = "#urlKey", cacheManager = CacheKey.REDIS_CACHE_MANAGER)
    public void evictShortUrl(String urlKey) {
    }


}

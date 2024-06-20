package com.ggdev.shorturl.service.impl;


import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Objects;

import com.ggdev.shorturl.common.response.CommonException;
import com.ggdev.shorturl.config.ShortUrlConfig;
import com.ggdev.shorturl.exception.UrlNotFoundException;
import com.ggdev.shorturl.mapper.ShortUrlMapper;
import com.ggdev.shorturl.dto.ShortUrlEntity;
import com.ggdev.shorturl.dto.ShortUrlReq;
import com.ggdev.shorturl.dto.ShortUrlRes;
import com.ggdev.shorturl.service.KeyGeneratorService;
import com.ggdev.shorturl.service.ShortUrlCacheService;
import com.ggdev.shorturl.service.ShortUrlService;
import com.ggdev.shorturl.utils.KeyGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    protected ShortUrlConfig shortUrlConfig;
    final private ShortUrlMapper shortUrlMapper;
    final private ShortUrlCacheService shortUrlCacheService;
    final private KeyGeneratorService keyGeneratorService;


    @Autowired
    public ShortUrlServiceImpl(ShortUrlConfig shortUrlConfig,
                               ShortUrlMapper shortUrlMapper,
                               ShortUrlCacheService shortUrlCacheService,
                               KeyGeneratorService keyGeneratorService) {

       this.shortUrlConfig = shortUrlConfig;
       this.shortUrlMapper = shortUrlMapper;
       this.shortUrlCacheService = shortUrlCacheService;
       this.keyGeneratorService = keyGeneratorService;
    }

    @Override
    @Transactional(readOnly = true)
    public ShortUrlRes getShortUrl(String urlKey) {

      if(urlKey.length() > 6){
          throw new UrlNotFoundException(urlKey);
      }

      ShortUrlEntity urlEntity = shortUrlMapper.getShortUrl(urlKey);
      if(urlEntity == null){
          throw new UrlNotFoundException(urlKey);
      }

      return this.setShortUrlRes(urlEntity);

  }

    @Override
    public String getLongUrl(String urlKey, String pmi) {

        String longUrl;
        try{
            longUrl = String.valueOf(shortUrlCacheService.getLongUrl(urlKey));
            if(Objects.equals(longUrl, "null") || longUrl == null){
                throw new UrlNotFoundException(urlKey);
            }
        }catch (Exception e){
            log.info("e : {}",e.getMessage());
            throw new UrlNotFoundException(urlKey);
        }

        return longUrl;
    }

    @Override
    @Transactional
    public ShortUrlRes addShortUrl(ShortUrlReq params) throws UnsupportedEncodingException, CommonException {

        KeyGenerator keyGenerator = new KeyGenerator();
        String hashUrl = keyGenerator.hashSHA(params.getLongUrl());

        int chkHashUrl = shortUrlMapper.chkHashUrl(hashUrl);
        if(chkHashUrl > 0) {
            ShortUrlRes urlRes = this.setShortUrlRes(shortUrlMapper.getShortUrlForHash(hashUrl));
            urlRes.setDupYN("Y");
            return urlRes;
        }

        String urlKey = keyGeneratorService.getKey(hashUrl);

        ShortUrlEntity urlEntity = new ShortUrlEntity();
        urlEntity.setUrlKey(urlKey);
        urlEntity.setShortUrl(shortUrlConfig.getServiceUrl()+"/"+urlKey);
        urlEntity.setLongUrl(params.getLongUrl());
        urlEntity.setHashUrl(hashUrl);
        urlEntity.setUrlDesc(params.getUrlDesc());
        urlEntity.setUseYN("Y");
        urlEntity.setRegId(params.getUserId());
        urlEntity.setModId(params.getUserId());

        urlEntity.setRegDttm(LocalDateTime.now());
        urlEntity.setModDttm(LocalDateTime.now());

        shortUrlMapper.addShortUrl(urlEntity);
        log.info("urlEntity : {}", urlEntity);
        shortUrlCacheService.putShortUrl(urlEntity.getUrlKey(), urlEntity.getLongUrl());

        return this.setShortUrlRes(urlEntity);

  }

    private ShortUrlRes setShortUrlRes(ShortUrlEntity params) {

        ShortUrlRes urlRes = new ShortUrlRes();
        urlRes.setUrlKey(params.getUrlKey());
        urlRes.setShortUrl(params.getShortUrl());
        urlRes.setLongUrl(params.getLongUrl());
        urlRes.setUrlDesc(params.getUrlDesc());
        urlRes.setUseYN(params.getUseYN());
        urlRes.setDupYN("N");
        urlRes.setRegId(params.getRegId());
        urlRes.setRegDttm(params.getRegDttm());
        urlRes.setModId(params.getModId());
        urlRes.setModDttm(params.getModDttm());

        return urlRes;
    }


}

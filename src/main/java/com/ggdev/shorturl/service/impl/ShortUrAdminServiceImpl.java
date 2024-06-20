package com.ggdev.shorturl.service.impl;


import com.ggdev.shorturl.config.ShortUrlConfig;
import com.ggdev.shorturl.dto.ShortUrlAdminReq;
import com.ggdev.shorturl.dto.ShortUrlEntity;
import com.ggdev.shorturl.dto.ShortUrlLog;
import com.ggdev.shorturl.exception.UrlAlreadyExistsException;
import com.ggdev.shorturl.mapper.ShortUrlMapper;
import com.ggdev.shorturl.service.*;
import com.ggdev.shorturl.utils.KeyGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Slf4j
@Service
public class ShortUrAdminServiceImpl implements ShortUrlAdminService {

    protected ShortUrlConfig shortUrlConfig;
    final private ShortUrlMapper shortUrlMapper;
    final private ShortUrlCacheService shortUrlCacheService;

    @Autowired
    public ShortUrAdminServiceImpl(ShortUrlConfig shortUrlConfig,
                                   ShortUrlMapper shortUrlMapper,
                                   ShortUrlCacheService shortUrlCacheService) {

       this.shortUrlConfig = shortUrlConfig;
       this.shortUrlMapper = shortUrlMapper;
       this.shortUrlCacheService = shortUrlCacheService;

    }

    @Override
    @Transactional(readOnly = true)
    public List<ShortUrlEntity> getAllShortUrl() {
        return shortUrlMapper.getAllShortUrl();
    }

    @Override
    @Transactional(readOnly = true)
    public int getAllShortUrlCnt(ShortUrlAdminReq params) {
        return shortUrlMapper.getAllShortUrlCnt(params);
    }


    @Override
    @Transactional(readOnly = true)
    public List<ShortUrlEntity> getShortUrlList(ShortUrlAdminReq params) {
        params.setPageNumber(params.getPageNumber()-1);
        return shortUrlMapper.getShortUrlList(params);
    }


    @Override
    @Transactional
    public ShortUrlEntity updateShortUrl(ShortUrlAdminReq params) {

        ShortUrlEntity urlEntity = new ShortUrlEntity();
        urlEntity.setUrlKey(params.getUrlKey());
        urlEntity.setLongUrl(params.getLongUrl());
        urlEntity.setUrlDesc(params.getUrlDesc());
        urlEntity.setUseYN(params.getUseYN());
        urlEntity.setModId(params.getUserId());

        if(!params.getLongUrl().isBlank()){
            KeyGenerator keyGenerator = new KeyGenerator();
            String hashUrl = keyGenerator.hashSHA(params.getLongUrl());
            urlEntity.setHashUrl(hashUrl);
            int chkHashUrl = shortUrlMapper.chkHashUrlExcept(urlEntity);
            if(chkHashUrl > 0) {
                throw new UrlAlreadyExistsException(urlEntity.getLongUrl());
            }
        }

        if(shortUrlMapper.updateShortUrl(urlEntity)){
            if("N".equals(urlEntity.getUseYN())){
                shortUrlCacheService.evictShortUrl(params.getUrlKey());
            }else{
                shortUrlCacheService.putShortUrl(urlEntity.getUrlKey(), urlEntity.getLongUrl());
            }

        }

        return shortUrlMapper.getShortUrl(urlEntity.getUrlKey());
    }

    @Override
    public List<ShortUrlLog> getLogList(ShortUrlAdminReq params) {
        params.setPageNumber(params.getPageNumber()-1);
        return shortUrlMapper.getLogList(params);
    }

    @Override
    public int getLogListCnt(ShortUrlAdminReq params) {
        return shortUrlMapper.getLogListCnt(params);
    }


}

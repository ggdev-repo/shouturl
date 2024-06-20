package com.ggdev.shorturl.service.impl;


import com.google.gson.Gson;
import com.ggdev.shorturl.dto.ShortUrlLog;
import com.ggdev.shorturl.mapper.ShortUrlMapper;
import com.ggdev.shorturl.service.ShortUrlAsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;

@Slf4j
@Service
public class ShortUrlAsyncServiceImpl implements ShortUrlAsyncService {


    final private ShortUrlMapper shortUrlMapper;

    @Autowired
    public ShortUrlAsyncServiceImpl(ShortUrlMapper shortUrlMapper) {
        this.shortUrlMapper = shortUrlMapper;
    }

    @Override
    @Transactional
    @Async("AsyncUpdateClickLog")
    public void updateClickLog(String urlKey, HashMap<String, Object> headers) {
        try{

            String longUrl = String.valueOf(headers.get("longUrl"));

            ShortUrlLog shortUrlLog = new ShortUrlLog();
            shortUrlLog.setUrlKey(urlKey);
            shortUrlLog.setShortUrl(String.valueOf(headers.get("shortUrl")).replace("null",""));
            shortUrlLog.setLongUrl(longUrl);
            shortUrlLog.setHeaderParams(new Gson().toJson(headers));
            shortUrlLog.setReferer(String.valueOf(headers.get("referer")).replace("null",""));
            shortUrlLog.setUserAgent(String.valueOf(headers.get("user-agent")).replace("null",""));
            shortUrlLog.setSecChUa(String.valueOf(headers.get("sec-ch-ua")).replace("null",""));

            shortUrlMapper.insertClickLog(shortUrlLog);

        }catch (Exception e){
            log.error(e.getMessage());
        }

    }

}

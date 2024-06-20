package com.ggdev.shorturl.service.impl;


import com.ggdev.shorturl.common.response.CommonException;
import com.ggdev.shorturl.config.ShortUrlConfig;
import com.ggdev.shorturl.mapper.ShortUrlMapper;
import com.ggdev.shorturl.service.KeyGeneratorService;
import com.ggdev.shorturl.utils.KeyGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class KeyGeneratorServiceImpl implements KeyGeneratorService {

    protected ShortUrlConfig shortUrlConfig;
    private final ShortUrlMapper shortUrlMapper;

    @Autowired
    public KeyGeneratorServiceImpl(ShortUrlConfig shortUrlConfig,ShortUrlMapper shortUrlMapper) {
        this.shortUrlConfig = shortUrlConfig;
        this.shortUrlMapper = shortUrlMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public String getKey(String sha256hex) throws CommonException {

        KeyGenerator keyGenerator = new KeyGenerator();
        String key = keyGenerator.encodingBASE62(sha256hex);

        log.info("sha256hex : {}",sha256hex);
        log.info("key : {}",key);
        int len = sha256hex.length()-shortUrlConfig.getLength();
        for (int i = 1; i <= len; i++) {

            int chkUrlKey = shortUrlMapper.chkUrlKey(key);

            //시도 가능 횟수 초과
            if(i == len ){
                throw new CommonException("getKey Fail");
            } else if (chkUrlKey > 0) {
                // 키 중복시 한칸식 밀기
                key = keyGenerator.encodingBASE62(sha256hex.substring(i));
                log.info("key : {}", key);
            } else {
                // 키 발급 성공 나오기
                break;
            }
        }

      return key;

    }

}

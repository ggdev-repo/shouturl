package com.ggdev.shorturl.mapper;



import com.ggdev.shorturl.dto.ShortUrlAdminReq;
import com.ggdev.shorturl.dto.ShortUrlEntity;
import com.ggdev.shorturl.dto.ShortUrlLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShortUrlMapper {
    List<ShortUrlEntity> getAllShortUrl();

    int getAllShortUrlCnt(ShortUrlAdminReq params);

    List<ShortUrlEntity> getShortUrlList(ShortUrlAdminReq params);


    int chkHashUrl(String hashUrl);
    
    ShortUrlEntity getShortUrlForHash(String hashUrl);

    int chkUrlKey(String urlKey);

    ShortUrlEntity getShortUrl(String urlKey);
    ShortUrlEntity getShortUrlUse(String urlKey);

    Boolean addShortUrl(ShortUrlEntity params);

    int chkHashUrlExcept(ShortUrlEntity params);
    Boolean updateShortUrl(ShortUrlEntity params);

    Boolean insertClickLog(ShortUrlLog params);

    int getLogListCnt(ShortUrlAdminReq params);

    List<ShortUrlLog> getLogList(ShortUrlAdminReq params);


}

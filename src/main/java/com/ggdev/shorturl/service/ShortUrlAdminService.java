package com.ggdev.shorturl.service;


import com.ggdev.shorturl.dto.ShortUrlAdminReq;
import com.ggdev.shorturl.dto.ShortUrlEntity;
import com.ggdev.shorturl.dto.ShortUrlLog;

import java.util.List;


public interface ShortUrlAdminService {

  List<ShortUrlEntity> getAllShortUrl();

  int getAllShortUrlCnt(ShortUrlAdminReq params);

  List<ShortUrlEntity> getShortUrlList(ShortUrlAdminReq params);

  ShortUrlEntity updateShortUrl(ShortUrlAdminReq params);

  List<ShortUrlLog> getLogList(ShortUrlAdminReq params);
  int getLogListCnt(ShortUrlAdminReq params);
}

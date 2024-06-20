package com.ggdev.shorturl.service;


import com.ggdev.shorturl.common.response.CommonException;
import com.ggdev.shorturl.dto.ShortUrlReq;
import com.ggdev.shorturl.dto.ShortUrlRes;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;


public interface ShortUrlService {

  ShortUrlRes getShortUrl(String urlKey);

  String getLongUrl(String urlKey, String pmi);

  ShortUrlRes addShortUrl(ShortUrlReq params) throws UnsupportedEncodingException, CommonException, NoSuchAlgorithmException;

}

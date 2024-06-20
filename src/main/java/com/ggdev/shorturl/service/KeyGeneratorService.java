package com.ggdev.shorturl.service;


import com.ggdev.shorturl.common.response.CommonException;

import java.io.UnsupportedEncodingException;

public interface KeyGeneratorService {

  String getKey(String hashUrl) throws UnsupportedEncodingException, CommonException;


}

package com.ggdev.shorturl.service;


import java.util.HashMap;

public interface ShortUrlAsyncService {

    void updateClickLog(String urlKey, HashMap<String, Object> headers) throws Exception;

}

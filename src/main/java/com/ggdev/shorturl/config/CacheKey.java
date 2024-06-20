
package com.ggdev.shorturl.config;

public class CacheKey {

    public static final String REDIS_CACHE_MANAGER = "redisCacheManager";
    public static final String CHANNEL_NAME = "ggdev";
    public static final long MIN_1 = 60;
    public static final long HOUR_1= MIN_1*60;
    public static final long DAY_1= HOUR_1*24;

    public static final long MON_1= DAY_1*30;

    public static final String SHORTURL = CHANNEL_NAME + ".shorturl";
    public static final long SHORTURL_EXPIRE = DAY_1*3; //3일

}

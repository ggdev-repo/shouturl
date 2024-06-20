package com.ggdev.shorturl.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class ShortUrlLog {

  private String basDt;
  private String urlKey;
  private String shortUrl;
  private String longUrl;
  private String headerParams;
  private String referer;
  private String userAgent;
  private String secChUa;
  @JsonFormat(pattern = "yyyyMMddHHmmss", timezone = "Asia/Seoul")
  private String regDttm;

}

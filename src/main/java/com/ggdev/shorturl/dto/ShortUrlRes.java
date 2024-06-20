package com.ggdev.shorturl.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ShortUrlRes {

  private String urlKey;
  private String shortUrl;
  private String longUrl;
  private String urlDesc;
  private String useYN; //N 미사용 Y 사용
  private String dupYN; // Y 중복
  @JsonFormat(pattern = "yyyyMMddHHmmss", timezone = "Asia/Seoul")
  private LocalDateTime regDttm;
  private String regId;
  @JsonFormat(pattern = "yyyyMMddHHmmss", timezone = "Asia/Seoul")
  private LocalDateTime modDttm;
  private String modId;

}

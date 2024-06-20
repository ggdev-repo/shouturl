package com.ggdev.shorturl.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShortUrlAdminReq {

  private int pageNumber = 1;
  private int perPageNum = 20;
  private String urlKey = "";
  private String userId = "";
  private String longUrl = "";
  private String urlDesc = "";
  private String useYN = ""; //N 미사용 Y 사용

}

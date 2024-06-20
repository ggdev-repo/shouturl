package com.ggdev.shorturl.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShortUrlReq {
  @URL
  private String longUrl = "";
  private String urlDesc = "";
  private String userId = "";
}

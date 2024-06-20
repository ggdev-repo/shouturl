package com.ggdev.shorturl.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class ShortUrlConfig {

  @Value("${env.service.url}")
  private String serviceUrl;

  @Value("${env.service.length}")
  private int length;
}

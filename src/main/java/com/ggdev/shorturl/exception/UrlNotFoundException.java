package com.ggdev.shorturl.exception;

public class UrlNotFoundException extends RuntimeException {

  public UrlNotFoundException(String urlKey) {
    super(String.format("UrlKey not found for [ %s ] link key", urlKey));
  }
}

package com.ggdev.shorturl.exception;

public class UrlAlreadyExistsException extends RuntimeException {

  public UrlAlreadyExistsException(String url) {
    super(String.format("Url [ %s ] already exists", url));
  }
}

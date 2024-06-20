package com.ggdev.shorturl.exception;

public class  InvalidLinkException extends RuntimeException {

  public InvalidLinkException(String url) {
    super(String.format("Url [ %s ] is invalid", url));
  }
}

package com.ggdev.shorturl.controller;

import com.ggdev.shorturl.common.response.CommonException;
import com.ggdev.shorturl.common.response.CommonResponseService;
import com.ggdev.shorturl.common.response.CommonResponseService.SingleResult;
import com.ggdev.shorturl.dto.ShortUrlReq;
import com.ggdev.shorturl.dto.ShortUrlRes;
import com.ggdev.shorturl.service.ShortUrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@RequestMapping("/shorturl/api")
@RestController
public class ShortUrlRestController {

  private final CommonResponseService responseService;
  private final ShortUrlService shortUrlService;

  @Autowired
  public ShortUrlRestController(CommonResponseService responseService, ShortUrlService shortUrlService) {
    this.responseService = responseService;
    this.shortUrlService = shortUrlService;
  }


  @GetMapping("/{urlKey}")
  public SingleResult<ShortUrlRes> getUrl(@PathVariable String urlKey) {
    ShortUrlRes res = shortUrlService.getShortUrl(urlKey);
    return responseService.getSingleResult(res);
  }

  @PostMapping("/add")
  public SingleResult<ShortUrlRes> addUrl(@RequestBody ShortUrlReq shortUrlReq) throws UnsupportedEncodingException, CommonException, NoSuchAlgorithmException {
    ShortUrlRes res = shortUrlService.addShortUrl(shortUrlReq);
    return responseService.getSingleResult(res);
  }

}

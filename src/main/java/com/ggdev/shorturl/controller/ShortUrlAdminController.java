package com.ggdev.shorturl.controller;

import com.ggdev.shorturl.common.response.CommonResponseService;
import com.ggdev.shorturl.common.response.CommonResponseService.ListResult;
import com.ggdev.shorturl.common.response.CommonResponseService.SingleResult;
import com.ggdev.shorturl.dto.ShortUrlAdminReq;
import com.ggdev.shorturl.dto.ShortUrlEntity;
import com.ggdev.shorturl.dto.ShortUrlLog;
import com.ggdev.shorturl.service.ShortUrlAdminService;
import com.ggdev.shorturl.utils.BaristaUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RequestMapping("/shorturl/admin")
@RestController
public class ShortUrlAdminController {

  private final CommonResponseService responseService;
  private final ShortUrlAdminService shortUrlAdminService;

  @Autowired
  public ShortUrlAdminController(CommonResponseService responseService, ShortUrlAdminService shortUrlAdminService) {
    this.responseService = responseService;
    this.shortUrlAdminService = shortUrlAdminService;
  }

  @GetMapping("/allurl")
  public ListResult<ShortUrlEntity> getAllUrl() {
    List<ShortUrlEntity> list = shortUrlAdminService.getAllShortUrl();
    return responseService.getListResult(list,list.size(), 0);

  }

  @PostMapping("/urllist")
  public ListResult<ShortUrlEntity> getUrlList(@RequestBody ShortUrlAdminReq params) {

    List<ShortUrlEntity> list = shortUrlAdminService.getShortUrlList(params);
    int totalCount = shortUrlAdminService.getAllShortUrlCnt(params);
    int nextKey = new BaristaUtils().getNextKey(params.getPageNumber(), params.getPerPageNum(), totalCount);

    return responseService.getListResult(list, totalCount, nextKey);

  }

  @PostMapping("/update")
  public SingleResult<ShortUrlEntity> updateUrl(@RequestBody ShortUrlAdminReq params) {
    ShortUrlEntity res = shortUrlAdminService.updateShortUrl(params);
    return responseService.getSingleResult(res);
  }

  @PostMapping("/loglist")
  public ListResult<ShortUrlLog> getLogList(@RequestBody ShortUrlAdminReq params) {
    List<ShortUrlLog> list = shortUrlAdminService.getLogList(params);
    int totalCount = shortUrlAdminService.getLogListCnt(params);
    int nextKey = new BaristaUtils().getNextKey(params.getPageNumber(), params.getPerPageNum(), totalCount);
    return responseService.getListResult(list,list.size(), nextKey);

  }

}

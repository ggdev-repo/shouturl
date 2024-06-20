package com.ggdev.shorturl.controller;

import com.ggdev.shorturl.service.ShortUrlAsyncService;
import com.ggdev.shorturl.service.ShortUrlService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Mono;
import java.util.HashMap;

@Slf4j
@Controller
public class ShortUrlController {

    final private ShortUrlService shortUrlService;
    final private ShortUrlAsyncService shortUrlAsyncService;

    @Autowired
    public ShortUrlController(ShortUrlService shortUrlService, ShortUrlAsyncService shortUrlAsyncService) {
        this.shortUrlService = shortUrlService;
        this.shortUrlAsyncService = shortUrlAsyncService;
    }


  @GetMapping("/{urlKey}")
  public Mono<String> redirect(
          HttpServletRequest request,
          @RequestHeader HashMap<String, Object> headers,
          @PathVariable String urlKey) throws Exception {

      String longUrl = shortUrlService.getLongUrl(urlKey, "");
      shortUrlAsyncService.updateClickLog(urlKey, headers);

      // FeedbackAop 에서 longUrl 사용을 위해 셋팅
      headers.put("shortUrl", request.getRequestURI());
      headers.put("longUrl", longUrl);
      return Mono.just("redirect:"+longUrl);

  }

    @GetMapping("/{urlKey}/{pmi}")
    public Mono<String> redirectWithPMI(
            HttpServletRequest request,
            @RequestHeader HashMap<String, Object> headers,
            @PathVariable String urlKey,
            @PathVariable String pmi) throws Exception {

        String longUrl = shortUrlService.getLongUrl(urlKey, pmi);
        shortUrlAsyncService.updateClickLog(urlKey, headers);

        // FeedbackAop 에서 longUrl 사용을 위해 셋팅
        headers.put("shortUrl", request.getRequestURI());
        headers.put("longUrl", longUrl);

        return Mono.just("redirect:"+longUrl);

    }

}

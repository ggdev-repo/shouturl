package com.ggdev.shorturl.common.response;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@RestControllerAdvice
public class CommonExceptionHandler {

	@Autowired
	private Environment env;

	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(value = HttpStatus.OK)
	protected ResponseEntity<CommonResponseModel> handleException(Exception e) {
		
		CommonResponseModel response;
 		
		String className = e.getClass().getSimpleName();
		
		if(className.contains("CommonException")) {
			response = new CommonResponseModel(e.getMessage());	
		}else {
			response = new CommonResponseModel(e.getMessage());
		}
	
		HttpHeaders httpHeaders = new HttpHeaders();
		try {
			httpHeaders.set("exceptionCode", URLEncoder(response.getErrorCode()));
			if(className.contains("CommonException")) {
				httpHeaders.set("exceptionMessage", URLEncoder(response.getMessage()));
			}else {
				httpHeaders.set("exceptionMessage", URLEncoder(e.getMessage()));
			}
 		} catch (Exception ex) {
			//LogUtil.error(ex, ex.getMessage());
		}

		//Error 로그
		//LogUtil.error(e, response.getMessage());
		
 		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
	}
	
	@Data
	public class CommonResponseModel {

		private LocalDateTime timestamp;
		private int status;
		private boolean error;
		private String errorCode;
		private String message;

		public CommonResponseModel() {
			this.timestamp = LocalDateTime.now();
			this.error = HttpStatus.BAD_REQUEST.isError();
			this.status = HttpStatus.OK.value();  // 클라이언트에서는 정상 응답으로 보내고 errorCode 로 처리
			this.errorCode = "E999";
			this.message = env.getProperty("message.errorCode." + errorCode);
		}
		
		public CommonResponseModel(String message) {
			this.timestamp = LocalDateTime.now();
			this.error = HttpStatus.BAD_REQUEST.isError();
			this.status = HttpStatus.OK.value();  // 클라이언트에서는 정상 응답으로 보내고 errorCode 로 처리
			this.errorCode = "E999";
			this.message = message;
			
			if(message.startsWith("E")) {
				this.errorCode = message;	
				this.message = env.getProperty("message.errorCode." + errorCode);
			}
		}
		
	}
	
	private String URLEncoder(String value) throws Exception {
		return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
	}
	
	private String URLDecoder(String value) throws Exception {
		return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
	}

}
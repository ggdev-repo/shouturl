package com.ggdev.shorturl.common.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
public class CommonException extends Exception {
	
	@Serial
	private static final long serialVersionUID = -4459466437943410342L;
	private String message;
	private String errorCode;
 	
	public CommonException(String message) {
		setMessage(message);
	}


	public CommonException(String message, String errorCode) {
		setMessage(message);
		setErrorCode(errorCode);
	}
	
}

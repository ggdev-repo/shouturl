package com.ggdev.shorturl.common.response;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommonResponseService {

	@Autowired
	private Environment env;

	public <T> SingleResult<T> getSingleResult(T data) {
		SingleResult<T> result = new SingleResult<>();
		result.setData(data);
		return result;
	}

	public <T> ListResult<T> getListResult(List<T> list) {
		ListResult<T> result = new ListResult<>();
		result.setList(list);
		return result;
	}

	
	public <T> ListResult<T> getListResult(List<T> list, int totalCount) {
		ListResult<T> result = new ListResult<>();
		result.setList(list);
		result.setDataCount(list.size());
		result.setTotalCount(totalCount);
		return result;
	}
	
	public <T> ListResult<T> getListResult(List<T> list, int totalCount, int nextKey) {
		ListResult<T> result = new ListResult<>();
		result.setList(list);
		result.setDataCount(list.size());
		result.setTotalCount(totalCount);
		result.setNextKey(nextKey);
		return result;
	}
	
	@Data
	public class SingleResult<T> {

		private LocalDateTime timestamp;
		private int status;
		private boolean error;
		private String errorCode;
		private String message;
		private T data;

		public SingleResult() {
			this.timestamp = LocalDateTime.now();
			this.error = HttpStatus.OK.isError();
			this.status = HttpStatus.OK.value();
			this.errorCode = "E000";
			this.message = env.getProperty("message.errorCode." + errorCode);
		}
		
		public SingleResult(String message, String code) {
			this.timestamp = LocalDateTime.now();
			this.error = HttpStatus.OK.isError();
			this.status = HttpStatus.OK.value();
			this.errorCode = code;
			this.message = message;
		}
		
	}

	@Data
	public class ListResult<T> {

		private LocalDateTime timestamp;
		private int status;
		private boolean error;
		private String errorCode;
		private String message;
		private Integer dataCount;  //배열 길이
		private Integer totalCount; //페이징 별 데이터 총합 카운트
		private List<T> list;
		private int nextKey;

		public ListResult() {
			this.timestamp = LocalDateTime.now();
			this.status = HttpStatus.OK.value();
			this.error = HttpStatus.OK.isError();
			this.errorCode = "E000";
			this.message = env.getProperty("message.errorCode." + errorCode);
			this.dataCount = 0;
			this.totalCount = 0;
			this.nextKey = 1;
		}
	}

}

package com.cafe24.mysite.dto;

public class JSONResult { // 데이터 전달을 위한 것이 DTO, 자신(or 회사)만의 전송 규약 설정

	private String result; // "success" or "fail"
	private String message; // result 가 "fail" 이면 원인 메세지
	private Object data; // result가 "success"이면 전달한 데이터

	private JSONResult(String result, String message, Object data) {
		this.result = result;
		this.message = message;
		this.data = data;
	}

	public static JSONResult success(Object data) {
		return new JSONResult("success", null, data);
	}

	public static JSONResult fail(String message) {
		return new JSONResult("fail", message, null);
	}

	public String getResult() {
		return result;
	}

	public String getMessage() {
		return message;
	}

	public Object getData() {
		return data;
	}

}

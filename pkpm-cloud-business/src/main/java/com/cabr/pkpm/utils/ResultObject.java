package com.cabr.pkpm.utils;

import org.springframework.http.HttpStatus;

public class ResultObject {
	private Integer code;

    private String message;

    private Object data;

    private ResultObject() {}

    public static ResultObject success() {
        ResultObject result = new ResultObject();
        result.code = HttpStatus.OK.value();
        return result;
    }

    public static ResultObject success(Object data) {
        ResultObject result = new ResultObject();
        result.code = HttpStatus.OK.value();
        result.data = data;
        return result;
    }

    public static ResultObject success(Object data,String message) {
        ResultObject result = new ResultObject();
        result.code = HttpStatus.OK.value();
        result.message=message;
        result.data = data;
        return result;
    }

    public static ResultObject failure(String message) {
        ResultObject result = new ResultObject();
        result.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        result.message = message;
        return result;
    }

    public static ResultObject failure(Integer code, String message) {
        ResultObject result = new ResultObject();
        result.code = code;
        result.message = message;
        return result;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}

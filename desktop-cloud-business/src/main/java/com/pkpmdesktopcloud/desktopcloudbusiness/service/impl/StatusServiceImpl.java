package com.pkpmdesktopcloud.desktopcloudbusiness.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.desktop.utils.HttpConfigBuilder;
import com.desktop.utils.JsonUtil;
import com.desktop.utils.exception.Exceptions;
import com.desktop.utils.page.ResultObject;
import com.gateway.common.domain.CommonRequestBean;
import com.pkpm.httpclientutil.HttpClientUtil;
import com.pkpm.httpclientutil.MyHttpResponse;
import com.pkpm.httpclientutil.common.HttpMethods;
import com.pkpm.httpclientutil.exception.HttpProcessException;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.StatusService;
@Service
public class StatusServiceImpl implements StatusService {
	
	@Value("${server.host}")
	private String serverHost;

	@SuppressWarnings("unused")
	@Override
	public String queryDesktopStatus(CommonRequestBean commonRequestBean) {
		
		String urlDesktopStatus =serverHost + "/status/desktopStatus";
		
		try {
			String strJson = JsonUtil.serialize(commonRequestBean);
			String strResponse = HttpClientUtil.mysend(
					HttpConfigBuilder.buildHttpConfigNoToken(urlDesktopStatus, strJson, 5, "utf-8", 10000).method(HttpMethods.POST));
			MyHttpResponse myHttpResponse = JsonUtil.deserialize(strResponse, MyHttpResponse.class);
			Integer statusCode = myHttpResponse.getStatusCode();
			
			if(HttpStatus.OK.value() == statusCode){
				String body = myHttpResponse.getBody();
				ResultObject result = JsonUtil.deserialize(body, ResultObject.class);
				Integer code = result.getCode();
				if(HttpStatus.OK.value() == code){
					return (String) result.getData();
				}
			}
		
		} catch (HttpProcessException e) {
			
			e.printStackTrace();
		}
		throw  Exceptions.newBusinessException("查询桌面状态失败,请重新尝试!");
	}

	public String queryOperateStatus(CommonRequestBean commonRequestBean) {
		  String urlOperateStatus =serverHost + "/status/operateStatus";
		
		try {
			String strJson = JsonUtil.serialize(commonRequestBean);
			String strResponse = HttpClientUtil.mysend(
					HttpConfigBuilder.buildHttpConfigNoToken(urlOperateStatus, strJson, 5, "utf-8", 10000).method(HttpMethods.POST));
			MyHttpResponse myHttpResponse = JsonUtil.deserialize(strResponse, MyHttpResponse.class);
			Integer statusCode = myHttpResponse.getStatusCode();
			
			if(HttpStatus.OK.value() == statusCode){
				String body = myHttpResponse.getBody();
				ResultObject result = JsonUtil.deserialize(body, ResultObject.class);
				Integer code = result.getCode();
				if(HttpStatus.OK.value() == code){
					return (String) result.getData();
				}
			}
		
		} catch (HttpProcessException e) {
			
			e.printStackTrace();
		}
		throw  Exceptions.newBusinessException("查询桌面操作状态失败,请重新尝试!");
		
	}

}

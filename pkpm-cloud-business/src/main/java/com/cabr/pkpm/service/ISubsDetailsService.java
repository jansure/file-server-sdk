package com.cabr.pkpm.service;

import com.cabr.pkpm.utils.ResponseResult;

public interface ISubsDetailsService {
	//产品列表
	ResponseResult showList(int userId, Integer currentPage, Integer pageSize);

}

package com.gatewayserver.gatewayserver.service;


import com.gateway.common.domain.CommonRequestBean;
import com.gateway.common.dto.ad.AdComputer;
import com.gateway.common.dto.ad.AdUser;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdService {


    String addAdUser(CommonRequestBean requestBean) ;

    String updateAdUser(CommonRequestBean requestBean);

    Integer getUserCountByAdIpAddress(String adIpAddress);

    Integer getUserOuCountByAdId(Integer adId);

    List<AdUser> getUsersByAdId(Integer adId);

    List<AdComputer>  getComputersByAdId(Integer adId);

    /**
     * @Description: 检查用户是否存在
     * @Param: * @param userName,adId
     * @Return boolean
     */
    Boolean checkUser(String userName, Integer adId);

    void deleteUser(String userName, Integer adId);

    void deleteComputer(String computerName, Integer adId);

}
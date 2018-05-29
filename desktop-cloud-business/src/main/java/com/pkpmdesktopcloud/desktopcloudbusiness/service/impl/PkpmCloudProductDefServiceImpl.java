package com.pkpmdesktopcloud.desktopcloudbusiness.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pkpmdesktopcloud.desktopcloudbusiness.constants.SysConstant;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.PkpmCloudComponentDefDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.PkpmCloudProductDefDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudComponentDef;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudNavigation;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudProductDef;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmSysConfig;
import com.pkpmdesktopcloud.desktopcloudbusiness.dto.ComponentVO;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.PkpmCloudProductDefService;
import com.pkpmdesktopcloud.redis.RedisCache;

/**
 * 产品接口实现类
 * 
 * @author yangpengfei
 *
 */
@Service
public class PkpmCloudProductDefServiceImpl implements PkpmCloudProductDefService {
	
	
	private static final String PRODUCT_ID = "product";
	
	private static final String PRODUCT_TYPE_LIST_ID = "productTypeList";
	
	private static final String ALL_TYPE_REDIS_KEY = "allTypeRedisKey";
	
	@Resource
	private PkpmCloudProductDefDAO productDAO;
	
	@Resource
	private PkpmCloudComponentDefDAO componentDAO;

	@Override
	public List<PkpmCloudProductDef> getProductByType(Integer productType) {
		
		// 若存在Redis缓存，从缓存中读取
		RedisCache cache = new RedisCache(PRODUCT_ID);
		List<PkpmCloudProductDef> productInfo = (List<PkpmCloudProductDef>)cache.getObject(productType);
		if(productInfo != null) {
			
			return productInfo;
		}
			
		// 若不存在对应的Redis缓存，从数据库查询
		productInfo = productDAO.getProductByType(productType);
		// 写入Redis缓存
		cache.putObject(PRODUCT_ID, productInfo);
		return productInfo;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<PkpmCloudProductDef> getProductTypeList() {
		RedisCache cache = new RedisCache(PRODUCT_TYPE_LIST_ID);
		List<PkpmCloudProductDef> productTypeList = (List<PkpmCloudProductDef>)cache.getObject(ALL_TYPE_REDIS_KEY);
		
		// 若存在Redis缓存，从缓存中读取
		if (productTypeList != null) {
			return productTypeList;
		}
		
		// 若不存在对应的Redis缓存，从数据库查询
		productTypeList = productDAO.getProductTypeList();
		for (PkpmCloudProductDef product : productTypeList) {
			// 设置“全家桶类型”为默认套餐
			if (SysConstant.PRODUCT_TYPE_ALL.equals(product.getProductType().toString())) {
				product.setIsDefault(SysConstant.IS_DEFAULT_YES);
			}else {
				product.setIsDefault(SysConstant.IS_DEFAULT_NO);
			}
		}
		
		// 写入Redis缓存
		cache.putObject(ALL_TYPE_REDIS_KEY, productTypeList);
		return productTypeList;
	}


	/* (non-Javadoc)
	 * @see com.pkpmdesktopcloud.desktopcloudbusiness.service.PkpmCloudProductDefService#getProductBuyMap()
	 */
	@Override
	public Map<String, Object> getProductBuyMap() {
		Map<String, Object> map = new HashMap<String, Object>(16);
		// 产品套餐类型列表
		List<PkpmCloudProductDef> productTypeList = this.getProductTypeList();
		map.put(SysConstant.BUY_TYPE, productTypeList);
		
		List<PkpmCloudComponentDef> componentDefList = componentDAO.getList();
		if (componentDefList != null && componentDefList.size() > 0) {
			// 软件名称列表
			Map<String, List<PkpmCloudComponentDef>> softwareTypeMap = componentDefList.stream().filter(PkpmCloudComponentDef->PkpmCloudComponentDef.getComponentType().equals(Integer.parseInt(SysConstant.BUY_APP)))
					.collect(Collectors.groupingBy(PkpmCloudComponentDef::getComponentDesc));
			map.put(SysConstant.BUY_APP, softwareTypeMap);
		
			// 地域列表
			List<PkpmCloudComponentDef> areaList = componentDefList.stream().filter(PkpmCloudComponentDef->PkpmCloudComponentDef.getComponentType().equals(Integer.parseInt(SysConstant.BUY_AREA))).collect(Collectors.toList());
			map.put(SysConstant.BUY_AREA, areaList);
			
			// 主机配置列表
			List<PkpmCloudComponentDef> hostConfigList = componentDefList.stream().filter(PkpmCloudComponentDef->PkpmCloudComponentDef.getComponentType().equals(Integer.parseInt(SysConstant.BUY_HOST))).collect(Collectors.toList());
			map.put(SysConstant.BUY_HOST, hostConfigList);
			
			// 云存储列表
			List<PkpmCloudComponentDef> storageList = componentDefList.stream().filter(PkpmCloudComponentDef->PkpmCloudComponentDef.getComponentType().equals(Integer.parseInt(SysConstant.BUY_STORAGE))).collect(Collectors.toList());
			map.put(SysConstant.BUY_STORAGE, storageList);
		}

		return map;
	}
}

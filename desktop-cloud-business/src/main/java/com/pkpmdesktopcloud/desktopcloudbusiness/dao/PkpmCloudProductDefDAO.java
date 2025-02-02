package com.pkpmdesktopcloud.desktopcloudbusiness.dao;

import java.util.List;
import java.util.Map;

import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudNavigation;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudProductDef;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmSysConfig;
import com.pkpmdesktopcloud.desktopcloudbusiness.dto.ComponentVO;

/**
 * 产品映射接口
 * @author yangpengfei
 * @date 2017/12/25
 *
 */
public interface PkpmCloudProductDefDAO {
	/**
	 * 根据父id获取子目录
	 * @param productType 产品类型
	 * @return
	 */
    List<PkpmCloudProductDef> getProductByType(Integer productType);
    
    
    /**
     * 根据productId获取对应的product实体
     * @param productId
     * @return
     */
    List<PkpmCloudProductDef> getProductByProductId(Integer productId);
    
    /**
     * 返回全部产品套餐列表
     * @return
     */
    List<PkpmCloudProductDef> getProductTypeList();
    
}

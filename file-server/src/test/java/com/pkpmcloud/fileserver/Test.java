package com.pkpmcloud.fileserver;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/19 23:38 <br/>
 */
public class Test {
    /**
     * 日志记录器
     */
    private final static Logger logger = LoggerFactory.getLogger(Test.class);

    @org.junit.Test
    public void test01() {
//        logger.info("232");
//        logger.info("232");
//        logger.info("232");
//        logger.info("232");
//        logger.info("232");
//        logger.info("232");
//        logger.info("232");
//        logger.info("232");
//        logger.info("232");
//        logger.info("232");
    	String s = "group1";
    	System.out.println(s.length());
    	System.out.println(StringUtils.substring(s, 5, s.length()));
    }
    
}

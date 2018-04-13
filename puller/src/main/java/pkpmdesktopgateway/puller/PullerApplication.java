package pkpmdesktopgateway.puller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class PullerApplication {

    public static void main(String[] args) {
    	
    	//启动增加参数opType，控制监听类型
    	//opType取值，（DESKTOP:创建桌面,DELETE::删除桌面,CHANGE:修改桌面属性,RESIZE:变更桌面规格,BOOT:启动桌面,REBOOT:重启桌面,CLOSE:关闭桌面）
    	//opType取值不区分大小写，多个类型值用英文逗号分割
    	//示例：java -jar xxx.jar -DopType=DESKTOP,DELETE,CHANGE
        SpringApplication.run(PullerApplication.class, args);
    }
}
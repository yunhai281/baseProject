package com.boyuyun.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * PropertiesUtils
 * 
 * @author Lynch 2014-09-15
 *
 */
public class PropertiesUtils {
	/**
	 * 根据数据库系统设置参数，获取环信app-key配置文件
	 * <br/>
	 * <b>首先判断生产/开发/演示环境，然后根据Sys.CURRENT_SCHOOL_SERIALIZE_NUMBER是否大于0判断是否是为学校单独部署的系统</b>
	 * @author XHL
	 * @return
	 */
	public static Properties getProperties() {
		Properties p = new Properties();
		InputStream inputStream = null;
		try {
			String fileName = "RestAPIConfig.properties";
			inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName);
			p.load(inputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}finally{
			if(inputStream!=null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return p;
	}

}

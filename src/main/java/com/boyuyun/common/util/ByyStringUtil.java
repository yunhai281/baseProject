package com.boyuyun.common.util;

import java.util.UUID;


public class ByyStringUtil {
	/**
	 * 获取随机的，去掉横线的uuid
	 * @return
	 */
	public static String getRandomUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}

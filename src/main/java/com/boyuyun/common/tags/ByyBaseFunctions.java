package com.boyuyun.common.tags;

import com.boyuyun.base.util.SystemParam;

public class ByyBaseFunctions {
	/**
	 * 获取系统参数
	 * @param parmName
	 * @return
	 */
	public static String param(String parmName){
		return SystemParam.get(parmName);
	}
}

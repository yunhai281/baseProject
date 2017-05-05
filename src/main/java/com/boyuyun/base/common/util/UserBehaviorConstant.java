package com.boyuyun.base.common.util;

import java.util.HashMap;
import java.util.Map;

public class UserBehaviorConstant {
	public static Map<String, String> behaviorTypeMap = new HashMap<String, String>();//操作习惯类型的 map,key是id
	static{
		behaviorTypeMap.put("1", "default_page_rows");//用户操作习惯，列表显示条数
	}
}

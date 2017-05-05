package com.boyuyun.base.services.action;

import com.boyuyun.base.util.base.BaseAction;

public class TestServiceAction extends BaseAction{
	public String test()throws Exception{
		response.setContentType("application/json");
		this.print("{userName:\"admin\",password:\"123321\",lastLogin:\"2017-02-17 12:32:12\"}");
		return null;
	}
	public String test1()throws Exception{
		response.setContentType("text/json");
		this.print("{userName:\"admin\",password:\"123321\",lastLogin:\"2017-02-17 12:32:12\"}");
		return null;
	}
}

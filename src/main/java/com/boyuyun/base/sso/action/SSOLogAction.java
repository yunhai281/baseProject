package com.boyuyun.base.sso.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.boyuyun.base.sso.biz.SSOLogBiz;
import com.boyuyun.base.sso.entity.SSOLog;
import com.boyuyun.base.util.base.BaseAction;
import com.boyuyun.common.json.ByyJsonUtil;
import com.boyuyun.common.util.ByyDateUtil;
import com.opensymphony.xwork2.ModelDriven;
@Controller
public class SSOLogAction extends BaseAction implements ModelDriven<SSOLog>{
	private SSOLog ssoLog = new SSOLog();
	
	@Resource
	private SSOLogBiz ssoLogBiz;
	public String toList()throws Exception{
		return "logList";
	}
	public String getList()throws Exception{
		String result = "";
		List<SSOLog> pageInfo = ssoLogBiz.getListPaged(ssoLog);
		int count = ssoLogBiz.getListPagedCount(ssoLog);
		result = ByyJsonUtil.serialize(count,pageInfo,ByyDateUtil.YYYY_MM_DD_HH_MM_SS);
		this.print(result);
		return null;
	}
	@Override
	public SSOLog getModel() {
		return (SSOLog)initPage(ssoLog);
	}
}

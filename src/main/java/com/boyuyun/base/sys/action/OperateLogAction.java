package com.boyuyun.base.sys.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.boyuyun.base.sso.entity.SSOLog;
import com.boyuyun.base.sys.biz.OperateLogBiz;
import com.boyuyun.base.sys.entity.OperateLog;
import com.boyuyun.common.json.ByyJsonUtil;
import com.boyuyun.common.util.ByyDateUtil;
import com.dhcc.common.struts2.DhccActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Controller
public class OperateLogAction extends DhccActionSupport implements ModelDriven<OperateLog>
{

	private OperateLog operateLog=new OperateLog();
	
	@Resource
	private OperateLogBiz operateLogServices;
	
	public String toList()throws Exception
	{
		return "toList";
	}
	
	public String getList()throws Exception
	{
		String result = "";
		List<OperateLog> pageInfo = operateLogServices.getListPaged(operateLog);
		int count = operateLogServices.getListPagedCount(operateLog);
		result = ByyJsonUtil.serialize(count,pageInfo,ByyDateUtil.YYYY_MM_DD_HH_MM_SS);
		response.getWriter().print(result);
		return null;
	}
	
	@Override
	public OperateLog getModel() 
	{
		return (OperateLog)initPage(operateLog);
	}

}

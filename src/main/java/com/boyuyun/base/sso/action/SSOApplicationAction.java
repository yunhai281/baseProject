package com.boyuyun.base.sso.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.boyuyun.base.sso.biz.SSOApplicationBiz;
import com.boyuyun.base.sso.entity.SSOApplication;
import com.boyuyun.base.util.base.BaseAction;
import com.boyuyun.common.annotation.LogThisOperate;
import com.boyuyun.common.autolog.OperateType;
import com.boyuyun.common.json.ByyJsonUtil;
import com.google.common.base.Strings;
import com.opensymphony.xwork2.ModelDriven;

@Controller
public class SSOApplicationAction extends BaseAction implements ModelDriven<SSOApplication>{
	private SSOApplication app = new SSOApplication();
	
	@Override
	public SSOApplication getModel() {
		return (SSOApplication) initPage(app);
	}
	@Resource
	private SSOApplicationBiz sSoApplicationService;
	/**
	 * 跳转至列表页面
	 * @return
	 */
	public String toList(){
		return "list";
	}
	
	public String toView(){
		return "view";
	}
	
	public String detail(){
		return "detail";
	}
	
	public String getList()throws Exception{
		String result = "";
		List<SSOApplication> pageInfo = sSoApplicationService.getListPaged(app);
		int count = sSoApplicationService.getListPagedCount(app);
		result = ByyJsonUtil.serialize(count,pageInfo);
		this.print(result);
		return null;
	} 
	
	/**
	 * 保存编辑
	 * @param course
	 * @return
	 */
	@LogThisOperate(module="应用管理",operateType=OperateType.新增或修改)
	public String save()throws Exception{
		String id = request.getParameter("id");
		String result =  this.getFailJson("保存失败!");
		String attr = request.getParameter("attribute");
		String[] attrs = attr.split(",");
		if(Strings.isNullOrEmpty(id)){
			//新增
			if(app.getEvaluation_order()>-1){
			}else {
				app.setEvaluation_order(sSoApplicationService.getMaxSortNum());
			}			
			this.sSoApplicationService.add(app,attrs);
			result = this.getSuccessJson("保存成功!");
		}else {
			//编辑
			this.sSoApplicationService.update(app,attrs);
			result = this.getSuccessJson("保存成功!");
		}
		this.print(result);
		return null;
	}
	
	@LogThisOperate(module="应用管理",operateType=OperateType.删除)
	public String delete()throws Exception{
		String id = request.getParameter("id");
		String [] ids = request.getParameterValues("ids");
		String json = this.getFailJson("删除失败!");
		List IDSToDelete = new ArrayList();
		if(ids!=null&&ids.length>0){
			List list = new ArrayList();
			for (int i = 0; i < ids.length; i++) {
				if(Strings.isNullOrEmpty(ids[i]))continue;
				SSOApplication application =new SSOApplication();
				application.setId(Integer.valueOf(ids[i]));
				IDSToDelete.add(Integer.valueOf(ids[i]));
				list.add(application);
			}
			this.setBatchListId(IDSToDelete);
			this.sSoApplicationService.deleteAll(list);
			json = this.getSuccessJson("删除成功!");
		}else if(!Strings.isNullOrEmpty(id)){
			SSOApplication application =new SSOApplication();
			application.setId(Integer.valueOf(id));
			this.sSoApplicationService.delete(application);
			json = this.getSuccessJson("删除成功!");
		}
		this.print(json);
		return null;
	}

	public String getBean()throws Exception{
		SSOApplication application = sSoApplicationService.get(app.getId());
		String json = "";
		if(application!=null){
			json = ByyJsonUtil.serialize(application);
		}
		this.print(json);
		return null;
	}
	
	/**
	 * @Description 取得最大的编号 
	 * @author jms
	 * @return
	 * @throws Exception
	 */
	public String getMaxOrder()throws Exception{
		String result = "";
		int max= sSoApplicationService.getMaxOrder(); 
		this.print(String.valueOf(max+1) );
		return null;
	} 
}

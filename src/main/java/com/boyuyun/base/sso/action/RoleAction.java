package com.boyuyun.base.sso.action;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;

import com.boyuyun.base.sso.biz.RoleBiz;
import com.boyuyun.base.sso.biz.SSOApplicationBiz;
import com.boyuyun.base.sso.entity.Role;
import com.boyuyun.base.sso.entity.SSOApplication;
import com.boyuyun.base.user.entity.User;
import com.boyuyun.base.util.ConstantUtil;
import com.boyuyun.base.util.base.BaseAction;
import com.boyuyun.common.annotation.LogThisOperate;
import com.boyuyun.common.autolog.OperateType;
import com.boyuyun.common.json.ByyJsonUtil;
import com.boyuyun.common.util.ByyStringUtil;
import com.opensymphony.xwork2.ModelDriven;

@Controller 
public class RoleAction extends BaseAction implements ModelDriven<Role>{

	private Role role = new Role();
	 
	@Override
	public Role getModel() { 
		return (Role)initPage(role);
	}
	@Resource
	private RoleBiz roleBiz;

	@Resource
	private SSOApplicationBiz ssoApplicationBiz;
	
	
	/**
	 * @Description 跳转到角色管理列表页面
	 * @author jms
	 * @return
	 */ 
	public String toList(){
		return "list";
	}
	
	/**
	 * @Description 跳转到角色管理新增页面
	 * @author jms
	 * @return
	 */ 
	public String toAdd(){
		return "view";
	}
	
	/**
	 * 角色管理查看人员地址跳转
	 * @return
	 */
	public String toUsers(){
		response.setCharacterEncoding("utf-8");
		String roleId = request.getParameter("roleId");
		request.setAttribute("roleId", roleId);
		return "toUsers";
	}
	
	public String getUsers()throws Exception{
		String result = "";
		List<User> pageInfo = roleBiz.getUserByRolePaged(role);
		int count = roleBiz.getUserByRoleCount(role.getId());
		result = ByyJsonUtil.serialize(count,pageInfo);
		this.print(result);
		return null;
	}
	/**
	 * 得到树形列表
	 * @param request
	 * @param response
	 * @return
	 */
	public String getList()throws Exception{
		String result = "";
		if(role.getId()!=null && role.getId().equals("0")){
			JSONObject joMenu = new JSONObject();
			joMenu.element("id",""); 
			joMenu.element("isParent", true);
			joMenu.element("name",ConstantUtil.SYSTEM_NAME);
			result = ByyJsonUtil.serialize(joMenu);
		}else if(role.getId()==null || "".equals(role.getId()) ){
			// 取得全部角色
			List<Role> list= roleBiz.getAllRoleList();
			if(list!=null){
				JSONArray ja = new JSONArray();
				for(Iterator iterator = list.iterator(); iterator.hasNext();){
					JSONObject jo = new JSONObject();
					Role m = (Role) iterator.next();
					jo.element("id",m.getId());
					jo.element("name",m.getName()); 
					ja.add(jo);
				}
				result = ByyJsonUtil.serialize(ja);
			}else {
				result = this.getSuccessJson("没有数据!");
			} 
		}else {
			result = this.getSuccessJson("没有数据!");
		}
		this.print(result);
		return null;
	}
	
	
	/**
	 * @Description 菜单明细
	 * @author jms
	 * @return
	 * @throws Exception
	 */
	public String getBean()throws Exception{ 
		String result = null; 
		Role roleTemp = roleBiz.get(role.getId());
		if(role !=null){
			result = ByyJsonUtil.serialize(roleTemp,new String[]{});
		}else{
			result = this.getFailJson("网络异常!"); 
		}
		this.print(result);
		return null;
	}
	
	/**
	 * @Description 新增或者保存
	 * @author jms
	 * @param role
	 * @return
	 * @throws Exception
	 */
	@LogThisOperate(module="角色设置",operateType=OperateType.新增或修改)
	public String save() throws Exception{
		String result = this.getFailJson("操作失败");
		if(role.getId()!=null && role.getId().trim().length() > 0){
			
			roleBiz.update(role);
			result = this.getSuccessJson("操作成功");
		}else{
			//插入操作/新增
			String id=ByyStringUtil.getRandomUUID();
			role.setId(id); 
			roleBiz.add(role);
			result = this.getSuccessJson("操作成功");
		}
		this.print(result);
		return null;
	}
	
	/**
	 * @Description 删除
	 * @author jms
	 * @return
	 * @throws Exception
	 */
	@LogThisOperate(module="角色设置",operateType=OperateType.删除)
	public String delete()throws Exception{
		String result = this.getFailJson("删除失败!");
		if(role.getId()!=null && role.getId().length()!=0){
			roleBiz.delete(role.getId());
			result = this.getSuccessJson("删除成功!");
		}
		this.print(result);
		return null;
	}
	
	/**
	 * @Description 取得全部已经启用的实体类
	 * @author jms
	 * @return
	 * @throws Exception
	 */
	public String getAllAppList( )throws Exception{
		// 取得全部系统设置中已启用的应用
		List<SSOApplication> list= ssoApplicationBiz.getAllEnabledAppList();
		request.setAttribute("list", list);
		return "viewapp";
	}
	
	/**
	 * @Description  保存设置
	 * @author jms
	 * @param id
	 * @param roleId
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	@LogThisOperate(module="设置角色应用范围",operateType=OperateType.新增)
	public String saveApp() throws Exception{
		String id= request.getParameter("id");
		String roleId= request.getParameter("roleId");
		String appId= request.getParameter("appId");
		id = UUID.randomUUID().toString().replace("-", "");
		String result = this.getFailJson("操作失败");
		if(roleBiz.saveApp(id,roleId, appId)){
		result = this.getSuccessJson("操作成功");
		}
		this.print(result);
		return null;
	}
	
	
	public String getAPP() throws Exception{
		String result = this.getFailJson("操作失败");
		String roleId= request.getParameter("roleId");
		List list=roleBiz.getAPP(roleId);
		result = ByyJsonUtil.serialize(list);
		this.print(result);
		return null;
	}
	
	/**
	 * @Description 角色查询
	 * @author jms
	 * @return
	 * @throws Exception
	 */
	public String toSearch() throws Exception{
		String result = "";
		String name = request.getParameter("name");
		//生成一棵树
		List<Role> list = roleBiz.searchByName(name);
		result = ByyJsonUtil.serialize(list);
		this.print(result);
		return null;
	}
}

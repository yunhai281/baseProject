package com.boyuyun.base.sso.action;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;

import com.boyuyun.base.sso.biz.RoleUserTypeBiz;
import com.boyuyun.base.sso.entity.Role;
import com.boyuyun.base.sso.entity.RoleScope;
import com.boyuyun.base.util.base.BaseAction;
import com.boyuyun.common.annotation.LogThisOperate;
import com.boyuyun.common.autolog.OperateType;
import com.boyuyun.common.json.ByyJsonUtil;
import com.opensymphony.xwork2.ModelDriven;

@Controller  
public class RoleScopeAction extends BaseAction implements ModelDriven<RoleScope>{

	@Resource
	private RoleUserTypeBiz roleUserTypeService;
	
	private RoleScope roleScope = new RoleScope();
	
	
	/**
	 * @Description 跳转到角色范围管理设置页面
	 * @author shiss
	 * @return
	 * @throws Exception 
	 */
	public String toUserScope() throws Exception{
		return "userScope";
	}
	
	
	/**
	 * @Description 用户范围明细
	 * @author shiss
	 * @return
	 * @throws Exception
	 */
	public String getBean()throws Exception{ 
		String result = null;
		roleScope = roleUserTypeService.getRoleScope(roleScope.getRoleId());
		if((roleScope.getUserList()==null||roleScope.getUserList().isEmpty())&&(roleScope.getOrgList()==null||roleScope.getOrgList().isEmpty())&&StringUtils.isBlank(roleScope.getUserType())){
			roleScope.setAddOrUpdate("add");
		}else{
			roleScope.setAddOrUpdate("update");
		}
		if(roleScope !=null){
			result = ByyJsonUtil.serialize(roleScope,new String[]{});
		}else{
			result = this.getFailJson("网络异常!"); 
		}
		this.print(result);
		return null;
	}
	
	
	/**
	 * @author shiss
	 * @creteTime 2017-3-20 下午5:40:35
	 * @description 用户范围保存
	 * @param roleUserType
	 * @return
	 * @throws Exception
	 */
	@LogThisOperate(module="设置角色用户范围",operateType=OperateType.新增)
	public String save() throws Exception{
		String result = this.getFailJson("操作失败");
		try{
			if(roleScope!=null){
				if(roleScope.getAddOrUpdate()!=null){
					roleUserTypeService.save(roleScope);
					result = this.getSuccessJson("操作成功");
				}else{
					result = this.getFailJson("操作失败");
				}
			}
		}catch (Exception e) {
			result = this.getFailJson("操作失败");
		}
		this.print(result);
		return null;
	}
	
	@Override
	public RoleScope getModel() {
		return roleScope;
	}
	
	
	/**
	 * @author happyss
	 * @creteTime 2017-3-30 上午11:29:26
	 * @description 跳转机构用户选择弹框
	 * @return
	 * @throws Exception
	 */
	public String toOrgSchool() throws Exception{
		return "toOrgSchool";
	}
	
	/**
	 * @author happyss
	 * @creteTime 2017-3-30 下午6:34:37
	 * @description 跳转选择用户弹框
	 * @return
	 */
	public String toUserSelect(){
		return "useSelect";
	}
	
	/**
	 * @author happyss
	 * @creteTime 2017-4-1 下午2:10:46
	 * @description 获取用户角色列表
	 * @return
	 * @throws Exception
	 */
	public String getUserRoles() throws Exception{
		String userId = request.getParameter("userId");
		String result = "";
		try {
			List<Role> list = roleUserTypeService.getUserRoles(userId);
			result = ByyJsonUtil.serialize(list,new String[]{});
		} catch (SQLException e) {
			result = this.getFailJson("网络异常!"); 
		}
		this.print(result);
		return null;
	}
}

package com.boyuyun.base.user.action;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;

import com.boyuyun.base.common.biz.UserBehaviorBiz;
import com.boyuyun.base.common.entity.UserBehavior;
import com.boyuyun.base.user.biz.UserBiz;
import com.boyuyun.base.user.entity.User;
import com.boyuyun.base.util.ConstantUtil;
import com.boyuyun.base.util.base.BaseAction;
import com.boyuyun.base.util.consts.UserType;
import com.boyuyun.common.annotation.LogThisOperate;
import com.boyuyun.common.autolog.OperateType;

/**
 * 登录
 * @author LHui
 * 2017-2-10 上午9:40:43
 */
@Controller  
public class LoginAction extends BaseAction{
	@Resource
	private UserBiz userService;
	@Resource
	private UserBehaviorBiz behaviorService;
	/**
	 * 跳转到学校登录界面
	 * @author LHui
	 * @since 2017-2-10 上午9:59:18
	 * @return
	 */
	public String toLogin()throws Exception{
		//1.判断当前是否有登录,从session获取当前登录用户
		User user = (User) request.getAttribute("currUser");
		if(user != null){
			//已登录，跳转到登录后界面
			response.sendRedirect("/admin_index.do");
			return null;
		}
		return "login";
	}
	@LogThisOperate(module="用户管理",operateType=OperateType.登录)
	public String doLogin()throws Exception{
		try {
			JSONObject edu = new JSONObject();
			String result = this.getFailJson("用户名或密码错误！");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			if(null != username && null != password){
				//根据用户名拿到数据
				//判断是否是教育局dzadmin 666666
				JSONObject userInfo = null;
				User user = this.userService.selectByUserName(username,password,UserType.系统管理员);
				
				if(null == user){
					//登录失败
					this.setNewAddId(username);
				}else{
					UserBehavior behavior = new UserBehavior ();
					behavior.setUserid(user.getId());
					List<UserBehavior> behaviors = behaviorService.getList(behavior);
					Map<String ,String> map = new HashMap<String ,String>();
					for(UserBehavior b:behaviors){
						map.put(b.getBehaviorType(), b.getBehavior());
					}
					request.getSession().setAttribute(ConstantUtil.SESSSION_USER_ATTR_NAME, user);
					request.getSession().setAttribute(ConstantUtil.SESSSION_USER_BEHAVIOR_NAME, map);
					this.setNewAddId(user.getId());
					result = this.getSuccessJson("登录成功");
				}
			}
			this.print(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public String logout()throws Exception{
		request.getSession().invalidate();
		response.sendRedirect("login_toLogin.do");
		return null;
	}
}

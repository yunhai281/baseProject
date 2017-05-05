package com.boyuyun.base.util;

import javax.servlet.http.HttpServletRequest;

import com.boyuyun.base.user.entity.User;


/**
 * ClassName:UserUtil <br/>  
 * 用户工具类，可获取用户所在学校等<br/>  
 * Date: 2015-10-19 下午1:52:28<br/>  
 * @version 
 * @see 
 */
public class UserUtil {
	public static User getUser(HttpServletRequest request){
		return (User) request.getSession().getAttribute(ConstantUtil.SESSSION_USER_ATTR_NAME);
	}
}

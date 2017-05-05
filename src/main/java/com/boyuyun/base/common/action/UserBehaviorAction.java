package com.boyuyun.base.common.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.boyuyun.base.common.biz.UserBehaviorBiz;
import com.boyuyun.base.common.entity.UserBehavior;
import com.boyuyun.base.common.util.UserBehaviorConstant;
import com.boyuyun.base.user.entity.User;
import com.boyuyun.base.util.ConstantUtil;
import com.boyuyun.base.util.UserUtil;
import com.boyuyun.base.util.base.BaseAction;
import com.boyuyun.common.util.ByyStringUtil;
import com.opensymphony.xwork2.ModelDriven;
@Controller 
public class UserBehaviorAction extends BaseAction implements ModelDriven<UserBehavior>{
	private UserBehavior behavior = new UserBehavior();
	@Resource
	private UserBehaviorBiz behaviorService;

	@Override
	public UserBehavior getModel() {
		return (UserBehavior)initPage(behavior);
	}
	
	/**
	 * 添加/修改个人操作习惯记录
	 * @return
	 * @throws Exception
	 */
	public String addOrUpdate() throws Exception{
		String result = this.getFailJson("操作失败");
		try{
			//根据userId和类型判断是否已经添加
			User user = UserUtil.getUser(request);
			if(user==null){
				return null;
			}
			String type = UserBehaviorConstant.behaviorTypeMap.get(behavior.getBehaviorType());
			behavior.setUserid(user.getId());
			behavior.setBehaviorType(type);
			UserBehavior obj = behaviorService.getUserBehavior(behavior);
			if(obj==null){
				if(type!=null){
					String id =	ByyStringUtil.getRandomUUID();
					behavior.setId(id);
					behaviorService.add(behavior);
					result = this.getSuccessJson("添加成功");
				}
			}else{
				behaviorService.update(behavior);
				result = this.getSuccessJson("更新成功");
			}
			//更新session
			UserBehavior behavior = new UserBehavior ();
			behavior.setUserid(user.getId());
			List<UserBehavior> behaviors = behaviorService.getList(behavior);
			Map<String ,String> map = new HashMap<String ,String>();
			for(UserBehavior b:behaviors){
				map.put(b.getBehaviorType(), b.getBehavior());
			}
			request.getSession().setAttribute(ConstantUtil.SESSSION_USER_BEHAVIOR_NAME, map);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		this.print(result);
		return null;
	}
	
}

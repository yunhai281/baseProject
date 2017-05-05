package com.boyuyun.base.sys.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.boyuyun.base.sys.biz.MenuBiz;
import com.boyuyun.base.sys.entity.Menu;
import com.boyuyun.base.util.base.BaseAction;

/**
 * 用于后台管理的Controller
 * @author LHui
 * 2017-3-1 上午9:18:50
 */
@Controller  
public class AdminAction extends BaseAction {
	@Resource
	private MenuBiz menuService;
	
	public String index()throws Exception{
		//1.获取后台的所有管理菜单（只搜索parent_id为null的菜单）
		List<Menu> list = null;
		list = menuService.getTopMenuListWithChild();
		if(list == null || list.size() == 0){
			list = new ArrayList<Menu>();
		}
		request.setAttribute("menu", list);
		return "index";
	}
}

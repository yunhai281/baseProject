package com.boyuyun.base.sys.action;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;

import com.boyuyun.base.sys.biz.MenuBiz;
import com.boyuyun.base.sys.entity.Menu;
import com.boyuyun.base.util.ConstantUtil;
import com.boyuyun.base.util.base.BaseAction;
import com.boyuyun.common.annotation.LogThisOperate;
import com.boyuyun.common.autolog.OperateType;
import com.boyuyun.common.json.ByyJsonUtil;
import com.boyuyun.common.util.ByyStringUtil;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @author LHui
 * 2017-2-20 下午1:41:10
 */
@Controller  
public class MenuAction extends BaseAction implements ModelDriven<Menu>{
	private Menu menu = new Menu();
	@Resource
	private MenuBiz menuService;
	
	/**
	 * 跳转到应用管理列表界面
	 * @author LHui
	 * @since 2017-2-20 上午10:36:53
	 * @return
	 */
	public String toList(){
		return "list";
	}
	
	public String toAdd(){
		return "view";
	}
	
	/**
	 * 得到树形列表
	 * @param request
	 * @param response
	 * @return
	 */
	public String getList()throws Exception{
		String result = "";
		if(menu.getId()!=null && menu.getId().equals("0")){
			JSONArray jaMenu = new JSONArray();
			JSONObject joMenu = new JSONObject();
			joMenu.element("id","");
			joMenu.element("available",true);
			joMenu.element("isParent", true);
			joMenu.element("levelType","0");
			joMenu.element("name",ConstantUtil.SYSTEM_NAME);
			result = ByyJsonUtil.serialize(joMenu);
		}else if(menu.getId()==null || "".equals(menu.getId()) ){
			List<Menu> list = menuService.getTopMenuListWithChild();
			if(list!=null){
				JSONArray ja = new JSONArray();
				for(Iterator iterator = list.iterator(); iterator.hasNext();){
					JSONObject jo = new JSONObject();
					Menu m = (Menu) iterator.next();
					jo.element("id",m.getId());
					jo.element("available",m.isAvailable());
					jo.element("levelType",m.getLevelType());
					jo.element("name",m.getName());
					if(m.getChild() != null && m.getChild().size()>0){
						jo.element("isParent", true);
					} 
					ja.add(jo);
				}
				result = ByyJsonUtil.serialize(ja);
			}else {
				result = this.getSuccessJson("没有数据!");
			} 
		}else {
			JSONArray ja = new JSONArray();
			List<Menu> list = menuService.getMenuListByParentId(menu.getId());
			if(list!=null){
				for(Iterator iterator = list.iterator(); iterator.hasNext();){
					JSONObject jo = new JSONObject();
					Menu m = (Menu) iterator.next();
					jo.element("id",m.getId());
					jo.element("available",m.isAvailable());
					jo.element("levelType",m.getLevelType());
					jo.element("name",m.getName());
					jo.element("isParent", false);
					if( m.getParent()!=null){
						jo.element("parentId", m.getParent().getId());
					}
					ja.add(jo);
				}
				result = ByyJsonUtil.serialize(ja);
			}else {
				result = this.getSuccessJson("没有数据!");
			} 
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
		Menu menu = menuService.get(this.menu.getId());
		if(menu !=null){
			result = ByyJsonUtil.serialize(menu,new String[]{"child","parent"});
		}else{
			result = this.getFailJson("网络异常!"); 
		}
		this.print(result);
		return null;
	}
	
	@LogThisOperate(module="菜单管理",operateType=OperateType.新增或修改)
	public String save() throws Exception{
		String result = this.getFailJson("操作失败");
		if(menu.getId()!=null && menu.getId().trim().length() > 0){
			//更新操作
			String parentId = request.getParameter("parentId");
			if(parentId!=null && !"".equals(parentId)){
				Menu menuParent= new Menu();
				menuParent.setId(parentId);
				menu.setParent(menuParent);
				menu.setLevelType(2);
			}else{
				menu.setParent(null);
				menu.setParentId(null);				
				menu.setLevelType(1);
			}
			menuService.update(menu);
			result = this.getSuccessJson("操作成功");
		}else{
			//插入操作/新增
			String id=ByyStringUtil.getRandomUUID();
			menu.setId(id);
			String parentId = request.getParameter("parentId");
			if(parentId!=null && !"".equals(parentId)){
				Menu menuParent= new Menu();
				menuParent.setId(parentId);
				menu.setParent(menuParent);
				menu.setLevelType(2);
			}else {
				menu.setParent(null);
				menu.setParentId(null);
				menu.setLevelType(1);
			}
			menuService.add(menu);
			result = this.getSuccessJson("操作成功");
		}
		this.print(result);
		return null;
	}
	
	@LogThisOperate(module="菜单管理",operateType=OperateType.删除)	
	public String delete()throws Exception{
		String result = this.getFailJson("删除失败!");
		if(menu.getId()!=null && menu.getId().length()!=0){
			menu = menuService.get(menu.getId());
			if(menu.getChild()!=null && menu.getChild().size()>0){
				result = this.getFailJson("存在下级目录无法删除!");
			}else {
				menuService.delete(menu);
				result = this.getSuccessJson("删除成功!");
			}
		}
		this.print(result);
		return null;
	}

	public Menu getModel() {
		return menu;
	}
}

package com.boyuyun.base.sys.biz.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.boyuyun.base.sys.biz.MenuBiz;
import com.boyuyun.base.sys.dao.MenuDao;
import com.boyuyun.base.sys.entity.Menu;
import com.google.common.base.Strings;
@Service
public class MenuBizImpl  implements MenuBiz {

    @Resource
    private MenuDao menuDao ;

	/**
	 * 查询所有顶级菜单，并且自动设置好子菜单
	 * @return
	 */
	public List<Menu> getTopMenuListWithChild()throws Exception {
		List<Menu> allList = menuDao.getAllAvailableMenu();
		List<Menu> result = new ArrayList<Menu>();
		if(allList!=null){
			Map<String,Menu> dic = new HashMap<String,Menu>();
			List<Menu> childs = new ArrayList<Menu>();
			//都放到一个Map里
			for (Iterator iterator = allList.iterator(); iterator.hasNext();) {
				Menu menu = (Menu) iterator.next();
				dic.put(menu.getId(), menu);
				if(Strings.isNullOrEmpty(menu.getParentId())){
					result.add(menu);
				}else{
					childs.add(menu);
				}
			}
			for (Iterator iterator = childs.iterator(); iterator.hasNext();) {
				Menu menu = (Menu) iterator.next();
				if(dic.get(menu.getParentId())!=null){
					dic.get(menu.getParentId()).getChild().add(menu);
				}
			}
			//排序
			Collections.sort(result);
			for (Iterator iterator = result.iterator(); iterator.hasNext();) {
				Menu menu = (Menu) iterator.next();
				Collections.sort(menu.getChild());
			}
			return result;
		}
		return null;
	}

	@Override
	public List<Menu> getMenuListByParentId(String parentId)throws Exception {
		return menuDao.getMenuListByParentId(parentId);
	}

	@Transactional
	public boolean add(Menu menu) throws Exception{
		return menuDao.insert(menu);
	}

	@Transactional
	public boolean update(Menu menu) throws Exception{
		return menuDao.update(menu);
	}

	@Transactional
	public boolean delete(Menu menu) throws Exception{
		return menuDao.delete(menu);
	}

	@Override
	public Menu get(String menuid) throws Exception{
		return menuDao.get(menuid);
	}
	
}

package com.boyuyun.base.sso.biz.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boyuyun.base.sso.biz.RoleBiz;
import com.boyuyun.base.sso.dao.RoleDao;
import com.boyuyun.base.sso.entity.Role;
import com.boyuyun.base.user.entity.User;

@Service
public class RoleBizImpl implements RoleBiz {

	@Resource 
	private RoleDao roleDao;
	
	@Override
	public List<Role> getAllRoleList()throws SQLException {
		return roleDao.getAllRoleList();
	}

	@Override
	public boolean add(Role role) throws SQLException{
		return roleDao.insert(role);
	}

	@Override
	public Role get(String id) throws SQLException{
		return roleDao.get(id);
	}

	@Override
	@Transactional
	public boolean update(Role role) throws SQLException{
		return roleDao.update(role);
	}

	@Override
	@Transactional
	public boolean delete(String id) throws SQLException{
		return roleDao.delete(id);
	}

	@Override
	@Transactional
	public boolean saveApp(String id,String roleId, String applicationId) throws SQLException{
		roleDao.deleteApp(roleId);
		return roleDao.saveApp(id, roleId, applicationId);
	}
 
	@Override
	public List getAPP(String roleId) throws SQLException {
		return roleDao.getAPP(roleId);
	}
	
	/**
	 * 根据角色查询人员
	 * @param roleId
	 * @return
	 */
	public List<User> getUserByRolePaged(Role role) throws SQLException {
		return roleDao.getUserByRolePaged(role.getId(),role.getBegin(),role.getEnd());
	}
	/**
	 * 根据角色查询人员
	 * @param roleId
	 * @return
	 */
	public int getUserByRoleCount(String roleId) throws SQLException {
		return roleDao.getUserByRoleCount(roleId);
	}

	@Override
	public List searchByName(String name) throws SQLException {
		return roleDao.selectByName(name);
		/*List<TreeDTO> list  = new ArrayList<TreeDTO>();
		//生成一棵树
		if(!Strings.isNullOrEmpty(name)){
			List<Government> govList = roleDao.selectGovByName(name);
			if(govList != null && govList.size() > 0){
				//进行循环
				for(Iterator iterator = govList.iterator();iterator.hasNext();){
					Government government = (Government) iterator.next();
					List<Government> sons =roleDao. selectByParentId(government.getId());
					List<TreeDTO> children = new ArrayList<TreeDTO>();
					//循环每一个元素查看是否还有子节点，如果没有则为叶子，有则为父节点
					for(Iterator iterator2 = sons.iterator();iterator2.hasNext();){
						Government temp =  (Government)iterator2.next();
						if(temp.getChildNum() > 0){
							temp.setIsParent("true");
						}else{
							temp.setIsParent("false");
						}
						children.add(temp.getTreeDTO());
					}
					TreeDTO dto = government.getTreeDTO();
					dto.setChildren(children);
					list.add(dto);
				}
			}
		}
		return list;*/
	}
}

package com.boyuyun.base.sso.biz.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.boyuyun.base.sso.biz.RoleUserTypeBiz;
import com.boyuyun.base.sso.dao.RoleDao;
import com.boyuyun.base.sso.dao.RoleUserDao;
import com.boyuyun.base.sso.dao.RoleUserOrgDao;
import com.boyuyun.base.sso.dao.RoleUserTypeDao;
import com.boyuyun.base.sso.entity.Role;
import com.boyuyun.base.sso.entity.RoleScope;
import com.boyuyun.base.sso.entity.RoleUser;
import com.boyuyun.base.sso.entity.RoleUserOrg;
import com.boyuyun.base.sso.entity.RoleUserType;
@Service
public class RoleUserTypeBizImpl implements RoleUserTypeBiz{

	@Resource
	private RoleUserTypeDao roleUserTypeDao;
	@Resource
	private RoleUserDao roleUserDao;
	@Resource
	private RoleUserOrgDao roleUserOrgDao;
	@Resource
	private RoleDao roleDao;
	

	@Override
	public boolean save(RoleScope po) throws SQLException {
		boolean result = false;
		if(po.getAddOrUpdate()!=null){
			if(po.getAddOrUpdate().equals("add")||po.getAddOrUpdate().equals("update")){
				if(po.getAddOrUpdate().equals("update")){
					roleUserOrgDao.deleteByRoleId(po.getRoleId());
					roleUserDao.deleteByRoleId(po.getRoleId());
					roleUserTypeDao.deleteByRoleId(po.getRoleId());
				}
				//1.更新角色表中的userType及goverType
				Role role = new Role();
				role = roleDao.get(po.getRoleId());
				role.setUserType(StringUtils.isBlank(po.getUserType())?null:po.getUserType().equals("all")?"all":"self");
				role.setGoverType(StringUtils.isBlank(po.getGoverType())?null:po.getGoverType());
				role.setUserScopeType(po.getUserScopeType());
				roleDao.update(role);
				
				//2.处理RoleUserOrg
				List<RoleUserOrg> orgList = po.getOrgList();
				if(orgList!=null&&!orgList.isEmpty()){
					roleUserOrgDao.addBatch(orgList);
				}
				
				//3.处理RoleUser
				List<RoleUser> userList = po.getUserList();
				if(userList!=null&&!userList.isEmpty()){
					List<RoleUser> roleUserList = new ArrayList<RoleUser>();
					for(RoleUser roleUser:userList){
						if(roleUser.getUserId().indexOf(",")>-1){
							String[] userIds = roleUser.getUserId().split(",");
							for(String userId:userIds){
								RoleUser roleUserPo = new RoleUser();
								roleUserPo.setUserId(userId);
								roleUserPo.setRoleId(roleUser.getRoleId());
								roleUserPo.setUserType(roleUser.getUserType());
								roleUserList.add(roleUserPo);
							}
						}else{
							roleUserList.add(roleUser);
						}
					}
					roleUserDao.addBatch(roleUserList);
				}
				//4.处理RoleUserType
				int userTypeSize = 0;
				boolean emptyUserType = false;
				boolean emptyGoverType = false;
				if(StringUtils.isNotBlank(po.getUserType())){
					userTypeSize = po.getUserType().split(",").length;
				}else{
					emptyUserType = true;
				}
				if(StringUtils.isNotBlank(po.getGoverType())){
					userTypeSize = po.getGoverType().split(",").length;
				}else{
					emptyGoverType = true;
				}
				if(userTypeSize>0){
					List<RoleUserType> userTypeList = new ArrayList<RoleUserType>();
					if(emptyUserType){
						String[] goverTypes = po.getGoverType().split(",");
						for(String goverType:goverTypes){
							RoleUserType roleUserType = new RoleUserType();
							roleUserType.setUserType(null);
							roleUserType.setGoverType(goverType);
							roleUserType.setRoleId(po.getRoleId());
							userTypeList.add(roleUserType);
						}
					}else if(emptyGoverType){
						String[] userTypes = po.getUserType().split(",");
						for(String userType:userTypes){
							RoleUserType roleUserType = new RoleUserType();
							roleUserType.setUserType(userType);
							roleUserType.setGoverType(null);
							roleUserType.setRoleId(po.getRoleId());
							userTypeList.add(roleUserType);
						}
					}else{
						String[] userTypes = po.getUserType().split(",");
						String[] goverTypes = po.getGoverType().split(",");
						for(String userType:userTypes){
							for(String goverType:goverTypes){
								RoleUserType roleUserType = new RoleUserType();
								roleUserType.setUserType(userType);
								roleUserType.setGoverType(goverType);
								roleUserType.setRoleId(po.getRoleId());
								userTypeList.add(roleUserType);
							}
						}
					}
					if(userTypeList!=null&&!userTypeList.isEmpty()){
						roleUserTypeDao.addBatch(userTypeList);
					}
				}
				result = true;
			}
		}
		
		return result;
	}

	@Override
	public RoleScope getRoleScope(String roleId) throws SQLException {
		RoleScope po = new RoleScope();
		po.setRoleId(roleId);
		
		//1.处理RoleUserOrg
		List<RoleUserOrg> orgList = roleUserOrgDao.getList(roleId);
		//2.处理RoleUser
		List<RoleUser> userList = roleUserDao.getList(roleId);
		//3.获取用户类型
		List<RoleUserType> userTypeList = roleUserTypeDao.getList(roleId);
		Set<String> userTypeSet = new HashSet<String>();
		Set<String> goverTypeSet = new HashSet<String>();
		for(int i=0;i<userTypeList.size();i++){
			RoleUserType userType = userTypeList.get(i);
			userTypeSet.add(userType.getUserType());
			goverTypeSet.add(userType.getGoverType());
		}
		//4.获取用户userScopeType
		Role role = roleDao.get(roleId);
		po.setUserScopeType(role.getUserScopeType());
		po.setOrgList(orgList==null||orgList.isEmpty()?null:orgList);
		po.setUserList(userList==null||userList.isEmpty()?null:userList);
		po.setUserType(userTypeSet==null||userTypeSet.isEmpty()?"":StringUtils.join(userTypeSet,","));
		po.setGoverType(goverTypeSet==null||goverTypeSet.isEmpty()?"":StringUtils.join(goverTypeSet,","));
		
		return po;
	}


	/**
	 * @author happyss
	 * @creteTime 2017-3-27 下午3:16:54
	 * @description 根据用户id获取用户角色列表
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	@Override
	public List<Role> getUserRoles(String userId) throws SQLException {
		List<Role> roleList = roleUserDao.getRoleListByUserId(userId);
		return roleList;
	}

}

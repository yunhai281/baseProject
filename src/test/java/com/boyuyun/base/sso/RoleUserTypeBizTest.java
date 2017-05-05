package com.boyuyun.base.sso;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.boyuyun.base.sso.biz.RoleUserTypeBiz;
import com.boyuyun.base.sso.entity.Role;
import com.boyuyun.base.sso.entity.RoleScope;
import com.boyuyun.common.junit.SpringJunitTest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RoleUserTypeBizTest extends SpringJunitTest {

	private static Logger logger = Logger.getLogger(RoleUserTypeBizTest.class);
	
	@Resource
	private RoleUserTypeBiz roleUserTypeService;
	
	/**
	 * 这个方法关联到4个表而且没有删除方法,不建议使用此测试
	 * sso_role_application,
	 * sso_role_user,
	 * sso_role_user_government_school,
	 * sso_role_user_type
	 */
//	@Test
//	public void testSave() {
//		RoleScope rs = new RoleScope();
//		rs.setAddOrUpdate("add");
//		rs.setRoleId("bbeddc6f93fc498cbaf190ed80c4df87");
//		try {
//			roleUserTypeService.save(rs);
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(e);
//		}
//	}

	@Test
	public void testA_GetRoleScope() {
		RoleScope po = null;
		try {
			po = roleUserTypeService.getRoleScope("e327d216d8d6459298b8867eb26e0de4");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(po != null);
	}

	@Test
	public void testB_GetUserRoles() {
		List<Role> list = null;
		try {
			list = roleUserTypeService.getUserRoles("540fed39-633f-41b5-a181-527d71e94d73");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(list != null);
	}

}

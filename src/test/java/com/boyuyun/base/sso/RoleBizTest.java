package com.boyuyun.base.sso;

import static org.junit.Assert.*;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.boyuyun.base.sso.biz.RoleBiz;
import com.boyuyun.base.sso.entity.Role;
import com.boyuyun.base.user.entity.User;
import com.boyuyun.common.junit.SpringJunitTest;
import com.boyuyun.common.util.ByyStringUtil;

/**
 * 本次方法的特殊性由于查看,修改删除都需要新增之后的ID值,所以智能一个方法一个方法的测试
 * @author Administrator
 *
 */

public class RoleBizTest extends SpringJunitTest {

	private static Logger logger = Logger.getLogger(RoleBizTest.class);

	@Resource
	private RoleBiz roleBiz;
	

	
	@Test
	public void testGetAllRoleList() {
		List<Role> list= null;
		try {
			list = roleBiz.getAllRoleList();
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(list != null);
	}

	@Test
	public void testAdd() {
		Role role = new Role();
		 
		role.setId("123"); 
		role.setName("123");
		role.setUserScopeType("person");
		Exception ex = null;
		try {
			roleBiz.add(role);
		} catch (Exception e) {
			ex = e;
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(ex==null);	
	}
	
	/**
	 * 这些Id值*(f9d4ad63bd2f4dd5a9a8ee1e604c673e)在新增测试后从数据库中取值
	 */
	@Test
	public void testB_Get() {
		Role role = null;
		try {
			role = roleBiz.get("123");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(role!=null);
	}

	@Test
	public void testBUpdate() {
		Role role = new Role();
		try {
			role = roleBiz.get("123");
			role.setName("111");
			roleBiz.update(role);
			role = roleBiz.get("123");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(role.getName().equals("111"));
	}

	/**
	 * 注意SaveApp这个sso_role_application表后没有单独的删除方法,需手动到数据库中删除
	 */
	@Test
	public void testH_SaveApp() {
		String id = UUID.randomUUID().toString().replace("-", "");
		Exception ex = null;
		try {
			roleBiz.saveApp(id, "12", "23");
		} catch (Exception e) {
			ex = e;
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(ex==null);	
	}

	@Test
	public void testI_GetAPP() {
		List list = null;
		try {
			list = roleBiz.getAPP("12");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(list!=null);
	}

	@Test
	public void testC_GetUserByRolePaged() {
		List<User> list = null;
		try {
			Role role = new Role();
			role.setId("123");
			list = roleBiz.getUserByRolePaged(role);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(list!=null);
	}

	@Test
	public void testDGetUserByRoleCount() {
		int list = 0;
		try {
			list = roleBiz.getUserByRoleCount("123");
			System.out.print(list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(list > 0);
	}

	@Test
	public void testG_SearchByName() {
		List<Role> list = null ;
		try {
			list = roleBiz.searchByName("123");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(list!=null);		
	}

	@Test
	public void testZ_Delete() {
		Role role = new Role();
		try {
			roleBiz.delete("123");
			role=roleBiz.get("123");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertFalse(role != null);
	}
}

package com.boyuyun.base.user;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.boyuyun.base.user.biz.UserBiz;
import com.boyuyun.base.user.entity.User;
import com.boyuyun.base.util.consts.UserType;
import com.boyuyun.common.junit.SpringJunitTest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserBizTest extends SpringJunitTest {
	private static Logger logger = Logger.getLogger(UserBizTest.class);
	@Resource
	private UserBiz userService;

	@Test
	public void testC_SelectByUserName() {
		User user = null;
		try {
			user = userService.selectByUserName("abcd", "abcd", UserType.教师);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(user.getRealName().equals("abcd"));
	}

	@Test
	public void testD_ResetPassword() {
		User user = null;
		try {
			user = userService.get("abcd");
			List<User> list = new ArrayList<User>();
			list.add(user);
			userService.resetPassword(list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		try {
			Assert.assertTrue(!userService.get("abcd").getPwd().equals("abcd"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testB_Get() {
		User user = null;
		try {
			user = userService.get("abcd");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(user.getRealName().equals("abcd"));
	}

	@Test
	public void testE_Update() {
		User user = null;
		try {
			user = userService.get("abcd");
			user.setRealName("aaaa");
			userService.update(user);
			user = userService.get("abcd");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(user.getRealName().equals("aaaa"));
	}

	@Test
	public void testAdd() {
		User user = new User();
		user.setId("abcd");
		user.setPwd("abcd");
		Integer ordinal = UserType.教师.ordinal();
		user.setUserType(ordinal.toString());
		user.setUserName("abcd");
		user.setRealName("abcd");
		Exception ex = null;
		try {
			userService.add(user);
		} catch (Exception e) {
			ex = e;
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(ex == null);

	}

	@Test
	public void testK_Delete() {
		User user = null;
		try {
			user= userService.get("abcd");
			userService.delete(user);
			user= userService.get("abcd");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertFalse(user!=null);
	}

	@Test
	public void testF_GetMobileCount() {
		String mobile = "13800007923";
		int mobileCount = 0;
		try {
			mobileCount = userService.getMobileCount(mobile);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(mobileCount != 0);
	}

	@Test
	public void testGetListPagedAdmin() {
		User user = new User();
		user.setUserName("abcd");
		user.setRealName("abcd");
		List<User> listPagedAdmin = null;
		try {
			listPagedAdmin = userService.getListPagedAdmin(user);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(listPagedAdmin != null);
	}

	@Test
	public void testH_GetListPagedCountAdmin() {
		User user = new User();
		int listPagedCountAdmin = 0;
		try {
			listPagedCountAdmin = userService.getListPagedCountAdmin(user);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(listPagedCountAdmin != 0);
	}

	@Test
	public void testI_UpdatePassword() {

		String pwd = "abcd";
		String id = "abcd";
		User user = null;
		try {
			userService.updatePassword(id, pwd);
			user = userService.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(user.getPwd().equals(pwd));

	}

	@Test
	public void testJ_ValidateUserName() {
		String userName="abcd";
		String id="abcd";
		boolean validateUserName=false;
		try {
			validateUserName = userService.validateUserName(userName, id);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(validateUserName);

	}

}

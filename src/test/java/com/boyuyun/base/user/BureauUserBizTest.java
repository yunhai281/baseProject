package com.boyuyun.base.user;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.boyuyun.base.user.biz.BureauUserBiz;
import com.boyuyun.base.user.entity.BureauUser;
import com.boyuyun.common.junit.SpringJunitTest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BureauUserBizTest extends SpringJunitTest {

	private static Logger logger = Logger.getLogger(BureauUserBizTest.class);
	@Resource
	private BureauUserBiz bureauService;

	/**
	 * 这个方法是将更新和添加放在一起的，把id注释掉，测试添加，或者是设置一个已经存在的ID，测试更新
	 */
	@Test
	public void testASaveBureau() {
		BureauUser bureauUser = new BureauUser();
		//bureauUser.setId("3cf00a42765e4fc3b20b3941af0b180f");
		bureauUser.setRealName("abcd");
		bureauUser.setUserName("abcd");
		bureauUser.setCertificateNo("abcd");
		bureauUser.setPwd("abcd");
		bureauUser.setSchoolName("abcd");
		bureauUser.setGovernmentId("000013602");

		String[] postList = new String[] { "10106", "10108" };
		Exception exception = null;
		try {
			bureauService.saveBureau(bureauUser, postList);
		} catch (Exception e) {
			exception = e;
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(exception == null);
	}

	/**
	 * bureauService.get(bureauUser); 会查出教育局的职务，所以删除之后在调用一定会有空指针异常。
	 */
	@Test
	public void testZDeleteBueau() {
		List<BureauUser> list = new ArrayList<>();
		BureauUser bureauUser = new BureauUser();
		bureauUser.setId("123");
		list.add(bureauUser);
		Exception exception = null;
		try {
			bureauService.deleteBueau(list);
			bureauUser.setId("123");
			bureauUser = bureauService.get(bureauUser);
		} catch (Exception e) {
			exception = e;
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(exception != null);
	}

	@Test
	public void testGetListPaged() {
		BureauUser user = new BureauUser();
		List<BureauUser> listPaged = null;
		try {
			listPaged = bureauService.getListPaged(user);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(listPaged.size() > 0);

	}

	@Test
	public void testGetListPagedCount() {
		BureauUser user = new BureauUser();
		int listPagedCount = 0;
		try {
			user.setId("3cf00a42765e4fc3b20b3941af0b180f");
			listPagedCount = bureauService.getListPagedCount(user);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(listPagedCount > 0);
	}

	@Test
	public void testBGet() {
		BureauUser user = new BureauUser();
		user.setId("3cf00a42765e4fc3b20b3941af0b180f");
		try {
			user = bureauService.get(user);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(user.getRealName() != null);
	}

}

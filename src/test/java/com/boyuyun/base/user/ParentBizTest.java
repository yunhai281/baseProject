package com.boyuyun.base.user;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.boyuyun.base.user.biz.ParentBiz;
import com.boyuyun.base.user.entity.Parent;
import com.boyuyun.common.junit.SpringJunitTest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ParentBizTest extends SpringJunitTest {

	private static Logger logger = Logger.getLogger(ParentBizTest.class);
	@Resource
	private ParentBiz parentService;

	@Test
	public void testAdd() {
		Parent parent = new Parent();
		parent.setId("abcd");
		parent.setChildName("abcd");
		parent.setRealName("abcd");
		parent.setUserName("abcd");
		parent.setCertificateNo("abcd");
		parent.setPwd("abcd");
		parent.setSchoolId("042003ea120946698e61721b839baa90");
		Exception exception = null;
		try {
			parentService.add(parent);
		} catch (Exception e) {
			exception = e;
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(exception == null);
	}

	@Test
	public void testYUpdate() {
		Parent parent = new Parent();
		parent.setId("abcd");
		try {
			parent = parentService.get(parent);
			parent.setRealName("aaaa");
			parentService.update(parent);
			parent = parentService.get(parent);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(parent.getRealName().equals("aaaa"));
	}

	@Test
	public void testZDelete() {
		List<Parent> parents = new ArrayList<>();
		Parent parent = new Parent();
		parent.setId("abcd");
		parents.add(parent);
		try {
			parentService.delete(parents);
			parent.setId("abcd");
			parent = parentService.get(parent);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertFalse(parent != null);
	}

	@Test
	public void testGetListNonePaged() {

		Parent parent = new Parent();
		List<Parent> listNonePaged = null;
		try {
			listNonePaged = parentService.getListNonePaged(parent);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		logger.error(listNonePaged.size() > 0);
	}

	@Test
	public void testGetListPaged() {
		Parent parent = new Parent();
		List<Parent> listPaged = null;
		try {
			listPaged = parentService.getListPaged(parent);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(listPaged.size() > 0);
	}

	@Test
	public void testGetListPagedCount() {

		Parent parent = new Parent();
		int listPagedCount = 0;
		try {
			listPagedCount = parentService.getListPagedCount(parent);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(listPagedCount > 0);
	}

	@Test
	public void testGet() {
		Parent parent = new Parent();
		parent.setId("abcd");
		try {
			parent = parentService.get(parent);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(parent.getRealName().equals("abcd"));
	}

}

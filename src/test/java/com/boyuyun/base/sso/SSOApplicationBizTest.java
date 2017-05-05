package com.boyuyun.base.sso;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;

import org.apache.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.boyuyun.base.sso.biz.SSOApplicationBiz;
import com.boyuyun.base.sso.entity.SSOApplication;
import com.boyuyun.common.junit.SpringJunitTest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SSOApplicationBizTest extends SpringJunitTest {
	
	private static Logger logger = Logger.getLogger(SSOApplicationBizTest.class);
   
	@Resource
	private SSOApplicationBiz ssoApplicationService;
	
	@Test
	public void testAdd() {
		SSOApplication ssoApplication = new SSOApplication();
		String[] attr = {
		 "idNo",
		 "clientPassword",
		 "clientUserId",
		 "clientUserName",
		 "mobile",
		 "usertype",
		 "governmentId"
		};
		Exception ex = null;
		try{
		ssoApplication.setAllowedtoproxy(false);
		ssoApplication.setAnonymousaccess(false);
		ssoApplication.setEnabled(true);
		ssoApplication.setIgnoreattributes(false);
		ssoApplication.setSsoenabled(false);
		ssoApplication.setEvaluation_order(ssoApplicationService.getMaxSortNum());
		ssoApplication.setName("单元测试");
		ssoApplicationService.add(ssoApplication, attr);
		}catch (Exception e) {
			ex = e;
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(ex==null);
	}

	@Test
	public void testB_Get() {
		SSOApplication ssoApplication = null;
		try {
			ssoApplication = ssoApplicationService.get(9);	
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(ssoApplication != null);
	}

	@Test
	public void testC_Update() {
		SSOApplication ssoApplication = new SSOApplication();
		try {
			ssoApplication = ssoApplicationService.get(9);
			ssoApplication.setName("22单元测试");
			String[] attr = {
					 "idNo",
					 "clientPassword",
					 "clientUserId",
					 "clientUserName",
					 "mobile",
					 "usertype",
					 "governmentId"
					};
			ssoApplicationService.update(ssoApplication, attr);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(ssoApplication.getName().equals("22单元测试"));
	}

	@Test
	public void testI_Delete() {
		SSOApplication ssoApplication = new SSOApplication();
		Exception ex = null;
		ssoApplication.setId(9);
		try {
			ssoApplicationService.delete(ssoApplication);
		} catch (Exception e) {
			ex=e;
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(ex == null);
	}

	@Test
	public void testJ_DeleteAll() {
		List<SSOApplication> list = new ArrayList<SSOApplication>();
		SSOApplication ssoApplication = new SSOApplication();
		Exception ex = null;
		ssoApplication.setId(9);
		list.add(ssoApplication);
		try {
			ssoApplicationService.deleteAll(list);
		} catch (Exception e) {
			ex=e;
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(ex==null);
	}

	@Test
	public void testD_GetListPaged() {
		List<SSOApplication> list = null;
		try {
			SSOApplication ssoApplication = ssoApplicationService.get(9);
			list = ssoApplicationService.getListPaged(ssoApplication);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(list != null);
	}

	@Test
	public void testE_GetListPagedCount() {
		int list = 0;
		try {
			SSOApplication ssoApplication = ssoApplicationService.get(9);
			list = ssoApplicationService.getListPagedCount(ssoApplication);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(list != 0);
	}

	@Test
	public void testF_GetAllEnabledAppList() {
		List<SSOApplication> list = null;
		try {
			list = ssoApplicationService.getAllEnabledAppList();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(list != null);
	}

	@Test
	public void testGetMaxSortNum() {
		int list = 0;
		try {
			list = ssoApplicationService.getMaxSortNum();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(list != 0);
	}

	@Test
	public void testH_GetMaxOrder() {
		int list = 0;
		try {
			list = ssoApplicationService.getMaxOrder();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(list != 0);
	}
}

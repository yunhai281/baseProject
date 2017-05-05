package com.boyuyun.base.sso;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.boyuyun.base.sso.biz.SSOLogBiz;
import com.boyuyun.base.sso.entity.SSOLog;
import com.boyuyun.common.junit.SpringJunitTest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SSOLogBizTest extends SpringJunitTest {

	private static Logger logger = Logger.getLogger(SSOLogBizTest.class);

	@Resource
	private SSOLogBiz ssoLogBiz;
	
	@Test
	public void testA_GetListPaged() {
		List<SSOLog> list = null;
		SSOLog log = new SSOLog();
		log.setUserName("18365658989");
		try {
			list = ssoLogBiz.getListPaged(log);
		} catch (Exception e) {
			e.printStackTrace();
			list = null;
			logger.error(e);
		}
		Assert.assertTrue(list != null);
	}

	@Test
	public void testB_GetListPagedCount() {
		int list = 0;
		SSOLog log = new SSOLog();
		log.setUserName("18365658989");
		try {
			list = ssoLogBiz.getListPagedCount(log);
		} catch (Exception e) {
			e.printStackTrace();
			list = 0;
			logger.error(e);
		}
		Assert.assertTrue(list >0);
	}

}

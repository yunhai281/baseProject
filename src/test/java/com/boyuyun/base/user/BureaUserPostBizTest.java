package com.boyuyun.base.user;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.boyuyun.base.user.biz.BureaUserPostBiz;
import com.boyuyun.base.user.entity.BureauUserPost;
import com.boyuyun.common.junit.SpringJunitTest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BureaUserPostBizTest extends SpringJunitTest {
 
	private static Logger logger = Logger.getLogger(BureaUserPostBizTest.class);
	@Resource
	private BureaUserPostBiz postService;
	
 
	@Test
	public void testGetListByUserId() {
		String bureauUserId="f508076e61ee4393aaac1f60bd3f0f92";
		List<BureauUserPost> listByUserId = null;
		try {
			 listByUserId = postService.getListByUserId(bureauUserId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(listByUserId.size()>0);
	}

}

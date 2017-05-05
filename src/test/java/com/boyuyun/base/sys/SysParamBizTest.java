package com.boyuyun.base.sys;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.boyuyun.base.course.CourseBizTest; 
import com.boyuyun.base.sys.biz.SysParamBiz;
import com.boyuyun.base.sys.entity.SysParam;
import com.boyuyun.common.junit.SpringJunitTest;
import com.boyuyun.common.util.ByyStringUtil;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SysParamBizTest extends SpringJunitTest {
	
	private static Logger logger = Logger.getLogger(CourseBizTest.class);
	@Resource
	private SysParamBiz SysParamService;
	
	//@Test
	public void testA_Save() {
		SysParam sysParam = new SysParam();
		String id=ByyStringUtil.getRandomUUID();
		sysParam.setParamKey(id);
		sysParam.setParamValue("123"); 
		Exception ex = null;
		try {
			SysParamService.save(sysParam);
		} catch (Exception e) {
			ex = e;
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(ex==null);
	}

	@Test
	public void testB_GetAll() {
		List<SysParam> list= null;
		try {
			list= SysParamService.getAll();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(list!=null);
	}

	@Test
	public void testC_GetAllAsMap() {
		Map list= null;
		try {
			list= SysParamService.getAllAsMap();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(list!=null);
	}

}

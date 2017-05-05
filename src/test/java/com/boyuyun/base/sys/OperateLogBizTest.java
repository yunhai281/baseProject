package com.boyuyun.base.sys;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.boyuyun.base.course.CourseBizTest;
import com.boyuyun.base.sys.biz.OperateLogBiz;
import com.boyuyun.base.sys.biz.SysParamBiz;
import com.boyuyun.base.sys.entity.OperateLog;
import com.boyuyun.base.sys.entity.SysParam;
import com.boyuyun.common.junit.SpringJunitTest;
import com.boyuyun.common.util.ByyStringUtil;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OperateLogBizTest extends SpringJunitTest {

	private static Logger logger = Logger.getLogger(CourseBizTest.class);
	@Resource
	private OperateLogBiz OperateLogService;
	
	@Test
	public void testAddOperateLog() {
		OperateLog operateLog = new OperateLog();
		String id=ByyStringUtil.getRandomUUID();
		operateLog.setId(id);
		operateLog.setModuleName("123");
		operateLog.setOperateTime(new Date());
		Exception ex = null;
		try {
			OperateLogService.add(operateLog);
		} catch (Exception e) {
			ex = e;
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(ex==null);
	}

	@Test
	public void testD_GetListPaged() {
		List<OperateLog> list =null;
		try {
			OperateLog operateLog= new OperateLog();
			operateLog.setModuleName("123");
			operateLog.setBegin(1);
			operateLog.setEnd(10);
			list = OperateLogService.getListPaged(operateLog);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(list!=null);
	}

	@Test
	public void testC_GetListPagedCount() {
		int num=0;
		try {
			OperateLog operateLog= new OperateLog();
			operateLog.setModuleName("123"); 
			num = OperateLogService.getListPagedCount(operateLog);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(num!=0);
	}

}

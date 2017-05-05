package com.boyuyun.base.user.biz.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.boyuyun.base.user.biz.SchoolAdminBiz;
import com.boyuyun.base.user.dao.SchoolAdminDao;
@Service
public class SchoolAdminBizImpl  implements SchoolAdminBiz {

	@Resource
	private SchoolAdminDao schoolAdminDao;
}

package com.boyuyun.base.user.biz.impl;

import javax.annotation.Resource;

import com.boyuyun.base.user.biz.StudentParentBiz;
import com.boyuyun.base.user.dao.StudentParentDao;

public class StudentParentServiceImpl  implements StudentParentBiz {
	@Resource
	private StudentParentDao studentParentDao;
}

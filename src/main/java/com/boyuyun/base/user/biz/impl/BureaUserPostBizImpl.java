package com.boyuyun.base.user.biz.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.boyuyun.base.user.biz.BureaUserPostBiz;
import com.boyuyun.base.user.dao.BureauUserPostDao;
import com.boyuyun.base.user.entity.BureauUserPost;


@Service
public class BureaUserPostBizImpl implements BureaUserPostBiz {
	@Resource
	private BureauUserPostDao BureauUserPostDao;
	
	@Override
	public List<BureauUserPost> getListByUserId(String bureauUserId) throws Exception {
		// TODO Auto-generated method stub
		return BureauUserPostDao.getListByUserId(bureauUserId);
	}

}

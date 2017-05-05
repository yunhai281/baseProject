package com.boyuyun.base.user.biz;

import java.util.List;

import com.boyuyun.base.user.entity.BureauUserPost;

public interface BureaUserPostBiz {
	List<BureauUserPost> getListByUserId(String bureauUserId)throws Exception;
}

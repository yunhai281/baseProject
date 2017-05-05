package com.boyuyun.base.sys.biz.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boyuyun.base.sys.biz.AreaBiz;
import com.boyuyun.base.sys.dao.AreaDao;
import com.boyuyun.base.sys.entity.Area;
@Service
public class AreaBizImpl implements AreaBiz {

    @Resource
    AreaDao areaDao;

	@Override
	public List<Area> getListByParent(String parentId)throws Exception {
		return this.areaDao.selectByParent(parentId);
	}

	@Override
	public Area getParent(String parentId)throws Exception {
		
		return this.areaDao.getParentByPid(parentId);
	}

	@Override
	public Area selectLinkByPrimaryKey(String id)throws Exception {
		return this.areaDao.selectLinkByPrimaryKey(id);
	}

	@Transactional
	public boolean add(Area area)throws Exception {
		return this.areaDao.insert(area);
	}

	@Transactional
	public boolean update(Area area)throws Exception {
		return this.areaDao.update(area);
	}

	@Transactional
	public boolean delete(Area area)throws Exception {
		return this.areaDao.delete(area);
	}

	@Override
	public List<Area> getListNonePaged(Area area)throws Exception {
		return this.areaDao.getListNonePaged(area);
	}

	@Override
	public Area get(String id)throws Exception {
		return this.areaDao.get(id);
	}
    
}

package com.boyuyun.base.sys.biz.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.boyuyun.base.sys.biz.SysParamBiz;
import com.boyuyun.base.sys.dao.SysParamDao;
import com.boyuyun.base.sys.entity.SysParam;
import com.google.common.base.Strings;
@Service
public class SysParamBizImpl  implements SysParamBiz {

    @Resource
    private SysParamDao paramDao ;

	@Transactional
	public boolean save(SysParam param) throws Exception{
		//1.删除原系统参数设置
		boolean rtn = paramDao.delete();
		if(rtn){
			String[] paramKey = param.getParamKey().split(",");
			String[] paramValue = param.getParamValue().split(",");
			List<SysParam> list = new ArrayList<SysParam>();
			for(int i=0;i<paramKey.length;i++){
				SysParam po = new SysParam();
				po.setParamKey(paramKey[i]);
				po.setParamValue(Strings.isNullOrEmpty(paramValue[i])?null:paramValue[i]);
				list.add(po);
			}
			paramDao.insert(list);
			rtn = true;
		}
		return rtn;
	}
	public Map getAllAsMap()throws Exception{
		List<SysParam> list = paramDao.getAll();
		Map map = new HashMap();
		if(list!=null){
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				SysParam sysParam = (SysParam) iterator.next();
				map.put(sysParam.getParamKey(), sysParam.getParamValue());
			}
		}
		return map;
	}
	@Override
	public List<SysParam> getAll() throws Exception{
		return paramDao.getAll();
	}

}

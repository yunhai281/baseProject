package com.boyuyun.base.org.biz;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.boyuyun.base.org.entity.Government;

public interface GovernmentBiz  {
	public Government get(String id)  throws SQLException;
	public boolean add(Government gov)throws SQLException;
	public boolean update(Government gov)throws SQLException;
	public boolean delete(Government gov)throws SQLException;
	/**
	 * 选择所有的父级机构
	 * @author LHui
	 * @since 2017-3-1 下午2:04:29
	 * @return
	 */
	public List<Government> listRoot()  throws SQLException;
	
	/**
	 * 根据id，找到该id下的所有子节点
	 * @author LHui
	 * @since 2017-3-3 上午11:40:35
	 * @param id
	 * @return
	 */
	public List<Government> getByParentId(String id) throws SQLException;
	
	/**
	 * 增加、修改时的查重校验
	 * @author LHui
	 * @since 2017-3-3 下午12:53:49
	 * @param government
	 * @return
	 */
	public List<Government> validateGov(Government government)  throws SQLException;
	
	/**
	 * 查询机构，包括查询该机构父节点name
	 * @author LHui
	 * @since 2017-3-6 上午10:42:20
	 * @param id
	 * @return
	 */
	public Government selectFullById(String id)  throws SQLException;
	/**
	 * 动态树
	 * @param parentId
	 * @return
	 */
	public List getDynamicGovernmentTree(String parentId)  throws SQLException;
	
	/**
	 * 根据机构名称，搜索机构，以及该机构下的所有子机构，并形成树结构返回
	 * @author LHui
	 * @since 2017-3-13 上午10:23:37
	 * @param name
	 * @return
	 */
	public List searchByName(String name)  throws SQLException;
	
	public String insertImportExcel(Map<Integer, List> contentMap,List listForIds)throws Exception;
}

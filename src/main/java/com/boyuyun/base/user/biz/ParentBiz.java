package com.boyuyun.base.user.biz;

import java.util.List;

import com.boyuyun.base.user.entity.Parent;


public interface ParentBiz  {
	/**
	 * @author caoyy
	 * @date 2017-03-21
	 * @see 新建
	 * @return
	 */
	public boolean add(Parent parent) throws Exception;
	/**
	 * @author caoyy
	 * @date 2017-03-21
	 * @see 修改
	 * @return
	 */
	public boolean update(Parent parent)throws Exception;
	/**
	 * @author caoyy
	 * @date 2017-03-21
	 * @see 批量删除
	 * @return
	 */
	public boolean delete(List<Parent> parents)throws Exception;
	/**
	 * @author caoyy
	 * @date 2017-03-21
	 * @see 分页获取记录
	 * @return
	 */
	public List<Parent> getListNonePaged(Parent parent)throws Exception;
	/**
	 * @author caoyy
	 * @date 2017-03-21
	 * @see 获取记录
	 * @return
	 */
	public List<Parent> getListPaged(Parent parent)throws Exception;
	/**
	 * @author caoyy
	 * @date 2017-03-21
	 * @see 获取记录个数
	 * @return
	 */
	public int getListPagedCount(Parent parent)throws Exception;
	/**
	 * @author caoyy
	 * @date 2017-03-21
	 * @see 根据id获取记录
	 * @return
	 */
	public Parent get(Parent parent)throws Exception;
}

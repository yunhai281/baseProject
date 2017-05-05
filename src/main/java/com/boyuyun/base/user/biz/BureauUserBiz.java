package com.boyuyun.base.user.biz;

import java.util.List;
import java.util.Map;

import com.boyuyun.base.user.entity.BureauUser;

public interface BureauUserBiz  {
	/**
	 * 保存教育局用户
	 * @param burea 教育局用户实体类
	 * @param postList 岗位数组
	 * @return
	 * @throws Exception
	 */
	public int  saveBureau(BureauUser burea,String [] postList) throws Exception;
	/**
	 * 批量删除教育局用户
	 * @param list 教育局用户集合
	 * @return
	 * @throws Exception
	 */
	public int deleteBueau(List<BureauUser> list) throws Exception;
	/**
	 * 获取教育局用户列表（不分页）
	 * @param user 教育局用户
	 * @return
	 * @throws Exception
	 */
	public List<BureauUser> getListNonePaged(BureauUser user) throws Exception;
	/**
	 * 获取教育局用户列表（分页）
	 * @param user 教育局用户
	 * @return
	 * @throws Exception
	 */
	public List<BureauUser> getListPaged(BureauUser user)throws Exception;
	/**
	 * 获取教育局用户列表个数
	 * @param user 教育局用户
	 * @return
	 * @throws Exception
	 */
	public int getListPagedCount(BureauUser user)throws Exception;
	/**
	 * 获取教育局用户实体类
	 * @param user 教育局用户
	 * @return
	 * @throws Exception
	 */
	public BureauUser get(BureauUser user)throws Exception;
	
	public String insertImportExcel(Map<Integer, List> contentMap,List listForIds)throws Exception;
	
	public String updateSortNumber (String id, String type)throws Exception;
}

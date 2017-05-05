package com.boyuyun.base.user.biz.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boyuyun.base.org.dao.GovernmentDao;
import com.boyuyun.base.org.entity.Government;
import com.boyuyun.base.sys.dao.DictionaryItemDao;
import com.boyuyun.base.sys.entity.DictionaryItem;
import com.boyuyun.base.user.biz.BureauUserBiz;
import com.boyuyun.base.user.dao.BureauUserDao;
import com.boyuyun.base.user.dao.BureauUserPostDao;
import com.boyuyun.base.user.dao.UserDao;
import com.boyuyun.base.user.entity.BureauUser;
import com.boyuyun.base.user.entity.BureauUserPost;
import com.boyuyun.common.util.ByyStringUtil;
import com.google.common.base.Strings;
@Service
public class BureauUserBizImpl  implements BureauUserBiz {
	@Resource
	private BureauUserDao BureauUserDao;
	@Resource
	private BureauUserPostDao BureauUserPostDao;

	@Resource
	private DictionaryItemDao dictionaryItemDao;
	@Resource
	private GovernmentDao governmentDao;
	
	
	@Resource
	private UserDao userDao;

	/**
	 * 保存教育局用户
	 * @param burea 教育局用户实体类
	 * @param postList 岗位数组
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int saveBureau(BureauUser bureauUser, String [] postList) throws Exception {
		if (bureauUser.getId() != null && bureauUser.getId().trim().length() > 0) {
			// 修改人员是否更新部门
			BureauUser tempuUser = BureauUserDao.getBureauUser(bureauUser);
			String governmentId="";
			String tempId="";
			if (!Strings.isNullOrEmpty(bureauUser.getGovernmentJuId())) {
				governmentId=bureauUser.getGovernmentJuId();
				tempId=tempuUser.getGovernmentJuId();
			}else if (!Strings.isNullOrEmpty(bureauUser.getGovernmentTingId())) {
				governmentId=bureauUser.getGovernmentTingId();
				tempId=tempuUser.getGovernmentTingId();
			}else {
				governmentId=bureauUser.getGovernmentId();
				tempId=tempuUser.getGovernmentId();
			}
			if(!governmentId.equals(tempId)){
				int sort = BureauUserDao.getMaxSortnum(governmentId);
				bureauUser.setSortnum(sort+1);
				BureauUserDao.updateSortnum(bureauUser);
			}
			//更新教育局用户信息
			BureauUserDao.update(bureauUser);
			//更新用户信息
			userDao.update(bureauUser);
			//根据教育局用户id删除该用户的岗位
			BureauUserPostDao.deleteByUserId(bureauUser.getId());
			//添加教育局岗位
			for(String postId:postList){
				BureauUserPost post = new BureauUserPost();
				post.setId(UUID.randomUUID().toString());
				post.setBureauUserId(bureauUser.getId());
				post.setPost(Integer.valueOf(postId));
				BureauUserPostDao.insert(post);
			}
		} else {
			String id = ByyStringUtil.getRandomUUID();
			bureauUser.setId(id);
			//添加教育局用户信息
			BureauUserDao.insert(bureauUser);
			//添加用户信息
			userDao.insert(bureauUser);
			//添加岗位信息
			for(String postId:postList){
				BureauUserPost post = new BureauUserPost();
				post.setId(UUID.randomUUID().toString());
				post.setBureauUserId(id);
				post.setPost(Integer.valueOf(postId));
				BureauUserPostDao.insert(post);
			}
		}
		
		return 1;
	}

	/**
	 * 删除教育局用户
	 * @param list 教育局用户集合
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public int deleteBueau(List<BureauUser> list) throws Exception{
		for(BureauUser b:list){
			userDao.delete(b);
			BureauUserDao.delete(b);
			BureauUserPostDao.deleteByUserId(b.getId());
		}
		return 1;
	}
	/**
	 * 获取教育局用户集合(不分页)
	 * @param user 教育局用户
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<BureauUser> getListNonePaged(BureauUser user) throws Exception{
		user.setBegin(-1);
		user.setEnd(-1);
		return BureauUserDao.getList(user);
	}
	/**
	 * 获取教育局用户集合(分页)
	 * @param user 教育局用户
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<BureauUser> getListPaged(BureauUser user)  throws Exception{
		boolean searchPost = false;
		if(user.getPostName()!=null && !"".equals(user.getPostName())){
			searchPost = user.getPostName().equals("true")?true:false;
		}
		List<BureauUser> list = BureauUserDao.getList(user);
		if(searchPost){
			if(list!=null&&!list.isEmpty()){
				for(BureauUser po:list){
					List<BureauUserPost> postList = BureauUserPostDao.getListByUserId(po.getId());
					List<String> posts = new ArrayList<String>();
					for(int i=0;i<postList.size();i++){
						posts.add(postList.get(i).getPostName());
					}
					po.setPostName(StringUtils.join(posts, ","));
				}
			}
		}
		return list;
	}
	/**
	 * 获取教育局用户列表个数
	 * @param user 教育局用户
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getListPagedCount(BureauUser user)  throws Exception{
		return BureauUserDao.getListPagedCount(user);
	}
	
	/**
	 * 获取教育局用户
	 * @param user 教育局用户
	 * @return
	 * @throws Exception
	 */
	@Override
	public BureauUser get(BureauUser user) throws Exception{
		List<BureauUserPost> postList = BureauUserPostDao.getListByUserId(user.getId());
		BureauUser rtn = null;
		//获取岗位列表
		rtn = BureauUserDao.getBureauUser(user);
		rtn.setPost(postList);
		return rtn;
	}
	
	private Map<Integer,String> getDictionaryItemArr(String code) throws Exception{
		List<DictionaryItem> list=dictionaryItemDao.getDictionaryItemList(code, "");
		Map<Integer,String> datamMap= new HashMap<>();
		if(null!=list && list.size()>0){
        	for (int i = 0; i < list.size(); i++) {
        		DictionaryItem dictionaryItem = list.get(i);
        		datamMap.put(dictionaryItem.getId(), dictionaryItem.getName());
			}
        }  
        return datamMap;
	}
	
	/**
	 * @Description 根绝map的value取得key(字典id,不用多次查询数据库)
	 * @author jms
	 * @param map
	 * @param value
	 * @return
	 */
	 public Integer getKeyByValue(Map<Integer,String> map, String value)throws Exception  {  
        int keys=-1;  
        Set<Integer>kset=map.keySet();
        for(Integer ks:kset){
            if(value.equals(map.get(ks))){
            	keys=ks;
            	break;
            }
        } 
        return keys;  
     }
	 

	@Override
	@Transactional
	public String insertImportExcel(Map<Integer, List> contentMap,List listForIds) throws Exception {
		String msg="";
		String result="";
		Map<String, String> validNoMap = new HashMap<String, String>(); 
		// 取得字典
		// 学校类型
		Map<Integer, String> sexArr=this.getDictionaryItemArr("Gender");
		// 岗位
		Map<Integer, String> governmentPostArr=this.getDictionaryItemArr("GovernmentPost");
		//循环map
		List<BureauUser> bureauUsers = new ArrayList<BureauUser>();
		List<BureauUserPost> bureauUserPosts = new ArrayList<BureauUserPost>();
		boolean flag = true;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map mobileMap = new HashMap();
		for(int i=1;i<=contentMap.size();i++){
			List contentList = contentMap.get(i);
			boolean hasContent = false;
			for (Object object : contentList) {
				String string = (String) object;
				if(!Strings.isNullOrEmpty(string)){
					hasContent = true;
					break;
				}  
			} 
			if (!hasContent) {
				continue;
			}
			//一个List就是一个对象，然后进行保存
			// 用户名	
			String userName = (String)contentList.get(0);
			int count = BureauUserDao.getListByName(userName);
			if(count!=0){
				msg+="第"+(i+1)+"行用户名已存在!<br>";
				flag= false;
			}
			if(validNoMap.containsKey(userName)){
				msg+="第"+(i+1)+"行用户名已存在!<br>";
				flag= false;
			}
			validNoMap.put(userName, userName);
			
			// 姓名
			String realName = (String)contentList.get(1);
			
			// 	性别	
			String sex = (String)contentList.get(2);
			int sexKey=-1;
			if(!Strings.isNullOrEmpty(sex)){ 
				 if(sexArr.containsValue(sex)){
					 sexKey = this.getKeyByValue(sexArr, sex);
				}else{
					msg+="第"+(i+1)+"行性别选择不正确!<br>";
					flag = false;
				}
			}
			// 手机号码
			String mobile = (String)contentList.get(3);
			if(null!=mobile && !"".equals(mobile)){
				mobile = mobile.trim();
				if(mobileMap.get(mobile)!=null){
					msg+="第"+(i+1)+"行手机号重复!<br>";
					flag = false;
				}
				mobileMap.put(mobile, mobile);
				count= userDao.getMobileCount(mobile);
				if(count>0){
					msg+="第"+(i+1)+"行手机号重复!<br>";
					flag = false;
				}
			}
				
			// 	邮箱	
			String email = (String)contentList.get(4);
			if(null!=email && !"".equals(email)){
				email = email.trim();
				if(mobileMap.get(email)!=null){
					msg+="第"+(i+1)+"行邮箱重复!<br>";
					flag = false;
				}
				mobileMap.put(email, email);
				count= userDao.getEmailCount(email);
				if(count>0){
					msg+="第"+(i+1)+"行邮箱重复!<br>";
					flag = false;
				}
			}
			
			// 所属部门	 
			String governmentName = (String)contentList.get(5);
			String governmentId="";
			String governmentJuId="";
			String governmentTingId="";
			if(governmentName!=null && !"".equals(governmentName)){
				List<Government> list = governmentDao.getGovByName(governmentName);
				if(list== null || list.size()==0){
					msg+="第"+(i+1)+"行该教育局不存在,请重新输入!<br>";
					flag= false;
				}else {
					Government government =list.get(0);
					// 若教育局存在取得教育局的id
					governmentId = government.getId();
					// 教育厅
					if(government.getLevelType().equals("2")){
						governmentTingId = government.getId();
					}else if(government.getLevelType().equals("3")){
						// 教育局
						governmentJuId = government.getId();
						governmentTingId = government.getParentId();
					} 
				}
			}else{
				msg+="第"+(i+1)+"行该教育局为必填!<br>";
				flag= false;
			}
			// 岗位
			String governmentPost = (String)contentList.get(6);
			int governmentPostKey=-1;
			if(governmentPost!=null && !"".equals(governmentPost)){
				 if(governmentPostArr.containsValue(governmentPost)){
					 governmentPostKey = this.getKeyByValue(governmentPostArr, governmentPost);
				}else{
					msg+="第"+(i+1)+"行岗位不正确!<br>";
					flag = false;
				}
			}else {
				msg+="第"+(i+1)+"行岗位为必填!<br>";
				flag= false;
			}
			 
			BureauUser tempBureauUser= new BureauUser();
			String id=ByyStringUtil.getRandomUUID().replace("-", "");
			tempBureauUser.setId(id); 
			listForIds.add(id);
			tempBureauUser.setGovernmentId(governmentId);
			tempBureauUser.setGovernmentJuId(governmentJuId);
			tempBureauUser.setGovernmentTingId(governmentTingId);
			// User
			String pwd ="f379eaf3c831b04de153469d1bec345e";
			tempBureauUser.setUserName(userName);
			tempBureauUser.setPwd(pwd); 
			if(sexKey!=-1){
				tempBureauUser.setSex(sexKey);
			}   

			tempBureauUser.setRealName(realName);
			tempBureauUser.setMobile(mobile);
			tempBureauUser.setEmail(email);
			bureauUsers.add(tempBureauUser);
			
			// BureauUserPost
			BureauUserPost bureauUserPost = new BureauUserPost();
			String idPost=ByyStringUtil.getRandomUUID().replace("-", "");
			bureauUserPost.setId(idPost); 
			bureauUserPost.setBureauUserId(id);
			bureauUserPost.setPost(governmentPostKey);
			bureauUserPosts.add(bureauUserPost);
		}
		if(flag){
			for (int j = 0; j < bureauUsers.size(); j++) {
				BureauUser bureauUser =bureauUsers.get(j);
				BureauUserPost bureauUserPost  = bureauUserPosts.get(j);
				userDao.insert(bureauUser);
				BureauUserDao.insert(bureauUser);
				BureauUserPostDao.insert(bureauUserPost);
			}
		}else {
			result = msg;
		}
		
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String updateSortNumber(String id, String type) throws Exception {
		String result ="";
		
		BureauUser bureauUser = new BureauUser();
		bureauUser.setId(id);
		bureauUser =BureauUserDao.getBureauUser(bureauUser);
		int sort= bureauUser.getSortnum();
		if("up".equals(type)){
			if(sort==0 || sort==1){
				result ="当前排序已是第一位!";
			}else {
				BureauUser upBureauUser = new BureauUser();
				upBureauUser.setSortnum(bureauUser.getSortnum()-1);
				upBureauUser.setGovernmentId(bureauUser.getGovernmentId());
				upBureauUser.setGovernmentJuId(bureauUser.getGovernmentJuId());
				upBureauUser.setGovernmentTingId(bureauUser.getGovernmentTingId());
				List<BureauUser> list= BureauUserDao.getSortnumList(upBureauUser);
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					BureauUser bureauUser2 = (BureauUser) iterator.next();
					// 选中人员的前的人员下移
					bureauUser2.setSortnum(sort);
					// 选中的人员上移
					bureauUser.setSortnum(sort-1);
					BureauUserDao.updateSortnum(bureauUser2);
					BureauUserDao.updateSortnum(bureauUser);
				}
				result ="移动成功!";
			}
		}else {
			BureauUser upBureauUser = new BureauUser();
			upBureauUser.setSortnum(bureauUser.getSortnum()+1);
			upBureauUser.setGovernmentId(bureauUser.getGovernmentId());
			upBureauUser.setGovernmentJuId(bureauUser.getGovernmentJuId());
			upBureauUser.setGovernmentTingId(bureauUser.getGovernmentTingId());
			List<BureauUser> list= BureauUserDao.getSortnumList(upBureauUser);
			if(list.size()!=0){
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					BureauUser bureauUser2 = (BureauUser) iterator.next();
					// 选中人员的后的人员上移
					bureauUser2.setSortnum(sort);
					// 选中的人员下移
					bureauUser.setSortnum(sort+1);
					BureauUserDao.updateSortnum(bureauUser2);
					BureauUserDao.updateSortnum(bureauUser);
				}
				result ="移动成功!";
			}else {
				result ="已经是最后一位!";
			}
		}
		return result;
	}

}

package com.boyuyun.base.org.biz.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boyuyun.base.org.biz.GovernmentBiz;
import com.boyuyun.base.org.dao.GovernmentDao;
import com.boyuyun.base.org.entity.Government;
import com.boyuyun.base.org.util.TreeDTO;
import com.boyuyun.base.sys.dao.AreaDao;
import com.boyuyun.base.sys.entity.Area;
import com.boyuyun.common.annotation.SyncTo;
import com.boyuyun.common.datasync.SyncDataType;
import com.boyuyun.common.datasync.SyncOperateType;
import com.boyuyun.common.util.ByyStringUtil;
import com.google.common.base.Strings;
@Service
public class GovernmentBizImpl  implements GovernmentBiz {

    @Resource
    GovernmentDao governmentDao;
	@Resource
	private AreaDao areaDao; 

	@Override
	public List<Government> listRoot()  throws SQLException{
		return this.governmentDao.selectGovRoot();
	}

	@Override
	public List<Government>  getByParentId(String id)  throws SQLException{
		return this.governmentDao.selectByParentId(id);
	}

	@Override
	public List<Government> validateGov(Government government)  throws SQLException{
		return this.governmentDao.validateGov(government);
	}

	@Override
	public Government selectFullById(String id)  throws SQLException{
		return this.governmentDao.selectFullById(id);
	}

	@Override
	public List getDynamicGovernmentTree(String parentId) throws SQLException {
		List<TreeDTO> list  = new ArrayList<TreeDTO>();
		//生成一棵树
		if(!Strings.isNullOrEmpty(parentId)){
			Government govobj = (Government) this.get(parentId);
			if(govobj != null){
				List<Government> sons = governmentDao.selectByParentId(govobj.getId());
				if(sons != null && sons.size() > 0){
					for(Iterator iterator = sons.iterator();iterator.hasNext();){
						Government government =  (Government)iterator.next();
						if(government.getChildNum() > 0){
							government.setIsParent("true");
						}else{
							government.setIsParent("false");
						}
						list.add(government.getTreeDTO());
					}
				}
			}
			
		}else{
			//第一次加载，只需要加载部级 ，然后通过sons得到子集 ，然后传递过去。
			List<Government> govList = governmentDao.selectGovRoot();
			if(govList != null && govList.size() > 0){
				//进行循环
				for(Iterator iterator = govList.iterator();iterator.hasNext();){
					Government government = (Government) iterator.next();
					List<Government> sons =governmentDao. selectByParentId(government.getId());
					List<TreeDTO> children = new ArrayList<TreeDTO>();
					//循环每一个元素查看是否还有子节点，如果没有则为叶子，有则为父节点
					for(Iterator iterator2 = sons.iterator();iterator2.hasNext();){
						Government temp =  (Government)iterator2.next();
						if(temp.getChildNum() > 0){
							temp.setIsParent("true");
						}else{
							temp.setIsParent("false");
						}
						children.add(temp.getTreeDTO());
					}
					TreeDTO dto = government.getTreeDTO();
					dto.setChildren(children);
					list.add(dto);
				}
			}
		}
		return list;
	}

	@Override
	public List searchByName(String name)  throws SQLException{
		List<TreeDTO> list  = new ArrayList<TreeDTO>();
		//生成一棵树
		if(!Strings.isNullOrEmpty(name)){
			List<Government> govList = governmentDao.selectGovByName(name);
			if(govList != null && govList.size() > 0){
				//进行循环
				for(Iterator iterator = govList.iterator();iterator.hasNext();){
					Government government = (Government) iterator.next();
					List<Government> sons =governmentDao. selectByParentId(government.getId());
					List<TreeDTO> children = new ArrayList<TreeDTO>();
					//循环每一个元素查看是否还有子节点，如果没有则为叶子，有则为父节点
					for(Iterator iterator2 = sons.iterator();iterator2.hasNext();){
						Government temp =  (Government)iterator2.next();
						if(temp.getChildNum() > 0){
							temp.setIsParent("true");
						}else{
							temp.setIsParent("false");
						}
						children.add(temp.getTreeDTO());
					}
					TreeDTO dto = government.getTreeDTO();
					dto.setChildren(children);
					list.add(dto);
				}
			}else{
				List<Government> govListParent = governmentDao.selectGovRoot();
				if(govListParent != null && govListParent.size() > 0){
					//进行循环
					for(Iterator iterator = govListParent.iterator();iterator.hasNext();){
						Government government = (Government) iterator.next();
						List<Government> sons =governmentDao. selectByParentId(government.getId());
						List<TreeDTO> children = new ArrayList<TreeDTO>();
						TreeDTO dto = government.getTreeDTO();
						dto.setChildren(children);
						list.add(dto);
					}
				}
			}
				
		}
		return list;
	}

	@Override
	@Transactional
	@SyncTo(dataType=SyncDataType.Government,operateType=SyncOperateType.Add,system={"dudao","oa","kg","wd"})
	public boolean add(Government gov) throws SQLException {
		return governmentDao.insert(gov);
	}

	@Override
	@Transactional
	@SyncTo(dataType=SyncDataType.Government,operateType=SyncOperateType.Update,system={"dudao","oa","kg","wd"})
	public boolean update(Government gov)  throws SQLException{
		return governmentDao.update(gov);
	}

	@Override
	@Transactional
	@SyncTo(dataType=SyncDataType.Government,operateType=SyncOperateType.Delete,system={"dudao","oa","kg","wd"})
	public boolean delete(Government gov)  throws SQLException{
		if(gov==null){
			return false;
		}
		return governmentDao.delete(gov.getId());
	}

	@Override
	public Government get(String id)  throws SQLException{
		return governmentDao.get(id);
	}
	 
	@Override
	@Transactional
	public String insertImportExcel(Map<Integer, List> contentMap,List listForIds) throws Exception {
		String msg="";
		String result="";
		Map<String, String> validNoMap = new HashMap<String, String>();
		Map<String, String> validNameMap = new HashMap<String, String>();
		// 取得字典
		//循环map
		List<Government> govList = new ArrayList<Government>();
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
			// 机构名称 
			String governmentName = (String)contentList.get(0);
			if(governmentName!=null && !"".equals(governmentName)){
				List<Government> governments= governmentDao.getGovByName(governmentName);
				if(governments.size()!=0){
					msg+="第"+(i+1)+"行该机构已存在!<br>";
					flag= false;
				}
				if(validNameMap.containsKey(governmentName)){
					msg+="第"+(i+1)+"行结构名称excel中以录入,请检查是否重复!<br>";
					flag= false;
				}else {
					validNameMap.put(governmentName, governmentName);
				}
			}else {
				msg+="第"+(i+1)+"行机构为必填!<br>";
				flag= false;
			}
			
			//机构号码
			String code = (String)contentList.get(1);
			String regEx = "^\\d+$";
			
			if(code!=null && !"".equals(code)){
				if(code.length()>20){
					msg+="第"+(i+1)+"行该机构号码长度小于20位!<br>";
					flag= false;
				}
				
				if(code.matches(regEx) ){
					int count=governmentDao.getGovByCode(code);
					if(count>0){
						msg+="第"+(i+1)+"行该机构号码已存在!<br>";
						flag= false;
					}
					if(validNoMap.containsValue(code)){
						msg+="第"+(i+1)+"行该机构号码excel中以录入,请检查是否重复!<br>";
						flag= false;
					}else {
						validNoMap.put(code, code);
					}
				}else {
					msg+="第"+(i+1)+"行该机构号码格式不正确!<br>";
					flag= false;
				}
			}else{
				msg+="第"+(i+1)+"行该机构号码为必填!<br>";
				flag= false;
			}
				
			//机构级别
			String levelType = (String)contentList.get(2);
			String levelTypeKey="";
			if(levelType!=null && !"".equals(levelType)){
				if(levelType.equals("部")){
					levelTypeKey = "1";
				}else if(levelType.equals("厅")){
					levelTypeKey = "2";
				}else if(levelType.equals("局")){
					levelTypeKey = "3";
				}else if(levelType.equals("科室")){
					levelTypeKey = "4";
				}else {
					msg+="第"+(i+1)+"行该机构级别不存在,请重新输入!<br>";
					flag= false;
				}
			}else {
				msg+="第"+(i+1)+"行机构级别必填!<br>";
				flag= false;
			}
			
			//上级机构
			String parentName = (String)contentList.get(3);
			String parentId="";
			if(parentName!=null && !"".equals(parentName)){
				List<Government> list = governmentDao.getGovByName(parentName);
				if(list== null || list.size()==0){
					msg+="第"+(i+1)+"行该教育局不存在,请重新输入!<br>";
					flag= false;
				}else {
					// 若教育局存在取得教育局的id
					parentId = list.get(0).getId();
				}
			}
			//所在地 => 市级单位
			String areaName = (String)contentList.get(4);
			String areaId="";
			if(areaName!=null && !"".equals(areaName)){
				List<Area> areas= areaDao.selectByName(areaName);
				if(areas!=null && areas.size()>0){
					areaId = areas.get(0).getId();
				}else {
					msg+="第"+(i+1)+"行地域不存在,请重新输入!<br>";
					flag= false;
				}
				
			}
			Government government = new Government();
			String id=ByyStringUtil.getRandomUUID().replace("-", "");
			government.setId(id);
			listForIds.add(id);
			government.setCode(code);
			government.setName(governmentName);
			government.setLevelType(levelTypeKey);
			government.setParentId(parentId);
			government.setAreaId(areaId);
			govList.add(government);
			
		}
		if(flag){
			for (Iterator iterator = govList.iterator(); iterator.hasNext();) {
				Government government = (Government) iterator.next();
				governmentDao.insert(government);
			} 
		}else {
			result = msg;
		}
		
		return result;
	}
}

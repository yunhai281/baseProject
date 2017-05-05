package com.boyuyun.base.org.biz.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boyuyun.base.org.biz.SchoolBiz;
import com.boyuyun.base.org.dao.GovernmentDao;
import com.boyuyun.base.org.dao.SchoolDao;
import com.boyuyun.base.org.dao.SchoolGradeDao;
import com.boyuyun.base.org.entity.Government;
import com.boyuyun.base.org.entity.School;
import com.boyuyun.base.org.entity.SchoolGrade;
import com.boyuyun.base.org.util.TreeDTO;
import com.boyuyun.base.sys.dao.AreaDao;
import com.boyuyun.base.sys.dao.DictionaryItemDao;
import com.boyuyun.base.sys.entity.Area;
import com.boyuyun.base.sys.entity.DictionaryItem;
import com.boyuyun.base.user.dao.UserDao;
import com.boyuyun.base.user.entity.User;
import com.boyuyun.base.util.ConstantUtil;
import com.boyuyun.base.util.consts.Stage;
import com.boyuyun.common.annotation.SyncTo;
import com.boyuyun.common.datasync.SyncDataType;
import com.boyuyun.common.datasync.SyncOperateType;
import com.boyuyun.common.util.ByyStringUtil;
import com.boyuyun.common.util.PinyinUtil;
import com.google.common.base.Strings;

@Service
public class SchoolBizImpl  implements SchoolBiz {

	@Resource
	private SchoolDao schoolDao;
	@Resource
	private UserDao userDao;
	
	@Resource
	private SchoolGradeDao schoolGradeDao;
	@Resource
	private GovernmentDao governmentDao; 
	@Resource
	private AreaDao areaDao; 
	@Resource
	private DictionaryItemDao dictionaryItemDao;
	 
	@Override
	public List<School> selectAll() throws Exception{
		return this.schoolDao.selectAll();
	}

	@Override
	public List<School> selectAllParent() throws Exception{
		return this.schoolDao.selectAllParent();
	}

	@Override
	public List<School> getByParentId(String id) throws Exception{
		return schoolDao.getByParentId(id);
	}

	@Override
	public List<School> getByGovernmentId(String id) throws Exception{
		return schoolDao.getByGovernmentId(id);
	}
	public List getDynamicGovernmentSchoolAndGradeTree(String type, String parentId)throws Exception {
		List<TreeDTO> list = new ArrayList<TreeDTO>();
		// 生成一棵树
		if ("government".equals(type) && !Strings.isNullOrEmpty(parentId)) {
			Government govobj = (Government) governmentDao.get(parentId);
			if (govobj != null) {
				List sons = governmentDao.selectByParentId(govobj.getId());
				List<School> childSchools = null;
				Map<String, String> mapSchool = new HashMap<String, String>();
				if (sons == null) {
					sons = new ArrayList();
				} else {
					List item = new ArrayList();
					for (Iterator iterator = sons.iterator(); iterator.hasNext();) {
						Object obj = iterator.next();
						if (obj instanceof Government) {
							Government government = (Government) obj;
							item.add(government.getId());
						}
					}
					if (item != null && item.size() > 0) {
						childSchools = this.findByGovIdsMap(item);
						if (childSchools != null && childSchools.size() > 0) {
							for (Iterator<School> iterator = childSchools.iterator(); iterator.hasNext();) {
								School school = (School) iterator.next();
								if (!mapSchool.containsKey(school.getId())) {
									mapSchool.put(school.getGovernmentId(), school.getId());
								}
							}
						}
					}
				}
				List son_schools = this.getByGovernmentId(govobj.getId());
				if (son_schools != null)
					sons.addAll(son_schools);
				Map<String, TreeDTO> checkObj = new HashMap<String, TreeDTO>();// 用于排除重复显示学校用
				List<School> checkSch = new ArrayList<School>();// 用于排除重复显示学校用
				if (sons != null && sons.size() > 0) {
					for (Iterator iterator = sons.iterator(); iterator.hasNext();) {
						Object obj = iterator.next();
						if (obj instanceof Government) {
							Government government = (Government) obj;
							if (government.getChildNum() > 0) {
								government.setIsParent("true");
							} else {
								if (mapSchool.containsKey(government.getId())) {
									government.setIsParent("true");
								} else {
									government.setIsParent("false");
								}
							}
							list.add(government.getTreeDTO());
						} else if (obj instanceof School) {
							School sch = (School) obj;
							if (sch.getParentId() != null && sch.getParentId() != "") {
								List<School> list2 =getByParentId(sch.getId());
								sch.setChildNum(list2.size());
								checkSch.add(sch);
							} else {
								checkObj.put(sch.getId(), sch.getTreeDTO());
								
								SchoolGrade schoolGrade = new SchoolGrade();
								schoolGrade.setSchoolId(sch.getId());
								List<SchoolGrade> schoolGrades = schoolGradeDao.getListNonePaged(schoolGrade);
								if (sch.getChildNum() > 0) {
									sch.setIsParent("true");
								}else if (schoolGrades.size()>0) {
									sch.setIsParent("true");
								} else {
									sch.setIsParent("false");
								}
								list.add(sch.getTreeDTO());
							}
						}
					}
					/**
					 * 操作用于，分校、总校，分校、总校有两种存在情况 1.该分校和总校在同一个教育局下 2.不在同一个教育局下
					 * 对于这两种情况，要进行判断，才能正确显示在树中
					 */
					if (checkSch != null && checkSch.size() > 0) {
						for (School school : checkSch) {
							if (!checkObj.containsKey(school.getParentId())) {
								SchoolGrade schoolGrade = new SchoolGrade();
								schoolGrade.setSchoolId(school.getId());
								List<SchoolGrade> schoolGrades = schoolGradeDao.getListNonePaged(schoolGrade);
								if (school.getChildNum() > 0) {
									school.setIsParent("true");
								}else if (schoolGrades.size()>0) {
									school.setIsParent("true");
								} else {
									school.setIsParent("false");
								}
								if(school.getParentId()==null || "".equals(school.getParentId())){
									list.add(school.getTreeDTO());
								}
							}
						}
					}
				}
			}

		} else if ("school".equals(type) && !Strings.isNullOrEmpty(parentId)) {
			School school = this.get(parentId);
			if (school != null) {
				List<School> sons = schoolDao.getByParentIdWidthChildAndGradeNums(school.getId());
				if (sons != null && sons.size() > 0) {
					for (Iterator iterator = sons.iterator(); iterator.hasNext();) {
						School sch = (School) iterator.next();
						if (sch.getChildNum() > 0||sch.getGradeNum() > 0) {
							sch.setIsParent("true");
						} else {
							sch.setIsParent("false");
						}
						list.add(sch.getTreeDTO());
					}
				}
				//处理学段
				List<Stage> stageList = schoolDao.getSchoolStageList(school.getId());
				if(stageList.size()>0){
					for (Iterator iterator = stageList.iterator(); iterator.hasNext();) {
						Stage stage = (Stage) iterator.next();
						TreeDTO dto = new TreeDTO();
						dto.setId(school.getId()+"_"+stage.ordinal());
						dto.setName(stage.name());
						dto.setType("stage");
						dto.setIsParent("true");
						dto.setIcon(ConstantUtil.TREE_ICON_SCHOOL);
						list.add(dto);
					}
				}
			}
		} else if ("stage".equals(type) && !Strings.isNullOrEmpty(parentId)){
			String schoolId = parentId.substring(0,parentId.indexOf("_"));
			String stageStr = parentId.substring(parentId.indexOf("_")+1);
			Stage stage = Stage.values()[Integer.parseInt(stageStr)];
			List<SchoolGrade> grades = schoolGradeDao.getSchoolGradeBySchoolAndStage(schoolId, stage);
			if(grades!=null){
				for (Iterator iterator = grades.iterator(); iterator.hasNext();) {
					SchoolGrade grade = (SchoolGrade) iterator.next();
					list.add(grade.getTreeDTO());
				}
			}
		}else {
			// 第一次加载，只需要加载部级 ，然后通过sons得到子集 ，然后传递过去。
			List<Government> govList = governmentDao.selectGovRoot();
			if (govList != null && govList.size() > 0) {
				// 进行循环
				for (Iterator iterator = govList.iterator(); iterator.hasNext();) {
					Government government = (Government) iterator.next();
					List sons = governmentDao.selectByParentId(government.getId());
					if (sons == null)
						sons = new ArrayList();
					List son_schools = this.getByGovernmentId(government.getId());
					if (son_schools != null)
						sons.addAll(son_schools);
					List<TreeDTO> children = new ArrayList<TreeDTO>();
					// 循环每一个元素查看是否还有子节点，如果没有则为叶子，有则为父节点
					for (Iterator iterator2 = sons.iterator(); iterator2.hasNext();) {
						Object tempobj = iterator2.next();
						if (tempobj instanceof Government) {
							Government temp = (Government) tempobj;
							if (temp.getChildNum() > 0) {// 这里只是查看了该节点下面是否有部门的子节点,没有查看该节点下是否含有学校
								temp.setIsParent("true");
							} else {
								temp.setIsParent("false");
							}
						 	List<School> list2= getByGovernmentId(temp.getId());
						 	if(list2!=null && list2.size()>0){
						 		temp.setIsParent("true");
						 	}
							children.add(temp.getTreeDTO());
						} else if (tempobj instanceof School) {
							School sch = (School) tempobj;
							if (sch.getChildNum() > 0) {
								sch.setIsParent("true");
							} else {
								sch.setIsParent("false");
							}
							children.add(sch.getTreeDTO());
						}
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
	public List getDynamicGovernmentAndSchoolTree(String type, String parentId)throws Exception {
		List<TreeDTO> list = new ArrayList<TreeDTO>();
		// 生成一棵树
		if ("government".equals(type) && !Strings.isNullOrEmpty(parentId)) {
			Government govobj = (Government) governmentDao.get(parentId);
			if (govobj != null) {
				List sons = governmentDao.selectByParentId(govobj.getId());
				List<School> childSchools = null;
				Map<String, String> mapSchool = new HashMap<String, String>();
				if (sons == null) {
					sons = new ArrayList();
				} else {
					List item = new ArrayList();
					for (Iterator iterator = sons.iterator(); iterator.hasNext();) {
						Object obj = iterator.next();
						if (obj instanceof Government) {
							Government government = (Government) obj;
							item.add(government.getId());
						}
					}
					if (item != null && item.size() > 0) {
						childSchools = this.findByGovIdsMap(item);
						if (childSchools != null && childSchools.size() > 0) {
							for (Iterator iterator = childSchools.iterator(); iterator.hasNext();) {
								School school = (School) iterator.next();
								if (!mapSchool.containsKey(school.getId())) {
									mapSchool.put(school.getGovernmentId(), school.getId());
								}
							}
						}
					}
				}
				List son_schools = this.getByGovernmentId(govobj.getId());
				if (son_schools != null)
					sons.addAll(son_schools);
				Map<String, TreeDTO> checkObj = new HashMap<String, TreeDTO>();// 用于排除重复显示学校用
				List<School> checkSch = new ArrayList<School>();// 用于排除重复显示学校用
				if (sons != null && sons.size() > 0) {
					for (Iterator iterator = sons.iterator(); iterator.hasNext();) {
						Object obj = iterator.next();
						if (obj instanceof Government) {
							Government government = (Government) obj;
							if (government.getChildNum() > 0) {
								government.setIsParent("true");
							} else {
								if (mapSchool.containsKey(government.getId())) {
									government.setIsParent("true");
								} else {
									government.setIsParent("false");
								}
							}
							list.add(government.getTreeDTO());
						} else if (obj instanceof School) {
							School sch = (School) obj;
							if (sch.getParentId() != null && sch.getParentId() != "") {
								List<School> list2 =getByParentId(sch.getId());
								sch.setChildNum(list2.size());
								checkSch.add(sch);
							} else {
								checkObj.put(sch.getId(), sch.getTreeDTO());
								if (sch.getChildNum() > 0) {
									sch.setIsParent("true");
								} else {
									sch.setIsParent("false");
								}
								list.add(sch.getTreeDTO());
							}
						}
					}
					/**
					 * 操作用于，分校、总校，分校、总校有两种存在情况 1.该分校和总校在同一个教育局下 2.不在同一个教育局下
					 * 对于这两种情况，要进行判断，才能正确显示在树中
					 */
					if (checkSch != null && checkSch.size() > 0) {
						for (School school : checkSch) {
							if (!checkObj.containsKey(school.getParentId())) {
								if (school.getChildNum() > 0) {
									school.setIsParent("true");
								} else {
									school.setIsParent("false");
								}
								if(school.getParentId()==null || "".equals(school.getParentId())){
									list.add(school.getTreeDTO());
								}
							}
						}
					}
				}
			}

		} else if ("school".equals(type) && !Strings.isNullOrEmpty(parentId)) {
			School school = this.get(parentId);
			if (school != null) {
				List<School> sons = this.getByParentId(school.getId());
				if (sons != null && sons.size() > 0) {
					for (Iterator iterator = sons.iterator(); iterator.hasNext();) {
						School sch = (School) iterator.next();
						if (sch.getChildNum() > 0) {
							sch.setIsParent("true");
						} else {
							sch.setIsParent("false");
						}
						list.add(sch.getTreeDTO());
					}
				}
			}
		} else {
			// 第一次加载，只需要加载部级 ，然后通过sons得到子集 ，然后传递过去。
			List<Government> govList = governmentDao.selectGovRoot();
			if (govList != null && govList.size() > 0) {
				// 进行循环
				for (Iterator iterator = govList.iterator(); iterator.hasNext();) {
					Government government = (Government) iterator.next();
					List sons = governmentDao.selectByParentId(government.getId());
					if (sons == null)
						sons = new ArrayList();
					List son_schools = this.getByGovernmentId(government.getId());
					if (son_schools != null)
						sons.addAll(son_schools);
					List<TreeDTO> children = new ArrayList<TreeDTO>();
					// 循环每一个元素查看是否还有子节点，如果没有则为叶子，有则为父节点
					for (Iterator iterator2 = sons.iterator(); iterator2.hasNext();) {
						Object tempobj = iterator2.next();
						if (tempobj instanceof Government) {
							Government temp = (Government) tempobj;
							if (temp.getChildNum() > 0) {// 这里只是查看了该节点下面是否有部门的子节点,没有查看该节点下是否含有学校
								temp.setIsParent("true");
							} else {
								temp.setIsParent("false");
							}
						 	List<School> list2= getByGovernmentId(temp.getId());
						 	if(list2!=null && list2.size()>0){
						 		temp.setIsParent("true");
						 	}
							children.add(temp.getTreeDTO());
						} else if (tempobj instanceof School) {
							School sch = (School) tempobj;
							if (sch.getChildNum() > 0) {
								sch.setIsParent("true");
							} else {
								sch.setIsParent("false");
							}
							children.add(sch.getTreeDTO());
						}
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
	public List<School> findByGovIdsMap(List item) throws Exception{
		return this.schoolDao.findByGovIdsMap(item);
	}

	@Override
	public List searchByName(String name) throws Exception{
		List<TreeDTO> list = new ArrayList<TreeDTO>();
		if (!Strings.isNullOrEmpty(name)) {
			List<Government> govListParent = governmentDao.selectGovRoot();
			if(govListParent != null && govListParent.size() > 0){
				//进行循环
				for(Iterator iterator0 = govListParent.iterator();iterator0.hasNext();){
					Government government = (Government) iterator0.next();
					List<Government> sons =governmentDao. selectByParentId(government.getId());
					List<TreeDTO> children = new ArrayList<TreeDTO>();
					TreeDTO dto = government.getTreeDTO();
					List<School> schoolList = this.schoolDao.selectSchoolByName(name);
					if (schoolList != null && schoolList.size() > 0) {
						List<School> checkSch = new ArrayList<School>();// 用于排除重复显示学校用
						Map<String, TreeDTO> checkObj = new HashMap<String, TreeDTO>();// 用于排除重复显示学校用
						for (Iterator iterator = schoolList.iterator(); iterator.hasNext();) {
							School sch = (School) iterator.next();
							
							if (sch.getParentId() != null && sch.getParentId() != "") {
								List<School> list2 =getByParentId(sch.getId());
								sch.setChildNum(list2.size());
								checkSch.add(sch);
							} else {
								checkObj.put(sch.getId(), sch.getTreeDTO());
								
								SchoolGrade schoolGrade = new SchoolGrade();
								schoolGrade.setSchoolId(sch.getId());
								List<SchoolGrade> schoolGrades = schoolGradeDao.getListNonePaged(schoolGrade);
								if (sch.getChildNum() > 0) {
									sch.setIsParent("true");
								}else if (schoolGrades.size()>0) {
									sch.setIsParent("true");
								} else {
									sch.setIsParent("false");
								}
								children.add(sch.getTreeDTO());
							}
							
						}
						if (checkSch != null && checkSch.size() > 0) {
							for (School school : checkSch) {
								if (!checkObj.containsKey(school.getParentId())) {
									SchoolGrade schoolGrade = new SchoolGrade();
									schoolGrade.setSchoolId(school.getId());
									List<SchoolGrade> schoolGrades = schoolGradeDao.getListNonePaged(schoolGrade);
									if (school.getChildNum() > 0) {
										school.setIsParent("true");
									}else if (schoolGrades.size()>0) {
										school.setIsParent("true");
									} else {
										school.setIsParent("false");
									}
									if(school.getParentId()==null || "".equals(school.getParentId())){
										children.add(school.getTreeDTO());
									}
								}
							}
						}
						dto.setChildren(children);
						list.add(dto);
					}
				}				
			}else {
				List<Government> govListParent1 = governmentDao.selectGovRoot();
				if(govListParent1 != null && govListParent1.size() > 0){
					//进行循环
					for(Iterator iterator = govListParent1.iterator();iterator.hasNext();){
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
	public School selectFullByPrimaryKey(String id) throws Exception{
		return this.schoolDao.selectFullByPrimaryKey(id);
	}

	@Override
	@Transactional
	@SyncTo(dataType=SyncDataType.School,operateType=SyncOperateType.Add,system={"dudao","oa","kg","wd"})
	public boolean add(School school)  throws Exception{
		if(school.getSerialNumber()!=null && !"".equals(school.getSerialNumber())){
			
		}else {
			String serialNumber=getSerialNumber(school);
			school.setSerialNumber(serialNumber);
		}
		return schoolDao.insert(school);
	}
	
	public String getSerialNumber(School school) throws SQLException{
		String serialNumber="";
		int pinCount = schoolDao.getSchoolJianPinCount(school.getJianpin());
		if(pinCount==0){
			serialNumber = school.getJianpin();
		}else{
			pinCount = schoolDao.getSchoolPinYinCount(school.getPinyin());
			if(pinCount==0){
				serialNumber = school.getPinyin();
			}else{
				serialNumber=school.getPinyin()+ String.format("%02d", pinCount);  
			}
		}
		int count = schoolDao.getSchoolSerialNumberCount(serialNumber,null,school.getId());
		if(count!=0){
			serialNumber = school.getPinyin()+ String.format("%02d", pinCount+ count);
		}
		return serialNumber;
	}
	
	
	@Override
	@Transactional
	@SyncTo(dataType=SyncDataType.School,operateType=SyncOperateType.Update,system={"dudao","oa","kg","wd"})
	public boolean update(School school)  throws Exception{
		// 批量更新学校下面人员的用户名
		School tempSchool = schoolDao.get(school.getId());
		if(!tempSchool.getSerialNumber().equals(school.getSerialNumber())){
			List<User> users= userDao.getUserList(school.getId());
			for (Iterator iterator = users.iterator(); iterator.hasNext();) {
				User user = (User) iterator.next();
				String code = user.getUserName();
				if(code!=null && !"".equals(code)){
					String[] str = code.split("@");
					if(str.length==2){
						String name = str[0]+ "@" + school.getSerialNumber();
						user.setUserName(name);
						userDao.update(user);
					}
				}
			}
		} 
		return schoolDao.update(school);
	}

	@Override
	@Transactional
	@SyncTo(dataType=SyncDataType.School,operateType=SyncOperateType.Delete,system={"dudao","oa","kg","wd"})
	public boolean delete(School sch) throws Exception {
		if(sch==null){
			return false;
		}
		return schoolDao.delete(sch.getId());
	}

	@Override
	public School get(String id)throws Exception  {
		return schoolDao.get(id);
	}

	@Override
	public int getListPagedCount(School school) throws Exception{
		return schoolDao.getListPagedCount(school);
	}

	@Override
	public List<School> getListPaged(School school) throws Exception{
		return schoolDao.getListPaged(school);
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
		Map<String, String> validNameMap = new HashMap<String, String>();
		// 取得字典
		// 学校类型
		Map<Integer, String> systemTypeArr=this.getDictionaryItemArr("SchoolSystemType");
		// 办学类型
		Map<Integer, String> schoolTypeArr=this.getDictionaryItemArr("SchoolType");
		//int serialNumber = schoolDao.getSerialNumber();
		//循环map
		List<School> schoolList = new ArrayList<School>();
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
			//学校名称
			String schoolName = (String)contentList.get(0);
			List<School> schools = new ArrayList<>();
			if(schoolName!=null && !"".equals(schoolName)){
				if(schoolName.length()>20){
					msg+="第"+(i+1)+"行学校名称长度小于20位!<br>";
					flag= false;
				}else{
					schools= schoolDao.selectSchoolName(schoolName);
					if(schools.size()!=0){
						msg+="第"+(i+1)+"行该学校已存在!<br>";
						flag= false;
					}
					if(validNameMap.containsKey(schoolName)){
						msg+="第"+(i+1)+"行结构名称excel中以录入,请检查是否重复!<br>";
						flag= false;
					}else {
						validNameMap.put(schoolName, schoolName);
					}
				}
			}else {
				msg+="第"+(i+1)+"行学校名称为必填!<br>";
				flag= false;
			}
			 
			//机构代码	
			String code = (String)contentList.get(1);
			String regEx = "^\\d+$";
			if(code!=null && !"".equals(code)){
				if(code.length()>20){
					msg+="第"+(i+1)+"行该机构号码长度小于20位!<br>";
					flag= false;
				}
				if(code.matches(regEx) ){
					int count=schoolDao.getListCodeCount(code);
					if(count>0){
						msg+="第"+(i+1)+"行该机构代码已存在!<br>";
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
				
			}else {
				msg+="第"+(i+1)+"行机构代码为必填!<br>";
				flag= false;
			}
			//学制=>学校类型
			String systemType = (String)contentList.get(2);
			int systemTypeKey=-1;
			if(!Strings.isNullOrEmpty(systemType)){ 
				 if(systemTypeArr.containsValue(systemType)){
					 systemTypeKey = this.getKeyByValue(systemTypeArr, systemType);
				}else{
					msg+="第"+(i+1)+"行学校类型不正确,请重新输入!<br>";
					flag = false;
				}
			}
			//所诉教育局	
			String governmentName = (String)contentList.get(3);
			String governmentId="";
			if(governmentName!=null && !"".equals(governmentName)){
				List<Government> list = governmentDao.getGovByName(governmentName);
				if(list== null || list.size()==0){
					msg+="第"+(i+1)+"行所属机构不存在,请重新输入!<br>";
					flag= false;
				}else {
					// 若教育局存在取得教育局的id
					governmentId = list.get(0).getId();
				}
			}else{
				msg+="第"+(i+1)+"行所属机构为必填!<br>";
				flag= false;
			}
				
			//上级学校	 
			String parentName = (String)contentList.get(4);
			String parentId="";
			if(parentName!=null && !"".equals(parentName)){
				schools= schoolDao.selectSchoolName(parentName);
				if( schools.size()==0){
					msg+="第"+(i+1)+"行该上级学校不存在,请重新输入!<br>";
					flag= false;
				}else {
					parentId = schools.get(0).getId();
				}
			}
			//办学类型	  
			String schoolType = (String)contentList.get(5);
			int schoolTypeKey=-1;
			if(!Strings.isNullOrEmpty(schoolType)){
				 if(schoolTypeArr.containsValue(schoolType)){
					 schoolTypeKey = this.getKeyByValue(schoolTypeArr, schoolType);
				}else{
					msg+="第"+(i+1)+"行办学类型式不正确,请重新输入!<br>";
					flag = false;
				}
			}
			//联系电话	
			String tel = (String)contentList.get(6);
			if(!tel.matches(regEx) ){
				msg+="第"+(i+1)+"行联系电话不正确,请重新输入!<br>";
				flag= false;
			} 
			//所在地 => 市级单位
			String areaName = (String)contentList.get(7);
			String areaId="";
			if(areaName!=null && !"".equals(areaName)){
				List<Area> areas= areaDao.selectByName(areaName);
				if(areas!=null && areas.size()>0){
					areaId = areas.get(0).getId();
				}else {
					msg+="第"+(i+1)+"行所在地域不存在,请重新输入!<br>";
					flag= false;
				}
			}
			/*int number=0;
			if(serialNumber<1000){
				DecimalFormat df1 = new DecimalFormat("1000");
				number= Integer.parseInt(df1.format(serialNumber));
			}else {
				DecimalFormat df1 = new DecimalFormat("####");
				number= Integer.parseInt(df1.format(serialNumber));
			}*/ 
			School tempSchool= new School();
			String id=ByyStringUtil.getRandomUUID().replace("-", "");
			tempSchool.setId(id);
			listForIds.add(id);
			tempSchool.setName(schoolName);
			tempSchool.setCode(code);
			tempSchool.setSystemType(systemTypeKey);
			tempSchool.setGovernmentId(governmentId);
			tempSchool.setParentId(parentId);
			tempSchool.setSchoolType(schoolTypeKey);
			tempSchool.setTel(tel);
			tempSchool.setAreaId(areaId);
			tempSchool.setPinyin(PinyinUtil.converterToSpell(tempSchool.getName()));//全拼
			tempSchool.setJianpin(PinyinUtil.converterToAllFirstSpell(tempSchool.getName()));//简拼
			String serialNumber=getSerialNumber(tempSchool);
			tempSchool.setSerialNumber(serialNumber);
			schoolList.add(tempSchool);
			
		}
		if(flag){
			if(schoolList!=null && schoolList.size()!=0){
				for (Iterator iterator = schoolList.iterator(); iterator.hasNext();) {
					School school = (School) iterator.next();
					schoolDao.insert(school);
				}
			} else {
				result = "模板未输入信息,请填写学校资料!";
			}
		}else {
			result = msg;
		}
		
		return result;
	}

	@Override
	public int getSchoolJianPinCount(String jianpin) throws SQLException {
		return schoolDao.getSchoolJianPinCount(jianpin);
	}

	@Override
	public int getSchoolPinYinCount(String pinyin) throws SQLException {
		return schoolDao.getSchoolPinYinCount(pinyin);
	}

	@Override
	public int getSchoolSerialNumberCount(String serialNumber,String code,String id) throws SQLException {
		return schoolDao.getSchoolSerialNumberCount(serialNumber,code,id);
	}

	@Override
	public int validatName(String name, String id) throws SQLException {
		return schoolDao.validatName(name, id);
	}

}

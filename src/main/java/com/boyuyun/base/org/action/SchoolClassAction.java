package com.boyuyun.base.org.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.boyuyun.base.org.biz.SchoolBiz;
import com.boyuyun.base.org.biz.SchoolClassBiz;
import com.boyuyun.base.org.biz.SchoolGradeBiz;
import com.boyuyun.base.org.entity.School;
import com.boyuyun.base.org.entity.SchoolClass;
import com.boyuyun.base.org.entity.SchoolGrade;
import com.boyuyun.base.util.base.BaseAction;
import com.boyuyun.base.util.consts.Stage;
import com.boyuyun.common.annotation.LogThisOperate;
import com.boyuyun.common.autolog.OperateType;
import com.boyuyun.common.json.ByyJsonUtil;
import com.boyuyun.common.util.ByyDateUtil;
import com.boyuyun.common.util.ByyStringUtil;
import com.dhcc.common.json.JsonUtil;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 班级管理
 * 
 * @author zhy
 */
@Controller
// ("/school/class")
public class SchoolClassAction extends BaseAction implements ModelDriven<SchoolClass>{
	private SchoolClass schoolClass = new SchoolClass();
	@Resource
	private SchoolClassBiz schoolClassService;
	@Resource
	private SchoolGradeBiz schoolGradeService;
	@Resource
	private SchoolBiz schoolService;

	public String getGradeList() throws Exception {
		String result = "";
		SchoolGrade grade =  new SchoolGrade ();
		List<SchoolGrade> list = schoolGradeService.getListNonePaged(grade);
		result = ByyJsonUtil.serialize(list);
		this.print(result);
		return null;
	}

	public String getListNonePaged() throws Exception{
		List<SchoolClass> list = schoolClassService.getListPaged(schoolClass);
		String result=JsonUtil.toJSONStringByFastjson(list);
		this.print(result);
		return null;
		
	}
	public String getList() throws Exception {
		List<SchoolClass> list = schoolClassService.getListPaged(schoolClass);
		int count = schoolClassService.getListPagedCount(schoolClass);
		String result=ByyJsonUtil.serialize(count,list);
		this.print(result);
		return null;
	}

	@LogThisOperate(module="班级管理",operateType=OperateType.新增或修改)
	public String save() throws Exception {
		String result = this.getFailJson("操作失败");
		SchoolGrade schoolGrade = new SchoolGrade();
		schoolGrade= schoolGradeService.get(schoolClass.getGradeId());
		Integer stageId = schoolGrade.getSysGradeStage();
		schoolClass.setStageId(stageId.toString());
		schoolClass.setSchoolId(schoolGrade.getSchoolId());
		if (schoolClass.getId() != null && schoolClass.getId().trim().length() > 0) {
			schoolClassService.update(schoolClass);
			result = this.getSuccessJson("修改成功");
		} else {
			String id = ByyStringUtil.getRandomUUID();
			schoolClass.setId(id);
			schoolClass.setSortNum(schoolClassService.getMaxSortNum());
			schoolClassService.add(schoolClass);
			result = this.getSuccessJson("保存成功");
		}
		this.print(result);
		return null;
	}

	public String getBean() throws Exception {
		String id = schoolClass.getId();
		SchoolClass	schoolClass = (SchoolClass) schoolClassService.get(id);
		String result = ByyJsonUtil.serialize(schoolClass, null, ByyDateUtil.YYYY_MM_DD);
		this.print(result);
		return null;
	}

	/**
	 * 单个删除和多个删除共用
	 * @return
	 */
	@LogThisOperate(module="班级管理",operateType=OperateType.删除)
	public String delete() throws Exception {
		String id = request.getParameter("id");
		String result = "";
		String[] ids = id.split(",");
		List<SchoolClass> schoolClasss = new ArrayList<SchoolClass>();
		List IDSToDelete = new ArrayList();
		for (String string : ids) {
			SchoolClass schoolClass = (SchoolClass) schoolClassService.get(string);
			schoolClasss.add(schoolClass);
			IDSToDelete.add(IDSToDelete);
		}
		if (schoolClasss.size() > 0) {
			this.setBatchListId(IDSToDelete);
			schoolClassService.deleteAll(schoolClasss);
			result = this.getSuccessJson("删除成功");
		} else {
			result = this.getFailJson("删除失败");
		}
		this.print(result);
		return null;
	}

	/**
	 * 跳转列表页面
	 * @return
	 */
	public String toList() throws Exception {
		SchoolGrade grade = this.schoolGradeService.get(schoolClass.getGradeId());
		School school = schoolService.get(grade.getSchoolId());
		request.setAttribute("school", school);
		request.setAttribute("grade", grade);
		return "list";
	}

	public String index() {
		return "index";
	}

	/**
	 * 跳转新增页面
	 * @return
	 */
	public String toAdd() {
		return "view";
	}

	/**
	 * 跳转查看页面
	 * @return
	 */
	public String toView() {
		return "detail";
	}

	/**
	 * 跳转修改页面
	 * @return
	 */
	public String toEdit() {
		return "view";
	}

	@Override
	public SchoolClass getModel() {
		return (SchoolClass) initPage(schoolClass);
	}
	
	/**
	 * @Description 根据学校和学段,年级取得班级
	 * @author jms
	 * @return
	 * @throws Exception
	 */
	public String getSchoolClass() throws Exception{
		String result = "";
		try {
			String schoolId = request.getParameter("schoolId");
			String gradeId= request.getParameter("gradeId");
			String stageName= request.getParameter("stageName");
			Stage stage = null;
			if(stageName!=null && !"".equals(stageName)){
				stage = Stage.valueOf(stageName);
			}
			List<SchoolClass> list = schoolClassService.getSchoolClassListBy(schoolId,stage,gradeId);
			result = ByyJsonUtil.serialize(list);
		} catch (Exception e) {
			e.printStackTrace();
			result = this.getFailJson("系统出错，请稍后重试");
		} 
		this.print(result);
		return null;
	}

}

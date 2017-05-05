package com.boyuyun.base.org.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.boyuyun.base.org.biz.SchoolBiz;
import com.boyuyun.base.org.biz.SchoolGradeBiz;
import com.boyuyun.base.org.entity.School;
import com.boyuyun.base.org.entity.SchoolGrade;
import com.boyuyun.base.org.util.TreeDTO;
import com.boyuyun.base.util.base.BaseAction;
import com.boyuyun.base.util.consts.Stage;
import com.boyuyun.common.annotation.LogThisOperate;
import com.boyuyun.common.autolog.OperateType;
import com.boyuyun.common.json.ByyJsonUtil;
import com.boyuyun.common.util.ByyDateUtil;
import com.boyuyun.common.util.ByyStringUtil;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 年级管理
 * @author zhy
 */
@Controller
public class SchoolGradeAction extends BaseAction implements ModelDriven<SchoolGrade>{
	@Resource
	private SchoolGradeBiz schoolGradeService;

	@Resource
	private SchoolBiz schoolService;

	private SchoolGrade schoolGrade = new SchoolGrade();

	public String getList()  throws Exception{
		List<SchoolGrade> list = schoolGradeService.getListPaged(schoolGrade);
		int count = schoolGradeService.getListPagedCount(schoolGrade);
		String result=ByyJsonUtil.serialize(count,list);
		this.print(result);
		return null;
	}

	/**
	 * 新增修改共用 路径save
	 * 
	 * @return
	 */
	@LogThisOperate(module="年级管理",operateType=OperateType.新增或修改)
	public String save() throws Exception{
		String result = this.getSuccessJson("保存成功");
		String schoolId = request.getParameter("schoolId");
		if (null == schoolGrade.getSchoolId() || schoolGrade.getSchoolId().trim().length() == 0) {
			schoolGrade.setSchoolId(schoolId);
		}
		if (schoolGrade.getId() != null && schoolGrade.getId().trim().length() > 0) {
			schoolGradeService.update(schoolGrade);
			result = this.getSuccessJson("修改成功");
		} else {
			String id = ByyStringUtil.getRandomUUID();
			schoolGrade.setId(id);
			schoolGrade.setSortNum(schoolGradeService.getMaxSortNum());
			schoolGradeService.add(schoolGrade);
		}
		this.print(result);
		return null;
	}

	public String getBean()  throws Exception{
		String result = "";
		if (schoolGrade.getId() != null && schoolGrade.getId().trim().length() > 0) {
			schoolGrade = (SchoolGrade) schoolGradeService.get(schoolGrade.getId());
		}
		result = ByyJsonUtil.serialize(schoolGrade, null, ByyDateUtil.YYYY_MM_DD);
		this.print(result);
		return null;
	}

	/**
	 * 单个删除和多个删除共用
	 * 
	 * @return
	 */
	@LogThisOperate(module="年级管理",operateType=OperateType.删除)
	public String delete()  throws Exception{
		String result = "";
		String[] ids = schoolGrade.getId().split(",");
		List<SchoolGrade> schoolGrades = new ArrayList<SchoolGrade>();
		List IDSToDelete = new ArrayList();
		for (String string : ids) {
			if(schoolGradeService.getListByOrgClassCount(string)){
			schoolGrade = (SchoolGrade) schoolGradeService.get(string);
			schoolGrades.add(schoolGrade);
			IDSToDelete.add(string);
			}else{
				result = this.getFailJson("年级下已有班级,无法删除!");
				this.print(result);
				return null;
			}
		}
		if (schoolGrades.size() > 0) {
			this.setBatchListId(IDSToDelete);
			schoolGradeService.deleteAll(schoolGrades);
			result = this.getSuccessJson("删除成功");
		} else {
			result = this.getFailJson("删除失败");
		}
		this.print(result);
		return null;
	}

	public String getTree() throws Exception {
		String result = "";
		String type = request.getParameter("type");
		String id = schoolGrade.getId();
		List<TreeDTO> list = schoolService.getDynamicGovernmentSchoolAndGradeTree(type, id);
		result = ByyJsonUtil.serialize(list);
		this.print(result);
		return null;
	}

	/**
	 * 跳转列表页面
	 * 
	 * @return
	 */
	public String toList()  throws Exception{
		School school = schoolService.get(schoolGrade.getSchoolId());
		request.setAttribute("school", school);
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

	public SchoolGrade getModel() {
		return (SchoolGrade) initPage(schoolGrade);
	}
	

	/**
	 * @Description 根据学校和学段取得年级
	 * @author jms
	 * @return
	 * @throws Exception
	 */
	public String getSchoolGrade() throws Exception{
		String result = "";
		try {
			String schoolId = request.getParameter("schoolId");
			String stageName= request.getParameter("stageName");
			Stage stage = null;
			if(stageName!=null && !"".equals(stageName)){
				stage = Stage.valueOf(stageName);
			}
			List<SchoolGrade> list = schoolGradeService.getSchoolGradeBySchoolAndStage(schoolId,stage);
			result = ByyJsonUtil.serialize(list);
		} catch (Exception e) {
			e.printStackTrace();
			result = this.getFailJson("系统出错，请稍后重试");
		} 
		this.print(result);
		return null;
	}
}

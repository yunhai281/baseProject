package com.boyuyun.base.course.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.boyuyun.base.course.biz.CourseBiz;
import com.boyuyun.base.course.entity.Course;
import com.boyuyun.base.util.base.BaseAction;
import com.boyuyun.common.annotation.LogThisOperate;
import com.boyuyun.common.json.ByyJsonUtil;
import com.boyuyun.common.util.ByyStringUtil;
import com.dhcc.common.json.JsonUtil;
import com.google.common.base.Strings;
import com.opensymphony.xwork2.ModelDriven;
import com.boyuyun.common.autolog.OperateType;

@Controller
public class CourseAction extends BaseAction implements ModelDriven<Course>{
	private Course course = new  Course();
	@Override
	public Course getModel() {
		return (Course) this.initPage(course);
	} 
	@Resource
	private CourseBiz courseService;
	
	/**
	 * 跳转详情页面
	 * @return
	 */
	public String detail(){
		return "detail";
	}
	/**
	 * 跳转到列表页
	 * @return
	 */
	public String toList(){
		return "toList";
	}
	/**
	 * 跳转到新增编辑页
	 * @return
	 */
	public String toView(){
		return "toView";
	}
	/**
	 * 保存编辑
	 * @param course
	 * @return
	 */
	@LogThisOperate(module="学科设置",operateType=OperateType.新增或修改)
	public String save()throws Exception{
		String result =  this.getFailJson("保存失败!");
		if(Strings.isNullOrEmpty(course.getId())){
			//新增
			course.setId(ByyStringUtil.getRandomUUID());
			course.setSchoolId(null);
			if(course.getSortNum()>-1){
			}else{
				course.setSortNum(courseService.getMaxSortNum());
			}
			this.courseService.add(course);
			result = this.getSuccessJson("保存成功!");
		}else {
			//编辑
			this.courseService.update(course);
			result = this.getSuccessJson("保存成功!");
		}
		this.print(result);
		return null;
	}

	/**
	 * 获取数据列表分页
	 * @param course
	 * @param page
	 * @param pagesize
	 * @return
	 */
	public String getList()throws Exception{
		int count = courseService.getListPagedCount(course);
		List list = courseService.getListPaged(course);
		String result=JsonUtil.toJSONStringByFastjson(count,list);
		this.print(result);
		return null;
	} 
	/**
	 * 删除,单一删除,多个删除共用
	 * @return
	 */
	@LogThisOperate(module="学科设置",operateType=OperateType.删除)
	public String delete()throws Exception{
		String id = course.getId();
		String json = this.getFailJson("删除失败!");
		List IDSToDelete = new ArrayList();
		if(!Strings.isNullOrEmpty(id)&&id.indexOf(",")>0){
			String[] ids = id.split(",");
			List list = new ArrayList();
			for (int i = 0; i < ids.length; i++) {
				if(Strings.isNullOrEmpty(ids[i]))continue;
				Course course =new Course();
				course.setId(ids[i]);
				IDSToDelete.add(ids[i]);
				list.add(course);
			}
			this.setBatchListId(IDSToDelete);
			this.courseService.deleteAll(list);
			json = this.getSuccessJson("删除成功!");
		}else if(!Strings.isNullOrEmpty(id)){
			Course course = new Course();
			course.setId(id);
			this.courseService.delete(course);
			json = this.getSuccessJson("删除成功!");
		}
		this.print(json);
		return null;
	}
	
	/**
	 * 获取一个实体的方法，路径统一为get
	 * @return
	 */
	public String getBean()throws Exception{
		String id = course.getId();
		Course course = courseService.get(id);
		String json = "";
		if(course!=null){
			json = ByyJsonUtil.serialize(course);
		}
		this.print(json);
		return null;
	}
	

	public String getAllList()throws Exception {
		String result = "";
		List<Course> pageInfo = courseService.getListNonePaged(new Course());
		if (pageInfo == null) {
			pageInfo = new ArrayList<Course>();
		}
		result = ByyJsonUtil.serialize(pageInfo);
		this.print(result);
		return null;
	}
}

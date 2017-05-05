package com.boyuyun.base.sys.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.boyuyun.base.sys.biz.GradeBiz;
import com.boyuyun.base.sys.entity.Grade;
import com.boyuyun.base.util.base.BaseAction;
import com.boyuyun.common.annotation.LogThisOperate;
import com.boyuyun.common.autolog.OperateType;
import com.boyuyun.common.json.ByyJsonUtil;
import com.boyuyun.common.util.ByyStringUtil;
import com.google.common.base.Strings;
import com.opensymphony.xwork2.ModelDriven;
@Controller  
public class SysGradeAction extends BaseAction implements ModelDriven<Grade>{
	
	private Grade grade = new Grade();
	
	@Override
	public Grade getModel() {
		return (Grade)this.initPage(grade);
	}	
	
	@Resource
	private GradeBiz gradeService;
	/**
	 * 获取一个实体的方法，路径统一为get
	 * @return
	 */
	public String getBean()throws Exception{
		String id = request.getParameter("id");
		Grade grade = gradeService.get(id);
		String json = "";
		if(grade!=null){
			json = ByyJsonUtil.serialize(grade);
		}
		this.print(json);
		return null;
	}
	/**
	 * 获取列表页面json或树形结果json的方法，路径统一为getList
	 * @return
	 */
	public String getList()throws Exception{
		List<Grade> pageInfo = gradeService.getListPaged(grade);
		int count = gradeService.getListPagedCount(grade);
		String result = ByyJsonUtil.serialize(count,pageInfo);
		this.print(result);
		return null;
	}
	public String getGradeList()throws Exception{
		String result = "";
		List<Grade> list = gradeService.getListNonePaged(grade);
		result = ByyJsonUtil.serialize(list);
		this.print(result);	
		return null;
	}
	
	/**
	 * 新增修改共用 路径save
	 * @return
	 */
	@LogThisOperate(module="年级设置",operateType=OperateType.新增或修改)
	public String save()throws Exception{
		String result = this.getFailJson("保存失败!");
		if(Strings.isNullOrEmpty(grade.getId())){
			//新增
			grade.setId(ByyStringUtil.getRandomUUID());
			//排序最小从1开始，
			if(grade.getSortNum()==0){
				int maxSortNum = gradeService.getMaxSortNum();
				grade.setSortNum(maxSortNum+1);
			}
			this.gradeService.add(grade);
			result = this.getSuccessJson("保存成功!");
		}else{
			//修改
			this.gradeService.update(grade);
			result = this.getSuccessJson("保存成功!");
		}
		this.print(result);		
		return null;
	}
	/**
	 * 单个删除和多个删除共用
	 * @return
	 */
	@LogThisOperate(module="年级设置",operateType=OperateType.删除)
	public String delete()throws Exception{
		String id = request.getParameter("id");
		String json = this.getFailJson("删除失败!");
		String[] ids = null;
		List IDSToDelete = new ArrayList();
		if(!Strings.isNullOrEmpty(id)&&id.indexOf(",")>-1){
			ids = id.split(",");
		}
		if(ids!=null&&ids.length>0){
			List todel = new ArrayList();
			for (int i = 0; i < ids.length; i++) {
				if(Strings.isNullOrEmpty(ids[i]))continue;
				if(gradeService.getListByOrgGradeCount(ids[i])){
					Grade grade = new Grade();
					grade.setId(ids[i]);
					IDSToDelete.add(ids[i]);
					todel.add(grade);
				}else{
					json = this.getFailJson("该年级已经被使用,无法删除!");
					this.print(json);
					return null;
				}
			}
			this.setBatchListId(IDSToDelete);
			this.gradeService.deleteAll(todel);
			json = this.getSuccessJson("删除成功!");
		}else if(!Strings.isNullOrEmpty(id)){
			if(gradeService.getListByOrgGradeCount(id)){			
			grade.setId(id);
			this.gradeService.delete(grade);
			json = this.getSuccessJson("删除成功!");
			}else{
				json = this.getFailJson("该年级已经被使用,无法删除!");
				this.print(json);
				return null;
			}			
		}
		this.print(json);
		return null;
	}
	

	/**
	 *列表上的上移和下移按钮
	 */
	@LogThisOperate(module="年级设置",operateType=OperateType.更改排序)
	public String  move()throws Exception{
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		String result=this.getFailJson("移动失败");
		int i=gradeService.moveGradeSortNum(id, type);
		if(i==0){
			result=this.getFailJson("移动失败");
		}else if(i==2){
			result=this.getFailJson("已经是第一条记录，移动失败");
		}else if(i==1){
			result=this.getSuccessJson("上移成功");
		}else if(i==-2){
			result=this.getFailJson("已经是最后一条记录，移动失败");
		}else if(i==-1){
			result=this.getSuccessJson("下移成功");
		}
		this.print(result);
		return null;
	}
	/**
	 * 跳转列表页面
	 * @return
	 */
	public String toList(){
		return "toList";
	}
	/**
	 * 跳转新增页面
	 * @return
	 */
	public String toView(){
		return "toView";
	}
	/**
	 * 跳转修改页面
	 * @return
	 */
	public String toEdit(){
		return "toEdit";
	}
	 
	/**
	 * @Description 取得最大的年级编号 
	 * @author jms
	 * @return
	 * @throws Exception
	 */
	public String getMaxSortNum() throws Exception{
		int count= gradeService.getMaxSortNum();
		this.print(String.valueOf(count+1));
		return null;
	}
	
	public String getValidateSortNum() throws Exception{
		String sortnum = request.getParameter("sortNum");
		String id = request.getParameter("id");
		int count= gradeService.getSortNum(sortnum,id);
		if(count!=0){
			this.print(false);
		}else {
			this.print(true);
		}
		return null;
	}
	
	/**
	 * @Description 
	 * @author jms
	 * @return
	 * @throws Exception
	 */
	public String updateSortNum() throws Exception{
		String result = this.getFailJson("保存失败!");
		String sortnum = request.getParameter("sort");
		String id = request.getParameter("id");
		Integer sort= Integer.valueOf(sortnum);
		if(id!=null && !"".equals(id)){
			int count= gradeService.getSortNum(sortnum,id);
			if(count!=0){
				result = this.getFailJson("排序重复!");
			}else {
				grade =gradeService.get(id);
				grade.setSortNum(sort);
				gradeService.update(grade);
				result = this.getSuccessJson("保存成功!");
			}
		} 
		this.print(result);	
		return null;
	}
}

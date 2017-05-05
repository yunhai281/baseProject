package com.boyuyun.base.user.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Controller;
import org.apache.commons.lang3.StringUtils;
import com.boyuyun.base.course.biz.CourseBiz;
import com.boyuyun.base.course.biz.TeacherCourseBiz;
import com.boyuyun.base.course.entity.TeacherCourse;
import com.boyuyun.base.org.biz.SchoolBiz;
import com.boyuyun.base.sys.biz.DictionaryItemBiz;
import com.boyuyun.base.user.biz.TeacherBiz;
import com.boyuyun.base.user.biz.UserBiz;
import com.boyuyun.base.user.entity.Teacher;
import com.boyuyun.base.user.entity.User;
import com.boyuyun.base.util.ConstantUtil;
import com.boyuyun.base.util.base.BaseAction;
import com.boyuyun.base.util.consts.UserType;
import com.boyuyun.common.annotation.LogThisOperate;
import com.boyuyun.common.autolog.OperateType;
import com.boyuyun.common.json.ByyJsonUtil;
import com.boyuyun.common.util.ByyDateUtil;
import com.boyuyun.common.util.ByyStringUtil;
import com.boyuyun.common.util.ExcelReader;
import com.dhcc.common.util.StringUtil;
import com.opensymphony.xwork2.ModelDriven;

@Controller
public class TeacherAction extends BaseAction implements ModelDriven<Teacher> {

	private Teacher teacher = new Teacher();
	@Resource
	private TeacherCourseBiz teacherCourseService;
	@Resource
	private TeacherBiz teacherService;
	@Resource
	private DictionaryItemBiz dictionaryItemService;
	@Resource
	private CourseBiz courseBiz;
	@Resource
	private SchoolBiz schoolBiz;
	@Resource
	private UserBiz userBiz;
	@Override
	public Teacher getModel() {
		return (Teacher) initPage(teacher);
	}
	public String toAdd() {
		return "toView";
	}

	public String toEdit() {
		return "toView";
	}

	public String toList() {
		return "toList";
	}

	public String toView() {
		return "toDetail";
	}

	public String toimport(){
		return "toimport";
	} 
	
	/**
	 * 跳转到选择教师界面
	 * @author jms
	 * @since  
	 * @return
	 */ 
	public String toSelect(){
		return "userSelect";
	}
	
	/**
	 * @Title: getList
	 * @author: wsc
	 * @Description:
	 * @throws @time:2017年3月2日
	 */
	public String getList() throws Exception{
		int count = teacherService.getListPagedCount(teacher);
		List<Teacher> list = teacherService.getListPaged(teacher);
		String result=ByyJsonUtil.serialize(count,list);
		this.print(result);
		return null;
	}
	@LogThisOperate(module="教师管理",operateType=OperateType.新增或修改)
	public String save() throws Exception{
		String result = this.getFailJson("保存失败");
		//设置用户类型为教师
		Integer ordinal = UserType.教师.ordinal();
		teacher.setUserType(ordinal.toString());
		String md5Pwd = StringUtil.encryptMd5(teacher.getPwd());
		teacher.setPwd(md5Pwd);
		String teacherId = "";
		if(StringUtils.isEmpty(teacher.getId())){
			teacherId = ByyStringUtil.getRandomUUID();
		}else{
			teacherId = teacher.getId();
		}
		String courseList = request.getParameter("courseList");
		if (courseList != null && !"".equals(courseList)) {
			List<TeacherCourse> teacherCourses = new ArrayList<TeacherCourse>();
			for (int i = 0; i < courseList.split(",").length; i++) {
				String courseId = courseList.split(",")[i];
				TeacherCourse teacherCourse = new TeacherCourse();
				teacherCourse.setId(ByyStringUtil.getRandomUUID());
				teacherCourse.setCourseId(courseId);
				teacherCourse.setTeacherId(teacherId);
				teacherCourses.add(teacherCourse);
			}
			teacher.setCourses(teacherCourses);
		}
		if (teacher.getId() != null && teacher.getId().trim().length() > 0) {
			User userTemp = (User) teacherService.get(teacher.getId());
			teacher.setPwd(userTemp.getPwd());
			teacherService.update(teacher);
			result =  this.getSuccessJson("修改成功");
		}else{
			teacher.setId(teacherId);
			teacherService.add(teacher);
			result =  this.getSuccessJson("保存成功");
		}
		this.print(result);
		return null;
	}
	
	public String getBean() throws Exception{
		String id = this.teacher.getId();
		Teacher teacher = (Teacher) teacherService.get(id);
		String result = ByyJsonUtil.serialize(teacher, null, ByyDateUtil.YYYY_MM_DD);
		this.print(result);
		return null;
	}
	@LogThisOperate(module="教师管理",operateType=OperateType.删除)	
	public String delete() throws Exception{
		String id = teacher.getId();
		String result = "";
		Teacher teacher = null;
		String[] ids = id.split(",");
		List<Teacher> teachers = new ArrayList<Teacher>();
		List IDSToDelete = new ArrayList();
		for (String string : ids) {
			// 同时删除教师任教
			teacherCourseService.deleteByTeacher(string);
			teacher = (Teacher) teacherService.get(string);
			teachers.add(teacher);
			IDSToDelete.add(string);
		}
		if (teachers.size() > 0) {
			this.setBatchListId(IDSToDelete);
			teacherService.deleteAll(teachers);
			result = this.getSuccessJson("删除成功");
		} else {
			result = this.getFailJson("删除失败");
		}
		this.print(result);
		return null;
	} 
	
	/***
	 * 导出模版
	 * @return
	 */
	public String exportTeacherTemplateExcel() throws Exception{
		response.setCharacterEncoding("utf-8");
		String strDirPath = request.getSession().getServletContext().getRealPath("/");
    	String path =  strDirPath+"/WEB-INF/page/admin/user/teacher/教师基本信息模板.xls";
    	File file = new File(path);
    	if(file.exists()){
    		// 以流的形式下载文件。
			String fileName = "教师基本信息模板";
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8")+".xls");
			InputStream fis = new FileInputStream(file);
            POIFSFileSystem fs = new POIFSFileSystem(fis);
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);           
            ServletOutputStream out =  response.getOutputStream();
            wb.write(out);
            out.flush();
            out.close(); 
    	}
        
		return null;
	}
	
	
	/****
	 * 导出教师的列表到EXCEL中,GET方式请求进来.
	 * @return
	 */
	public String exportTeacherExcel() throws Exception{
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/vnd.ms-excel"); 
		HSSFWorkbook wb = new HSSFWorkbook();
        try {
        	String strDirPath = request.getSession().getServletContext().getRealPath("/");
        	String path =  strDirPath+"/WEB-INF/page/admin/user/teacher/教师基本信息模板.xls";
			String fileName = "教师信息";
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-disposition","attachment;filename=" + URLEncoder.encode(fileName, "utf-8")+".xls");
			File file = new File(path);
			wb = new HSSFWorkbook(new FileInputStream(file));
        	HSSFSheet sheet = wb.getSheetAt(0);
        	HSSFRow tempRow = sheet.createRow(1);
        	HSSFCell tempCell = tempRow.createCell(0);
        	
        	List<Teacher> teacherList= teacherService.getTeacherList(teacher);
        	
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for(int i=0;i<teacherList.size();i++){
            	Teacher tempTeacher = (Teacher)teacherList.get(i);
            	tempRow = sheet.createRow(i+1);
            	tempCell = tempRow.createCell(0);
            	tempCell.setCellValue((i+1)+"");
            	//学校
            	tempCell = tempRow.createCell(1);
            	tempCell.setCellValue(tempTeacher.getSchoolName());
            	// 工号
            	tempCell = tempRow.createCell(2);
            	tempCell.setCellValue(tempTeacher.getTeacherNo());
            	// 姓名
            	tempCell = tempRow.createCell(3);
        		tempCell.setCellValue(tempTeacher.getRealName());
        		// 性别
        		tempCell = tempRow.createCell(4);
        		tempCell.setCellValue(tempTeacher.getSexName());
        		// 岗位
        		tempCell = tempRow.createCell(5);
        		tempCell.setCellValue(tempTeacher.getPostTypeName());
        		// 学科
        		tempCell = tempRow.createCell(6);
        		tempCell.setCellValue("");
        		//在职状态
        		tempCell = tempRow.createCell(7);
        		tempCell.setCellValue(tempTeacher.getWorkStatusName());
        		// 手机号码
        		tempCell = tempRow.createCell(8);
        		tempCell.setCellValue(tempTeacher.getMobile());
        		// 证件号
        		tempCell = tempRow.createCell(9);
        		tempCell.setCellValue(tempTeacher.getCertificateNo());
        		// 出生日期
        		tempCell = tempRow.createCell(10);
        		if(tempTeacher.getBirthday()!=null && !"".equals(tempTeacher.getBirthday())){
            		tempCell.setCellValue(sdf.format(tempTeacher.getBirthday()));
        		}else {
        			tempCell.setCellValue("");
				}
        		// 民族
        		tempCell = tempRow.createCell(11);
        		tempCell.setCellValue(tempTeacher.getNationName());
        		// 政治面貌
        		tempCell = tempRow.createCell(12);
        		tempCell.setCellValue(tempTeacher.getPoliticsName());
        		// 毕业学校
        		tempCell = tempRow.createCell(13);
        		tempCell.setCellValue(tempTeacher.getGraduateSchool());
        		// 学历学位
        		tempCell = tempRow.createCell(14);
        		tempCell.setCellValue(tempTeacher.getDgreeName());
        		//加入本校时间
        		tempCell = tempRow.createCell(15);
        		if(tempTeacher.getHireDate()!=null && !"".equals(tempTeacher.getHireDate())){
            		tempCell.setCellValue(sdf.format(tempTeacher.getHireDate()));
        		} 
            }
        	
			ServletOutputStream out =  response.getOutputStream();
			wb.write(out);
			out.close();  
		}catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @Description 
	 * @author jms
	 * @return
	 * @throws Exception
	 */
	@LogThisOperate(module="教师管理",operateType=OperateType.导入)
	public String importTeacherExcel() throws Exception{
		response.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		String result = this.getFailJson("导入失败!");
		String filename = request.getParameter("filename");//ParamUtil.getParameter(request, "uploadFilePath");
		String filePath = request.getParameter("filePath");
		
		try {  
			//开始处理文件导入，判断文件类型和存在
			if(null != filePath && !"".equals(filePath)){
				//判断文件后缀是不是xls或者xlsx
				if(filePath.endsWith(".xls") || filePath.endsWith(".xlsx")){
					String path= ConstantUtil.UPLOAD_FOLDER + filePath;
					File sourceFile = new File(path);
					if(sourceFile.exists()){
						String fileName = sourceFile.getName(); 
					}
					// 写入模板 
					File dirFile = new File(path);  
					//InputStream fis =null;//HadoopFileManager.getLocalOrHadoopFileInputStream(uploadFilePath, request.getSession().getServletContext().getRealPath(uploadFilePath));
					if(dirFile.exists()){
						//存在该文件.通过POI进行读取数据，然后保存到数据库中。 
						ExcelReader excelReader = new ExcelReader();
						Map<Integer, List> contentMap= excelReader.readExcelContent(new FileInputStream(path));
						List listForIds = new ArrayList();//用于存放生成的id
						String mgs=teacherService.insertImportTeacherExcel(contentMap,listForIds);
						if(mgs!=null && !"".equals(mgs)){
							result = this.getFailJson(mgs);
						}else {
							this.setBatchListId(listForIds);
							result = this.getSuccessJson("导入成功!");
						}
					}else{
						result = this.getFailJson("文件未上传成功!");
					}
				}else{
					result = this.getFailJson("文件后缀不符合.xls,.xlsx的格式!");
				}
			}else{
				result = this.getFailJson("文件路径获取失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		this.print(result);
		return null;
	}
	
	/**
	 * @Description 获取教师任教的课程
	 * @author jms
	 * @return
	 * @throws Exception
	 */
	public String getListByTeacher()throws Exception{
		String result = null;
		String id= request.getParameter("id");
		List<TeacherCourse> list = teacherCourseService.getTeacherCourse(id);
		result = ByyJsonUtil.serialize(list,new String[]{"child","parent"});
		this.print(result);
		return null;
	}

}

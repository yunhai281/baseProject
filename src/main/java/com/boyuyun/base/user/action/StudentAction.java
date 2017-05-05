package com.boyuyun.base.user.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Controller;

import com.boyuyun.base.user.biz.StudentBiz;
import com.boyuyun.base.user.entity.Student;
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
import com.dhcc.common.util.FileUtil;
import com.dhcc.common.util.StringUtil;
import com.opensymphony.xwork2.ModelDriven;

@Controller
public class StudentAction extends BaseAction implements ModelDriven<Student>{

	@Resource
	private StudentBiz studentService;
	
	private Student student = new Student();

	@Override
	public Student getModel() {
		return (Student) initPage(student);
	}

	public String toList() {
		return "toList";
	}

	public String toAdd() {
		return "toView";
	}

	public String toView() {
		return "toDetail";
	}

	public String toEdit() {
		return "toView";
	}

	public String toImport() {
		return "toImport";
	}

	public String exportStudentTemplateExcel() throws Exception {
		response.setCharacterEncoding("utf-8");
		String path = FileUtil.getRealPath() + "/WEB-INF/page/admin/user/student/学生基本信息模板.xls";
		File file = new File(path);
		if (file.exists()) {
			String fileName = "学生基本信息模板";
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8") + ".xls");
			// 以流的形式下载文件。
			InputStream fis = new FileInputStream(file);
			POIFSFileSystem fs = new POIFSFileSystem(fis);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			ServletOutputStream out = response.getOutputStream();
			wb.write(out);
			out.flush();
			out.close();
			wb.close();
		}
		return null;
	}

	public String getList() throws Exception {
		String result = "";
		List<Student> list = studentService.getListPaged(student);
		int count = studentService.getListPagedCount(student);
		result = ByyJsonUtil.serialize(count, list);
		print(result);
		return null;
	}

	public String getListNonePaged() throws Exception {
		String result = "";
		List<Student> list = studentService.getListNonePaged(student);
		result = ByyJsonUtil.serialize(list);
		print(result);
		return null;
	}

	@LogThisOperate(module="学生管理",operateType=OperateType.新增或修改)
	public String save() throws Exception {
		String result = getFailJson("保存失败");
		// 设置用户类型为学生
		Integer ordinal = UserType.学生.ordinal();
		student.setUserType(ordinal.toString());
		String md5Pwd = StringUtil.encryptMd5(student.getPwd());
		student.setPwd(md5Pwd);
		if (student.getId() != null && student.getId().trim().length() > 0) {
			User userTemp = (User) studentService.get(student.getId());
			student.setPwd(userTemp.getPwd());
			studentService.update(student);
			result = getSuccessJson("修改成功");

		} else {
			// 新增学生，
			String id = ByyStringUtil.getRandomUUID();
			student.setId(id);
			studentService.add(student);
			result = getSuccessJson("保存成功");
		}
		print(result);
		return null;
	}

	public String getBean() throws Exception {
		String id = this.student.getId();
		String result = "";
		Student student = (Student) studentService.get(id);
		result = ByyJsonUtil.serialize(student, null, ByyDateUtil.YYYY_MM_DD);
		print(result);
		return null;
	}
	@LogThisOperate(module="学生管理",operateType=OperateType.删除)
	public String delete() throws Exception {
		String id = student.getId();
		String result = "";
		String[] ids = id.split(",");
		List<Student> students = new ArrayList<Student>();
		List IDSToDelete = new ArrayList();
		for (String string : ids) {
			student = (Student) studentService.get(string);
			students.add(student);
			IDSToDelete.add(string);
		}
		if (students.size() > 0) {
			studentService.deleteAll(students);
			this.setBatchListId(IDSToDelete);
			result = this.getSuccessJson("删除成功");
		} else {
			result = getFailJson("删除失败");
		}
		print(result);
		return null;
	}
	
	/***
	 * 导入学生数据 
	 * 表中班级年级的名称在到层中均按shortname短名称做查询
	 * @return
	 */
	@LogThisOperate(module="学生管理",operateType=OperateType.导入)	
	public String importStudentExcel() throws Exception{
		response.setCharacterEncoding("utf-8");
		String result = getFailJson("导入失败!");
		String filename = request.getParameter("filename");
		String filePath = request.getParameter("filePath");
		try {
			// 开始处理文件导入，判断文件类型和存在
			if (null != filePath && !"".equals(filePath)) {
				// 判断文件后缀是不是xls或者xlsx
				if (filePath.endsWith(".xls") || filePath.endsWith(".xlsx")) {
					String path = ConstantUtil.UPLOAD_FOLDER + filePath;
					File sourceFile = new File(path);
					if (sourceFile.exists()) {
						String fileName = sourceFile.getName();
					}
					// 写入模板
					File dirFile = new File(path);
					if (dirFile.exists()) {
						// 存在该文件.通过POI进行读取数据，然后保存到数据库中。
						ExcelReader excelReader = new ExcelReader();
						Map<Integer, List> contentMap = excelReader.readExcelContent(new FileInputStream(path));
						List listForIds = new ArrayList();//用于存放生成的id
						//导入学生基本信息模板.xls
//						String msg = studentService.insertImportStudentExcel(contentMap,listForIds);
						//导入国家学籍数据模板.xlsx
						String msg = studentService.importNationalStudentExcel(contentMap,listForIds);
						if(msg!=null&&msg.contains("成功")){
							result = getSuccessJson(msg);
							this.setBatchListId(listForIds);
						}else{
							result = getFailJson(msg);
						}
					}else{
						result = getFailJson("文件未上传成功!");
					}
				}else{
					result = getFailJson("文件后缀不符合.xls,.xlsx的格式!");
				}
			}else{
				result = getFailJson("文件路径获取失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		print(result);
		return null;
	}
}

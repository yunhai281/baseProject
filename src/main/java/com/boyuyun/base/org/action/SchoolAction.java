package com.boyuyun.base.org.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;

import com.boyuyun.base.org.biz.GovernmentBiz;
import com.boyuyun.base.org.biz.SchoolBiz;
import com.boyuyun.base.org.biz.SchoolGradeBiz;
import com.boyuyun.base.org.entity.School;
import com.boyuyun.base.org.entity.SchoolGrade;
import com.boyuyun.base.org.util.TreeDTO;
import com.boyuyun.base.sys.biz.AreaBiz;
import com.boyuyun.base.sys.biz.DictionaryItemBiz;
import com.boyuyun.base.sys.entity.Area;
import com.boyuyun.base.util.ConstantUtil;
import com.boyuyun.base.util.base.BaseAction;
import com.boyuyun.base.util.consts.Stage;
import com.boyuyun.common.annotation.LogThisOperate;
import com.boyuyun.common.autolog.OperateType;
import com.boyuyun.common.json.ByyJsonUtil;
import com.boyuyun.common.util.ByyDateUtil;
import com.boyuyun.common.util.ByyStringUtil;
import com.boyuyun.common.util.ExcelReader;
import com.boyuyun.common.util.PinyinUtil;
import com.google.common.base.Strings;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @author LHui
 * 2017-3-3 下午1:44:52
 */
@Controller  
public class SchoolAction extends BaseAction implements ModelDriven<School>{

	@Resource
	private SchoolBiz schoolBiz;
	@Resource
	private GovernmentBiz governmentBiz;
	@Resource
	private DictionaryItemBiz dictionaryItemBiz;
	@Resource
	private SchoolGradeBiz schoolGradeBiz;
	@Resource
	private AreaBiz areaBiz;
	
	private School school = new School();
	
	@Override
	public School getModel() {
		return (School) initPage( school);
	}
	
	/**
	 * 返回到学校树结构显示界面
	 * @author LHui
	 * @since 2017-3-3 下午1:46:21
	 * @return
	 */ 
	public String toList(){
		return "list";
	}

	
	/**
	 * 跳转到新增、修改界面
	 * @author LHui
	 * @since 2017-3-3 下午3:01:11
	 * @return
	 */
	public String toAdd(){
		return "view";
	}
	/**
	 * 跳转到选择学校Demo界面
	 * @author LHui
	 * @since 2017-3-14 下午3:40:56
	 * @return
	 */
	public String toDemo(){
		return "demo";
	}
	
	/**
	 * 跳转到选择学校界面
	 * @author LHui
	 * @since 2017-3-14 上午10:11:31
	 * @return
	 */ 
	public String toSelect(){
		return "schoolSelect";
	}
		
	/**
	 * @Description 学校导入 
	 * @author jms
	 * @return
	 */
	public String toImport(){
		return "toimport";
	}
	
	/**
	 * 将学校数据以树的形式返回到前台显示
	 * @author LHui
	 * @since 2017-3-3 下午1:47:59
	 * @return
	 * @throws IOException 
	 */
	public String getList() throws IOException{
		String result = "";
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		try {
			List<TreeDTO> list = this.schoolBiz.getDynamicGovernmentAndSchoolTree(type, id);
			result = ByyJsonUtil.serialize(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.print(result);
		return null;
	}
	
	public String getBean() throws Exception{
		String result = ""; 
		String areaload = request.getParameter("areaload");//是否需要加载学校的所属地区信息标志
		JSONObject obj = new JSONObject();
		if( school.getId() != null){
			school = schoolBiz.selectFullByPrimaryKey(school.getId());
//				obj.element("school", school);
			if(areaload != null && "true".equals(areaload)){
				if(school.getAreaId() != null){
					Area area = areaBiz.selectLinkByPrimaryKey(school.getAreaId());
					obj.element("area", area);
				}
			}
		}
		if(school == null){
			school = new School();
		}
		String schoolResult = ByyJsonUtil.serialize(school,new String[]{},ByyDateUtil.YYYY_MM_DD);
		obj.element("school", schoolResult);
		result = ByyJsonUtil.serialize(obj,new String[]{},ByyDateUtil.YYYY_MM_DD);
		this.print(result);
		return null;
	}
	
	/**
	 * 查询所有总校
	 * @author LHui
	 * @since 2017-3-3 下午3:48:49
	 * @return
	 */  
	public String getParentSchool() throws Exception{
		String result = "";
		List<School> list = null;
		list = schoolBiz.selectAllParent();
		if(list == null){
			list = new ArrayList<School>();
		}
		result = ByyJsonUtil.serialize(list);
		this.print(result);
		return null;
	}
	
	/**
	 * 新增、更新学校信息
	 * @author LHui
	 * @since 2017-3-3 下午4:03:12
	 * @return
	 */
	@LogThisOperate(module="学校管理",operateType=OperateType.新增或修改)
	public String save() throws Exception{
		String result = this.getSuccessJson("保存成功");
		try {
			if(!Strings.isNullOrEmpty(school.getName())){
				school.setPinyin(PinyinUtil.converterToSpell(school.getName()));//全拼
				school.setJianpin(PinyinUtil.converterToAllFirstSpell(school.getName()));//简拼
			}
			if(school.getId() != null && school.getId().trim().length() > 0){
				//更新操作
				schoolBiz.update(school);
			}else{
				//新增操作
				String id = ByyStringUtil.getRandomUUID();
				school.setId(id);
				schoolBiz.add(school);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = this.getFailJson("保存失败");
		}
		this.print(result);
		return null;
	} 
	
	@LogThisOperate(module="学校管理",operateType=OperateType.删除)
	public String delete() throws Exception{
		String result = this.getSuccessJson("删除成功");
		try {
			if(school.getId() != null && school.getId().trim().length() > 0){
				//查看该学校下是否有子校
				School temp = new School();
				temp.setId(school.getId());
				school.setParentId(temp.getId());
				List<School> list = schoolBiz.getByParentId(school.getId());
				if(list.size() > 0){
					result = this.getFailJson("删除失败：该学校下有子校存在");
				}else{
					school = schoolBiz.get(school.getId());//伟东同步数据，需要使用其他数据。
					schoolBiz.delete(school);
					result = this.getSuccessJson("删除成功");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = this.getFailJson("系统出错，请稍后重试");
		}

		this.print(result);
		return null;
	}
	 
	public String search() throws IOException{
		String result = "";
		try {
			List<School> list = schoolBiz.searchByName(school.getName());
			result = ByyJsonUtil.serialize(list);
		} catch (Exception e) {
			e.printStackTrace();
			result = this.getFailJson("系统出错，请稍后重试");
		}

		this.print(result);
		return null;
	}
	 
	public String getSchoolList() throws IOException{
		String result = "";
		JSONObject obj = new JSONObject();
		try {
			obj.element("total", schoolBiz.getListPagedCount(school));
			obj.element("items", schoolBiz.getListPaged(school));
			result = ByyJsonUtil.serialize(obj);
		} catch (Exception e) {
			e.printStackTrace();
			result = this.getFailJson("系统出错，请稍后重试");
		}
		this.print(result);
		return null;
	}
	
	/***
	 * 导出模版
	 * @return
	 */
	public String exportSchoolTemplateExcel() throws Exception{
		response.setCharacterEncoding("utf-8");
		String strDirPath = request.getSession().getServletContext().getRealPath("/");
    	String path =  strDirPath+"/WEB-INF/page/admin/org/school/学校基本信息模板.xlsx";
    	File file = new File(path);
    	if(file.exists()){
    		// 以流的形式下载文件。
			String fileName = "学校基本信息模板";
			response.setContentType("application/x-msdownload");
			
			String agent = (String)request.getHeader("USER-AGENT");    
			if(agent != null && agent.indexOf("MSIE") == -1) {// FF  
				String enableFileName = "=?UTF-8?B?" + (new String(Base64.encodeBase64((fileName+".xlsx").getBytes("utf-8")))) + "?=";    
				response.setHeader("Content-Disposition", "attachment; filename=" + enableFileName);    
			}else {
				response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8")+".xlsx");
			}
			
			//response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8")+".xlsx");
			InputStream fis = new FileInputStream(file); 
            XSSFWorkbook wb = new XSSFWorkbook(fis);       
            String[] schoolTypeArr=dictionaryItemBiz.getArr("SchoolType");
            String[] schoolSystemTypeArr=dictionaryItemBiz.getArr("SchoolSystemType");
            XSSFSheet sheet = wb.getSheetAt(0);
            //设置数据有效性
            sheet.addValidationData(ExcelReader.setDataValidation(sheet,schoolSystemTypeArr  , 1, 6000, 2, 2));
            sheet.addValidationData(ExcelReader.setDataValidation(sheet,schoolTypeArr , 1, 6000, 5, 5));
            ServletOutputStream out =  response.getOutputStream();
            wb.write(out);
            out.flush();
            out.close(); 
    	}
		return null;
	}
	
	
	/**
	 * @Description 导入
	 * @author jms
	 * @return
	 * @throws Exception
	 */
	@LogThisOperate(module="学校管理",operateType=OperateType.导入)
	public String importExcel() throws Exception{
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
					if(dirFile.exists()){
						//存在该文件.通过POI进行读取数据，然后保存到数据库中。 
						ExcelReader excelReader = new ExcelReader();
						List listForIds = new ArrayList();//用于存放生成的id
						Map<Integer, List> contentMap= excelReader.readExcelContent(new FileInputStream(path));
						String mgs=schoolBiz.insertImportExcel(contentMap,listForIds);
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
	 * @Description 取得学校学段
	 * @author jms
	 * @return
	 * @throws Exception
	 */
	public String getSchoolStage() throws Exception{
		String result = "";
		try {
			String schoolId = school.getId();
			List<SchoolGrade> list = schoolGradeBiz.getSchoolGradeBySchoolAndStage(schoolId);
			List<Stage> stages= new ArrayList<Stage>();
			Stage[] stageArr= Stage.values();
			// 去除重复的学段
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				SchoolGrade schoolGrade = (SchoolGrade) iterator.next();
				for (int i = 0; i < stageArr.length; i++) {
					Stage s= stageArr[i];
					if(s.ordinal() ==schoolGrade.getSysGradeStage()){
						if(!stages.contains(s)){
							stages.add(s);
						}
					}
				}
				
			}
			result = ByyJsonUtil.serialize(stages);
		} catch (Exception e) {
			e.printStackTrace();
			result = this.getFailJson("系统出错，请稍后重试");
		} 
		this.print(result);
		return null;
	}

	public Boolean validatSerialNumber()throws Exception{
		boolean flag = true;
		int count = schoolBiz.getSchoolSerialNumberCount(school.getSerialNumber(),school.getCode(),school.getId());
		if(count!=0){
			flag = false;
		}
		this.print(flag);
		return null;
	}
	 
	public Boolean validatName()throws Exception{
		boolean flag = true;
		int count = schoolBiz.validatName(school.getName(),school.getId());
		if(count!=0){
			flag = false;
		}
		this.print(flag);
		return null;
	}
}

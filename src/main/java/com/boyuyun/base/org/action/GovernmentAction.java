package com.boyuyun.base.org.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import net.sf.json.JSONObject;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;

import com.boyuyun.base.org.biz.GovernmentBiz;
import com.boyuyun.base.org.entity.Government;
import com.boyuyun.base.sys.biz.AreaBiz;
import com.boyuyun.base.sys.entity.Area;
import com.boyuyun.base.util.ConstantUtil;
import com.boyuyun.base.util.base.BaseAction;
import com.boyuyun.common.annotation.LogThisOperate;
import com.boyuyun.common.autolog.OperateType;
import com.boyuyun.common.json.ByyJsonUtil;
import com.boyuyun.common.util.ByyDateUtil;
import com.boyuyun.common.util.ByyStringUtil;
import com.boyuyun.common.util.ExcelReader;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 管理机构Controller
 * @author LHui
 * 2017-3-1 下午1:27:47
 */
@Controller   
public class GovernmentAction extends BaseAction implements ModelDriven<Government>{
	private Government government = new Government();

	@Resource
	private GovernmentBiz governmentService;
	@Resource
	private AreaBiz areaService;

	@Override
	public Government getModel() {
		return (Government)initPage(government);
	}
	
	/**
	 * 跳转到管理机构列表界面
	 * @author LHui
	 * @since 2017-3-1 下午1:34:35
	 * @return
	 */
	public String toList(){
		return "list";
	} 
	
	/**
	 * 跳转到机构Add界面
	 * @author LHui
	 * @since 2017-3-2 上午10:52:10
	 * @return
	 */ 
	public String toAdd(){
		return "view";
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
	 * 机构树
	 * @author LHui
	 * @since 2017-3-1 下午1:48:25
	 * @param gov
	 * @return
	 */
	public String getList() throws Exception{
		String result = "";
		try {
			//生成一棵树
			List<Government> list = this.governmentService.getDynamicGovernmentTree(government.getId());
			result = ByyJsonUtil.serialize(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.print(result);
		return null;
	}
	
	/**
	 * 根据前台传递过来的机构ID，获取该机构详情
	 * @author LHui
	 * @since 2017-3-1 下午4:52:37
	 * @param gov
	 * @return
	 */
	public String getData() throws Exception{
		String result = "";
		Government government = null;
		if(this.government.getId() != null && this.government.getId().trim().length() > 0){
			government = governmentService.selectFullById(this.government.getId());
		}else{
			government = new Government();
		}
		result = ByyJsonUtil.serialize(government, new String[]{"sons"}, ByyDateUtil.YYYY_MM_DD);
		this.print(result);
		return null;
	}
	
	/**
	 * 机构信息保存
	 * @author LHui
	 * @since 2017-3-2 下午4:37:31
	 * @return
	 */ 
	@LogThisOperate(module="管理机构",operateType=OperateType.新增或修改)
	public String toSave() throws Exception{
		String result = this.getSuccessJson("保存成功");
		try {
			if(government.getId() != null && government.getId().trim().length() > 0){
				//更新操作
				governmentService.update(government);
			}else{
				//插入操作
				//新增
				String id = ByyStringUtil.getRandomUUID();
				government.setId(id);
				governmentService.add(government);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = this.getFailJson("保存失败");
		}
		this.print(result);
		return null;
	}
	
	/**
	 * 根据前台传递过来的机构ID，获取该机构详细信息
	 * @author LHui
	 * @since 2017-3-2 下午4:57:39
	 * @return
	 */
	public String getBean() throws Exception{
		String result = "";
		//Government gov= null;
		JSONObject obj = new JSONObject();
		if(government.getId() != null){
			government = governmentService.selectFullById(government.getId());
			if(government != null){
				if(government.getAreaId() != null){
					Area area = areaService.selectLinkByPrimaryKey(government.getAreaId());
					obj.element("area", area);
				}
			}
		}
	
		if(government == null){
			government = new Government();
		}
		obj.element("gov", government);
		result = ByyJsonUtil.serialize(obj);
		this.print(result);
		return null;
	}
	
	/**
	 * 删除机构信息
	 * @author LHui
	 * @since 2017-3-3 上午11:30:22
	 * @return
	 */
	@LogThisOperate(module="管理机构",operateType=OperateType.删除)
	public String delete() throws Exception{
		String result = this.getSuccessJson("删除成功");
		try {
			if(government.getId() != null && government.getId().trim().length() > 0){
				//查看该Id下是否有子节点
				List<Government> list = governmentService.getByParentId(government.getId());
				if(list != null && list.size() > 0){
					result = this.getFailJson("删除失败：该机构下含有子机构");
				}else{
					government = (Government) governmentService.get(government.getId());
					governmentService.delete(government);
				}
			}
		} catch (Exception e) {
			result = this.getFailJson("系统出错，请稍后重试");
		}
		this.print(result);
		return null;
	}
	
	/**
	 * 机构增加、修改操作时的查重校验
	 * @author LHui
	 * @since 2017-3-3 下午12:56:25
	 * @param gov
	 * @return
	 */ 
	public Boolean toValidate()throws Exception{
		boolean flag = true;
		List<Government> list = governmentService.validateGov(government);
		if(list != null && list.size() > 0){
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 根据前台传递过来的机构名称，查询，并查询出该机构下的子机构，并以树的形式返回
	 * @author LHui
	 * @since 2017-3-13 上午10:24:43
	 * @param name
	 * @return
	 */
	public String toSearch() throws Exception{
		String result = "";
		String name = request.getParameter("name");
		//生成一棵树
		List<Government> list = this.governmentService.searchByName(name);
		result = ByyJsonUtil.serialize(list);
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
    	String path =  strDirPath+"/WEB-INF/page/admin/org/government/教育局机构基本信息模板.xlsx";
    	File file = new File(path);
    	if(file.exists()){
    		// 以流的形式下载文件。
			String fileName = "教育局机构基本信息模板";
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8")+".xlsx");
			InputStream fis = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(fis);                
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
	@LogThisOperate(module="管理机构",operateType=OperateType.导入)
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
						String mgs=governmentService.insertImportExcel(contentMap,listForIds);
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
}

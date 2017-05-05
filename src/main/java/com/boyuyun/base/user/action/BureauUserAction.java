package com.boyuyun.base.user.action;

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

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;

import com.boyuyun.base.sys.biz.DictionaryBiz;
import com.boyuyun.base.sys.biz.DictionaryItemBiz;
import com.boyuyun.base.sys.entity.Dictionary;
import com.boyuyun.base.sys.entity.DictionaryItem;
import com.boyuyun.base.user.biz.BureauUserBiz;
import com.boyuyun.base.user.entity.BureauUser;
import com.boyuyun.base.util.ConstantUtil;
import com.boyuyun.base.util.base.BaseAction;
import com.boyuyun.common.annotation.LogThisOperate;
import com.boyuyun.common.autolog.OperateType;
import com.boyuyun.common.json.ByyJsonUtil;
import com.boyuyun.common.util.ByyDateUtil;
import com.boyuyun.common.util.ExcelReader;
import com.google.common.base.Strings;
import com.opensymphony.xwork2.ModelDriven;
@Controller
public class BureauUserAction extends BaseAction implements ModelDriven<BureauUser> {
	
	/** @Fields serialVersionUID: */
	  	
	private static final long serialVersionUID = -5061318888080230449L;
	@Resource
	private BureauUserBiz bureauUserService;
	@Resource
	private DictionaryBiz dictionaryService;
	@Resource
	private DictionaryItemBiz dictionaryItemService;
	
	private BureauUser bureauUser = new BureauUser();
	@Override
	public BureauUser getModel() {
		return (BureauUser) initPage(bureauUser);
	}
	/**
	 * @author caoyy
	 * @date 2017-03-21
	 * @see 跳转到教育局用户列表页面
	 * @return
	 */
	public String toList() {
		return "toList";
	}
	
	public String toimport(){
		return "toimport";
	}
	
	/**
	 * @author caoyy
	 * @date 2017-03-21
	 * @see 跳转到教育局用户选择页面
	 * @return
	 */
	public String toSelectList() {
		response.setCharacterEncoding("utf-8");
		 String governmentId = request.getParameter("governmentId");
		 request.setAttribute("governmentId", governmentId);
		return "toSelectList";
	}
	
	/**
	 * @author caoyy
	 * @date 2017-03-21
	 * @see 跳转到教育局用户新建页面
	 * @return
	 */
	public String toAdd() throws Exception{
		response.setCharacterEncoding("utf-8");
		Dictionary dic = dictionaryService.getByCode("GovernmentPost");
		if(dic!=null){
			DictionaryItem exampleItem = new DictionaryItem();
			exampleItem.setDictionaryId(dic.getId());
			List<DictionaryItem> itemList = dictionaryItemService.getListNonePaged(exampleItem);
			request.setAttribute("items", itemList);
		}
		return "toView";
	}
	
	/**
	 * @author caoyy
	 * @date 2017-03-21
	 * @see 跳转到教育局用户详情页面
	 * @return
	 */
	public String toView() throws Exception{
		response.setCharacterEncoding("utf-8");
		return "toDetail";
	}

	/**
	 * @author caoyy
	 * @date 2017-03-21
	 * @see 跳转到教育局用户编辑页面
	 * @return
	 */
	public String toEdit() throws Exception{
		response.setCharacterEncoding("utf-8");
		Dictionary dic = dictionaryService.getByCode("GovernmentPost");
		if(dic!=null){
			DictionaryItem exampleItem = new DictionaryItem();
			exampleItem.setDictionaryId(dic.getId());
			List<DictionaryItem> itemList = dictionaryItemService.getListNonePaged(exampleItem);
			request.setAttribute("items", itemList);
		}
		return "toView";
	}

	/**
	 * @author caoyy
	 * @date 2017-03-21
	 * @see 获取教育局用户列表数据
	 * @return
	 */
	public String getList() throws Exception{
		response.setCharacterEncoding("utf-8");
		String result = "";
		try{
			List<BureauUser> pageInfo = bureauUserService.getListPaged(bureauUser);
			int count = bureauUserService.getListPagedCount(bureauUser);
			result = ByyJsonUtil.serialize(count,pageInfo);
		}catch(Exception e){
			e.printStackTrace();
		}
		this.print(result);
		return null;
	}
	/**
	 * @author caoyy
	 * @date 2017-03-21
	 * @see 教育局用户数据保存、编辑
	 * @return
	 */
	@LogThisOperate(module="教育局用户",operateType=OperateType.新增或修改)
	public String save() throws Exception{
		response.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		String result = this.getSuccessJson("保存成功");
		try {
			String postId =  request.getParameter("postId");
			if(!Strings.isNullOrEmpty(postId)){
				bureauUserService.saveBureau(bureauUser, postId.split(","));
			}else{
				result = this.getFailJson("保存失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = this.getFailJson("保存失败");
		}
		this.print(result);
		return null;
	}

	/**
	 * @author caoyy
	 * @date 2017-03-21
	 * @see 根据唯一标识获取教育局用户数据
	 * @return
	 */
	public String getBean()throws Exception {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		String result = "";
		String id = request.getParameter("id");
		try {
			if (id != null && id.trim().length() > 0) {
				bureauUser = (BureauUser) bureauUserService.get(bureauUser);
			}
			result = ByyJsonUtil.serialize(bureauUser,null,ByyDateUtil.YYYY_MM_DD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.print(result);
		return null;
	}
	
	/**
	 * @author caoyy
	 * @date 2017-03-21
	 * @see 删除教育局用户；可批量删除
	 * @return
	 */
	@LogThisOperate(module="教育局用户",operateType=OperateType.删除)
	public String delete() throws Exception{
		response.setCharacterEncoding("utf-8");
		String result = "";
		try {
			String id =  request.getParameter("id");
			if(!Strings.isNullOrEmpty(id)){
				String[] ids = id.split(",");
				List<BureauUser> bureauUsers = new ArrayList<BureauUser>();
				List IDSToDelete = new ArrayList();
				for(String string : ids){
					bureauUser = new BureauUser ();
					bureauUser.setId(string);
					IDSToDelete.add(string);
					bureauUsers.add(bureauUser);
				}
				if(bureauUsers.size()>0){
					bureauUserService.deleteBueau(bureauUsers);
					this.setBatchListId(IDSToDelete);
					result = this.getSuccessJson("删除成功");
				}else {
					result = this.getFailJson("删除失败");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result =this.getFailJson("程序出错");
		}
		this.print(result);
		return null;
	}
	
	/***
	 * 导出模版
	 * @return
	 */
	public String exportTemplateExcel() throws Exception{
		response.setCharacterEncoding("utf-8");
		String strDirPath = request.getSession().getServletContext().getRealPath("/");
    	String path =  strDirPath+"/WEB-INF/page/admin/user/bureau/教育局人员模板.xlsx";
    	File file = new File(path);
    	if(file.exists()){
    		// 以流的形式下载文件。
			String fileName = "教育局人员模板";
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8")+".xlsx");
			InputStream fis = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(fis); 
            String[] governmentPostArr=dictionaryItemService.getArr("GovernmentPost");
            XSSFSheet sheet = wb.getSheetAt(0);
            //设置数据有效性
            sheet.addValidationData(ExcelReader.setDataValidation(sheet, governmentPostArr, 1, 6000, 6, 6));
            ServletOutputStream out =  response.getOutputStream();
            wb.write(out);
            out.flush();
            out.close(); 
    	}
        
		return null;
	}
	
	/**
	 * @Description 
	 * @author jms
	 * @return
	 * @throws Exception
	 */
	@LogThisOperate(module="教育局用户",operateType=OperateType.导入)
	public String importExcel() throws Exception{
		response.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		String result = this.getFailJson("导入失败!");
		String filename = request.getParameter("filename"); 
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
						Map<Integer, List> contentMap= excelReader.readExcelContent(new FileInputStream(path));
						List listForIds = new ArrayList();//用于存放生成的id
						String mgs=bureauUserService.insertImportExcel(contentMap,listForIds);
						if(mgs!=null && !"".equals(mgs)){
							result = this.getFailJson(mgs);
						}else {
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
	 * @author jms
	 * @date 2017-04-07
	 * @see 上移下移部门人员
	 * @return
	 */
	@LogThisOperate(module="教育局用户",operateType=OperateType.更改排序)
	public String updateSortnum() throws Exception{
		response.setCharacterEncoding("utf-8");
		String result = this.getSuccessJson("移动成功");
		try {
			String uesrId =  request.getParameter("id");
			String type =  request.getParameter("type");
			if(!Strings.isNullOrEmpty(type)){
				result=  bureauUserService.updateSortNumber(uesrId, type);
				result =this.getSuccessJson(result);
			}else{
				result = this.getFailJson("移动失败");
			}
			/*if(!Strings.isNullOrEmpty(postId)){
				bureauUserService.saveBureau(bureauUser, postId.split(","));
			}else{
				result = this.getFailJson("移动失败");
			}*/
		} catch (Exception e) {
			e.printStackTrace();
			result = this.getFailJson("移动失败");
		}
		this.print(result);
		return null;
	}
}

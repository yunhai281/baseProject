package com.boyuyun.base.sys.action;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;

import com.boyuyun.base.sys.biz.DictionaryBiz;
import com.boyuyun.base.sys.biz.DictionaryItemBiz;
import com.boyuyun.base.sys.entity.Dictionary;
import com.boyuyun.base.sys.entity.DictionaryItem;
import com.boyuyun.base.util.ConstantUtil;
import com.boyuyun.base.util.base.BaseAction;
import com.boyuyun.common.annotation.LogThisOperate;
import com.boyuyun.common.autolog.OperateType;
import com.boyuyun.common.json.ByyJsonUtil;
import com.google.common.base.Strings;
import com.opensymphony.xwork2.ModelDriven;

@Controller
public class DictionaryAction extends BaseAction implements ModelDriven<Dictionary>{

	private Dictionary dictionary = new Dictionary();
	@Override
	public Dictionary getModel() {
		return dictionary;
	}
	
	
	@Resource
	private DictionaryBiz dictionaryService;
	
	@Resource
	private DictionaryItemBiz dictionaryItemService;
	
	
	/**
	 * 封装获取dictinaryItem的方法
	 * @return
	 */
	private DictionaryItem getDictionaryItem(HttpServletRequest request){
		DictionaryItem dicItem = new DictionaryItem();
		if(!Strings.isNullOrEmpty(request.getParameter("name"))){
			dicItem.setName(request.getParameter("name"));
		}
		if(!Strings.isNullOrEmpty(request.getParameter("value"))){
			dicItem.setValue(request.getParameter("value"));
		}
		if(!Strings.isNullOrEmpty(request.getParameter("remark"))){
			dicItem.setRemark(request.getParameter("remark"));
		}		
		if(!Strings.isNullOrEmpty(request.getParameter("schoolId"))){
			dicItem.setSchoolId(request.getParameter("schoolId"));
		}
		if(!Strings.isNullOrEmpty(request.getParameter("schoolName"))){
			dicItem.setSchoolName(request.getParameter("schoolName"));
		}
		if(!Strings.isNullOrEmpty(request.getParameter("dictionaryName"))){
			dicItem.setDictionaryName(request.getParameter("dictionaryName"));
		}
		String num = request.getParameter("num");
		if(!Strings.isNullOrEmpty(num)){
			dicItem.setNum(Integer.valueOf(num));
		}
		String sortNum = request.getParameter("sortNum");
		if(!Strings.isNullOrEmpty(sortNum)){
			dicItem.setSortNum(Integer.valueOf(sortNum));
		}		
		String dictionaryId = request.getParameter("dictionaryId");
		if(!Strings.isNullOrEmpty(dictionaryId)){
			dicItem.setDictionaryId(Integer.valueOf(dictionaryId));
		}		
		String id = request.getParameter("id");
		if(!Strings.isNullOrEmpty(id)){
			dicItem.setId(Integer.valueOf(id));
		}		
        return dicItem;
	}
	
	public String getDictionaryBean() throws Exception{
		Integer id = Integer.valueOf(request.getParameter("id"));
		String result = null;
		 Dictionary dictionary = dictionaryService.get(id);
		 if(dictionary !=null){
			 result = ByyJsonUtil.serialize(dictionary,new String[]{});
		 }else{
			 result = this.getFailJson("网络异常!"); 
		 }
		 this.print(result);
		return null;
	}
	
	
	public String getDictionaryItemBean() throws Exception{
		Integer id = Integer.valueOf(request.getParameter("id"));
		String result = null;
		 DictionaryItem dictionaryItem = dictionaryItemService.get(id);
		 if(dictionaryItem != null){
			 result = ByyJsonUtil.serialize(dictionaryItem,new String[]{});
		 }else{
			 result = this.getFailJson("网络异常!"); 
		 }
		 this.print(result);
		return null;
	}	
	
	
	/**
	 * 内部封装方法
	 * 获取dictionaryItem实体
	 * @param dicId
	 * @return
	 */
	private DictionaryItem getItem(int dicId){
		DictionaryItem dicItem = new DictionaryItem();
		dicItem.setDictionaryId(dicId);
		return dicItem;
	}
	/**
	 * 内部封装方法
	 * 由外键获取dictionaryItem
	 * @param dicId
	 * @return
	 */
	private List<DictionaryItem> verItem(DictionaryItem dicItem)throws Exception{
		return dictionaryItemService.getListNonePaged(dicItem);
	}
	
	/**
	 * 得到树形列表
	 * @param request
	 * @param response
	 * @return
	 */
	public String getList()throws Exception{
		String id = request.getParameter("id");
		String levelType = request.getParameter("levelType");
		String result = "";
		if(levelType.equals("1")){
			JSONArray jsonArray = new JSONArray();
			List<Dictionary> list = dictionaryService.getListNonePaged(new Dictionary());
			if(list!=null){
				for(Iterator iterator = list.iterator(); iterator.hasNext();){
					JSONObject jsonObject = new JSONObject();
					Dictionary dic = (Dictionary) iterator.next();
					jsonObject.put("id", dic.getId());
					jsonObject.put("name", dic.getName());
					jsonObject.put("code", dic.getCode());
					jsonObject.put("remark", dic.getRemark());
					jsonObject.put("editable", String.valueOf(dic.isEditable()));
					jsonObject.put("schooldiy", String.valueOf(dic.isSchooldiy()));
					jsonObject.put("parentName", ConstantUtil.SYSTEM_NAME);
					jsonObject.put("levelType", "2");
					if(this.verItem(this.getItem(dic.getId())).size()>0){
						jsonObject.put("isParent", true);
					}
					jsonArray.add(jsonObject);
				}
				result = ByyJsonUtil.serialize(jsonArray);
			}else{
				result = this.getSuccessJson("没有数据");
			}	
			result = ByyJsonUtil.serialize(jsonArray);
		}else if (levelType.equals("2")) {
			int getid = Integer.valueOf(id).intValue();
			List<DictionaryItem> list = this.verItem(this.getItem(getid));
			JSONArray jsonArray = new JSONArray();
			if(list!=null){
				for(Iterator iterator = list.iterator(); iterator.hasNext();){
					JSONObject jsonObject =new JSONObject();
					DictionaryItem dicI = (DictionaryItem) iterator.next();
					jsonObject.put("id", dicI.getId());
					jsonObject.put("dictionaryId", dicI.getDictionaryId());
					jsonObject.put("dictionaryName", dicI.getDictionaryName());
					jsonObject.put("name", dicI.getName());
					jsonObject.put("value",dicI.getValue());
					jsonObject.put("remark",dicI.getRemark());
					jsonObject.put("num",dicI.getNum());
					jsonObject.put("shortNum",dicI.getSortNum());
					jsonObject.put("schoolId",dicI.getSchoolId());
					jsonObject.put("schoolName",dicI.getSchoolName());
					jsonObject.put("levelType","3");
					jsonArray.add(jsonObject);
				}	
				result = ByyJsonUtil.serialize(jsonArray);
		    }else{
		    	result = this.getSuccessJson("没有数据");
		    }
		}else{
			//获取根节点
			String name = ConstantUtil.SYSTEM_NAME;
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("name", name);
			jsonObject.put("levelType", "1");
			jsonObject.put("isParent", true);
			result = ByyJsonUtil.serialize(jsonObject,new String[]{});
		}
		this.print(result);
		return null;
	}
	/**
	 * 模糊查询字典项
	 * @return
	 */
	public String toSearch()throws Exception{
		String name = request.getParameter("name");
		String result = "";
		String rootname = ConstantUtil.SYSTEM_NAME;
		Dictionary dictionary = new Dictionary();
		dictionary.setCode(name);
		dictionary.setName(name);
		List<Dictionary> list = dictionaryService.getListNonePaged(dictionary);
		JSONArray jsonArray = new JSONArray();
		if(list!=null){
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				JSONObject jsonObject = new JSONObject();
				Dictionary dic = (Dictionary)iterator.next();
				jsonObject.put("id", dic.getId());
				jsonObject.put("name", dic.getName());
				jsonObject.put("code", dic.getCode());
				jsonObject.put("remark", dic.getRemark());
				jsonObject.put("editable", String.valueOf(dic.isEditable()));
				jsonObject.put("schooldiy", String.valueOf(dic.isSchooldiy()));
				jsonObject.put("parentName", ConstantUtil.SYSTEM_NAME);
				jsonObject.put("levelType", "2");
				if(this.verItem(this.getItem(dic.getId())).size()>0){
					jsonObject.put("isParent", true);
				}
				jsonArray.add(jsonObject);				
			}
		}else{
			result = this.getSuccessJson("没有数据");
		}
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("name", rootname);
		jsonObject2.put("levelType", "1");
		jsonObject2.put("isParent", true);
		jsonObject2.put("children", jsonArray);
		result = ByyJsonUtil.serialize(jsonObject2);
		this.print(result);
		return null;
	}
	
	/**
	 * 保存字典项
	 * @param dic
	 * @return
	 */
	@LogThisOperate(module="字典项",operateType=OperateType.新增或修改)
	public String saveDictionary() throws Exception{
		String result = this.getFailJson("操作失败");
		if(dictionary.getId()>0){
			//更新操作
			dictionaryService.update(dictionary);
			result = this.getSuccessJson("操作成功");
		}else{
			//插入操作/新增
			dictionaryService.add(dictionary);
			result = this.getSuccessJson("操作成功");
		}
		this.print(result);
		return null;
	}

	/**
	 * 保存字典值
	 * @param dicI
	 * @return
	 */
	@LogThisOperate(module="字典值",operateType=OperateType.新增或修改)
	public String saveDictionaryItem()throws Exception{
		DictionaryItem dicItem = this.getDictionaryItem(request);
		String result = this.getFailJson("操作失败!");
		if(dicItem.getId()>0){
			//更新操作
			dictionaryItemService.update(dicItem);
			result = this.getSuccessJson("操作成功!");
		}else{
			//插入操作
			//新增
			dicItem.setSchoolId(null);
			dictionaryItemService.add(dicItem);
			dictionary.setId(dicItem.getId());
			result = this.getSuccessJson("操作成功!");
		}
		this.print(result);
		return null;
	}	
	
	/**
	 * 删除字典项
	 * @param dictionary
	 * @return
	 */
	@LogThisOperate(module="字典项",operateType=OperateType.删除)
	public String deleteDictionary()throws Exception{
		String result = this.getFailJson("删除失败!");
		if(dictionary.getId()>0){
			List<DictionaryItem> list = this.verItem(this.getItem(dictionary.getId()));
			if(list.size()>0){
				dictionaryItemService.deleteByForeignKey(this.getItem(dictionary.getId()));
				dictionaryService.delete(dictionary);
				result = this.getSuccessJson("删除成功!");
			}else{
				dictionaryService.delete(dictionary);
				result = this.getSuccessJson("删除成功!");
			}
		}
		this.print(result);
		return null;
	}
	
	/**
	 * 删除字典值
	 * @param dicI
	 * @return
	 */
	@LogThisOperate(module="字典值",operateType=OperateType.删除)
	public String deleteDictionaryItem()throws Exception {
		String id = request.getParameter("id");
		String result = this.getFailJson("删除失败!");
		if(!Strings.isNullOrEmpty(id)){
			DictionaryItem dicI = new DictionaryItem();
			dicI.setId(Integer.parseInt(id));
			if(dicI.getId()>0){
					dictionaryItemService.delete(dicI);
					result = this.getSuccessJson("删除成功!");
			}
		}
		this.print(result);
		return null;
	}
	
	/**
	 *字典管理跳转 
	 * @return
	 */
	public String toList() {
		return "toList";
	}
	/**
	 * 添加编辑跳转
	 * @return
	 */
	public String toAdd() {
		return "toAdd";
	}

	/**
	 * 验证名称,编码是否重复
	 * @param dic
	 * @return
	 */
	public Boolean dictionaryValidate()throws Exception{
		boolean flag = true;
		flag = dictionaryService.validateDictionary(dictionary);
		this.print(flag);
		return null;
	}

	/**
	 * 验证编码,名称是否重复
	 * @param dicI
	 * @return
	 */
	public Boolean dictionaryItemValidate()throws Exception{
		DictionaryItem dicItem = this.getDictionaryItem(request);
		boolean flag = true;
		flag = dictionaryItemService.validateDictionaryItem(dicItem);
		this.print(flag);
		return null;
	}

}

package com.boyuyun.base.sys.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;

import com.boyuyun.base.sys.biz.AreaBiz;
import com.boyuyun.base.sys.entity.Area;
import com.boyuyun.base.util.base.BaseAction;
import com.boyuyun.common.json.ByyJsonUtil;
import com.boyuyun.common.util.ByyDateUtil;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @author LHui
 * 2017-3-2 下午1:32:23
 */
@Controller  
public class AreaAction extends BaseAction implements ModelDriven<Area>{
	private Area area = new Area();
	
	@Override
	public Area getModel() {
		return area;
	}	
	@Resource
	private AreaBiz areaService;
	/**
	 * 跳转到地域管理页面
	 * @author jijiuliang
	 * @since 2017-3-1 下午15:00:36
	 * @return
	 */
	public String toArea(){
		return "toView";
	}
	
	/**
	 * 获取单个的节点信息
	 * @return
	 */
	public String getBean()throws Exception{
		String id = area.getId();
		String result = "";
		Area area = new Area();
		JSONObject jsonObject = new JSONObject();
		if(id != null && id.trim().length() > 0){
			area = areaService.selectLinkByPrimaryKey(id);
			this.putJsonObject(jsonObject, area);
			jsonObject.put("parentName", area.getParentName());//父节点名称	
		}
		result = ByyJsonUtil.serialize(jsonObject, new String[]{} , ByyDateUtil.YYYY_MM_DD);
		this.print(result);
		return null;
	}

	/**
	 * 加载地域树列表
	 * @return
	 */
	public String getList()throws Exception{
		String id = null == request.getParameter("id") || "".equals(request.getParameter("id"))? "100000" : request.getParameter("id");
		//是否要加载数据
		String asyn = request.getParameter("asyn");
		String result = null;
		JSONArray jsonArray = new JSONArray();
		Area area = areaService.get(id);
		if("true".equals(asyn)){
			List<Area> childList = areaService.getListByParent(id);
			if("2".equals(String.valueOf(area.getLevelType()))){
				for(Iterator iterator = childList.iterator(); iterator.hasNext();){
					JSONObject jsonObject = new JSONObject();
					Area temp = (Area) iterator.next();
					this.putJsonObject(jsonObject, temp);
					if(areaService.getListNonePaged(temp).size()>0){
						jsonObject.put("isParent", "true");
					}else{
						jsonObject.put("isParent", "false");
					}
					jsonArray.add(jsonObject);
				}
			}else{
				for(Iterator iterator = childList.iterator(); iterator.hasNext();){
					JSONObject jsonObject = new JSONObject();
					Area temp = (Area) iterator.next();
					this.putJsonObject(jsonObject, temp);
					if(areaService.getListNonePaged(temp).size()>0){
						jsonObject.put("isParent", "true");
					}else{
						jsonObject.put("isParent", "false");
					}
					jsonArray.add(jsonObject);
				}
			}
			result = ByyJsonUtil.serialize(jsonArray, new String[]{} , ByyDateUtil.YYYY_MM_DD);
		}else{
			JSONObject jsonObject = new JSONObject();
			this.putJsonObject(jsonObject, area);
			jsonObject.put("isParent", "true");//是否父节点
			result = ByyJsonUtil.serialize(jsonObject, new String[]{} , ByyDateUtil.YYYY_MM_DD);
		}
		this.print(result);
		return null;
	}
	/**
	 * json数据填充封装
	 * @param jsonObject
	 * @param temp
	 * @return
	 */
	private JSONObject putJsonObject(JSONObject jsonObject,Area temp){
		jsonObject.put("id", temp.getId());//主键
		jsonObject.put("name", temp.getName());//名称
		jsonObject.put("mergerName", temp.getMergerName());// 完整名称
		jsonObject.put("shortName", temp.getShortName());//短名称
		jsonObject.put("pinyin", temp.getPinyin());//拼音
		jsonObject.put("firstLetter", temp.getFirstLetter());//首字母
		jsonObject.put("levelType", temp.getLevelType());//区域等级(1省/2市/3区县)
		jsonObject.put("longitude", String.valueOf(temp.getLongitude()));//经度
		jsonObject.put("dimension", String.valueOf(temp.getDimension()));//纬度
		jsonObject.put("code", temp.getCode());//区号
		jsonObject.put("zip", temp.getZip());//邮编
		jsonObject.put("available", String.valueOf(temp.isAvailable()));//是否可用
		jsonObject.put("parentId", temp.getParentId());//父节点
		return jsonObject;
	}
	/**
	 * 根据前台传递过来的ID，获取对应的Area
	 * @author LHui
	 * @since 2017-3-2 下午1:34:29
	 * @return
	 */
	public String getCommonList()throws Exception{
		PrintWriter out = null;
		String result = "";
		String id = null == request.getParameter("id") || "".equals(request.getParameter("id"))? "100000" : request.getParameter("id");
		Area area = (Area) areaService.get(id);
		//按照levelType给area分类，为3的时候，是最低的一层
		//查询当前所选Area的子节点
		List<Area> childList = areaService.getListByParent(area.getId());
		List<Area> tempList = new ArrayList<Area>();
		if("2".equals(String.valueOf(area.getLevelType()))){
			for (Iterator iterator = childList.iterator(); iterator.hasNext();) {
				Area temp = (Area) iterator.next();
				temp.setIsParent("false");
				tempList.add(temp);
			}
		}else{
			tempList= childList;
		}
		for (Area area2 : tempList) {
			area2.getName();
		}
		result = ByyJsonUtil.serialize(tempList);
		this.print(result);
		return null;
	}


}

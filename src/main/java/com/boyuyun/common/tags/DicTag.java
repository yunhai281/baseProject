package com.boyuyun.common.tags;

import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.boyuyun.base.sys.biz.DictionaryBiz;
import com.boyuyun.base.sys.biz.DictionaryItemBiz;
import com.boyuyun.base.sys.entity.Dictionary;
import com.boyuyun.base.sys.entity.DictionaryItem;
import com.boyuyun.common.util.context.ApplicationContextHolder;
import com.google.common.base.Strings;

public class DicTag extends TagSupport{
	private static final long serialVersionUID = -6607184338161119635L;
	private String dicId;
	private String dicCode;//code如果有多个存在，则会返回第一个
	private String schoolId;//如果字典不支持schoolId则自动忽略，如果支持schoolId但未提供，则会显示空白
	private String sync="false";//同步还是异步加载，如果是异步则使用ajax来加载，依赖jquery，会在页面生成javascript代码
	private int defaultValue;//默认值
	
	private String name; //html name
	private String id;//html id
	private String style;//css style
	private String cssClass;//css 类
	private boolean pleaseChoose = true;//是否生成请选择
	private boolean showOnly = false;//是否仅显示
	private boolean multiSelect = false;//是否允许多选
	@Override
	public int doEndTag() throws JspException {
		try {
			DictionaryBiz dictionaryService = ApplicationContextHolder.getBean(DictionaryBiz.class);
			DictionaryItemBiz dictionaryItemService = ApplicationContextHolder.getBean(DictionaryItemBiz.class);
			StringBuffer sb = new StringBuffer();
			Dictionary dic = null;
			if(!Strings.isNullOrEmpty(dicId)){
				//按id
				dic = dictionaryService.get(Integer.parseInt(dicId));
			}else if(!Strings.isNullOrEmpty(dicCode)){
				//按code
				dic = dictionaryService.getByCode(dicCode);
			}
			if(dic!=null){
				if(showOnly){
					//仅显示
					DictionaryItem example = new DictionaryItem();
					example.setDictionaryId(dic.getId());
					example.setSchoolId(schoolId);
					example.setId(defaultValue);
					List<DictionaryItem> itemList = dictionaryItemService.getListNonePaged(example);
					if(itemList!=null&&itemList.size()>0){
						sb.append(itemList.get(0).getName());
					}
				}else{
					sb.append("<select ");
					if(!Strings.isNullOrEmpty(name))sb.append(" name=\""+name+"\"");
					if(!Strings.isNullOrEmpty(id))sb.append(" id=\""+id+"\"");
					if(!Strings.isNullOrEmpty(style))sb.append(" style=\""+style+"\"");
					if(!Strings.isNullOrEmpty(cssClass))sb.append(" class=\""+cssClass+"\"");
					if(multiSelect)sb.append(" multiple=\"multiple\" ");
					sb.append(" >\n");
					if(pleaseChoose)sb.append("<option value=''>请选择...</option> ");
					if("true".equals(sync)){
						//异步加载 TODO 暂未实现
						
					}else{
						DictionaryItem example = new DictionaryItem();
						example.setDictionaryId(dic.getId());
						example.setSchoolId(schoolId);
						List<DictionaryItem> itemList = dictionaryItemService.getListNonePaged(example);
						if(itemList!=null){
							for (Iterator iterator = itemList.iterator(); iterator
									.hasNext();) {
								DictionaryItem dictionaryItem = (DictionaryItem) iterator.next();
								sb.append("<option value=\""+dictionaryItem.getId()+"\" ");
								if(dictionaryItem.getId()==defaultValue)sb.append(" selected=\"selected\"");
								sb.append(" >").append(dictionaryItem.getName()).append("</option>\n");
							}
						}
					}
					sb.append(" </select>");
				}
			}
			this.pageContext.getOut().print(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EVAL_BODY_INCLUDE;
	}

	public String getDicId() {
		return dicId;
	}

	public void setDicId(String dicId) {
		this.dicId = dicId;
	}

	public String getDicCode() {
		return dicCode;
	}

	public void setDicCode(String dicCode) {
		this.dicCode = dicCode;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getSync() {
		return sync;
	}

	public void setSync(String sync) {
		this.sync = sync;
	}

	public int getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(int defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public boolean isPleaseChoose() {
		return pleaseChoose;
	}

	public void setPleaseChoose(boolean pleaseChoose) {
		this.pleaseChoose = pleaseChoose;
	}

	public boolean isShowOnly() {
		return showOnly;
	}

	public void setShowOnly(boolean showOnly) {
		this.showOnly = showOnly;
	}
	
}

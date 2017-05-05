package com.boyuyun.common.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.google.common.base.Strings;
/**
 * 在jsp中以下拉列表框形式显示一个枚举
 * @author zhy
 */
public class EnumTag extends TagSupport{
	private String className;//枚举所在类名
	private String name; //html name
	private String id;//html id
	private String style;//css style
	private String cssClass;//css 类
	private int intValue = -1;//默认值
	private boolean pleaseChoose = true;//是否生成请选择
	private boolean showOnly = false;//是否仅显示
	@Override
	public int doEndTag() throws JspException {
		StringBuffer sb = new StringBuffer();
		Class cls;
		try {
			cls = Class.forName(className);
			if(cls.isEnum()){
				Object [] ex = cls.getEnumConstants();
				if(showOnly){
					//仅显示
					if(intValue>0&&intValue<ex.length){
						sb.append(ex[intValue].toString());
					}
				}else{
					sb.append("<select ");
					if(!Strings.isNullOrEmpty(name))sb.append(" name=\""+name+"\"");
					if(!Strings.isNullOrEmpty(id))sb.append(" id=\""+id+"\"");
					if(!Strings.isNullOrEmpty(style))sb.append(" style=\""+style+"\"");
					if(!Strings.isNullOrEmpty(cssClass))sb.append(" class=\""+cssClass+"\"");
					sb.append(" >\n");
					if(pleaseChoose)sb.append("<option value='' >请选择...</option> ");
					if(ex!=null){
						for (int i = 0; i < ex.length; i++) {
							sb.append("<option value=\""+i+"\" ");
							if(intValue==i)sb.append(" selected=\"selected\"");
							sb.append(" >").append(ex[i].toString()).append("</option>\n");
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
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
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
	
	public int getIntValue() {
		return intValue;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

	public boolean isShowOnly() {
		return showOnly;
	}

	public void setShowOnly(boolean showOnly) {
		this.showOnly = showOnly;
	}

	public boolean isPleaseChoose() {
		return pleaseChoose;
	}
	public void setPleaseChoose(boolean pleaseChoose) {
		this.pleaseChoose = pleaseChoose;
	}
}

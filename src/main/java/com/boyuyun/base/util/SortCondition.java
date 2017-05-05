package com.boyuyun.base.util;

import java.util.regex.Pattern;


public class SortCondition {
	private String sort;
	private String order;
	private String alias;
	/**
	 * 校验对于sql语句来说是否安全，只允许输入字母，数字，_和%
	 * 慎用
	 * @return
	 */
	private static final Pattern pattern = Pattern.compile("^[A-Za-z0-9]*(_)*$");
	private static boolean isStrSafeForSQL(String sql){
		return pattern.matcher(sql).matches();
	}
	public String getSort() {
		return sort;
	}
	public String getAliasSafely() {
		if(isStrSafeForSQL(alias)){
			return alias;
		}
		return null;
	}
	public String getSortSafely() {
		if(isStrSafeForSQL(sort)){
			return sort;
		}
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public String getOrderSafely() {
		if(order==null)return "asc";
		if("desc".equals(order.trim().toLowerCase()))return order;
		return "asc";
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
}

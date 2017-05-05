package com.boyuyun.base.util;

import java.util.ArrayList;
import java.util.List;

import com.dhcc.common.db.page.Page;

public class SortablePage extends Page {
	private  List<SortCondition> sortConditions = new ArrayList<SortCondition>();
	public List<SortCondition> getSortConditions() {
		return sortConditions;
	}
	public void setSortConditions(List<SortCondition> sortConditions) {
		this.sortConditions = sortConditions;
	}
}

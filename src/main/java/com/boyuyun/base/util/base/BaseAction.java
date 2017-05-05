package com.boyuyun.base.util.base;

import java.io.IOException;
import java.util.List;

import com.boyuyun.common.json.ByyJsonUtil;
import com.dhcc.common.struts2.DhccActionSupport;

public class BaseAction extends DhccActionSupport{
	/**
	 * 单纯输出
	 * @param msg
	 */
	public void print(boolean msg){
		try {
			response.getWriter().print(msg);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 单纯输出
	 * @param msg
	 */
	public void print(String msg){
		try {
			response.getWriter().print(msg);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 操作失败
	 * @param msg
	 */
	public void printFail(String msg){
		print(ByyJsonUtil.getFailJson(msg));
		executeSuccess = false;
		failMessage = msg;
	}
	/**
	 * 操作成功
	 * @param msg
	 */
	public void printSuccess(String msg){
		print(ByyJsonUtil.getSuccessJson(msg));
		executeSuccess = true;
		failMessage = null;
	}
	public String getFailJson(String msg){
		executeSuccess = false;
		failMessage = msg;
		return ByyJsonUtil.getFailJson(msg);
	}
	public String getSuccessJson(String msg){
		executeSuccess = true;
		failMessage = null;
		return ByyJsonUtil.getSuccessJson(msg);
	}
	private boolean executeSuccess = true;//记录当次操作结果
	private String failMessage;//记录操作失败信息
	private List batchListId;//批量操作时的id
	private String newAddId;//新增的时候id
	public String getNewAddId() {
		return newAddId;
	}
	public void setNewAddId(String newAddId) {
		this.newAddId = newAddId;
	}
	public List getBatchListId() {
		return batchListId;
	}
	public void setBatchListId(List batchListId) {
		this.batchListId = batchListId;
	}
	public boolean isExecuteSuccess() {
		return executeSuccess;
	}
	public String getFailMessage() {
		return failMessage;
	}
}

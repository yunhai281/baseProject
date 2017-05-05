package com.boyuyun.base.util;

import com.dhcc.common.util.FileUtil;

public class ConstantUtil {
	public static final String UPLOADFILEPATH = "attachment";//上传文件路径
	public static final String UPLOAD_FOLDER = FileUtil.getRootPath();//文件的基础路径，例如：i:\\test
	
	public static final int STAGE_KINDER = 0;//幼儿园
	public static final int STAGE_PRIMARY = 1;//小学
	public static final int STAGE_JUNIOR = 2;//初中
	public static final int STAGE_SENIOR = 3;//高中
	public static final String SESSSION_USER_ATTR_NAME="user";//与框架有绑定，名字不要改
	public static final String SESSSION_USER_BEHAVIOR_NAME="behavior";
	public static String SYSTEM_NAME;//系统名称，系统启动时会初始化
	public static String SYSTEM_VERSION;//系统版本，系统启动时会初始化
	public static final String USER_DEFAULT_PASSWORD = "666666";//用户初始密码
	public static final String TREE_ICON_GOVERNMENT_BU = "./resources/images/ztree-icons/bu.png";//树形结构节点图标，管理机构
	public static final String TREE_ICON_GOVERNMENT_TING = "./resources/images/ztree-icons/ting.png";//树形结构节点图标，管理机构
	public static final String TREE_ICON_GOVERNMENT_JU = "./resources/images/ztree-icons/ju.png";//树形结构节点图标，管理机构
	public static final String TREE_ICON_GOVERNMENT_KESHI = "./resources/images/ztree-icons/ke.png";//树形结构节点图标，管理机构
	public static final String TREE_ICON_SCHOOL = "./resources/images/ztree-icons/school-icon.gif";//树形结构节点图标，学校
	public static final String TREE_ICON_PEOPEL = "./resources/images/ztree-icons/government-icon.gif";//树形结构节点图标，人员
	
	//oa 接口开始
	public static String BYY_OA_URL;
	
	public static  String BYYOA_GOVERNMENT_WSDL_URL;
	public static  String BYYOA_USER_WSDL_URL;
	//dudao 接口开始
	public static String DUDAO_URL;
	public static String KGGRADING_URL;
}

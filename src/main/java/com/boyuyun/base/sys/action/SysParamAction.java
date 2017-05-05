package com.boyuyun.base.sys.action;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.boyuyun.base.sys.biz.SysParamBiz;
import com.boyuyun.base.sys.entity.SysParam;
import com.boyuyun.base.util.SystemParam;
import com.boyuyun.base.util.base.BaseAction;
import com.boyuyun.base.util.consts.SysParamKey;
import com.boyuyun.common.annotation.LogThisOperate;
import com.boyuyun.common.autolog.OperateType;
import com.boyuyun.common.json.ByyJsonUtil;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @author happyss
 * @fileName com.boyuyun.base.sys.action.ParamSetAction.java
 * @version 1.0
 * @createTime 2017-4-6 上午11:25:02
 * @description 类说明
 */
public class SysParamAction extends BaseAction implements ModelDriven<SysParam>{
	private SysParam param = new SysParam();
	@Resource
	private SysParamBiz paramService;
	
	/**
	 * 跳转到参数设置界面
	 * @author LHui
	 * @since 2017-2-20 上午10:36:53
	 * @return
	 */
	public String toView(){
		return "view";
	}
	
	

	/**
	 * @author happyss
	 * @creteTime 2017-4-6 上午11:56:45
	 * @description 参数设置信息
	 * @return
	 * @throws Exception
	 */
	public String getBean()throws Exception{
		String result = null;
		List<SysParam> param = paramService.getAll();
		if(param !=null){
			JSONObject json = new JSONObject();
			for(int i=0;i<param.size();i++){
				if(param.get(i).getParamKey().equals(SysParamKey.SYS_NAME)){
					json.put(SysParamKey.SYS_NAME, param.get(i).getParamValue());
				}else if(param.get(i).getParamKey().equals(SysParamKey.COPY_RIGHT_INFO)){
					json.put(SysParamKey.COPY_RIGHT_INFO, param.get(i).getParamValue());
				}else if(param.get(i).getParamKey().equals(SysParamKey.TEL)){
					json.put(SysParamKey.TEL, param.get(i).getParamValue());
				}else if(param.get(i).getParamKey().equals(SysParamKey.PICPATH)){
					json.put(SysParamKey.PICPATH, param.get(i).getParamValue());
				}else if(param.get(i).getParamKey().equals(SysParamKey.ICP)){
					json.put(SysParamKey.ICP, param.get(i).getParamValue());
				}
			}
			result = ByyJsonUtil.serialize(json);
		}else{
			result = this.getFailJson("网络异常!"); 
		}
		this.print(result);
		return null;
	}
	
	
	/**
	 * @author happyss
	 * @creteTime 2017-4-6 上午11:57:25
	 * @description 参数设置保存
	 * @return
	 * @throws Exception
	 */
	@LogThisOperate(module="系统设置",operateType=OperateType.修改)
	public String save() throws Exception{
		String result = this.getFailJson("操作失败");
		String paramKey = request.getParameter("paramKey");
		if(paramKey!=null && !"".equals(paramKey)){
			boolean saveStatus = paramService.save(param);
			if(saveStatus){
				result = this.getSuccessJson("操作成功");
			}else{
				result = this.getSuccessJson("操作失败");
			}
		}
		SystemParam.refresh();
		this.print(result);
		return null;
	}



	@Override
	public SysParam getModel() {
		return param;
	}
}

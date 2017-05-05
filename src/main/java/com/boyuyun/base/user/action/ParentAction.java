package com.boyuyun.base.user.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;

import com.boyuyun.base.sys.biz.DictionaryBiz;
import com.boyuyun.base.sys.biz.DictionaryItemBiz;
import com.boyuyun.base.sys.entity.Dictionary;
import com.boyuyun.base.sys.entity.DictionaryItem;
import com.boyuyun.base.user.biz.ParentBiz;
import com.boyuyun.base.user.biz.UserBiz;
import com.boyuyun.base.user.entity.Parent;
import com.boyuyun.base.user.entity.User;
import com.boyuyun.base.util.base.BaseAction;
import com.boyuyun.base.util.consts.UserType;
import com.boyuyun.common.annotation.LogThisOperate;
import com.boyuyun.common.autolog.OperateType;
import com.boyuyun.common.json.ByyJsonUtil;
import com.boyuyun.common.util.ByyDateUtil;
import com.boyuyun.common.util.ByyStringUtil;
import com.dhcc.common.util.StringUtil;
import com.opensymphony.xwork2.ModelDriven;


@Controller
public class ParentAction extends BaseAction implements ModelDriven<Parent>{
	@Resource
	private ParentBiz parentService;
	@Resource
	private UserBiz userService;
	@Resource
	private DictionaryBiz dictionaryService;
	@Resource
	private DictionaryItemBiz dictionaryItemService;

	private Parent parent = new Parent();
	@Override
	public Parent getModel() {
		return (Parent) initPage(parent);
	}
	
	public String toList() {
		return "toList";
	}

	/**
	 * @author caoyy
	 * @date 2017-03-21
	 * @see 跳转到家长新建页面
	 * @return
	 */
	public String toAdd() throws Exception{
		Dictionary dic = dictionaryService.getByCode("Relation");
		if(dic!=null){
			DictionaryItem exampleItem = new DictionaryItem();
			exampleItem.setDictionaryId(dic.getId());
			List<DictionaryItem> itemList = dictionaryItemService.getListNonePaged(exampleItem);
			request.setAttribute("relations", itemList);
		}
		return "toView";
	}
	/**
	 * @author caoyy
	 * @date 2017-03-21
	 * @see 跳转到家长详情页面
	 * @return
	 */
	public String toView() {
		return "toDetail";
	}

	/**
	 * @author caoyy
	 * @date 2017-03-21
	 * @see 跳转到家长编辑页面
	 * @return
	 */
	public String toEdit() throws Exception{
		Dictionary dic = dictionaryService.getByCode("Relation");
		if(dic!=null){
			DictionaryItem exampleItem = new DictionaryItem();
			exampleItem.setDictionaryId(dic.getId());
			List<DictionaryItem> itemList = dictionaryItemService.getListNonePaged(exampleItem);
			request.setAttribute("relations", itemList);
		}
		return "toView";
	}

	/**
	 * @author caoyy
	 * @date 2017-03-21
	 * @see 获取家长列表
	 * @return
	 */
	public String getList()throws Exception {
		response.setCharacterEncoding("utf-8");
		String result = "";
		List<Parent> pageInfo = parentService.getListPaged(parent);
		int count = parentService.getListPagedCount(parent);
		result = ByyJsonUtil.serialize(count,pageInfo);
		this.print(result);
		return null;
	}
	/**
	 * @author caoyy
	 * @date 2017-03-21
	 * @see 保存或修改家长记录
	 * @return
	 */
	@LogThisOperate(module="家长管理",operateType=OperateType.新增或修改)
	public String save() throws Exception{
		response.setCharacterEncoding("utf-8");
		System.out.println(parent.toString());
		String result = this.getSuccessJson("保存成功");
		//设置用户类型为家长
		Integer ordinal = UserType.家长.ordinal();
		parent.setUserType(ordinal.toString());
		String md5Pwd = StringUtil.encryptMd5(parent.getPwd());
		parent.setPwd(md5Pwd);
		try {
			if (parent.getId() != null && parent.getId().trim().length() > 0) {
				User userTemp = (User) userService.get(parent.getId());
				parent.setPwd(userTemp.getPwd());
				parentService.update(parent);
				result = this.getSuccessJson("修改成功");
			} else {
				String id = ByyStringUtil.getRandomUUID();
				parent.setId(id);
				parentService.add(parent);
				result = this.getSuccessJson("添加成功");
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
	 * @see 根据Id获取家长记录
	 * @return
	 */
	public String getBean()throws Exception  {
		String result = "";
		try {
			parent = (Parent) parentService.get(parent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		result = ByyJsonUtil.serialize(parent,null,ByyDateUtil.YYYY_MM_DD);
		PrintWriter out = null;
		this.print(result);
		return null;
	}

	/**
	 * @author caoyy
	 * @date 2017-03-21
	 * @see 批量删除家长记录。
	 * @return
	 */
	@LogThisOperate(module="家长管理",operateType=OperateType.删除)
	public String delete()throws Exception {
		String result = "";
		
		try {
			if(StringUtils.isNoneBlank(parent.getId())){
				String[] ids = parent.getId().split(",");
				List<Parent> parents = new ArrayList<Parent>();
				List IDSToDelete = new ArrayList();
				for(String string : ids){
					parent = new Parent();
					parent.setId(string);
					IDSToDelete.add(string);
					parent = parentService.get(parent);
					parents.add(parent);
				}
				if(parents.size()>0){
					this.setBatchListId(IDSToDelete);
					parentService.delete(parents);
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

}

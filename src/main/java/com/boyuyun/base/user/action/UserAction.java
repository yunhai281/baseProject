package com.boyuyun.base.user.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;

import com.boyuyun.base.course.entity.TeacherCourse;
import com.boyuyun.base.user.biz.BureauUserBiz;
import com.boyuyun.base.user.biz.ParentBiz;
import com.boyuyun.base.user.biz.StudentBiz;
import com.boyuyun.base.user.biz.TeacherBiz;
import com.boyuyun.base.user.biz.UserBiz;
import com.boyuyun.base.user.entity.BureauUser;
import com.boyuyun.base.user.entity.Parent;
import com.boyuyun.base.user.entity.Student;
import com.boyuyun.base.user.entity.Teacher;
import com.boyuyun.base.user.entity.User;
import com.boyuyun.base.util.base.BaseAction;
import com.boyuyun.base.util.consts.UserType;
import com.boyuyun.common.annotation.LogThisOperate;
import com.boyuyun.common.autolog.OperateType;
import com.boyuyun.common.json.ByyJsonUtil;
import com.boyuyun.common.util.ByyDateUtil;
import com.boyuyun.common.util.ByyStringUtil;
import com.dhcc.common.struts2.DhccActionSupport;
import com.dhcc.common.util.StringUtil;

import com.opensymphony.xwork2.ModelDriven;

@Controller
public class UserAction extends BaseAction implements ModelDriven<User> {
	// 重置密码
	@Resource
	private UserBiz userService;
	@Resource
	private TeacherBiz teacherService;
	@Resource
	private ParentBiz parentService;
	@Resource
	private StudentBiz studentService;
	@Resource
	private BureauUserBiz bureauService;

	private User user = new User();

	public String toModify() throws Exception {
		return "toModify";
	}

	@LogThisOperate(module = "用户管理", operateType = OperateType.修改密码)
	public String modifyPwd() throws Exception {
		String result = this.getFailJson("修改失败!");
		// 从前台得到新旧密码
		String oldPwd = request.getParameter("oldPwd");
		String newPwd = request.getParameter("newPwd");
		String conNewPwd = request.getParameter("conNewPwd");

		// 从Seesion中获取用户信息
		User use = (User) request.getSession().getAttribute("user");
		// 得到Session中用户ID
		String id = use.getId();
		// 判断输入的密码和原密码是否相同
		if (use.getPwd().equals(oldPwd)) {
			// 后台判断两次输入的新密码是否相同
			if (newPwd.equals(conNewPwd)) {
				this.userService.updatePassword(id, newPwd);
				result = this.getSuccessJson("修改成功!");
			}
		} else {
			result = this.getFailJson("原密码输入错误!");
		}
		this.print(result);
		return null;
	}

	/**
	 * 跳转到选择人员Demo界面
	 * 
	 * @author LHui
	 * @since 2017-3-14 下午3:40:56
	 * @return
	 */
	public String toDemo() {
		return "demo";
	}

	public String toAdd() {
		return "toView";
	}

	public String toEdit() {
		return "toView";
	}

	public String toList() {
		return "toList";
	}

	public String toView() {
		return "toDetail";
	}

	/**
	 * @Description 系统管理员
	 * @author jms
	 * @return
	 * @throws Exception
	 */
	public String getList() throws Exception {
		int count = userService.getListPagedCountAdmin(user);
		List<User> list = userService.getListPagedAdmin(user);
		String result = ByyJsonUtil.serialize(count, list);
		response.getWriter().print(result);
		return null;
	}

	public String getBean() throws Exception {
		String id = this.user.getId();
		User user = (User) userService.get(id);
		String result = ByyJsonUtil.serialize(user, null, ByyDateUtil.YYYY_MM_DD);
		response.getWriter().print(result);
		return null;
	}

	@LogThisOperate(module = "系统管理员", operateType = OperateType.新增或修改)
	public String save() throws Exception {
		String result = this.getFailJson("保存失败");
		// 设置用户类型为学生
		Integer ordinal = UserType.系统管理员.ordinal();
		user.setUserType(ordinal.toString());
		String md5Pwd = StringUtil.encryptMd5(user.getPwd());
		user.setPwd(md5Pwd);
		if (user.getId() != null && user.getId().trim().length() > 0) {
			User userTemp = (User) userService.get(user.getId());
			if (!"admin".equals(userTemp.getUserName())) {
				userService.update(user);
				result = this.getSuccessJson("修改成功");
			} else {
				result = this.getFailJson("admin不允许修改!");
			}
		} else {
			String id = ByyStringUtil.getRandomUUID();
			user.setId(id);
			userService.add(user);
			result = this.getSuccessJson("保存成功");
		}
		response.getWriter().print(result);
		return null;
	}

	@LogThisOperate(module = "系统管理员", operateType = OperateType.删除)
	public String delete() throws Exception {
		String id = user.getId();
		String result = "";
		String[] ids = id.split(",");
		List<User> users = new ArrayList<User>();
		List IDSToDelete = new ArrayList();
		boolean flag = true;
		for (String string : ids) {
			user = (User) userService.get(string);
			users.add(user);
			IDSToDelete.add(string);
			if ("admin".equals(user.getUserName())) {
				flag = false;
				result = this.getFailJson("admin不可删除");
				break;
			}
		}
		if (flag) {
			if (users.size() > 0) {
				userService.deleteAll(users);
				this.setBatchListId(IDSToDelete);
				result = this.getSuccessJson("删除成功");
			} else {
				result = this.getFailJson("删除失败");
			}
		}
		response.getWriter().print(result);
		return null;
	}

	@LogThisOperate(module = "用户管理", operateType = OperateType.重置密码)
	public String resetPassword() throws Exception {
		String id = request.getParameter("id");
		String result = "";
		User user = null;
		String[] ids = id.split(",");
		List<String> batchListId = new ArrayList<String>();
		List<User> users = new ArrayList<User>();
		for (String string : ids) {
			System.out.println(string);
			user = (User) userService.get(string);
			users.add(user);
			batchListId.add(string);
		}
		if (users.size() > 0) {
			userService.resetPassword(users);
			result = this.getSuccessJson("重置密码成功");
			this.setBatchListId(batchListId);
		} else {
			result = this.getFailJson("重置失败");
		}
		this.print(result);
		return null;
	}

	/**
	 * @author happyss
	 * @creteTime 2017-3-29 下午3:08:43
	 * @description 根据学校获取用户信息
	 * @return
	 * @throws Exception
	 */
	public String getUserBySchool() throws Exception {
		String treeType = request.getParameter("treeType");
		String keyword = request.getParameter("keyword");
		String type = request.getParameter("type");
		String schoolId = request.getParameter("schoolId");
		String positionalTitle = StringUtils.isNotBlank(request.getParameter("positionalTitle"))
				&& request.getParameter("positionalTitle").equals("true") ? "true" : "false";
		String ret = null;
		Map map = new HashMap();
		List list = null;
		int count = 0;

		if ("teacher".equals(treeType)) {
			Teacher teacher = new Teacher();
			teacher.setSchoolId(schoolId);
			teacher.setRealName(keyword);
			list = teacherService.getListPaged(teacher);
			count = teacherService.getListPagedCount(teacher);
		} else if ("student".equals(treeType)) {
			Student student = new Student();
			student.setSchoolId(schoolId);
			student.setRealName(keyword);
			list = studentService.getListPaged(student);
			count = studentService.getListPagedCount(student);
		} else if ("parent".equals(treeType)) {
			Parent parent = new Parent();
			parent.setSchoolId(schoolId);
			parent.setRealName(keyword);
			parent.setChildName(positionalTitle);
			list = parentService.getListPaged(parent);
			count = parentService.getListPagedCount(parent);
		} else if ("bureau".equals(treeType)) {
			BureauUser bureau = new BureauUser();
			bureau.setGovernmentId(schoolId);
			bureau.setRealName(keyword);
			bureau.setPostName(positionalTitle);
			list = bureauService.getListPaged(bureau);
			count = bureauService.getListPagedCount(bureau);
		}
		map.put("total", count);
		map.put("rows", list);
		ret = ByyJsonUtil.serialize(map);
		this.print(ret);
		return null;
	}

	@LogThisOperate(module = "用户管理", operateType = OperateType.用户状态修改)
	public String doEnable() throws Exception {
		String id = request.getParameter("id");
		String type = request.getParameter("type");// 判断批量启用禁用
		String result = "";
		String[] ids = id.split(",");
		List<String> batchListId = new ArrayList<String>();
		if (StringUtil.isEmpty(type)) {
			for (String string : ids) {
				batchListId.add(string);
				user = (User) userService.get(string);
				if (user.isEnable()) {
					user.setEnable(false);
				} else {
					user.setEnable(true);
				}
				userService.update(user);
			}
		} else {
			// 批量启用
			if (type.equals("yes")) {
				for (String string : ids) {
					batchListId.add(string);
					user = (User) userService.get(string);
					if (!user.isEnable()) {
						user.setEnable(true);
						userService.update(user);
					}
				}

			} else {
				// 批量禁用
				for (String string : ids) {
					batchListId.add(string);
					user = (User) userService.get(string);
					if (user.isEnable()) {
						user.setEnable(false);
						userService.update(user);
					} 
				}
			}
		}

		if (batchListId.size() > 0) {
			this.setBatchListId(batchListId);
			result = this.getSuccessJson("更新成功");
		} else {
			result = this.getFailJson("更新失败");
		}
		this.print(result);
		return null;
	}

	@Override
	public User getModel() {
		return (User) initPage(user);
	}

	/**
	 * @Description 用户名是否重复
	 * @author jms
	 * @return
	 * @throws Exception
	 */
	public Boolean validateUserName() throws Exception {
		boolean flag = true;
		flag = userService.validateUserName(user.getUserName(), user.getId());
		this.print(flag);
		return null;
	}

}

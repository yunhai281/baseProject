package com.boyuyun.base.user.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.boyuyun.base.util.consts.UserType;
import com.boyuyun.common.annotation.ForViewUse;
import com.dhcc.common.db.page.Page;
import com.google.common.base.Strings;

public class User extends Page implements Serializable{
    private String id;//主键ID

    private String avatar;//头像

    private String email;//email,绑定后的邮箱账号,绑定后用户不能随意更改

    private String mobile;//手机号码,绑定后的手机号,可以使用手机号登录,绑定后用户不能随意更改

    private String nickName;//昵称

    private String pwd;//用户密码,非空

    private int sex;//性别
    private String sexName;//性别

    private boolean enable = true;//是否启用
    
	private String userName;//用户登录名,非空,格式为 学校管理的学号或教师编号等+@学校序列号,如T1001@7784,用户不可修改

    private String userType;//用户类型,非空;教师, 学生, 家长, 公众, 教育局,学校管理员
    private List<UserType> userTypes = new ArrayList<UserType>();//用户类型，多个
    private boolean validateEmail = false;//email是否通过验证

    private boolean validateMobile = false;//手机号码是否通过验证

    public List<UserType> getUserTypes() {
    	if(userTypes.size()==0&&userType!=null){
    		String [] arr = userType.split(",");
    		if(arr!=null&&arr.length>0){
    			for (int i = 0; i < arr.length; i++) {
    				if(Strings.isNullOrEmpty(arr[i]))
    				userTypes.add(UserType.values()[Integer.parseInt(arr[i])]);
				}
    		}
    	}
		return userTypes;
	}

	private Date birthday;//生日

    private String realName;//真实姓名,非空

    private String loginName;//自定义登录名,可以使用此登录,用户可以自定义,但必须全系统唯一
    private String lastLoginIp;//最后登录IP

	@ForViewUse
	private String schoolName;    
    
    public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	private Date lastLoginTime;//最后登录时间
    private String certificateNo; // 证件号
    public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	private String pinyin;//全拼
    private String jianpin;//简拼
    private int nation;//民族
    @ForViewUse
    private String nationName;//民族
	public int getNation() {
		return nation;
	}

	public void setNation(int nation) {
		this.nation = nation;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getJianpin() {
		return jianpin;
	}

	public void setJianpin(String jianpin) {
		this.jianpin = jianpin;
	}

	public String getSexName() {
		return sexName;
	}

	public void setSexName(String sexName) {
		this.sexName = sexName;
	}

    public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }
      
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public boolean isValidateEmail() {
		return validateEmail;
	}

	public void setValidateEmail(boolean validateEmail) {
		this.validateEmail = validateEmail;
	}

	public boolean isValidateMobile() {
		return validateMobile;
	}

	public void setValidateMobile(boolean validateMobile) {
		this.validateMobile = validateMobile;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getNationName() {
		return nationName;
	}

	public void setNationName(String nationName) {
		this.nationName = nationName;
	}
	
	
}
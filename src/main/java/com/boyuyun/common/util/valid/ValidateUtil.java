package com.boyuyun.common.util.valid;

import java.util.regex.Pattern;

public class ValidateUtil {
	private static ValidateUtil instance=null;
	public static final String regexPhonenumb = "^1[3-9][0-9]{9}$";
	public static final String regexEmail = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$";
	public static final String regexAccount = "^[a-zA-Z_][a-zA-Z0-9]{5,15}$";
	public static final String regexPwd = "^[a-zA-Z_][a-zA-Z0-9]{5,15}$";
	private Pattern pPhonenumb = Pattern.compile(regexPhonenumb);
	private Pattern pEmail = Pattern.compile(regexEmail);
	private Pattern pAccount = Pattern.compile(regexAccount);
	private Pattern pPwd = Pattern.compile(regexPwd);
	
	public static ValidateUtil instance(){
		if(instance==null){
			instance=new ValidateUtil();
		}
		return instance;
	}
	
	public boolean validatePhone(String value){
		return pPhonenumb.matcher(value).matches();
	}
	
	public boolean validateEmail(String value){
		return pEmail.matcher(value).matches();
	}
	
	public boolean validateAccount(String value){
		return pAccount.matcher(value).matches();
	}

	public boolean validatePwd(String value){
		return pPwd.matcher(value).matches();
	}
}

/***
* 登录模块
* @author lx
* @since　2017年2月16日 14:16:08
* @version 1.0
***/
'use strict';

var Login = function( bwin ){
	var thiz = this;
	
	this.nextPwd = function(ev){
		if(ev.keyCode === 13){
			$('input[name="password"]').val('').focus();
		}
	};
	this.submit = function(){
		var data = b('.byy-form').getValues();
		if(data.username == '' || data.username.trim() == ''){
			bwin.msg('请输入用户名');
			return;
		}
		if(data.password == '' || data.password.trim() == ''){
			bwin.msg('请输入密码');
			return;
		}
		var index = bwin.load(1, {
		  shade: [0.1,'#fff']
		});
		data.password = byy.md5(data.password);
		$.ajax({
			url : base+'/login_doLogin.do',
			data : data,
			type : 'POST',
			dataType : 'json',
			success : function( str ){
				bwin.close(index);
				var obj = b.json(str);
				if(obj.success == true || obj.success == 'true'){
					//跳转
					top.location.href = base+'/admin_index.do';
				}else{
					bwin.msg(obj.msg);
				}
			}
		})
	};
	this.toSubmit = function(ev){
		if(ev.keyCode === 13){
			thiz.submit();
		}
	}
	
	this.bindEvent = function(){
		$('input[name="username"]').on('keyup',function(ev){
			thiz.nextPwd(ev);
		});
		$('input[name="password"]').on('keyup',thiz.toSubmit);
		$('#submit').on('click',thiz.submit);
	};
};
b.require('win',function(){
	var login = new Login(byy.win);
	login.bindEvent();
});
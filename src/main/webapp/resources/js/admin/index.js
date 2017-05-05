var main = {
	form : {
		//一些页面初始化工作
		init : function() {
			//1.子菜单点击事件：跳转界面
			$('.byy-nav').delegate('ul li','click',function(){
				var url = $(this).attr('data-url');
				$(".byy-body").load(base+url);
				//由于页面可能存在需要初始化的空间，需要调用下初始化函数
				b('.byy-body').initUI();//控制所有的初始化。如果需要独立的请查找源码 暂时注释掉，因为加上会导致菜单页面不好用
			});
			//右侧显示第一个
			$(".byy-body").load(base+$(".byy-nav .byy-nav-child>li").first().attr("data-url"));
		},
		//事件绑定
		bindEvent : function() {
			//修改密码
			$('.list_modify').click(main.form.modify);
		},
		//跳转到修改密码页面
		modify : function() {
			byy.win.open({
		    	title: '修改密码',
		    	type: 2,
		    	area: ['500px', '300px'],
		    	fixed: false, //不固定
		    	maxmin: true,
		    	content: base+'/user_toModify.do'
		    });
		}
	},
	list : {
		bindEvent : function() {
			//绑定提交按钮
			$('#submit').click(main.list.modifyPwd);
			//绑定关闭按钮
			$('#close').click(main.list.close);
		},
		validator : function() {
			var rules = {
				oldPwd : {required : true,maxlength : 16,minlength : 6},
				newPwd : {required : true,maxlength : 16,minlength : 6},
				conNewPwd : {equalTo:"#newPwd"}
			};
			return byy('.byy-form').validate({
				rules : rules
			});
		},
		modifyPwd: function() {
			var searchData = byy('.byy-form').getValues();
			searchData.oldPwd=byy.md5(searchData.oldPwd);
			searchData.newPwd=byy.md5(searchData.newPwd);
			searchData.conNewPwd=byy.md5(searchData.conNewPwd);
			if(byy('.byy-form').valid()){
				var formIndex = byy.win.load(1);
				$.ajax({
					url : base+'/user_modifyPwd.do',
					type : 'POST',
					data : searchData,
					dataType : 'json',
					success : function(res){
						byy.win.close(formIndex);
						var resobj = byy.json(res);
						if(resobj.msg.indexOf('原密码输入错误') > -1){
							$('#tips').css("display", "");
							$('#pwdError').text("原密码输入错误");
							return false;
						}
						parent.byy.win.closeAll();
						parent.closeWin(resobj);
						//关闭弹窗
					}
				})
			}
		},
		close : function() {
			parent.byy.win.closeAll();
		}
	}
}

var closeWin = function(resobj){
	if(resobj.msg.indexOf('成功') > -1){
		byy.win.msg(resobj.msg,{shift:-1},function(){
			top.location.href='login_logout.do';
		});
	}else{
		byy.win.msg(resobj.msg,{shift:-1},function(){});
	}
}
byy.require(['win','jquery','validator'],function(){
	var h = location.href.toLowerCase();
	byy('.byy-breadcrumb').breadcrumb();
	if(h.indexOf('index') > -1){
		main.form.init();
		main.form.bindEvent();
	}else{
		$('#tips').css("display", "none");
		main.list.bindEvent();
		main.list.validator();
	}	
});

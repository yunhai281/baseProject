var menu = {
	config : {
		toList : base + '/user_getList.do',
		doSaveUrl : base + '/user_save.do',
		getForm : base + '/user_getBean.do',
		doDelUrl: base + '/user_delete.do',
		resetPassword : base + "/user_resetPassword.do", 
		doEnable: base+ "/user_doEnable.do", 
		doValidateUserName: base+ "/user_validateUserName.do",
		
		studentStart : 0,
		selectSchoolP:null,
		selectStudentP:null,
		parentRelation : null,
		students:[], 
		stuRelation :'',
		pagesize : 10,
		form : '.byy-form',
		columns : [
			{checkbox : true,width:40,align:'center' }, 
			{
				column : 'userName',name : '用户名',width : 150,
				formatter : function(v, obj) {
					var str = '';
					if (v.length > 20) {
						str = v.substring(0, 20) + '...';
					} else {
						str = v;
					}
					return '<span title="' + v + '" >' + str + '</span>';
				}
			},
			{column : 'realName' , name : '姓名',width : 130,formatter : function(v ,obj){
				var str='';
				if(v.length>4){
					str = v.substring(0,4)+'...';
				}else{
					str = v;
				}
			    return '<span class="main-color1-font" title="'+v+'">'+str+'</span>';
			}},
			{column : 'sexName' , name : '性别',width : 70,formatter : function(v ,obj){
				var str='';
				if(v.length>20){
					str = v.substring(0,20)+'...';
				}else{
					str = v;
				}
			    return '<span title="'+v+'" >'+str+'</span>';
			}},
			
			{column : 'mobile', name : '手机号',width : 80,formatter : function( v ){
				var str='';
				if(v.length>=12){
					str = v.substring(0,11);
				}else{
					str = v;
				}
			    return str; 
			}},
			{column : 'email', name :'Email',width : 100,formatter : function(v){
				var str='';
				if(v.length>20){
					str = v.substring(0,20)+'...';
				}else{
					str = v;
				}
			    return '<span title="'+v+'" >'+str+'</span>';
			}},
			{
				column : 'enable',name : '启用',width : 70,
				formatter : function(v) {
					var str = '';
					if (v==true||v=="true") {
						str = '是';
					} else {
						str = '否';
					}
					return '<span title="' + str + '" >' + str + '</span>';
				}
			},
			{column : 'dataStatus',name : '操作',width:220,formatter : function(v,obj){
				if(obj.userName!='admin'){
					var flag='';
					flag='<span class="byy-btn primary mini" id="getdel" name="getdel"><i class="fa fa-trash"></i>删除</span><span class="byy-btn primary mini" id="getEdit" name="getEdit"><i class="fa fa-edit"></i>编辑</span>';
					if(obj.enable==true||obj.enable=='true'){
						flag+='<span class="byy-btn primary mini" id="getEnable" name="getEnable"><i class="fa fa-remove"></i>禁用</span>';
					}else{
						flag+='<span class="byy-btn primary mini" id="getEnable" name="getEnable"><i class="fa fa-check"></i>启用</span>'
					}
					return flag;
				}else{
					return '';
				}
			}}
        ]
	},
	list : {
		//事件绑定
		bindEvent : function(){
			//1.查询
			$('.list_search').click(menu.list.loadData);
			//2.重置
			$('.list_reset').click(menu.list.searchReset);
			//3.新增教师
			$('.list_add').click(menu.list.add);
			//4.编辑
			$('.byy-table').on('click','#getEdit',menu.list.edit);
			//5.批量删除
			$('body').on('click','.list_del',menu.list.delList);
			//6.查看
			$('.byy-table').on('click','.main-color1-font',menu.list.view);
			//7.删除
			$('.byy-table').on('click','#getdel',menu.list.del);
			// 8.重置密码
			$('.resetPassword').click(menu.list.resetPassword);
			//10.是否启用
			$('.byy-table').on('click','#getEnable',menu.list.enable);
			//11.批量启用
			$('.batchYes').click(menu.list.batchYes);
			//12.批量禁用
			$('.batchNo').click(menu.list.batchNo);
		},
		//加载表格数据
		loadData : function(opt){
			//1.得到查询框的默认参数
			var searchData = byy('.byy-form').getValues();
			//2.向参数增加分页参数
			searchData.rows = menu.config.pagesize;
			//3.如果有传递过来的分页参数，第几页，需要再增加进来
			if(opt){
				//这里通过查询获取到的page中的rows、page参数为undefined，需要判断并重新赋值一下
				searchData.rows = opt.pagesize || menu.config.pagesize;
				searchData.page = opt.curr || 1;
			}else{
				searchData.page=1;
				searchData.rows = behavior_page_rows||menu.config.pagesize;
			}
			//4.向后台请求，查询列表数据并进行加载
			var listIndex = byy.win.load(1);
			$.ajax({
				url : menu.config.toList,
				data : searchData,
				type : 'POST',
				success : function(res){
					byy.win.close(listIndex);
					var resobj = byy.json(res);
					if(resobj.error){
						byy.win.msg('后台查询错误');
					}
					var total = resobj.total;
                    byy.table({
                        selector : '#dtable',
                        columns : menu.config.columns,
                        data : resobj.rows,//opt && opt.curr == 2 ? data.splice(0,2) : data,
                        border : 0,
                        striped : false
                    });
                    byy.page({
                    	pageArray:commonPageArray,
                        selector : '.pagination',
                        total : total,
                        showTotal : true,
						pagesize : searchData.rows,
                        curr : opt ? opt.curr : 1,
                        callback : function(opt){
                        	menu.list.loadData(opt);
                        }
                    });
				}
			});
		},
		//查询重置
		searchReset :  function(){
			//清空
			$('.byy-form').find('input:not([type=hidden])').val('');
			var cfg = $('.pagination').data('obj');
			menu.list.loadData(cfg);
		},
		//新增
		add : function(){
		    byy.win.open({
		    	title: '新增',
		    	type: 2,
		    	area: ['800px', '330px'],
		    	fixed: false, //不固定
		    	maxmin: true,
		    	content: base+'/user_toAdd.do'
		    });
		},
		edit : function(){
			var data = $(this).parent().parent().data('obj');
            if( !byy.isNull(data.id) ){
            	byy.win.open({
    				title: '编辑',
    				type: 2,
    				area: ['800px', '330px'],
    				fixed: false, //不固定
    				maxmin: true,
    		    	content: base+'/user_toEdit.do?id='+data.id
    			});
            }else{
            	byy.win.msg('未找到相关信息，请刷新重试！');
            }
		},
		view : function(){
			var data = $(this).parent().parent().data('obj');
            if( !byy.isNull(data.id) ){
            	var detailIndex = byy.win.open({
    				title: '查看',
    				type: 2,
    				area: ['800px', '330px'],
    				btn : ['关闭'],
    				yes : function(){
    					byy.win.close(detailIndex);
    				},
    				fixed: false, //不固定
    				maxmin: true,
    		    	content: base+'/user_toView.do?id='+data.id,
    		    	success : function(lo,index){
    		    		var body = byy.win.getChildFrame('form',index);
    		    		byy(body).setValues(data);
    		    	}
    			});
            }else{
            	byy.win.msg('未找到相关信息，请刷新重试！');
            }
		},
		resetPassword : function() {
			var id = $('.byy-table>tbody input[type="checkbox"]:checked').map(function(){
				return $(this).parent().parent().data('obj').id;
			}).get().join(',');
			if(id.length > 0){
				byy.win.confirm('是否确认重置密码?', {
					  icon:3,
	            	  btn: ['是','否'] 
	            }, function(){
	            	$.ajax({
						url: menu.config.resetPassword,
						type: "POST",
						data: {id:id},
						success: function (res) {
							b.win.closeAll();
							var resobj = byy.json(res);
							byy.win.msg(resobj.msg);
							menu.list.searchReset();
						}
					});
	            }, function(){
	            });
			}else{
				byy.win.msg('请至少选中一条记录重置密码!');
			}
		},
		delList : function(){
			var id = $('.byy-table>tbody input[type="checkbox"]:checked').map(function(){
				return $(this).parent().parent().data('obj').id;
			}).get().join(',');
			return null == id || void 0 == id ? void b.win.msg("没有获得相关数据，请刷新后重试！") : void b.win.confirm("是否确定删除系统管理员？", {
				title : '提示',
				icon: 2,
				btn: ["确定", "取消"]
			}, function () {
				$.ajax({
					url: menu.config.doDelUrl,
					type: "POST",
					data: {id:id},
					dataType : 'json',
					success: function (res) {
						b.win.closeAll();
						var resobj = byy.json(res);
						console.log(resobj);
						byy.win.msg(resobj.msg);
						menu.list.searchReset();
					}
				});
			}, function () {})
			/*if(id.length > 0){
				byy.win.confirm('是否确认删除系统管理员?', {
					  icon:3,
	            	  btn: ['是','否'] //按钮
	            }, function(){
	                //删除，此处补充异步跳转后台
	            	$.ajax({
						url: menu.config.doDelUrl,
						type: "POST",
						data: {id:id},
						dataType : 'json',
						success: function (res) {
							b.win.closeAll();
							var resobj = byy.json(res);
							byy.win.msg(resobj.msg);
							menu.list.searchReset();
						}
					});
	            }, function(){
	               //关闭
	            	b.win.closeAll();
					var resobj = byy.json(res);
					byy.win.msg(resobj.msg);
	            });
			}else{
				byy.win.msg('请至少选中一条记录进行删除!');
			}*/
		},
		del : function(a) {
			var id = $(a.currentTarget).parent().parent().data("obj").id;
			return null == id || void 0 == id ? void b.win.msg("没有获得相关数据，请刷新后重试！") : void b.win.confirm("是否确定删除系统管理员？", {
				title : '提示',
				icon: 3,
				btn: ["是", "否"]
			}, function () {
				$.ajax({
					url: menu.config.doDelUrl,
					type: "POST",
					data: {id:id},
					dataType : 'json',
					success: function (res) {
						b.win.closeAll();
						var resobj = byy.json(res);
						byy.win.msg(resobj.msg);
						menu.list.searchReset();
					}
				});
			}, function () {})
		},
		enable : function(a) {
			var id = $(a.currentTarget).parent().parent().data("obj").id;
			var enable= $(a.currentTarget).parent().parent().data("obj").enable;
			var str=''; 
			if(enable==true || enable=='true'){
				str ='是否禁用该用户?';
			}else{
				str ='是否启用该用户?';
			}
			if (!byy.isNull(id) && id != '') {
				byy.win.confirm(str, {
					  icon:3,
			      	  btn: ['是','否'] //按钮
			      }, function(){
			      	$.ajax({
							url: menu.config.doEnable,
							type: "POST",
							data: {id:id},
							dataType : 'json',
							traditional:true,
							success: function (res) {
								b.win.closeAll();
								var resobj = byy.json(res);
								byy.win.msg(resobj.msg);
								menu.list.searchReset();
							}
						});
			      	
			      }, function(){
			         //关闭
			      });
			} 
		},
		batchYes : function() {
			var id = $('.byy-table>tbody input[type="checkbox"]:checked').map(function() {
				return $(this).parent().parent().data('obj').id;
			}).get().join(',');
			if (id.length > 0) {
				byy.win.confirm('是否批量启用用户?', {
					icon : 3,
					btn : [ '是', '否' ]
				// 按钮
				}, function() {
					$.ajax({
						url : menu.config.doEnable,
						type : "POST",
						data : {id : id,type :'yes' },
						dataType : 'json',
						success : function(res) {
							b.win.closeAll();
							var resobj = byy.json(res);
							byy.win.msg(resobj.msg);
							menu.list.searchReset();
						}
					});
				}, function() {
					// 关闭
				});
			} else {
				byy.win.msg('请至少选中一条记录进行操作!');
			}
		},
		batchNo : function() {
			var id = $('.byy-table>tbody input[type="checkbox"]:checked').map(function() {
				return $(this).parent().parent().data('obj').id;
			}).get().join(',');
			if (id.length > 0) {
				byy.win.confirm('是否批量禁用用户?', {
					icon : 3,
					btn : [ '是', '否' ]
				// 按钮
				}, function() {
					// 启用/禁用通用
					$.ajax({
						url : menu.config.doEnable,
						type : "POST",
						data : {id : id,type :'no' },
						dataType : 'json',
						success : function(res) {
							b.win.closeAll();
							var resobj = byy.json(res);
							byy.win.msg(resobj.msg);
							menu.list.searchReset();
						}
					});
				}, function() {
					// 关闭
				});
			} else {
				byy.win.msg('请至少选中一条记录进行操作!');
			}
		}
	},
	form : {
		bindEvent : function(){
			//1.提交按钮绑定事件
            $('#submit').on('click',menu.form.save);
			//2.关闭按钮绑定事件
            $('#close').on('click',menu.form.close);
            var iframe =window.frameElement.id;
		},
		view : function(){
			//1.获取列表界面传递过来的ID
			var id = byy.getSearch('id');
			menu.form.validator();
			if(id != null && id != undefined && id != ''){
				menu.form.loadData(id);
			}
		},
		loadData : function(id){
			$.ajax({
				url : menu.config.getForm,
				type : 'POST',
				data : {
					id : id
				},
				dataType : 'json',
				success : function(res){
					var resobj = byy.json(res);
					byy('form').setValues(resobj);
					$('input[name="pwd"]').parent().parent().remove();
				}
			});
		},
		validator : function(){
			var rules = {
				userName : {
					required : true ,
					remote : {
						url : menu.config.doValidateUserName,
						type : 'POST',
						dataType : 'json',
						async: false,
						data : {
							name : function(){
								return $('input[name=name]').val();
							},
							id : function(){
								var id =  $('input[name=id]').val();		
								if(id==""||id==undefined){
									id = 0;
								}
								return id;
							}
						}		
					}	
				},
				realName : {required: true},
				pwd :{required : true},
				email :{email : true},
				mobile :{phone :true}
				
			};
			return $(menu.config.form).validate({
				focusInvalid: true,
				errorPlacement : function(error,element){
					byy.win.tips(error.get(0).innerHTML,element);
				},
				rules:rules,
				messages:{
					userName:{
						remote:"用户名已存在"
					} 
				}
			});
		},
		save : function(){
			var searchData = byy('.byy-form').getValues();
			if($(menu.config.form).valid()){
				var formIndex = byy.win.load(1); 
				//提交的时候将提交按钮禁用，防止重复提交
				$('#submit').off('click').addClass('disabled');
				$.ajax({
					url : menu.config.doSaveUrl,
					type : 'POST',
					data : searchData,
					dataType : 'json',
					success : function(res){
						byy.win.close(formIndex);
						var resobj = byy.json(res);
						//更新列表数据
						var pageobj = $(parent.document.body).find('.pagination');
						var pagecurr = pageobj.attr('page-curr'),pagesize = pageobj.attr('page-size');
						parent.menu.list.loadData({
							curr : pagecurr,
							pagesize : pagesize
						});
						//关闭弹窗
						parent.byy.win.closeAll();
						parent.byy.win.msg(resobj.msg,{shift:-1},function(){
							
						});
					}
				});
				
			}
		},
		close : function(){
            parent.byy.win.closeAll();
        }
	}
}; 
byy.require(['jquery','win','table','page'],function(){
    var h = location.href;
    byy('.byy-breadcrumb').breadcrumb();
    if(h.toLowerCase().indexOf('edit')>-1 || h.toLowerCase().indexOf('view')>-1|| h.toLowerCase().indexOf('add')>-1){
    	menu.form.bindEvent();
    	menu.form.view();
    }else{
    	menu.list.bindEvent();
    	menu.list.loadData();
    }
});
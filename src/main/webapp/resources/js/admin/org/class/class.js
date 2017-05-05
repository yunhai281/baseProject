var menu = {
	config : {
		toList : base + '/org_class_getList.do'+'?gradeId='+global_gradeId,
		doSaveUrl : base + '/org_class_save.do',
		getForm : base + '/org_class_getBean.do',
		doDelUrl: base + '/org_class_delete.do',
		getSysGrade : base + '/org_class_getGradeList.do',
		pagesize : 10,
		form : '.byy-form',
		columns : [
			{checkbox : true,width:40,align:'center'}, 
			{column : 'name' , name : '班级名称',width : 150,formatter : function(v ,obj){
				var str='';
				if(v.length>9){
					str = v.substring(0,9)+'...';
				}else{
					str = v;
				}
			    return '<span class="main-color1-font" title="'+v+'">'+str+'</span>';
			}},
			{column : 'gradeName', name :'所属年级',width : 110,formatter : function(v){
				var str='';
				if(v.length>15){
					str = v.substring(0,15)+'...';
				}else{
					str = v;
				}
			    return '<span title="'+v+'" >'+str+'</span>';
			}},
			{column : 'beginYear' , name : '开始学年',width : 90,formatter : function(v ,obj){
				var str='';
				if(v.length>5){
					str = v.substring(0,5);
				}else{
					str = v;
				}
			    return '<span title="'+v+'" >'+str+'</span>';
			}},
			{column : 'statusName' , name : '班级状态',width : 90,formatter : function(v ,obj){
				var str='';
				if(v.length>10){
					str = v.substring(0,10);
				}else{
					str = v;
				}
				return '<span title="'+v+'" >'+str+'</span>';
			}},
			{column : 'dataStatus',name : '操作',width:120,formatter : function(v,obj){
				var flag='';
				flag='<span class="byy-btn primary mini" id="getdel" name="getdel"><i class="fa fa-trash"></i>删除</span><span class="byy-btn primary mini" id="getEdit" name="getEdit"><i class="fa fa-edit"></i>编辑</span>';
				return flag;
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
			//3.新增班级
			$('.list_add').click(menu.list.add);
			//4.编辑
			$('.byy-table').on('click','#getEdit',menu.list.edit);
			//5.批量删除
			$('body').on('click','.list_del',menu.list.delList);
			//6.查看
			$('.byy-table').on('click','.main-color1-font',menu.list.view);
			//7.删除
			$('.byy-table').on('click','#getdel',menu.list.del);
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
					var resobj = byy.json (res);
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
		//新增班级
		add : function(){
		    byy.win.open({
		    	title: '新增',
		    	type: 2,
		    	area: ['700px', '450px'],
		    	fixed: false, //不固定
		    	maxmin: true,
		    	content: base+'/org_class_toAdd.do?gradeId='+global_gradeId
		    });
		},
		edit : function(){
			var data = $(this).parent().parent().data('obj');
            if( !byy.isNull(data.id) ){
            	byy.win.open({
    				title: '编辑',
    				type: 2,
    				area: ['700px', '450px'],
    				fixed: false, //不固定
    				maxmin: true,
    		    	content: base+'/org_class_toEdit.do?id='+data.id
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
    				area: ['700px', '500px'],
    				btn : ['关闭'],
    				yes : function(){
    					byy.win.close(detailIndex);
    				},
    				fixed: false, //不固定
    				maxmin: true,
    		    	content: base+'/org_class_toView.do?id='+data.id,
    		    	success : function(lo,index){
    		    		var body = byy.win.getChildFrame('form',index);
    		    		byy(body).setValues(data);
    		    	}
    			});
            }else{
            	byy.win.msg('未找到相关信息，请刷新重试！');
            }
		},
		delList : function(){
			var id = $('.byy-table>tbody input[type="checkbox"]:checked').map(function(){
				return $(this).parent().parent().data('obj').id;
			}).get().join(',');
			if(id.length > 0){
				byy.win.confirm('是否确认删除班级?', {
					  icon:3,
					  title: '提示',
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
	            });
			}else{
				byy.win.msg('请至少选中一条记录进行删除!');
			}
		},
		del : function(a) {
			var id = $(a.currentTarget).parent().parent().data("obj").id;
			return null == id || void 0 == id ? void b.win.msg("没有获得相关数据，请刷新后重试！") : void b.win.confirm("是否确定删除记录？", {
				icon: 3,
				title: '提示',
            	btn: ['是','否'] //按钮
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
		}
	},
	form : {
		bindEvent : function(){
			//1.提交按钮绑定事件
            $('#submit').on('click',menu.form.save);
			//2.关闭按钮绑定事件
            $('#close').on('click',menu.form.close);
		},
		view : function(){
			//1.获取列表界面传递过来的ID
			var id = byy.getSearch('id');
			var formIndex = byy.win.load(1);//新增遮盖防止加载信息时提交
			//添加验证方法
			menu.form.loadSysGrade(id);
			byy.win.close(formIndex);
			menu.form.validator();
			
		},
		loadSysGrade : function (id) {
			$.ajax({
				url :menu.config.getSysGrade,
				type :'POST',
				dataType : 'json',
				success :function(res){
					var orgGrade = byy.json(res);
					for (var i = 0; i < orgGrade.length; i++) {
						var sysgrade = orgGrade[i];
						$("#gradeId").append('<option value="'+sysgrade.id +'">'+sysgrade.name+'</option>');
					}
					byy("#gradeId").select();
					if(id != null && id != undefined && id != ''){
						menu.form.loadData(id);
					}
				}
			});
			
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
				}
			});
		},
		
		validator : function(){
				jQuery.validator.addMethod("enClName", function(value, element) {       
	 			return this.optional(element) || /^[a-zA-Z0-9_\u4E00-\u9FA5]+$/.test(value);       
	 		}, '请输入正确的中,英文,下划线和数字.');
			var rules = {
				name : {required : true,enClName : true,maxlength:30},
				shortName : {enClName : true,maxlength:30},
				status : {required: true},
				gradeId : {required : true},
				beginYear : {required :true,
							maxlength:4,
							minlength:4,
						    digits:true
							}
			};
			return byy(menu.config.form).validate({
				rules:rules
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
						parent.byy.win.closeAll();
						parent.byy.win.msg(resobj.msg,{shift:-1},function(){
							//关闭弹窗
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
byy.require(['jquery','win','table','page','validator'],function(){
    var h = location.href;
//    alert(h.toLowerCase().indexOf('edit'));
    byy('.byy-breadcrumb').breadcrumb();
    if(h.toLowerCase().indexOf('edit',0)>-1 ||  h.toLowerCase().indexOf('add',0)>-1|| h.toLowerCase().indexOf('view',0)>-1){
    	menu.form.bindEvent();
    	menu.form.view();
    }else{
    	menu.list.bindEvent();
    	menu.list.loadData();
    }
});
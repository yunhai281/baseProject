var menu = {
	config : {
		toList : base + '/sys_grade_getList.do',
		doSaveUrl : base + '/sys_grade_save.do',
		getForm : base + '/sys_grade_getBean.do',
		doDelUrl : base + '/sys_grade_delete.do',
		getMaxUrl : base + '/sys_grade_getMaxSortNum.do',
		doValidateSortNum: base + '/sys_grade_getValidateSortNum.do',
		updateSortNumUrl: base + '/sys_grade_updateSortNum.do',
		pagesize : 10,
		form : '.byy-form',
		columns : [
			{checkbox : true,width:40,align:'center'}, 
			{column : 'name', name : '年级名称',width : 150},
			{column : 'stage' , name : '学段',width : 150,formatter : function(v,obj){
			 var stage = ['幼儿园','小学','初中','高中'];
			 return stage[v];
			}},
			{column : 'shortName', name :'年级简称',width : 100},
			{column : 'sortNum', name :'序号',width : 40,align:'center'},
			{column : 'dataStatus',name : '操作',width:230,formatter : function(v,obj){
				var flag='';
				flag='<span class="byy-btn primary mini" id="getDel" name="getDel"><i class="fa fa-trash"></i>删除</span><span class="byy-btn primary mini" id="getEdit" name="getEdit"><i class="fa fa-edit"></i>编辑</span>';
				flag+='<span class="byy-btn primary mini" onclick="move(\''+obj.id+'\',\'up\')"><i class="fa fa-arrow-up"></i>上移</span><span class="byy-btn primary mini" onclick="move(\''+obj.id+'\',\'down\')"><i class="fa fa-arrow-down"></i>下移</span>';
				if(obj.enable == 'true' || obj.enable == true){
					flag += '<span class="byy-btn primary mini" name="dataStatus">禁用</span>';
				}if(obj.enable == 'false' || obj.enable == false){
					flag += '<span class="byy-btn primary mini" name="dataStatus">启用</span>';
				}
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
			//3.新增年级
			$('.list_add').click(menu.list.add);
			//4.编辑
			$('.byy-table').on('click','#getEdit',menu.list.edit);
			//5.批量删除
			$('body').on('click','.list_del',menu.list.delList);
			//6.查看
			$('.byy-table').on('click','.main-color1-font',menu.list.view);
			//7.删除
			$('.byy-table').on('click',"#getDel",menu.list.del);
			//7.删除
			$('.byy-table').on('click',"#dataSortNum",menu.list.sortNum);
			
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
		//新增年级
		add : function(){
		    byy.win.open({
		    	title: '新增',
		    	type: 2,
		    	area: ['500px', '350px'],
		    	fixed: false, //不固定
		    	maxmin: true,
		    	content: base+'/sys_grade_toView.do'
		    });
		},
		edit : function(){
			var data = $(this).parent().parent().data('obj');
            if( !byy.isNull(data.id) ){
            	byy.win.open({
    				title: '编辑',
    				type: 2,
    				area: ['500px', '350px'],
    				fixed: false, //不固定
    				maxmin: true,
    		    	content: base+'/sys_grade_toView.do?id='+data.id
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
    				area: ['850px', '350px'],
    				btn : ['关闭'],
    				yes : function(){
    					byy.win.close(detailIndex);
    				},
    				fixed: false, //不固定
    				maxmin: true,
    		    	content: base+'/menu_detail.do?id='+data.id,
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
			var ids = $('.byy-table>tbody input[type="checkbox"]:checked').map(function(){
				return $(this).parent().parent().data('obj').id;
			}).get();
			if(ids.length > 0){
				byy.win.confirm('是否确认删除记录?', {
					  icon:3,
					  title: '提示',
	            	  btn: ['是','否'] //按钮
	            }, function(){
	            	$.ajax({
						url: menu.config.doDelUrl,
						type: "POST",
						data: {id:ids.join(',')},
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
			}else{
				byy.win.msg('请至少选中一条记录进行删除!');
			}
		},
		del :function (a){
			var id = $(a.currentTarget).parent().parent().data("obj").id;
			return null == id || void 0 == id ? void b.win.msg("没有获得相关数据，请刷新后重试！") : void b.win.confirm("是否确定删除记录？", {
				icon:3,
				  title: '提示',
          	  btn: ['是','否'] //按钮
          }, function(){
          	$.ajax({
					url: menu.config.doDelUrl,
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
          })
		},
		sortNum:function (a){
			var id = $(a.currentTarget).parent().parent().data("obj").id;
			var html='<input type="text"  style="width: 15px;" name="sort" class="sort" /><span class="byy-btn primary mini" style="cursor: pointer;margin-left: 2px;" onclick="menu.list.updateSortNum(\''+id+'\',this);" >保存</span>';
			$(a.currentTarget).parent().html(html); 
		},
		updateSortNum:function (id,btn){
			var sort =  $(btn).parent().find('.sort').val();
			if(/^\d+$/.test( sort )){
				$.ajax({
					url: menu.config.updateSortNumUrl,
					type: "POST",
					data: {id:id,sort:sort},
					dataType : 'json',
					success: function (res) {
						b.win.closeAll();
						var resobj = byy.json(res);
						byy.win.msg(resobj.msg);
						menu.list.searchReset();
					}
				});
			}else{
				byy.win.msg('请输入正整数!');
			}
			
			
		},
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
			menu.form.validator();
			if(id != null && id != undefined && id != ''){
				menu.form.loadData(id);
			}else{
				menu.form.loadMax();
			}
		},
		loadMax : function(){
			$.ajax({
				url : menu.config.getMaxUrl,
				type : 'POST',
				dataType : 'json',
				success : function(res){
					var resobj = byy.json(res);
					//byy('form').setValues(resobj);
					$('input[name=sortNum]').val(resobj)
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
			var rules = {
				name : {required : true,maxlength:30 },
				stage : {required : true },
				shortName:{required:true,maxlength:30},
				sortNum:{digits:true,maxlength:3,
					remote : {
						url : menu.config.doValidateSortNum,
						type : 'POST',
						dataType : 'json',
						async: false,
						data : {
							sortNum : function(){
								return $('input[name="sortNum"]').val();
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
				}
			};
			return byy(menu.config.form).validate({
				rules : rules,
				messages:{
					sortNum:{
						remote:"序号重复"
					} 
				}
			});
		},
		save : function(){
			var searchData = byy('.byy-form').getValues();
			if(byy(menu.config.form).valid()){
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
			/*if(searchData.name==''){
				flag=false;
				msg+='请输入年级名称!<br/>';
			}
			if(searchData.code==''){
				flag=false;
				msg+='请输入年级编号!<br/>';
			}
			if(searchData.icon==''){
				flag=false;
				msg+='请选择年级图标!<br/>';
			}
			if(searchData.developer==''){
				flag=false;
				msg+='请输入开发者!<br/>';
			}*/
		},
		close : function(){
            parent.byy.win.closeAll();
        }
	}
};

byy.require(['jquery','win','table','validator','page'],function(){
    var h = location.href.toLowerCase();
    byy('.byy-breadcrumb').breadcrumb();
    if(h.indexOf('view') > -1){
    	menu.form.bindEvent();
    	menu.form.view();
    }else{
    	menu.list.bindEvent();
    	menu.list.loadData();
    }
});

function move(id,type){
	$.ajax({
		url : base + '/sys_grade_move.do',
		type : 'POST',
		data : {
			id : id,
			type:type
		},
		dataType : 'json',
		success : function(res){
			var resobj = byy.json(res);
			menu.list.loadData();
			byy.win.msg(resobj.msg,{shift:-1},function(){
				
			});
		}
	});
}
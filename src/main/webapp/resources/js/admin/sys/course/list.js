var main = {
	config : {
		toView  : base + '/course_toView.do',
		save    : base + '/course_save.do',
		getBean : base + '/course_getBean.do',
		getList : base + '/course_getList.do',
		deleteUrl : base +'/course_delete.do',
		pagesize : 10,
		tableObj : null,
		form : '.byy-form',
		tableCfg : {
			selector : '#dtable',
            border : 0,
            striped : false,
            sortSingle : true,
            checkOnSelect : true,
			columns : [
				{checkbox : true,width:40,align:'center'}, 
				{column : 'name' , name : '学科名称',width : 150,formatter : function(v ,obj){
					var str='';
					if(v.length>10){
						str = v.substring(0,10)+'...';
					}else{
						str = v;
					}
				    return '<span class="main-color1-font" title="'+v+'">'+str+'</span>';
				}},
				{column : 'subjectFieldName' , name : '学科领域',width : 100},
				{column : 'subjectCode' , name : '学科代码',width : 150,formatter : function(v ,obj){
					var str='';
					if(v.length>20){
						str = v.substring(0,20)+'...';
					}else{
						str = v;
					}
				    return '<span title="'+v+'" >'+str+'</span>';
				}},				
				{column : 'hours', name : '学期总课时',width : 100,formatter : function( v ){
					var str='';
					if(v.length>=16){
						str = v.substring(0,16);
					}else{
						str = v;
					}
				    return str; 
				}},
				{column : 'mainCourse', name :'是否主课',width : 100,formatter : function(v){
					var str = '';
					if(v == true || v == 'true'){
						str = '是';
					}else if(v == false || v == 'false'){
						str = '否';
					}
					return '<span title="'+v+'" >'+str+'</span>';
				},tname : 't1',sortNo : 'a2',sort : function(){
					main.list.loadData();
				}},
				{column : 'sortNum', name :'序号',width : 100,sortNo : 'a1',tname : 't1',sort : function(a){
					//获得当前的所有的排序属性，传递给加载器
					main.list.loadData();
				}},
				{column : 'dataStatus',name : '操作',width:230,formatter : function(v,obj){
					var flag='';
					flag='<span class="byy-btn primary mini" id="getDel" name="getDel"><i class="fa fa-trash"></i>删除</span><span class="byy-btn primary mini" id="getEdit" name="getEdit"><i class="fa fa-edit"></i>编辑</span>';
					return flag;
				}}
		    ]
		},
		
	},
	list : {
		//事件绑定
		bindEvent : function(){
			//1.查询
			$('.list_search').click(main.list.loadData);
			//2.重置
			$('.list_reset').click(main.list.searchReset);
			//3.新增
			$('.list_add').click(main.list.add);
			//4.编辑
			$('.byy-table').on('click','#getEdit',main.list.edit);
			//5.批量删除
			$('body').on('click','.list_del',main.list.delList);
			//6.查看
			$('.byy-table').on('click','.main-color1-font',main.list.view);
			//7.删除
			$('.byy-table').on('click',"#getDel",main.list.del);			
		},
		//加载列表数据--opt传递过来的分页参数
		//加载表格数据
		loadData : function(opt){
			//1.得到查询框的默认参数
			var searchData = byy('.byy-form').getValues();
			//1.5 获得排序参数
			var sortArr = [{
				sort : 'sortNum',
				order : 'asc',
				tname : 't1'
			},{
				sort : 'mainCourse',
				order : 'asc',
				tname : 't1'
			}];//此处应该填写默认的排序规则
			if(main.config.tableObj){
				sortArr = main.config.tableObj.getSort();
			}
			sortArr.forEach(function(ele,index){
				searchData["sortConditions["+index+"].sort"] = ele.sort;
				searchData["sortConditions["+index+"].order"] = ele.order;
				searchData["sortConditions["+index+"].alias"] = ele.tname;
			});
			//1.6+ 这里对sortMap进行转换适合自己的队列并加到searchData中
//			searchData.sortConditions = sortArr;
			//2.向参数增加分页参数
			searchData.rows = main.config.pagesize;
			//3.如果有传递过来的分页参数，第几页，需要再增加进来
			if(opt){
				//这里通过查询获取到的page中的rows、page参数为undefined，需要判断并重新赋值一下
				searchData.rows = opt.pagesize || main.config.pagesize;
				searchData.page = opt.curr || 1;
			}else{
				searchData.page=1;
				searchData.rows = main.config.pagesize;
			}
			//4.向后台请求，查询列表数据并进行加载
			var listIndex = byy.win.load(1);
			$.ajax({
				url : main.config.getList,
				data : searchData,
				type : 'POST',
				success : function(res){
					byy.win.close(listIndex);
					var resobj = byy.json(res);
					if(resobj.error){
						byy.win.msg('后台查询错误');
					}
					var total = resobj.total;
					if(main.config.tableObj){
						main.config.tableObj.loadData(resobj.rows);
					}else{
						main.config.tableCfg.data = resobj.rows;
						main.config.tableObj = byy.table(main.config.tableCfg);
					}
                    byy.page({
                    	pageArray:commonPageArray,
                        selector : '.pagination',
                        total : total,
                        showTotal : true,
                        pagesize : searchData.rows,
                        curr : opt ? opt.curr : 1,
                        callback : function(opt){
                        	main.list.loadData(opt);
                        }
                    }); 

				}
			});
		},
		//查询重置
		searchReset :  function(){
			//清空
			$('.byy-form').find('input:not([type=hidden])').val('');
			//$('#query').val("");
			//获取分页对象
			var cfg = $('.pagination').data('obj');
			main.list.loadData(cfg);
		},
		//新增学科
		add:function(){
		    byy.win.open({
		    	title: '新增',
		    	type: 2,
		    	area: ['700px', '560px'],
		    	fixed: false, //不固定
		    	maxmin: true,
		    	content: main.config.toView
		    });
		},
		edit : function(){
			var data = $(this).parent().parent().data('obj');
            if( !byy.isNull(data.id) ){
            	byy.win.open({
    				title:'编辑',
    				type: 2,
    				area: ['700px', '560px'],
    				fixed: false, //不固定
    				maxmin: true,
    		    	content: main.config.toView+'?id='+data.id
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
    				area: ['700px', '570px'],
    				btn : ['关闭'],
    				yes : function(){
    					byy.win.close(detailIndex);
    				},
    				fixed: false, //不固定
    				maxmin: true,
    		    	content: base+'/course_detail.do?id='+data.id,
    		    	success : function(lo,index){
    		    		var body = byy.win.getChildFrame('form',index);
						//实现文字显示
    		    		var enable = ['是','否'];
    		    		data.generalCourse = enable[data.generalCourse ? 0 : 1];
    		    		data.mainCourse = enable[data.mainCourse? 0 : 1];
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
					  icon:3,title: '提示',
	            	  btn: ['是','否'] //按钮
	            }, function(){
	            	$.ajax({
						url: main.config.deleteUrl,
						type: "POST",
						data: {id:ids.join(',')},
						dataType : 'json',
						traditional:true,
						success: function (res) {
							b.win.closeAll();
							var resobj = byy.json(res);
							byy.win.msg(resobj.msg);
							main.list.searchReset();
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
				icon: 3,
				title: '提示',
            	btn: ['是','否'] //按钮
			}, function () {
				$.ajax({
					url: main.config.deleteUrl,
					type: "POST",
					data: {id:id},
					dataType : 'json',
					success: function (res) {
						b.win.closeAll();
						var resobj = byy.json(res);
						byy.win.msg(resobj.msg);
						main.list.searchReset();
					}
				});
			}, function () {})
		}		
	},
	form : {
		bindEvent : function(){
			//1.提交按钮绑定事件
            $('#submit').on('click',main.form.save);
			//2.关闭按钮绑定事件
            $('#close').on('click',main.form.close);
		},
		view : function(){
			//1.获取列表界面传递过来的ID
			var id = byy.getSearch('id');
			main.form.validator();
			if(id != null && id != undefined && id != ''){
				main.form.loadData(id);
			}
		},
		loadData : function(id){
			$.ajax({
				url : main.config.getBean,
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
		save : function(){
			var searchData = byy('.byy-form').getValues();
			if($('.byy-form').valid()){
				var formIndex = byy.win.load(1);
				//提交的时候将提交按钮禁用，防止重复提交
				$('#submit').off('click').addClass('disabled');
				$.ajax({
					url : main.config.save,
					type : 'POST',
					data : searchData,
					dataType : 'json',
					success : function(res){
						byy.win.close(formIndex);
						var resobj = byy.json(res);
						//更新列表数据
						var pageobj = $(parent.document.body).find('.pagination');
						var pagecurr = pageobj.attr('page-curr'),pagesize = pageobj.attr('page-size');
						parent.main.list.loadData({
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
        },
		validator : function(){
			var rules = {
				name : {required : true },
				hours : {required : true,digits : true},
				sortNum : {required : true,digits : true},
				subjectField : {required : true},
				subjectCode : {maxlength:60,required : true}
			};
			return byy(main.config.form).validate({
				rules:rules
			});
		}        
	}
};
byy.require(['jquery','win','table','page','validator'],function(){
	var h = location.href.toLowerCase();
	byy('.byy-breadcrumb').breadcrumb();
	if(h.indexOf('view') > -1){
	    main.form.bindEvent();
    	main.form.view();
	}else{
		main.list.bindEvent();
		main.list.loadData();
	}
});
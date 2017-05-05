var main = {
	config : {
		getList : base + '/sso_app_getList.do',
		 toView : base + '/sso_app_toView.do',
		save : base + '/sso_app_save.do',
		deleteUrl : base + '/sso_app_delete.do',
		getBean : base +'/sso_app_getBean.do',
		toDetail : base+'/sso_app_detail.do',
		getMax: base+'/sso_app_getMaxOrder.do',
		pagesize : 10,
		form : '.byy-form',
		maxChars : 200,//文本域的最多字数
		columns : [
			{checkbox : true,width : 40,align:'center'},
			{column : 'name',name : '应用名称',width : 170,formatter : function(v,obj){
				var str = '';
				if(v.length>10){
					str = v.substring(0,10)+'...';
				}else{
					str = v;
				}
				return '<span class="main-color1-font" title="'+v+'">'+str+'</span>';
			}},
			{column : 'applicationType',name : '应用类型',width : 100,formatter : function(v,obj){
				var str = '';
				if(v.length>10){
					str = v.substring(0,20)+'...';
				}else{
					str = v;
				}
				return '<span title="'+v+'">'+str+'</span>';
			}},			
			{column : 'serviceid',name : 'URL模式',width : 150,formatter : function(v,obj){
				var str = '';
				if(v.length>40){
					str = v.substring(0,40)+'...';
				}else{
					str = v;
				}
				return '<span title="'+v+'">'+str+'</span>';
			}},
			{column : 'usecount',name : '使用量',width : 80,formatter : function(v,obj){
				var str = '';
				if(v.length>10){
					str = v.substring(0,10)+'...';
				}else{
					str = v;
				}
				return '<span title="'+v+'">'+str+'</span>';
			}},			
			{column : 'manufacturer',name : '厂商',width : 60,formatter : function(v,obj){
				var str = '';
				if(v.length>10){
					str = v.substring(0,10)+'...';
				}else{
					str = v;
				}
				return '<span title="'+v+'">'+str+'</span>';
			}},			
			{column : 'enabled', name : '是否启用',width : 70,formatter : function(v){
				var str = '';
				if(v == true || v == 'true'){
					str = '启用';
				}else{
					str = '未启用';
				}
				return '<span title="'+v+'">'+str+'</span>';
			}},	
			{column : 'wicketed', name : '是否打开新窗口',width : 100,formatter : function(v){
				var str = '';
				if(v == true || v == 'true'){
					str = '是';
				}else{
					str = '否';
				}
				return '<span title="'+v+'">'+str+'</span>';
			}},				
			{column : 'dataStatus',name : '操作',width:170,formatter : function(v,obj){
				var flag='';
				flag='<span class="byy-btn primary mini" id="getDel" name="getDel"><i class="fa fa-trash"></i>删除</span><span class="byy-btn primary mini" id="getEdit" name="getEdit"><i class="fa fa-edit"></i>编辑</span>';
				return flag;
			}}			
			]
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
		//加载列表数据--opt传过来的分页参数
		loadData : function(opt){
			//1.得到查询框的默认参数
			var searchData = byy('.byy-form').getValues();
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
					byy.table({
                        selector : '#dtable',
                        columns : main.config.columns,
                        data : resobj.rows,//opt && opt.curr == 2 ? data.splice(0,2) : data,
                        border : 0,
                        striped : false
                    });
                    byy.page({
                    	pageArray:commonPageArray,
                        selector : '.pagination',
                        total : total,
                        showTotal : true,
                        pagesize : (opt&&opt.pagesize)?opt.pagesize:(resobj.pagesize?resobj.pagesize:main.config.pagesize),
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
		//新增科目
		add:function(){
		    byy.win.open({
		    	title: '新增',
		    	type: 2,
		    	area: ['850px', '600px'],
		    	fixed: false, //不固定
		    	maxmin: true,
		    	content: main.config.toView
		    });
		},
		edit : function(){
			var data = $(this).parent().parent().data('obj');
            if( !byy.isNull(data.id) ){
            	byy.win.open({
    				title: '编辑',
    				type: 2,
    				area: ['850px', '600px'],
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
    				area: ['850px', '600px'],
    				btn : ['关闭'],
    				yes : function(){
    					byy.win.close(detailIndex);
    				},
    				fixed: false, //不固定
    				maxmin: true,
    		    	content: main.config.toDetail+'?id='+data.id
//    		    	success : function(lo,index){
//    		    		var body = byy.win.getChildFrame('form',index);
//						//实现文字显示
//    		    		var enable = ['是','否'];
//    		    		data.enabled = enable[data.enabled ? 0 : 1];
//    		    		data.wicketed = enable[data.wicketed ? 0 : 1];
//    		    		byy(body).setValues(data);
//    		    		if(data.icon!=null&&data.icon!=''){
//    		    		$(body).find("#IconThum").attr( 'src',base+data.icon);
//    		    		}else{
//    		    		$(body).find("#IconThum").attr( 'src',base+'/plugins/ueditor/dialogs/attachment/images/image.png');
//    		    		}
//    		    	}
    			});
            }else{
            	byy.win.msg('未找到相关信息，请刷新重试！');
            }
		},
		//批量删除
		delList : function(){
			var ids = $('.byy-table>tbody input[type="checkbox"]:checked').map(function(){
				return $(this).parent().parent().data('obj').id;
			}).get();
			if(ids.length > 0){
				byy.win.confirm('是否确认删除记录?', {
					  title : '提示',
					  icon:3,
	            	  btn: ['是','否'] //按钮
	            }, function(){
	            	$.ajax({
						url: main.config.deleteUrl,
						type: "POST",
						data: {ids:ids},
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
		//单个删除
		del :function (a){
			var id = $(a.currentTarget).parent().parent().data("obj");
			return null == id || void 0 == id ? void b.win.msg("没有获得相关数据，请刷新后重试！") : void b.win.confirm("是否确定删除记录？", {
				title : '提示',
				icon: 3,
				btn: ["是", "否"]
			}, function () {
				$.ajax({
					url: main.config.deleteUrl,
					type: "POST",
					data: id,
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
			//绑定上传事件
			var uploader_Excel = byy.upload.create({
    			auto : false,  
				// swf文件路径
    			swf: base + 'plugins/webuploader/Uploader.swf',
    			// 文件接收服务端。
    			server : base+'/FileUpload',
    			fileNumLimit : 1,
    			chunked : true,
    			// 选择文件的按钮。可选。
    			// 内部根据当前运行是创建，可能是input元素，也可能是flash.
    			pick: {
    				id : '#uploadimageDiv',
    				multiple  : false,
         			innerHTML : '<span class="uploadimage byy-btn small pull-right" style="cursor:pointer;padding-top: 0px;" >图片上传</span>'
    			},
		       // 只允许选择图片文件。  
		       accept: {  
		           title: 'Images',  
		           extensions: 'gif,jpg,jpeg,bmp,png',  
		           mimeTypes: 'image/jpg,image/jpeg,image/png'  
		       },  
		       method:'POST'
    		});  
			uploader_Excel.on('fileQueued',function( file ) {
			 
			    var $li = $(".excel-row");
			    $li.attr('id',file.id);
	            uploader_Excel.upload(file);
    		});
			uploader_Excel.on( 'uploadSuccess', function( file ,response) {
    			var $target = $('#'+file.id);
    			//添加返回值，暂时..
    			var object= response[0];
    			var filename = object.filename || '',
    					fileSize = object.size || 0,
    					type = '.'+object.type;
    			$('input[name="filePath"]').val(filename);
				$("#IconThum").attr( 'src', base+filename );
				//清空队列
				uploader_Excel.reset();
    		});    		
		},
		view : function(){
			//1.获取列表界面传递过来的ID
			var id = byy.getSearch('id');
			main.form.validator();			
			if(id != null && id != undefined && id != ''){
				for (var i = 0; i < $("#attributer")[0].options.length; i++) { 
					$("#attributer")[0].options[i].selected=false;
				}
				main.form.loadData(id);
			}else{
				main.form.loadOrber();
			}
		},
		loadOrber:function(){
			$.ajax({
				url : main.config.getMax,
				type : 'POST', 
				dataType : 'json',
				success : function(res){
					var resobj = byy.json(res);
					 $('input[name=evaluation_order]').val(resobj);
				}
			});
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
					//编辑时处理文本域
					$(".no").html('').append(resobj.description.length.toString()+"/"+main.config.maxChars);
					//处理checkbox
					if(!resobj.enabled){
						$('input[name="enabled"]').removeAttr('checked');
					}
					if(!resobj.wicketed){
						$('input[name="wicketed"]').removeAttr('checked');
					}
					//处理头像
					if(resobj.icon!=null&&resobj.icon!=''){
		    			$("#IconThum").attr( 'src',base+resobj.icon);
		    		}else{
		    			$("#IconThum").attr( 'src',base+'/plugins/ueditor/dialogs/attachment/images/image.png');
		    		}			
     	    		for(var j= 0; j<resobj.attributes.length;j++){
			    		for (var i = 0; i < $("#attributer")[0].options.length; i++) { 
			    		 if($("#attributer")[0].options[i].value==resobj.attributes[j]){
							$("#attributer")[0].options[i].selected = true;			    		 
			    		 }
			    		}
		    		}
		    		byy('form').setValues(resobj);	
				}
			});
		},		
		save : function(){
			var searchData = byy('.byy-form').getValues();
			var attr='';
			if (searchData.attributer != undefined) {
				if (searchData.attributer instanceof Array) {
					for (var i = 0; i < searchData.attributer.length; i++) {
						attr += searchData.attributer[i] + ',';
					}
					searchData.attribute = attr;
				}
			}			
			searchData.icon = searchData.filePath;
			if($(main.config.form).valid()){
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
						parent.byy.win.closeAll();
						//更新列表数据
						var pageobj = $(parent.document.body).find('.pagination');
						var pagecurr = pageobj.attr('page-curr'),pagesize = pageobj.attr('page-size');
						parent.main.list.loadData({
							curr : pagecurr,
							pagesize : pagesize
						});
						//关闭弹窗
						parent.byy.win.closeAll();
						parent.byy.win.msg(resobj.msg,{shift:-1}
						);
					}
				});
				
			}
		},
		close : function(){
            parent.byy.win.closeAll();
        },
		validator : function(){
			var rules = {
				name : {required : true,maxlength:60},
				serviceid:{required:true,maxlength:60},
				manufacturer:{required:true,maxlength:60},
				evaluation_order : {digits : true,maxlength:6},
				url: {required:true},
				applicationType : {maxlength:60}
			};
			return byy(main.config.form).validate({
				rules:rules
			});
		}        
	},
	detail : {
			init : function(){
			var id = byy.getSearch('id');
			main.detail.loadData(id);
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
					var data = byy.json(res);
					//实现文字显示
		    		var enable = ['是','否'];
		    		data.enabled = enable[data.enabled ? 0 : 1];
		    		data.wicketed = enable[data.wicketed ? 0 : 1];
		    		data.attributes = data.attributes.join(",");
		    		byy('form').setValues(data);
		    		if(data.icon!=null&&data.icon!=''){
		    		$('.byy-form-item').find("#IconThum").attr( 'src',base+data.icon);
		    		}else{
		    		$('.byy-form-item').find("#IconThum").attr( 'src',base+'/plugins/ueditor/dialogs/attachment/images/image.png');
		    		}
		    		
				}
			});
		}
	}
};
byy.require(['jquery','win','table','page','upload','validator'],function(){
	var h = location.href;
	byy('.byy-breadcrumb').breadcrumb();
	if(h.indexOf('index') > -1){
		main.list.bindEvent();
		main.list.loadData();
	}else if(h.indexOf('toView') > -1){
		main.form.bindEvent();
		main.form.view();
	}else{
		main.detail.init();
	}
});
//额外处理的函数--textarea的字数统计功能
function checkLen(obj)  
{ 
    $(".no").html('').append(obj.value.length.toString()+"/"+main.config.maxChars);
}
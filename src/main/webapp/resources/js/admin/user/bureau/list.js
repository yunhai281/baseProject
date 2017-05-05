var menu = {
	config : {
		getTreeUrl : base + '/government_getList.do',
		toList : base + '/bureau_getList.do',
		doSaveUrl : base + '/bureau_save.do',
		getForm : base + '/bureau_getBean.do',
		doDelUrl: base + '/bureau_delete.do',
		doImportUrl : base + "/bureau_importExcel.do",
		doSortnumUrl: base + "/bureau_updateSortnum.do",
		doValidateUserName: base+ "/user_validateUserName.do",
		doEnable: base+ "/user_doEnable.do",
		pagesize : 10,
		form : '.byy-form',
		treeObj : null,
		isEdit:true,
		governmentId:'',
		treeset : {
			view : {
				selectedMulti : false
			},
			callback : {
				onDblClick : function(){
				},
				onClick : function(event,treeId,treeNode,flag){
					var id = treeNode.id;
					var listIndex = byy.win.load(1);
					$("#governmentId").val(id);
					$("#governmentName").val(treeNode.name);
					$.ajax({
						url : menu.config.detailurl,
						type : 'POST',
						data : {
							id :  id
						},
						success : function(res){
							byy.win.close(listIndex);
							var resobj = byy.json(res);
							//清空之前的值
							$('.byy-form .form-detail').html('');
							byy('form').setValues(resobj);
							//地域赋值
							if(!byy.isEmptyObject( resobj )){
								if(!byy.isNull( resobj.area )){
									$('div[name=area]').html(resobj.area.mergerName);
								}
							}
						}
					});
				},
				onExpand : function(event,treeId,treeNode){
					var id = treeNode.id;
					var pnode = treeNode.getParentNode();
					if(pnode!=null){
						if(treeNode.isAjaxing == false){
							treeNode.isAjaxing = true;
							$.ajax({
								url : menu.config.getTreeUrl,
								type : 'POST',
								data : {
									id : id,
									order : 'asc'
								},
								dataType : 'json',
								success : function(res){
									var resobj = byy.json(res);
									if(menu.config.treeObj != null){
										menu.config.treeObj.addNodes(treeNode,resobj,false);
									}
								}
							});
						}
						
					}
				},
				
			}
		},

		columns : [
			{checkbox : true,width:40,align:'center'}, 
			{column : 'realName' , name : '姓名',width : 120,formatter : function(v ,obj){
				var str='';
				if(v.length>4){
					str = v.substring(0,4)+'...';
				}else{
					str = v;
				}
			    return '<span class="main-color1-font" title="'+v+'">'+str+'</span>';
			}},
			{column : 'governmentName' , name : '部门',width : 150},
			{column : 'postName' , name : '岗位',width : 150},
			{column : 'sexName' , name : '性别',width : 100,formatter : function(v ,obj){
				var str='';
				if(v.length>2){
					str = v.substring(0,2)+'...';
				}else{
					str = v;
				}
			    return '<span title="'+v+'" >'+str+'</span>';
			}},
			
			{column : 'mobile', name : '手机号',width : 150,formatter : function( v ){
				var str='';
				if(v.length>=12){
					str = v.substring(0,12);
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
				{
					column : 'dataStatus',
					name : '操作',
					width : 230,
					formatter : function(v, obj) {
						var flag = '';
						flag = '<span class="byy-btn primary mini" id="getdel" name="getdel"><i class="fa fa-trash"></i>删除</span><span class="byy-btn primary mini" id="getEdit" name="getEdit"><i class="fa fa-edit"></i>编辑</span>';
						if (obj.enable == true || obj.enable == 'true') {
							flag += '<span class="byy-btn primary mini" id="getEnable" name="getEnable"><i class="fa fa-remove"></i>禁用</span>';
						} else {
							flag += '<span class="byy-btn primary mini" id="getEnable" name="getEnable"><i class="fa fa-check"></i>启用</span>'
						}
						return flag;
					}}
        ],
		columns2 : [
					{checkbox : true,width:40,align:'center'}, 
					{column : 'realName' , name : '姓名',width : 150,formatter : function(v ,obj){
						var str='';
						if(v.length>10){
							str = v.substring(0,10)+'...';
						}else{
							str = v;
						}
					    return '<span class="main-color1-font" title="'+v+'">'+str+'</span>';
					}},
					{column : 'governmentName' , name : '部门',width : 150},
					{column : 'postName' , name : '岗位',width : 150},
					{column : 'sexName' , name : '性别',width : 150,formatter : function(v ,obj){
						var str='';
						if(v.length>20){
							str = v.substring(0,20)+'...';
						}else{
							str = v;
						}
					    return '<span title="'+v+'" >'+str+'</span>';
					}},
					
					{column : 'mobile', name : '手机号',width : 150,formatter : function( v ){
						var str='';
						if(v.length>=12){
							str = v.substring(0,12);
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
					{column : 'dataStatus',name : '操作',width:230,formatter : function(v,obj){
						var flag='';
						flag='<span class="byy-btn primary mini" id="getUp" name="getUp"><i class="fa fa-arrow-up"></i>上移</span><span class="byy-btn primary mini" id="getDown" name="getDown"><i class="fa fa-arrow-down"></i>下移</span>';
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
			// 9.导出
			$('.list_import').click(menu.list.importExcel);
			//10.上移
			$('.byy-table').on('click','#getUp',menu.list.up);
			//11.下移
			$('.byy-table').on('click','#getDown',menu.list.down);
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
				searchData.rows = behavior_page_rows || menu.config.pagesize;
			}
			searchData.governmentId = menu.config.governmentId;
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
					var c = menu.config.columns;
					if(menu.config.isEdit){
						c = menu.config.columns;
					}else{
						c = menu.config.columns2;
					}
                    byy.table({
                        selector : '#dtable',
                        columns : c,
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
		    	area: ['850px', '560px'],
		    	fixed: false, //不固定
		    	maxmin: true,
		    	content: base+'/bureau_toAdd.do'
		    });
		},
		edit : function(){
			var data = $(this).parent().parent().data('obj');
            if( !byy.isNull(data.id) ){
            	byy.win.open({
    				title: '编辑',
    				type: 2,
    				area: ['850px', '560px'],
    				fixed: false, //不固定
    				maxmin: true,
    		    	content: base+'/bureau_toEdit.do?id='+data.id
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
    				area: ['700px', '400px'],
    				btn : ['关闭'],
    				yes : function(){
    					byy.win.close(detailIndex);
    				},
    				fixed: false, //不固定
    				maxmin: true,
    		    	content: base+'/bureau_toView.do?id='+data.id,
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
				byy.win.confirm('是否确认删除教育局用户?', {
					  title : '提示',
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
	            });
			}else{
				byy.win.msg('请至少选中一条记录进行删除!');
			}
		},
		del : function(a) {
			var id = $(a.currentTarget).parent().parent().data("obj").id;
			return null == id || void 0 == id ? void b.win.msg("没有获得相关数据，请刷新后重试！") : void b.win.confirm("是否确定删除记录？", {
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
		up : function(a) {
			var id = $(a.currentTarget).parent().parent().data("obj").id;
			if( !byy.isNull(id) ){
				$.ajax({
					url : menu.config.doSortnumUrl,
					type : 'POST',
					data: {id:id,type:'up'},
					dataType : 'json',
					success : function(res){
						b.win.closeAll();
						var resobj = byy.json(res);
						byy.win.msg(resobj.msg);
						menu.list.searchReset();
					}
				});
			}else{
				byy.win.msg("没有获得相关数据，请刷新后重试!");
			}
		},
		down : function(a) {
			var id = $(a.currentTarget).parent().parent().data("obj").id;
			if( !byy.isNull(id) ){
				$.ajax({
					url : menu.config.doSortnumUrl,
					type : 'POST',
					data: {id:id,type:'down'},
					dataType : 'json',
					success : function(res){
						b.win.closeAll();
						var resobj = byy.json(res);
						byy.win.msg(resobj.msg);
						menu.list.searchReset();
					}
				});
			}else{
				byy.win.msg("没有获得相关数据，请刷新后重试!");
			}
				
			/*return null == id || void 0 == id ? void b.win.msg("没有获得相关数据，请刷新后重试!") : void b.win.confirm("是否向下移动?", {
				icon: 3,
				title : '信息'
			}, function (index) {
				$.ajax({
					url: menu.config.doSortnumUrl,
					type: "POST",
					data: {id:id,type:'down'},
					dataType : 'json',
					success: function (res) {
						b.win.closeAll();
						var resobj = byy.json(res);
						byy.win.msg(resobj.msg);
						menu.list.searchReset();
					}
				});
			}, function () {})*/
		},
		// 导入
		importExcel : function() {  
			byy.win.open({
				title : '教育局用户批量导入',
				type : 2,
				area: ['750px', '450px'],
				fixed : false, // 不固定
				maxmin : true,
				content : base +'/bureau_toimport.do'
			});
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
            $("#postId").change(function(){
            	$("#postTip").val($("#postId").val());
            });
           
		},
		loadTree : function(){
			//1.向后台请求，查询树数据并进行加载
			var listIndex = byy.win.load(1);
			$.ajax({
				url : menu.config.getTreeUrl,
				type : 'POST',
				dataType : 'json',
				success : function(res){
					byy.win.close(listIndex);
					var resobj = byy.json (res);
					menu.config.treeObj = $.fn.zTree.init($("#governmentdiv"), menu.config.treeset, resobj);
					//展开第一个节点
					var rootnode = menu.config.treeObj.getNodeByParam("levelType","1");
					menu.config.treeObj.expandNode(rootnode);
				}
			});
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
					if(resobj){
						var postd = [];
						$.each(resobj.post, function(index, value) {
							postd.push(value.post);
						});
						$("#postId").val(postd).trigger('change');
					}
					byy('form').setValues(resobj);
					$('input[name="pwd"]').parent().parent().remove();
					
				}
			});
		},
		validator : function(){
			var rules = {
				userName : {
					required : true,
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
				governmentId:{required : true},
				postTip:{required : true},
				governmentName:{required : true},
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
			
			var postId = "";
			$("#postId").find("option:selected").each(function() {
				postId += $(this).val() + ",";
			});
			if (postId.indexOf(",") >= 0) {
				postId = postId.substring(0, postId.length - 1);
			}
			searchData.postId = postId;
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
	},
	excel:{
		bindEvent : function(){
			$('.submitbtn .back,.importcancel,.pagetime').click(menu.excel.close);
			$('img.uploadimage').delegate('','click',menu.excel.doImport);
			//$('span.importdel').delegate('','click',menu.excel.doDeleteExcel);
			$('span.importsubmit').delegate('','click',menu.excel.doImportExcel);
			$('span.importdel').css('display','none');
			// 绑定上山事件
			var uploader_Excel = byy.upload.create({
    			// swf文件路径
    			swf: base + 'plugins/webuploader/Uploader.swf',
    			// 文件接收服务端。
    			//server: base+'/FileUploadChrunk',
    			server : base+'/FileUpload',
    			fileNumLimit : 10,
    			chunked : true,
    			chunkSize : 2048000,
    			chunkRetry : 3,
    			threads : 3,
    			formData :{
    				
    			},
    			// 选择文件的按钮。可选。
    			// 内部根据当前运行是创建，可能是input元素，也可能是flash.
    			pick: {
    				id : '#uploadimageDiv',
    				multiple  : false,
    				innerHTML : '<img class="uploadimage" title="点击选择文件上传"  style="cursor:pointer;" src="'+base+'/plugins/ueditor/dialogs/attachment/images/image.png" alt="" />'
    			},
    			auto : false,   
    			accept: {
    				title: 'excel文件',
    				extensions: 'xls,xlsx',
    				mimeTypes: 'application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
    			}
    		});
			 
			uploader_Excel.on('fileQueued',function( file ) {
    			$('#uploadimageDiv').append( '<div class="excel-row" id="'+file.id+'"></div>' );
    			/*var data_num = parseInt($('#uploadimageDiv').data('num'));
    			$('#uploadimageDiv').data('num',data_num+1);*/
				uploader_Excel.upload(file);
    		});

			uploader_Excel.on( 'uploadSuccess', function( file ,response) {
    			var $target = $('#'+file.id);
    			//添加返回值，暂时..
    			var object= response[0];
    			var filename = object.filename || '',
    					name = file.name,
    					fileSize = object.size || 0,
    					type = '.'+object.type;
    			$('#uploadimageDiv').hide();
    			$('#uploadimagefileDiv').show();
    			$('input[name="filename"]').val(name);
    			$('input[name="filePath"]').val(filename);
    			$('.importName').html(name);
				$('span.importdel').css('display','inline-block');
    		});
			uploader_Excel.on( 'error', function( type ){
    			if(type === 'Q_EXCEED_SIZE_LIMIT'){
    				byy.win.msg('文件大小超过100M!',{icon:2,time:1500})
    			}else if(type === 'Q_TYPE_DENIED'){
    				byy.win.msg('只支持上传excel文件!',{icon:2,time:1500})
    			}else if(type === 'Q_EXCEED_NUM_LIMIT'){
    				byy.win.msg('上传文件数量不能超过1个！',{icon:2,time:1500})
    			}else if(type === 'F_DUPLICATE'){
    				byy.win.msg('请不要重复上传文件！',{icon:2,time:1500})
    			}
    		});
			
			$('#delDiv').on('click','span.importdel',function(e){
				var $this = $(this)
	            var $p = $this.parent().parent().find('#uploadimageDiv').find('.excel-row');
				var tid  = $p[0].id; 
				if( !byy.isNull(tid) ){
					b.win.confirm('是否确定删除上传文件?',{icon : 3,title : '信息'},function(index){
						uploader_Excel.cancelFile(tid);
		    			$('input[name="filename"]').val('');
		    			$('input[name="filePath"]').val('');
		    			$('.importName').html('');
		    			$('#uploadimageDiv').show();
		    			$('#uploadimagefileDiv').hide();
						$('span.importdel').css('display','none');
						b.win.closeAll();
					});
				}
            })
			
		}, 
		doImportExcel : function(){
			var filename = $('input[name=filename]').val();
			var filePath = $('input[name=filePath]').val();
			if(filePath!=''){
				$.ajax({
					url : menu.config.doImportUrl,
					type : 'POST',
					dataType : 'json',
					data :{
						filePath : filePath,filename:filename
					},
					success: function(res){
						b.win.closeAll();
						var resobj = byy.json(res);
						if(resobj.success == true || resobj.success == 'true'){
							// 更新列表数据
							var pageobj = $(parent.document.body).find('.pagination');
							var pagecurr = pageobj.attr('page-curr'), pagesize = pageobj.attr('page-size');
							parent.menu.list.loadData({
								curr : pagecurr,
								pagesize : pagesize
							});
							// 关闭弹窗
							parent.byy.win.closeAll();
							parent.byy.win.msg(resobj.msg, {
								shift : -1
							}, function() {
								
							});
						}else{
							byy.win.msg(resobj.msg);
						}
						
						
					}
				});
			}
			
		}, 
		//导出当前查询出的学生信息
		exportExcel : function(){ 
			$('form[name=uploadForm]').remove();
			$('body').append('<form target="_blank" method="post" action="'+menu.config.doExportUrl+'" name="uploadForm"><input type="hidden" name="realName"  value="'+ $('#realName').val()+'" /></form>');
			$('form[name=uploadForm]').get(0).submit();
		},
		close : function() {
			parent.byy.win.closeAll();
		},
	}
};

byy.require(['jquery','win','table','page','upload'],function(){
    var h = location.href;
    byy('.byy-breadcrumb').breadcrumb();
    if(h.toLowerCase().indexOf('edit')>-1 || h.toLowerCase().indexOf('view')>-1|| h.toLowerCase().indexOf('add')>-1){
    	menu.form.bindEvent();
    	menu.form.loadTree();
    	menu.form.view();
    }else if (h.toLowerCase().indexOf('toimport') > -1 ) {
		menu.excel.bindEvent();  
	}else{
    	menu.list.bindEvent();
    	menu.list.loadData();
    }
});
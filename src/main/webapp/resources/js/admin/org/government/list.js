var main = {
	config : {
		getTreeUrl : base + '/government_getList.do',
		detailurl : base + '/government_getBean.do',
		addUrl : base + '/government_toAdd.do',
		saveUrl : base + '/government_toSave.do',
		dataUrl : base + '/government_getBean.do',
		deleteUrl : base + '/government_delete.do',
		validateUrl : base + '/government_validate.do',
		getTreeSearchUrl : base + '/government_toSearch.do',
		doImportUrl: base + '/government_importExcel.do',
		form : '.byy-form',
		treeObj : null,
		treeset : {
			view : {
				selectedMulti : false
			},
			callback : {
				onDblClick : function(){
				},
				onClick : function(event,treeId,treeNode,flag){
					var id = treeNode.id;
					//点击时释放显示区
					$('#mainRightForm').show();
					var listIndex = byy.win.load(1);
					$.ajax({
						url : main.config.detailurl,
						type : 'POST',
						data : {
							id :  id
						},
						dataType : 'json',
						success : function(res){
							byy.win.close(listIndex);
							var resobj = byy.json(res);
							//清空之前的值
							$('.byy-form .form-detail').html('');
							if(resobj.gov.levelType == '1'){
								resobj.gov.levelType = '部';
							}else if(resobj.gov.levelType == '2'){
								resobj.gov.levelType = '厅';
							}else if(resobj.gov.levelType == '3'){
								resobj.gov.levelType = '局';
							}else{
								resobj.gov.levelType = '科室';
							}
							byy('form').setValues(resobj.gov);
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
								url : main.config.getTreeUrl,
								type : 'POST',
								data : {
									id : id,
									order : 'asc'
								},
								dataType : 'json',
								success : function(res){
									var resobj = byy.json(res);
									if(main.config.treeObj != null){
										main.config.treeObj.addNodes(treeNode,resobj,false);
									}
								}
							});
						}
						
					}
				},
				//右键菜单
				onRightClick : function(event,treeId,treeNode){
					//显示出菜单来.
					main.config.treeObj.selectNode(treeNode);
					var menuArr = [
						'<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">',
					    '  <li role="presentation">',
					    '     <a role="menuitem" tabindex="-1" href="#" onclick="main.list.add()">新增</a>',
					    '  </li>',
					    '  <li role="presentation">',
					    '     <a role="menuitem" tabindex="-1" href="#" onclick="main.list.edit()">编辑</a>',
					    '  </li>',
					    '  <li role="presentation">',
					    '     <a role="menuitem" tabindex="-1" href="#" onclick="main.list.del()">',
					    '        删除',
					    '     </a>',
					    '  </li>',
					    '  <li role="presentation">',
					    '     <a role="menuitem" tabindex="-1" href="#" onclick="main.list.userList()">',
					    '        部门人员',
					    '     </a>',
					    '  </li>',
					   '</ul>'
					];
					$('.dropdown-menu').remove();
					$('body').append(menuArr.join(''));
					var left = event.clientX,right=event.clientY;
					var ele = event.srcElement ? event.srcElement :event.targetElement;
					$('.dropdown-menu').css({
						'display':'block',
						'position':'absolute',
						'left':left,
						'top':right
					});
				}
			}
		}
	},
	list : {
		init : function(){
			//绑定查询事件
			$('.btn_search').delegate('','click',main.list.search);
			//开始时隐藏显示区
			$('#mainRightForm').hide();
			//绑定重置事件
			$('.btn_reset').delegate('','click',main.list.resetSearch);
			$('.list_import').click(main.list.importExcel);
		},
		list : function(){
			//初始化树数据
			main.list.loadTree();
		},
		loadTree : function(){
			//1.向后台请求，查询树数据并进行加载
			var listIndex = byy.win.load(1);
			$.ajax({
				url : main.config.getTreeUrl,
				type : 'POST',
				dataType : 'json',
				success : function(res){
					byy.win.close(listIndex);
					var resobj = byy.json (res);
					main.config.treeObj = $.fn.zTree.init($("#governmentdiv"), main.config.treeset, resobj);
					//展开第一个节点
					var rootnode = main.config.treeObj.getNodeByParam("levelType","1");
					main.config.treeObj.expandNode(rootnode);
					//默认选中第一个节点，并执行点击事件
					main.config.treeObj.selectNode(rootnode);
					main.config.treeObj.setting.callback.onClick(null, main.config.treeObj.setting.treeId, rootnode);//调用事件
				}
			});
		},
		add : function(){
			//得到选中的树节点
			var node = main.config.treeObj.getSelectedNodes()[0];
			var nodeId = node.id;
			var nodeName = node.name;
			byy.win.open({
		    	title: '新增',
		    	type: 2,
		    	area: ['700px', '530px'],
		    	fixed: false, //不固定
		    	maxmin: true,
		    	content: base+'/government_toAdd.do?parentId='+nodeId+'&name='+encodeURI(nodeName)
		    });
		},
		edit : function(){
			//得到选中的树节点
			var node = main.config.treeObj.getSelectedNodes()[0];
            if( !byy.isNull(node.id) ){
            	byy.win.open({
    				title: '编辑',
    				type: 2,
    				area: ['700px', '530px'],
    				fixed: false, //不固定
    				maxmin: true,
    		    	content: base+'/government_toAdd.do?id='+node.id
    			});
            }else{
            	byy.win.msg('未找到相关信息，请刷新重试！');
            }
		},
		userList:function(){
			var node = main.config.treeObj.getSelectedNodes()[0];
			if( !byy.isNull(node.id) ){
				byy.win.open({
					title: '部门人员',
					type: 2,
					area: ['870px', '560px'],
					fixed: false, //不固定
					maxmin: true,
			    	content: base+'/bureau_toSelectList.do?governmentId='+node.id
				});
			}
		},
		del : function(){
			//得到选中的树节点
			var node = main.config.treeObj.getSelectedNodes()[0];
			if( !byy.isNull(node.id) ){
				b.win.confirm('是否确定删除教育机构?',{icon : 3,title: '提示',},function(index){
					var lindex = b.win.load(1);
					$.ajax({
						url : main.config.deleteUrl,
						type : 'POST',
						data : {
							id : node.id
						},
						dataType : 'json',
						success : function(res){
							byy.win.close(lindex);
							var resobj = byy.json(res);
							if(resobj.success == true || resobj.success == 'true'){
								byy.win.msg(resobj.msg);
								main.list.loadTree();
							}else{
								byy.win.msg(resobj.msg);
								
							}
						}
					});
				});
				/*$.ajax({
					url : main.config.deleteUrl,
					type : 'POST',
					data : {
						id : node.id
					},
					dataType : 'json',
					success : function(res){
						var resobj = byy.json(res);
						if(resobj.success == true || resobj.success == 'true'){
							byy.win.msg(resobj.msg);
							main.list.loadTree();
						}else{
							byy.win.msg(resobj.msg);
						}
					}
				});*/
			}
			
		},
		search : function(){
			//1.获取name值
			var name = $('input[name=name]').val();
			if(!byy.isNull(name) && name != ''){
				//2.向后台请求，查询树数据并进行加载
				var listIndex = byy.win.load(1);
				$.ajax({
					url : main.config.getTreeSearchUrl,
					type : 'POST',
					data : {
						name : name
					},
					dataType : 'json',
					success : function(res){
						byy.win.close(listIndex);
						$("#governmentdiv").html();
						var resobj = byy.json (res);
						main.config.treeObj = $.fn.zTree.init($("#governmentdiv"), main.config.treeset, resobj);
						//展开第一个节点
						var rootnode = main.config.treeObj.getNodeByParam("levelType","1");
						main.config.treeObj.expandNode(rootnode);
					}
				});
			}else{
				main.list.list();
			}
		},
		resetSearch : function(){
			$('input').val('');
			main.list.loadTree();
		},
		// 导入
		importExcel : function() {
			byy.win.open({
				title : '机构批量导入',
				type : 2,
				area: ['780px', '450px'],
				fixed : false, // 不固定
				maxmin : true,
				content : base + '/government_toImport.do'
			});
		}
	},
	form : {
		bindEvent : function(){
			//1.提交按钮绑定事件
            $('#submit').on('click',main.form.save);
			//2.关闭按钮绑定事件
            $('#close').on('click',main.form.close);
		},
		form : function(){
			//加载校验
			main.form.validator();
			main.form.loadCommonData();
			var id = byy.getSearch('id');
			if(id != undefined && id != null && id != '' && id != null){
				main.form.loadData(id);
			}else{
				//加载地域
				$('.areadiv').linkpage();
			}
		},
		loadCommonData : function(){
			//上级结构赋值
			var parentId = byy.getSearch('parentId');
			var parentName =byy.getSearch('name');
			$('input[name="parentId"]').val(parentId);
			$('input[name="parent.name"]').val(decodeURI(parentName));
		},
		loadData : function(id){
			$.ajax({
				url : main.config.dataUrl,
				type : 'POST',
				data : {
					id : id
				},
				dataType : 'json',
				success : function(res){
					var resobj = byy.json(res);
					byy('form').setValues(resobj.gov);
					$('input[name="parentId"]').val(resobj.gov.parentId);
					$('input[name="parent.name"]').val(resobj.gov.parentName);
					//加载地域
					$('.areadiv').linkpage(resobj.area);
				}
			});
		},
		save : function(){
			var searchData = byy('.byy-form').getValues();
			if($(main.config.form).valid()){
				searchData['areaId'] = searchData['areadivdistrict'];
				//提交的时候将提交按钮禁用，防止重复提交
				$('#submit').off('click').addClass('disabled');
				if(searchData['levelType'] == ''){
					byy.win.msg('请选择机构等级!');
				}else if(searchData['areadivdistrict'] == ''){
					byy.win.msg('请选择所在区域!');
				}else{
					if(searchData['seq'] == ''){
						searchData['seq'] = 99;
					}
					$.ajax({
						url : main.config.saveUrl,
						type : 'POST',
						data : searchData,
						dataType : 'json',
						success : function(res){
							var resobj = byy.json(res);
							//更新列表数据
							//关闭弹窗
							parent.byy.win.closeAll();
							parent.main.list.loadTree();
							parent.byy.win.msg(resobj.msg,{shift:-1});
						}
					});
				}
			}
		},
		validator : function(){
			var rules = {
				name : {
					required : true, maxlength:20,
					
					remote : {
						url : main.config.validateUrl,
						type : 'POST',
						dataType : 'json',
						async: false,
						data : {
							name : function(){
								return $('input[name=name]').val();
							},
							id : function(){
								return $('input[name=id]').val();
							},
							areaId : function(){
								return $('select[name="areadivdistrict"]').val();
							}
						}
					}
				},
				shortName : {required : true, maxlength:20},
				seq : {digits : true , maxlength:20},
				areadivdistrict:{required:true},
				
			};
			return byy(main.config.form).validate({
				rules:rules
			});
		},
		close : function(){
            parent.byy.win.closeAll();
        }
	},
	excel:{
		bindEvent : function(){
			$('.submitbtn .back,.importcancel,.pagetime').click(main.excel.close);
			$('img.uploadimage').delegate('','click',main.excel.doImport);
			//$('span.importdel').delegate('','click',menu.excel.doDeleteExcel);
			$('span.importsubmit').delegate('','click',main.excel.doImportExcel);
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
					url : main.config.doImportUrl,
					type : 'POST',
					dataType : 'json',
					data :{
						filePath : filePath,filename:filename
					},
					success: function(res){
						b.win.closeAll();
						var resobj = byy.json(res);
						if(resobj.success=="true"){
							//关闭弹窗
							parent.byy.win.closeAll();
							parent.main.list.loadTree();
							parent.byy.win.msg(resobj.msg,{shift:-1});
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
			$('body').append('<form target="_blank" method="post" action="'+main.config.doExportUrl+'" name="uploadForm"><input type="hidden" name="realName"  value="'+ $('#realName').val()+'" /></form>');
			$('form[name=uploadForm]').get(0).submit();
		},
		close : function() {
			parent.byy.win.closeAll();
		}
	}
};

byy.require(['jquery','win','upload','page','validator'],function(){
    var h = location.href;
    if(h.indexOf('index') > -1){
    	byy('.byy-breadcrumb').breadcrumb();//显示界面头部标题
    	main.list.init();
    	main.list.list();
    }else if (h.toLowerCase().indexOf('toimport') > -1 ) {
    	main.excel.bindEvent();  
	}else{
    	main.form.bindEvent();
    	main.form.form();
    }
});
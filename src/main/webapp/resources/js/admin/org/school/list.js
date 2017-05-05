var main = {
	config : {
		getTreeUrl : base + '/school_getList.do',
		getDataUrl : base + '/school_getBean.do',
		addUrl : base + '/school_toAdd.do',
		getNullParentSchool : base + '/school_getParentSchool.do',
		saveUrl : base + '/school_save.do',
		deleteUrl : base + '/school_delete.do',
		getGovTreeUrl : base + '/government_getList.do',
		getTreeSearchUrl : base + '/school_search.do',
		doImportUrl: base + '/school_importExcel.do',
		validatUrl: base + '/school_validatSerialNumber.do',
		validatNameUrl: base + '/school_validatName.do',
		form : '.byy-form',
		treeObj : null,
		treeset : {
			view : {
				selectedMulti : false
			},
			callback : {
				onExpand : function(event,treeId,treeNode){
					var id = treeNode.id;
					var parent = treeNode.getParentNode();
					var levelType = treeNode.levelType;//该字段用于判断再点击了government节点时，是否加载该节点下的学校
					if(parent!=null&&treeNode.isAjaxing == false){
						treeNode.isAjaxing = true;
						$.ajax({
							url : main.config.getTreeUrl,
							type : 'POST',
							data : {
								id : id,
								type:treeNode.type,
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
				},
				onDblClick : function(){
				},
				onClick : function(event,treeId,treeNode,flag){
					var id = treeNode.id;
					var isroot = treeNode.root ? true : false;
					if(!isroot){
						$.ajax({
							url : main.config.getDataUrl,
							type : 'POST',
							data : {
								id :  id
							},
							dataType : 'json',
							success : function(res){
								var resobj = byy.json(res);
								$('#mainRightForm').hide();
								//清空之前的值
								$('.byy-form .form-detail').html('');
								//点击学校时才放开显示区
								if(resobj.school.id!=null&&resobj.school.id!=''){
								$('.byy-form').show();
								byy('form').setValues(resobj.school);
								}
							}
						});
					}
				},
				//右键菜单
				onRightClick : function(event,treeId,treeNode){
					//显示出菜单来.
					var isleaf = false;
					if(!treeNode.root && treeNode.getParentNode() && treeNode.getParentNode().getParentNode()){/*超过两级则不显示右键*/
						isleaf = true;
					}
					isleaf = false;
					main.config.treeObj.selectNode(treeNode);
					var schoolId = treeNode.id;
					var parentId = treeNode.id;
					var nodeType = treeNode.type;
					var parentName = treeNode.name;
					var governmentId='';
					var governmentName='';
					if(nodeType=='government'){
						governmentId=parentId;
						governmentName=parentName;
						parentId ='';
						parentName ='';
					}
					//如果nodeType类型为school，则显示添加子学校
					var menuArr = [
						'<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">',
					    '  <li role="presentation">',
					    ( nodeType!= "school" ? '<a role="menuitem" tabindex="-1" href="#" onclick="main.list.add(\''+governmentId+'\',\''+governmentName+'\',\''+parentId+'\',\''+parentName+'\',\''+nodeType+'\')">新增学校</a>' : '<a role="menuitem" tabindex="-1" href="#" onclick="main.list.add(\''+governmentId+'\',\''+governmentName+'\',\''+parentId+'\',\''+parentName+'\',\''+nodeType+'\')">新增子校</a>'),
					    '  </li>',
					    
					    '  <li role="presentation">',
					    		(nodeType!="school"||!schoolId) ? '' : '<a role="menuitem" tabindex="-1" href="#" onclick="main.list.edit(\''+schoolId+'\',\''+parentId+'\')">编辑</a>',
					    '  </li>',
					    '  <li role="presentation">',
					    (nodeType!="school"||!schoolId) ? '' : '     <a role="menuitem" tabindex="-1" href="#" onclick="main.list.del(\''+schoolId+'\')">删除</a>',
					    '  </li>',
					   '</ul>'
					];
					$('.dropdown-menu').remove();
					$('body').append(menuArr.join(''));
					var left = event.pageX,right=event.pageY;
					var ele = event.srcElement ? event.srcElement :event.targetElement;
					$('.dropdown-menu').css({
						'display':'block',
						'position':'absolute',
						'left':left,
						'top':right
					});
				}
			}
		},
		treeGovObj : null,
		treeGovset : {
			callback : {
				onDblClick : function(){
				},
				onClick : function(event,treeId,treeNode,flag){
					var id = treeNode.id;
					var levelType = treeNode.levelType;
					$('input[name="governmentName"]').val(treeNode.name);
					$('input[name="governmentId"]').val(id);
					//隐藏
					$('#governmentdiv').hide('slow');
				},
				onExpand : function(event,treeId,treeNode){
					var id = treeNode.id;
					var levelType = treeNode.levelType;
					if(parseInt(levelType,10) != 1 && !isNaN(parseInt(levelType,10))){
						if(treeNode.isAjaxing == false){
							treeNode.isAjaxing = true;
							$.ajax({
								url : main.config.getGovTreeUrl,
								type : 'POST',
								data : {
									id : id,
									order : 'asc'
								},
								dataType : 'json',
								success : function(res){
									var resobj = byy.json(res);
									if(main.config.treeGovObj != null){
										main.config.treeGovObj.addNodes(treeNode,resobj,false);
									}
								}
							});
						}
						
					}
				}
			}
		}
	},
	list : {
		init : function(){
			//绑定查询事件
			$('.btn_search').delegate('','click',main.list.search);
			//绑定重置事件
			$('.btn_reset').delegate('','click',main.list.resetSearch);
			//隐藏显示区
			$('#mainRightForm').hide();
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
					main.config.treeObj = $.fn.zTree.init($("#schooldiv"), main.config.treeset, resobj);
					//展开第一个节点
					var rootnode = main.config.treeObj.getNodeByParam("levelType","1");
					main.config.treeObj.expandNode(rootnode);
				}
			});
		},
		add : function(governmentId,governmentName,parentId,parentName,type){
			byy.win.open({
		    	title: '新增',
		    	type: 2,
		    	area: ['700px', '530px'],
		    	fixed: false, //不固定
		    	maxmin: true,
		    	content: base+'/school_toAdd.do?parentId='+parentId+'&parentName='+encodeURI(parentName)+'&governmentId='+governmentId+'&governmentName='+encodeURI(governmentName)+'&type='+type
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
    		    	content: base+'/school_toAdd.do?id='+node.id
    			});
            }else{
            	byy.win.msg('未找到相关信息，请刷新重试！');
            }
		},
		del : function(){
			//得到选中的树节点
			var node = main.config.treeObj.getSelectedNodes()[0];
			if( !byy.isNull(node.id) ){
				b.win.confirm('是否确定删除学校?',{icon : 3,title : '提示'},function(index){
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
						$("#schooldiv").html();
						var resobj = byy.json (res);
						main.config.treeObj = $.fn.zTree.init($("#schooldiv"), main.config.treeset, resobj);
						//展开第一个节点
						var rootnode = main.config.treeObj.getNodeByParam("levelType","1");
						main.config.treeObj.expandNode(rootnode);
					}
				});
			}else{
				main.list.loadTree();
			}
		},
		resetSearch : function(){
			$('input').val('');
			main.list.loadTree();
		},
		// 导入
		importExcel : function() {
			byy.win.open({
				title : '学校批量导入',
				type : 2,
				area: ['780px', '450px'],
				fixed : false, // 不固定
				maxmin : true,
				content : base + '/school_toImport.do'
			});
		}
	},
	form : {
		bindEvent : function(){
			//1.提交按钮绑定事件
            $('#submit').on('click',main.form.save);
			//2.关闭按钮绑定事件
            $('#close').on('click',main.form.close);
            //3.点击所属机构input，弹出机构信息树结构
            $('input[name="governmentName"]').click(main.form.showGovernment);
		},
		form : function(){
			//加载校验
			jQuery.validator.addMethod("enOrChName", function(value, element) {       
	 			return this.optional(element) || /^[a-zA-Z\u4E00-\u9FA5]+$/.test(value);       
	 		}, '请输入正确的中,英文.');
			main.form.validator();
			//加载总校下拉框
			main.form.loadNullParentSchool();
			main.form.loadCommonData();
			var id = byy.getSearch('id');
			if(id != undefined && id != null && id != ''){
				main.form.loadData(id);
			}else{//增加界面
				//加载地域
				$('.areadiv').linkpage();
				//更改上级学校
				var parentId = byy.getSearch('parentId');
				var parentName = decodeURI(byy.getSearch('parentName'));
				if(parentId != undefined && parentId != null && parentId != '' && parentId != 'undefined'){
					$('input[name=parentId]').val(parentId);
				}
				if(parentName != undefined && parentName != null && parentName != '' && parentName != 'undefined'){
					$('input[name=parentName]').val(parentName);
				}
			}
		},
		loadCommonData : function(){
			//上级结构赋值 
			var type = byy.getSearch('type');
			var parentId = byy.getSearch('parentId');
			var parentName =byy.getSearch('parentName');
			var governmentId = byy.getSearch('governmentId');
			var governmentName =byy.getSearch('governmentName');
			$('input[name="governmentId"]').val(governmentId);
			$('input[name="governmentName"]').val(decodeURI(governmentName));
			if(type=='school'){
				$.ajax({
					url : main.config.getDataUrl,
					type : 'POST',
					data : {
						id : parentId, 
					},
					dataType : 'json',
					success : function(res){
						var resobj = byy.json(res); 
						$('input[name="governmentId"]').val(resobj.school.governmentId);
						$('input[name="governmentName"]').val(resobj.school.governmentName);
					}
				});
			}
		},
		loadData : function(id){
			$.ajax({
				url : main.config.getDataUrl,
				type : 'POST',
				data : {
					id : id,
					areaload : 'true'
				},
				dataType : 'json',
				success : function(res){
					var resobj = byy.json(res);
					byy('form').setValues(resobj.school);
					if( resobj.school.parentName!=''){
						$('input[name="governmentName"]').unbind('click');
					}
					//加载地域
					$('.areadiv').linkpage(resobj.area);
				}
			});
		},
		save : function(){
			if($(main.config.form).valid()){
				var searchData = byy('.byy-form').getValues();
				//提交的时候将提交按钮禁用，防止重复提交
				$('#submit').off('click').addClass('disabled');
				searchData['areaId'] = searchData['areadiv-district'];
				//判断驻地类型、办学类型、学校类型、寄宿类型是否为空，为空，则给默认值0
				if(searchData['schoolStation'] == ''){
					searchData['schoolStation'] = 0;
				}
				if(searchData['schoolType'] == ''){
					searchData['schoolType'] = 0;
				}
				if(searchData['systemType'] == ''){
					searchData['systemType'] = 0;
				}
				if(searchData['schoolBoardType'] == ''){
					searchData['schoolBoardType'] = 0;
				}
				$.ajax({
					url : main.config.saveUrl,
					type : 'POST',
					data : searchData,
					dataType : 'json',
					success : function(res){
						var resobj = byy.json(res);
						parent.main.list.resetSearch;
						//关闭弹窗
						parent.byy.win.closeAll();
						//更新列表数据
						parent.main.list.loadTree();
						parent.byy.win.msg(resobj.msg,{shift:-1});
						
					}
				});
			}
		},
		validator : function(){
			var rules = {
					serialNumber: {
						zimushuzi: true, zimushuzi: true,
						maxlength:20,
						remote : {
							url : main.config.validatUrl,
							type : 'POST',
							dataType : 'json',
							async: false,
							data : {
								serialNumber : function(){
									return $('input[name=serialNumber]').val();
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
				name : {required : true, maxlength:20,
					remote : {
						url : main.config.validatNameUrl,
						type : 'POST',
						dataType : 'json',
						async: false,
						data : {
							serialNumber : function(){
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
				shortName : {required : true, maxlength:20 },
				code : {required : true , maxlength:20,	digits:true,
					remote : {
						url : main.config.validatUrl,
						type : 'POST',
						dataType : 'json',
						async: false,
						data : {
							code:function(){
								return $('input[name=code]').val();
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
				headmaster :{enOrChName:true,maxlength : 10},
				zip : {digits:true},
				tel : {tel:true},
				fax: {tel:true},
				email : {email: true,maxlength:60},
				adminMobile : {phone:true},
				governmentName: {required : true},
				description :{maxlength : 300},
				address:{maxlength : 100}
			};
			return $(main.config.form).validate({
				focusInvalid: true,
				errorPlacement : function(error,element){
					byy.win.tips(error.get(0).innerHTML,element);
				},
				rules:rules,
				messages:{
					serialNumber:{
						remote:"学校标识已存在"
					} ,
					code:{remote:"机构号码已存在"},
					name:{remote:"学校名称重复"}
				
				}
			});
		},
		showGovernment : function(event){
			event = event || window.event;
			var x = event.clientX,y = event.clientY;
			$('#governmentdiv').css({
				display:'block',
				left : 0,
				'-webkit-transition ' : 'display 1s ease-in',
				opacity : .8,
				width : $('input[name="government.name"]').width(),
				top : 33
			});
			//加载树
			//1.向后台请求，查询树数据并进行加载
			var listIndex = byy.win.load(1);
			$.ajax({
				url : main.config.getGovTreeUrl,
				type : 'POST',
				dataType : 'json',
				success : function(res){
					byy.win.close(listIndex);
					var resobj = byy.json (res);
					main.config.treeGovObj = $.fn.zTree.init($("#governmentdiv"), main.config.treeGovset, resobj);
					//展开第一个节点
					var rootnode = main.config.treeGovObj.getNodeByParam("levelType","1");
					main.config.treeGovObj.expandNode(rootnode);
				}
			});
		},
		loadNullParentSchool : function(){
			$.ajax({
				url : main.config.getNullParentSchool,
				type : 'POST',
				dataType : 'json',
				async:false,
				success : function(res){
					//渲染数据，同时渲染分页数据
					var  resobj = byy.json(res);
					var html = '<option value="">请选择...</option>';
					for(var i = 0 ; i < resobj.length ; i++){
						html += '<option value="'+resobj[i].id+'">'+resobj[i].name+'</option>'
					}
					$("select[name='parentId']").html(html);
					//重新初始化下拉框
					byy($("select[name='parentId']")).select();
					$('.selectpicker').selectpicker({"val":"",'style':'no-radiu','noneResultsText':'未查询到'});
					$(".selectpicker").selectpicker('refresh');
					$(".selectpicker").selectpicker('show');
				}
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
						var resobj = byy.json(res);

						if(resobj.success=="true"){
							//关闭弹窗
							parent.byy.win.closeAll();
							parent.byy.win.msg(resobj.msg,{shift:-1},function(){
								//更新列表数据
								parent.main.list.loadTree();
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
			$('body').append('<form target="_blank" method="post" action="'+main.config.doExportUrl+'" name="uploadForm"><input type="hidden" name="realName"  value="'+ $('#realName').val()+'" /></form>');
			$('form[name=uploadForm]').get(0).submit();
		},
		close : function() {
			parent.byy.win.closeAll();
		}
	}
};

byy.require(['jquery','win','upload'],function(){
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
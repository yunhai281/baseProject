var menu = {
	config : {
		toList : base + '/student_getList.do',
		doSaveUrl : base + '/student_save.do',
		getForm : base + '/student_getBean.do',
		doDelUrl: base + '/student_delete.do',
		resetPassword : base + "/user_resetPassword.do",
		doImportUrl : base + "/student_importStudentExcel.do",
		getSchoolStageUrl: base + "/school_getSchoolStage.do",
		getSchoolGradeUrl: base + "/org_grade_getSchoolGrade.do",
		getSchoolClassUrl: base + "/org_class_getSchoolClass.do",
		getSerialNumberUrl : base + '/school_getBean.do',
		doEnable: base+ "/user_doEnable.do",
		pagesize : 10,
		form : '.byy-form',
		columns : [
				{
					checkbox : true,
					width : 40,
					align : 'center'
				},
				{
					column : 'userName',
					name : '用户名',
					width : 120,
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
//				{
//					column : 'studentNo',
//					name : '学号',
//					width : 80,
//					formatter : function(v, obj) {
//						var str = '';
//						if (v.length > 10) {
//							str = v.substring(0, 10) + '...';
//						} else {
//							str = v;
//						}
//						return '<span title="' + v + '" >' + str + '</span>';
//					}
//				},
				{
					column : 'realName',
					name : '姓名',
					width : 80,
					formatter : function(v, obj) {
						var str = '';
						if (v.length > 4) {
							str = v.substring(0, 4) + '...';
						} else {
							str = v;
						}
						return '<span class="main-color1-font" title="' + v + '">' + str + '</span>';
					}
				},
				{
					column : 'sexName',
					name : '性别',
					width : 60,
					formatter : function(v, obj) {
						var str = '';
						if (v.length > 3) {
							str = v.substring(0, 3) + '...';
						} else {
							str = v;
						}
						return '<span title="' + v + '" >' + str + '</span>';
					}
				},
				{
					column : 'birthday',
					name : '生日',
					width : 105,
					formatter : function(v, obj) {
						var str = '';
						if (v.length > 10) {
							str = v.substring(0, 10);
						} else {
							str = v;
						}
						return '<span title="' + v + '" >' + str + '</span>';
					}
				},
				{
					column : 'mobile',
					name : '手机号',
					width : 80,
					formatter : function(v) {
						var str = '';
						if (v.length >= 12) {
							str = v.substring(0, 11);
						} else {
							str = v;
						}
						return str;
					}
				},
				{
					column : 'statusName',
					name : '状态',
					width : 60,
					formatter : function(v) {
						var str = '';
						if (v.length > 20) {
							str = v.substring(0, 20) + '...';
						} else {
							str = v;
						}
						return '<span title="' + v + '" >' + str + '</span>';
					}
				},
				{
					column : 'schoolName',
					name : '所属学校',
					width : 180,
					formatter : function(v) {
						var str = '';
						if (v.length > 20) {
							str = v.substring(0, 20) + '...';
						} else {
							str = v;
						}
						return '<span title="' + v + '" >' + str + '</span>';
					}
				},{
					column : 'enable',
					name : '启用',
					width : 60,
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
					width : 210,
					formatter : function(v, obj) {
						var flag = '';
						flag = '<span class="byy-btn primary mini" id="getdel" name="getdel"><i class="fa fa-trash"></i>删除</span><span class="byy-btn primary mini" id="getEdit" name="getEdit"><i class="fa fa-edit"></i>编辑</span>';
						if(obj.enable==true||obj.enable=='true'){
							flag+='<span class="byy-btn primary mini" id="getEnable" name="getEnable"><i class="fa fa-remove"></i>禁用</span>';
						}else{
							flag+='<span class="byy-btn primary mini" id="getEnable" name="getEnable"><i class="fa fa-check"></i>启用</span>'
						}
						return flag;
					}
				} ]
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
			//9.批量导入学生
			$('.importStudent').click(menu.excel.excel);
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
		//新增教师
		add : function(){
		    byy.win.open({
		    	title: '新增',
		    	type: 2,
		    	area: ['850px', '650px'],
		    	fixed: false, //不固定
		    	maxmin: true,
		    	content: base+'/student_toAdd.do'
		    });
		},
		edit : function(){
			var data = $(this).parent().parent().data('obj');
            if( !byy.isNull(data.id) ){
            	byy.win.open({
    				title: '编辑',
    				type: 2,
    				area: ['850px', '650px'],
    				fixed: false, //不固定
    				maxmin: true,
    		    	content: base+'/student_toEdit.do?id='+data.id
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
    				area: ['850px', '530px'],
    				btn : ['关闭'],
    				yes : function(){
    					byy.win.close(detailIndex);
    				},
    				fixed: false, //不固定
    				maxmin: true,
    		    	content: base+'/student_toView.do?id='+data.id,
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
			if(id.length > 0){
				byy.win.confirm('是否确认删除学生?', {
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
			/*var id = $(a.currentTarget).parent().parent().data("obj").id;
			return null == id || void 0 == id ? void b.win.msg("没有获得相关数据，请刷新后重试！") : void b.win.confirm("是否确定更改该用户？", {
				icon: 2,
				btn: ["确定", "取消"]
			}, function () {
				$.ajax({
					url: menu.config.doEnable,
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
			}, function () {})*/
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
			//绑定选择学校事件
			$(".selectSchool").delegate("","click",function(){
				parent.byy.win.open({
					type:2,
					title:"选择学校",
					frameName:"byy-byywin",
					name:"selectschool",
					shade:.6,
					area:["1200px","600px"],
					content:base+'/school_toSelect.do?callback=cbstr&num=1&parentName='+iframe,//num可以填具体的限制选择学校数字,该参数为选填，不填的时候，默认为选择多个学校
					className:"masterWindow"
			    });
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
					byy('form').setValues(resobj);
					$('input[name="pwd"]').parent().parent().remove();
					var type='';
					if(resobj.belongGradeStage=='0'){
						type='幼儿园';
					}else if(resobj.belongGradeStage=='1'){
						type='小学';
					}else if(resobj.belongGradeStage=='2'){
						type='初中';
					}else if(resobj.belongGradeStage=='3'){
						type='高中';
					}
					getStage(type,resobj.belongGradeId,resobj.belongClassId);
					$('#stage').next('div.byy-form-select').delegate('dl', 'click', function() {
						getGrade();
					});
					// 初始化之后要绑定
					$('#belongGradeId').next('div.byy-form-select').delegate('dd', 'click', function() {
						getClass();
					});
					//修改日期显示格式
//					if(!byy.isNull($('#birthday').val())){
//						$('#birthday').val($('#birthday').val().substring(0,10));
//					}
//					if(!byy.isNull($('#leavingDate').val())){
//						$('#leavingDate').val($('#leavingDate').val().substring(0,10));
//					}
				}
			});
		},
		validator : function(){
			var rules = {
				userName : {required : true },
				realName : {required: true},
				pwd :{required : true},
				email :{email : true},
				mobile :{phone :true},
				studentNo : {digits:true}
			};
			return $(menu.config.form).validate({
				focusInvalid : true,
				errorPlacement : function(error, element) {
					byy.win.tips(error.get(0).innerHTML, element);
				},
				rules : rules,
				messages : {
					studentNo : {
						digits : '学号必须是数字'
					}
				}
			});
		},
		save : function(){
			var searchData = byy('.byy-form').getValues();
			if($(menu.config.form).valid()){
				var formIndex = byy.win.load(1);
				if($('.serialNumber').html()!=''){
					searchData.userName=searchData.userName +$('.serialNumber').html();
				}
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
	excel :{
		excel : function(){
			byy.win.open({
				title: '批量导入学生',
				type: 2,
				area: ['850px', '530px'],
				fixed: false, //不固定
				maxmin: true,
		    	content: base+'/student_toImport.do'
			});
		},
		init : function(){
			$('.submitbtn .back,.importcancel,.pagetime,#close').click(menu.excel.close);
			$('img.uploadimage').delegate('','click',menu.excel.doImport);
			$('span.importsubmit').delegate('','click',menu.excel.doImportExcel);
			$('span.importdel').css('display','none');
			// 绑定上传事件
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
    				title: '',
    				extensions: '*',
    				mimeTypes: ''
    			}
    		});
			 
			uploader_Excel.on('fileQueued',function( file ) {
    			$('#uploadimageDiv').append( '<div class="excel-row" id="'+file.id+'"></div>' );
				/*$('#uploadimageDiv').append( '<div class="enclosure-row" id="'+file.id+'"><span class="enclosure-title">正在上传...</span></div>' );
    			var data_num = parseInt($('#uploadimageDiv').data('num'));
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
//    			$('#uploadimagefileDiv').css({
//					'background-image':'url('+base+'/plugins/ueditor/dialogs/attachment/images/file-icons.gif)',
//					'background-position':'-350px center',
//					'width':'70px','height':'70px'
//				});
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
				//$p.remove();
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
						filePath : filePath,
						filename:filename
					},
					success: function(res){
			            var resobj = byy.json(res);
			            
			            // 更新列表数据
						var pageobj = $(parent.document.body).find('.pagination');
						var pagecurr = pageobj.attr('page-curr'), pagesize = pageobj.attr('page-size');
						parent.menu.list.loadData({
							curr : pagecurr,
							pagesize : pagesize
						});
			            parent.byy.win.closeAll();
						parent.byy.win.msg(resobj.msg,{shift:-1},function(){
							
						});
						//byy.win.msg(resobj.msg);
						menu.list.searchReset();
						//如果有错误就提示，没有则关闭
						/*$('body').removeMask();
						var resobj = common.parseJson(res);
						if(resobj.success == false || resobj.success == 'false'){
							common.alert('',resobj.msg);
						}else{
							window.parent.common.alert('',resobj.msg,'',function(){
								$(".pagetime").click();
							});
						}*/
					}
				});
			}
			
		},
		//导出当前查询出的学生信息
		exportExcel : function(){
			var d  = $('.formsearch').getValues();
			$('form[name=uploadForm]').remove();
			$('body').append('<form target="_blank" method="post" action="'+main.config.doExportUrl+'" name="uploadForm"><input type="hidden" name="belongClass.school.id" /><input type="hidden" name="belongClass.grade.id" /><input type="hidden" name="belongClass.grade.stage" /><input type="hidden" name="belongClass.id" /><input type="hidden" name="status" /><input type="hidden" name="studentNo" /></form>');
			$('form[name=uploadForm]').loadData(d);
			$('form[name=uploadForm]').get(0).submit();
		},
		close : function() {
			parent.byy.win.closeAll();
		}
		
		
	}
};
function getStage(type,belongGradeId,belongClassId){
	var schoolId= $('#schoolId').val();
	$.ajax({
		url : menu.config.getSchoolStageUrl,
		type : 'POST',
		data : {id:schoolId},
		dataType : 'json',
		success : function(res){
			//byy.win.close(formIndex);
			var resobj = byy.json(res);
			//更新列表数据
			$('select[name=stage]').html('');
			var html='<option value="">请选择...</option>';
			for(var i =0; i<resobj.length;i++){
				var data=resobj[i];
				html+='<option value="'+data+'">'+data+'</option> ';
			}
			$('select[name=stage]').append(html);
			byy('select[name=stage]').select();
			$('select[name=belongGradeId]').html('');
			$('select[name=belongClassId]').html('');
			if(type!=undefined){
				var obj={stage:type}
				byy('form').setValues(obj); 
				getGrade(type,belongGradeId,belongClassId);
			}
			$('#stage').next('div.byy-form-select').delegate('dl', 'click', function() {
				getGrade();
			});
		}
	});
}
function getGrade(stageType,belongGradeId,belongClassId){
	// 学段
	// 只有是教师的时候才能选择学科
	var stage = $('#stage').val();
	if(stageType!=undefined){
		stage= stageType;
	}
	var schoolId= $('#schoolId').val();
	$.ajax({
		url : menu.config.getSchoolGradeUrl,
		type : 'POST',
		data : {schoolId:schoolId,stageName:stage},
		dataType : 'json',
		success : function(res){
			//byy.win.close(formIndex);
			var resobj = byy.json(res);
			//更新列表数据
			$('select[name=belongGradeId]').html('');
			var html='<option value="">请选择...</option>';
			for(var i =0; i<resobj.length;i++){
				var data=resobj[i];
				html+='<option value="'+data.id+'">'+data.name+'</option> ';
			}
			$('select[name=belongGradeId]').append(html);
			byy('select[name=belongGradeId]').select();
			$('select[name=belongClassId]').html('');
			byy('select[name=belongClassId]').select();
			if(belongGradeId!=undefined){
				var obj={belongGradeId:belongGradeId}
				byy('form').setValues(obj);  
				getClass(belongGradeId,belongClassId);
				$('#stage').next('div.byy-form-select').delegate('dl', 'click', function() {
					getGrade();
				});
			}
			// 初始化之后要绑定
			$('#belongGradeId').next('div.byy-form-select').delegate('dd', 'click', function() {
				getClass();
			});
		}
	});
}
// 取得班级
function getClass(belongGradeId,belongClassId){
	// 年级
	// 只有是教师的时候才能选择学科
	var gradeId = $('#belongGradeId').val();
	if(belongGradeId!=undefined){
		gradeId= belongGradeId;
	}
	var schoolId= $('#schoolId').val();
	$.ajax({
		url : menu.config.getSchoolClassUrl,
		type : 'POST',
		data : {schoolId:schoolId,gradeId:gradeId},
		dataType : 'json',
		success : function(res){
			//byy.win.close(formIndex);
			var resobj = byy.json(res);
			//更新列表数据
			$('select[name=belongClassId]').html(''); 
			var html='<option value="">请选择...</option>';
			for(var i =0; i<resobj.length;i++){
				var data=resobj[i];
				html+='<option value="'+data.id+'">'+data.name+'</option> ';
			}
			$('select[name=belongClassId]').append(html);
			byy('select[name=belongClassId]').select();
			if(belongClassId!=undefined){
				var obj={belongClassId:belongClassId}
				byy('form').setValues(obj);
				// 初始化之后要绑定
				$('#belongGradeId').next('div.byy-form-select').delegate('dd', 'click', function() {
					getClass();
				});  
			} 
		}
	});
}
function cbstr(id,name){
	//alert(id+','+name);
	//console.info($('body').html()); 
	if(!byy.isNull( id ) && id != ''){
		$('input[name=schoolId]').val(id);
	}
	if(!byy.isNull( name ) && name != ''){
		$('input[name=schoolName]').val(name);
	}
	var formIndex = byy.win.load(1);
	$.ajax({
		url : menu.config.getSerialNumberUrl,
		type : 'POST',
		data : {id:id},
		dataType : 'json',
		success : function(res) {
			byy.win.close(formIndex);
			var resobj = byy.json(res);
			$('.serialNumber').html('@'+resobj.school.serialNumber);
		}
	});
	
	$('select[name=stage]').html('');
	byy('select[name=stage]').select();
	$('select[name=belongGradeId]').html('');
	byy('select[name=belongGradeId]').select();
	$('select[name=belongClassId]').html('');
	byy('select[name=belongClassId]').select();
	getStage();
}
byy.require([ 'jquery', 'win', 'table', 'page','upload'], function() {
    var h = location.href;
    byy('.byy-breadcrumb').breadcrumb();
    if(h.toLowerCase().indexOf('edit')>-1 || h.toLowerCase().indexOf('view')>-1|| h.toLowerCase().indexOf('add')>-1){
    	menu.form.bindEvent();
    	menu.form.view();
    }if(h.toLowerCase().indexOf('import')>-1){
    	menu.excel.init();
    } else{
    	menu.list.bindEvent();
    	menu.list.loadData();
    }
});
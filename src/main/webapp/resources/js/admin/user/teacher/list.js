var menu = {
	config : {
		toList : base + '/teacher_getList.do',
		doSaveUrl : base + '/teacher_save.do',
		getForm : base + '/teacher_getBean.do',
		doDelUrl : base + '/teacher_delete.do',
		getCourseUrl : base + '/course_getAllList.do',
		getTeacherCourseUrl : base + '/teacher_getListByTeacher.do',
		resetPassword : base + '/user_resetPassword.do',
		doExportUrl : base + '/teacher_exportTeacherExcel.do',
		doImportUrl : base + '/teacher_importTeacherExcel.do',
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
				{
					column : 'realName',
					name : '姓名',
					width : 130,
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
					width : 70,
					align : 'center',
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

				{
					column : 'hireDate',
					name : '入职时间',
					width : 140,
					formatter : function(v, obj) {
						var str = '';
						if (v.length > 11) {
							str = v.substring(0, 11);
						} else {
							str = v;
						}
						return '<span title="' + v + '" >' + str + '</span>';
					}
				},
				{
					column : 'postTypeName',
					name : '岗位',
					width : 80,
					align : 'center',
					formatter : function(v) {
						var str = '';
						if (v.length >= 12) {
							str = v.substring(0, 12);
						} else {
							str = v;
						}
						return str;
					}
				},
				{
					column : 'mobile',
					name : '手机号',
					width : 60,
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
					column : 'workStatusName',
					name : '状态',
					width : 70,
					align : 'center',
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
					column : 'schoolName',
					name : '所属学校',
					width : 150,
					align : 'center',
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
					column : 'enable',name : '启用',width : 70,
					align : 'center',
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
					width : 290,
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
		// 事件绑定
		bindEvent : function() {
			// 1.查询
			$('.list_search').click(menu.list.loadData);
			// 2.重置
			$('.list_reset').click(menu.list.searchReset);
			// 3.新增教师
			$('.list_add').click(menu.list.add);
			// 4.编辑
			$('.byy-table').on('click', '#getEdit', menu.list.edit);
			// 5.批量删除
			$('body').on('click', '.list_del', menu.list.delList);
			// 6.查看
			$('.byy-table').on('click', '.main-color1-font', menu.list.view);
			// 7.删除
			$('.byy-table').on('click', '#getdel', menu.list.del);
			// 8.重置密码
			$('.resetPassword').click(menu.list.resetPassword);
			// 9.导出
			$('.list_import').click(menu.list.importExcel);
			// 9.导入
			$('.list_export').click(menu.excel.exportExcel);
			//10.是否启用
			$('.byy-table').on('click','#getEnable',menu.list.enable);
			//11.批量启用
			$('.batchYes').click(menu.list.batchYes);
			//12.批量禁用
			$('.batchNo').click(menu.list.batchNo);
		},
		// 加载表格数据
		loadData : function(opt) {
			// 1.得到查询框的默认参数
			var searchData = byy('.byy-form').getValues();
			// 2.向参数增加分页参数
			searchData.rows = menu.config.pagesize;
			// 3.如果有传递过来的分页参数，第几页，需要再增加进来
			if (opt) {
				// 这里通过查询获取到的page中的rows、page参数为undefined，需要判断并重新赋值一下
				searchData.rows = opt.pagesize || menu.config.pagesize;
				searchData.page = opt.curr || 1;
			} else {
				searchData.page = 1;
				searchData.rows = behavior_page_rows||menu.config.pagesize;
			}
			// 4.向后台请求，查询列表数据并进行加载
			var listIndex = byy.win.load(1);
			$.ajax({
				url : menu.config.toList,
				data : searchData,
				type : 'POST',
				success : function(res) {
					byy.win.close(listIndex);
					var resobj = byy.json(res);
					if (resobj.error) {
						byy.win.msg('后台查询错误');
					}
					var total = resobj.total;
					byy.table({
						selector : '#dtable',
						columns : menu.config.columns,
						data : resobj.rows,// opt && opt.curr == 2 ?
						// data.splice(0,2) : data,
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
		// 查询重置
		searchReset : function() {
			// 清空
			$('.byy-form').find('input:not([type=hidden])').val('');
			var cfg = $('.pagination').data('obj');
			menu.list.loadData(cfg);
		},
		// 新增教师
		add : function() {
			byy.win.open({
				title : '新增',
				type : 2,
				area : [ '850px', '530px' ],
				fixed : false, // 不固定
				maxmin : true,
				content : base + '/teacher_toAdd.do'
			});
		},
		edit : function() {
			var data = $(this).parent().parent().data('obj');
			if (!byy.isNull(data.id)) {
				byy.win.open({
					title : '编辑',
					type : 2,
					area : [ '850px', '530px' ],
					fixed : false, // 不固定
					maxmin : true,
					content : base + '/teacher_toEdit.do?id=' + data.id
				});
			} else {
				byy.win.msg('未找到相关信息，请刷新重试！');
			}
		},
		view : function() {
			var data = $(this).parent().parent().data('obj');
			if (!byy.isNull(data.id)) {
				var detailIndex = byy.win.open({
					title : '查看',
					type : 2,
					area : [ '850px', '530px' ],
					btn : [ '关闭' ],
					yes : function() {
						byy.win.close(detailIndex);
					},
					fixed : false, // 不固定
					maxmin : true,
					content : base + '/teacher_toView.do?id=' + data.id,
					success : function(lo, index) {
						var body = byy.win.getChildFrame('form', index);
						var enable = [ '是', '否' ];
						data.hasCompile = enable[data.hasCompile ? 0 : 1];
						data.hasTeacherCertificate = enable[data.hasTeacherCertificate ? 0 : 1];
						byy(body).setValues(data);
					}
				});
			} else {
				byy.win.msg('未找到相关信息，请刷新重试！');
			}
		},
		resetPassword : function() {
			var id = $('.byy-table>tbody input[type="checkbox"]:checked').map(function() {
				return $(this).parent().parent().data('obj').id;
			}).get().join(',');
			if (id.length > 0) {
				byy.win.confirm('是否确认重置密码?', {
					icon : 3,
					btn : [ '是', '否' ]
				}, function() {
					$.ajax({
						url : menu.config.resetPassword,
						type : "POST",
						data : {
							id : id
						},
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
				byy.win.msg('请至少选中一条记录重置密码!');
			}
		},
		delList : function() {
			var id = $('.byy-table>tbody input[type="checkbox"]:checked').map(function() {
				return $(this).parent().parent().data('obj').id;
			}).get().join(',');
			if (id.length > 0) {
				byy.win.confirm('是否确认删除教师?', {
					title : '提示',
					icon : 3,
					btn : [ '是', '否' ]
				// 按钮
				}, function() {
					// 删除，此处补充异步跳转后台
					$.ajax({
						url : menu.config.doDelUrl,
						type : "POST",
						data : {
							id : id
						},
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
				byy.win.msg('请至少选中一条记录进行删除!');
			}
		},
		del : function(a) {
			var id = $(a.currentTarget).parent().parent().data("obj").id;
			return null == id || void 0 == id ? void b.win.msg("没有获得相关数据，请刷新后重试！") : void b.win.confirm("是否确定删除记录？", {
				title : '提示',
				icon: 3,
				btn: ["是", "否"]
			}, function() {
				$.ajax({
					url : menu.config.doDelUrl,
					type : "POST",
					data : {
						id : id
					},
					dataType : 'json',
					success : function(res) {
						b.win.closeAll();
						var resobj = byy.json(res);
						byy.win.msg(resobj.msg);
						menu.list.searchReset();
					}
				});
			}, function() {
			})
		},
		// 导入
		importExcel : function() {
			byy.win.open({
				title : '教师批量导入',
				type : 2,
				area : [ '750px', '450px' ],
				fixed : false, // 不固定
				maxmin : true,
				content : base + '/teacher_toimport.do'
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
		bindEvent : function() {
			// 1.提交按钮绑定事件
			$('#submit').on('click', menu.form.save);
			// 2.关闭按钮绑定事件
			$('#close').on('click', menu.form.close);
			$('#courseList').val('');
			$('#courseList').prop('disabled', true);
			$('#postType').next('div.byy-form-select').delegate('dd', 'click', function() {
				// 只有是教师的时候才能选择学科
				var course = $('#postType').val();
				if (course == '10064') {
					$('#courseList').prop('disabled', false);
				} else {
					$('#courseList').val('');
					$('#courseList').prop('disabled', true);
				}
			});
			var iframe = window.frameElement.id;
			// 绑定选择学校事件
			$(".selectSchool").delegate("", "click", function() {
				parent.byy.win.open({
					type : 2,
					title : "选择学校",
					frameName : "byy-byywin",
					name : "selectschool",
					shade : .6,
					area : [ "1200px", "600px" ],
					content : base + '/school_toSelect.do?callback=cbstr&num=1&parentName=' + iframe,// num可以填具体的限制选择学校数字,该参数为选填，不填的时候，默认为选择多个学校
					className : "masterWindow"
				});
			});
		},
		view : function() {
			// 1.获取列表界面传递过来的ID
			var id = byy.getSearch('id');
			menu.form.validator();
			menu.form.loadCourse(id);
			if (id != null && id != undefined && id != '') {
				menu.form.loadData(id);
			} 
		},
		loadtime: function() {
			byy('form').reset();
			$('input[name="pwd"]').val('');
			$('input[name="email"]').val('');
		},
		
		loadCourse : function(id) {
			var listIndex = byy.win.load(1);
			var data = '';
			$.ajax({
				url : menu.config.getCourseUrl,
				type : 'POST',
				dataType : 'json',
				success : function(res) {
					byy.win.close(listIndex);
					data = byy.json(res);
					$('#courseList').html('');
					for (var i = 0; i < data.length; i++) {
						var course = data[i];
						// 首先要清空才能继续添加项
						$('#courseList').append('<option value="' + course.id + '">' + course.name + '</option>');
					}

				}
			});
			if (id != null && id != '') {
				$.ajax({
					url : menu.config.getTeacherCourseUrl,
					data : {
						id : id
					},
					type : 'POST',
					dataType : 'json',
					success : function(res) {
						byy.win.close(listIndex);
						var resobj = byy.json(res);
						var h = location.href;
						if (h.toLowerCase().indexOf('view') > -1) {
							var name = '';
							for (var i = 0; i < data.length; i++) {
								var courseId = data[i].id;
								for (var j = 0; j < resobj.length; j++) {
									var course = resobj[j].courseId;
									if (course == courseId) {
										name += data[i].name + ',';
									}

								}
							}
							$('#courseListDiv').html(name);
						} else {
							var selectArr = [];
							for (var i = 0; i < resobj.length; i++) {
								var course = resobj[i];
								selectArr.push(course.courseId);
							}
							$('#courseList').val(selectArr);
						}
					}
				});
			}
		},
		loadData : function(id) {
			$.ajax({
				url : menu.config.getForm,
				type : 'POST',
				data : {
					id : id
				},
				dataType : 'json',
				success : function(res) {
					var resobj = byy.json(res);
					byy('form').setValues(resobj);
					$('input[name="pwd"]').parent().parent().remove();
					if(resobj.userName!=''){
						if(resobj.userName.split('@').length==2){
							$('input[name=userName]').val(resobj.userName.split('@')[0]);
							$('.serialNumber').html('@'+resobj.userName.split('@')[1]);
						}
					}
					
					$('#postType').next('div.byy-form-select').delegate('dd', 'click', function() {
						// 只有是教师的时候才能选择学科
						var course = $('#postType').val();
						if (course == '10064') {
							$('#courseList').prop('disabled', false);
						} else {
							$('#courseList').val('');
							$('#courseList').prop('disabled', true);
						}
					});
					// 修改日期显示格式 controller 中 getBean→JsonUtil.serialize
					// 把日期格式给修改了
					// if(!byy.isNull($('#graduateDate').val())){
					// $('#graduateDate').val($('#graduateDate').val().substring(0,10));
					// }
					// if(!byy.isNull($('#birthday').val())){
					// $('#birthday').val($('#birthday').val().substring(0,10));
					// }
					// if(!byy.isNull($('#hireDate').val())){
					// $('#hireDate').val($('#hireDate').val().substring(0,10));
					// }
					// if(!byy.isNull($('#startWorkDate').val())){
					// $('#startWorkDate').val($('#startWorkDate').val().substring(0,10));
					// }
				}
			});
		},
		validator : function() {
			var rules = {
				userName : {
					required : true,maxlength:20
				},
				realName : {
					required : true,maxlength:20
				},
				sex :{ required:true},
				pwd : {
					required : true
				},
				email : {
					email : true,maxlength:30
				},
				workStatus:{required:true},
				mobile : {
					required:true,
					phone : true
				},
				hireDate :{required:true},
				postType :{required:true},
				schoolName:{required:true,maxlength:20},
				teacherNo : {
					digits : true,
					required:true,maxlength:20
				}
			};
			return $(menu.config.form).validate({
				focusInvalid : true,
				errorPlacement : function(error, element) {
					byy.win.tips(error.get(0).innerHTML, element);
				},
				rules : rules,
				messages : {
					teacherNo : {
						digits : '工号必须是数字'
					}
				}
			});
		},
		save : function() {
			var searchData = byy('.byy-form').getValues();
			if ($(menu.config.form).valid()) {
				var formIndex = byy.win.load(1);
				// 提交的时候将提交按钮禁用，防止重复提交
				$('#submit').off('click').addClass('disabled');
				var course = '';
				if (searchData.courseList != undefined) {
					if (searchData.courseList instanceof Array) {
						for (var i = 0; i < searchData.courseList.length; i++) {
							course += searchData.courseList[i] + ',';
						}
						searchData.courseList = course;
					}
				}
				searchData.compileType = searchData.compileType == '' ? '0' : searchData.compileType;
				searchData.dgree = searchData.dgree == '' ? '0' : searchData.dgree;
				searchData.education = searchData.education == '' ? '0' : searchData.education;
				searchData.maritalStatus = searchData.maritalStatus == '' ? '0' : searchData.maritalStatus;
				searchData.positionalTitle = searchData.positionalTitle == '' ? '0' : searchData.positionalTitle;
				searchData.postType = searchData.postType == '' ? '0' : searchData.postType;
				searchData.sex = searchData.sex == '' ? '0' : searchData.sex;
				searchData.skeletonType = searchData.skeletonType == '' ? '0' : searchData.skeletonType;
				searchData.status = searchData.status == '' ? '0' : searchData.status;
				searchData.workStatus = searchData.workStatus == '' ? '0' : searchData.workStatus;
				if($('.serialNumber').html()!=''){
					searchData.userName=searchData.userName +$('.serialNumber').html();
				}
				$.ajax({
					url : menu.config.doSaveUrl,
					type : 'POST',
					data : searchData,
					dataType : 'json',
					success : function(res) {
						byy.win.close(formIndex);
						var resobj = byy.json(res);
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
					}
				});

			}
			/*
			 * if(searchData.name==''){ flag=false; msg+='请输入教师名称!<br/>'; }
			 * if(searchData.code==''){ flag=false; msg+='请输入教师编号!<br/>'; }
			 * if(searchData.icon==''){ flag=false; msg+='请选择教师图标!<br/>'; }
			 * if(searchData.developer==''){ flag=false; msg+='请输入开发者!<br/>'; }
			 */
		},
		close : function() {
			parent.byy.win.closeAll();
		},
		test : function() {
			console.info('开始测试的');
		}
	},
	excel : {
		bindEvent : function() {
			$('.submitbtn .back,.importcancel,.pagetime').click(menu.excel.close);
			$('img.uploadimage').delegate('', 'click', menu.excel.doImport);
			// $('span.importdel').delegate('','click',menu.excel.doDeleteExcel);
			$('span.importsubmit').delegate('', 'click', menu.excel.doImportExcel);
			$('span.importdel').css('display', 'none');
			// 绑定上山事件
			var uploader_Excel = byy.upload.create({
				// swf文件路径
				swf : base + 'plugins/webuploader/Uploader.swf',
				// 文件接收服务端。
				// server: base+'/FileUploadChrunk',
				server : base + '/FileUpload',
				fileNumLimit : 10,
				chunked : true,
				chunkSize : 2048000,
				chunkRetry : 3,
				threads : 3,
				formData : {

				},
				// 选择文件的按钮。可选。
				// 内部根据当前运行是创建，可能是input元素，也可能是flash.
				pick : {
					id : '#uploadimageDiv',
					multiple : false,
					innerHTML : '<img class="uploadimage" title="点击选择文件上传"  style="cursor:pointer;" src="' + base
							+ '/plugins/ueditor/dialogs/attachment/images/image.png" alt="" />'
				},
				auto : false,
				accept : {
					title : 'excel文件',
					extensions : 'xls,xlsx',
					mimeTypes : 'application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
				}
			});

			uploader_Excel.on('fileQueued', function(file) {
				$('#uploadimageDiv').append('<div class="excel-row" id="' + file.id + '"></div>');
				/*
				 * var data_num = parseInt($('#uploadimageDiv').data('num'));
				 * $('#uploadimageDiv').data('num',data_num+1);
				 */
				uploader_Excel.upload(file);
			});

			uploader_Excel.on('uploadSuccess', function(file, response) {
				var $target = $('#' + file.id);
				// 添加返回值，暂时..
				var object = response[0];
				var filename = object.filename || '', name = file.name, fileSize = object.size || 0, type = '.' + object.type;
				$('#uploadimageDiv').hide();
				$('#uploadimagefileDiv').show();
				$('input[name="filename"]').val(name);
				$('input[name="filePath"]').val(filename);
    			$('.importName').html(name);
				$('span.importdel').css('display', 'inline-block');
			});
			uploader_Excel.on('error', function(type) {
				if (type === 'Q_EXCEED_SIZE_LIMIT') {
					byy.win.msg('文件大小超过100M!', {
						icon : 2,
						time : 1500
					})
				} else if (type === 'Q_TYPE_DENIED') {
					byy.win.msg('只支持上传excel文件!', {
						icon : 2,
						time : 1500
					})
				} else if (type === 'Q_EXCEED_NUM_LIMIT') {
					byy.win.msg('上传文件数量不能超过1个！', {
						icon : 2,
						time : 1500
					})
				} else if (type === 'F_DUPLICATE') {
					byy.win.msg('请不要重复上传文件！', {
						icon : 2,
						time : 1500
					})
				}
			});

			$('#delDiv').on('click', 'span.importdel', function(e) {
				var $this = $(this)
				var $p = $this.parent().parent().find('#uploadimageDiv').find('.excel-row');
				var tid = $p[0].id;
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
		doImportExcel : function() {
			var filename = $('input[name=filename]').val();
			var filePath = $('input[name=filePath]').val();
			if (filePath != '') {
				$.ajax({
					url : menu.config.doImportUrl,
					type : 'POST',
					dataType : 'json',
					data : {
						filePath : filePath,
						filename : filename
					},
					success : function(res) {
						b.win.closeAll();
						var resobj = byy.json(res);
						if (resobj.success == true || resobj.success == 'true') {
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
						} else {
							byy.win.msg(resobj.msg);
						}
					}
				});
			}

		},
		// 导出当前查询出的学生信息
		exportExcel : function() {
			$('form[name=uploadForm]').remove();
			$('body').append(
					'<form target="_blank" method="post" action="' + menu.config.doExportUrl
							+ '" name="uploadForm"><input type="hidden" name="realName"  value="' + $('#realName').val() + '" /></form>');
			$('form[name=uploadForm]').get(0).submit();
		},
		close : function() {
			parent.byy.win.closeAll();
		},
	}
};

function cbstr(id, name) {
	// alert(id+','+name);
	// console.info($('body').html());
	if (!byy.isNull(id) && id != '') {
		$('input[name=schoolId]').val(id);
	}
	if (!byy.isNull(name) && name != '') {
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
	
}

byy.require([ 'jquery', 'win', 'table', 'page', 'upload' ], function() {
	var h = location.href;
	byy('.byy-breadcrumb').breadcrumb();
	if (h.toLowerCase().indexOf('edit') > -1 || h.toLowerCase().indexOf('view') > -1 || h.toLowerCase().indexOf('add') > -1) {
		menu.form.bindEvent();
		menu.form.view();
	} else if (h.toLowerCase().indexOf('toimport') > -1) {
		menu.excel.bindEvent();
	} else {
		menu.list.bindEvent();
		menu.list.loadData();
	}
});
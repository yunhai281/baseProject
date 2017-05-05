var menu = {
	config : {
		toList : base + '/parent_getList.do',
		doSaveUrl : base + '/parent_save.do',
		getForm : base + '/parent_getBean.do',
		doDelUrl: base + '/parent_delete.do',
		resetPassword : base + "/user_resetPassword.do",
		gradeList : base + '/org_grade_getList.do',
		classList : base + '/org_class_getListNonePaged.do',
		studentList: base+  '/student_getListNonePaged.do',
		getSchoolStageUrl: base + "/school_getSchoolStage.do",
		getSchoolGradeUrl: base + "/org_grade_getSchoolGrade.do",
		getSchoolClassUrl: base + "/org_class_getSchoolClass.do",
		getSchoolStudentUrl: base + "/student_getListNonePaged.do",
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
			{checkbox : true,width:40,align:'center'}, 
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
			{column : 'work', name :'工作单位',width : 100,formatter : function(v){
				var str='';
				if(v.length>10){
					str = v.substring(0,10)+'...';
				}else{
					str = v;
				}
			    return '<span title="'+v+'" >'+str+'</span>';
			}},
			{column : 'educationName', name :'学历',width : 100},	
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
				var flag='';
				flag='<span class="byy-btn primary mini" id="getdel" name="getdel"><i class="fa fa-trash"></i>删除</span><span class="byy-btn primary mini" id="getEdit" name="getEdit"><i class="fa fa-edit"></i>编辑</span>';
				if(obj.enable==true||obj.enable=='true'){
					flag+='<span class="byy-btn primary mini" id="getEnable" name="getEnable"><i class="fa fa-remove"></i>禁用</span>';
				}else{
					flag+='<span class="byy-btn primary mini" id="getEnable" name="getEnable"><i class="fa fa-check"></i>启用</span>'
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
		    	area: ['850px', '530px'],
		    	fixed: false, //不固定
		    	maxmin: true,
		    	content: base+'/parent_toAdd.do'
		    });
		},
		edit : function(){
			var data = $(this).parent().parent().data('obj');
            if( !byy.isNull(data.id) ){
            	byy.win.open({
    				title: '编辑',
    				type: 2,
    				area: ['850px', '530px'],
    				fixed: false, //不固定
    				maxmin: true,
    		    	content: base+'/parent_toEdit.do?id='+data.id
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
    		    	content: base+'/parent_toView.do?id='+data.id,
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
				byy.win.confirm('是否确认删除家长?', {
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
            $('#studentDivs').delegate(".selectSchool","click",function(event){
				var t = event.currentTarget,$t = $(t),$parent = $t.parent();
				selectSchoolP = $parent;
				selectStudentP =  $parent.parent().parent();
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
			
			//学段下拉框选择事件
			$('#studentDivs').delegate('select[name="stage"]','change',menu.form.student.stageChange);
			//年级下拉框选择事件
			$('#studentDivs').delegate('select[name="grade"]','change',menu.form.student.gradeChange);
			//班级下拉框选择事件
			$('#studentDivs').delegate('select[name="classes"]','change',menu.form.student.classChange);
			//学生下拉框选择事件
			$('#studentDivs').delegate('select[name="student"]','change',function(){
			});
			//学生下拉框选择事件
			$('#studentDivs').delegate('select[name="studentRelation"]','change',menu.form.student.studentRelation);
			$('#studentDivs').delegate('.del_student','click',function(){
				$(this).parent().parent().parent().remove();
				menu.config.studentStart= menu.config.studentStart-1;
			});
			
			$('#studentDivs').delegate('.add_student','click',function(){
				var count = menu.config.studentStart;
				menu.config.studentStart= menu.config.studentStart+1;
				$("#studentDivs").append("<div id='row"+menu.config.studentStart+"' >"+$("#row0").html()+"</div>");
				$('#row'+menu.config.studentStart).find(".handlerRow").html('<span class="byy-btn mini del_student" style="margin:8px 0px;"><i class="fa fa-plus"></i>删除</span>');
				byy('#row'+menu.config.studentStart+' select[name=stage]').select(); 
				byy('#row'+menu.config.studentStart+' select[name=grade]').select(); 
				byy('#row'+menu.config.studentStart+' select[name=classes]').select(); 
				byy('#row'+menu.config.studentStart+' select[name=studentId]').select();  
				byy('#row'+menu.config.studentStart+' select[name=relation]').select();  
				 
			});
			
			
			
		},
		student:{
			/**
			 * 加载年级
			 * @param studentIdArr
			 */
			loadGrade:function(schoolId){
				var listIndex = byy.win.load(1);
				if(schoolId!=""){
					$.ajax({
						url : menu.config.gradeList,
						data : {begin:-1,end:-1,schoolId:schoolId},
						dataType : 'json',
						type : 'POST',
						success : function(res){
							byy.win.close(listIndex);
							var resobj = byy.json (res);
							var total = resobj.total;
							var options = "<option value=''>请选择年级</option>";
							$.each(resobj.rows, function(index, value) {
								options += "<option value='"+value.id+"'>"+value.name+"</option>";
							});
							selectStudentP.find("#grade").html(options);
						}
					});
				}
			},
			loadClass:function(gradeId){
				var listIndex = byy.win.load(1);
				if(gradeId!=""){
					$.ajax({
						url : menu.config.classList,
						data : {gradeId:gradeId},
						dataType : 'json',
						type : 'POST',
						success : function(res){
							byy.win.close(listIndex);
							var resobj = byy.json (res);
							var options = "<option value=''>请选择班级</option>";
							$.each(resobj, function(index, value) {
								options += "<option value='"+value.id+"'>"+value.name+"</option>";
							});
							selectStudentP.find("#classes").html(options);
						}
					});
				}
			},
			/*加载学生*/
			loadStudent : function(classId){
				var listIndex = byy.win.load(1);
				if(classId!=""){
					$.ajax({
						url : menu.config.studentList,
						data : {belongClassId:classId},
						dataType : 'json',
						type : 'POST',
						success : function(res){
							byy.win.close(listIndex);
							var resobj = byy.json (res);
							var options = "<option value=''>请选择学生</option>";
							$.each(resobj, function(index, value) {
								options += "<option value='"+value.id+"'>"+value.realName+"</option>";
							});
							selectStudentP.find("#student").html(options);
							$('.js-example-basic-single').select2( {placeholder: "选择学生", language: "zh-CN",   });
							
						}
					});
				}
			},
			/*学段改变*/
			stageChange : function(event){
				var t = event.currentTarget,$t = $(t),$parent = $t.parent();
				var v= $t.val();
				if(v == ''){
					//清空后面所有滴元素值
					menu.form.student.clearValue($parent, 0);
				}else{
					menu.form.student.clearValue($parent, 1);
					menu.form.student.loadClass(v);
				}
			},
			gradeChange : function(event){
				var t = event.currentTarget,$t = $(t),$parent = $t.parent();
				var v= $t.val();
				if(v == ''){
					//清空后面所有滴元素值
					menu.form.student.clearValue($parent, 1);
				}else{
					menu.form.student.clearValue($parent, 2);
					menu.form.student.loadClass(v);
					//menu.form.student.selectNext($parent, 1, v);
				}
			},
			classChange : function(event){
				var t = event.currentTarget,$t = $(t),$parent = $t.parent();
				var v= $t.val();
				if(v == ''){
					//清空后面所有滴元素值
					menu.form.student.clearValue($parent, 2);
				}else{
					menu.form.student.clearValue($parent, 3);
					menu.form.student.loadStudent(v);
					//menu.form.student.selectNext($parent, 2, v);
				}
			},
			studentChange : function(event){
				var t = event.currentTarget,$t = $(t),$parent = $t.parent();
				var v= $t.val();
				if(v == ''){
					//清空后面所有滴元素值
					menu.form.student.clearValue($parent, 3);
				}else{
					//直接赋值
					//$parent.find('input[name^="studentList"][name$="id"]').val(v);
					$parent.find('input[name^="parentRelation"][name$="id"]').val(v);
				}
			},
			
			/*根据不同的级别选择下一集的数据展示*/
			selectNext : function($parent,flag,id){
				switch (flag) {
				//选择年级
				case 0:
					menu.form.student.loadGrade(id);
					break;
				//选择班级
				case 1 : 
					menu.form.student.loadClass(id);
					break;
				//选择学生
				case 2 : 
					menu.form.student.loadStudent(id);
					break;
				case 3: //选择关系
					
					break;
				default:
					break;
				}
			},
			
			/*清除选择的值*/
			clearValue : function(parent,flag){
				switch (flag) {
				case 0:
					selectStudentP.find('select[name="grade"]').html('').append('<option value="">请选择</option>');
					selectStudentP.find('select[name="classes"]').html('').append('<option value="">请选择</option>');
					selectStudentP.find('select[name="student"]').html('').append('<option value="">请选择</option>');
					selectStudentP.find('input[name^="parentRelation"][name$="id"]').val('');
					break;
				case 1 : 
					selectStudentP.find('select[name="classes"]').html('').append('<option value="">请选择</option>');
					selectStudentP.find('select[name="student"]').html('').append('<option value="">请选择</option>');
					selectStudentP.find('input[name^="parentRelation"][name$="id"]').val('');
					break;
				case 2 : 
					selectStudentP.find('select[name="student"]').html('').append('<option value="">请选择</option>');
					selectStudentP.find('input[name^="parentRelation"][name$="id"]').val('');
					selectStudentP.find('input[name^="parentRelation"][name$="id"]').val('');
					break;
				case 3 : 
					selectStudentP.find('input[name^="parentRelation"][name$="id"]').val('');
					break;
				default:
					break;
				}
			},
			
			
		},
		add_student:function(){
			
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
					var h = location.href;
					if(h.toLowerCase().indexOf('view')>-1){
						if(resobj.students){
							menu.config.studentStart= resobj.students.length-1;
							var str='';
							for(var i = 0; i<resobj.students.length;i++){
								var stu=resobj.students[i];
								var stage='';
								if(stu.stageId=='0'){
									stage='幼儿园';
								}else if(stu.stageId=='1'){
									stage='小学';
								}else if(stu.stageId=='2'){
									stage='初中';
								}else if(stu.stageId=='3'){
									stage='高中';
								}
								str+='<div><span>'+stu.schoolName+'</span> <span>'+stage+'</span> <span>'+stu.gradeName+'</span> <span>'+stu.className+'</span> <span>'+stu.studentName+'</span> <div>'
							}
							$('.students').append(str)
						}
					}else{
						if(resobj.students){
							menu.config.studentStart= resobj.students.length-1;
							for(var i = 0; i<resobj.students.length;i++){
								var student=resobj.students[i]
								if(i!=0){
									$("#studentDivs").append("<div id='row"+i+"' >"+$("#row0").html()+"</div>");
									$('#row'+i).find(".handlerRow").html('<span class="byy-btn mini del_student" style="margin:8px 0px;"><i class="fa fa-plus"></i>删除</span>');
									byy('#row'+i+' select[name=stage]').select(); 
									byy('#row'+i+' select[name=grade]').select(); 
									byy('#row'+i+' select[name=classes]').select(); 
									byy('#row'+i+' select[name=studentId]').select();  
									byy('#row'+i+' select[name=relation]').select();  
								}

								var btn = $('#row'+i);
								btn.find('input[name=schoolId]').val(student.schoolId);
								btn.find('input[name=schoolName]').val(student.schoolName);
								
								btn.find('select[name=relation]').val(student.relation); 
								byy('#row'+i+' select[name=relation]').select(); 
								getStage(btn,student);
							} 
						}
					}
					
					
				}
			});
		},
		validator : function(){
			var rules = {
				userName : {required : true ,
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
				var isSu = true;
				var msg='';
				var studentId = "";
				var relation = "";
				var n = 0;
				if(menu.config.studentStart!=0){
					if(byy.isArray(searchData.studentId)){
						if(searchData.studentId.length!=(menu.config.studentStart+1)){
							isSu = false;
							msg+='请选择学生!<br/>';
						}
					}
					if(byy.isArray(searchData.relation)){
						if(searchData.relation.length!=(menu.config.studentStart+1)){
							isSu = false;
							msg+='请选家长与学生的关系!<br/>';
						}
					}
				}else{
					if(byy.isNull(searchData.studentId) || searchData.studentId == ''){
						isSu = false;
						msg+='请选择学生!<br/>';
					}
					if(byy.isNull(searchData.relation) || searchData.relation == ''){
						isSu = false;
						msg+='请选家长与学生的关系!<br/>';
					}
				}
				if(isSu){
					var formIndex = byy.win.load(1); 
					for(var i=0;i<(menu.config.studentStart+1);i++){
						if(byy.isArray(searchData.studentId)){
							searchData['students['+i+'].studentId']=searchData.studentId[i];
						}else{
							searchData['students['+i+'].studentId']=searchData.studentId
						}
							
						if(byy.isArray(searchData.relation)){
							searchData['students['+i+'].relation']=searchData.relation[i];
						}else{
							searchData['students['+i+'].relation']=searchData.relation
						}
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
				}else{
					byy.win.msg(msg,{shift:-1},function(){});
				}
				
			}
		},
		close : function(){
            parent.byy.win.closeAll();
        }
	}
};
function getStage(btn,student){
	//var data= $(this);
	var schoolId=btn.find('input[name=schoolId]').val();
	$.ajax({
		url : menu.config.getSchoolStageUrl,
		type : 'POST',
		data : {id:schoolId},
		dataType : 'json',
		success : function(res){
			//byy.win.close(formIndex);
			var resobj = byy.json(res);
			//更新列表数据
			btn.find('select[name=stage]').html('');
			var html='<option value="">请选择...</option>';
			for(var i =0; i<resobj.length;i++){
				var data=resobj[i];
				html+='<option value="'+data+'">'+data+'</option> ';
			}
			btn.find('select[name=stage]').append(html);
			byy('#'+btn[0].id+' select[name=stage]').select(); 
			if(student!=undefined){
				var stage='';
				if(student.stageId=='0'){
					stage='幼儿园';
				}else if(student.stageId=='1'){
					stage='小学';
				}else if(student.stageId=='2'){
					stage='初中';
				}else if(student.stageId=='3'){
					stage='高中';
				}
				btn.find('select[name=stage]').val(stage);
				byy('#'+btn[0].id+' select[name=stage]').select(); 
				/*var obj={stage:stage}
				byy('form').setValues(obj); */
				getGrade(btn,student);
			}
			btn.find('select[name=stage]').next('div.byy-form-select').delegate('dl', 'click', function() {
				var btnGrade = $(this).parent().parent().parent().parent();
				getGrade(btnGrade);
			});
		}
	});
}
function getGrade(btn,student){
	// 学段
	// 只有是教师的时候才能选择学科
	var stage = btn.find('select[name=stage]').val();
	var schoolId=btn.find('input[name=schoolId]').val();
	$.ajax({
		url : menu.config.getSchoolGradeUrl,
		type : 'POST',
		data : {schoolId:schoolId,stageName:stage},
		dataType : 'json',
		success : function(res){
			//byy.win.close(formIndex);
			var resobj = byy.json(res);
			//更新列表数据
			btn.find('select[name=grade]').html('');
			var html='<option value="">请选择...</option>';
			for(var i =0; i<resobj.length;i++){
				var data=resobj[i];
				html+='<option value="'+data.id+'">'+data.name+'</option> ';
			}
			btn.find('select[name=grade]').append(html);
			byy('#'+btn[0].id+' select[name=grade]').select();
			btn.find('select[name=classes]').html('');
			byy('#'+btn[0].id+' select[name=classes]').select();
			btn.find('select[name=studentId]').html('');
			byy('#'+btn[0].id+' select[name=studentId]').select();
			if(student!=undefined){
				btn.find('select[name=grade]').val(student.gradeId);
				byy('#'+btn[0].id+' select[name=grade]').select(); 
				/*var obj={grade:student.gradeId}
				byy('form').setValues(obj);  */
				getClass(btn,student);
				btn.find('select[name=stage]').next('div.byy-form-select').delegate('dl', 'click', function() {
					var btnGrade = $(this).parent().parent().parent().parent();
					getGrade(btnGrade);
				});
			}
			// 初始化之后要绑定
			btn.find('select[name=grade]').next('div.byy-form-select').delegate('dd', 'click', function() {
				var btnClass = $(this).parent().parent().parent().parent().parent().parent();
				if(btnClass[0].id=='studentDivs'){
					btnClass = $(this).parent().parent().parent().parent().parent();
				}
				getClass(btnClass);
			});
		}
	});
}
//取得班级
function getClass(btn,student){
	// 年级
	// 只有是教师的时候才能选择学科
	var gradeId =btn.find('select[name=grade]').val();
	var schoolId=btn.find('input[name=schoolId]').val();
	$.ajax({
		url : menu.config.getSchoolClassUrl,
		type : 'POST',
		data : {schoolId:schoolId,gradeId:gradeId},
		dataType : 'json',
		success : function(res){
			//byy.win.close(formIndex);
			var resobj = byy.json(res);
			//更新列表数据 
			btn.find('select[name=classes]').html('');
			var html='<option value="">请选择...</option>';
			for(var i =0; i<resobj.length;i++){
				var data=resobj[i];
				html+='<option value="'+data.id+'">'+data.name+'</option> ';
			}
			btn.find('select[name=classes]').append(html);
			byy('#'+btn[0].id+' select[name=classes]').select();
			btn.find('select[name=studentId]').html('');
			byy('#'+btn[0].id+' select[name=studentId]').select();
			if(student!=undefined){
				btn.find('select[name=classes]').val(student.classId);
				byy('#'+btn[0].id+' select[name=classes]').select(); 
				getStudent(btn,student);
				// 初始化之后要绑定
				btn.find('select[name=grade]').next('div.byy-form-select').delegate('dd', 'click', function() {
					var btnClass = $(this).parent().parent().parent().parent().parent().parent();
					if(btnClass[0].id=='studentDivs'){
						btnClass = $(this).parent().parent().parent().parent().parent();
					}
					getClass(btnClass);
				});  
			} 
			// 初始化之后要绑定
			btn.find('select[name=classes]').next('div.byy-form-select').delegate('dd', 'click', function() {
				var btnStudent = $(this).parent().parent().parent().parent().parent();
				if(btnStudent[0].id=='studentDivs'){
					btnStudent = $(this).parent().parent().parent().parent().parent();
				}
				getStudent(btnStudent);
			});
		}
	});
}
//取得班级
function getStudent(btn,student){
	// 年级
	// 只有是教师的时候才能选择学科
	var belongClassId =btn.find('select[name=classes]').val();
	var schoolId=btn.find('input[name=schoolId]').val();
	$.ajax({
		url : menu.config.getSchoolStudentUrl,
		type : 'POST',
		data : {belongClassId:belongClassId},
		dataType : 'json',
		success : function(res){
			//byy.win.close(formIndex);
			var resobj = byy.json(res);
			//更新列表数据
			var name='';
			btn.find('select[name="studentId"]').html(''); 
			var html='<option value="">请选择...</option>';
			for(var i =0; i<resobj.length;i++){
				var data=resobj[i];
				html+='<option value="'+data.id+'">'+data.realName+'</option> ';
			}
			btn.find('select[name="studentId"]').append(html);
			byy('#'+btn[0].id+' select[name="studentId"]').select();
			if(student!=undefined){
				btn.find('select[name=studentId]').val(student.studentId);
				byy('#'+btn[0].id+' select[name=studentId]').select(); 
				/*var obj={studentId:student.studentId}
				byy('form').setValues(obj);  */
			}
		}
	});
}
function cbstr(id,name){
	//console.info($('body').html()); 
	if(!byy.isNull( id ) && id != ''){
		selectSchoolP.find('input[name=schoolId]').val(id);
		//$('input[name=schoolId]').val(id);
	}
	if(!byy.isNull( name ) && name != ''){
		selectSchoolP.find('input[name=schoolName]').val(name);
		//$('input[name=schoolName]').val(name);
	}
	/*//加载学段年级班级学生数据，联动
	menu.form.student.loadGrade(id);
	//清空后面所有滴元素值
	menu.form.student.clearValue($("#studentDivs"), 0);*/
	//
	selectStudentP.find('select[name=stage]').html('');
	selectStudentP.find('select[name=grade]').html('');
	selectStudentP.find('select[name=classes]').html('');
	var name='select[name="students['+menu.config.studentStart+'].studentId"]';
	$(name).html(''); 
	byy('#row'+menu.config.studentStart+' select[name=stage]').select(); 
	byy('#row'+menu.config.studentStart+' select[name=grade]').select(); 
	byy('#row'+menu.config.studentStart+' select[name=classes]').select(); 
	byy(name).select();  
	var btn=selectStudentP;
	getStage(btn);
}

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
var main = {
	config : {
		save : base + '/roleScope_save.do',
		getBean : base +'/roleScope_getBean.do',
		orgSchoolSelect : base + '/roleScope_toOrgSchool.do',
		seletUserUrl : base + '/roleScope_toUserSelect.do',
		pagesize : 10,
		form : 'byy-form',
		userTypeMap : {
			"0":"teacher",
			"1":"student",
			"2":"parent",
			"3":"bureau"
		},
		userTypeMap2 :{
			"teacher":"0",
			"student":"1",
			"parent":"2",
			"bureau":"3"
		}
	},
	form : {
		bindEvent : function(){
			//1.提交按钮绑定事件
            $('#submit').on('click',main.form.save);
			//2.关闭按钮绑定事件
            $('#close').on('click',main.form.close);
            //3.学校绑定事件
            $('input[name="schoolNames"]').click(main.form.selectOrgSchool);
            //4.机构绑定事件
            $('input[name="governmentNames"]').click(main.form.selectOrgSchool);
            //5. 绑定选择用户事件
			$(".selectUser").delegate("", "click",main.form.selectUser);
			//6. 绑定设置方式事件
			$('.byy-form-radio').delegate("", "click",function(){
				var userScopeTypeObj = $(this);
				if(userScopeTypeObj.hasClass('byy-form-radioed')){
					$('input[name="userScopeType"]').removeAttr('checked');
					userScopeTypeObj.prev().attr('checked',true);
					var userScopeType = userScopeTypeObj.prev().val();
					if(userScopeType=='type'){
						$('#userScopeType-type').show();
						$('#userScopeType-person').hide();
					}else{
						$('#userScopeType-type').hide();
						$('#userScopeType-person').show();
					}
				}
			});
		},
		view : function(){
			//1.获取列表界面传递过来的roleId
			var roleId = byy.getSearch('roleId');
			main.form.validator();
			if(roleId != null && roleId != undefined && roleId != ''){
				main.form.loadData(roleId);
			}
		},
		loadData : function(id){
			$.ajax({
				url : main.config.getBean,
				type : 'POST',
				data : {
					roleId : id
				},
				dataType : 'json',
				success : function(res){
					var resobj = byy.json(res);
					//byy('form').setValues(resobj);
					
					//加载数据
					var addOrUpdate = resobj.addOrUpdate;
					var orgList = resobj.orgList;
					var userList = resobj.userList;
					var userType = resobj.userType;
					var goverType = resobj.goverType;
					var userScopeType = resobj.userScopeType==null||resobj.userScopeType==''?'type':resobj.userScopeType;

					$('input[name="addOrUpdate"]').val(addOrUpdate);
					//userScopeType初始化
					$('input[name="userScopeType"]').removeAttr('checked');
					$('input[name="userScopeType"][value="'+userScopeType+'"]').attr('checked',true);
					$('input[name="userScopeType"][value="'+userScopeType+'"]').next().click();
					
					if(userScopeType == 'type'){//设置方式为用户类型
						//1.清空数据并设置用户类型
						if(userType){
							$('#userType').find('div').find('.byy-form-checkbox').each(function(index,element){
								if(userType.indexOf($(element).prev().val())>-1){
									$(element).prev().attr('checked',true);
									$(element).addClass('byy-form-checked');
								}else{
									$(element).removeClass('byy-form-checked');
									$(element).prev().removeAttr('checked');
								}
							});
						}
						//2.清空数据并设置用户机构
						if(goverType){
							$('#userOrg').find('div').find('.byy-form-checkbox').each(function(index,element){
								if(goverType.indexOf($(element).prev().val())>-1){
				            		$(element).prev().attr('checked',true);
				            		$(element).addClass('byy-form-checked');
				            	}else{
				            		$(element).removeClass('byy-form-checked');
									$(element).prev().removeAttr('checked');
				            	}
							});
						}
						//3.加载用户机构数据
						if(orgList!=null&&orgList!=undefined&&orgList.length>0){
							var schoolIds = '';
							var schoolNames = '';
							var governmentIds = '';
							var governmentNames = '';
							for(var i=0;i<orgList.length;i++){
								var org = orgList[i];
								if(org.governmentType=='school'){//学校
									schoolIds += org.governmentId+',';
									schoolNames += org.governmentName+',';
								}else{//机构
									governmentIds += org.governmentId+',';
									governmentNames += org.governmentName+',';
									
								}
							}
							if(schoolIds.charAt(schoolIds.length - 1)==','){
								schoolIds = schoolIds.substring(0, schoolIds.length-1);
								schoolNames = schoolNames.substring(0, schoolNames.length-1);
							}
							if(governmentIds.charAt(governmentIds.length - 1)==','){
								governmentIds = governmentIds.substring(0, governmentIds.length-1);
								governmentNames = governmentNames.substring(0, governmentNames.length-1);
							}
						}
						if(schoolIds){
							$('#schoolDiv').show();
							$('input[name="schoolIds"]').val(schoolIds);
							$('input[name="schoolNames"]').val(schoolNames);
						}else{
							$('#schoolDiv').hide();
						}
						if(governmentIds){
							$('#governmentDiv').show();
							$('input[name="governmentIds"]').val(governmentIds);
							$('input[name="governmentNames"]').val(governmentNames);
						}else{
							$('#governmentDiv').hide();
						}
						//隐藏person设置
						$('#userScopeType-person').hide();
					}else{//设置方式为指定人员
						//4.加载指定人员
						$('input[name^="userIds"]').each(function(index,element){
							var eleUserType = $(element).attr('userType');
							var userIds = [];
							var userNames = [];
			            	for(var i=0;i<userList.length;i++){
			            		if(userList[i].userType==eleUserType){
			            			userIds.push(userList[i].userId);
			            			userNames.push(userList[i].userName);
			            		}
			            	}
			            	$(element).val(userIds.join(','));
			            	$(element).next().val(userNames.join(','));
						});
						//隐藏type设置
						$('#userScopeType-type').hide();
					}
					
					//5.用户身份绑定事件
		            $('input[type="checkbox"][name^="userType"]').each(function(index,element){
		            	$(element).next().on('click',main.form.changeUserType);
		            });
		            //6.用户机构绑定事件
		            $('input[type="checkbox"][name^="userOrg"]').each(function(index,element){
		            	$(element).next().on('click',main.form.changeUserOrg);
		            });
				}
			});
		},		
		save : function(){
			var searchData = byy('.byy-form').getValues();
			var userScopeType = $('.byy-form-radioed').prev().val();
			searchData.userScopeType = userScopeType;
			if(searchData.userScopeType=='type'){//设置方式-人员类型
				//1.获取用户身份设置
				var userType = '';
				$('input[type="checkbox"][name^="userType"]:checked').each(function(index,element){
					userType +=$(element).val()+',';
				});
				if(userType.charAt(userType.length - 1)==','){
					userType = userType.substring(0, userType.length-1);
				}
				searchData.userType = userType;
				//2.获取用户机构类型设置
				var goverType = '';
				$('input[type="checkbox"][name^="userOrg"]:checked').each(function(index,element){
					goverType +=$(element).val()+',';
				});
				if(goverType.charAt(goverType.length - 1)==','){
					goverType = goverType.substring(0, goverType.length-1);
				}
				searchData.goverType = goverType;
				if(userType!=''||goverType!=''){
					//3.获取用户机构
					var schoolIds = $('input[name="schoolIds"]').val();
					var governmentIds = $('input[name="governmentIds"]').val();
					var orgCount = 0;
					if(schoolIds){
						var schools = schoolIds.split(',');
						for(var i=0;i<schools.length;i++){
							searchData["orgList[" + (orgCount) + "].roleId"] = searchData.roleId;
							searchData["orgList[" + (orgCount) + "].governmentId"] = schools[i];
							searchData["orgList[" + (orgCount) + "].governmentType"] = 'school';//学校
							orgCount++;
						}
					}
					if(governmentIds){
						var governments = governmentIds.split(',');
						for(var i=0;i<governments.length;i++){
							searchData["orgList[" + (orgCount) + "].roleId"] = searchData.roleId;
							searchData["orgList[" + (orgCount) + "].governmentId"] = governments[i];
							searchData["orgList[" + (orgCount) + "].governmentType"] = 'government';//教育局
							orgCount++;
						}
					}
					var msg = main.form.userOrgValidator(goverType,schoolIds,governmentIds);
					if(msg!=''){
						byy.win.msg(msg);
						return;
					}
				}else{
					byy.win.msg('用户身份和机构类型不能同时为空');
					return;
				}
			}else if(searchData.userScopeType=='person'){//设置方式-指定人员
				searchData.userType = '';
				searchData.goverType = '';
				//1.获取所有类型人员是否指定
				var existUser = false;
				var selfCount = 0
				$('input[name^="userIds"]').each(function(index,element){
					var obj = $(element);
					if(obj.val()!=undefined&&obj.val()!=''){
						existUser = true;
						searchData["userList[" + (selfCount) + "].userId"] = obj.val();
						searchData["userList[" + (selfCount) + "].userType"] = obj.attr('userType');
						searchData["userList[" + (selfCount) + "].roleId"] = searchData.roleId;
						selfCount++;
					}
				});
				if(!existUser){
					byy.win.msg('请至少为一种用户指定人员');
					return;
				}
			}else{
				byy.win.msg('设置方式不合法');
				return;
			}
            
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
					parent.byy.win.msg(resobj.msg,{shift:-1}
						//关闭弹窗
					);
				}
			});
				
		},
		close : function(){
            parent.byy.win.closeAll();
        },
		validator : function(){
			var rules = {
				name : {required : true },
				evaluationOrder : {required : true,
				digits : true}
			};
			return $(main.config.form).validate({
				focusInvalid: true,
				errorPlacement : function(error,element){
					byy.win.tips(error.get(0).innerHTML,element);
				},
				rules:rules
			});
		},
		changeUserType : function(event){
			event = event ? event : window.event; 
			var obj = event.srcElement ? event.srcElement : event.target; 
			//这时obj就是触发事件的对象，可以使用它的各个属性 
			//还可以将obj转换成jquery对象，方便选用其他元素 
			var $obj = $(obj);
			//获取父级节点的上一个节点，即隐藏的chekbox元素
			var $checkbox = $obj.parent().prev();
			if($checkbox.val()=='all'){//全部
				//清空userType中所有checkbox设置
				$('#userType').find('div').find('.byy-form-checkbox').each(function(index,element){
					if($(element).hasClass('byy-form-checked')&&$(element).prev().val()!='all'){
						$(element).removeClass('byy-form-checked');
						$(element).prev().removeAttr('checked');
					}
				});
			}else{
				$('input[name="userTypeAll"]').removeAttr('checked');
				$('input[name="userTypeAll"]').next().removeClass('byy-form-checked');
			}
			
			//获取父级节点的上一个节点
			var $div = $obj.parent();
			var userType = $checkbox.val();
			var userTypeName = $checkbox.attr('title');
			//显示前台
			if($div.hasClass('byy-form-checked')){
				//操作input-checkbox设置选中
				$checkbox.attr('checked',true);
			}else{
				//操作input-checkbox设置取消选中
				$checkbox.removeAttr('checked');
			}
		},
		changeUserOrg : function(event){
			event = event ? event : window.event; 
			var obj = event.srcElement ? event.srcElement : event.target; 
			//这时obj就是触发事件的对象，可以使用它的各个属性 
			//还可以将obj转换成jquery对象，方便选用其他元素 
			var $obj = $(obj);
			//获取父级节点的上一个节点，即隐藏的chekbox元素
			var $checkbox = $obj.parent().prev();
			if($checkbox.val()=='all'){//全部
				//清空userType中所有checkbox设置
				$('#userOrg').find('div').find('.byy-form-checkbox').each(function(index,element){
					if($(element).hasClass('byy-form-checked')&&$(element).prev().val()!='all'){
						$(element).removeClass('byy-form-checked');
						$(element).prev().removeAttr('checked');
					}
				});
			}else{
				$('input[name="userOrgAll"]').removeAttr('checked');
				$('input[name="userOrgAll"]').next().removeClass('byy-form-checked');
			}
			
			//获取父级节点的上一个节点
			var $div = $obj.parent();
			var userOrg = $checkbox.val();
			//显示前台
			if($div.hasClass('byy-form-checked')){
				var checkedCount = $('#userOrg').find('div').find('.byy-form-checked').length;
				/*if(checkedCount==2){
					$('#userOrg').find('div').find('.byy-form-checked').each(function(index,element){
						$(element).removeClass('byy-form-checked');
						$(element).prev().removeAttr('checked');
					});
					$('input[name="userOrgAll"]').attr('checked',true);
					$('input[name="userOrgAll"]').next().addClass('byy-form-checked');

					$('input[name="schoolIds"]').val('');
					$('input[name="schoolNames"]').val('');
					$('input[name="governmentIds"]').val('');
					$('input[name="governmentNames"]').val('');
					
					$('#schoolDiv').hide();
					$('#governmentDiv').hide();
				}else{*/
					//操作input-checkbox设置选中
					$checkbox.attr('checked',true);
					if(userOrg=='all'){
						$('input[name="schoolIds"]').val('');
						$('input[name="schoolNames"]').val('');
						$('input[name="governmentIds"]').val('');
						$('input[name="governmentNames"]').val('');
						$('#schoolDiv').hide();
						$('#governmentDiv').hide();
					}else{
						$('#userOrg').find('div').find('.byy-form-checked').each(function(index,element){
							if($(element).prev().val()=='school'){
								$('#schoolDiv').show();
							}else if($(element).prev().val()=='government'){
								$('#governmentDiv').show();
							}
						});
					}
				/*}*/
			}else{
				//操作input-checkbox设置取消选中
				$checkbox.removeAttr('checked');
				if(userOrg=='school'){
					$('#schoolDiv').hide();
					//清空学校中已设置信息
					$('input[name="schoolIds"]').val('');
					$('input[name="schoolNames"]').val('');
				}else if(userOrg=='government'){
					$('#governmentDiv').hide();
					//清空学校中已设置信息
					$('input[name="governmentIds"]').val('');
					$('input[name="governmentNames"]').val('');
				}else{
					$('#schoolDiv').hide();
					$('#governmentDiv').hide();
					//清空已设置信息
					$('input[name="schoolIds"]').val('');
					$('input[name="schoolNames"]').val('');
					$('input[name="governmentIds"]').val('');
					$('input[name="governmentNames"]').val('');
				}
			}
			
		},
		selectOrgSchool : function(event){
			event = event || window.event;
			var obj = event.srcElement ? event.srcElement : event.target;
			var initIds = $(obj).prev().val();
			var initNames = $(obj).val();
			var type = $(obj).attr('orgtype');
			var roleId = $('input[name="roleId"]').val();
			var iframe =window.frameElement.id;
			parent.byy.win.open({
				title: '设置用户机构',
				frameName:"byy-byywin",
				name:"selectorgschool",
				type : 2,
				area : ['600px','580px'],
				fixed : false,//不固定
				maxmin : true,
				content: main.config.orgSchoolSelect+'?roleId='+roleId+'&selectType='+type+'&callback=saveRoleUserOrg&initIds='+initIds+'&initNames='+encodeURI(encodeURI(initNames))+'&parentName='+iframe,
				className:"masterWindow"
			});
		},
		selectUser : function(event){
			event = event || window.event;
			var obj = event.srcElement ? event.srcElement : event.target;
			var roleId = $('input[name="roleId"]').val();
			var userType = $(obj).attr('userType');
			var initIds = $('input[name="userIds'+userType+'"]').val();
			var initNames = $('input[name="userNames'+userType+'"]').val();
			var iframe =window.frameElement.id;
			parent.byy.win.open({
				type : 2,
				title : "选择用户",
				frameName : "byy-byywin",
				name : "selectuser",
				shade : .6,
				area : ['900px','580px'],
				content : main.config.seletUserUrl+'?roleId='+roleId+'&userType='+main.config.userTypeMap[userType]+'&callback=saveRoleUser&initIds='+initIds+'&initNames='+encodeURI(encodeURI(initNames))+'&parentName='+iframe,
				//content : main.config.seletUserUrl+'?roleId='+roleId+'&userType=&callback=saveRoleUser&initIds='+initIds+'&initNames='+encodeURI(encodeURI(initNames))+'&parentName='+iframe,
				className : "masterWindow"
			});
		},
		userOrgValidator:function(goverType,schoolIds,governmentIds){
			var msg = '';
			if(goverType){
    			if(goverType!='all'){//goverType不能全部
    				if(goverType.indexOf('school')>-1){
    					if(schoolIds==undefined||schoolIds==null||schoolIds==''){
    						msg = '用户机构-学校不能为空';
    					}
    				}
    				if(goverType.indexOf('government')>-1){
    					if(governmentIds==undefined||governmentIds==null||governmentIds==''){
    						msg = '用户机构-机构不能为空';
    					}
    				}
    			}
    		}
			return msg;
		}
		
	}
};

/**
 * 保存角色机构信息
 * @param id  机构或学校id
 * @param name 机构或学校名称
 * @param selectType  机构或学校
 * @param roleId 角色id
 */
function saveRoleUserOrg(id,name,selectType,roleId){
	if(!byy.isNull( id ) && id != ''){
		$('input[name='+selectType+'Ids]').val(id);
		$('input[name='+selectType+'Names]').val(name);
	}else{
		byy.win.msg('请选择'+(selectType!=undefined&&selectType=='government'?'教育局':'学校')+'!');
	}
}

/**
 * 保存角色机构信息
 * @param id  用户id
 * @param name 用户姓名
 * @param userType  用户身份
 * @param roleId 角色id
 */
function saveRoleUser(id,name,userType,roleId){
	if(!byy.isNull( id ) && id != ''){
		$('input[name=userIds'+main.config.userTypeMap2[userType]).val(id);
		$('input[name=userNames'+main.config.userTypeMap2[userType]).val(name);
	}else{
		byy.win.msg('请选择用户!');
	}
}

byy.require(['jquery','win','table','page'],function(){
	var h = location.href;
	byy('.byy-breadcrumb').breadcrumb();
	main.form.bindEvent();
	main.form.view();
});
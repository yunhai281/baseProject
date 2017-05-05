var main = {
	config : { 
		add:base + '/role_toAdd.do',
		edit:base + '/role_toAdd.do',
		getTreeUrl : base + '/role_getList.do', 
		getInfo : base + '/role_getBean.do',
		doSaveUrl : base + '/role_save.do',
		doDelUrl : base + '/role_delete.do',
		getAllAppListUrl: base + '/role_getAllAppList.do',
		getAppUrl: base + '/role_getAPP.do',
		saveApp: base + '/role_saveApp.do',
		userScopeUrl: base + '/roleScope_toUserScope.do',
		userUrl : base + '/role_toUsers.do',

		getTreeSearchUrl : base + '/role_toSearch.do',
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
					var listIndex = byy.win.load(1);
					$.ajax({
						url : main.config.getInfo,
						type : 'POST',
						data : {
							id :  id
						},
						dataType : 'json',
						success : function(res){
							byy.win.close(listIndex);
							//点击非目标时不显示
							$('#mainRightForm').hide();
							var resobj = byy.json(res);
							//当点击角色时才显示
							if(resobj.name!=null&&resobj.name!=''){
						    $('.byy-form').show();
						    byy('form').setValues(resobj);
							}
						}
					});
				},
				onExpand : function(event,treeId,treeNode){
					var id = treeNode.id; 
					var levelType = treeNode.levelType;
						if(treeNode.isAjaxing == false){
							treeNode.isAjaxing = true;
							$.ajax({	
								url : main.config.getTreeUrl,
								type : 'POST',
								data : {
									id : id
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
				//右键菜单
				onRightClick : function(event,treeId,treeNode){
					//只有在树上点击右键的时候，下面的显示各种菜单的功能才会起作用
					if(treeNode == null || treeNode.level == null){
						return;
					}
					main.config.treeObj.selectNode(treeNode);
					var selfId = treeNode.id;
					var appId = treeNode.level==0 ? treeNode.id : treeNode.id;
					var parentId = treeNode.level==0 ? '' : (treeNode.getParentNode().level==0 ? '' : treeNode.getParentNode().id);
					var menuArr = [
						'<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">',
					    '  <li role="presentation">',
					    treeNode.level==1 ? '' :  '<a role="menuitem" tabindex="-1" href="#" onclick="main.list.add(\''+selfId+'\',\''+treeNode.level+'\')">新增角色</a>',
					    '  </li>',
					    '  <li role="presentation">',
					    treeNode.level==0 ? '' : '<a role="menuitem" tabindex="-1" href="#" onclick="main.list.setAPP(\''+selfId+'\')">设置应用范围</a>',
					    '  </li>',
					    '  <li role="presentation">',
					    treeNode.level==0 ? '' : '<a role="menuitem" tabindex="-1" href="#" onclick="main.list.setUserScope(\''+selfId+'\')">设置用户范围</a>',
					    '  </li>',
					    '  <li role="presentation">',
					    treeNode.level==0 ? '' : '<a role="menuitem" tabindex="-1" href="#" >设置数据范围</a>',
					    '  </li>',
					    '  <li role="presentation">',
					    treeNode.level==0 ? '' : '<a role="menuitem" tabindex="-1" href="#" onclick="main.list.setUsers(\''+selfId+'\')">查看人员</a>',
					    '  </li>',						    
					    '  <li role="presentation">',
					    treeNode.level==0 ? '' : '<a role="menuitem" tabindex="-1" href="#" onclick="main.list.edit(\''+parentId+'\',\''+selfId+'\',\''+treeNode.level+'\')">编辑</a>',
					    '  </li>',
					    '  <li role="presentation">',
					    treeNode.level==0 ? '' : '     <a role="menuitem" tabindex="-1" href="#" onclick="main.list.del(\''+selfId+'\')">删除</a>',
					    '  </li>',
					   '</ul>'
					];
					$('.dropdown-menu').remove();
					$('body').append(menuArr.join(''));
					var left = event.pageX,right=event.pageY;
					var ele = event.srcElement ? event.srcElement :event.targetElement;
					//最大的父元素不显示
					if(treeNode.level == 0){
						$('.dropdown-menu').css({
							'display':'block',
							'position':'absolute',
							'left':left,
							'top':right
						});
					}else{
						$('.dropdown-menu').css({
							'display':'block',
							'position':'absolute',
							'left':left,
							'top':right
						});
					}
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
		        data : {
				   id : "0"
				},
				dataType : 'json',
				success : function(res){
					byy.win.close(listIndex);
					var levelType = res.levelType;
					var resobj = byy.json(res);
					//加载右边的信息
				    main.list.loadData(resobj);
					main.config.treeObj = $.fn.zTree.init($("#menutree"), main.config.treeset, resobj);
					//展开第一个节点
					var rootnode = main.config.treeObj.getNodeByParam("id","");
					rootnode.isParent=true;
					//main.config.treeset.callback.onExpand(rootnode);
					main.config.treeObj.expandNode(rootnode,true,true,true,true);
					//默认选中第一个节点，并执行点击事件
					main.config.treeObj.selectNode(rootnode);
					main.config.treeObj.setting.callback.onClick(null, main.config.treeObj.setting.treeId, rootnode);//调用事件
					
				}
			});
		},
		loadData : function(obj){		   
           $('.root').find('div[name=rootname]').html(obj.name);
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
						var resobj = byy.json (res);
						main.config.treeObj = $.fn.zTree.init($("#menutree"), main.config.treeset, resobj);
						//展开第一个节点
						var rootnode = main.config.treeObj.getNodeByParam("id","");
						rootnode.isParent=true;
						//main.config.treeset.callback.onExpand(rootnode);
						main.config.treeObj.expandNode(rootnode,true,true,true,true);
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
		// 设置应用范围
		setAPP: function(selfId){
			byy.win.open({
				title: '设置应用范围',
				type : 2,
				area : ['600px','300px'],
				fixed : false,//不固定
				maxmin : true,
				content: main.config.getAllAppListUrl+'?roleId='+selfId
				});
		},
		// 设置用户范围
		setUserScope: function(selfId){
			byy.win.open({
				title: '设置用户范围',
				type : 2,
				area : ['750px','450px'],	
				fixed : false,//不固定
				maxmin : true,
				//content: main.config.userScopeUrl+'?roleId='+selfId+'&callback=saveRoleUser&initIds=540fed39-633f-41b5-a181-527d71e94d73,7c95f5fd2b484007b3f7c16ddefb8872&initNames='+encodeURI(encodeURI('教士5,李磊'))
				content: main.config.userScopeUrl+'?roleId='+selfId
			});
		},
		// 设置用户范围
		setUsers: function(selfId){
			byy.win.open({
				title: '查看人员',
				type : 2,
				area : ['830px','650px'],	
				fixed : false,//不固定
				maxmin : true,
				content: main.config.userUrl+'?roleId='+selfId
			});
		},		
		add : function(selfId,level){
			byy.win.open({
				title: '新增角色',
				type : 2,
				area : ['600px','300px'],
				fixed : false,//不固定
				maxmin : true,
				content: main.config.add+'?parentId='+selfId+'&level='+level
				});
		},
		edit : function(parentId,selfId,level){
			byy.win.open({
				title: '编辑',
				type : 2,
				area : ['600px','300px'],
				fixed : false,//不固定
				maxmin : true,
				content: main.config.add+'?id='+selfId+'&parentId='+parentId+'&level='+level
				});
			
		},
		del : function(selfId){
			return null == selfId || void 0 == selfId ? void b.win.msg("没有获得相关数据，请刷新后重试！") : void b.win.confirm("是否确定删除数据？", {
				title : '提示',
				icon: 3,
				btn: ["是", "否"]
			    },function(){	
					$.ajax({
						url : main.config.doDelUrl,
						type : 'POST',
				        data : {
						   id : selfId
						},
						dataType : 'json',
						success : function(res){
							var resobj = byy.json(res);
							//更新列表数据
							byy.win.msg(resobj.msg,{shift:-1},function(){
								parent.main.list.loadTree(); 
							});
						}
					});
			  })
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
			//先加载验证数据再加载校验
			main.form.loadCommonData();
			main.form.validator();
			var id = byy.getSearch('id');
			if(id != undefined && id != null && id != '' && id != null){
				main.form.loadData(id);
			}
		},
		loadCommonData : function(){
			//上级结构赋值
			var parentId = byy.getSearch('parentId');
			var parentName =byy.getSearch('name');
			$('input[name="parentId"]').val(parentId);
			$('input[name="dictionaryName"]').val(decodeURI(parentName));
		},
		loadData : function(id){
			var listIndex = byy.win.load(1);
			$.ajax({
				url : main.config.getInfo,
				type : 'POST',
				data : {
					id :  id
				},
				dataType : 'json',
				success : function(res){
					byy.win.close(listIndex);
					var resobj = byy.json(res);
				    byy('form').setValues(resobj);
                  	var enable = ['是','否'];
				    $('div[name=available]').html(enable[resobj.available ? 0 : 1]);
				}
			});
		},
		save : function(){
			var searchData = byy('.byy-form').getValues();
			var parentId = byy.getSearch('parentId');
			var level = byy.getSearch('level');
			if(level!=undefined || level!=''){
				searchData.levelType=level; 
			} 
			if(parentId==undefined || parentId==''){
				searchData.parentId='';
			} else{
				searchData.levelType=2;
			}
			if($(main.config.form).valid()){
		    	$.ajax({
					url : main.config.doSaveUrl,
					type : 'POST',
					data : searchData,
					dataType : 'json',
					success : function(res){
						var resobj = byy.json(res);
						//更新列表数据
						parent.main.list.loadTree();
						//关闭弹窗
						parent.byy.win.closeAll();
						parent.byy.win.msg(resobj.msg,{shift:-1},function(){
							
						});
					}
				});
			}
		},
		validator : function(){
			 var rules = {
				name : {required : true,maxlength:20},
				remark : {maxlength:50}		
			};		
			return $(main.config.form).validate({
				focusInvalid: true,
				errorPlacement : function(error,element){
					byy.win.tips(error.get(0).innerHTML,element);
				},
				rules:rules
			});
		},
		close : function(){
            parent.byy.win.closeAll();
        }
	}	,
	// 设置应用
	setAPP : {
		bindEvent : function(){
			//1.提交按钮绑定事件
            $('#submit').on('click',main.setAPP.save);
			//2.关闭按钮绑定事件
            $('#close').on('click',main.setAPP.close);
            var roleId = byy.getSearch('roleId');
            $('#roleId').val(roleId);
		}, 
		loadData : function(){
			var listIndex = byy.win.load(1);
			$.ajax({
				url : main.config.getAppUrl,
				type : 'POST',
				data : {
					roleId :  $('#roleId').val()
				},
				dataType : 'json',
				success : function(res){
					byy.win.close(listIndex);
					var resobj = byy.json(res);
				    var data= $('form').find('.ssoAppDiv');
				    for(var i=0;i<data.length;i++){
				    	var d=data[i];
				    	var applicationId= $(d).find('input[name="applicationId"]');
				    	for(var j=0;j<resobj.length;j++){
				    		var id=resobj[j];
					    	var enabled= $(d).find('input[name="enabled"]');
					    	var checkbox= $(d).find('.byy-unselect.byy-form-checkbox');
				    		if(id[2]==applicationId[0].id){
						    	enabled[0].setAttribute('checked',true);
						    	$(checkbox[0]).addClass('byy-form-checked');
						    	break;
				    		}else{
				    			enabled[0].removeAttribute('checked');
				    			$(checkbox[0]).removeClass('byy-form-checked');
				    		}
				    	}
				    }
                  	 
				}
			});
		},
		save : function(){
			var searchData = byy('.byy-form').getValues();
			var enabled=''; 
			var appId ='';
			if(byy.isArray(searchData.applicationId)){
				for(var i=0;i<searchData.applicationId.length;i++){
					var d= $('#'+searchData.applicationId[i]).parent().parent().find('.byy-form-checked');
					if(d.length>0){
						enabled += "1,";
						appId+=searchData.applicationId[i]+',';
					}else{
						enabled += "0,";
					}
				}
			}
			searchData.enabled=enabled;
			searchData.appId=appId; 
			var formIndex = byy.win.load(1);
			//提交的时候将提交按钮禁用，防止重复提交
			$('#submit').off('click').addClass('disabled');
			$.ajax({
				url : main.config.saveApp,
				type : 'POST',
				data : searchData,
				dataType : 'json',
				success : function(res){
					byy.win.close(formIndex);
					var resobj = byy.json(res);
					parent.byy.win.closeAll();
					parent.byy.win.msg(resobj.msg,{shift:-1});
				}
			});
		},
		close : function(){
            parent.byy.win.closeAll();
        }
	}
	
};

/**
 * 保存用户范围-指定用户
 * @param id
 * @param name
 * @param userType
 */
function saveRoleUser(id,name,userType,roleId){
	if(!byy.isNull( id ) && id != ''){
		$.ajax({
			url : base + '/roleScope_saveRoleUser.do',
			type : 'POST',
			data : {id:id,userType:userType,roleId:roleId},
			dataType : 'json',
			success : function(res){
				var resobj = byy.json(res);
				parent.byy.win.msg(resobj.msg);
			}
		});
	}else{
		byy.win.msg('请选择用户!');
	}
}

byy.require(['jquery','win'],function(){
    var h = location.href;
	if(h.indexOf('index') > -1){
	    byy('.byy-breadcrumb').breadcrumb();//显示界面头部标题
	    main.list.init();
	    main.list.list();
	}else if(h.indexOf('getAllAppList') > -1){
		main.setAPP.bindEvent();
		main.setAPP.loadData();
	}else{ 
		main.form.bindEvent();
		main.form.form(); 
	}
});
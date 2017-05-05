var main = {
	config : { 
		add:base + '/menu_toAdd.do',
		edit:base + '/menu_toAdd.do',
		getTreeUrl : base + '/menu_getList.do', 
		getInfo : base + '/menu_getBean.do',
		doSaveUrl : base + '/menu_save.do',
		doDelUrl : base + '/menu_delete.do',
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
							//非目标时不显示
							$('.byy-form').hide();
							var resobj = byy.json(res);
							//当点击菜单时,才显示
							if(treeNode.levelType>0)
							$('.byy-form').show();
						    byy('form').setValues(resobj);
		                  	var enable = ['是','否'];
						    $('div[name=available]').html(enable[resobj.available ? 0 : 1]);
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
					    treeNode.level==2 ? '' :  '<a role="menuitem" tabindex="-1" href="#" onclick="main.list.add(\''+selfId+'\',\''+treeNode.level+'\')">新增应用菜单</a>',
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
			//开始时隐藏显示区
			$('.byy-form').hide();
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
				}
			});
		},
		loadData : function(obj){		   
           $('.root').find('div[name=rootname]').html(obj.name);
		},
		add : function(selfId,level){
			byy.win.open({
				title: '新增',
				type : 2,
				area : ['550px','350px'],
				fixed : false,//不固定
				maxmin : true,
				content: main.config.add+'?parentId='+selfId+'&level='+level
				});
		},
		edit : function(parentId,selfId,level){
			byy.win.open({
				title: '编辑',
				type : 2,
				area : ['550px','350px'],
				fixed : false,//不固定
				maxmin : true,
				content: main.config.add+'?id='+selfId+'&parentId='+parentId+'&level='+level
				});
			
		},
		del : function(selfId){
			b.win.confirm('是否确定删除菜单xxx?',{icon : 3,title : '提示',btn: ["是", "否"]},function(index){
				var lindex = b.win.load(1);
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
			//提交的时候将提交按钮禁用，防止重复提交
			$('#submit').off('click').addClass('disabled');
			if($(main.config.form).valid()){
		    	$.ajax({
					url : main.config.doSaveUrl,
					type : 'POST',
					data : searchData,
					dataType : 'json',
					success : function(res){
						var resobj = byy.json(res);
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
				name : {required : true,maxlength:20},
				sortNum : {required : true,digits : true }		
			};		
			return byy(main.config.form).validate({
				rules:rules
			});
		},
		close : function(){
            parent.byy.win.closeAll();
        }
	}	
};

byy.require(['jquery', 'win', 'table', 'page','validator'],function(){
    var h = location.href;
	if(h.indexOf('index') > -1){
	    byy('.byy-breadcrumb').breadcrumb();//显示界面头部标题
	    main.list.init();
	    main.list.list();
	}else{ 
		main.form.bindEvent();
		main.form.form(); 
	}
});
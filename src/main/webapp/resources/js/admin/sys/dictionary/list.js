var main = {
	/*用于校正表单的true和false的展示*/
	formatterTrue : function(v){
		return v && (v == true || v == 'true') ? '是' : '否';
	},
	config : {
		dicUrl:{
				del : base + '/dictionary_deleteDictionary.do',
				save : base + '/dictionary_saveDictionary.do',
				info : base + '/dictionary_getDictionaryBean.do',
				vali : base + '/dictionary_dictionaryValidate.do'
		},
		dicItemUrl:{
				del : base + '/dictionary_deleteDictionaryItem.do',
				save : base + '/dictionary_saveDictionaryItem.do',
				info : base + '/dictionary_getDictionaryItemBean.do',
				vali : base + '/dictionary_dictionaryItemValidate.do'		
		},
		getTreeSearchUrl : base + '/dictionary_toSearch.do',
		add:base + '/dictionary_toAdd.do',
		edit:base + '/dictionary_toAdd.do',
		getTreeUrl : base + '/dictionary_getList.do',
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
					var levelType = treeNode.levelType;
					if(levelType == '2'){
						$('.dic').show();
						$('.dicI').hide();
						$('.root').hide();
					var listIndex = byy.win.load(1);
					$.ajax({
						url : main.config.dicUrl.info,
						type : 'POST',
						data : {
							id :  id,
							levelType : levelType
						},
						dataType : 'json',
						success : function(res){
							byy.win.close(listIndex);
							var resobj = byy.json(res);
						    byy('.dic').setValues(resobj);
		                  	var enable = ['是','否'];
							var name = resobj.name;
							$('.dic').find('div[name=name]').html(name);
							$('.dic').find('div[name=remark]').html(resobj.remark);
						    $('.dic').find('div[name=editable]').html(enable[resobj.editable ? 0 : 1]);
		                    $('.dic').find('div[name=schooldiy]').html(enable[resobj.schooldiy ? 0 : 1]);
						}
					});						
					}else if(levelType == '3'){
						$('.dic').hide();
						$('.dicI').show();
						$('root').hide();
						var listIndex = byy.win.load(1);
						$.ajax({
							url : main.config.dicItemUrl.info,
							type : 'POST',
							data : {
								id :  id,
								levelType : levelType
							},
							success : function(res){
								byy.win.close(listIndex);
								var resobj = byy.json(res);
							    byy('.dicI').setValues(resobj);
								var name = resobj.name;
								$('.dicI').find('div[name=name]').html(name);
								$('.dicI').find('div[name=remark]').html(resobj.remark);
							}
						});
					}else{
						$('.root').show();
						$('.dic').hide();
						$('.dicI').hide();
					}
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
									id : id,
									order : 'asc',
									levelType : levelType
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
					//显示出菜单来.
					main.config.treeObj.selectNode(treeNode);
					var levelType = treeNode.levelType;
					var menuArr4 = [
						'<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">',
					    '  <li role="presentation">',
					    '     <a role="menuitem" tabindex="-1" href="#" onclick="main.list.add()">新增字典项</a>',
					    '  </li>',
					   '</ul>'
					];
					var menuArr5 = [
						'<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">',
					    '  <li role="presentation">',
					    '     <a role="menuitem" tabindex="-1" href="#" onclick="main.list.add()">新增字典值</a>',
					    '  </li>',
					    '  <li role="presentation">',
					    '     <a role="menuitem" tabindex="-1" href="#" onclick="main.list.edit()">编辑</a>',
					    '  </li>',
					    '  <li role="presentation">',
					    '     <a role="menuitem" tabindex="-1" href="#" onclick="main.list.del()">',
					    '        删除',
					    '     </a>',
					    '  </li>',
					   '</ul>'
					];
					var menuArr6 = [
						'<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">',
					    '  <li role="presentation">',
					    '     <a role="menuitem" tabindex="-1" href="#" onclick="main.list.edit()">编辑</a>',
					    '  </li>',
					    '  <li role="presentation">',
					    '     <a role="menuitem" tabindex="-1" href="#" onclick="main.list.del()">',
					    '        删除',
					    '     </a>',
					    '  </li>',
					   '</ul>'
					];
					$('.dropdown-menu').remove();
					if(levelType == '1'){
						$('body').append(menuArr4.join(''));
					}else if(levelType == '2'){
						$('body').append(menuArr5.join(''));
					}else if(levelType == '3'){
						$('body').append(menuArr6.join(''));
					}
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
				   levelType : "0"
				},
				dataType : 'json',
				success : function(res){
					byy.win.close(listIndex);
					var levelType = res.levelType;
					var resobj = byy.json(res);
					//加载右边的信息
				    main.list.loadData(resobj);
					main.config.treeObj = $.fn.zTree.init($("#dictionary"), main.config.treeset, resobj);
					//展开第一个节点
					var rootnode = main.config.treeObj.getNodeByParam("levelType","1");
					rootnode.isParent=true;
					//main.config.treeset.callback.onExpand(rootnode);
					main.config.treeObj.expandNode(rootnode,true,true,true,true);
				}
			});
		},
		loadData : function(obj){		   
           $('.root').find('div[name=rootname]').html(obj.name);
		},
		add : function(){
			var node = main.config.treeObj.getSelectedNodes()[0];
			var nodeId = node.id;
			var levelType = parseInt(node.levelType,10)+1;
			var name = node.name;
			if(levelType == '2'){
				byy.win.open({
					title: '新增字典项',
					type : 2,
					area : ['650px','500px'],
					fixed : false,//不固定
					maxmin : true,
					content : main.config.add+'?parentId='+nodeId+'&name='+encodeURI(name)+'&levelType='+levelType
					});
			}else if(levelType == '3'){
				byy.win.open({
				title: '新增字典值',
				type : 2,
				area : ['650px','500px'],
				fixed : false,//不固定
				maxmin : true,
				content : main.config.add+'?parentId='+nodeId+'&name='+encodeURI(name)+'&levelType='+levelType	
				});
			}
		},
		edit : function(){
			//得到选中的树节点
			var node = main.config.treeObj.getSelectedNodes()[0];
			var levelType = parseInt(node.levelType,10);
            if( !byy.isNull(node.id) ){
            	if(levelType == '2'){
	            	byy.win.open({
	    				title: '编辑字典项',
	    				type: 2,
	    				area: ['650px','500px'],
	    				fixed: false, //不固定
	    				maxmin: true,
	    		    	content: main.config.edit+'?id='+node.id+'&levelType='+levelType
	    			});
    			}else if(levelType == '3'){
	            	byy.win.open({
	    				title: '编辑字典值',
	    				type: 2,
	    				area: ['650px','500px'],
	    				fixed: false, //不固定
	    				maxmin: true,
	    		    	content: main.config.edit+'?id='+node.id+'&levelType='+levelType
	    			});
			    }
            }else{
            	byy.win.msg('未找到相关信息，请刷新重试！');
            }
		},
		del : function(){
			//得到选中的树节点
			var node = main.config.treeObj.getSelectedNodes()[0];
			var levelType = node.levelType;
            if(levelType == '2'){			
				if(!byy.isNull(node.id)){
					return null == node.id || void 0 == node.id ? void b.win.msg("没有获得相关数据，请刷新后重试！") : void b.win.confirm("该数据下可能有子节点,是否确定删除记录？", {
					title : '提示',
					icon: 3,
					btn: ["是", "否"]
				    },function(){		
					$.ajax({
						url : main.config.dicUrl.del,
						type : 'POST',
						data : {
							id : node.id
						},
						dataType : 'json',
						success : function(res){
							b.win.closeAll();
							var resobj = byy.json(res);
							if(resobj.success == true || resobj.success == 'true'){
								byy.win.msg(resobj.msg);
								main.list.loadTree();
							}else{
								byy.win.msg(resobj.msg);
							}
						}
					});
				    }, function(){
			               //关闭
		            })
				}
			}else if(levelType == '3'){
				if(!byy.isNull(node.id)){
					return null == node.id || void 0 == node.id ? void b.win.msg("没有获得相关数据，请刷新后重试！") : void b.win.confirm("是否确定删除记录？", {
						icon: 3,
						title: '提示',
		            	btn: ['是','否'] //按钮
				    },function(){					
					$.ajax({
						url : main.config.dicItemUrl.del,
						type : 'POST',
						data : {
							id : node.id
						},
						dataType : 'json',
						success : function(res){
							b.win.closeAll();
							var resobj = byy.json(res);
							if(resobj.success == true || resobj.success == 'true'){
								byy.win.msg(resobj.msg);
								main.list.loadTree();
							}else{
								byy.win.msg(resobj.msg);
							}
						}
					});
					}, function(){
			               //关闭
		            })
				}
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
						$("#dictionary").html();
						var resobj = byy.json (res);
						main.config.treeObj = $.fn.zTree.init($("#dictionary"), main.config.treeset, resobj);
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
			}else{
				//$('.areadiv').linkpage();
			}
		},
		loadCommonData : function(){
			//上级结构赋值
			var parentId = byy.getSearch('parentId');
			var parentName =byy.getSearch('name');
			$('input[name="dictionaryId"]').val(parentId);
			$('input[name="dictionaryName"]').val(decodeURI(parentName));
		},
		loadData : function(id){
			var levelType = byy.getSearch('levelType');
			if(levelType == '2'){
				$.ajax({
					url : main.config.dicUrl.info,
					type : 'POST',
					data : {
						id : id
					},
					dataType : 'json',
					success : function(res){
						var obj = byy.json(res);
						byy('form').setValues(obj);
					}
				});
			}else if(levelType == '3'){
				$.ajax({
					url : main.config.dicItemUrl.info,
					type : 'POST',
					data : {
						id : id
					},
					dataType : 'json',
					success : function(res){
						var obj = byy.json(res);
						byy('form').setValues(obj);
	             		//加载地域
						//$('.areadiv').linkpage(resobj.area);
					}
				});
			}
		},
		save : function(){
			var searchData = byy('.byy-form').getValues();
			var levelType = parseInt(searchData['levelType'],10);
			//提交的时候将提交按钮禁用，防止重复提交
			$('#submit').off('click').addClass('disabled');
			if($(main.config.form).valid()){
		    	if(searchData.id==""||searchData==undefined){
		    		searchData.id = 0;
		    	}
				if(searchData['levelType'] == '2'){
					$.ajax({
						url : main.config.dicUrl.save,
						type : 'POST',
						data : searchData,
						dataType : 'json',
						success : function(res){
							var resobj = byy.json(res);
							//更新列表数据
							//关闭弹窗
							parent.byy.win.closeAll();
							parent.main.list.loadTree();
							parent.byy.win.msg(resobj.msg,{shift:-1},function(){
							});
						}
					});				
				}else if(searchData['levelType'] == '3'){
				if(searchData.id==""||searchData==undefined){
		    		searchData.id = 0;
		    	}
					$.ajax({
						url : main.config.dicItemUrl.save,
						type : 'POST',
						data : searchData,
						dataType : 'json',
						success : function(res){
							var resobj = byy.json(res);
							//更新列表数据
							//关闭弹窗
							parent.byy.win.closeAll();
							parent.main.list.loadTree();
							parent.byy.win.msg(resobj.msg,{shift:-1},function(){
							});
						}
					});
				}
			}
		},
		validator : function(){
			var levelType = $('input[name=levelType]').val();
		    if(levelType == '2'){
			    var rules = {
					name : {
						required : true,
						maxlength : 20,
						remote : {
							url : main.config.dicUrl.vali,
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
					code : {
						required : true,
						remote : {
							url : main.config.dicUrl.vali,
							type : 'POST',
							dataType : 'json',
							async: true,
							data : {
								code : function(){
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
					}
			    };	
		    }else{
		    var rules = {
				name : {
					required : true,
					maxlength : 20,
					remote : {
						url : main.config.dicItemUrl.vali,
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
							},
							dictionaryId :  function (){
								var id = $('input[name=dictionaryId]').val();
								if(id==""||id==undefined){
									id = 0;
								}
								return id;
							}					
						}		
					}
				},
				value : {
					required : true,
					digits : true,
					remote : {
						url : main.config.dicItemUrl.vali,
						type : 'POST',
						dataType : 'json',
						async: true,
						data : {
							value : function(){
							 return $('input[name=value]').val();
							},
							id : function(){
								var id =  $('input[name=id]').val();		
								if(id==""||id==undefined){
									id = 0;
								}
								return id;
							},
							dictionaryId : function(){
								var id = $('input[name=dictionaryId]').val();
								if(id==""||id==undefined){
									id = 0;
								}
								return id;								
							}
						}					
					}
				},
				num : {	
					required : true,
					digits : true 
						},
				sortNum : {
					required : true,
					digits : true 
				}		
			};			
		    }		
			return $(main.config.form).validate({
				focusInvalid: true,
				errorPlacement : function(error,element){
					byy.win.tips(error.get(0).innerHTML,element);
				},
				rules:rules,
				messages:{
					name:{
						remote:"该名称已存在"
					},
					value:{
						remote:"编码值已存在"
					},
					code:{
						remote:"该编码已存在"
					}
				}
			});
		},
		close : function(){
            parent.byy.win.closeAll();
        }
	}	
};

byy.require(['jquery','win'],function(){
    var h = location.href;
	if(h.indexOf('index') > -1){
	    byy('.byy-breadcrumb').breadcrumb();//显示界面头部标题
	    main.list.init();
	    main.list.list();
	    $('.dic').hide();
	    $('.dicI').hide();
	}else{
		main.form.bindEvent();
		main.form.form(); 
	}
});
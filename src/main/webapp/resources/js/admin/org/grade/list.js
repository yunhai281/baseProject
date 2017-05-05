var main = {
	config : {
		getListUrl : base + '/org_grade_toList.do',
		getTreeUrl : base + '/school_getList.do',
		getTreeSearchUrl : base + '/school_search.do',
		form : '.byy-form',
		treeObj : null,
		treeset : {
			view : {
				selectedMulti : false
			},
			callback : {
				onExpand : function(event, treeId, treeNode) {
					var id = treeNode.id;
					var parent = treeNode.getParentNode();
					var levelType = treeNode.levelType;// 该字段用于判断再点击了government节点时，是否加载该节点下的学校
					if (parent != null && treeNode.isAjaxing == false) {
						treeNode.isAjaxing = true;
						$.ajax({
							url : main.config.getTreeUrl,
							type : 'POST',
							data : {
								id : id,
								type : treeNode.type,
								order : 'asc'
							},
							dataType : 'json',
							success : function(res) {
								var resobj = byy.json(res);
								if (main.config.treeObj != null) {
									main.config.treeObj.addNodes(treeNode, resobj, false);
								}
							}
						});
					}
				},
				onClick : function(event, treeId, treeNode, flag) {
					var id = treeNode.id;
					var isroot = treeNode.root ? true : false;
					if (treeNode.type == "school") {
						$("#right_content").load(main.config.getListUrl + "?schoolId=" + id);
					}
				}
			}
		}
	},
	list : {
		init : function() {
			// 绑定查询事件
			$('.btn_search').delegate('', 'click', main.list.search);
			// 绑定重置事件
			$('.btn_reset').delegate('', 'click', main.list.resetSearch);
		},
		list : function() {
			// 初始化树数据
			main.list.loadTree();
		},
		loadTree : function() {
			// 1.向后台请求，查询树数据并进行加载
			var listIndex = byy.win.load(1);
			$.ajax({
				url : main.config.getTreeUrl,
				type : 'POST',
				dataType : 'json',
				success : function(res) {
					byy.win.close(listIndex);
					var resobj = byy.json(res);
					main.config.treeObj = $.fn.zTree.init($("#schooldiv"), main.config.treeset, resobj);
					// 展开第一个节点
					var rootnode = main.config.treeObj.getNodeByParam("levelType", "1");
					main.config.treeObj.expandNode(rootnode);
				}
			});
		},
		search : function() {
			// 1.获取name值
			var name = $('input[name=name]').val();
			if (!byy.isNull(name) && name != '') {
				// 2.向后台请求，查询树数据并进行加载
				var listIndex = byy.win.load(1);
				$.ajax({
					url : main.config.getTreeSearchUrl,
					type : 'POST',
					data : {
						name : name
					},
					dataType : 'json',
					success : function(res) {
						byy.win.close(listIndex);
						$("#schooldiv").html();
						var resobj = byy.json(res);
						main.config.treeObj = $.fn.zTree.init($("#schooldiv"), main.config.treeset, resobj);
						// 展开第一个节点
						var rootnode = main.config.treeObj.getNodeByParam("levelType", "1");
						main.config.treeObj.expandNode(rootnode);
					}
				});
			} else {
				main.list.loadTree();
			}
		},
		resetSearch : function() {
			$('input').val('');
			main.list.loadTree();
		}
	}
};

byy.require([ 'win' ], function() {
	var h = location.href;
	if (h.indexOf('index') > -1) {
		byy('.byy-breadcrumb').breadcrumb();// 显示界面头部标题
		main.list.init();
		main.list.list();
	}
});
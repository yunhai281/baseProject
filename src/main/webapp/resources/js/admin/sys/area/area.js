/*****
思路：
1.在JS中配置该表的属性，以及对应属性的样式，比如：input,日期空间啊，下拉框啊等
2.在加载新增页面或者列表页面的时候，过来这里读取
3.读取后进行加载
*/
//var base = '/byybase';

var main = {
	//整个user功能的配置，放在这里
	config : {
		listurl : base+'/area_getList.do',
		getInfo : base+'/area_getBean.do',
		addform :'.byy-form',
		treeObj : null,
		treeset : {
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
							var resobj = byy.json(res);
							main.loadData(resobj);
						}
					});
				},
				onExpand : function(event,treeId,treeNode){
					var id = treeNode.id;
					if(treeNode.isAjaxing == false){
						treeNode.isAjaxing = true;
						//请求后台
						var listIndex = byy.win.load(1);
						$.ajax({
							url : main.config.listurl,
							type : 'POST',
							data : {
								id : id,
								available : true,
								asyn : 'true'
							},
							dataType : 'json',
							success : function(res){
								byy.win.close(listIndex);
								var resobj = byy.json(res);
								if(main.config.treeObj != null){
									main.config.treeObj.addNodes(treeNode,resobj,false);
								}
							}
						});
					}
				}
			}
		}
	},
	//页面加载初始化
	init : function(){
		main.list();
		//绑定事件
	},
	//列表数据加载渲染
	list : function(){
		//遮盖
		var listIndex = byy.win.load(1);
		//进行渲染
		$.ajax({
			url : main.config.listurl,
			type : 'POST',
			data :{
				levelType : 1,
				available : true
			},
			dataType : 'json',
			success : function(res){
				//取消遮盖
				byy.win.close(listIndex);
				var resobj = byy.json(res);
				//加载右边的信息
				main.loadData(resobj);
				main.config.treeObj = $.fn.zTree.init($("#areatree"), main.config.treeset, resobj);
				//展开第一个节点
				var rootnode = main.config.treeObj.getNodeByParam("levelType","0");
				main.config.treeObj.expandNode(rootnode,true,true,true,true);
			}
		});
	},
	loadData : function(obj){

		$.each(obj,function(n,value){
			$(main.config.addform).find('div[name='+n+']').html(value);
		})
		//更	换其他的值
		var lt = ['国家','省份','市级','县(区)'];
		var enable = ['不可用','可用'];
		$(main.config.addform).find('div[name=levelType]').html(lt[parseInt(obj.levelType,10)]);
		$(main.config.addform).find('div[name=available]').html(enable[obj.available ? 1 : 0]);
		//得到父节点的name
		if(obj.parent){
			$(main.config.addform).find('div[name=parentName]').html(obj.parentName);
		}
	}
};
//页面加载后执行。
byy.require(['jquery','win'],function(){
	main.init();
	byy('.byy-breadcrumb').breadcrumb();//显示界面头部标题
})
'use strict';
/****
 * 学校选择组件
 * @author byy
 * @since 2017年1月17日 11:42:36
 * 
 ***/
(function(window,undefined){	
	var OrgSchoolSelect = function(params){
		return new OrgSchoolSelect.fn.init(params);
	};
	OrgSchoolSelect.fn = OrgSchoolSelect.prototype = {
			constructor : OrgSchoolSelect,
			init : function(params){
				OrgSchoolSelect.cfg = params;
				return this;
			},
			version : '1.0'
	};
	OrgSchoolSelect.fn.init.prototype = OrgSchoolSelect.fn;
	OrgSchoolSelect.extend = OrgSchoolSelect.fn.extend = function(obj) {
		var target = this;
		for(var k in obj){
			target[k] = obj[k];
		}
		return target;
	};
	//以上为jquery式的构造，无关业务
	//---------------------------------------------------------------------------------------------
	//以下为业务接口
	OrgSchoolSelect.extend({  
		config : {
			school : null,//存储查询过来的学校数据
			names : {},//存储学校ID和名称对应
			pagesize : 10, 
			loadTreeUrl : base+"/school_getList.do",
			treeSet : {
				check:{
					enable: false
				},
				view : {
					selectedMulti : true
				},
				callback : {
					onExpand : function(event,treeId,treeNode){
						var id = treeNode.id;
						var parent = treeNode.getParentNode();
						var levelType = treeNode.levelType;//该字段用于判断再点击了government节点时，是否加载该节点下的学校
						if(parent!=null&&treeNode.isAjaxing == false){
							treeNode.isAjaxing = true;
							$.ajax({
								url : OrgSchoolSelect.config.loadTreeUrl,
								type : 'POST',
								data : {
									id : id,
									type:treeNode.type,
									order : 'asc'
								},
								dataType : 'json',
								success : function(res){
									var resobj = byy.json(res);
									if(OrgSchoolSelect.tree != null){
										OrgSchoolSelect.tree.addNodes(treeNode,resobj,false);
									}
								}
							});
						}
					},
					onDblClick : function(){
					},
					onClick : function(event,treeId,treeNode,flag){
						//右侧显示选中节点信息
						if(treeNode.type==cfg.selectType){
							OrgSchoolSelect.selectRecursely(treeNode.id,treeNode.name);
						}
					}
				}
				}
		},
		bindEvent : function(){
			//1. 绑定关闭按钮
			$('.pagetime').on('click',function(){
				list.back();
			});
			//2.点击右侧已设置项
			$('#selectedBox').on('click','.selectedTeacher',function(){
				var sid = $(this).attr('sid');
				byy('a.checkbox[id="'+sid+'"]').toggleChecked('ico_nocheck','ico_checked','ico_nocheck');
				$(this).remove();
				//更新数量
				$('#hasschool').html('已选择: <font color="green">'+$('.selectedTeacher').length+'</font> '+(cfg.selectType!=undefined&&cfg.selectType=='government'?'个教育局':'所学校'));
			});
			//3.绑定事件
			$('#okbtn').on('click',function(){
				OrgSchoolSelect.submit();
			});
			//4.树类型绑定事件
			$(".userTypeBox").click(function(){
				$(".userTypeBoxSelected").find("img").first().attr("src",$(".userTypeBoxSelected").find("img").first().attr("src").replace("checked","unchecked"));
				$(".userTypeBox").removeClass("userTypeBoxSelected");
				$(".userTypeBox").addClass("userTypeBoxUnSelected");
				$(this).removeClass("userTypeBoxUnSelected");
				$(this).addClass("userTypeBoxSelected");
				$(this).find("img").first().attr("src",$(this).find("img").first().attr("src").replace("unchecked","checked"))
				//OrgSchoolSelect.config.loadTreeUrl = base+"/"+$(this).attr("url");
				cfg.selectType = $('.userTypeBoxSelected').attr("stype");
				OrgSchoolSelect.createTree();
				//清空右侧已设置机构或学校
				$('#selectedBox').empty();
			});
		},
		//检索机构或学校数据-初始化树
		createTree : function(){
			if(cfg.selectType==undefined||cfg.selectType==''){
				cfg.selectType = $('.userTypeBoxSelected').attr("stype");
			}
			if(cfg.selectType=='government'){
				OrgSchoolSelect.config.loadTreeUrl = base+'/government_getList.do';
			}else{
				OrgSchoolSelect.config.loadTreeUrl = base+'/school_getList.do';
			}
			
			$.ajax({
				url : OrgSchoolSelect.config.loadTreeUrl,
				type : 'POST',
				data : {
					people:"true",
					asyn:"false"
				},
				success : function(res){
					//渲染数据，同时渲染分页数据
					//var resobj = common.parseJson('(' + res + ')');
					var resobj = byy.json(res);
					OrgSchoolSelect.tree = $.fn.zTree.init($("#group_tree_div"),OrgSchoolSelect.config.treeSet, resobj);
					var node = OrgSchoolSelect.tree.getNodes()[0];
					OrgSchoolSelect.tree.selectNode(node);//选择点  
					//OrgSchoolSelect.tree.setting.callback.onClick(null, OrgSchoolSelect.tree.setting.treeId, node);//调用事件  
				}
			});
		},
		initSelect : function(){
			if(cfg.initIds){
				var initsetIds = cfg.initIds.split(',');
				var initsetNames = cfg.initNames.split(',');
				for(var i=0;i<initsetIds.length;i++){
					OrgSchoolSelect.selectRecursely(initsetIds[i],initsetNames[i]);
				}
			}
		},
		//级联选中
		selectRecursely : function(id,name){
			if($(".selectedTeacher[id='"+id+"']").length<1)
			$("#selectedBox").append("<li title='单击删除' ptype='teacher' class='selectedTeacher' id='"+id+"' >"+name+"</li>");
			//更新数量
			$('#hasschool').html('已选择: <font color="green">'+$('.selectedTeacher').length+'</font> '+(cfg.selectType!=undefined&&cfg.selectType=='government'?'个教育局':'所学校'));
		},
		//提交
		submit : function(){
			//1.获得选中的数据
			var selected = $('.selectedTeacher');
			var len = selected.length;//所选择学校的个数（这里用来限制调用界面选择学校个数）
			//2.根据min和max限制
			var ids = selected.map(function(){return $(this).attr('id');}).get().join(',');
			var names = selected.map(function(){return $(this).text();}).get().join(',');
			
			//3.根据传递的parentName进行传值-有可能是父页面
			var frameObject =  null,topWindow = null;
			//3.校验
			var typeName = (cfg.selectType!=undefined&&cfg.selectType=='government'?'教育局':'学校');
			if(len == 0){
				byy.win.msg('请选择'+(cfg.selectType!=undefined&&cfg.selectType=='government'?'教育局':'学校')+'!');
				return;
			}else if (cfg.max!=-1&&len>parseInt(cfg.max, 10)) {
				byy.win.msg('请不要选择超过'+cfg.max+'个'+typeName+'!');
			}else if (len<parseInt(cfg.min, 10)) {
				byy.win.msg('请不要选择少于'+cfg.min+'个'+typeName+'!');
			}else{
				var frameObject =  null,topWindow = null;
				var index = byywin.getFrameIndexNew(window,window.name);
				var cbstr = cfg.callback;
				if(cfg.parentName){
					frameObject =  byy.findFrameByName(cfg.parentName);
				}
				if(null == frameObject){
					topWindow = window.parent;
				}else{
					topWindow = frameObject[0].contentWindow.window;
				}
				if(cbstr){
					//由于安全性问题，这里不通过eval执行，直接调用topWindow
					//topWindow.cbstr(ids,names,cfg.selectType,cfg.roleId);
					eval('topWindow.' +cfg.callback + '(\'' + ids + '\',\'' + names + '\',\'' + cfg.selectType + '\',\'' + cfg.roleId+ '\')');
				}
				/*if(cfg.callback){
					eval("frameObject[0].contentWindow.window."+cfg.callback+"('"+s_id+"','"+s_name+"','"+s_type+"');");
				}*/
				//parent.byy.win.close(index);
				//关闭最新弹出的窗口
				parent.byy.win.close(parent.byy.win.index);
				
			}
		},
		hide : function(){
			if($('.userTypeBox').length==1){
				$('.userTypeBox').parent().hide();
			}
		}
	});
	OrgSchoolSelect.fn.extend({
		start : function(){
			//1. 绑定事件
			//2. 加载机构或学校树
			//3. 加载初始化设置数据
			OrgSchoolSelect.bindEvent();
			OrgSchoolSelect.createTree(); 
			OrgSchoolSelect.initSelect();
			OrgSchoolSelect.hide();
			return this;
		}
	});
	
	
	//给一个短变量名
	window.OrgSchoolSelect = window.byyorgschool = OrgSchoolSelect;
})(window);

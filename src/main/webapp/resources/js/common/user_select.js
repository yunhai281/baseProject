var schoolTreeUrl = base+"/school_getList.do";
var currentDeptid = null;
var currentNode = null;
function loadRight(paginationParam){
	var treeType = $('.userTypeBoxSelected').attr("stype");
	/*
	if(treeType=='teacher'){
		treeType = $(".ts_group_checked").attr("treeType");
	}*/
	var schoolNode = currentNode;
	/*while(schoolNode&&schoolNode.type!='school'){
		schoolNode = schoolNode.getParentNode();
	}*/
	var type=currentNode.type;
	var searchData = {
		id:currentDeptid,
		type:(currentNode==null?null:currentNode.type),
		treeType:treeType,
		schoolId : (schoolNode==null?null:schoolNode.id),
		keyword:$("#search_keyword").val()
	};
	if(searchData.treeType=='parent'){
		$("#positionalTitleTh").show();
		$("#positionalTitleTh").html("孩子");
		$("#teacherNoTh").hide();
		$("#phoneTh").show();
		searchData.positionalTitle = true;
	}else if(searchData.treeType=='student'){
		$("#positionalTitleTh").hide();
		$("#positionalTitleTh").html("职务");
		$("#teacherNoTh").hide();
		$("#phoneTh").show();
	}else if(searchData.treeType=='bureau'){
		$("#teacherNoTh").hide();
		$("#phoneTh").show();
		$("#positionalTitleTh").show();
		$("#positionalTitleTh").html("岗位");
		searchData.positionalTitle = true;
	}else{
		$("#positionalTitleTh").hide();
		$("#teacherNoTh").show();
		$("#phoneTh").show();
	}
	/*
	if(currentNode!=null&&currentNode.type=="course"){
		searchData.stageId = currentNode.value1;
	}
	*/
	if(paginationParam){
		searchData = apply(searchData,paginationParam,true);
	}else{
		searchData = apply(searchData,{rows:10},true);
	}
	if(type=='school'||(treeType=='bureau'&&type=='government')){
		$.ajax({
			url : base+"/user_getUserBySchool.do",
			type : 'POST',
			asyn:'true',
			data : searchData,
			success : function(res){
				//渲染数据，同时渲染分页数据
				//var resobj = common.parseJson('(' + res + ')');
				var resobj = byy.json(res);
				var total = resobj.total;
				var html = "";
				if(resobj.rows!=null&&resobj.rows.length>0){
					for(var i=0;i<resobj.rows.length;i++){
						if(i%2==0){
							html = html+'<tr class="line_g1" height="33" >';
						}else{
							html = html+'<tr class="line_g1 line_gchange" height="33" >';
						}
						html = html+'<td><a class="checkbox ico_nocheck" sid="'+resobj.rows[i].id+'" sname="'+resobj.rows[i].realName+'" ></a></td>';
						if(searchData.treeType=='parent'||searchData.treeType=='bureau'){
							html = html+'<td>'+resobj.rows[i].realName+'</td>';
							html = html+'<td>'+resobj.rows[i].mobile+'</td>';
							var positionalTitle = '无';
							if(searchData.treeType=='parent'){
								positionalTitle = resobj.rows[i].childName;
							}else{
								positionalTitle = resobj.rows[i].postName;
							}
							if(positionalTitle==null||positionalTitle=="null"){
								positionalTitle = "无";
							}
							html = html+'<td>'+positionalTitle+'</td>';
						}else if(searchData.treeType=='student'){
							html = html+'<td>'+resobj.rows[i].realName+'</td>';
							html = html+'<td>'+resobj.rows[i].mobile+'</td>';
						}else{
							html = html+'<td>'+resobj.rows[i].teacherNo+'</td>';
							html = html+'<td>'+resobj.rows[i].realName+'</td>';
							html = html+'<td>'+resobj.rows[i].mobile+'</td>';
						}
						html = html+'</tr>';
					}
				}
				if(resobj.rows.length==0){
					/*if(searchData.treeType=='parent'||searchData.treeType=='student'){
						html='<td align="center" style="height:115px;line-height:115px;" colspan="4">没有查询到数据</td>';
					}else{
						html='<td align="center" style="height:115px;line-height:115px;" colspan="5">没有查询到数据</td>';
					}*/
					html='<td align="center" style="height:115px;line-height:115px;" colspan="4">没有查询到数据</td>';
				}
				//checkbox点击事件
				/*pagination.init({
					count : total,
					increment:2,
					pagesize : (paginationParam && paginationParam.rows) ? paginationParam.rows :10,
							pageArray:[10,100,1000],
							callback : loadRight
				});*/
				byy.page({
					selector : '.pagination',
					total : total,
					pagesize : (paginationParam && paginationParam.rows) ? paginationParam.rows :10,
					curr : paginationParam ? paginationParam.curr : 1,
					callback : function(paginationParam) {
						loadRight(paginationParam);
					}
				});
				$('.list_table').find('tbody').html('').append(html);
				//仅样式更换 具体选中事件请结合实际情况添加
				$(".checkbox[id!='set_all']").click(function(){
					if($(this).hasClass("ico_nocheck")){
						//选中
						$(this).removeClass("ico_nocheck").addClass("ico_checked");	
					}else{
						//取消选中
						$(this).removeClass("ico_checked").addClass("ico_nocheck");	
					}
				});	
				//操作列按钮点击方法绑定
				for(var i=0;i<resobj.rows.length;i++){
					$('.table-delete'+i).attr("contactId",resobj.rows[i].id);
					$('.table-delete'+i).click(function(){
						tableDel($(this).attr("contactId"));
					});
					$('.table-show'+i).attr("contactId",resobj.rows[i].id);
					$('.table-show'+i).click(function(){
						tableShow($(this).attr("contactId"));
					});
					$('.table-modify'+i).attr("contactId",resobj.rows[i].id);
					$('.table-modify'+i).click(function(){
						tableMod($(this).attr("contactId"));
					});
				}
			}
		});
	}else{
		$('.list_table').find('tbody').html('');
	}
}
function initTree(){
	schoolTreeUrl = base+"/"+$(".userTypeBoxSelected").attr("url");
	$.ajax({
			url : schoolTreeUrl,
			type : 'POST',
			data : {
				people:"true",
				asyn:"false"
				//,showGroup:showGroup,
				//showChildren:showChildren
				//,treeType:$(".ts_group_checked").attr("treeType")//教师选择时可根据教师部门、年级、科目可以进行查询
			},
			success : function(res){
				//渲染数据，同时渲染分页数据
				//var resobj = common.parseJson('(' + res + ')');
				var resobj = byy.json(res);
				teacherTree = $.fn.zTree.init($("#group_tree_div"),{
				check:{
					enable: false
				},
					view : {
					selectedMulti : false
				},
				callback : {
					onExpand : function(event,treeId,treeNode){
						var id = treeNode.id;
						var parent = treeNode.getParentNode();
						var levelType = treeNode.levelType;//该字段用于判断再点击了government节点时，是否加载该节点下的学校
						if(parent!=null&&treeNode.isAjaxing == false){
							treeNode.isAjaxing = true;
							$.ajax({
								url : schoolTreeUrl,
								type : 'POST',
								data : {
									id : id,
									type:treeNode.type,
									order : 'asc'
								},
								dataType : 'json',
								success : function(res){
									var resobj = byy.json(res);
									if(teacherTree != null){
										teacherTree.addNodes(treeNode,resobj,false);
									}
								}
							});
						}
					},
					onDblClick : function(){
					},
					onClick : function(event,treeId,treeNode,flag){
						currentDeptid = treeNode.id;
						currentNode = treeNode;
						$("#search_keyword").val("");
						loadRight();
					}
				}
				}, resobj);
				var node = teacherTree.getNodes()[0];
				teacherTree.selectNode(node);//选择点  
				teacherTree.setting.callback.onClick(null, teacherTree.setting.treeId, node);//调用事件  
			}
		});
}
function initFunction(){
	//单击删除事件
	$('body').on('click','.selectedTeacher',function(){
		try{
			var id = $(this).attr("id");
			$(this).remove();
			teacherTree.checkNode(teacherTree.getNodeByParam( "id",id ),false); 
		}catch(ex){}
	});
	$('.pagetime').delegate('','click',close);
	$(".select_teachers").click(function(){
		loadRight();
	});
	$(".userTypeBox").click(function(){
		$(".userTypeBoxSelected").find("img").first().attr("src",$(".userTypeBoxSelected").find("img").first().attr("src").replace("checked","unchecked"));
		$(".userTypeBox").removeClass("userTypeBoxSelected");
		$(".userTypeBox").addClass("userTypeBoxUnSelected");
		$(this).removeClass("userTypeBoxUnSelected");
		$(this).addClass("userTypeBoxSelected");
		$(this).find("img").first().attr("src",$(this).find("img").first().attr("src").replace("unchecked","checked"))
		schoolTreeUrl = base+"/"+$(this).attr("url");
		/*
		if($(this).attr("stype")=="teacher"){
			$("#treeTypeBox").show();
		}else{
			$("#treeTypeBox").hide();
		}*/
		//清空右侧已选择
		$("#selectedBox").empty();
		initTree();
	});
	$('body').keydown(function(e){
		if(e.keyCode==13){
			loadRight();
		}
	});
	/*
	$("#treeTypeBox>span").click(function(){
		$("#treeTypeBox>span").removeClass("ts_group_checked").addClass("ts_group_unchecked");
		$(this).removeClass("ts_group_unchecked").addClass("ts_group_checked");
		initTree();
	});
	*/
	$("#set_all").click(function(){
		if($(this).hasClass("ico_nocheck")){
			$(this).addClass("ico_checked");
			$(this).removeClass("ico_nocheck");
			$.each($('.checkbox'), function(){
				var id = $(this).attr('sid');
				var name = $(this).attr('sname');
				if(id){
					var s_count = $(".selectedTeacher").length+1;
					if (cfg.min!=-1&&s_count>parseInt(cfg.max, 10)) {
						byy.win.msg('请不要选择超过'+cfg.max+'人!');
						return false;
					}else if(cfg.removeId.indexOf(id)>-1){
						byy.win.msg('不能选择'+name+'!');
						return false;
					}else{
						selectRecursely(id,name);
						$(this).addClass("ico_checked");
						$(this).removeClass("ico_nocheck");
					}
				}
			});
		}else{
			$(".checkbox").removeClass("ico_checked").addClass("ico_nocheck");
			$.each($('.checkbox'), function(){
				var id = $(this).attr('sid');
				var name = $(this).attr('sname');
				if(id)
				unSelectRecursely(id,name);
			});
		}
	});
	//取消选中
	$('.mlist-rtmenu').on("click",".ico_nocheck",function(){
		var id = $(this).attr('sid');
		var name = $(this).attr('sname');
		if(id)
		unSelectRecursely(id);
	});
	//选中
	$('.mlist-rtmenu').on("click",".ico_checked",function(){
		var id = $(this).attr('sid');
		var name = $(this).attr('sname');
		if(id){
			var s_count = $(".selectedTeacher").length+1;
			if (cfg.max!=-1&&s_count>parseInt(cfg.max, 10)) {
				byy.win.msg('请不要选择超过'+cfg.max+'人!');
				$(this).removeClass("ico_checked");
				$(this).addClass("ico_nocheck");
			}else if(cfg.removeId.indexOf(id)>-1){
				byy.win.msg('不能选择'+name+'!');
				$(this).removeClass("ico_checked");
				$(this).addClass("ico_nocheck");
			}else{
				selectRecursely(id,name);
			}
		}
	});
	$("#okbtn").click(function(){
		//多选
		var s_name = "";
		var s_id = "";
		var treeType = $('.userTypeBoxSelected').attr("stype");
		var s_count = 0;
		$(".selectedTeacher").each(function(){
			s_count++;
			if(s_name==""){
				s_name = $(this).html();
				s_id = $(this).attr("id");
			}else{
				s_name += ","+$(this).html();
				s_id +=  ","+$(this).attr("id");
				//s_type+=  ","+$(this).attr("ptype");
			}
		});
		if(s_count==0){
			byy.win.msg('请选择用户!');
		}else if (cfg.max!=-1&&s_count>parseInt(cfg.max, 10)) {
			byy.win.msg('请不要选择超过'+cfg.max+'人!');
		}else if (s_count<parseInt(cfg.min, 10)) {
			byy.win.msg('请不要选择少于'+cfg.min+'人!');
		}else{
			var frameObject =  null,topWindow = null;
			var index = byywin.getFrameIndexNew(window,window.name);
			var cbstr = callback;
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
				//topWindow.cbstr(s_id,s_name,s_type);
				eval('topWindow.' +cfg.callback + '(\'' + s_id + '\',\'' + s_name + '\',\'' + treeType + '\',\'' + cfg.roleId+ '\')');
			}
			/*if(cfg.callback){
				eval("frameObject[0].contentWindow.window."+cfg.callback+"('"+s_id+"','"+s_name+"','"+s_type+"');");
			}*/
			parent.byy.win.close(parent.byy.win.index);
			
		}
	});
	initTree();
	//翻页时取消所有选中
	$('body').on('click',".pagedown,.pageup",function(){
		$(".checkbox").removeClass("ico_checked").addClass("ico_nocheck");
	});
	//初始化显示右侧已选中
	if(cfg.initIds){
		var initsetIds = cfg.initIds.split(',');
		var initsetNames = cfg.initNames.split(',');
		for(var i=0;i<initsetIds.length;i++){
			selectRecursely(initsetIds[i],initsetNames[i]);
		}
	}
}
//级联选中
function selectRecursely(id,name){
	if($(".selectedTeacher[id='"+id+"']").length<1)
	$("#selectedBox").append("<li title='单击删除' ptype='teacher' class='selectedTeacher' id='"+id+"' >"+name+"</li>");
}
//级联取消选中
function unSelectRecursely(id){
	$(".selectedTeacher[id='"+id+"']").remove();
}
function close(){
    //parent.byy.win.closeAll();
	list.back();
}

/***
 * 简单复制
 * 合并对象，以前面对象为主体，主体有则忽略，主体没有则赋值。
 * @param {} dest:目标对象
 * @param {} def ：源对象
 * @param boolean flag : true 则会强制赋值给目标对象，即使目标对象存在该属性，false,则不会，如果目标对象存在该属性则忽略。
 * @return {}
 */
function apply(dest,def,flag){
	for(var p in def){
		if(null != dest[p] && undefined != dest[p]){
			if(flag && flag == true){
				dest[p] = def[p];
			}
		}else{
			dest[p] = def[p];
		}
	}
	return dest;
}
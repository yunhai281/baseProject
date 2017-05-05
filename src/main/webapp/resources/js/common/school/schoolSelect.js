'use strict';
/****
 * 学校选择组件
 * @author byy
 * @since 2017年1月17日 11:42:36
 * 
 ***/
(function(window,undefined){	
	var SchoolSelect = function(params){
		return new SchoolSelect.fn.init(params);
	};
	SchoolSelect.fn = SchoolSelect.prototype = {
			constructor : SchoolSelect,
			init : function(params){
				SchoolSelect.cfg = params;
				return this;
			},
			version : '1.0'
	};
	SchoolSelect.fn.init.prototype = SchoolSelect.fn;
	SchoolSelect.extend = SchoolSelect.fn.extend = function(obj) {
		var target = this;
		for(var k in obj){
			target[k] = obj[k];
		}
		return target;
	};
	//以上为jquery式的构造，无关业务
	//---------------------------------------------------------------------------------------------
	//以下为业务接口
	SchoolSelect.extend({  
		config : {
			school : null,//存储查询过来的学校数据
			names : {},//存储学校ID和名称对应
			pagesize : 10, 
			loadSchoolUrl : base+'/school_getSchoolList.do',
		},
		load : function(url,data,fn){ 
			$.ajax({
				url : url,
				data : data,
				dataType : 'json',
			}).done(function(res){
				var resobj = byy.json(res); 
				fn(resobj);
			});
		},
		bindEvent : function(){
			//1. 绑定关闭按钮
			$('.pagetime').on('click',function(){
				list.back();
			});
			//2.点击全选
			$('#set_all').on('click',function(){
				byy($(this)).toggleChecked('ico_nocheck','ico_checked');
				if($(this).hasClass('ico_checked')){
					//选中下面的所有的
					byy($('.list_table').find('tbody').find('a.checkbox')).toggleChecked('ico_nocheck','ico_checked','ico_checked');
				}else{
					byy($('.list_table').find('tbody').find('a.checkbox')).toggleChecked('ico_nocheck','ico_checked','ico_nocheck');
				}
				SchoolSelect.collectSelect();
			});
			$('.list_table_body').on('click','a.checkbox',function(){
				byy($(this)).toggleChecked('ico_nocheck','ico_checked');
				SchoolSelect.collectSelect();
				return false;
			});
			$('.list_table_body').on('click','tr',function(){
				var leftIds2 = $('a.checkbox.ico_checked:not(#set_all)');//选中的
				if(SchoolSelect.cfg.num<leftIds2.length +1){
					byy.win.msg('最多只能选择'+SchoolSelect.cfg.num+'个学校!');
					return;
				}
				byy($(this).find('a.checkbox')).toggleChecked('ico_nocheck','ico_checked');
				SchoolSelect.collectSelect();
				return false;
			});
			$('#selectedBox').on('click','.selectedTeacher',function(){
				var sid = $(this).attr('sid');
				byy('a.checkbox[id="'+sid+'"]').toggleChecked('ico_nocheck','ico_checked','ico_nocheck');
				$(this).remove();
				//更新数量
				$('#hasschool').html('已选择: <font color="green">'+$('.selectedTeacher').length+'</font> 所学校');
			});
			$('.select_teachers').on('click',function(){
				SchoolSelect.searchSchool(null,1,SchoolSelect.config.pagesize);
			});
			$('.type_cls').on('click',function(){
				$(this).toggleClass('active');
				//检索
				SchoolSelect.searchSchool(null,1,SchoolSelect.config.pagesize);
			});
			$('#okbtn').on('click',function(){
				SchoolSelect.submit();
			});
			$('#collect_all').on('click',function(){
				//将所有的学校放在addIds中，同时，如果当前页面没有选中的话，则需要选中
				var tarr = addIds.split(',');
				for(var id in SchoolSelect.config.names){
					if(addIds.indexOf(id) < 0){
						tarr.push(id);
					}
				}
				addIds = tarr.join(',');
				//选中当前页面所有复选框
				$('.ico_nocheck').addClass('ico_checked').removeClass('ico_nocheck');
				SchoolSelect.collectSelect();
			});
		},
		createTree : function(nodes,rootId){
			SchoolSelect.schoolTree = $.fn.zTree.init($("#group_tree_div"),{
				check:{
					enable: true,
					halfCheck : false
				},
				view : {
					selectedMulti : false
				},
				callback : {
					onExpand : function(event,treeId,treeNode){
						if(treeNode.isAjaxing == false){
							//继续后台加载.
							SchoolSelect.loadAreaChild(treeNode);
						}
					},
					onCheck : function(event,treeId,treeNode){
						//查找学校，并展示
						var nodes = SchoolSelect.schoolTree.getCheckedNodes(true);
						var checkedNodes = nodes.filter(function(ele){
							var aa = ele.getCheckStatus();
							return aa.checked && !aa.half;
						});
						var nodeids = checkedNodes.map(function(ele){
							return ele.id;
						});
						SchoolSelect.searchSchool(nodeids,1,SchoolSelect.config.pagesize);
					}
				}
			}, nodes);
			//展开第一root节点
			var root = SchoolSelect.schoolTree.getNodeByParam("id",rootId);
			if(root){
				root.isAjaxing = true;
				SchoolSelect.schoolTree.expandNode(root);
			}
			//执行一次检索
			SchoolSelect.searchSchool(null,1,SchoolSelect.config.pagesize);
		}, 
		//检索学校数据
		searchSchool : function(areaIds,page,rows){
			if(page == undefined){
				page = 1;
			}
			if(rows == undefined){
				rows = SchoolSelect.config.pagesize;
			}
			//获得输入关键字
			var mindex = byy.win.load(1,{});
			var keyword = encodeURIComponent($('#search_keyword').val().trim()); 
			SchoolSelect.load(SchoolSelect.config.loadSchoolUrl,{page : page,name : keyword,  rows : rows},function(res){
				byy.win.close(mindex);
				//将数据渲染到视图中....
				//将数据缓存到浏览器内存中，用于下次检索或翻页
				SchoolSelect.config.school = res;
				//存储数据
				res.items.forEach(function(ele,index){
					var id = ele.id;
					SchoolSelect.config.names[id] = ele;
				});
				
				var items = res.items.slice(0,SchoolSelect.config.pagesize);
				var resobj = {
						total : res.total,
						items : items
				};
				SchoolSelect.renderSchool(resobj,page,rows);
				//执行一次选中右侧数据，将已经用户传递过来的数据加进去。
				SchoolSelect.collectSelect();
			});
		},
		//找到选中的元素，并显示在右侧区域
		collectSelect : function(){
			//1.获得右侧的数据
			//2. 获得左侧的数据
			//3.对比左侧的选中，与右侧的数据进行对应
			//4.获得剩余的，然后渲染到右侧。
			var leftIds = $('a.checkbox.ico_nocheck:not(#set_all)');//没选中的
			var leftIds2 = $('a.checkbox.ico_checked:not(#set_all)');//选中的
			//将左侧没有选中的，在默认中去除，并将左侧选中的，在默认中增加。
//			var rightIds = $('.selectTeacher');//右边的
			leftIds.each(function(){
				var id = $(this).attr('id');
				//将右侧的删除
				if(addIds.indexOf(id) > -1){
					addIds = addIds.replace(id,'');
				}
				$('.selectedTeacher[sid="'+id+'"]').remove();
			});
			leftIds2.each(function(){
				var id = $(this).attr('id'),name = $(this).attr('name');
				if(addIds.indexOf(id) < 0){
					var tarr = addIds.split(',');
					tarr.push(id);
					addIds = tarr.join(',');
				}
				//先查找是否存在，如果存在，就不处理，如果不存在则添加
				if($('.selectedTeacher[sid="'+id+'"]').length == 0){
					$('#selectedBox').append('<li class="selectedTeacher" title="点击直接移除" sid="'+id+'" sname="'+name+'">'+name+'</li>');
				}
			});
			addIds.split(',').forEach(function(ele,index){
				if(ele != ''){
					var sch = SchoolSelect.config.names[ele];
					var id = sch.id,name = sch.name;
					if($('.selectedTeacher[sid="'+id+'"]').length == 0){
						$('#selectedBox').append('<li class="selectedTeacher" title="点击直接移除" sid="'+id+'" sname="'+name+'">'+name+'</li>');
					}
				}
			});
			//在右侧显示选择的数量
			$('#hasschool').html('已选择: <font style="color:green;">'+$('.selectedTeacher').length + '</font> 所学校');
		},
		 
		
		renderSchool : function(res,page,pagesize){
			//开始渲染，包括table以及分页。,如果数据为空，要处理以下。
			if(SchoolSelect.cfg.num=='1'){
				$('#collect_all').remove();
			}
			var total = res.total || 0;
			var rows = res.items || [];
			//生成tbody
			var $tbody = $('.list_table').find('tbody');
			$tbody.html('');
			var addArr = [];
			var htmls = rows.map(function(ele){
				var id = ele.id,name = ele.name,areaname = ele.code ? ele.code : '';
				//比对选中和禁用的学校信息
				var checkHtml = '';
				//判断右侧的是否在这里已经有选中的了，实时获取
				var selectedIds = $('.selectedTeacher').map(function(){return $(this).attr('sid');}).get().join(',');
//				selectedIds = selectedIds.join(',');
				if(selectedIds.indexOf(id) > -1){
					checkHtml = '<a class="checkbox ico_checked" id="'+id+'" name="'+name+'"></a>';
				}else if(SchoolSelect.cfg.addIds.indexOf(id) >-1){//默认选中
					checkHtml = '<a class="checkbox ico_checked" id="'+id+'" name="'+name+'"></a>';
					addArr.push({id : id,name : name});
				}/*else if(SchoolSelect.cfg.removeIds.indexOf(id) > -1){
					checkHtml = '';
				}*/else{
					checkHtml = '<a class="checkbox ico_nocheck" id="'+id+'" name="'+name+'"></a>';
				}
				return '<tr height="33px"><td>'+(checkHtml)+'</td><td>'+name+'</td><td>'+areaname+'</td></tr>';
			});
			if(htmls.length > 0){
				$tbody.append(htmls.join(''));
			}else{
				//empty
				$tbody.append('<tr height="100px" line-height="100px"><td colspan="3"><div>没有检索到符合条件的学校</div></td></tr>');
			}
			if(addArr.length > 0){//将默认选中的学校添加到右侧显示区域
				addArr.forEach(function(ele){
					var id = ele.id,name = ele.name;
					if($('.selectedTeacher[sid="'+id+'"]').length == 0){
						$('#selectedBox').append('<li class="selectedTeacher" title="点击直接移除" sid="'+id+'" sname="'+name+'">'+name+'</li>');
					}
				});
			}
			//生成分页
			byy.page({
				pageArray:commonPageArray,
                selector : '.pagination',
                total : total,
                showTotal : true,
                pagesize :pagesize, 
                curr : page,
                callback : function(p){
                	var page = p.curr,tr = p.pagesize;
					SchoolSelect.searchSchool(null,page,tr);
                }
            });
			//将全选取消
			$('#set_all').removeClass('ico_checked').addClass('ico_nocheck');
		},
		//提交
		submit : function(){
			//1.获得选中的数据
			var selected = $('.selectedTeacher');
			var len = selected.length;//所选择学校的个数（这里用来限制调用界面选择学校个数）
			//2.根据min和max限制
			var ids = selected.map(function(){return $(this).attr('sid');}).get().join(',');
			var names = selected.map(function(){return $(this).attr('sname');}).get().join(',');
			//3.根据传递的parentName进行传值-有可能是父页面
			var frameObject =  null,topWindow = null;
			/*回调页面赋值---暂时不用
			 * var $parent = $(topWindow.document);
			var sipid = SchoolSelect.cfg.iptId,sipname = SchoolSelect.cfg.iptName;
			if(sipid != ""){
				if($parent.find('#'+sipid).length > 0){
					$parent.find('#'+sipid).val(ids);
				}else if($parent.find('[name="'+sipid+'"]').length > 0){
					$parent.find('[name="'+sipid+'"]').val(ids);
				}
			}
			if(sipname != ""){
				if($parent.find('#'+sipname).length > 0){
					$parent.find('#'+sipname).val(names);
				}else if($parent.find('[name="'+sipname+'"]').length > 0){
					$parent.find('[name="'+sipname+'"]').val(names);
				}
			}*/
			if(len == 0){
				byy.win.msg('请选择学校！');
				return;
			}
			//查看允许选择的学校数量
			var sNum = SchoolSelect.cfg.num;
			if(sNum != ''){
				if(byy.isNumeric( sNum )){
					if(sNum < len){
						byy.win.msg('最多只能选择'+sNum+'个学校！');
						return;
					}
				}
			}
//			var index = byywin.getFrameIndexNew(window,window.name);
			var cbstr = SchoolSelect.cfg.callback;
			var parentName = SchoolSelect.cfg.parentName;
			if(parentName){
				frameObject =  byy.findFrameByName(parentName);
			}
			if(null == frameObject){
				topWindow = window.parent;
			}else{
				topWindow = frameObject[0].contentWindow.window;
			}
			if(cbstr){
				//由于安全性问题，这里不通过eval执行，直接调用topWindow
				topWindow.cbstr(ids,names);
			}
			//关闭最新弹出的窗口
			parent.byy.win.close(parent.byy.win.index);
		},
	});
	SchoolSelect.fn.extend({
		start : function(){
			//1. 加载区域地域信息
			//2. 绑定事件
			//3. 根据事件进行处理不同的事件，
			//4. 保存，关闭
			SchoolSelect.bindEvent();
			SchoolSelect.searchSchool(); 
			return this;
		}
	});
	
	
	//给一个短变量名
	window.SchoolSelect = window.byyschool = SchoolSelect;
})(window);

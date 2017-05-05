/****
jQuery 插件，自己开发。
***/

(function($){
	//简化版，只查询文本框
	$.fn.getInputValuesSimplified = function(){
		var form = $(this);
		var obj = {};
		var inputItems = form.find('input');
		for(var i=0;i<inputItems.length;i++){
			var tempObj = inputItems[i];
			var type = tempObj.type;
			var name = tempObj.name;
			if(type == 'text' ||  type == 'hidden'){
				if(name&&name!=""&&obj[name]!=undefined){
					if(typeof(obj[name])=='object'){
						var arr  = obj[name];
						arr.push(tempObj.value);
						obj[name] = arr;
					}else{
						var arr = [];
						arr.push(obj[name]);
						arr.push(tempObj.value);
						obj[name] = arr;
					}
				}else{
					obj[name] = tempObj.value;
				}
				
			}
		}
		return obj;
	};
	//getValues  优化高速版
	$.fn.getValuesFast = function(){
		var form = $(this);
		var obj = {};
		var inputItems = form.find('input');
		var selectItems = form.find('select');
		var textItems = form.find('textarea');
		for(var i=0;i<inputItems.length;i++){
			var tempObj = inputItems[i];
			//判断类型--text,button,checkbox,radio,hidden,file,
			var type = tempObj.type;
			var name = tempObj.name;
			if(type == 'text' ||  type == 'hidden' || type=='password'){
				if(name&&name!=""&&obj[name]!=undefined){
					if(typeof(obj[name])=='object'){
						var arr  = obj[name];
						arr.push(tempObj.value);
						obj[name] = arr;
					}else{
						var arr = [];
						arr.push(obj[name]);
						arr.push(tempObj.value);
						obj[name] = arr;
					}
				}else{
					obj[name] = tempObj.value;
				}
				
			}else if(type == 'checkbox'){
				if($(tempObj).prop('checked') == true){
					var t = obj[name] == undefined ? new Array() : obj[name].split(',');
					t.push(tempObj.value);
					var str = t.join(',');
					obj[name] = str;
				}
			}else if(type == 'radio'){
				if(tempObj.checked == true){
					obj[name] = tempObj.value;
				}
			}else if(type == 'file'){
				obj[name] = tempObj.value;//得到上传文件路径
			}
		}
		for(var i=0;i<selectItems.length;i++){
			//yuansheng JS select
			var selObj = selectItems[i];
			var name = selObj.name;
//			var v = selObj.options[selObj.selectedIndex] ? selObj.options[selObj.selectedIndex].value : '';
			var v = $(selObj).val();
			obj[name] = v ;
		}
		for(var i=0;i<textItems.length;i++){
			var textObj = textItems[i];
			var name = textObj.name;
			
			if(textObj.type == 'textarea'){
				//obj[name] = textObj.value;
				if(form.find('textarea[name="'+name+'"]').length > 1){
					if(obj[name]){
						var arr  = obj[name];
						arr.push(textObj.value);
						obj[name] = arr;
					}else{
						var arr = [];
						arr.push(textObj.value);
						obj[name] = arr;
					}
				}else{
					obj[name] = textObj.value;
				}
				
			}
		}
		
		
		return obj;
		
	};
	/****
	 * Form表单异步获取key=value 生成对象并返回
	 * 使用方法:
	 * $(selector).getValues();
	 */
	$.fn.getValues = function(){
		var form = $(this);
		var obj = {};
		var inputItems = form.find('input');
		var selectItems = form.find('select');
		var textItems = form.find('textarea');
		for(var i=0;i<inputItems.length;i++){
			var tempObj = inputItems[i];
			//判断类型--text,button,checkbox,radio,hidden,file,
			var type = tempObj.type;
			var name = tempObj.name;
			if(type == 'text' ||  type == 'hidden' || type=='password'){
				if(form.find('input[name="'+name+'"]').length > 1){
					if(obj[name]){
						var arr  = obj[name];
						arr.push(tempObj.value);
						obj[name] = arr;
					}else{
						var arr = [];
						arr.push(tempObj.value);
						obj[name] = arr;
					}
				}else{
					obj[name] = tempObj.value;
				}
				
			}else if(type == 'checkbox'){
				if($(tempObj).prop('checked') == true){
					var t = obj[name] == undefined ? new Array() : obj[name].split(',');
					t.push(tempObj.value);
					var str = t.join(',');
					obj[name] = str;
				}
			}else if(type == 'radio'){
				if(tempObj.checked == true){
					obj[name] = tempObj.value;
				}
			}else if(type == 'file'){
				obj[name] = tempObj.value;//得到上传文件路径
			}
		}
		for(var i=0;i<selectItems.length;i++){
			//yuansheng JS select
			var selObj = selectItems[i];
			var name = selObj.name;
//			var v = selObj.options[selObj.selectedIndex] ? selObj.options[selObj.selectedIndex].value : '';
			var v = $(selObj).val();
			obj[name] = v ;
		}
		for(var i=0;i<textItems.length;i++){
			var textObj = textItems[i];
			var name = textObj.name;
			
			if(textObj.type == 'textarea'){
				//obj[name] = textObj.value;
				if(form.find('textarea[name="'+name+'"]').length > 1){
					if(obj[name]){
						var arr  = obj[name];
						arr.push(textObj.value);
						obj[name] = arr;
					}else{
						var arr = [];
						arr.push(textObj.value);
						obj[name] = arr;
					}
				}else{
					obj[name] = textObj.value;
				}
				
			}
		}
		
		
		return obj;
		
	};
	/***
	 * 生成一个遮罩层
	 * $('body').mask('正在加载中,请稍后...');
	 */
	$.fn.mask = function(msg){
		var $bgc = $("<div class=\"dmask\" style=\"display:block\"></div>");
		$bgc.appendTo($(this));
		var msg=$("<div class=\"dmask-msg\"  style=\"display:block;width:300px;z-Index:9999;\"></div>").html(msg?msg:'正在处理,请等待......').appendTo($(this));
		$bgc.css('height',$(this).height());
		msg.css('top',250);
		msg.css('margin-left','-150px');
		//1.判断当前是否在frame中，如果在frame中则获取frame的宽度，否则是this的宽度
		var frameObj = window.frameElement;
		var w= $(frameObj).css('width');
		if($(frameObj).attr('name')!='index-frame-target'&&w){
			w = parseInt(w.replace('px',''),10);
			msg.css("left",(w)/2);
		}else{
			var wd = $(this).width() == 0 ? screen.availWidth : $(this).width();
			msg.css("left",(wd)/2);
		}
		
	};
	/****
	 * 移除遮罩层
	 * $('body').removeMask();
	 */
	$.fn.removeMask = function(){
		$(this).children("div.dmask-msg").remove();
		$(this).children("div.dmask").remove();
	};
	//得到后台传递的数据后，进行赋值
	/****
	 * 编辑、查看页面的加载数据，可能不全，开发补充。
	 * $(selector).loadData(rows,cfg);
	 * rows : 为后台向前端返回的记录
	 * cfg : 为表单中的属性属于哪个对象，如果没有特殊可传空。
	 */
	$.fn.loadData = function  (data,cfg) {
//		cfg = cfg || {};
//		for(var n in data){
//			var value = data[n];
//			//循环数据中得到为对象，判断在cfg中是否包含有该对象中的数据
//			if(typeof value =='object'){
//				for(var c in cfg){
//					if(cfg[c] == n){
//						$(this).setValue(c,value[c]);
//					}
//				}
//			}else{
//				$(this).setValue(n,value);
//			}
//		}
		var $form = $(this);
		var $eles = $form.find('div[name],input[name]:not([type=button]),select[name],textarea[name]');
		$.each($eles,function(){
			var $ele = $(this);
			var name = $ele.attr('name');
			if(null  != name && undefined != name && ''!=$.trim(name)){
				//name值存在.
				//1.查看该name在data中是否存在值
				//1.查看name是否有点
				//首先查看该name在对象中是否存在
				var tempobj = data[name];
				if(undefined != tempobj){
					$form.setValue(name,tempobj);
				}else{
					var names = name.split('.');//attachment[0].stage
					var tempobj = data;
					for(var i=0;i<names.length;i++){
						var nameattr = names[i];
						if(null  != nameattr && ''!= nameattr){
							var tempobj2 = null;
							if(nameattr.indexOf(']')>-1){
								//是数组.
								var index = nameattr.substring(nameattr.indexOf('[')+1,nameattr.indexOf(']'));
								nameattr = nameattr.substring(0,nameattr.indexOf('['));
								tempobj2 =tempobj[nameattr];
								if(tempobj2 instanceof Array){
									tempobj2 = tempobj2[index];
								}
							}else{
								tempobj2 = tempobj[nameattr];
							}
							//判断当前是第几个了..，同时判断一下是不是最后一个了.
							if(null != tempobj2 && undefined != tempobj2){
								tempobj = tempobj2;
							}else{
								if(undefined == tempobj2){
									tempobj = tempobj2;
								}else{
									tempobj = '';
								}
								break;
							}
						}else{
							//如果是空的怎么破
						}
					}
					if(undefined != tempobj){
						$form.setValue(name,tempobj);	
					}
				}
				
			}
		});
	};
	/****
	 * 向表单中赋值,根据NAME来进行赋值
	 * @param {String}	n name:<input type="text" <b>name</b>="abc" />
	 * @param {String} v value 值
	 */
	$.fn.setValue = function(n,v){
		var form = $(this);
		var target = form.find('[name="'+n+'"]');
		if(target.length >1 ){
			//如果获得多个name相同的，那么就是checkbox/radio,然后判断value是数组还是str
			for(var i=0;i<target.length;i++){
				var temp = target[i];
				var type = temp.type;
				if(type == 'radio'){
					if(temp.value == v || temp.value == (''+v)){
						temp.checked = true;
						$(temp).prop('checked',true);
					}	
				}else if(type == 'checkbox'){
					if(v instanceof Array || v==true|| v==false){
						if(v==true){
							v = 'true';
							v = v.split(',');
						}else if(v == false){
							v = 'false';
							v = v.split(',');
						}
					}else{
						v = v.split(',');
					}
					if(v.isContains(temp.value)){
						temp.checked = true;
						$(temp).prop('checked',true);
					}	
				}else{
					throw new Error('form 里有重名:'+n)
				}
			}
		}else if(target.length == 1){
			var temp = target[0];
			if(temp != undefined){
				var type = temp.nodeName;
				if(type == 'INPUT'){
					var inputtype = temp.type;
					if(inputtype == 'radio'){
						if(temp.value == v || temp.value == ''+v){
							temp.checked = true;
							$(temp).prop('checked',true);
						}	
					}else if(inputtype == 'checkbox'){
						if(v instanceof Array || v==true|| v==false){
							if(v==true){
								v = 'true';
								v = v.split(',');
							}else if(v == false){
								v = 'false';
								v = v.split(',');
							}
						}else{
							v = v.split(',');
						}
						if(v.isContains(temp.value)){
							temp.checked = true;
							$(temp).prop('checked',true);
						};	
					}
					if(typeof v=='boolean'){
						v = ''+v;
					}
					$(temp).val(v);	
				}else if(type=='SELECT'){
					$(temp).val(v);
				}else if( type == 'TEXTAREA'){
					$(temp).html(v);
				}else if(type == 'IMAGE'){
					$(temp).attr('src',v);
				}else{
					$(temp).html(v);
				}
				
			}
			
		}
	};
	//将表单修改为查看页面样式的样子
	$.fn.setView = function(specDiv){
		var $form = $(this);
		var $ipts = $form.find('input:not([type=hidden][name])');
		var $sels = $form.find('select');
		var $text = $form.find('textarea');
		$ipts.each(function(){
			var v = $(this).val();
			var name = $(this).attr('name');
			var type = $(this).attr('type');
			if(null != specDiv && undefined != specDiv && null != $(this).findParent(specDiv)){
				//特殊区域，不予处理
				$(this).prop('disabled','true');
			}else{
				//当前为radio,那么相同name的需要放在一起
				if(type == 'radio'){
//					$(this).attr('disabled','true');
					//修改为直接显示值的样式
					var checkedFlag = $(this).prop('checked');
					if(checkedFlag == true){
						var norp = $(this)[0].nextSibling;
						var norp2 = $(this)[0].prevSibling;
						var radioValue = '';
						if(undefined != norp && norp.nodeType==3){
							radioValue = norp.nodeValue.trim();
						}else if(undefined != norp2 && norp2.nodeType == 3){
							radioValue = norp2.nodeValue.trim();
						}else{
							radioValue = $(this).val();
						}
						if($(this).parent()[0].nodeName != 'DIV'){
							$(this).parent().parent().html('<div class="viewipt" name='+name+'>'+radioValue+'</div>');
						}else{
							$(this).parent().html('<div class="viewipt" name='+name+'>'+radioValue+'</div>');
						}
					}
				//复选框
				}else if(type == 'checkbox'){
//					$(this).attr('disabled','true');
					//修改为直接显示内容
					var checkedFlag = $(this).prop('checked');
					if(checkedFlag == true){
						var norp = $(this)[0].nextSibling;
						var norp2 = $(this)[0].prevSibling;
						var checkedValue = '';
						if(undefined != norp && norp.nodeType==3){
							checkedValue = norp.nodeValue.trim();
						}else if(undefined != norp2 && norp2.nodeType == 3){
							checkedValue = norp2.nodeValue.trim();
						}else{
							checkedValue = $(this).val();
						}
						var checkedTarget = $(this).parent()[0].nodeName != 'DIV' ? $(this).parent().parent() : $(this).parent();
						if($('div.viewipt[name="'+name+'"]').length > 0){
							var checkedTarget2 = $('div.viewipt[name="'+name+'"]');
							checkedTarget2.html(checkedTarget2.html()+','+checkedValue);
						}else{
							checkedTarget.html('<div class="viewipt" name='+name+'>'+checkedValue+'</div>');
						}
					}
				}else{
					if(specDiv){
						if($(this).parents(specDiv).length > 0){
							$(this).prop('disabled','true');
						}else{
							$(this).parent().html('<div class="viewipt" name='+name+'>'+v+'</div>');
						}
					}else{
						$(this).parent().html('<div class="viewipt" name='+name+'>'+v+'</div>');
					}
					
				}
			}
			
		});
		//下拉框
		$sels.each(function(){
			var tempobj = $(this).get(0);
			var name = '',value='',text ='';
			name = $(this).attr('name');
			value = $(this).val();
			if(null != specDiv && undefined != specDiv && null != $(this).findParent(specDiv)){
				$(this).prop('disabled','true');
			}else{
				if(''!=value){
					text = '';
					var selectArr = $(this).find('option:selected');
					if(selectArr.length == 1){
						text = selectArr.text();
					}else{
						selectArr.each(function(){
							var t = $(this);
							text +=t.text()+'、';
						});
					}
				}
				$(this).parent().html('<div class="viewipt" name='+name+'>'+text+'</div>');
			}
		});
		//文本域
		$text.each(function(){
			var text = $(this).val();
			var name = $(this).attr('name');
			$(this).parent().html('<div class="viewchkbx" name='+name+'>'+text+'</div>');
		});
		
		$form.find('.inputtitle').each(function(){
			$(this).parent().prev().remove();
			$(this).parent().remove();
		});
		$form.find('.requirecls').each(function(){$(this).remove();});
		$('.submitbtn .submit').remove();
	};
	
	//省市县三级联动
	$.fn.linkpage = function(area){
		var flag = true;
		var cityFlag = true;
		var $div = $(this);
		var $pro = $div.find('select:eq(0)');
		var $city = $div.find('select:eq(1)');
		var $district = $div.find('select:eq(2)');
		//绑定事件
//		$pro.delegate('','change',prochange);
//		$city.delegate('','change',citychange);
		//省份变化后，更新下拉菜单内容
		function prochange (){
			var v = $pro.val();
			//清空后面两个城市和县区的数据
			citymsg();
			dismsg();
			if(v == ''){
			}else{
				loadInfo($city,v);
			}
		};
		function citychange (){
			var v = $city.val();
			//清空后面两个城市和县区的数据.
			dismsg();
			if(v == ''){
			}else{
				loadInfo($district,v);
			}
		};
		var appendData = function(ele,data){
			var appendObj = $(ele).next().find('dl');
			$.each(data,function(){
				var id = this.id,name = this.name;
				$(ele).append('<option value="'+id+'">'+name+'</option>');
				
			});
			//因为页面加载时，已经完成了下拉框的加载，因此这里要重新初始化一下地址下拉框
			byy(ele).select();
			//如果有参数的话，执行函数体
			// 
			if(area){
				var level = area.levelType;//area.parent ? (area.parent.parent ? (area.parent.parent.parent ? 3 : 2) : 1) : 0;
				var proid = '',cityid = '',disid = '';
				switch(level){
					case "1" : 
						proid = area.id;
						break;
					case "2" : 
						proid = area.id.substring(0,area.id.length-4) +'0000';
						cityid = area.id;
						break;
					case "3" : 
						disid = area.id;
						cityid = area.id.substring(0,area.id.length-2) +'00';
						proid = area.id.substring(0,area.id.length-4) +'0000';
						//proid = area.parent.parent.id;cityid = area.parent.id;disid = area.id;
						break;
					default : 
						break;
				}
				//1.赋值并触发省事件
				if(area){
					if(ele.selector.indexOf('0') > 0){
						//赋值
						$(ele).next('div.byy-form-select').find('dl>dd').each(function(){
							var id = $(this).attr('byy-value');
							if(id == proid){
								$(this).click();
								return;
							}
						});
						prochange();
					}else if(ele.selector.indexOf('1')>0){
						//赋值
						$(ele).next('div.byy-form-select').find('dl>dd').each(function(){
							var id = $(this).attr('byy-value');
							if(id == cityid){
								$(this).click();
								return;
							}
						});
						ele.val(cityid);
						citychange();
					}else if(ele.selector.indexOf('2')>0){
						//赋值
						$(ele).next('div.byy-form-select').find('dl>dd').each(function(){
							var id = $(this).attr('byy-value');
							if(id == disid){
								$(this).click();
								return;
							}
						});
						
					}
				}
			}
		};
		//加载数据
		var loadInfo = function(ele,id){
			$.ajax({
				url : base+'/area_getCommonList.do',
				type : 'POST',
				data : {
					id : id,
					asyn : "true",
					available : true
				},
				dataType : 'json',
				success : function(res){
					var resobj = byy.json(res);
					//循环进行增加option
					appendData(ele,resobj);
					var attrName = $(ele).attr('name');//当前加载元素
					if(attrName == 'areadiv-city'){
						if(cityFlag == true || cityFlag == 'true'){
							//初始化城市变更、点击事件,同时要防止重复绑定
							$city.next('div.byy-form-select').find('dl').delegate('dd','click',function(){
								citychange();
							});
							cityFlag = false;
						}
					}
					//如果flag=true，则初始化点击、绑定事件，否则不初始化(省点击事件初始化)
					if(flag == true || flag == 'true'){
						//下拉框点击事件，如果点击同一地址，则不加载子节点，否则加载子节点
						$pro.next('div.byy-form-select').delegate('dd','click',function(){
							prochange();
						});
						flag = false;
					}
				}
			});
		};
		var promsg = function(){
			//首先进行清空
			$pro.html('');
			var promsg = $pro.attr('msg');
			promsg = promsg ? promsg : '请选择省份';
			$pro.append('<option value="">'+promsg+'</option>');
		};
		var citymsg = function(){
			$city.html('');
			var citymsg = $city.attr('msg');
			citymsg = citymsg ? citymsg : '请选择市';
			$city.append('<option value="">'+citymsg+'</option>');
			cityFlag = true;//清空后，再次进行城市点击事件的绑定
		};
		var dismsg = function(){
			$district.html('');
			var dismsg = $district.attr('msg');
			dismsg = dismsg ? dismsg : '请选择区(县)';
			$district.append('<option value="">'+dismsg+'</option>');
			byy($district).select();
		};
		var msgInit = function(){
			promsg();
			citymsg();
			dismsg();
		};
		//执行初始化
		msgInit();
		loadInfo($pro,null);
	};
	//自动生成html
	$.fn.createlink = function(params){
		params = params || {};
		params.width = params.width || '175';
		var html = [
		'<select name="province" style="float:left;min-width:'+params.width+'px;" msg="请选择省份" >',
		'</select>',
		'<span class="add-on" style="line-height:30px;float:left;margin-right:10px;">省</span>',
		'<select name="city" style="float:left;min-width:'+params.width+'px;" msg="请选择市">',
		'</select>',
		'<span class="add-on" style="float:left;line-height:30px;margin-right:10px;">市</span>',
		'<select name="district" style="float:left;min-width:'+params.width+'px;10px;" msg="请选择区(县)" >',
		'</select>',
		'<span class="add-on" style="float:left;line-height:30px;margin-right:10px;">区(县)</span>'
		];
		$(this).html(html.join(''));
		$(this).linkpage();
	};
	//获得地域三级联动最终选中的那个地域ID
	$.fn.getAreaId = function(){
		var areaid = '';
		var pro = $(this).find('select:eq(0)');
		var city = $(this).find('select:eq(1)');
		var dis = $(this).find('select:eq(2)');
		if(dis.val() == ''){
			if(city.val() == ''){
				if(pro.val() == ''){
					areaid = '';
				}else{
					areaid = pro.val();
				}				
			}else{
				areaid = city.val();
			}
		}else{
			areaid = dis.val();
		}
		return areaid;
	};
	/***
	 * 通过后台处理数据，到前端进行下拉框的初始化或者赋值
	 * params : {
	 * 	url : 请求后台的地址
	 * 	data : 请求后台地址同时附带的参数
	 * 	value : 前端渲染的value在对象中的name值
	 * 	text : 前端渲染的option的text在对象中的name值
	 * 	selvalue : 需要进行选中的值
	 * }
	 */
	$.fn.initSelect = function(params){
		var $sel = $(this);
		if(!params){
			throw Error('请传递params参数');				
		}
		$.ajax({
			url : params.url,
			type : 'POST',
			data : params.data,
			success : function(res){
				var resobj = common.parseJson('(' + res + ')');
				//这是一个数组？？或者一个对象，需要得到其中的
				var arr = [];
				if(typeof resobj == 'Array'){
					arr = resobj;
				}else{
					arr = resobj.rows;
				}
				//进行下拉框的数据加载。
				var init = function(arr){
					$sel.html('').append('<option value="">请选择...</option>');
					$.each(arr,function(){
						var v='',t='',checked='';
						v = this[params.value];
						t = this[params.text];
						if(params.selvalue){
							checked = v == params.selvalue ? ' selected="true" ' :'';	
						}
						var optstr = '<option value="'+v+'" '+checked+'>'+t+'</option>';
						$sel.append(optstr);
					});
					
				};
				init(arr);
			}
		});
	};
	/***
	 * 查找父级元素中为selector的元素，返回该元素，不包含中间所查到的不符合 selector的元素，且返回第一个匹配的
	 * 思路：无限循环，直到body 或 html 结束，或者找到匹配
	 * @return $object || null
	 */
	$.fn.findParent = function(selector){
		var temp = $(this)[0];
		if(null != temp){
			while(null != temp && temp.nodeType != 9 && temp.nodeType == 1 ){
				if($(temp).is(selector)){
					return $(temp);
				}else{
					temp = temp['parentNode'];
				}
			}
		}
		return null;
	};
	$.fn.extend({
		//切换cls1 和 cls2 的class ，例如：<a class="checked"></a> <a class="nochecked"></a> ,对a标签切换 checked和nochecked两个样式。如果有forceCls ，则不论是两个中的哪个都强制替换为forceCls
		toggleChecked : function(cls1,cls2,forceCls){
			var cls = ($(this).attr('class') || '' ).split(' ');
			var newcls = cls.map(function(temp){
				if(temp == cls1){
					return forceCls || cls2;
				}else if(temp == cls2){
					return forceCls || cls1;
				}else{
					return temp;
				}
			});
			$(this).attr('class',newcls.join(' '));
		}
	});
	/***
	 * 将日期字符串转化为日期对象
	 * @param {String} str {Format} yyyy-MM-dd
	 * @return {Date} date
	 * @author lee
	 * @version 1.0
	 * @date 2015年2月26日 16:35:40
	 */
	$.parseDateString = function(str){
		return new Date(parseInt(str.split('-')[0]),parseInt(str.split('-')[1])-1,parseInt(str.split('-')[2]));
	};
	$.extend({
		/****
		 * 阻止非数字输入:允许输入的有：数字、小数点、回车键、退格键,TAB
		 * @param {Boolean} dot 是否允许小数点的输入，默认允许，dot==false 时不允许
		 * @return {Boolean} flag
		 * @author lee
		 */
		checkDot :function(dot){
			var code = event.keyCode;
			var narrow = [37,38,39,40];//上下左右
			var gongneng = [8,9,13,46];//退格，回车，删除
			var num1 = [48,49,50,51,52,53,54,55,56,57];//大键盘数字
			var num2 = [96,97,98,99,100,101,102,103,104,105];//小键盘数字
			var dotArr = [110,190];//小数点
			if(event.shiftKey == 1){
				event.preventDefault();
			} 
		 	if(gongneng.isContains(code) || narrow.isContains(code)){
		 		return true;
		 	}
		 	if(num1.isContains(code) || num2.isContains(code)){
		 		return true;
		 	}
		 	if(dotArr.isContains(code)){
		 		if(dot){
	 				var target = event.srcElement;
			 		var v = target.value;
			 		if(v.indexOf('.') > -1){
			 			event.preventDefault();
			 		}else{
			 			if(v == ''){
			 				v = '0';
			 				target.value = v;
			 			}
			 			return true;
			 		}		
		 		}else{
		 			event.preventDefault();
		 		}
		 	}
		 	event.preventDefault();
		},
		
		dateUtil : {
			year : new Date().getFullYear(),
			month : new Date().getMonth()+1,
			day : new Date().getDate(),
			time : new Date().getTime(),
			/**
			 * 得到下一天的日期对象
	 		 * @param {Date|String} d 日期对象或‘2012-02-22’
	 		 * @param {Number} i 第几天，如果为0，则为当天，如果1为明天，以此类推。
	 		 * @return {Date|String} m
	 		 * @author lee
	 		 * @version 1.0
	 		 * @date 2015年2月26日 16:35:40
			 */
			getNextDay : function(d,i){
				i=i?i:1;
				if(typeof d == 'string'){
					var m=this.parseDateString(d);
					var temp = new Date(m.getTime()+(i*24*60*60*1000));
					return this.parseDate(temp);
				}else{
					var l = d.getTime()+(m*24*60*60*1000);
					var temp = new Date(d.getTime()+(i*24*60*60*1000));
					return temp;
				}
			},
			/***
			 * 将日期字符串转化为日期对象
			 * @param {String} str {Format} yyyy-MM-dd
			 * @return {Date} date
			 * @author lee
	 		 * @version 1.0
	 		 * @date 2015年2月26日 16:35:40
			 */
			parseDateString : function(str){
				return new Date(parseInt(str.split('-')[0]),parseInt(str.split('-')[1])-1,parseInt(str.split('-')[2]));
			},
			/**
			 * 将日期对象转化为日期字符串 yyyy-MM-dd
			 * @param {Date} d
			 * @return {String} str
			 * @author lee
	 		 * @version 1.0
	 		 * @date 2015年2月26日 16:35:40
			 */
			parseDate : function(d){
				var y = d.getFullYear(),m=d.getMonth(),a=d.getDate();
				m = parseInt(m,10)+1 <10 ? '0'+(parseInt(m,10)+1) : parseInt(m,10)+1;
				a = parseInt(a,10) <10 ? '0'+(parseInt(a,10)) : parseInt(a,10);
				return y+'-'+m+'-'+a;
			},
			/**
			 * 将日期对象转化为日期字符串 yyyy-MM-dd hh:mm:ss
			 * @param {Date} d
			 * @return {String} str
			 * @author lee
	 		 * @version 1.0
	 		 * @date 2015年2月26日 16:35:40
			 */
			parseDate2 : function(d){
				var y = d.getFullYear(),m=d.getMonth(),a=d.getDate(),h = d.getHours(),m2 = d.getMinutes(),s = d.getSeconds();
				m = parseInt(m,10)+1 <10 ? '0'+(parseInt(m)+1) : parseInt(m,10)+1;
				a = parseInt(a,10) <10 ? '0'+(parseInt(a,10)) : parseInt(a,10);
				h = parseInt(h,10) < 10 ? '0'+(parseInt(h,10)) : parseInt(h,10);
				m2 = parseInt(m2,10) < 10 ? '0'+(parseInt(m2,10)) : parseInt(m2,10);
				s = parseInt(s,10) < 10 ? '0'+(parseInt(s,10)) : parseInt(s,10);
				return y+'-'+m+'-'+a+' '+h+':'+m2+':'+s;
			},
			/***
			 * 得到传入的日期和今天相差的天数 
	 		 * @param {Date} d
	 		 * @return {Object} num 
	 		 * @author lee
	 		 * @version 1.0
	 		 * @date 2015年2月26日 16:35:40
			 */
			getDifferDays : function(d){
				var d1 = this.parseDate(d);
				var d2 = this.parseDate(new Date());
				return (this.parseDateString(d1).getTime()-this.parseDateString(d2))/1000/60/60/24;
				//return (d.getTime()-new Date().getTime())/1000/60/60/24;
			},
			/***
			 * 判断日期对象的年度是否是闰年
			 * @param {Date} d
			 * @return {Boolean} b
			 * @author lee
			 */
			isLeapYear : function(d){
				var year = d.getFullYear();
	    		return !!((year & 3) == 0 && (year % 100 || (year % 400 == 0 && year)));
			},
			/****
			 * 得到日期对象的当前月份的有多少天
			 * @param {Date} d
			 * @return {Object} num
			 * @author lee
			 */
			getDaysInMonth : function(d){
				var daysInMonth = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
				var m = d.getMonth();
		        return m == 1 && this.isLeapYear(d) ? 29 : daysInMonth[m];
			},
			/****
			 * 传递一个时间字符串，返回周几(汉字)
			 * @param {String} sj yyyy-MM-dd
			 * @return {String} 
			 * @author lee
			 */
			getWeek : function(sj){
				var weeks = ['日','一','二','三','四','五','六','日'];
				var d;
				if(typeof sj == 'string'){
					d = this.parseDateString(sj);
				}else{d = sj;}
				return weeks[d.getDay()];
			},
			
			/*****
			 * 得到某一天的一月后的日期,如果今天是2015-01-31，下个月为2015-02-28
			 * @param {Date} d 日期对象
			 * @param {Number} i 默认为1,是为数目，表示几个月后打哦日期
			 * @return {Date} d i月后的日期对象
			 */
			getAfterMonth : function(d,i){
				
				dd = typeof d == 'string' ? this.parseDateString(d) : d;
				i=i!=undefined && $.dtqy.isNumber(i)?i:1;
				var days = [31,28,31,30,31,30,31,31,30,31,30,31,31];
				var m = dd.getMonth(),a = dd.getDate();
				var mm = (m+i)%12;
				if(this.isLeapYear(dd) && mm==1 && a>29){
					var temp = new Date(dd.getFullYear(),mm,days[mm]);
					return typeof d == 'string' ? this.parseDate(temp) : temp;
				}else{
					if(a > days[mm]){
						var temp = new Date(dd.getFullYear(),mm,days[mm]);
						return typeof d == 'string' ? this.parseDate(temp) : temp;
					}else{
						var temp =new Date(dd.getFullYear(),mm,a); 
						return typeof d == 'string' ? this.parseDate(temp) : temp;
					}
				}
			}
		}
	});
})(jQuery);

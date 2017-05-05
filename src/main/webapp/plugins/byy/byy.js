/****
 * @title 博育云前端框架
 * @author lixun
 * @version 1.0.0 版本初建，并修复部分BUG。
 * @version 1.0.1 去掉cookie操作相关的函数，系统安全性上的考虑。
 * @version 1.0.2 增加 byy('form').reset(obj) 重置，obj可为空，同时setValues 增加dom上的formatter过滤（简单过滤）
 * @version 1.0.3 增加fixJson函数，处理$ref 对应后台的json序列问题，可以通过 byy.json(str,flag) 来控制，默认不转化。
 * @version 1.0.4 集成jquery.validator 插件，扩展byy('selector').validate() 和 byy('selector').valid() 函数供正常使用，若不足，后续添加。
 * @version 1.0.5 修复getSearch addUrlParams 函数的中文解析问题 
 * @version 1.0.6 修复多行文本域在表单提交时换行符号的替换以及换回和显示。
 * @version 1.0.7 修复tab控件的逻辑问题（0.添加后应该立刻显示；1.重复点击问题）
 ***/

(function( global , factory ){

	factory(global);

})(typeof window !== "undefined" ? window : this,function( win , undefined ){
	var 
		version = '1.0',
		location = win.location,
		isOpera = typeof opera !== 'undefined' && opera.toString() === '[object Opera]',
		byy = function( selector , context ){
			return new byy.fn.init( selector , context );
		},
		error = function( msg ){
			win.console && console.error && console.error('byyjs hint: ' + msg);
		},
		doc = win.document, 
		getPath = function(){
			var js = doc.scripts, tnode = js[js.length - 1],jsPath = tnode.querySelector ? tnode.src : tnode.getAttribute('src');
  			return jsPath.substring(0, jsPath.lastIndexOf('/') + 1);
		}(),
		modules = {
			page : 'lib/page'//分页
			,jquery : 'lib/jquery'
			,win : 'lib/win'
			,table : 'lib/table'
			,upload : 'lib/webuploader_noimage'
			,'upload-image' : 'lib/webuploader'
			,validator : 'lib/jquery_validator'
			,inputsearch : 'lib/inputsearch'
		};

	if ( !Array.prototype.forEach ) {
		Array.prototype.forEach = function forEach( callback, thisArg ) {
			var T, k;
			if ( this == null ) {
				throw new TypeError( "this is null or not defined" );
			}
			var O = Object(this);
			var len = O.length >>> 0; 
			if ( typeof callback !== "function" ) {
				throw new TypeError( callback + " is not a function" );
			}
			if ( arguments.length > 1 ) {
				T = thisArg;
			}
			k = 0;
			while( k < len ) {
				var kValue;
				if ( k in O ) {
					kValue = O[ k ];
					callback.call( T, kValue, k, O );
				}
				k++;
			}
		};
	}
	if (!Array.prototype.map) {
	    Array.prototype.map = function(callback, thisArg) {
	        var T, A, k;
	        if (this == null) {
	            throw new TypeError(" this is null or not defined");
	        }

	        var O = Object(this);
	        var len = O.length >>> 0;

	        if (typeof callback !== "function") {
	            throw new TypeError(callback + " is not a function");
	        }

	        if (thisArg) {
	            T = thisArg;
	        }

	        A = new Array(len);

	        k = 0;
	        while(k < len) {

	            var kValue, mappedValue;
	            if (k in O) {
	                kValue = O[ k ];
	                mappedValue = callback.call(T, kValue, k, O);
	                A[ k ] = mappedValue;
	            }
	            k++;
	        }
	        return A;
	    };
	}
	

	byy.fn = byy.prototype = {
		byy : version,
		constructor: byy,
		init : function( selector , context ){
			//如果selector 是jquery对象的话
			if(typeof selector === 'string'){
				this.selector= selector;
				this.$ele = $(selector, context);
				this.length = this.$ele.length;
				this.$ = this.jquery = $;
			}else if( selector.nodeType ){
				this.selector = $(selector).selector;
				this.$ele = $(selector);
				this.length = 1;
			}else if(selector.selector !== undefined){
				this.selector= selector.selector;
				this.$ele = selector;
				this.length = selector.length;
			}
			return this;
		}
	};


	byy.fn.init.prototype = byy.fn;
	//config
	var config = byy.cache = {};
	config.modules = {}; //记录模块物理路径
	config.status = {}; //记录模块加载状态
	config.timeout = 10; //符合规范的模块请求最长等待秒数
	config.event = {}; //记录模块自定义事件

	

	/*简单的继承,针对纯对象*/
	byy.extend = byy.fn.extend = function(){
		var target = arguments[0]||{}, i = 1,length = arguments.length,clone;
		if(i == length){
			clone = target;
			target = this;
		}else{
			clone = arguments[i];
		}
		for(var k in clone){
			target[k] = clone[k];
		}
		return target;
	};

	byy.fn.extend({
		getStyle : function( node , name ){
			var style = node.currentStyle ? node.currentStyle : win.getComputedStyle(node, null);
  			return style[style.getPropertyValue ? 'getPropertyValue' : 'getAttribute'](name);
		},
		config : function( options ){
			options = options || {};
			for(var key in options){
				config[key] = options[key];
			}
			return this;
		},
		
	});

	byy.extend({
		error : error
		,test : function(){
			console.log('enter byy tool method');
		}
		,device : function(){
			var agent = navigator.userAgent.toLowerCase();
			//获取版本号
			var getVersion = function(label){
				var exp = new RegExp(label + '/([^\\s\\_\\-]+)');
				label = (agent.match(exp)||[])[1];
				return label || false;
			};

			var result = {
				os: function(){ //底层操作系统
				  if(/windows/.test(agent)){
				    return 'windows';
				  } else if(/linux/.test(agent)){
				    return 'linux';
				  } else if(/iphone|ipod|ipad|ios/.test(agent)){
				    return 'ios';
				  }
				}()
				,ie: function(){ //ie版本
				  return (!!win.ActiveXObject || "ActiveXObject" in win) ? (
				    (agent.match(/msie\s(\d+)/) || [])[1] || '11' //由于ie11并没有msie的标识
				  ) : false;
				}()
				,weixin: getVersion('micromessenger')  //是否微信
			}
			return result;
		}
		,modules : function(){
			var clone = {};
			for(var k in modules){
				clone[k] = modules[k];
			}
			return clone;
		}()
		,define : function( deeps , callback ){
			var thiz = this,
				type = typeof deeps === 'function',
				mods = function(){
					typeof callback === 'function' && callback(function( app , exports){
						byy[app] = exports;
						config.status[app] = true;
					});
					return this;
				};
			if(type){
				callback = deeps;
				deeps = [];
			}
			if(byy['all']){
				return mods.call(thiz);
			}
			thiz.require(deeps , mods);
			return thiz;
		}
		,require : function( apps , callback ,exports ){
			var that = this, dir = config.dir = config.dir ? config.dir : getPath;
			var head = doc.getElementsByTagName('head')[0];
			apps = typeof apps === 'string' ? [apps] : apps;

			//如果页面已经存在jQuery1.7+库且所定义的模块依赖jQuery，则不加载内部jquery模块
			if(window.jQuery && jQuery.fn.on){
				that.each(apps, function(index, item){
				  if(item === 'jquery'){
				    apps.splice(index, 1);
				  }
				});
				
			}

			var item = apps[0], timeout = 0;
			exports = exports || [];

			//静态资源host
			config.host = config.host || (dir.match(/\/\/([\s\S]+?)\//)||['//'+ location.host +'/'])[0];

			if(apps.length === 0 || (byy['all'] && modules[item])){
				return typeof callback === 'function' && callback.apply(byy, exports), that;
			}

			//加载完毕
			function onScriptLoad(e, url){
				var readyRegExp = navigator.platform === 'PLaySTATION 3' ? /^complete$/ : /^(complete|loaded)$/;
				if (e.type === 'load' || (readyRegExp.test((e.currentTarget || e.srcElement).readyState))) {
				  config.modules[item] = url;
				  head.removeChild(node);
				  (function poll() {
				    if(++timeout > config.timeout * 1000 / 4){
				      return error(item + ' is not a valid module');
				    };
				    config.status[item] ? onCallback() : setTimeout(poll, 4);
				  }());
				}
			}

			//加载模块
			var node = doc.createElement('script'), url =  (
			modules[item] ? (dir + '') : (config.base || '')
			) + (that.modules[item] || item) + '.js';
			node.async = true;
			node.charset = 'utf-8';
			node.src = url + function(){
			var version = config.version === true 
			? (config.v || (new Date()).getTime())
			: (config.version||'');
			return version ? ('?v=' + version) : '';
			}();
			//首次加载
			if(!config.modules[item]){
				head.appendChild(node);
				if(node.attachEvent && !(node.attachEvent.toString && node.attachEvent.toString().indexOf('[native code') < 0) && !isOpera){
				  node.attachEvent('onreadystatechange', function(e){
				    onScriptLoad(e, url);
				  });
				} else {
				  node.addEventListener('load', function(e){
				    onScriptLoad(e, url);
				  }, false);
				}
			} else {
				(function poll() {
				  if(++timeout > config.timeout * 1000 / 4){
				    return error(item + ' is not a valid module');
				  };
				  (typeof config.modules[item] === 'string' && config.status[item]) 
				  ? onCallback() 
				  : setTimeout(poll, 4);
				}());
			}

			config.modules[item] = url;

			//回调
			function onCallback(){
				exports.push(byy[item]);
				apps.length > 1 ?
				  that.require(apps.slice(1), callback, exports)
				: ( typeof callback === 'function' && callback.apply(byy, exports) );
			}

			return that;
		}
		,stope : function( e ){
			e = e || win.event;
  			e.stopPropagation ? e.stopPropagation() : e.cancelBubble = true;
		}
		,each : function(obj , fn ){
			var that = this, key;
			if(typeof fn !== 'function') return that;
			obj = obj || [];
			if(obj.constructor === Object){
				for(key in obj){
					if(fn.call(obj[key], key, obj[key])) break;
				}
			} else {
				for(key = 0; key < obj.length; key++){
					if(fn.call(obj[key], key, obj[key])) break;
				}
			}
			return that;
		}
	});
	
	/** 绑定一些常用的小默认事件 **/
	var initUI = function( target ){
		/* 如果页面中存在 .byy-code 则初始化code */
		target = target || $('body');
		if(target.find('pre.byy-code').length > 0 ){
			byy(target.find('pre.byy-code')).code();
		}

		/* 如果页面中存在 .byy-radio 则初始化 radio */
		if(target.find('input.byy-form-radio').length > 0){
			byy(target.find('input.byy-form-radio')).radio();
		}
		/* 如果页面中存在 .byy-checkbox 则初始化 checkbox */
		if(target.find('input.byy-form-checkbox').length > 0){
			byy(target.find('input.byy-form-checkbox')).checkbox();
		}
		/* 如果页面中存在 .byy-form-select 则初始化select */
		if(target.find('select.byy-form-select').length > 0 ){
			byy(target.find('select.byy-form-select')).select();
		}

		/*NAV*/
		if(target.find('.byy-nav').length > 0){
			byy(target.find('.byy-nav')).nav();
		}
		/*breadcrumb*/
		if(target.find('.byy-breadcrumb').length > 0){
			byy(target.find('.byy-breadcrumb')).breadcrumb();
		}
		/*按钮菜单*/
		if(target.find('.byy-btn-menu').length > 0){
			byy(target.find('.byy-btn-menu')).buttonmenu();
		}
	}
	byy.fn.extend({
		initUI : function(){
			var $ele = this.$ele;
			initUI($ele);
		}
	});
	/**暴露initUI接口**/
	byy.extend({ 
		initUI : function(){
			initUI();
		}
	});
	/**工具类：创建Js / css **/
	byy.extend({
		link : function(href, fn, cssname){
			var that = this, link = doc.createElement('link');
			var head = doc.getElementsByTagName('head')[0];
			if(typeof fn === 'string') cssname = fn;
			var app = (cssname || href).replace(/\.|\//g, '');
			var id = link.id = 'byycss-'+app, timeout = 0;

			link.rel = 'stylesheet';
			link.type = 'text/css';
			link.href = href + (config.debug ? '?v='+new Date().getTime() : '');
			link.media = 'all';

			if(!doc.getElementById(id)){
				head.appendChild(link);
			}

			if(typeof fn !== 'function') return ;
			//轮询css是否加载完毕
			(function poll() {
				if(++timeout > config.timeout * 1000 / 100){
				  return error(href + ' timeout');
				};
				parseInt(that.getStyle(doc.getElementById(id), 'width')) === 1989 ? function(){
				  fn();
				}() : setTimeout(poll, 100);
			}());
		},
		addcss : function(firename, fn, cssname){
			byy.link(config.dir + 'css/' + firename, fn, cssname);
		}
		
	});


	/**工具类：数组**/
	byy.extend({
		contains : function( target , item ){
			return target.indexOf(item) > -1;
		},
		removeAt : function( target , index ){
			return !!target.splice( index , 1).length;
		},
		remove : function( target , item ){
			var index = target.indexOf( item );
			if( index > -1 ){
				return byy.removeAt( target , index);
			}
			return false;
		},
		shuffle : function( target ){
			var x , j , l ;
			for(l = target.length; l > 0 ;){
				j = parseInt(Math.random() * l);
				x = target[--l],target[l] = target[j],target[j] = x;
			}
			return target;
		},
		random : function( target ){
			return target[parseInt(Math.random() * target.length )];
		},
		flatten : function( target ){
			var res = [];
			target.forEach(function( ele , index){
				if(Array.isArray( ele )){
					res = res.concat( byy.flatten( ele ) );
				}else{
					res.push( ele );
				}
			});
			return res;
		},
		unique : function( target ){
			var res = [];
			loop : for( var i =0 ,n = target.length;i<n;i++){
				for(var x = i + 1 ;x < n ; x++ ){
					if(target[x] === target[i]){
						continue loop;
					}
				}
				res.push(target[i]);
			}
			return res;
		},
		compact : function( target , isTrim){
			return target.filter(function( ele ){
				return ( isTrim ? (byy.trim(ele) === '' ? false : true ) : true ) && !byy.isNull( ele );
			});
		},
		/*仅仅能提取一层关系，主要是对于纯对象*/
		pluck : function( target , name ,rtnNull){
			var result = [], prop ;
			target.forEach(function( ele ){
				prop = ele[name];
				if(prop != null){
					result.push(prop);
				}else if(rtnNull === true){
					result.push(prop);
				}
			});
			return result;
		},
		union : function(){
			var len = arguments.length,res = [];
			for(var i=0;i<len;i++){
				res = res.concat(arguments[i]);
			}
			return byy.unique(res);
		},
		min : function( target ){
			return Math.min.apply(0 , target);
		},
		max : function( target ){
			return Math.max.apply(0 , target);
		}

	});


	/**工具类：日期**/
	byy.extend({
		getNextDay : function( date , i){

		},
		/*目前仅支持 - */
		parseDate : function( str ){

		},
		isLeepYear : function( d ){
			if(d instanceof Date){
				var year = d.getFullYear();
	    		return !!((year & 3) == 0 && (year % 100 || (year % 400 == 0 && year)));
			}else{
				error('日期类型不正确!');
			}
		}
	});



	/**工具类：变量判断**/
	byy.extend({
		isNull : function( obj ){
			return null == obj || undefined == obj;
		},
		isArray : function( obj ){
			return $.isArray( obj );
		},
		isFunction : function( obj ){
			return $.isFunction( obj );
		},
		isEmptyObject : function( obj ){
			return $.isEmptyObject( obj );
		},
		isPlainObject : function( obj ){
			return $.isPlainObject( obj );
		},
		isWindow : function( obj ){
			return $.isWindow( obj );
		},
		isNumeric : function( obj ){
			return $.isNumeric( obj );
		},
		type : function( obj ){
			return $.type ( obj );
		}
	});


	/**工具类：浏览器判断**/
	byy.extend({	
		isIE : function(){
			return $.browser.msie;
		},
		isOpera : function(){
			return $.browser.opera;
		},
		isFF : function(){
			return $.browser.mozilla;
		},
		isSafari : function(){
			return $.browser.safari;
		},
		//校验当前是否为移动端设备
		isMobile : function(){
			var sUserAgent = navigator.userAgent.toLowerCase();
		    var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";
		    var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
		    var bIsMidp = sUserAgent.match(/midp/i) == "midp";
		    var bIsUc7 = sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";
		    var bIsUc = sUserAgent.match(/ucweb/i) == "ucweb";
		    var bIsAndroid = sUserAgent.match(/android/i) == "android";
		    var bIsCE = sUserAgent.match(/windows ce/i) == "windows ce";
		    var bIsWM = sUserAgent.match(/windows mobile/i) == "windows mobile";
		    if (!(bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM) ){
		        return false;
		    }else{
		    	return true;
		    }
		},
		isIOS : function(){
			var sUserAgent = navigator.userAgent.toLowerCase();
		    var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";
		    var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
		    var bIsMidp = sUserAgent.match(/midp/i) == "midp";
		    var bIsUc7 = sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";
		    var bIsUc = sUserAgent.match(/ucweb/i) == "ucweb";
		    var bIsAndroid = sUserAgent.match(/android/i) == "android";
		    var bIsCE = sUserAgent.match(/windows ce/i) == "windows ce";
		    var bIsWM = sUserAgent.match(/windows mobile/i) == "windows mobile";
		    if (!(bIsIpad || bIsIphoneOs ) ){
		        return false;
		    }else{
		    	return true;
		    }
		}
	});

	/**工具类：url**/
	byy.extend({
		getSearch : function( url , name ){
			if( !name ){
				name = url;
				url = location.href;
			}
			var rv = '';
			url = url.indexOf('?') > -1 ? url.split('?')[1] : '';
			if(url != ''){
				var ls = url.split('&');
				for(var i=0;i<ls.length;i++){
					var ele = ls[i];
					var kname = ele.split('=')[0] ||'',kvalue = ele.split('=')[1] || '';
					if(kname === name){
						rv = kvalue;
						break;
					}
				}
			}
			return decodeURIComponent(rv);
		},
		addUrlParams : function( url , params){
			if(!!!params){
				params = url;
				url = location.href;
			}
			var addStr = (function(opt){
				var ts = '';
				for(var k in opt){
					var value = encodeURIComponent(opt[k]);
					ts += k+'='+value+'&';
				}
				ts = ts.substring(0,ts.lastIndexOf('&'));
				return ts;
			})(params);
			if(url.indexOf('?') > -1 ){
				url = url + '&' + addStr;
			}else{
				url = url + '?' + addStr;
			}
			return url;
		}

	});


	/***工具类：字符串*/
	byy.extend({
		trim : function( str ){
			if( typeof str === 'string'){
				return str.replace(/^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g,'');
			}
			return str;
		},
		/**格式化字符串替换**/
		formatStr : function( ){
			str = arguments[0];
			var arr = [].splice.call(arguments,1,arguments.length -1);
			return str.replace(/\{(\d+)\}/g,function(s,i){
				return arr[i] || '';
			});
		}
	});

	/**工具类：md5**/
	byy.extend({
		md5 : function( string ){
			var rotateLeft = function(lValue, iShiftBits) {
				return (lValue << iShiftBits) | (lValue >>> (32 - iShiftBits));
			};
			var addUnsigned = function(lX, lY) {
				var lX4, lY4, lX8, lY8, lResult;
				lX8 = (lX & 0x80000000);
				lY8 = (lY & 0x80000000);
				lX4 = (lX & 0x40000000);
				lY4 = (lY & 0x40000000);
				lResult = (lX & 0x3FFFFFFF) + (lY & 0x3FFFFFFF);
				if (lX4 & lY4) return (lResult ^ 0x80000000 ^ lX8 ^ lY8);
				if (lX4 | lY4) {
					if (lResult & 0x40000000) return (lResult ^ 0xC0000000 ^ lX8 ^ lY8);
					else return (lResult ^ 0x40000000 ^ lX8 ^ lY8);
				} else {
					return (lResult ^ lX8 ^ lY8);
				}
			};
			var F = function(x, y, z) {
				return (x & y) | ((~ x) & z);
			};
			var G = function(x, y, z) {
				return (x & z) | (y & (~ z));
			};
			var H = function(x, y, z) {
				return (x ^ y ^ z);
			};
			var I = function(x, y, z) {
				return (y ^ (x | (~ z)));
			};
			var FF = function(a, b, c, d, x, s, ac) {
				a = addUnsigned(a, addUnsigned(addUnsigned(F(b, c, d), x), ac));
				return addUnsigned(rotateLeft(a, s), b);
			};
			var GG = function(a, b, c, d, x, s, ac) {
				a = addUnsigned(a, addUnsigned(addUnsigned(G(b, c, d), x), ac));
				return addUnsigned(rotateLeft(a, s), b);
			};
			var HH = function(a, b, c, d, x, s, ac) {
				a = addUnsigned(a, addUnsigned(addUnsigned(H(b, c, d), x), ac));
				return addUnsigned(rotateLeft(a, s), b);
			};
			var II = function(a, b, c, d, x, s, ac) {
				a = addUnsigned(a, addUnsigned(addUnsigned(I(b, c, d), x), ac));
				return addUnsigned(rotateLeft(a, s), b);
			};
			var convertToWordArray = function(string) {
				var lWordCount;
				var lMessageLength = string.length;
				var lNumberOfWordsTempOne = lMessageLength + 8;
				var lNumberOfWordsTempTwo = (lNumberOfWordsTempOne - (lNumberOfWordsTempOne % 64)) / 64;
				var lNumberOfWords = (lNumberOfWordsTempTwo + 1) * 16;
				var lWordArray = Array(lNumberOfWords - 1);
				var lBytePosition = 0;
				var lByteCount = 0;
				while (lByteCount < lMessageLength) {
					lWordCount = (lByteCount - (lByteCount % 4)) / 4;
					lBytePosition = (lByteCount % 4) * 8;
					lWordArray[lWordCount] = (lWordArray[lWordCount] | (string.charCodeAt(lByteCount) << lBytePosition));
					lByteCount++;
				}
				lWordCount = (lByteCount - (lByteCount % 4)) / 4;
				lBytePosition = (lByteCount % 4) * 8;
				lWordArray[lWordCount] = lWordArray[lWordCount] | (0x80 << lBytePosition);
				lWordArray[lNumberOfWords - 2] = lMessageLength << 3;
				lWordArray[lNumberOfWords - 1] = lMessageLength >>> 29;
				return lWordArray;
			};
			var wordToHex = function(lValue) {
				var WordToHexValue = "", WordToHexValueTemp = "", lByte, lCount;
				for (lCount = 0; lCount <= 3; lCount++) {
					lByte = (lValue >>> (lCount * 8)) & 255;
					WordToHexValueTemp = "0" + lByte.toString(16);
					WordToHexValue = WordToHexValue + WordToHexValueTemp.substr(WordToHexValueTemp.length - 2, 2);
				}
				return WordToHexValue;
			};
			var uTF8Encode = function(string) {
				string = string.replace(/\x0d\x0a/g, "\x0a");
				var output = "";
				for (var n = 0; n < string.length; n++) {
					var c = string.charCodeAt(n);
					if (c < 128) {
						output += String.fromCharCode(c);
					} else if ((c > 127) && (c < 2048)) {
						output += String.fromCharCode((c >> 6) | 192);
						output += String.fromCharCode((c & 63) | 128);
					} else {
						output += String.fromCharCode((c >> 12) | 224);
						output += String.fromCharCode(((c >> 6) & 63) | 128);
						output += String.fromCharCode((c & 63) | 128);
					}
				}
				return output;
			};
			var x = Array();
			var k, AA, BB, CC, DD, a, b, c, d;
			var S11=7, S12=12, S13=17, S14=22;
			var S21=5, S22=9 , S23=14, S24=20;
			var S31=4, S32=11, S33=16, S34=23;
			var S41=6, S42=10, S43=15, S44=21;
			string = uTF8Encode(string);
			x = convertToWordArray(string);
			a = 0x67452301; b = 0xEFCDAB89; c = 0x98BADCFE; d = 0x10325476;
			for (k = 0; k < x.length; k += 16) {
				AA = a; BB = b; CC = c; DD = d;
				a = FF(a, b, c, d, x[k+0],  S11, 0xD76AA478);
				d = FF(d, a, b, c, x[k+1],  S12, 0xE8C7B756);
				c = FF(c, d, a, b, x[k+2],  S13, 0x242070DB);
				b = FF(b, c, d, a, x[k+3],  S14, 0xC1BDCEEE);
				a = FF(a, b, c, d, x[k+4],  S11, 0xF57C0FAF);
				d = FF(d, a, b, c, x[k+5],  S12, 0x4787C62A);
				c = FF(c, d, a, b, x[k+6],  S13, 0xA8304613);
				b = FF(b, c, d, a, x[k+7],  S14, 0xFD469501);
				a = FF(a, b, c, d, x[k+8],  S11, 0x698098D8);
				d = FF(d, a, b, c, x[k+9],  S12, 0x8B44F7AF);
				c = FF(c, d, a, b, x[k+10], S13, 0xFFFF5BB1);
				b = FF(b, c, d, a, x[k+11], S14, 0x895CD7BE);
				a = FF(a, b, c, d, x[k+12], S11, 0x6B901122);
				d = FF(d, a, b, c, x[k+13], S12, 0xFD987193);
				c = FF(c, d, a, b, x[k+14], S13, 0xA679438E);
				b = FF(b, c, d, a, x[k+15], S14, 0x49B40821);
				a = GG(a, b, c, d, x[k+1],  S21, 0xF61E2562);
				d = GG(d, a, b, c, x[k+6],  S22, 0xC040B340);
				c = GG(c, d, a, b, x[k+11], S23, 0x265E5A51);
				b = GG(b, c, d, a, x[k+0],  S24, 0xE9B6C7AA);
				a = GG(a, b, c, d, x[k+5],  S21, 0xD62F105D);
				d = GG(d, a, b, c, x[k+10], S22, 0x2441453);
				c = GG(c, d, a, b, x[k+15], S23, 0xD8A1E681);
				b = GG(b, c, d, a, x[k+4],  S24, 0xE7D3FBC8);
				a = GG(a, b, c, d, x[k+9],  S21, 0x21E1CDE6);
				d = GG(d, a, b, c, x[k+14], S22, 0xC33707D6);
				c = GG(c, d, a, b, x[k+3],  S23, 0xF4D50D87);
				b = GG(b, c, d, a, x[k+8],  S24, 0x455A14ED);
				a = GG(a, b, c, d, x[k+13], S21, 0xA9E3E905);
				d = GG(d, a, b, c, x[k+2],  S22, 0xFCEFA3F8);
				c = GG(c, d, a, b, x[k+7],  S23, 0x676F02D9);
				b = GG(b, c, d, a, x[k+12], S24, 0x8D2A4C8A);
				a = HH(a, b, c, d, x[k+5],  S31, 0xFFFA3942);
				d = HH(d, a, b, c, x[k+8],  S32, 0x8771F681);
				c = HH(c, d, a, b, x[k+11], S33, 0x6D9D6122);
				b = HH(b, c, d, a, x[k+14], S34, 0xFDE5380C);
				a = HH(a, b, c, d, x[k+1],  S31, 0xA4BEEA44);
				d = HH(d, a, b, c, x[k+4],  S32, 0x4BDECFA9);
				c = HH(c, d, a, b, x[k+7],  S33, 0xF6BB4B60);
				b = HH(b, c, d, a, x[k+10], S34, 0xBEBFBC70);
				a = HH(a, b, c, d, x[k+13], S31, 0x289B7EC6);
				d = HH(d, a, b, c, x[k+0],  S32, 0xEAA127FA);
				c = HH(c, d, a, b, x[k+3],  S33, 0xD4EF3085);
				b = HH(b, c, d, a, x[k+6],  S34, 0x4881D05);
				a = HH(a, b, c, d, x[k+9],  S31, 0xD9D4D039);
				d = HH(d, a, b, c, x[k+12], S32, 0xE6DB99E5);
				c = HH(c, d, a, b, x[k+15], S33, 0x1FA27CF8);
				b = HH(b, c, d, a, x[k+2],  S34, 0xC4AC5665);
				a = II(a, b, c, d, x[k+0],  S41, 0xF4292244);
				d = II(d, a, b, c, x[k+7],  S42, 0x432AFF97);
				c = II(c, d, a, b, x[k+14], S43, 0xAB9423A7);
				b = II(b, c, d, a, x[k+5],  S44, 0xFC93A039);
				a = II(a, b, c, d, x[k+12], S41, 0x655B59C3);
				d = II(d, a, b, c, x[k+3],  S42, 0x8F0CCC92);
				c = II(c, d, a, b, x[k+10], S43, 0xFFEFF47D);
				b = II(b, c, d, a, x[k+1],  S44, 0x85845DD1);
				a = II(a, b, c, d, x[k+8],  S41, 0x6FA87E4F);
				d = II(d, a, b, c, x[k+15], S42, 0xFE2CE6E0);
				c = II(c, d, a, b, x[k+6],  S43, 0xA3014314);
				b = II(b, c, d, a, x[k+13], S44, 0x4E0811A1);
				a = II(a, b, c, d, x[k+4],  S41, 0xF7537E82);
				d = II(d, a, b, c, x[k+11], S42, 0xBD3AF235);
				c = II(c, d, a, b, x[k+2],  S43, 0x2AD7D2BB);
				b = II(b, c, d, a, x[k+9],  S44, 0xEB86D391);
				a = addUnsigned(a, AA);
				b = addUnsigned(b, BB);
				c = addUnsigned(c, CC);
				d = addUnsigned(d, DD);
			}
			var tempValue = wordToHex(a) + wordToHex(b) + wordToHex(c) + wordToHex(d);
			return tempValue.toLowerCase();
		}
	});

	/**** 工具类： json ****/
	byy.extend({
		json : function(str,flag){
			if(null !=str && undefined != str && ''!=str && typeof str != 'object'){
				var obj = {};
				try{
					obj = $.parseJSON(str);	
				}catch(Error){
					return {error:Error,str : str};
				}
				null != flag && undefined != flag && flag == true ? byy.fixJson(obj) : '';
				return obj;
			}else if(typeof str == 'object'){
				return str;
			}
			return {};
		},
		fixJson : function(target){
			var map = {};
			var scan = function(o){
				if(o.hasOwnProperty('$id')){
					var v = o['$id'];
					map[v] = o;
				}
				//如果含有ID ，则为独立对象，需要对ref进行替换
				if(!$.isEmptyObject(o) && !$.isFunction(o) &&null!=o&&o&&'null'!=o){
					for(var n in o){
						if(o.hasOwnProperty(n)){
							if(o[n] instanceof Object){
								scan(o[n]);
							}	
						}
					}
				}	
			};
			var replaceObj = function(o){
				//移除其他属性
				if(o.hasOwnProperty('$id')){
					delete o['$id'];
					delete o['$type'];
				}
				if(o.hasOwnProperty('$ref')){
					var v = o['$ref'];
					o = map[v];
					return o;
				}
				if(!$.isEmptyObject(o) && !$.isFunction(o)){
					for(var n in o){
						if(o[n] instanceof Object){
							o[n] = replaceObj(o[n]);
						}
					}
				}
				return o;
			};
			scan(target);
			replaceObj(target);
		},
		stringfy : function( obj ){
			if(null == obj || obj == undefined)return undefined;
			if(typeof obj == 'string')return obj;
			if(typeof obj =='number')return obj;
			var arrParse = function(temp){
				var tempstr = [];
				tempstr.push('[');
				for(var i=0;i<temp.length;i++){
					var tempobj = temp[i];
					var str = switchObj(tempobj);
					tempstr.push(str);
					if(i != temp.length-1){
						tempstr.push(',');
					}
				}
				tempstr.push(']');
				return tempstr.join('');
			
			};
			var switchObj  = function(tempobj){
				if(typeof tempobj == 'object'){
					if(tempobj instanceof Array){
						return arrParse(tempobj);
					}else if(tempobj instanceof Object){
						return objParse(tempobj);	
					}
				}else if(typeof tempobj == 'function'){
					return ''+tempobj.toString()+'';
				}else{
					return '"'+tempobj+'"';
				}
				return '';
			};
			var objParse = function(obj){
				var htmls = [];
				htmls.push('{');
				for(var p in obj){
					var tempobj = obj[p];
					var str= switchObj(tempobj);
					htmls.push('"'+p+'":'+str+'');
					htmls.push(',');
				}
				htmls.splice(htmls.length-1);
				htmls.push('}');
				return htmls.join('');
			};
			return switchObj(obj);
		}
	});


	/**frame 相关工具类**/
	byy.extend({
		//贯穿整个文档，查找该name的frame 对象<..实在找不到咋整昂。>
		findFrameByName : function(name){
			var w = win.top.document;
			//向下查找
			var num = 0;
			if(!$){
				error('jquery未引入');
				return;
			}
			var $iframes = $(w).find('iframe');
			var result = byy.findFrameByNameOfArr($iframes,name,0);
			return result;
		},
		frameLevel : 5,//frame 层级
		findFrameByNameOfArr : function(arr,name,num){
			num++;
			if(num == byy.frameLevel){
				return null;
			}
			var result = null;
			for(var i=0;i<arr.length;i++){
				var temp = arr[i];
				if($(temp).attr('name') == name){
					result =  $(temp);
					break;
				}else{
					var tempArr = $(temp).find('iframe');
					if(tempArr.length>0){
						result = byy.findFrameByNameOfArr(tempArr,name,num);
						if(result == null){
							continue;
						}else{
							break;
						}
					}else{
						continue;
					}
				}
			}
			return result;
		},
		//从顶级窗口开始查找frame,进行刷新reload,如果没有传递sel，则刷新第一个frame
		refreshFrame : function(frameName){
			var tempFrame = null;
			if(frameName){
				tempFrame = byy.findFrameByName(frameName);
			}else{
				tempFrame = $(top.window.document).find('iframe');
			}
			if(null != tempFrame){
				//刷新替换为location.reload，这样，在已经存在条件的页面则可以不丢失条件刷新。
				if(tempFrame[0]){
					tempFrame[0].contentWindow.location.reload();
				}
				//tempFrame.attr('src',tempFrame.attr('src'));
			}else{
				error('刷新失败，没有查找到该frame对象，请检查name是否正确');
			}
		},
	});


	/**其他工具类函数**/
	byy.extend({
		/**
		 * 获得视口的宽度和高度
		 ***/
		getPageWH : function(){
			var pageWidth=win.innerWidth,pageHeight=win.innerHeight;
			if ( typeof pageWidth !="number"){
				if ( doc.compatMode== "CSSICompat"){
					pageWidth=doc.documentElement.clientWidth;
					pageHeight=doc.documentElement.clientHeight;
				} else{
					pageWidth=doc.body.clientWidth;
					pageHeight=doc.body.clientHeight;
				}
			}
			return {
				width : pageWidth,
				height : pageHeight
			};
		},
		/***
		 * 得到obj对象中属性的个数
		 * @param obj
		 */
		getObjectLength : function(obj){
			var len = 0;
			for(var p in obj){
				if(obj.hasOwnProperty(p)){
					len++;
				}
			}
			return len;
		}
	});


	/** 代码修饰器 **/
	byy.fn.extend({
		code : function( opts ){
			var elems = [];
		    var options = opts || {};
		    options.elem = this.$ele || $('.byy-code');
		    
		    options.elem.each(function(){
		      elems.push(this);
		    });
		    elems.reverse().forEach(function( item , index ){
				var othis = $(item), html = othis.html();

				//转义HTML标签
				if(othis.attr('encode') || byy.isNull(options.encode) || options.encode == true ){
					html = html.replace(/&(?!#?[a-zA-Z0-9]+;)/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/'/g, '&#39;').replace(/"/g, '&quot;')
				}

				othis.html('<ol class="byy-code-ol"><li>' + html.replace(/[\r\n]+/g, '</li><li>').replace(/[\t]/g,'    ') + '</li></ol>')

				if(!othis.find('>.byy-code-h3')[0]){
					othis.prepend('<h3 class="byy-code-h3">'+ (options.title||'code')+ '</h3>');
				}

				var ol = othis.find('>.byy-code-ol');
				othis.addClass('byy-box byy-code-view');

				//识别皮肤
				if(options.skin){
					othis.addClass('byy-code-' +(options.skin));
				}

				//按行数适配左边距
				if((ol.find('li').length/100|0) > 0){
					ol.css('margin-left', (ol.find('li').length/100|0) + 'px');
				}

				//设置最大高度
				if( options.height){
					ol.css('max-height',  options.height);
				}
		    });
		}
	});


	var rsubmitterTypes = /^(?:submit|button|image|reset|file)$/i,
		rsubmittable = /^(?:input|select|textarea|keygen)/i,
		rCRLF = /\r?\n/g,
		rcheckableType = (/^(?:checkbox|radio)$/i);
	/**表单序列化**/
	byy.fn.extend({
		getValues : function(){
			var tempArr =  $('input,select,textarea,keygen')
			.map(function(){
				return $(this).get(0);
			})
			.filter(function(){
				var type = this.type;
				return this.name && !$(this).is(':disabled') && rsubmittable.test( this.nodeName ) && !rsubmitterTypes.test( type ) &&
				( this.checked || !rcheckableType.test( type ) );
			})
			.map(function( i , elem ){
				var val = jQuery( this ).val();
				return val == null ?
					null :
					jQuery.isArray( val ) ?
						jQuery.map( val, function( val ) {
							return { name: elem.name, value: val.replace( rCRLF, "<br/>" ) };
						}) :
						{ name: elem.name, value: val.replace( rCRLF, "<br/>" ) };
			})
			.get();
			//合并处理
			var obj = {};
			tempArr.forEach(function(ele){
				var name = ele.name,value = ele.value;
				if(name in obj){
					obj[name] = byy.isArray(obj[name]) ? obj[name].push(value) && obj[name] : [obj[name],value];
				}else{
					obj[name] = value;
				}
			});
			return obj;
		},
		setValues : function( obj ){
			if(byy.isEmptyObject( obj )){
				return;
			}
			var thiz = this;
			var map = {};
			thiz.$ele.find('[name]').each(function(){
				var name = $(this).attr('name');
				if(name.indexOf('.') > -1){
					//递归获得
					var names = name.split('.');
					//循环判断
					var getByName = function(obj,names){
						var n = names.splice(0,1)[0];
						if(obj[n] && names.length > 0){
							return getByName(obj[n],names);
						}else{
							if(obj[n]){
								return obj[n];
							}else{
								return "";
							}
							//如果不存在，要怎么处理？
							return "";
						}
					}
					var getVal = getByName(obj,names); 
					map[name] = getVal;
				}else{
					if( !byy.isNull(obj[name]) ){
						map[name] = obj[name];//直接赋值，如果有多个如何处理
					}
				}
			});
			//开始赋值
			for(var name in map){
				//1. 判断当前所属name的nodeType ,如果属于radio checkbox 则进行选中，如果属于text file hidden 则直接赋值，如果是
				var $target = thiz.$ele.find('[name="'+name+'"]');
				var eleshow = $target.css('display') === 'none' ? true : false,formatter = $target.attr('formatter') || '',ff = formatter == '' ? function(v){return v;} : eval('('+formatter+')');
				eleshow ? $target.show() : '';
				var val = map[name];
				if($target.length > 0){
					$target.each(function(){
						var $targetThis = $(this),targetThis = $targetThis[0],nodeType = targetThis.type,nodeName = targetThis.nodeName;
						//1.判断是不是check radio
						if(rcheckableType.test(nodeType)){
							//获得当前的值，判断是否在val中存在
							var targetVal = $targetThis.attr('value') || '';
							var valArr = byy.isArray(val) ? val : (val+'').split(',');//以逗号分割
							if(byy.contains(valArr,targetVal) && targetVal != ''){
								$targetThis.prop('checked',true);
							}else{
								$targetThis.prop('checked',false);
							}
						}else if(rsubmittable.test(nodeName)){
							$targetThis.val(typeof val == 'string' ? ff(val) : val);
						}else{
							$targetThis.html(typeof val == 'string' ? ff(val) : val);
						}
					});
				}
				eleshow ? $target.hide() : '';
			}
			//赋值结束后，如果需要渲染，则重新渲染
			if(thiz.$ele.find('.byy-form-select').length > 0){
				byy(thiz.$ele.find('select.byy-form-select')).select();
			}
			if(thiz.$ele.find('.byy-form-radio').length > 0){
				byy(thiz.$ele.find('.byy-form-radio')).radio();
			}
			if(thiz.$ele.find('.byy-form-checkbox').length > 0){
				byy(thiz.$ele.find('.byy-form-checkbox')).checkbox();
			}
		},
		/*重置表单内的所有的input/select/textarea/keygen/radio/hidden;等表单元素*/
		reset : function( obj ){//可以填写默认数据，类似setValues
			if(byy.isNull( obj )){
				obj = {};
			}
			var thiz = this;
			var map = {};
			thiz.$ele.find('[name]').each(function(){
				var name = $(this).attr('name');
				if(name.indexOf('.') > -1){
					//递归获得
					var names = name.split('.');
					//循环判断
					var getByName = function(obj,names){
						var n = names.splice(0,1)[0];
						if(obj[n] && names.length > 0){
							return getByName(obj[n],names);
						}else{
							if(obj[n]){
								return obj[n];
							}else{
								return "";
							}
							//如果不存在，要怎么处理？
							return "";
						}
					}
					var getVal = getByName(obj,names); 
					map[name] = getVal;
				}else{
					if( !byy.isNull(obj[name]) ){
						map[name] = obj[name];//直接赋值，如果有多个如何处理
					}else{
						map[name] = "";//如果不存在则为空
					}
				}
			});
			//开始赋值
			for(var name in map){
				//1. 判断当前所属name的nodeType ,如果属于radio checkbox 则进行选中，如果属于text file hidden 则直接赋值，如果是
				var $target = thiz.$ele.find('[name="'+name+'"]');
				var eleshow = $target.css('display') === 'none' ? true : false;
				eleshow ? $target.show() : '';
				var val = map[name];
				if($target.length > 1){
					var temp = $target[0];
					if(rcheckableType.test(temp.type)){
						//如果是复选框，判断val是不是数组，如果是数组则合并
						if(byy.isArray(val)){
							val = ','+val.join(',')+',';
						}else{
							val = ','+val+',';
						}
						$target.each(function(){
							if(val.indexOf(','+$(this).attr('value')+',') > -1 ){
								$(this).prop('checked',true);
							}else{
								$(this).prop('checked',false);
							}
						});
					}else{
						if(rsubmittable.test(temp.nodeName)){
							$target.val(val);
						}else{
							$target.attr('value',val);
						}
					}
				}else if($target.length == 1){
					//如果为div 或者span等需要显示的样式的话，用html
					if(rsubmittable.test($target.get(0).nodeName)){
						$target.val(val);
					}else{
						$target.html(val);
					}
				}else{//0 or hidden

				}
				eleshow ? $target.hide() : '';
			}
			//赋值结束后，如果需要渲染，则重新渲染
			if(thiz.$ele.find('.byy-form-select').length > 0){
				byy(thiz.$ele.find('select.byy-form-select')).select();
			}
			if(thiz.$ele.find('.byy-form-radio').length > 0){
				byy(thiz.$ele.find('.byy-form-radio')).radio();
			}
			if(thiz.$ele.find('.byy-form-checkbox').length > 0){
				byy(thiz.$ele.find('.byy-form-checkbox')).checkbox();
			}
		},
		toggleChecked : function(cls1,cls2,forceCls){
			var $ele = this.$ele;
			var cls = ($ele.attr('class') || '' ).split(' ');
			var newcls = cls.map(function(temp){
				if(temp == cls1){
					return forceCls || cls2;
				}else if(temp == cls2){
					return forceCls || cls1;
				}else{
					return temp;
				}
			});
			$ele.attr('class',newcls.join(' '));
		},
		//获得同级的index
		getIndex : function(){
			var $ele = this.$ele;
			var index = 0;
			while($ele.prev().length > 0){
				$ele = $ele.prev();
				index ++;
			}
			return index;
		}
	});


	/** 选项卡/标签页**/
	byy.fn.extend({
		tab : function( opts ){
			var thiz = this;
			var cfg = {
				skin : '',//card brief
				max : 10,
				notitle : false,//默认显示顶部标题
				async : false,//frame 异步加载
				contents : [
					{
						title : 'demo',
						content : 'demo content'
					}
				]
			};
			cfg = byy.extend( cfg , opts) ;
			var skin = cfg.skin == '' ? 'byy-tab' : ( cfg.skin == 'brief' ? 'byy-tab byy-tab-brief' : 'byy-tab byy-tab-card') + ( cfg.notitle === true ? ' notitle ' : '');
			var height = 'height' in cfg ? cfg.height : '';
			var $target = thiz.$ele,selector = thiz.selector;
			//将max存放在选择器元素上。
			$target.data('obj',cfg);
			(function( target ){
				target.html('').append('<div class="'+skin+'" style="'+(height == '' ? '' : 'height:'+height+'px;')+'"><ul class="byy-tab-title"></ul><div class="byy-tab-content" style="'+(height =='' ? '' : 'height:'+(height-55)+'px;')+'"></div></div>');

				cfg.contents.forEach(function( ele ,index ){
					ele.index = index;
					byy(selector).addTab( ele );
					if(cfg.onadd){
						cfg.onadd( ele );
					}
				});
				//查看是否有显示的
				if(target.find('.byy-tab-content>.byy-tab-item.show').length == 0 || target.find('.byy-tab-this').length == 0){
					//没有显示则将第一个设置为显示	
					target.find('.byy-tab-title>li:first').addClass('byy-tab-this');
					target.find('.byy-tab-content>.byy-tab-item:first').addClass('show');
				}
				//监听事件
				$(selector).on('click','.byy-tab-title>li',function(){
					var $tg = $(this);
					var temp = $tg.data('obj');
					if( $tg.hasClass('.byy-tab-this')){
						//不处理
					}else{
						byy(selector).toggleTab(temp.index);
					}
					//调用回调函数
					if(cfg.onClick){
						cfg.onclick( temp );
					}
				});
				$(selector).on('click','.byy-tab-close',function(ev){
					var temp = $(this).parent().data('obj'),index = temp.index;
					byy(selector).deleteTab(index);
					byy.stope(ev);
					byy(selector).fixTabIndex();
					if(opts.onClose){
						var rs = byy(selector).getNowTab();
						opts.onclose( rs );	
					}
				});
				return thiz;
			})($target);
		},
		getNowTab : function(){
			var $target = this.$ele;
			var rs = $target.find('.byy-tab-this').data('obj') || {};
			return rs;
		},
		addTab : function( object, index ){
			//查找ul和content
			var $container = this.$ele,cfg = $container.data('obj'),max = cfg.max || 10,notitle = cfg.notitle,async = cfg.async,selector = this.selector;
			var $title = $container.find('.byy-tab-title'),$content = $container.find('.byy-tab-content');
			//检查是否已经存在该tab页面（从title上进行监测）
			var nowtitle = object.title || '标题';
			var existsTab = false,existsTabIndex = 0;
			$title.find('li').each(function(){
				var tempObj = $(this).data('obj');
				if( (tempObj.title || '标题') == nowtitle){
					existsTab = true;
					existsTabIndex = tempObj.index;
				}
			});
			if(existsTab){
				byy(selector).toggleTab(existsTabIndex);
				return this;
			}
			var close = 'close' in object ? ( object.close === true ? true : false) : false;
			var li = $('<li></li');
			object.async = async;//设置everybody
			li.html(nowtitle);
			if(close){
				li.append('<i class="by-icon byy-unselect byy-tab-close">&#x1006;</i>');
			}
			//判断index 范围
			var hasIndex = false,nl = $title.find('li').length;
			if(!byy.isNull(index) && byy.isNumeric(index)){
				
				if(index > nl || index < 0 || index == nl) {
					index = nl;
					object.index = index;//直接插入
				}else {
					//修改后续的所有li
					hasIndex = true;
					object.index = index;
				}
			}
			if(byy.isNull(object.index)){
				object.index = nl;
			}
			li.data('obj',object);
			var content = '';
			if(object.url && object.url != ''){
				var iframe = '<iframe src="'+( !async ? object.url : '')+'" style="border:none;width:100%;height:100%;" ></iframe>';
				content = '<div class="byy-tab-item">'+(iframe)+'</div>';
			}else{
				content = '<div class="byy-tab-item">'+(object.content)+'</div>';	
			}
			if(hasIndex){
				$title.find('li:eq('+index+')').before(li);
				$content.find('.byy-tab-item:eq('+index+')').before(content);
			}else{
				$title.append(li),$content.append(content);	
			}
			byy(selector).fixTabIndex();
			//如果当前的长度已经大于max了
			if(nl > max-1 ){
				byy(selector).deleteTab(0);
			}
			//插入后如果当前没有显示的tab，则显示最后一个
			if($container.find('.byy-tab-this').length == 0){
				byy(selector).toggleTab($container.find('li').length -1 );
			}
			if(notitle === true){
				//删除前一个tab并跳转到最后一个
				for(var ii = nl - 1 ;ii>=0;ii--){
					byy(selector).deleteTab(ii);
				}
				byy(selector).toggleTab(0);//跳转到第一个
			}else{
				byy(selector).toggleTab(object.index);//跳转到第一个
			}
			return this;
		},
		fixTabIndex : function( ){
			var $othli = this.$ele.find('li');
			$othli.each(function(i,ele){
				var to = $(this).data('obj');
				to.index = i;
				$(this).data('obj',to);
			});
			return this;
		},
		deleteTab : function( index ){
			//删除某tab
			var $t = this.$ele,selector = this.selector;
			$t.find('.byy-tab-title').find('li:eq('+index+')').remove();
			$t.find('.byy-tab-content').find('.byy-tab-item:eq('+index+')').remove();
			//删除后调整index
			//关闭后跳转
			if($t.find('.byy-tab-this').length == 0 && $($t.find('li').length > 0)){
				byy(selector).toggleTab(index== 0 ? 0 : (index -1));
			}
			byy(selector).fixTabIndex();
			return this;
		},
		toggleTab : function( index ){
			//切换tab标签
			var $s = this.$ele;
			$s.find('.byy-tab-this').removeClass('byy-tab-this');
			$s.find('.show').removeClass('show');
			var $li = $s.find('.byy-tab-title>li:eq('+index+')'), obj = $li.data('obj'),async = obj.async,url = obj.url,hasload = obj.hasload ? true : false;
			$li.addClass('byy-tab-this');
			var $c = $s.find('.byy-tab-content>div:eq('+(index)+')');
			$c.addClass('show');
			if(!hasload && $c.find('iframe').length > 0){
				$c.find('iframe').attr('src',url);
				obj.hasload = true;
				$li.data('obj',obj);
			}
			return this;
		}
	});
	
	/** radio 组件**/
	byy.fn.extend({
		radio : function(){
	        var CLASS = 'byy-form-radio', ICON = ['&#xe643;', '&#xe63f;']
	        ,radios = this.$ele
	        
	        ,events = function(reElem){
	          var radio = $(this), ANIM = 'byy-anim-scaleSpring';
	          
	          reElem.on('click', function(){
	            var name = radio[0].name, forms = radio.parents();
	            // var filter = radio.attr('lay-filter'); //获取过滤器
	            var sameRadio = forms.find('input[name='+ name.replace(/(\.|#|\[|\])/g, '\\$1') +']'); //找到相同name的兄弟
	            if(radio[0].disabled) return;
	            
	            $.each(sameRadio, function(){
	              var next = $(this).next('.'+CLASS);
	              this.checked = false;
	              next.removeClass(CLASS+'ed');
	              next.find('.by-icon').removeClass(ANIM).html(ICON[1]);
	            });
	            
	            radio[0].checked = true;
	            reElem.addClass(CLASS+'ed');
	            reElem.find('.by-icon').addClass(ANIM).html(ICON[0]);
	            
	            // byy.event(MOD_NAME, 'radio('+ filter +')', {
	            //   elem: radio[0]
	            //   ,value: radio[0].value
	            // });
	          });
	        };
	        
	        radios.each(function(index, radio){
	          var othis = $(this), hasRender = othis.next('.' + CLASS), disabled = this.disabled;
	          
	          //替代元素
	          var reElem = $(['<div class="byy-unselect '+ CLASS + (radio.checked ? (' '+CLASS+'ed') : '') + (disabled ? ' byy-radio-disbaled '+DISABLED : '') +'">'
	          ,'<i class="byy-anim by-icon">'+ ICON[radio.checked ? 0 : 1] +'</i>'
	          ,'<span>'+ (radio.title||'未命名') +'</span>'
	          ,'</div>'].join(''));
	          
	          hasRender[0] && hasRender.remove(); //如果已经渲染，则Rerender
	          othis.after(reElem);
	          events.call(this, reElem);
	        });
		},
		checkbox: function(){
	        var CLASS = {
	          checkbox: ['byy-form-checkbox', 'byy-form-checked', 'checkbox']
	          ,_switch: ['byy-form-switch', 'byy-form-onswitch', 'switch']
	        }
	        ,checks = this.$ele
	        
	        ,events = function(reElem, RE_CLASS){
	          var check = $(this);
	          
	          //勾选
	          reElem.on('click', function(){
	            // var filter = check.attr('lay-filter'); //获取过滤器

	            if(check[0].disabled) return;
	            
	            check[0].checked ? (
	              check[0].checked = false
	              ,reElem.removeClass(RE_CLASS[1])
	            ) : (
	              check[0].checked = true
	              ,reElem.addClass(RE_CLASS[1])
	            );
	            // layui.event(MOD_NAME, RE_CLASS[2]+'('+ filter +')', {
	            //   elem: check[0]
	            //   ,value: check[0].value
	            // });
	          });
	        }
	        
	        checks.each(function(index, check){
	          var othis = $(this), skin = othis.attr('byy-skin'), disabled = this.disabled,switch_before = othis.attr('byy-before') || 'on',switch_after = othis.attr('byy-after') ||'off';
	          if(skin === 'switch') skin = '_'+skin;
	          var RE_CLASS = CLASS[skin] || CLASS.checkbox;
	          
	          //替代元素
	          var hasRender = othis.next('.' + RE_CLASS[0]);
	          var reElem = $(['<div class="byy-unselect '+ RE_CLASS[0] + (
	            check.checked ? (' '+RE_CLASS[1]) : '') + (disabled ? ' byy-checkbox-disbaled '+DISABLED : '') +'">'
	          ,{
	            _switch: '<span class="first">'+switch_before+'</span><i></i><span class="last">'+switch_after+'</span>'
	          }[skin] || ('<span>'+ (check.title || '勾选') +'</span><i class="by-icon">&#xe618;</i>')
	          ,'</div>'].join(''));

	          hasRender[0] && hasRender.remove(); //如果已经渲染，则Rerender
	          othis.after(reElem);
	          events.call(this, reElem, RE_CLASS);
	        });
      	},

      	select : function(){
	      	var TIPS = '请选择', CLASS = 'byy-form-select', TITLE = 'byy-select-title'
	        
	        ,selects = this.$ele, hide = function(e, clear){
	          if(!$(e.target).parent().hasClass(TITLE) || clear){
	            $('.'+CLASS).removeClass(CLASS+'ed');
	          }
	        }
	        
	        ,events = function(reElem, disabled){
	          var select = $(this), title = reElem.find('.' + TITLE);
	          
	          if(disabled) return;
	          //edited by lixun on 2017年3月13日 16:38:31，增加可以输入的情景
	          var canInput = select.attr('canInput') ? true : false;
	          if(canInput){
	          	var inputEle = reElem.find('input');
	          	inputEle.on('keyup',function(e){
	          		var val = $(this).val();//对底部的LI进行过滤
	          		var dds = reElem.find('dd');
	          		if(byy.trim(val) == ''){
	          			//全部显示
	          			dds.show();
	          		}else{
	          			dds.each(function(index,ele){
	          				if($(this).html().indexOf(val) > -1){
	          					$(this).show();
	          				}else{
	          					$(this).hide();
	          				}
	          			});
	          		}
	          	});
	          }

	          //展开下拉
	          title.on('click', function(e){
	            reElem.hasClass(CLASS+'ed') ? reElem.removeClass(CLASS+'ed') : (
	              hide(e, true), 
	              reElem.addClass(CLASS+'ed')
	            );
	          }); 
	          
	          //选择
	          reElem.find('dl>dd').on('click', function(){
	            var othis = $(this), value = othis.attr('byy-value');
	            var filter = select.attr('byy-filter'); //获取过滤器

	            if(othis.hasClass('byy-disabled')) return false;
	            
	            select.val(value).removeClass('byy-form-danger'), title.find('input').val(othis.text());
	            othis.addClass('byy-select-this').siblings().removeClass('byy-select-this');
	            // layui.event(MOD_NAME, 'select('+ filter +')', {
	            //   elem: select[0]
	            //   ,value: value
	            // });
	          });
	          
	          reElem.find('dl>dt').on('click', function(e){
	            return false;
	          });
	          
	          //关闭下拉
	          $(document).off('click', hide).on('click', hide)
	        }
	        
	        selects.each(function(index, select){
	          var othis = $(this), hasRender = othis.next('.'+CLASS), disabled = this.disabled;
	          var value = select.value, selected = $(select.options[select.selectedIndex]); //获取当前选中项
	          //edited by lixun on 2017年3月13日 16:35:24,增加可以输入的情景
	          var canInput = othis.attr('canInput') ? true : false;//true可以输入，false正常情况
	          //获得宽度
	          var cls = '';
	          if(othis.css('width')){
	          	var ew = othis.css('width');
	          	// ew = ew.indexOf('px') > -1 ? (parseInt(ew.replace('px',''),10)+10) : parseInt(ew,10)+10;
	          	cls = ' style="width:'+ew+';" '
	          }
	          //替代元素
	          var reElem = $(['<div '+cls+' class="byy-unselect '+ CLASS + (disabled ? ' byy-select-disabled' : '') +'">'
	            ,'<div class="'+ TITLE +'"><input type="text" placeholder="'+ (select.options.length > 0 && select.options[0].innerHTML ? select.options[0].innerHTML : TIPS) +'" value="'+ (value ? selected.html() : '') +'" '+( canInput ? '' : 'readonly')+' class="byy-input byy-unselect'+ (disabled ? (' '+DISABLED) : '') +'">'
	            ,'<i class="byy-edge"></i></div>'
	            ,'<dl class="byy-anim byy-anim-upbit'+ (othis.find('optgroup')[0] ? ' byy-select-group' : '') +'">'+ function(options){
	              var arr = [];
	              $.each(options, function(index, item){
	                if(index === 0 && !item.value) return;
	                if(item.tagName.toLowerCase() === 'optgroup'){
	                  arr.push('<dt>'+ item.label +'</dt>'); 
	                } else {
	                  arr.push('<dd byy-value="'+ item.value +'" class="'+ (value === item.value ?  'byy-select-this' : '') + (item.disabled ? (' '+DISABLED) : '') +'">'+ item.innerHTML +'</dd>');
	                }
	              });
	              return arr.join('');
	            }(othis.find('*')) +'</dl>'
	          ,'</div>'].join(''));
	          
	          hasRender[0] && hasRender.remove(); //如果已经渲染，则Rerender
	          othis.after(reElem);
	          events.call(this, reElem, disabled);
	        });
      	},

      	nav : function(){
      		var nav_ele = '.byy-nav',
      			nav_item = 'byy-nav-item',
      			nav_bar = 'byy-nav-bar',
      			mod_name = 'element',
      			nav_this = 'byy-this',
      			nav_show = 'show',
      			nav_tree = 'byy-nav-tree',
      			nav_child = 'byy-nav-child',
      			nav_more = 'byy-nav-more',
      			nav_anim = 'byy-anim byy-anim-upbit',
      			device = byy.device(),
      			navevents = {
      				clickThis : function(){
	      				var othis = $(this), 
	      					parents = othis.parents(nav_ele);
						if(othis.find('.'+nav_child)[0]){ return;}
						parents.find('.'+nav_this).removeClass(nav_this);
						othis.addClass(nav_this);
	      			},
	      			clickChild : function(){
	      				var othis = $(this), 
	      					parents = othis.parents( nav_ele );
						parents.find('.'+nav_this).removeClass( nav_this );
						othis.addClass( nav_this );
	      			},
	      			showChild : function(){
	      				var othis = $(this), 
	      					parents = othis.parents( nav_ele );
						var parent = othis.parent(), 
							child = othis.siblings('.'+nav_child);
						if(parents.hasClass( nav_tree )){
							child.removeClass( nav_anim );
							parent[child.css('display') === 'none' ? 'addClass': 'removeClass'](nav_item+'ed');
						}
	      			}
      			},
      			time = 200,
      			timer,timerMore,timerEnd,
      			follow = function(bar , nav){
      				var othis = $(this),
      					child = othis.find('.'+ nav_child);
      				if( nav.hasClass( nav_tree )){
      					bar.css({
      						top : othis.position().top,
      						height : othis.children('a').height(),
      						opacity : 1
      					});
      				}else{
      					child.addClass( nav_anim );
      					bar.css({
      						left : othis.position().left + parseFloat( othis.css('marginLeft') ),
      						top : othis.position().top + othis.height() - 5
      					});
      					timer = setTimeout(function(){
      						bar.css({
      							width : othis.width(),
      							opacity : 1
      						});
      					}, device.ie && device.ie < 10 ? 0 : time );
      					clearTimeout( timerEnd );
      					if( child.css('display') === 'block' ){
      						clearTimeout( timerMore );
      					}
      					timerMore = setTimeout(function(){
      						child.addClass( nav_show );
      						othis.find('.'+ nav_more).addClass( nav_more+'d' );
      					}, 300);
      				}
      			};
      		$( nav_ele).each(function(){
      			var othis = $(this),
      				bar = $('<span class="'+ nav_bar +'"></span>'),
      				itemElem = othis.find('.'+ nav_item );
      			if( !othis.find('.'+ nav_bar)[0] ){
      				othis.append( bar );
      				itemElem.on('mouseenter',function(){
      					follow.call( this ,bar , othis);
      				})
      				.on('mouseleave',function(){
      					if( !othis.hasClass( nav_tree )){
      						clearTimeout( timerMore );
      						timerMore = setTimeout(function(){
      							othis.find('.' + nav_child ).removeClass( nav_show );
      							othis.find('.'+ nav_more ).removeClass( nav_more+'d' );
      						},300);
      					}
      				});
      				
      				othis.on('mouseleave', function(){
      					clearTimeout( timer );
      					timeEnd = setTimeout( function(){
      						if( othis.hasClass( nav_tree )){
      							bar.css({
      								height : 0,
      								top : bar.position().top + bar.height() / 2,
      								opacity : 0 
      							});
      						}else{
      							bar.css({
      								width : 0,
      								left : bar.position().left + bar.width() / 2,
      								opacity : 0
      							});
      						}
      					} , time );
      				})
      			}

      			$( nav_ele ).each(function(){
      				var othis = $(this),
      					bar = $('<span class="'+ nav_bar +'"></span>'),
      					itemElem = othis.find('.'+ nav_item );
      				itemElem.each(function(){
      					var oitem = $(this),
      						child = oitem.find('.'+ nav_child);
      					if( child[0] && !oitem.find('.'+ nav_more)[0] ){
      						var one = oitem.children('a');
      						one.append('<span class="'+ nav_more +'"></span>');
      					}

      					oitem.off('click',navevents.clickThis ).on('click',navevents.clickThis);
      					oitem.children('a').off('click', navevents.showChild).on('click', navevents.showChild);
      					child.children('li').off('click', navevents.clickChild ).on('click', navevents.clickChild);
      				});
      			});
      		});
      	},
		breadcrumb: function(){
			var eles = this.$ele;
			$(eles).each(function(){
				var othis = $(this),separator = othis.attr('separator') || '>',aNode = othis.find('a');
				if(aNode.find('.byy-box')[0]) return;
				aNode.each(function(index){
					if(index === aNode.length - 1) return;
					$(this).append('<span class="byy-box">'+ separator +'</span>');
				});
				othis.css('visibility', 'visible');
			});
		},
		buttonmenu : function(){
			var ele = this.$ele;
			//绑定事件
			//获得btn的宽度和位置
			ele.find('ul').addClass('byy-anim').addClass('byy-anim-upbit');
			ele.on('mouseenter',function(){
				$(this).addClass('expand');
			}).on('mouseleave',function(){
				$(this).removeClass('expand');
			});
			//获得按钮宽度-设置
			var w = ele.css('width');
			ele.find('ul').css('min-width',w);
		}
	});

	/** 轮播,来自slideBox **/
	byy.fn.extend({
		slide : function( options ){
			//默认参数
			var defaults = {
				direction : 'left',//left,top
				duration : 0.6,//unit:seconds
				easing : 'swing',//swing,linear
				delay : 3,//unit:seconds
				startIndex : 0,
				hideClickBar : true,
				clickBarRadius : 5,//unit:px
				hideBottomBar : false
			};
			var settings = $.extend(defaults, options || {});
			//计算相关数据
			var wrapper = this.$ele, ul = wrapper.children('ul.items'), lis = ul.find('li'), firstPic = lis.first().find('img'),firstPicDiv= lis.first().find("div");
			var li_num = lis.size(), li_height = 0, li_width = 0;
			//初始化
			var init = function(){
				if(!wrapper.size()) return false;
				wrapper.data('over', 0);
				li_height = settings.height ? settings.height : lis.first().height();
				li_width = settings.width ? settings.width : lis.first().width();
	//			li_height = lis.first().height();
	//			li_width = lis.first().width();
				if(settings.wrapperWidth){
					wrapper.css({ height:li_height+'px'});
				}else{
					wrapper.css({width: li_width+'px', height:li_height+'px'});
				}
				lis.css({width: li_width+'px', height:li_height+'px'});//ADD.JENA.201207051027
				
				ul.append(ul.find('li:first').clone());
				li_num += 1;
				
				if (settings.direction == 'left') {
					ul.css('width', li_num * li_width + 'px');
				} else {
					ul.css('height', li_num * li_height + 'px');
				}			
				ul.find('li:eq('+settings.startIndex+')').addClass('active');
				
				if(!settings.hideBottomBar){//ADD.JENA.201208090859
					var tips = $('<div class="tips"></div>').css('opacity', 0.3).appendTo(wrapper);
					var title = $('<div class="title"></div>').html(function(){
						var active = ul.find('li.active').find('a'), text = active.attr('title'), href = active.attr('href');
						return $('<a>').attr('href', href).text(text);
					}).appendTo(tips);
					var nums = $('<div class="nums"></div>').hide().appendTo(tips);
					lis.each(function(i, n) {
						var a = $(n).find('a'), text = a.attr('title'), href = a.attr('href'), css = '';
						i == settings.startIndex && (css = 'active');
						$('<a>').attr('href', href).text(text).addClass(css).css('borderRadius', settings.clickBarRadius+'px').mouseover(function(){
							wrapper.data('over', 1);
							$(this).addClass('active').siblings().removeClass('active');
							ul.find('li:eq('+$(this).index()+')').addClass('active').siblings().removeClass('active');
							start();
						}).appendTo(nums);
					});
				
					if(settings.hideClickBar){//ADD.JENA.201206300847
						tips.hover(function(){
							nums.animate({top: '0px'}, 'fast');
						}, function(){
							nums.animate({top: tips.height()+'px'}, 'fast');
						});
						nums.show().delay(2000).animate({top: tips.height()+'px'}, 'fast');
					}else{
						nums.show();
					}
				}
				
				lis.size()>1 && start();
			};
			//开始轮播
			var start = function() {
				var active = ul.find('li.active'), active_a = active.find('a');
				var index = active.index();
				if(settings.direction == 'left'){
					offset = index * li_width * -1;
					param = {'left':offset + 'px' };
				}else{
					offset = index * li_height * -1;
					param = {'top':offset + 'px' };
				}
				
				wrapper.find('.nums').find('a:eq('+index+')').addClass('active').siblings().removeClass('active');
				wrapper.find('.title').find('a').attr('href', active_a.attr('href')).text(active_a.attr('title'));

				// EDIT.JENA.20150123
				var randomArr = ['linear','swing'];
				ul.stop().animate(param, settings.duration*1000, settings.easing == 'random' ? randomArr[Math.floor(Math.random()*randomArr.length)] : settings.easing, function() {
					active.removeClass('active');
					if(active.next().size()==0){
						ul.css({top:0, left:0}).find('li:eq(1)').addClass('active');
						wrapper.find('.nums').find('a:first').addClass('active').siblings().removeClass('active');
					}else{
						active.next().addClass('active');
					}
					wrapper.data('over')==0 && wrapper.data('timeid', window.setTimeout(start, settings.delay*1000));
				});
			};
			//停止轮播
			var stop = function() {
				window.clearTimeout(wrapper.data('timeid'));
			};
			//鼠标经过事件
	//		wrapper.hover(function(){
	//			wrapper.data('over', 1);
	//			stop();
	//		}, function(){
	//			wrapper.data('over', 0);
	//			start();
	//		});	
			//首张图片加载完毕后执行初始化
			var imgLoader = new Image();
			imgLoader.onload = function(){
				imgLoader.onload = null;
				init();
			};
			var background_image = "";
			if(firstPicDiv!=undefined&& firstPicDiv.css("background-image")!=undefined){
				var background_image = firstPicDiv.css("background-image");
				if(background_image.indexOf('"http')>=0){
					background_image = background_image.substring(background_image.indexOf("(")+2,background_image.length-2);
				}else{
					background_image = background_image.substring(background_image.indexOf("(")+1,background_image.length-1);
				}
			}
			imgLoader.src = firstPic.attr('src')||background_image;
		}
	});

	//自动引入jquery
	if(!window.$){
		byy.require('jquery',function(){
			//c
			initUI();
		});
	}else{
		initUI();
	}

	window.b = window.byy = byy;
	return byy;
});
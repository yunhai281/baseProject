/****
 * Jquery 滚轮事件
 * @author chrunlee
 * @support Jquery 1.7+
 * @time 2015年8月12日 17:43:29
 * 修改第一次。
 * 修改第二次。
 * 修改第三次。
 ****/
 (function(){
 	/***
 	 * @params className
 	 */
 	var df = [];
 	$.fn.scrollPage = function(className){
 		var $body = $(this);
 		$body.find(className).each(function(){
 			var $cn = $(this);
 			var top = $cn.offset().top;
 			df.push({
 				ele : $cn,
 				top : top
 			});
 		});
 	};
 	var  isscroll = false;
 	if(document.addEventListener){
 		document.addEventListener('DOMMouseScroll',function(event){
 			scrollPage(event);
 		},false);
 	}
	document.onmousewheel=function(event){
		scrollPage(event);
 	};
 	var scrollPage = function(event){
 		event = event || window.event;
		event.returnValue = false;
 		if(event.preventDefault){
 			event.preventDefault();
 		}
 		if(isscroll){
 			return false;
 		}
 		isscroll = true;
 		
 		var up = false;
 		if(event.wheelDelta){
 			up = event.wheelDelta > 0 ? true : false; 
 		}
 		if(event.detail){
 			up = event.detail >0 ? false : true;
 		}
 		var now = $(window).scrollTop();
 		var top = null;
 		
 		if(!up){//向下滚动
 			//拿到大于now的第一个元素
 			for(var i=0;i<df.length;i++){
 				var obj = df[i];
 				var temp = parseInt(obj.top,10);//火狐的高度有问题,后面有小数点...日
 				if(temp>now){
 					top = temp;
 					break;
 				}
 			}
 		}else{//向上滚动
 			for(var i=df.length-1;i>=0;i--){
 				var obj = df[i];
 				var temp = obj.top;
 				if(temp<now){
 					top = temp;
 					break;
 				}
 			}
 		}
 		//开始华东
 		$('html,body').animate({
 			scrollTop : top
 		},1000,function(){
 			isscroll = false;
 		});
 		
 	};
 	
 })(jQuery);
/*
 * 这里是仅用来定义树结构显示中，鼠标右键编辑时的一些事件
 */
$(function(){
	//点击页面空白处，隐藏右键显示菜单
	$("body").delegate('','click',function(){
		$('.dropdown-menu').css('display','none');
	});
});
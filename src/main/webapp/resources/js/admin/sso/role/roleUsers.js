var menu = {
	config : {
		getUsers : base + '/role_getUsers.do',
		pagesize : 10,
		form : '.byy-form',
		roleId:'',
		columns : [
			{checkbox : true,width:40,align:'center'},
			{column : 'userName' , name : '用户登录名',width : 150,formatter : function(v ,obj){
				var str='';
				if(v.length>10){
					str = v.substring(0,10)+'...';
				}else{
					str = v;
				}
			    return '<span title="'+v+'">'+str+'</span>';
			}},			
			{column : 'realName' , name : '姓名',width : 150,formatter : function(v ,obj){
				var str='';
				if(v.length>10){
					str = v.substring(0,10)+'...';
				}else{
					str = v;
				}
			    return '<span class="main-color1-font" title="'+v+'">'+str+'</span>';
			}},
			{column : 'sexName' , name : '性别',width : 150},
			{column : 'schoolName' , name : '学校',width : 150},			
			{column : 'mobile', name : '手机号',width : 150,formatter : function( v ){
				var str='';
				if(v.length>=12){
					str = v.substring(0,12);
				}else{
					str = v;
				}
			    return str; 
			}}
        ]
	},
	list : {
		//事件绑定
		bindEvent : function(){
			//1.查询
			$('.list_search').click(menu.list.loadData);
			//2.重置
			$('.list_reset').click(menu.list.searchReset);
			//3.新增教师
			$('.list_add').click(menu.list.add);
			
		},
		//加载表格数据
		loadData : function(opt){
			//1.得到查询框的默认参数
			var searchData = byy('.byy-form').getValues();
			//2.向参数增加分页参数
			searchData.rows = menu.config.pagesize;
			//3.如果有传递过来的分页参数，第几页，需要再增加进来
			if(opt){
				//这里通过查询获取到的page中的rows、page参数为undefined，需要判断并重新赋值一下
				searchData.rows = opt.pagesize || menu.config.pagesize;
				searchData.page = opt.curr || 1;
			}else{
				searchData.page=1;
				searchData.rows = behavior_page_rows||menu.config.pagesize;
			}
			searchData.Id = menu.config.roleId;
			//4.向后台请求，查询列表数据并进行加载
			var listIndex = byy.win.load(1);
			$.ajax({
				url : menu.config.getUsers,
				data : searchData,
				type : 'POST',
				success : function(res){
					byy.win.close(listIndex);
					var resobj = byy.json (res);
					if(resobj.error){
						byy.win.msg('后台查询错误');
					}
					var total = resobj.total;
                    byy.table({
                        selector : '#dtable',
                        columns : menu.config.columns,
                        data : resobj.rows,
                        border : 0,
                        striped : false
                    });
                    byy.page({
                    	pageArray:commonPageArray,
                        selector : '.pagination',
                        total : total,
                        showTotal : true,
                        pagesize : resobj.pagesize?resobj.pagesize:menu.config.pagesize,
                        curr : opt ? opt.curr : 1,
                        callback : function(opt){
                        	menu.list.loadData(opt);
                        }
                    });
				}
			});
		},
		//查询重置
		searchReset :  function(){
			//清空
			$('.byy-form').find('input:not([type=hidden])').val('');
			var cfg = $('.pagination').data('obj');
			menu.list.loadData(cfg);
		}
	}
};

byy.require(['jquery','win','table','page'],function(){
    var h = location.href;
    byy('.byy-breadcrumb').breadcrumb();
    if(h.toLowerCase().indexOf('tousers')>-1){
    	menu.list.bindEvent();
    	menu.list.loadData();
    }else{

    }
});
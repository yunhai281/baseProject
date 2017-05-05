var main = {
	config : {
		getList : base + '/operateLog_getList.do',
		pagesize : 10,
		tableObj : null,
		form : '.byy-form',
		tableCfg : {
			selector : '#dtable',
            border : 0,
            striped : false,
            sortSingle : true,
            checkOnSelect : true,
			columns : [
				{checkbox : true,width:40,align:'center'}, 
				{column : 'moduleName' , name : '操作模块',width :100},
				{column : 'ip' , name : '操作IP',width : 100},
				{column : 'operateDataId' , name : '操作目标',width : 100},
				{column : 'operateTime' , name : '操作时间',width : 110},
				{column : 'operateType', name : '操作类型',width : 130},
				{column : 'operateUserName', name : '操作人',width : 110},
				{column : 'operateResult',name : '操作结果',width : 100},
				{column : 'remark',name : '备注',width :200,formatter : function(v,obj){
							var str = '';
				if(v.length>15){
					str = v.substring(0,15)+'...';
				}else{
					str = v;
				}
				return '<span title="'+v+'">'+str+'</span>';
				}}
		    ]
		}
		
	},
	list : {
		//事件绑定
		bindEvent : function(){
			//1.查询
			$('.list_search').click(main.list.loadData);
			//2.重置
			$('.list_reset').click(main.list.searchReset);
		},
		//加载列表数据--opt传递过来的分页参数
		//加载表格数据
		loadData : function(opt){
			//1.得到查询框的默认参数
			var searchData = byy('.byy-form').getValues();
			//2.向参数增加分页参数
			searchData.rows = main.config.pagesize;
			//3.如果有传递过来的分页参数，第几页，需要再增加进来
			if(opt){
				//这里通过查询获取到的page中的rows、page参数为undefined，需要判断并重新赋值一下
				searchData.rows = opt.pagesize || main.config.pagesize;
				searchData.page = opt.curr || 1;
			}else{
				searchData.page=1;
				searchData.rows = main.config.pagesize;
			}
			//4.向后台请求，查询列表数据并进行加载
			var listIndex = byy.win.load(1);
			$.ajax({
				url : main.config.getList,
				data : searchData,
				type : 'POST',
				success : function(res){
					byy.win.close(listIndex);
					var resobj = byy.json(res);
					if(resobj.error){
						byy.win.msg('后台查询错误');
					}
					var total = resobj.total;
					if(main.config.tableObj){
						main.config.tableObj.loadData(resobj.rows);
					}else{
						main.config.tableCfg.data = resobj.rows;
						main.config.tableObj = byy.table(main.config.tableCfg);
					}
                    byy.page({
                    	pageArray:commonPageArray,
                        selector : '.pagination',
                        total : total,
                        showTotal:true,
                        pagesize : searchData.rows,
                        curr : opt ? opt.curr : 1,
                        callback : function(opt){
                        	main.list.loadData(opt);
                        }
                    });
				}
			});
		},
		//查询重置
		searchReset :  function(){
			//清空
			$('.byy-form').find('input:not([type=hidden])').val('');
			//获取分页对象
			var cfg = $('.pagination').data('obj');
			main.list.loadData(cfg);
		}
	}
}

byy.require(['jquery','win','table','page'],function(){
	var h = location.href;
	byy('.byy-breadcrumb').breadcrumb();
	main.list.bindEvent();
	main.list.loadData();
})
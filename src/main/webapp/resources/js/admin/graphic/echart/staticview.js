/**
 * 静态数据加载echart-柱状图
 */
function init_static_bar_echarts(){
	//路径配置
	require.config({
		paths:{
			echarts:'http://echarts.baidu.com/build/dist'
		}
	});
	//使用
	require(
		[
		 	'echarts',
		 	'echarts/chart/bar'//使用柱状图则加载bar模块，按需加载
		 ],
		 function(ec){
			//基于准备好的dom，初始化echarts图表
			var myChart = ec.init(document.getElementById('mainbar'));
			/*var option = {
					tooltip:{
						show:true
					},
					legend:{
						data:['销量']
					},
					xAxis:[
						{
							type:'category',
							data:["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子","衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
						}
					],
					yAxis:[
					    {
					    	type:'value'
					    }
			        ],
			        series:[
			             {
			             	"name":"销量",
			             	"type":"bar",
			             	"data":[5,20,40,10,10,20,5,20,40,10,10,20]
			             }
			         ]
			};*/
			var option = {
					title : {
				        text: '某地区蒸发量和降水量',
				        subtext: '纯属虚构'
				    },
				    tooltip : {
				        trigger: 'axis'
				    },
				    legend: {
				        data:['蒸发量','降水量']
				    },
				    toolbox: {
				        show : true,
				        feature : {
				            mark : {show: true},
				            dataView : {show: true, readOnly: false},
				            magicType : {show: true, type: ['line', 'bar']},
				            restore : {show: true},
				            saveAsImage : {show: true}
				        }
				    },
				    calculable : true,
				    xAxis : [
				        {
				            type : 'category',
				            data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value'
				        }
				    ],
				    series : [
				        {
				            name:'蒸发量',
				            type:'bar',
				            data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3],
				            markPoint : {
				                data : [
				                    {type : 'max', name: '最大值'},
				                    {type : 'min', name: '最小值'}
				                ]
				            },
				            markLine : {
				                data : [
				                    {type : 'average', name: '平均值'}
				                ]
				            }
				        },
				        {
				            name:'降水量',
				            type:'bar',
				            data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
				            markPoint : {
				                data : [
				                    {name : '年最高', value : 182.2, xAxis: 7, yAxis: 183, symbolSize:18},
				                    {name : '年最低', value : 2.3, xAxis: 11, yAxis: 3}
				                ]
				            },
				            markLine : {
				                data : [
				                    {type : 'average', name : '平均值'}
				                ]
				            }
				        }
				    ]
			};
			//为echarts对象加载数据
			myChart.setOption(option);
		});
}

/**
 * 动态初始化数据-柱状图
 */
function init_dynamic_bar_echarts(){
	//echarts初始化
	//路径配置
	/*require.config({
		paths:{
			echarts:'http://echarts.baidu.com/build/dist'
		}
	});
	require(
		    [
		        'echarts',
		        'echarts/chart/bar'
		    ], 
		    //回调函数内执行图表对象的初始化
		    function(ec) {
		        var myChart = ec.init(document.getElementById('mainbar'));
		        var option = {
		        		title : {
		        	        text: '某地区蒸发量和降水量-异步加载',
		        	        subtext: '纯属虚构'
		        	    },
		        	    tooltip : {
		        	        trigger: 'axis'
		        	    },
		        	    legend: {
		        	        data:['蒸发量','降水量']
		        	    },
		        	    toolbox: {
		        	        show : true,
		        	        feature : {
		        	            mark : {show: true},
		        	            dataView : {show: true, readOnly: false},
		        	            magicType : {show: true, type: ['line', 'bar']},
		        	            restore : {show: true},
		        	            saveAsImage : {show: true}
		        	        }
		        	    },
		        	    calculable : true,
		        	    xAxis : [
		        	        {
		        	            type : 'category',
		        	            data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
		        	        }
		        	    ],
		        	    yAxis : [
		        	        {
		        	            type : 'value'
		        	        }
		        	    ],
		        	    series : []
				};
		        myChart.showLoading();    //数据加载完之前先显示一段简单的loading动画
		        
		        var yData=[];    //数值（实际用来盛放Y坐标值）
		        
		        $.ajax({
		        	type:"post",
		        	async:true,//异步请求
		        	url:base+"/graphic_echartData.do",
		        	data:{},
		        	dataType:'json',
		        	success:function(result){
		        		//请求成功时执行该函数内容，result即为服务器返回的json对象
		                if (result) {
		                	myChart.hideLoading();    //隐藏加载动画
		                    option['series'] = result.series;      //加载数据图表
		                    //为echarts对象加载数据
		        			myChart.setOption(option);
		                }
		        	},
		        	error:function(errorMsg){
		        		//请求失败时执行该函数
		                alert("图表请求数据失败!");
		                myChart.hideLoading();
		        	}
		        });
		    }
		);*/
	
	$.ajax({
    	type:"post",
    	async:true,//异步请求
    	url:base+"/graphic_echartData.do",
    	data:{},
    	dataType:'json',
    	success:function(result){
    		//请求成功时执行该函数内容，result即为服务器返回的json对象
            if (result) {
            	var myChart = echarts.init(document.getElementById('mainbar'));
		        option = {
		        		title : {
		        	        text: '某地区蒸发量和降水量-异步加载',
		        	        subtext: '纯属虚构'
		        	    },
		        	    tooltip : {
		        	        trigger: 'axis'
		        	    },
		        	    legend: {
		        	        data:['蒸发量','降水量']
		        	    },
		        	    toolbox: {
		        	        show : true,
		        	        feature : {
		        	            mark : {show: true},
		        	            dataView : {show: true, readOnly: false},
		        	            magicType : {show: true, type: ['line', 'bar']},
		        	            restore : {show: true},
		        	            saveAsImage : {show: true}
		        	        }
		        	    },
		        	    calculable : true,
		        	    xAxis : [
		        	        {
		        	            type : 'category',
		        	            data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
		        	        }
		        	    ],
		        	    yAxis : [
		        	        {
		        	            type : 'value'
		        	        }
		        	    ],
		        	    series : result.series
				};
					                    
				// 为echarts对象加载数据 
				myChart.setOption(option); 
            }
    	},
    	error:function(errorMsg){
    		//请求失败时执行该函数
            alert("图表请求数据失败!");
            myChart.hideLoading();
    	}
    });
}

function init_static_line_echarts(){
	var myChart = echarts.init(document.getElementById('mainline'));
	var option = {
			title : {
		        text: '未来一周气温变化',
		        subtext: '纯属虚构'
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		        data:['最高气温','最低气温']
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: true},
		            dataView : {show: true, readOnly: false},
		            magicType : {show: true, type: ['line', 'bar']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    xAxis : [
		        {
		            type : 'category',
		            boundaryGap : false,
		            data : ['周一','周二','周三','周四','周五','周六','周日']
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value',
		            axisLabel : {
		                formatter: '{value} °C'
		            }
		        }
		    ],
		    series : [
		        {
		            name:'最高气温',
		            type:'line',
		            data:[11, 11, 15, 13, 12, 13, 10],
		            markPoint : {
		                data : [
		                    {type : 'max', name: '最大值'},
		                    {type : 'min', name: '最小值'}
		                ]
		            },
		            markLine : {
		                data : [
		                    {type : 'average', name: '平均值'}
		                ]
		            }
		        },
		        {
		            name:'最低气温',
		            type:'line',
		            data:[1, -2, 2, 5, 3, 2, 0],
		            markPoint : {
		                data : [
		                    {name : '周最低', value : -2, xAxis: 1, yAxis: -1.5}
		                ]
		            },
		            markLine : {
		                data : [
		                    {type : 'average', name : '平均值'}
		                ]
		            }
		        }
		    ]
	};
	//为echarts对象加载数据
	myChart.setOption(option);
}

function init_static_pie_echarts(){
	var myChart = echarts.init(document.getElementById('mainpie'));
	var option = {
			title : {
		        text: '某站点用户访问来源',
		        subtext: '纯属虚构',
		        x:'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        orient : 'vertical',
		        x : 'left',
		        data:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: true},
		            dataView : {show: true, readOnly: false},
		            magicType : {
		                show: true, 
		                type: ['pie', 'funnel'],
		                option: {
		                    funnel: {
		                        x: '25%',
		                        width: '50%',
		                        funnelAlign: 'left',
		                        max: 1548
		                    }
		                }
		            },
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    series : [
		        {
		            name:'访问来源',
		            type:'pie',
		            radius : '55%',
		            center: ['50%', '60%'],
		            data:[
		                {value:335, name:'直接访问'},
		                {value:310, name:'邮件营销'},
		                {value:234, name:'联盟广告'},
		                {value:135, name:'视频广告'},
		                {value:1548, name:'搜索引擎'}
		            ]
		        }
		    ]
	};
	//为echarts对象加载数据
	myChart.setOption(option);
}

function init_static_radar_echarts(){
	var myChart = echarts.init(document.getElementById('mainradar'));
	var option = {
			title : {
		        text: '罗纳尔多 vs 舍普琴科',
		        subtext: '完全实况球员数据'
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		        x : 'center',
		        data:['罗纳尔多','舍普琴科']
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: true},
		            dataView : {show: true, readOnly: false},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    polar : [
		        {
		            indicator : [
		                {text : '进攻', max  : 100},
		                {text : '防守', max  : 100},
		                {text : '体能', max  : 100},
		                {text : '速度', max  : 100},
		                {text : '力量', max  : 100},
		                {text : '技巧', max  : 100}
		            ],
		            radius : 130
		        }
		    ],
		    series : [
		        {
		            name: '完全实况球员数据',
		            type: 'radar',
		            itemStyle: {
		                normal: {
		                    areaStyle: {
		                        type: 'default'
		                    }
		                }
		            },
		            data : [
		                {
		                    value : [97, 42, 88, 94, 90, 86],
		                    name : '舍普琴科'
		                },
		                {
		                    value : [97, 32, 74, 95, 88, 92],
		                    name : '罗纳尔多'
		                }
		            ]
		        }
		    ]
	};
	//为echarts对象加载数据
	myChart.setOption(option);
}

$(document).ready(function () {
	//静态统计图调用
	//init_static_bar_echarts();
	//动态统计图调用
	init_dynamic_bar_echarts();
	init_static_line_echarts();
	init_static_pie_echarts();
	init_static_radar_echarts();
})
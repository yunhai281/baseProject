function init_static_bar_highcharts(){
	Highcharts.chart('mainbar', {
		chart: {
            type: 'column'
        },
        title: {
            text: '月平均降雨量'
        },
        subtitle: {
            text: '数据来源: WorldClimate.com'
        },
        xAxis: {
            categories: [
                '一月',
                '二月',
                '三月',
                '四月',
                '五月',
                '六月',
                '七月',
                '八月',
                '九月',
                '十月',
                '十一月',
                '十二月'
            ],
            crosshair: true
        },
        yAxis: {
            min: 0,
            title: {
                text: '降雨量 (mm)'
            }
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
            '<td style="padding:0"><b>{point.y:.1f} mm</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            }
        },
        series: [{
            name: '东京',
            data: [49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4]
        }, {
            name: '纽约',
            data: [83.6, 78.8, 98.5, 93.4, 106.0, 84.5, 105.0, 104.3, 91.2, 83.5, 106.6, 92.3]
        }, {
            name: '伦敦',
            data: [48.9, 38.8, 39.3, 41.4, 47.0, 48.3, 59.0, 59.6, 52.4, 65.2, 59.3, 51.2]
        }, {
            name: '柏林',
            data: [42.4, 33.2, 34.5, 39.7, 52.6, 75.5, 57.4, 60.4, 47.6, 39.1, 46.8, 51.1]
        }]
	});
}

function init_dynamic_bar_echarts(){
	$.getJSON(base+'/graphic_highchartData.do', function (data) {
        // Create the chart
		Highcharts.chart('mainbar', {
			chart: {
	            type: 'column'
	        },
	        title: {
	            text: '月平均降雨量'
	        },
	        subtitle: {
	            text: '数据来源: WorldClimate.com'
	        },
	        xAxis: {
	            categories: data.categories,
	            crosshair: true
	        },
	        yAxis: {
	            min: 0,
	            title: {
	                text: '降雨量 (mm)'
	            }
	        },
	        tooltip: {
	            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
	            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
	            '<td style="padding:0"><b>{point.y:.1f} mm</b></td></tr>',
	            footerFormat: '</table>',
	            shared: true,
	            useHTML: true
	        },
	        plotOptions: {
	            column: {
	                pointPadding: 0.2,
	                borderWidth: 0
	            }
	        },
	        series: data.series
		});
		
		
        /*$('#mainbar').highcharts('StockChart', {
            rangeSelector : {
                selected : 1
            },
            title : {
                text : 'AAPL Stock Price'
            },
            series : [{
                name : 'AAPL',
                data : data,
                tooltip: {
                    valueDecimals: 2
                }
            }]
        });*/
    });
}

function init_static_line_highcharts(){
	var chart = new Highcharts.Chart({
        chart: {
            renderTo: 'mainline'
            /*,type: 'column',
            margin: 75,
            options3d: {
                enabled: true,
                alpha: 15,
                beta: 15,
                depth: 50,
                viewDistance: 25
            }*/
        },
	//Highcharts.chart('mainline', {
		title: {
	        text: '不同城市的月平均气温',
	        x: -20
	    },
	    subtitle: {
	        text: '数据来源: WorldClimate.com',
	        x: -20
	    },
	    xAxis: {
	        categories: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月']
	    },
	    yAxis: {
	        title: {
	            text: '温度 (°C)'
	        },
	        plotLines: [{
	            value: 0,
	            width: 1,
	            color: '#808080'
	        }]
	    },
	    tooltip: {
	        valueSuffix: '°C'
	    },
	    legend: {
	        layout: 'vertical',
	        align: 'right',
	        verticalAlign: 'middle',
	        borderWidth: 0
	    },
	    series: [{
	        name: '东京',
	        data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
	    }, {
	        name: '纽约',
	        data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]
	    }, {
	        name: '柏林',
	        data: [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0]
	    }, {
	        name: '伦敦',
	        data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
	    }]
	});
}
function init_static_pie_highcharts(){
	Highcharts.chart('mainpie', {
	    chart: {
	        plotBackgroundColor: null,
	        plotBorderWidth: null,
	        plotShadow: false,
	        type: 'pie'
	    },
	    title: {
	        text: 'Browser market shares January, 2015 to May, 2015'
	    },
	    tooltip: {
	        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	    },
	    plotOptions: {
	        pie: {
	            allowPointSelect: true,
	            cursor: 'pointer',
	            dataLabels: {
	                enabled: true,
	                format: '<b>{point.name}</b>: {point.percentage:.1f} %',
	                style: {
	                    color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
	                }
	            }
	        }
	    },
	    series: [{
	        name: 'Brands',
	        colorByPoint: true,
	        data: [{
	            name: 'Microsoft Internet Explorer',
	            y: 56.33
	        }, {
	            name: 'Chrome',
	            y: 24.03,
	            sliced: true,
	            selected: true
	        }, {
	            name: 'Firefox',
	            y: 10.38
	        }, {
	            name: 'Safari',
	            y: 4.77
	        }, {
	            name: 'Opera',
	            y: 0.91
	        }, {
	            name: 'Proprietary or Undetectable',
	            y: 0.2
	        }]
	    }]
	});
}
function init_static_radar_highcharts(){
	Highcharts.chart('mainradar', {
		chart: {
	        polar: true
	    },

	    title: {
	        text: 'Highcharts Polar Chart'
	    },

	    pane: {
	        startAngle: 0,
	        endAngle: 360
	    },

	    xAxis: {
	        tickInterval: 45,
	        min: 0,
	        max: 360,
	        labels: {
	            formatter: function () {
	                return this.value + '°';
	            }
	        }
	    },

	    yAxis: {
	        min: 0
	    },

	    plotOptions: {
	        series: {
	            pointStart: 0,
	            pointInterval: 45
	        },
	        column: {
	            pointPadding: 0,
	            groupPadding: 0
	        }
	    },

	    series: [{
	        type: 'column',
	        name: 'Column',
	        data: [8, 7, 6, 5, 4, 3, 2, 1],
	        pointPlacement: 'between'
	    }, {
	        type: 'line',
	        name: 'Line',
	        data: [1, 2, 3, 4, 5, 6, 7, 8]
	    }, {
	        type: 'area',
	        name: 'Area',
	        data: [1, 8, 2, 7, 3, 6, 4, 5]
	    }]
	});
}
$(function(){
	Highcharts.setOptions({
		lang: {
			printChart:"打印图表",
            downloadJPEG: "下载JPEG 图片" , 
            downloadPDF: "下载PDF文档"  ,
            downloadPNG: "下载PNG 图片"  ,
            downloadSVG: "下载SVG 矢量图" , 
            exportButtonTitle: "导出图片" 
      }
    });
	//init_static_bar_highcharts();
	init_dynamic_bar_echarts();
	init_static_line_highcharts();
	init_static_pie_highcharts();
	init_static_radar_highcharts();
});
	
layui.config({
	base: 'http://www.qugou.com/merchant/layui/js/'
});
layui.use(['paging', 'form','laydate'], function() {
	var $ = layui.jquery,
		paging = layui.paging(),
		layerTips = parent.layer === undefined ? layui.layer : parent.layer, //获取父窗口的layer对象
		layer = layui.layer, //获取当前窗口的layer对象
		form = layui.form(),
	 	laydate = layui.laydate;
});
$(function(){
	//初始化统计信息
	doStatistics();
	$("#statistics").click(function(){
		doStatistics();
	});
	$("#export").click(function(){
		var url="/merchant/statistics/export/day";
		var exportType=$("#exportType").val();
		if(exportType!=null&&exportType!=''){
			url+="?exportType="+exportType;
		}
		window.open(url);
	});
});
function doStatistics(){
	 $.ajax({
			async : true, //是否异步  
			cache : false, //是否使用缓存  
			type : 'post', //请求方式,post  
			data : {
			},
			dataType : "json", //数据传输格式  
			url : '/merchant/statistics/saleDay', //请求链接  
			success : function(data) {
				if(data.status ==1) {
					var totalMoneyArray= new Array();
					var totalNumArray=new Array();
					var showIndexArray=new Array();
					$(data.data).each(function(index,item){
						showIndexArray[index]=item.productName;
						totalMoneyArray[index]=item.totalMoney;
						totalNumArray[index]=item.totalNum;
					});
					var date = new Date();
				    var year = date.getFullYear()+'年';
				    var month = date.getMonth()+1+'月';
				    var day = date.getDate()+'日';
				    var dateStr=year+month+day;
					init(showIndexArray,totalMoneyArray,totalNumArray,dateStr);
				}
			},
			error : function(XMLHttpRequest, data) {
				console.log(data.msg);
			}
	});
}

function init(showIndexArray,saleMoneyArray,saleNumArray,day){
	var dom = document.getElementById("container");
	var myChart = echarts.init(dom);
	var app = {};
	option = null;
	option = {
		title : {
			text : '今日销售统计',
			subtext : day
		},
		tooltip : {
			trigger : 'axis'
		},
		legend : {
			data : [ '销售额', '销售量' ]
		},
		toolbox : {
			show : true,
			feature : {
				dataView : {
					show : true,
					readOnly : false
				},
				magicType : {
					show : true,
					type : [ 'line', 'bar' ]
				},
				restore : {
					show : true
				},
				saveAsImage : {
					show : true
				}
			}
		},
		calculable : true,
		xAxis : [ {
			type : 'category',
			data : showIndexArray
		} ],
		yAxis : [ {
			type : 'value'
		} ],
		series : [
				{
					name : '销售额',
					type : 'bar',
					/*data : [ 2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2,
							32.6, 20.0, 6.4, 3.3 ],*/
					data :saleMoneyArray,
				markPoint : {
						data : [ {
							type : 'max',
							name : '最大值'
						}, {
							type : 'min',
							name : '最小值'
						} ]
					}/*,
					markLine : {
						data : [ {
							type : 'average',
							name : '平均值'
						} ]
					}*/
				},
				{
					name : '销售量',
					type : 'bar',
					/*data : [ 2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2,
							48.7, 18.8, 6.0, 2.3 ],*/
					data : saleNumArray,
					markPoint : {
						data : [ {
							type : 'max',
							name : '最大值'
						}, {
							type : 'min',
							name : '最小值'
						} ]
					}/*,
					markLine : {
						data : [ {
							type : 'average',
							name : '平均值'
						} ]
					}*/
				} ]
	};
	;
	if (option && typeof option === "object") {
		myChart.setOption(option, true);
	}
}
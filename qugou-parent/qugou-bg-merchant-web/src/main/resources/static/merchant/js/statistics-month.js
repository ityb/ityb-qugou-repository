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
		form.on('select(statistics_year)', function(data){
			 var date=new Date;
			 var nowYear=date.getFullYear(); 
			 var nowMonth=month=date.getMonth()+1; 
			 var selectYear=$("#statisticsYear").val();
			 var html="";
			 if(selectYear==(nowYear+"")){
				 for(var i=nowMonth;i>=1;i--){
					 html+="<option value='"+i+"'>"+i+"月</option>";
				 }
			 }else{
				 for(var i=1;i<=12;i++){
					 html+="<option value='"+i+"'>"+i+"月</option>";
				 }
			 }
			 $("#statisticsMonth").html(html);
			 form = layui.form();
			 form.render();
		});
});
$(function(){
	//初始化统计信息
	doStatistics();
	$("#statistics").click(function(){
		doStatistics();
	});
	$("#export").click(function(){
		var url="/merchant/statistics/export/month";
		var year=$("#statisticsYear").val();
		var month=$("#statisticsMonth").val()
		if(year!=null&&year!=''){
			url+="?year="+year;
		}
		if(year!=''&&month!=null&&month!=''){
			url+="&month="+month;
		}
		var exportType=$("#exportType").val();
		if(exportType!=null&&exportType!=''){
			url+="&exportType="+exportType;
		}
		window.open(url);
	});
});
function doStatistics(){
	var year=$("#statisticsYear").val();
	var month=$("#statisticsMonth").val();
	if(year!=null&&year!=''&&month!=null&&month!=''){
		 $.ajax({
				async : true, //是否异步  
				cache : false, //是否使用缓存  
				type : 'post', //请求方式,post  
				data : {
					year : year,
					month: month
				},
				dataType : "json", //数据传输格式  
				url : '/merchant/statistics/saleMonth', //请求链接  
				success : function(data) {
					if(data.status ==1) {
						var totalMoneyArray= new Array();
						var totalNumArray=new Array();
						var total=0;
						$(data.data).each(function(index,item){
							total+=item.totalMoney;
							totalMoneyArray[item.productName]=item.totalMoney;
							totalNumArray[item.productName]=item.totalNum;
						});
						var totalMoneyArrayJson=arrayToJson(saleMonthBarJson(totalMoneyArray));
						var totalNumArrayJson=arrayToJson(saleMonthBarJson(totalNumArray));
						init(totalMoneyArrayJson,totalNumArrayJson,total,year,month);
					}
				},
				error : function(XMLHttpRequest, data) {
					console.log(data.msg);
				}
		});
	}
}

function saleMonthBarJson(totalArray){
	var totalResultArray=new Array();
	var i=0;
	var other=0;
	var flag=false;
	for(var key in totalArray){
		i++;
		if(i>=10){
			other+=totalArray[key];
			flag=true;
		}else{
			totalResultArray[key]=totalArray[key];
		}
		if(flag){
			totalResultArray["其它"]=other;
		}
	}
	//进行降序排序
	totalResultArray.sort(function (x,y) {
         return x-y;
     });
	return totalResultArray;
}

function arrayToJson(totalResultArray){
	var arrayJson={};
	for(var key in totalResultArray){
		arrayJson[key]=totalResultArray[key];
	}
	return arrayJson;
}

function init(totalMoneyArrayJson,totalNumArrayJson,total,year,month){
	var dom = document.getElementById("container");
	var myChart = echarts.init(dom);
	var app = {};
	option = null;
	app.title = '月销售统计'

	var builderJson = {
	  "all": total,
	  "charts": totalMoneyArrayJson,
	  "components":totalNumArrayJson,
	  "ie": 9743
	};

	var downloadJson = totalMoneyArrayJson;

	var themeJson = totalNumArrayJson;

	var waterMarkText = 'ECHARTS';

	var canvas = document.createElement('canvas');
	var ctx = canvas.getContext('2d');
	canvas.width = canvas.height = 100;
	ctx.textAlign = 'center';
	ctx.textBaseline = 'middle';
	ctx.globalAlpha = 0.08;
	ctx.font = '20px Microsoft Yahei';
	ctx.translate(50, 50);
	ctx.rotate(-Math.PI / 4);
	ctx.fillText(waterMarkText, 0, 0);

	option = {
	    backgroundColor: {
	        type: 'pattern',
	        image: canvas,
	        repeat: 'repeat'
	    },
	    tooltip: {},
	    title: [{
	        text: '月销售统计',
	        subtext: '月份 ：' + year+"年"+month+"月",
	        x: '25%',
	        textAlign: 'center'
	    }, {
	        text: '销售额占比',
	        subtext: '月份 ：' + year+"年"+month+"月",
	        x: '75%',
	        textAlign: 'center'
	    }, {
	        text: '销售量占比',
	        subtext: '月份 ：' + year+"年"+month+"月",
	        x: '75%',
	        y: '50%',
	        textAlign: 'center'
	    }],
	    grid: [{
	        top: 50,
	        width: '50%',
	        bottom: '45%',
	        left: 10,
	        containLabel: true
	    }, {
	        top: '55%',
	        width: '50%',
	        bottom: 0,
	        left: 10,
	        containLabel: true
	    }],
	    xAxis: [{
	        type: 'value',
	        max: builderJson.all,
	        splitLine: {
	            show: false
	        }
	    }, {
	        type: 'value',
	        max: builderJson.all,
	        gridIndex: 1,
	        splitLine: {
	            show: false
	        }
	    }],
	    yAxis: [{
	        type: 'category',
	        data: Object.keys(builderJson.charts),
	        axisLabel: {
	            interval: 0,
	            rotate: 30
	        },
	        splitLine: {
	            show: false
	        }
	    }, {
	        gridIndex: 1,
	        type: 'category',
	        data: Object.keys(builderJson.components),
	        axisLabel: {
	            interval: 0,
	            rotate: 30
	        },
	        splitLine: {
	            show: false
	        }
	    }],
	    series: [{
	        type: 'bar',
	        stack: 'chart',
	        z: 3,
	        label: {
	            normal: {
	                position: 'right',
	                show: true
	            }
	        },
	        data: Object.keys(builderJson.charts).map(function (key) {
	            return builderJson.charts[key];
	        })
	    }, {
	        type: 'bar',
	        stack: 'chart',
	        silent: true,
	        itemStyle: {
	            normal: {
	                color: '#eee'
	            }
	        },
	        data: Object.keys(builderJson.charts).map(function (key) {
	            return builderJson.all - builderJson.charts[key];
	        })
	    }, {
	        type: 'bar',
	        stack: 'component',
	        xAxisIndex: 1,
	        yAxisIndex: 1,
	        z: 3,
	        label: {
	            normal: {
	                position: 'right',
	                show: true
	            }
	        },
	        data: Object.keys(builderJson.components).map(function (key) {
	            return builderJson.components[key];
	        })
	    }, {
	        type: 'bar',
	        stack: 'component',
	        silent: true,
	        xAxisIndex: 1,
	        yAxisIndex: 1,
	        itemStyle: {
	            normal: {
	                color: '#eee'
	            }
	        },
	        data: Object.keys(builderJson.components).map(function (key) {
	            return builderJson.all - builderJson.components[key];
	        })
	    }, {
	        type: 'pie',
	        radius: [0, '30%'],
	        center: ['75%', '25%'],
	        data: Object.keys(downloadJson).map(function (key) {
	            return {
	                name: key.replace('.js', ''),
	                value: downloadJson[key]
	            }
	        })
	    }, {
	        type: 'pie',
	        radius: [0, '30%'],
	        center: ['75%', '75%'],
	        data: Object.keys(themeJson).map(function (key) {
	            return {
	                name: key.replace('.js', ''),
	                value: themeJson[key]
	            }
	        })
	    }]
	};
	if (option && typeof option === "object") {
	    myChart.setOption(option, true);
	}
}
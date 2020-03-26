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
				 var nowSeason = Math.floor( ( nowMonth % 3 == 0 ? ( nowMonth / 3 ) : ( nowMonth / 3 + 1 ) ) );
				 for(var i=nowSeason;i>=1;i--){
					 html+="<option value='"+i+"'>第"+i+"季度</option>";
				 }
			 }else{
				 for(var i=1;i<=4;i++){
					 html+="<option value='"+i+"'>第"+i+"季度</option>";
				 }
			 }
			 $("#statisticsSeason").html(html);
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
		var url="/merchant/statistics/export/season";
		var year=$("#statisticsYear").val();
		var season=$("#statisticsSeason").val()
		if(year!=null&&year!=''){
			url+="?year="+year;
		}
		if(year!=''&&season!=null&&season!=''){
			url+="&season="+season;
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
	var season=$("#statisticsSeason").val();
	if(year!=null&&year!=''&&season!=null&&season!=''){
		 $.ajax({
				async : true, //是否异步  
				cache : false, //是否使用缓存  
				type : 'post', //请求方式,post  
				data : {
					year : year,
					season: season
				},
				dataType : "json", //数据传输格式  
				url : '/merchant/statistics/saleSeason', //请求链接  
				success : function(data) {
					if(data.status ==1) {
						var totalMoneyArray= new Array();
						var totalNumArray=new Array();
						var statisticsMonthArray=new Array();
						$(data.data).each(function(index,item){
							statisticsMonthArray[index]=item.month+"月";
							totalMoneyArray[index]=item.totalMoney;
							totalNumArray[index]=item.totalNum;
						});
						init(totalMoneyArray,totalNumArray,statisticsMonthArray,year,season);
					}
				},
				error : function(XMLHttpRequest, data) {
					console.log(data.msg);
				}
		});
	}
}

function init(totalMoneyArray,totalNumArray,statisticsMonthArray,year,season){
	var dom = document.getElementById("container");
	var myChart = echarts.init(dom);
	var app = {};
	option = null;
	app.title = '季度销售统计';

	option = {
	    title: {
	        text: '季度销售统计',
	        subtext: year+"年第"+season+"季度"
	    },
	    tooltip: {
	        trigger: 'axis',
	        axisPointer: {
	            type: 'shadow'
	        }
	    },
	    legend: {
	        data: ['销售额', '销售量']
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    xAxis: {
	        type: 'value',
	        boundaryGap: [0, 0.01]
	    },
	    yAxis: {
	        type: 'category',
	        data: statisticsMonthArray
	    },
	    series: [
	        {
	            name: '销售额',
	            type: 'bar',
	            data: totalMoneyArray
	        },
	        {
	            name: '销售量',
	            type: 'bar',
	            data: totalNumArray
	        }
	    ]
	};
	;
	if (option && typeof option === "object") {
	    myChart.setOption(option, true);
	}
}
/**
 * 分页插件
 */
$(function(){
	$(".nav-item-box").click(function(){
		$(".nav-item-box").each(function(index,item){
			$(item).css("color", "");
			$(item).css("border-bottom","");
			$(item).removeClass("order-state");
		});
		$(this).css("color", "#FF0036");
		$(this).css("border-bottom","1px solid #FF0036");
		var state=$(this).find("input[type=hidden]").val();
		var url="/shop/order/list";
		if(state!=0){
			url+="?state="+state;
		}
		window.location.href=url;
	});
	
	$(".refund").click(function(){
		var orderNumber=$(this).attr("title");
		var state=$("#order_state").val();
		ds.dialog.confirm("确定要退款？",function(){
			$.ajax({
				async : true, //是否异步  
				cache : false, //是否使用缓存  
				type : 'post', //请求方式,post  
				data : {
					number:orderNumber
				},
				dataType : "json", //数据传输格式  
				url : '/shop/order/refund?v=' + new Date().getTime(), //请求链接  
				success : function(data) {
					if(data.status==1){//表示登录成功，进行跳转
						var url="/shop/order/list";
						if(state!=''){
							url+="?state="+state;
						}
						window.location.href=url;
					}
				},
				error : function(XMLHttpRequest, data) {
					console.log(data.msg);
				}
			});
		},function(){
			//alert("逗比，为什么不回到首页？")
		});
	});
	
	$(".finish").click(function(){
		var orderNumber=$(this).attr("title");
		var state=$("#order_state").val();
		ds.dialog.confirm("确认收货？",function(){
			$.ajax({
				async : true, //是否异步  
				cache : false, //是否使用缓存  
				type : 'post', //请求方式,post  
				data : {
					number:orderNumber
				},
				dataType : "json", //数据传输格式  
				url : '/shop/order/finish?v=' + new Date().getTime(), //请求链接  
				success : function(data) {
					if(data.status==1){//表示登录成功，进行跳转
						var url="/shop/order/list";
						if(state!=''){
							url+="?state="+state;
						}
						window.location.href=url;
					}
				},
				error : function(XMLHttpRequest, data) {
					console.log(data.msg);
				}
			});
		},function(){
			//alert("逗比，为什么不回到首页？")
		});
	});
	
	
	$(".delete-order").click(function(){
		var orderNumber=$(this).attr("title");
		var state=$("#order_state").val();
		ds.dialog.confirm("确认删除该订单？",function(){
			$.ajax({
				async : true, //是否异步  
				cache : false, //是否使用缓存  
				type : 'post', //请求方式,post  
				data : {
					number:orderNumber
				},
				dataType : "json", //数据传输格式  
				url : '/shop/order/delete?v=' + new Date().getTime(), //请求链接  
				success : function(data) {
					if(data.status==1){//表示登录成功，进行跳转
						var url="/shop/order/list";
						if(state!=''){
							url+="?state="+state;
						}
						window.location.href=url;
					}
				},
				error : function(XMLHttpRequest, data) {
					console.log(data.msg);
				}
			});
		},function(){
			//alert("逗比，为什么不回到首页？")
		});
	});
	$(".delivery-info").click(function(){
		var orderId=$(this).attr("order-id");
		var state=$("#order_state").val();
		
	});
});

/**
 * 商品评价
 */
$(".order_evaluation").click(function(){
	var orderId=$(this).attr("title");
	window.location.href="/shop/evaluation/list";
});
$(function(){
	function getParameter(name) { 
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); 
		var r = window.location.search.substr(1).match(reg); 
		if (r!=null) return unescape(r[2]); return null;
	}

	//init
	$(function(){
		var totalRecords = $("#total_record").val();
		var totalPage = ((totalRecords-1)/50)+1;
		var pageNo = getParameter('pageIndex');
		if(!pageNo){
			pageNo = 1;
		}
		//初始化分页控件
		//有些参数是可选的，比如lang，若不传有默认值
		kkpager.init({
			pno : pageNo,
			//总页码
			total : totalPage,
			//总数据条数
			totalRecords : totalRecords,
			//链接前部
			hrefFormer : '/shop/order/list',
			//链接尾部
			hrefLatter : '',
			getLink : function(n){
				var url=this.hrefFormer + this.hrefLatter + "?pageIndex="+n;
				var state=$("#order_state").val();
				if(state!=0){
					url+=("&state="+state);
				}
				return url;
			},
			lang : {
				prePageText : '上一页',
				nextPageText : '下一页',
				totalPageBeforeText : '共',
				totalPageAfterText : '页',
				totalRecordsAfterText : '条数据',
				gopageBeforeText : '转到',
				gopageButtonOkText : '确定',
				gopageAfterText : '页',
				buttonTipBeforeText : '第',
				buttonTipAfterText : '页'
			}
		});
		//生成
		kkpager.generPageHtml();
	});
});
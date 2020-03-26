$(document).ready(function(){
	$(".search-keyword").focus(function(){
		$(this).attr("placeholder","");
		$(this).css("color","black");
	});
	$(".search-keyword").blur(function(){
		if($(this).val()==''||$(this).val()==null){
			$(this).css("color","#BBB");
			$(this).attr("placeholder",$("#df_key_word").val());
		}
	});
	$(".top-address").mouseenter(function(){
		$(this).css("color","#FF0036");
	});
	$(".top-address").mouseleave(function(){
		$(this).css("color","black");
	});
	
	/**
	 * 跳转到我的订单页面
	 */
	$(".order-my-nav").click(function(){
		jump("/shop/order/list");
	});
	
	/**
	 * 跳转到我的评论列表
	 */
	$(".evaluation-my-nav").click(function(){
		jump("/shop/evaluation/notEvaluation/list");
	});
	
	/**
	 * 跳转到我的足迹
	 */
	$(".browse-my-nav").click(function(){
		jump("/shop/browseHistory/list");
	});
	
	/**
	 * 跳转到我的收藏页面
	 */
	$(".collection-my-nav").click(function(){
		jump("/shop/collection/product/list");
	});
	
	$(".user-register").click(function(){
		window.location.href="/sso/shop/register";
	});
	
	function jump(url){
		var sessionId=$.cookie("shop_session_id");
		if (typeof(sessionId) == "undefined"||sessionId==null||sessionId=='') { //表示没有登录
			$('.theme-popover-mask').fadeIn(100);
			$('.theme-popover').slideDown(200);
			$(document).bind('mousewheel', function(event, delta) { return false; });
		}else{
			 $.ajax({
					async : true, //是否异步  
					cache : false, //是否使用缓存  
					type : 'post', //请求方式,post  
					data : {
						token:sessionId
					},
					dataType : "json", //数据传输格式  
					url : '/sso/shop/getToken?v=' + new Date().getTime(), //请求链接  
					success : function(data) {
						if(data.status==1){//表示登录成功，进行跳转
							window.location.href=url;
						}else{
							//弹出框
							$('.theme-popover-mask').fadeIn(100);
							$('.theme-popover').slideDown(200);
							$(document).bind('mousewheel', function(event, delta) { return false; });
						}
					},
					error : function(XMLHttpRequest, data) {
						console.log(data.msg);
					}
			});
		}  
	}
	
	$(".search-category-product").click(function(){
		$(this).removeClass("clear-bg");
		$(".search-category-shop").addClass("clear-bg");
		$("#search-category-type").val(1);
	});
	$(".search-category-shop").click(function(){
		$(this).removeClass("clear-bg");
		$("#search-category-type").val(2);
		$(".search-category-product").addClass("clear-bg");
	});
});
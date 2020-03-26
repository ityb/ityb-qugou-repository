//打开弹出框的低吗
$(function(){
	$('.delivery-info').click(function(){
		$('.delivery-theme-popover-mask').fadeIn(100);
		$('.delivery-theme-popover').slideDown(200);
		$(document).bind('mousewheel', function(event, delta) { return false; });
		var orderId=$(this).attr("order-id");
		var deliveryWay=$(this).attr("delivery-way");
		$.ajax({
			async : true, //是否异步  
			cache : false, //是否使用缓存  
			type : 'post', //请求方式,post  
			data : {
				orderId:orderId
			},
			dataType : "json", //数据传输格式  
			url : '/shop/order/getOrderDeliveryInfo?v=' + new Date().getTime(), //请求链接  
			success : function(data) {
				if(data.status==1){//表示登录成功，进行跳转
					$(".delivery-theme-popover input[name=deliveryName]").val(data.data.deliveryName);
					$(".delivery-theme-popover input[name=deliveryPhone]").val(data.data.deliveryPhone);
					if(deliveryWay==1){//表示上门自提
						$(".delivery-theme-popover textarea[name=deliveryInfo]").val(data.data.deliveryInfo);
					}else if(deliveryWay==2){//同城配送
						$(".delivery-theme-popover textarea[name=deliveryInfo]").val(data.data.deliveryPosition);
					}
				}
			},
			error : function(XMLHttpRequest, data) {
				console.log(data.msg);
			}
		});
	});
	//关闭弹出层
	$('.delivery-theme-poptit .delivery-close').click(function(){
		$('.delivery-theme-popover-mask').fadeOut(100);
		$('.delivery-theme-popover').slideUp(200);
		$(document).unbind('mousewheel');
	});
	$(".delivery-button-box").click(function(){
		$('.delivery-theme-poptit .delivery-close').click();
	});
});
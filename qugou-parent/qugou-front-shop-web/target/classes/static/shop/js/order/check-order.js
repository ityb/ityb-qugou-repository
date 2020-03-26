$(document ).on( "click", "input[name=address]", function() {
	var $parent=$(this).parent().parent();
	var address=$parent.find(".check-address").text();
	var customerPhone=$parent.find(".check-phone").text();
	var customerUser=$parent.find(".check-user").text();
	$(".pay-address").html(address);
	$(".pay-phone").html(customerPhone);
	$(".pay-user").html(customerUser);
}); 
$(function(){
	$("input[name=orderItem]").click(function(){
		var $parent=$(this).parent().parent().parent();
		if($(this).is(":checked")){
			$parent.css("background-color","");
		}else{
			$parent.css("background-color","#F7F7FD");
		}
	});
	$(".pay-button-box").click(function(){
		var isAddress=false;
		var isOrderItem=false;
		$("input[name=address]").each(function(index,item){
			if($(item).is(":checked")){
				isAddress=true;
			}
		});
		if(!isAddress){
			ds.dialog.alert("请选择收货地址",function(){},"info.png");
			return;
		}
		$("input[name=orderItem]").each(function(index,item){
			if($(item).is(":checked")){
				isOrderItem=true;
			}
		});
		if(!isOrderItem){
			ds.dialog.alert("请至少选择一个订单项",function(){},"info.png");
			return;
		}
		
		//获取订单项，拼接成josn字符串
		//获取地址
		var $addressParent= $("input[name='address']:checked").parent().parent();
		var address=$addressParent.find(".check-address").text();
		var customerPhone=$addressParent.find(".check-phone").text();
		var customerUser=$addressParent.find(".check-user").text();
		var tradeWay=$("input[name=tradeWay]:checked").val();
		var deliveryWay=$("input[name=deliveryWay]:checked").val();
		var userId=$("#check-user-id").val();
		var itemJson="[";
		//获取已经选择的的复选框
		$("input[name=orderItem]").each(function(index,item){
			if($(item).is(":checked")){
				//获取商家ID
				var merchantId=$(item).parent().parent().find(".check-merchant-name-box input[type=hidden]").val();
				//获取订单项总计
				var total=$(item).parent().parent().parent().find(".sub-total").text();
				itemJson+="{\"order\":{";
				itemJson+="\"merchantId\":"+"\""+merchantId+"\""+",";
				itemJson+="\"total\":"+total+",";
				itemJson+="\"tradeWay\":"+tradeWay+",";
				itemJson+="\"deliveryWay\":"+deliveryWay+",";
				itemJson+="\"customerPhone\":"+"\""+customerPhone+"\""+",";
				itemJson+="\"deliveryAddress\":"+"\""+address+"\""+",";
				itemJson+="\"customerName\":"+"\""+customerUser+"\""+",";
				itemJson+="\"creater\":"+"\""+userId+"\"";
				itemJson+="}";
				itemJson+=",\"orderItemList\":[";
				$(item).parent().parent().parent().find(".check-order-item-show-box").each(function(index,productItem){
					itemJson+="{"
					var specificationId=$(productItem).find(".order-specification-item-id").val();
					var buyNumber=$(productItem).find(".order-item-buy-number").text();
					var price=$(productItem).find(".order-item-price").text();
					var subTotal=new Number(buyNumber)*new Number(price);
					itemJson+="\"productSpecificationId\":"+"\""+specificationId+"\""+",";
					itemJson+="\"buyNum\":"+buyNumber+",";
					itemJson+="\"creater\":"+"\""+userId+"\""+",";
					itemJson+="\"subtotal\":"+subTotal+"";
					itemJson+="},";
				});
				itemJson=itemJson.substring(0,itemJson.length-1);
				itemJson+="]";
				itemJson+="},"
			}
		});
		itemJson=itemJson.substring(0,itemJson.length-1);
		itemJson+="]";
		if(itemJson!=null||itemJson!="["){
			 $.ajax({
					async : true, //是否异步  
					cache : false, //是否使用缓存  
					type : 'post', //请求方式,post  
					data : itemJson,
					contentType:'application/json;charset=UTF-8',//关键是要加上这行
					dataType : "json", //数据传输格式  
					url : '/shop/order/generate?v=' + new Date().getTime(), //请求链接  
					success : function(data) {
						if(data.status==1){
							//进行跳转（生成订单）将得到的数据
							if(tradeWay=="2"){//表示是在线支付，跳转到支付界面
								window.location.href="/shop/order/pay?orderNumbers="+data.data;
							}else{//表示是货到付款自己跳转到成功页面
								window.location.href="/shop/order/orderSuccess";
							}
						}else{
							ds.dialog.alert(data.msg,function(){},"info.png");
						}
					},
					error : function(XMLHttpRequest, data) {
						console.log(data.msg);
					}
		     });
		}
	});
});

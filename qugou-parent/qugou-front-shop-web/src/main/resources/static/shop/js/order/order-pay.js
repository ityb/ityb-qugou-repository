$(function(){
	$(".pay-button").click(function(){
		var payBankCode=$("input[name=payBankCode]:checked").val();
		window.location.href="/shop/order/doPay?payBankCode="+payBankCode+"&orderNumbers="+$("#orderNumbers").val();
	});
});
$(function(){
	$("input[name=productSpecification]").click(function(){
		var $li=$(this).parent();
		var price=$li.find("input[name=productSpecification_price]").val();
		$(".price-number").text(new Number(price).toFixed(2));
		var weight=$li.find("input[name=productSpecification_weight]").val();
		$("#product_weight").text((weight+"kg"));
		var stock=$li.find("input[name=productSpecification_stock]").val();
		$("#product_stock span").text(stock);
		$('#product_buy_number').val(1);
	});
	$(".search-button").click(function(){
		var shopId=$("#shop-nav-shop-id").val();
		if(shopId==null||shopId==''){
			return;
		}
		var keyWord=$("#product-detail-keyword").val();
		var url="/shop/store/detail?id="+shopId;
		if(keyWord!=null&&keyWord!=''){
			url+=("&keyWord="+keyWord);
		}
		window.location.href=url;
	});
	$('#product_buy_number').bind('input propertychange', function() {
		var reg=/^[0-9]*[1-9][0-9]*$/;
		var stock=$("#product_stock span").text();
		if(!reg.test($(this).val())){
			$(this).val(1);
		}else if($(this).val()=='0'){
			$(this).val(1);
		}else if(new Number($(this).val())>new Number(stock)){
			$(this).val(stock);
		}
	}); 
	
	$("#" +$('#product_buy_number').attr('id') + "r").click(function(){
		var value=$('#product_buy_number').val();
		var stock=$("#product_stock span").text();
		if(new Number(value)>=new Number(stock)){
			$('#product_buy_number').val(stock);
		}
	});
});
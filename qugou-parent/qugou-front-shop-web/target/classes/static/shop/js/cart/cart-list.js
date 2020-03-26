$(function(){
	$(".cart-now-box,.cart-now-box-1").click(function(){
		$(this).css("border-bottom","1px solid #FF0036");
		$(this).css("color","#FF0036");
		$(".cart-history-box").css("border-bottom","none");
		$(".cart-history-box").css("color","black");
		window.location.href="/shop/cart?cartType=1";
	});
	$(".cart-history-box,.cart-history-box-1").click(function(){
		$(this).css("border-bottom","1px solid #FF0036");
		$(this).css("color","#FF0036");
		$(".cart-now-box").css("border-bottom","none");
		$(".cart-now-box").css("color","black");
		window.location.href="/shop/cart?cartType=2";
	});
	$(".checkbox-single").click(function(){
		var $product=$(this).parent().parent();
		var $subTotal=$product.find(".price-font-sub");
		var $total=$("#product-total");
		var $buyNumber=$("#product-number");
		if($(this).is(':checked')){
			$product.css("background-color","#fff4e8");
			$(".select-all").attr("checked", true);
			var total=new Number($subTotal.text())+new Number($total.text());
			$total.text(new Number(total).toFixed(2));
			$buyNumber.text(new Number($buyNumber.text())+1);
		}else{
			//需要去判断是否有被选中
			var count=0;
			$(".checkbox-single").each(function(index,item){
				if($(item).is(':checked')){
					count++;
				}
			});
			if(count==0){
				$(".select-all").attr("checked", false);
			}
			var total=new Number($total.text())-new Number($subTotal.text());
			$total.text(new Number(total).toFixed(2));
			$buyNumber.text(new Number($buyNumber.text())-1);
			$product.css("background-color","");
		}
	});
	$("#select-all-1").click(function(){
		var $total=$("#product-total");
		var $buyNumber=$("#product-number");
		if($(this).is(":checked")){//全选中
			var count=0;
			var total=0;
			$(".checkbox-single").each(function(index,item){
				$(item).attr("checked", true);
				var $product=$(item).parent().parent();
				var $subTotal=$product.find(".price-font-sub");
				total+=new Number($subTotal.text());
				count++;
			});
			$(".cart-product-box").each(function(index,item){
				$(item).css("background-color","#fff4e8");
			});
			$("#select-all-2").attr("checked", true);
			$buyNumber.text(count);
			$total.text(new Number(total).toFixed(2));
		}else{//全不选
			$(".checkbox-single").each(function(index,item){
				$(item).attr("checked", false);
			});
			$(".cart-product-box").each(function(index,item){
				$(item).css("background-color","");
			});
			$("#select-all-2").attr("checked", false);
			$buyNumber.text(0);
			$total.text(new Number(0).toFixed(2));
		}
	});
	$("#select-all-2").click(function(){
		var $total=$("#product-total");
		var $buyNumber=$("#product-number");
		if($(this).is(":checked")){
			var count=0;
			var total=0;
			$(".checkbox-single").each(function(index,item){
				$(item).attr("checked", true);
				var $product=$(item).parent().parent();
				var $subTotal=$product.find(".price-font-sub");
				total+=new Number($subTotal.text());
				count++;
			});
			$(".cart-product-box").each(function(index,item){
				$(item).css("background-color","#fff4e8");
			});
			$("#select-all-1").attr("checked", true);
			$buyNumber.text(count);
			$total.text(new Number(total).toFixed(2));
		}else{
			$(".checkbox-single").each(function(index,item){
				$(item).attr("checked", false);
			});
			$(".cart-product-box").each(function(index,item){
				$(item).css("background-color","");
			});
			$("#select-all-1").attr("checked", false);
			$buyNumber.text(0);
			$total.text(new Number(0).toFixed(2));
		}
	});
	$(".cart-product-delete").click(function(){
		var cartType=$("#cart_type").val();
		var cartId=$(this).attr("name");
		if(cartId==null||cartId==''){
			return;
		}
		ds.dialog.confirm("是否删除商品？",function(){
			//进行ajax提交
			 $.ajax({
					async : true, //是否异步  
					cache : false, //是否使用缓存  
					type : 'post', //请求方式,post  
					data : {
						id:cartId
					},
					dataType : "json", //数据传输格式  
					url : '/shop/cart/deleteProduct?v=' + new Date().getTime(), //请求链接  
					success : function(data) {
						if(data.status==1){//表示登录成功，进行跳转
							window.location.href="/shop/cart?cartType="+cartType
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
	$(".cart-add-collection").click(function(){
		var specificationId=$(this).attr("name");
		if(specificationId==null||specificationId==''){
			return;
		}
		//进行ajax提交
		$.ajax({
			async : true, //是否异步  
			cache : false, //是否使用缓存  
			type : 'post', //请求方式,post  
			data : {
				collectionId:specificationId,
				type:1
			},
			dataType : "json", //数据传输格式  
			url : '/shop/collection/add?v=' + new Date().getTime(), //请求链接  
			success : function(data) {
				if(data.status==1){
					ds.dialog.alert("收藏成功",function(){},"info.png");
				}else{
					ds.dialog.alert("收藏失败",function(){},"info.png");
				}
			},
			error : function(XMLHttpRequest, data) {
				console.log(data.msg);
			}
		});
	});
	$(".cart-delete-select").click(function(){
		var cartType=$("#cart_type").val();
		var ids="";
		$(".checkbox-single:checked").each(function(index,item){
			ids+=$(item).val()+",";
		});
		if(ids==null||ids==''){
			ds.dialog.alert("至少选中一种商品",function(){},"info.png");
			return ;
		}
		ids=ids.substring(0,ids.length-1);
		ds.dialog.confirm("是否删除选中的商品？",function(){
			//进行ajax提交
			 $.ajax({
					async : true, //是否异步  
					cache : false, //是否使用缓存  
					type : 'post', //请求方式,post  
					data : {
						id:ids
					},
					dataType : "json", //数据传输格式  
					url : '/shop/cart/deleteProduct?v=' + new Date().getTime(), //请求链接  
					success : function(data) {
						if(data.status==1){//表示登录成功，进行跳转
							window.location.href="/shop/cart?cartType="+cartType
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
	
	$(".cart-add-collection-select").click(function(){
		var ids="";
		$(".checkbox-single:checked").each(function(index,item){
			ids+=$(item).parent().parent().find(".cart-add-collection").attr("name")+",";
		});
		if(ids==null||ids==''){
			ds.dialog.alert("至少选中一种商品",function(){},"info.png");
			return ;
		}
		ids=ids.substring(0,ids.length-1);
		ds.dialog.confirm("是否收藏选中的商品？",function(){
			//进行ajax提交
			$.ajax({
				async : true, //是否异步  
				cache : false, //是否使用缓存  
				type : 'post', //请求方式,post  
				data : {
					collectionId:ids,
					type:1
				},
				dataType : "json", //数据传输格式  
				url : '/shop/collection/add?v=' + new Date().getTime(), //请求链接  
				success : function(data) {
					if(data.status==1){//表示登录成功，进行跳转
						ds.dialog.alert("收藏成功",function(){},"info.png");
					}else{
						ds.dialog.alert("收藏失败",function(){},"info.png");
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
	
	$('.buy-number-input').bind('input propertychange', function() {
		var reg=/^[0-9]*[1-9][0-9]*$/;
		var stock=$(this).parent().parent().parent().find(".stock-span").text();
		if(!reg.test($(this).val())){
			$(this).val(1);
		}else if($(this).val()=='0'){
			$(this).val(1);
		}else if(new Number($(this).val())>new Number(stock)){
			$(this).val(stock);
		}
		var price=$(this).parent().parent().parent().find(".price-span").text();
		var subTotal=$(this).parent().parent().parent().find(".price-font-sub");
		var inputChcked=$(this).parent().parent().parent().find(".checkbox-single");
		var oldBuyNumber=$(this).attr("data-old-value");
		var id=inputChcked.val();
		subTotal.html((new Number(price)*new Number($(this).val())).toFixed(2));
		if(inputChcked.is(':checked')){
			var totalPrice=$("#product-total").text();
			$("#product-total").text((new Number(totalPrice)+(new Number(price)*
					(new Number($(this).val())-new Number(oldBuyNumber)))).toFixed(2));
		}
		 $.ajax({
				async : true, //是否异步  
				cache : false, //是否使用缓存  
				type : 'post', //请求方式,post  
				data : {
					id:id,
					buyNumber:$(this).val()
				},
				dataType : "json", //数据传输格式  
				url : '/shop/cart/updateBuyNumber?v=' + new Date().getTime(), //请求链接  
				success : function(data) {
					/*if(data.status==1){//表示登录成功，进行跳转
						window.location.href="/shop/cart?cartType="+cartType
					}*/
				},
				error : function(XMLHttpRequest, data) {
					console.log(data.msg);
				}
	     });
		 $(this).attr("data-old-value",$(this).val());
	}); 
	
	$(".buy-number-input").each(function(index,item){
		var price=$(item).parent().parent().parent().find(".price-span").text();
		var subTotal=$(item).parent().parent().parent().find(".price-font-sub");
		var inputChcked=$(item).parent().parent().parent().find(".checkbox-single");
		var stock=$(item).parent().parent().parent().find(".stock-span").text();
		var id=inputChcked.val();
		$("#" + $(item).attr('id') + "l").click(function(){//绑定减少事件
			var $input=$(item).parent().find(".buy-number-input");
			var oldBuyNumber=new Number($input.attr("data-old-value"));
			var buyNumber=$(item).val();
			if(new Number(stock)<=oldBuyNumber){
				$input.val(stock);
				$input.attr("data-old-value",stock);
				return;
			}
			$input.attr("data-old-value",oldBuyNumber-1);
			subTotal.html((new Number(price)*new Number(buyNumber)).toFixed(2));
			//需要判断是否被选中
			if(inputChcked.is(':checked')){
				var totalPrice=$("#product-total").text();
				if(new Number(totalPrice)>new Number(price)){
					$("#product-total").text((new Number(totalPrice)-new Number(price)).toFixed(2));
				}
			}
			 $.ajax({
					async : true, //是否异步  
					cache : false, //是否使用缓存  
					type : 'post', //请求方式,post  
					data : {
						id:id,
						buyNumber:buyNumber
					},
					dataType : "json", //数据传输格式  
					url : '/shop/cart/updateBuyNumber?v=' + new Date().getTime(), //请求链接  
					success : function(data) {
						/*if(data.status==1){//表示登录成功，进行跳转
							window.location.href="/shop/cart?cartType="+cartType
						}*/
					},
					error : function(XMLHttpRequest, data) {
						console.log(data.msg);
					}
		     });
			
		});
		$("#" + $(item).attr('id') + "r").click(function(){//绑定添加事件
			var $input=$(item).parent().find(".buy-number-input");
			var oldBuyNumber=new Number($input.attr("data-old-value"));
			var buyNumber=$(item).val();
			if(new Number(stock)<=oldBuyNumber){
				$input.text(stock);
				$input.attr("data-old-value",stock);
				return;
			}
			$input.attr("data-old-value",oldBuyNumber+1);
			subTotal.html((new Number(price)*new Number(buyNumber)).toFixed(2));
			//需要判断是否被选中
			if(inputChcked.is(':checked')){
				var totalPrice=$("#product-total").text();
				if(new Number(totalPrice)>=new Number(price)){
					$("#product-total").text((new Number(totalPrice)+new Number(price)).toFixed(2));
				}
			}
			 $.ajax({
					async : true, //是否异步  
					cache : false, //是否使用缓存  
					type : 'post', //请求方式,post  
					data : {
						id:id,
						buyNumber:buyNumber
					},
					dataType : "json", //数据传输格式  
					url : '/shop/cart/updateBuyNumber?v=' + new Date().getTime(), //请求链接  
					success : function(data) {
						/*if(data.status==1){//表示登录成功，进行跳转
							window.location.href="/shop/cart?cartType="+cartType
						}*/
					},
					error : function(XMLHttpRequest, data) {
						console.log(data.msg);
					}
		     });
		});
	});
	$(".js-button-box").click(function(){
		var ids="";
		$(".checkbox-single:checked").each(function(index,item){
			ids+=$(item).val()+",";
		});
		if(ids==null||ids==''){
			ds.dialog.alert("至少选中一种商品",function(){},"info.png");
			return ;
		}
		ids=ids.substring(0,ids.length-1);
		window.location.href="/shop/order/checkOrder?cartId="+ids;
	});
});
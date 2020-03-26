 $(function() {
	var offset = $("#icon-cart").offset();
	$(".addcart").click(function(event) {
		var sessionId=$.cookie("shop_session_id");
		var img = $(this).parent().parent().children('img').attr('src');//获取当前点击图片链接
		var specificationId=$(this).attr("data-id");
		var buyNumber=$(this).parent().find(".add-sub-show input").val();
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
						    var flyer = $('<img class="flyer-img" src="' + img + '">');//抛物体对象
						    flyer.fly({
						        start: {
						            left: event.pageX, //抛物体起点横坐标
						            top: event.pageY-$(document).scrollTop()////抛物体起点纵坐标
						        },
						        end: {
						            left: offset.left+10, //抛物体终点横坐标
						            top: 330+70//抛物体终点纵坐标,将坐标固定即可
//						            top: offset.top+70//抛物体终点纵坐标
						        },
						        onEnd: function() {
						            $("#tip").show().animate({width: '100px'}, 300).fadeOut(500);//成功加入购物车动画效果
						            this.destory();//销毁抛物体
						        }
						    });
						  //这里需要进行添加到购物车操作
					        $.ajax({
								async : true, //是否异步  
								cache : false, //是否使用缓存  
								type : 'post', //请求方式,post  
								data : {
									specificationId:specificationId,
									buyNumber:buyNumber,
									cartType:1
								},
								dataType : "json", //数据传输格式  
								url : '/shop/cart/add?v=' + new Date().getTime(), //请求链接  
								success : function(data) {
									if(data.status==1){//表示登录成功，进行跳转
										
									}else{
										
									}
								},
								error : function(XMLHttpRequest, data) {
									console.log(data.msg);
								} 
					        });
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
	});
	$('.add-sub-show input').bind('input propertychange', function() {
		var reg=/^[0-9]*[1-9][0-9]*$/;
		if(!reg.test($(this).val())){
			$(this).val(1);
		}else if($(this).val()=='0'){
			$(this).val(1);
		}
	}); 
});
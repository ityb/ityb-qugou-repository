/**
 * 点击添加购物车事件
 */
$(function() {
    var offset = $("#icon-cart").offset();
    $(".product-cart-box").click(function(event) {
    	var img =$(this).parent().parent().find(".product-img-box img").attr("src");//获取当前点击图片链接
    	var specificationId=$(this).parent().find(".product-price-min").attr("id");
    	var buyNumber=$(this).parent().find(".product-price-min").val();
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
							//登录成功则进行添加购物车操作
					    	var flyer = $('<img class="flyer-img" src="' + img + '">');//抛物体对象
					        flyer.fly({
					            start: {
					                left: event.pageX, //抛物体起点横坐标
					                top: event.pageY-$(document).scrollTop()////抛物体起点纵坐标
					            },
					            end: {
					                left: offset.left+10, //抛物体终点横坐标
					                top:330+70//抛物体终点纵坐标
					            },
					            onEnd: function() {
					                $("#tip").show().animate({width: '100px'}, 300).fadeOut(500);//成功加入购物车动画效果
					                this.destory();//销毁抛物体
					            }
					        });
					        /*
					    	 * 进行添加到服务端
					    	 */
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
});

$(function(){
	$(".price-font-box").click(function(){
		var $up_img=$(".up-box img");
		var $down_img=$(".down-box img");
		if($up_img.attr("alt")==1&&$down_img.attr("alt")==1){
			//设置为升序
			$up_img.attr("src","/shop/images/search/up.png");
			$up_img.attr("alt",2);
			$("#price-state").val(1);
		}else if($up_img.attr("alt")==1&&$down_img.attr("alt")==2){
			//设置为升序
			$up_img.attr("src","/shop/images/search/up.png");
			$up_img.attr("alt",2);
			$down_img.attr("alt",1);
			$down_img.attr("src","/shop/images/search/down-1.png");
			$("#price-state").val(1);
		}else if($up_img.attr("alt")==2&&$down_img.attr("alt")==1){
			$down_img.attr("src","/shop/images/search/down.png");
			$down_img.attr("alt",2);
			$up_img.attr("alt",1);
			$up_img.attr("src","/shop/images/search/up-1.png");
			//设置为降序
			$("#price-state").val(2);
		}else{
			$up_img.attr("src","/shop/images/search/up.png");
			$up_img.attr("alt",2);
			$up_img.attr("src","/shop/images/search/down.png");
			$up_img.attr("alt",1);
		}
		var keyWord=$(".search-keyword").val();
		var priceState=$("#price-state").val();
		window.location.href="/shop/search/product?keyWord="+keyWord+"&priceState="+priceState;
	});
	$(".up-box").click(function(){
		var $up_img=$(".up-box img");
		var $down_img=$(".down-box img");
		if($up_img.attr("alt")==1&&$down_img.attr("alt")==1){
			$up_img.attr("src","/shop/images/search/up.png");
			$up_img.attr("alt",2);
			//升序
			$("#price-state").val(1);
		}else if($up_img.attr("alt")==1&&$down_img.attr("alt")==2){
			$down_img.attr("src","/shop/images/search/down-1.png");
			$down_img.attr("alt",1);
			$up_img.attr("alt",2);
			$up_img.attr("src","/shop/images/search/up.png");
			$("#price-state").val(1);
		}
		var keyWord=$(".search-keyword").val();
		var priceState=$("#price-state").val();
		window.location.href="/shop/search/product?keyWord="+keyWord+"&priceState="+priceState;
	});
	$(".down-box").click(function(){
		var $up_img=$(".up-box img");
		var $down_img=$(".down-box img");
		if($up_img.attr("alt")==1&&$down_img.attr("alt")==1){
			$down_img.attr("src","/shop/images/search/down.png");
			$down_img.attr("alt",2);
			$("#price-state").val(2);
		}else if($up_img.attr("alt")==2&&$down_img.attr("alt")==1){
			$up_img.attr("src","/shop/images/search/up-1.png");
			$up_img.attr("alt",1);
			$down_img.attr("alt",2);
			$down_img.attr("src","/shop/images/search/down.png");
			$("#price-state").val(2);
		}
		var keyWord=$(".search-keyword").val();
		var priceState=$("#price-state").val();
		window.location.href="/shop/search/product?keyWord="+keyWord+"&priceState="+priceState;
	});
	$(".price-text-box input").focus(function(){
		$(".select-item-fw-button").css("display","block");
		$(this).css("border","1px solid #009688");
	});
	$(".price-text-box input").blur(function(){
		var  $price_input=$(".price-text-box input");
		var count=0;
		$price_input.each(function(){
			if($(this).val()==""){
				count++;
			}
		});
		if(count==$price_input.length){
			$(".select-item-fw-button").css("display","none");
		}
		$(this).css("border","1px solid #e2e2e2");
	});
	$("#price-clear-button").click(function(){
		$(".price-text-box input").val("");
		$(".select-item-fw-button").css("display","none");
	});
	$(".product-show-box").mouseenter(function(){
		$(this).css("box-shadow","0 0 10px #FF0036");
		$(this).css("-moz-box-shadow","0 0 10px #FF0036"); 
		$(this).css("-webkit-box-shadow","0 0 10px #FF0036"); 
	});
	$(".product-show-box").mouseleave(function(){
		$(this).css("box-shadow","none");
		$(this).css("-moz-box-shadow","none"); 
		$(this).css("-webkit-box-shadow","none"); 
	});
	$(".price-button-box").click(function(){
		var keyWord=$(".search-keyword").val();
		var priceState=$("#price-state").val();
		var priceLeft=$("#priceLeft").val();
		var priceRight=$("#priceRight").val();
		window.location.href="/shop/search/product?keyWord="+keyWord+"&priceState="+priceState+"&priceLeft="+priceLeft+"&priceRight="+priceRight;
	});
	$('.product-price-min').bind('input propertychange', function() {
		var reg=/^[0-9]*[1-9][0-9]*$/;
		if(!reg.test($(this).val())){
			$(this).val(1);
		}else if($(this).val()=='0'){
			$(this).val(1);
		}
	}); 
});
/**
 * 分页插件
 */
$(function(){
	function getParameter(name) { 
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); 
		var r = window.location.search.substr(1).match(reg); 
		if (r!=null) return unescape(r[2]); return null;
	}

	//init
	$(function(){
		var total_record=$("#total_record").text();
		var totalPage = ((total_record-1)/50)+1;
		var totalRecords = total_record;
		var pageNo = getParameter('pageNow');
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
			hrefFormer : '/shop/search/product',
			//链接尾部
			hrefLatter : '',
			getLink : function(n){
				var keyWord=$(".search-keyword").val();
				var priceState=$("#price-state").val();
				var priceLeft=$("#priceLeft").val();
				var priceRight=$("#priceRight").val();
				var url=this.hrefFormer + this.hrefLatter + "?pageNow="+n+"&keyWord="+keyWord+"&priceState="+priceState+"&priceLeft="+priceLeft+"&priceRight="+priceRight;
				var shopCategoryName=$("#shop_category_name").val();
				if(shopCategoryName!=null&&shopCategoryName!=''){
					url+=(url+"&shopCategoryName="+shopCategoryName);
				}
				var merchantCategoryName=$("#merchant_category_name").val();
				if(merchantCategoryName!=null&&merchantCategoryName!=''){
					url+=(url+"&merchantCategoryName="+merchantCategoryName);
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
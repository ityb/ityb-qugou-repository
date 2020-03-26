//打开弹出框的低吗
$(function(){
	$('.evaluation-button').click(function(){
		$("#evaluation-order-id").val($(this).attr("title"));
		$("#evaluation-specification-id").val($(this).attr("lang"));
		$('.evaluation-theme-popover-mask').fadeIn(100);
		$('.evaluation-theme-popover').slideDown(200);
		$(document).bind('mousewheel', function(event, delta) { return false; });
	});
	//关闭弹出层
	$('.evaluation-theme-poptit .evaluation-close').click(function(){
		$('.evaluation-theme-popover-mask').fadeOut(100);
		$('.evaluation-theme-popover').slideUp(200);
		$(document).unbind('mousewheel');
	});
	//显示分数
    $(".show_number li p").each(function(index, element) {
      var num=$(this).attr("tip");
      var www=num*2*16;//
      $(this).css("width",www);
      $(this).parent(".atar_Show").siblings("span").text(num+"分");
    });
    scoreFun($("#product_zl-pf"))
    scoreFun($("#product_ms-pf"))
    scoreFun($("#order_ps-pf"))
    scoreFun($("#starttwo"),{
          fen_d:22,//每一个a的宽度
          ScoreGrade:5//a的个数 10或者
    });
    
    /**
     * 评价图片上传功能
     */
    $("#upload-input").ajaxImageUpload({
        url: '/shop/evaluation/upload', //上传的服务器地址
        data: {},
        maxNum: 4, //允许上传图片数量
        zoom: true, //允许上传图片点击放大
        allowType: ["gif", "jpeg", "jpg", "bmp",'png'], //允许上传图片的类型
        maxSize :10, //允许上传图片的最大尺寸，单位M
        before: function () {
           // alert('上传前回调函数');
        },
        success:function(data){
            //alert('上传成功回调函数');
            console.log(data);
        },
        error:function (e) {
            //alert('上传失败回调函数');
            console.log(e);
        }
    });
    $(".evaluation-submit-button").click(function(){
    	var productSpecificationId=$("#evaluation-specification-id").val();
    	var orderId=$("#evaluation-order-id").val();
    	if(productSpecificationId==''||orderId==''){
    		$(".evaluation-error-tip").text("暂时不能评价");
    		return;
    	}
    	var qualityFraction=$("#product_zl-pf").find(".fenshu").text();
    	var descFraction=$("#product_ms-pf").find(".fenshu").text();
    	var deliveryFraction=$("#order_ps-pf").find(".fenshu").text();
    	if(qualityFraction==null||qualityFraction==''){
    		$(".evaluation-error-tip").text("您还没有对商品质量进行打分");
    		return;
    	}
    	if(descFraction==null||descFraction==''){
    		$(".evaluation-error-tip").text("您还没有对商品描述进行打分");
    		return;
    	}
    	if(deliveryFraction==null||deliveryFraction==''){
    		$(".evaluation-error-tip").text("您还没有对订单配送进行打分");
    		return;
    	}
    	var evaluationContent=$(".evaluation-content").val();
    	if(evaluationContent==null||evaluationContent==''){
    		$(".evaluation-error-tip").text("评价内容至少一个字");
    		return;
    	}
    	//获取图片Url
    	var evaluationPic="";
    	$(".image-show").each(function(index,item){
    		if($(item).attr("src")!=''){
    			evaluationPic+=$(item).attr("src")+",";
    		}
    	});
    	if(evaluationPic!=null&&evaluationPic!=''){
    		evaluationPic=evaluationPic.substring(0,evaluationPic.length-1);
    	}
	   	 $.ajax({
				async : true, //是否异步  
				cache : false, //是否使用缓存  
				type : 'post', //请求方式,post  
				data : {
					qualityFraction: qualityFraction,
					descFraction: descFraction,
					deliveryFraction: deliveryFraction,
					evaluationContent: evaluationContent,
					evaluationPic: evaluationPic,
					productSpecificationId: productSpecificationId,
					orderId: orderId
				},
				dataType : "json", //数据传输格式  
				url : '/shop/evaluation/add?v=' + new Date().getTime(), //请求链接  
				success : function(data) {
					if(data.status==1){//表示登录成功，进行跳转
						var url="/shop/evaluation/notEvaluation/list"
						var orderId=$("#evaluation_order_id").val();
						if(orderId!=null&&orderId!=''){
							url+="?orderId="+orderId;
						}
						window.location.href=url;
						$('.evaluation-theme-popover-mask').fadeOut(100);
						$('.evaluation-theme-popover').slideUp(200);
						$(document).unbind('mousewheel');
					}else{
						$(".evaluation-error-tip").text(data.msg);
					}
				},
				error : function(XMLHttpRequest, data) {
					console.log(data.msg);
				}
		});
    });
});

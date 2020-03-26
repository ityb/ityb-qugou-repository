$(function(){	
	//第一页的确定按钮
	$("#btn_part1").click(function(){						
		if(!verifyCheck._click()) return;
		//点击下一步去验证手机号码是否已经被注册了
		$.ajax({
			async : true, //是否异步  
			cache : false, //是否使用缓存  
			type : 'post', //请求方式,post  
			data : {
				verificationCode : $("#verifyNo").val(),
				userType : 2
			},
			dataType : "json", //数据传输格式  
			url : '/sso/shop/register/isEqVerificationCode?v=' + new Date().getTime(), //请求链接  
			success : function(data) {
				if(data.status==1){
					$(".part1").hide();
					$(".part2").show();
					/* $(".step li").eq(1).addClass("on");*/
				}else{
					$("#phone_v_error_show").text("验证码不正确");
				}
			},
			error : function(XMLHttpRequest, data) {
				console.log(data.msg);
			}
		});
	});
	//第二页的确定按钮
	$("#btn_part2").click(function(){			
		if(!verifyCheck._click()) return;
		$(".part2").hide();
		$(".part3").show();	
		$(".step li").eq(1).addClass("on");
	});	
	//第三页的确定按钮
	$("#btn_part3").click(function(){			
		if(!verifyCheck._click()) return;
		$("#merchant_register_form").submit();
		/*$(".part3").hide();
		$(".part4").show();
		$(".step li").eq(2).addClass("on");
		countdown({
			maxTime:10,
			ing:function(c){
				$("#times").text(c);
			},
			after:function(){
				window.location.href="my.html";		
			}
		});		*/
	});
	//点击区域选择
	$("#area_btn").click(function(){
		var province=$("select[name=province] option:checked").text();
		province=province==null?"":province;
		var city=$("select[name=city] option:checked").text();
		city=city==null?"":city;
		var area=$("select[name=area] option:checked").text();
		area=area==null?"":area;
		var town=$("select[name=town] option:checked").text();
		town=town==null?"":town;
		var detail=$("#address_detail").val();
		$("input[name=shopAddress]").val(province+city+area+town+detail);
	});
});
function showoutc(){$(".m-sPopBg,.m-sPopCon").show();}
function closeClause(){
	$(".m-sPopBg,.m-sPopCon").hide();		
}
function showoutc(){$(".m-sPopBg,.m-sPopCon").show();}
function closeClause(){
	$(".m-sPopBg,.m-sPopCon").hide();		
}
$("input[id=file_idcard]").change(function() {  
	$('#photoCover_idcard').val($(this).val());  
});  
$('input[id=file_hz]').change(function() {  
	$('#photoCover_hz').val($(this).val());  
});  

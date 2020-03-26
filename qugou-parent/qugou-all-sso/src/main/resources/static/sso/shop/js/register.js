$(function(){
	$("#userName").blur(function(){
		var userName=$(this).val();
		if(userName.length<6||userName.length>12){
			$("#tish").html("<font color='red'>请输入正确的用户名</font>");
			$("#flag").val(1);
		}else{
			 $.ajax({
					async : true, //是否异步  
					cache : false, //是否使用缓存  
					type : 'post', //请求方式,post  
					data : {
						userName : userName,
						userType : 3
					},
					dataType : "json", //数据传输格式  
					url : '/sso/user/isGetUser?v=' + new Date().getTime(), //请求链接  
					success : function(data) {
						if(data.status==1){
							$("#tish").html("<font color='red'>该用户名已经被注册</font>");
							$("#flag").val(1);
						}else{
							$("#tish").html("");
							$("#flag").val(0);
						}
					},
					error : function(XMLHttpRequest, data) {
						console.log(data.msg);
					}
				});
			$("#tish").html("");
			$("#flag").val(0);
		}
	});
	$("#password").blur(function(){
		var password=$(this).val();
		var reg = /^(?![^a-zA-Z]+$)(?!\D+$).{6,20}/;
		if(!reg.test(password)){
			$("#ts").html("<font color='red'>请输入正确的密码</font>");
			$("#flag").val(1);
		}else{
			$("#ts").html("");
			$("#flag").val(0);
		}
	});
	$("#repassword").blur(function(){
		var repassword=$(this).val();
		var password=$("#password").val();
		if(password!=repassword){
			$("#tishi").html("<font color='red'>两次密码不正确</font>");
			$("#flag").val(1);
		}else{
			$("#tishi").html("");
			$("#flag").val(0);
		}
	});
	$("#phone").blur(function(){
		var phone_reg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
		var phone=$(this).val();
		if(!phone_reg.test(phone)){
			$("#tss").html("<font color='red'>请输入正确的手机</font>");
			$("#flag").val(1);
		}else{
			//验证手机号码是否已经被注册了
			 $.ajax({
					async : true, //是否异步  
					cache : false, //是否使用缓存  
					type : 'post', //请求方式,post  
					data : {
						phone : phone,
						userType : 3
					},
					dataType : "json", //数据传输格式  
					url : '/sso/user/isGetUserInfo?v=' + new Date().getTime(), //请求链接  
					success : function(data) {
						if(data.status==1){
							$("#tss").html("<font color='red'>该手机号已经被绑定</font>");
							$("#flag").val(1);
						}else{
							$("#tss").html("");
							$("#flag").val(0);
						}
					},
					error : function(XMLHttpRequest, data) {
						console.log(data.msg);
					}
			});
			//进行判断是否已经被注册
			$("#tss").html("");
			$("#flag").val(0);
		}
	});
	//进行提交
	$("#submit").click(function(){
		if($("#flag").val()!=0){//表示有错误，不能进行提交
			return;
		}
		 var data = $("#register_form").serialize();
		 $.ajax({
				async : true, //是否异步  
				cache : false, //是否使用缓存  
				type : 'post', //请求方式,post  
				data : data,
				dataType : "json", //数据传输格式  
				url : '/sso/shop/register?v=' + new Date().getTime(), //请求链接  
				success : function(data) {
					if(data.status==1){
						  window.location.href="/sso/shop/register/success"; 
					}else{
						window.location.href="/sso/shop/register/error"; 
					}
				},
				error : function(XMLHttpRequest, data) {
					console.log(data.msg);
				}
		});
	});
});
function sendMessage(t) {
	var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
	var phone=document.form1.phone.value;
	if(!myreg.test(phone)){
		$("#tss").html("<font color='red'>请输入正确的手机</font>");
		// alert('请输入有效的手机号码！'); 
	  return false; 
	  } //
	document.form1.dynamic.disabled = true;
	for(i = 1; i <= t; i++) {
		window.setTimeout("update_p(" + i + "," + t + ")", i * 1000);
	}
	//在此进行验证码的发送
	 $.ajax({
			async : true, //是否异步  
			cache : false, //是否使用缓存  
			type : 'post', //请求方式,post  
			data : {phone:phone,userType:3},
			dataType : "json", //数据传输格式  
			url : '/sso/shop/register/sendVerificationCode?v=' + new Date().getTime(), //请求链接  
			success : function(data) {
				if(data.status!=1){
					$(".show_err").text("验证码发送失败，请检查手机号码是否正确");
					$("#flag").val(1);
				}
			},
			error : function(XMLHttpRequest, data) {
				console.log(data.msg);
			}
	});
}
function update_p(num, t) {
	if(num == t) {
		document.form1.dynamic.value = " 重新发送 ";
		document.form1.dynamic.disabled = false;
	} 
	else {
		printnr = t - num;
		document.form1.dynamic.value = " (" + printnr + ")秒后重发";
	}
}
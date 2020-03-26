$(function(){
	$("#userName").blur(function(){
		var userName=$(this).val();
		if(userName==null||userName==''){
			$(".error-show").text("*用户名/手机/邮箱不能为空");
		}
	});
	$("#password").blur(function(){
		var password=$(this).val();
		if(password==null||password==''){
			$(".error-show").text("*密码不能为空");
		}
	});
	document.onkeydown = function(e){ 
	    var ev = document.all ? window.event : e;
	    if(ev.keyCode==13) {
	    	$('#login-sso').click();
	     }
	}
	//登录动作
	$("#login-sso").click(function(){
		var userName=$("#userName-sso").val();
		var password=$("#password-sso").val()
		if(userName==""||password==""){
			return ;
		}
		 $.ajax({
				async : true, //是否异步  
				cache : false, //是否使用缓存  
				type : 'post', //请求方式,post  
				data : {
					userName:userName,
					password:password
				},
				dataType : "json", //数据传输格式  
				url : '/sso/shop/login?v=' + new Date().getTime(), //请求链接  
				success : function(data) {
					if(data.status==1){//表示登录成功，进行跳转
						 window.location.href="/shop"; 
					}else{
						$(".error-show").text(data.msg);
					}
				},
				error : function(XMLHttpRequest, data) {
					console.log(data.msg);
				}
		});
	});
});
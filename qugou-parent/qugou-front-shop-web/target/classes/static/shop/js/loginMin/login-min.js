$(function(){
	//打开弹出框的低吗
	/*$('.header-nav-name').click(function(){
		$('.theme-popover-mask').fadeIn(100);
		$('.theme-popover').slideDown(200);
		$(document).bind('mousewheel', function(event, delta) { return false; });
	});*/
	//关闭弹出层
	$('.theme-poptit .close').click(function(){
		$('.theme-popover-mask').fadeOut(100);
		$('.theme-popover').slideUp(200);
		$(document).unbind('mousewheel');
	});
	String.prototype.endWith=function(endStr){
	      var d=this.length-endStr.length;
	      return (d>=0&&this.lastIndexOf(endStr)==d)
	 
	 }
	/*document.onkeydown = function(e){ 
	    var ev = document.all ? window.event : e;
	    if(ev.keyCode==13) {
	    	$("#login-min-submit-box").click();
	     }
	}*/
	//点击提交
	$("#login-min-submit-box").click(function(){
		var userName=$("#login-min-userName").val();
		var password=$("#login-min-password").val();
		if(userName==""||password==""){
			$("#login-min-error").text("用户名或者密码不能为空");
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
					if(data.status==1){//表示登录成功
						$('.theme-poptit .close').click();
						var whref=window.location.href;
						if(whref.endWith("/shop")||whref.endWith("/shop#")){
							window.location.href="/shop";
						}else{
							var html="<span>欢迎您&nbsp;</span><a href='/sso/shop/login'>"+data.data.userName+"</a>"
							$(".top-login").html(html);
						}
					}else{
						$("#login-min-error").text(data.msg);
					}
				},
				error : function(XMLHttpRequest, data) {
					console.log(data.msg);
				}
		});
	})
});
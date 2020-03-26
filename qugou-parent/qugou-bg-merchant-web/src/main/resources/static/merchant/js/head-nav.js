var indexMerchantId="";
layui.use(['layer', 'form'], function() {
	var $ = layui.jquery,
		layerTips = parent.layer === undefined ? layui.layer : parent.layer, //获取父窗口的layer对象
		layer = layui.layer,
		form = layui.form();

	$('#newOrder').on('click', function() {
		$("#newOrder_index").html("");
		layer.open({
			title: '新订单',
			maxmin: true,
			type: 2,
			content: '/merchant/order/new/list',
			area: ['1400px', '700px']
		});
    });
	$('#todayTrade').on('click', function() {
		layer.open({
			title: '今日成交',
			maxmin: true,
			type: 2,
			content: '/merchant/order/todayTrade',
			area: ['600px', '200px']
		});
    });
    $('#newCustomer').on('click', function () {
    	$("#newCustomer_index").html("");
		layer.open({
			title: '新客户',
			maxmin: true,
			type: 2,
			content: '/merchant/member/list',
			area: ['1000px', '500px']
		});
    });
    $('#todayHot').on('click', function () {
    	layer.open({
    		title: '今日热卖',
    		maxmin: true,
    		type: 2,
    		content: '/merchant/statistics/day',
    		area: ['1000px', '600px']
    	});
    });
    $('#modifyPassword').on('click', function () {
    	$.get('/merchant/info/modifyPassword', null, function(form) {
			addBoxIndex = layer.open({
				type: 1,
				title: '修改密码',
				content: form,
				btn: ['保存', '取消'],
				shade: false,
				offset: ['200px', '30%'],
				area: ['680px', '300px'],
				zIndex: 19950924,
				maxmin: true,
				yes: function(index) {
					//触发表单的提交事件
					$('#modifyPassword_form').find('button[lay-filter=edit]').click();
				},
				full: function(elem) {
					var win = window.top === window.self ? window : parent.window;
					$(win).on('resize', function() {
						var $this = $(this);
						elem.width($this.width()).height($this.height()).css({
							top: 0,
							left: 0
						});
						elem.children('div.layui-layer-content').height($this.height() - 95);
					});
				},
				success: function(layero, index) {
					//弹出窗口成功后渲染表单
				    var form = layui.form();
					form.render();
					form.on('submit(edit)', function(data) {
						var password=$("#modifyPassword_form #password").val();
						var oldPassword=$("#modifyPassword_form #oldPassword").val();
						if($("#error-tip").text()==''){
							$.ajax({
								async : true, //是否异步  
								cache : false, //是否使用缓存  
								type : 'post', //请求方式,post  
								data : {
									password:password,
									oldPassword:oldPassword
								},
								dataType : "json", //数据传输格式  
								url : "/merchant/info/modifyPassword", //请求链接  
								success : function(data) {
									if (data.status ==1 ) {
										layer.msg('修改成功', {icon: 1,time:2000});
										layerTips.close(index);
										location.reload(); //刷新
										window.location.href="/sso/merchant/login";
									} else {
										//layer.msg(data.msg, {icon: 5,time:2000});
										$("#error-tip").text(data.msg);
									}
								},
								error : function(XMLHttpRequest, data) {
									console.log(data.msg);
								}
							});
						}
						return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。									
					});
				},
				end: function() {
					addBoxIndex = -1;
				}
			});
		});
    });
});

$(function(){
	$(document ).on( "blur", "#oldPassword", function() {
		if(!verifyPassword($(this).val())){
			$("#error-tip").text("密码由6-20位数字与字符混合组成");
		}else{
			$("#error-tip").text("");
		}
	}); 
	$(document ).on( "blur", "#password", function() {
		if(!verifyPassword($(this).val())){
			$("#error-tip").text("密码由6-20位数字与字符混合组成");
		}else{
			$("#error-tip").text("");
		}
	}); 
	$(document ).on( "blur", "#repassword", function() {
		if($(this).val()!=$("#password").val()){
			$("#error-tip").text("两次密码输入不一致");
		}else{
			$("#error-tip").text("");
		}
	}); 
	function verifyPassword(data){
		var regx=/^(?![^a-zA-Z]+$)(?!\\D+$).{6,20}$/;
		if(regx.test(data)){
			return true;
		}else{
			return false;
		}
	}
});

$(function() {
	indexMerchantId=$("#indexMerchantId").val();
	/* 在页面加载的时候触发websocket */
	var socket = new SockJS('http://localhost:8002/webSocketServer');
	var stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		// 注册发送消息(对应服务端第一种方式)
//		stompClient.subscribe('/topic/send', function(msg) {
//			$("#web-demo").html(JSON.parse(msg.body));
//		});
		// 注册推送时间回调（对应服务端第二种方式）
		stompClient.subscribe('/topic/newOrderCallback', function(msg) {
			var data=msg.body;
			if(data==null||data==''||typeof(data)=='undefined'){
				return;
			}
			var msgArray=data.split(",");
			if(msgArray[0]==indexMerchantId){
				var orderCount=$("#newOrder_index").children(".layui-badge").eq(0).attr("data-oder");
				if(orderCount==null||typeof(orderCount)=='undefined'||orderCount==''){//表明还未存在
					var html="<span class='layui-badge' data-oder='1'>1</span>"
					$("#newOrder_index").html(html);
				}else{
					var html="";
					if(new Number(orderCount)>=100){
						html+="<span class='layui-badge' data-oder='"+(new Number(orderCount)+1)+"'>99+</span>";
					}else{
						html+="<span class='layui-badge' data-oder='"+(new Number(orderCount)+1)+"'>"+(new Number(orderCount)+1)+"</span>";
					}
					$("#newOrder_index").html(html);
				}
			}
//			$("#web-demo").html('当前服务器时间：' + msg.body);
		});
	});
});
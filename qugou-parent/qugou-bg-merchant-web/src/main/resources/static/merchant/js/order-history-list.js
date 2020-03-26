layui.config({
	base: 'http://www.qugou.com/merchant/layui/js/'
});
layui.use(['paging', 'form','laydate'], function() {
	var $ = layui.jquery,
		paging = layui.paging(),
		layerTips = parent.layer === undefined ? layui.layer : parent.layer, //获取父窗口的layer对象
		layer = layui.layer, //获取当前窗口的layer对象
		form = layui.form(),
	 	laydate = layui.laydate;
	 //自定义验证规则  
	  form.verify({
		  positive_number:[/^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/, '必须为正数']
	  });  
    paging.init({
        openWait: true,
        url: 'http://www.qugou.com/merchant/order/list?v=' + new Date().getTime(), //地址
		elem: '#content', //内容容器
		params: { //发送到服务端的参数
			orderNumber:$('#order-history-from #orderNumber').val(),
			userName:$('#order-history-from #userName').val(),
			state:$("#order-history-from #state").val(),
			addDate:$('#order-history-from #addDate').val()
		},
		type: 'POST',
		tempElem: '#tpl', //模块容器
		pageConfig: { //分页参数配置
			elem: '#paged', //分页容器
			pageSize: 15 //分页大小
		},
		success: function() { //渲染成功的回调
			//alert('渲染成功');
		},
		fail: function(msg) { //获取数据失败的回调
			//alert('获取数据失败')
		},
		complate: function() { //完成的回调
			//alert('处理完成');
			//重新渲染复选框
			form.render('checkbox');
			form.on('checkbox(allselector)', function(data) {
				var elem = data.elem;
				$('#content').children('tr').each(function() {
					var $that = $(this);
					//全选或反选
					$that.children('td').eq(0).children('input[type=checkbox]')[0].checked = elem.checked;
					form.render('checkbox');
				});
			});
			
			//浏览商品信息
			//绑定所有编辑按钮事件，绑定删除按钮						
			$('#content').children('tr').each(function() {
				var $that = $(this);
				$that.children('td:last-child').children('a[data-opt=look]').on('click', function() {
					/*if(addBoxIndex !== -1)
						return;*/
					//本表单通过ajax加载 --以模板的形式，当然你也可以直接写在页面上读取
					$.get('/merchant/order/lookOrder?orderId='+$(this).data('name'), null, function(form) {
						addBoxIndex = layer.open({
							type: 1,
							title: '订单详细',
							content: form,
							shade: false,
							offset: ['0px', '18%'],
							area: ['700px', '600px'],
							zIndex: 19950924,
							maxmin: true,
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
							end: function() {
								addBoxIndex = -1;
							}
						});
					});
				});
				//本表单通过ajax加载 --以模板的形式，当然你也可以直接写在页面上读取
				$that.children('td:last-child').children('a[data-opt=edit]').on('click', function() {
					$.get('/merchant/order/updateOrder?orderId='+$(this).data('name'), null, function(form) {
					addBoxIndex = layer.open({
						type: 1,
						title: '修改订单信息',
						content: form,
						btn: ['保存', '取消'],
						shade: false,
						offset: ['0px', '18%'],
						area: ['700px', '600px'],
						zIndex: 19950924,
						maxmin: true,
						yes: function(index) {
							//触发表单的提交事件
							$('#order_form_update').find('button[lay-filter=edit]').click();
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
								var orderId=$("#order_form_update #orderId").val();
								if(orderId==null||orderId==''){
									layerTips.msg('更新失败');
									return;
								}
								$.ajax({
									async : true, //是否异步  
									cache : false, //是否使用缓存  
									type : 'post', //请求方式,post  
									data : {
										//这里是参数
										id:orderId,
										customerPhone:$("#order_form_update input[name=customerPhone]").val(),
										deliveryAddress:$("#order_form_update input[name=deliveryAddress]").val(),
										merchantRemark:$("#order_form_update #merchantRemark").text()
									},
									dataType : "json", //数据传输格式  
									url : "/merchant/order/updateOrder", //请求链接  
									success : function(data) {
										if (data.status ==1 ) {
											layerTips.msg('更新成功');
											layerTips.close(index);
											location.reload(); //刷新
										} else {
											layerTips.msg('更新失败');
										}
									},
									error : function(XMLHttpRequest, data) {
										console.log(data.msg);
									}
								});
								//这里可以写ajax方法提交表单
								return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。									
							});
						},
						end: function() {
							addBoxIndex = -1;
						}
					});
				  });
				});
				//接单
				$that.children('td:last-child').children('a[data-opt=accept]').on('click', function() {
					var orderId= $(this).data('name');
					layer.confirm('确认要接单吗？', {
			            btn : [ '确定', '取消' ]//按钮
			        }, function(index) {
			            $.ajax({
							async : true, //是否异步  
							cache : false, //是否使用缓存  
							type : 'post', //请求方式,post  
							data : {
								id : orderId,
								state:2
							},
							dataType : "json", //数据传输格式  
							url : '/merchant/order/updateState', //请求链接  
							success : function(data) {
								if(data.status ==1) {
									layer.msg("接单成功");
									location.reload(); //刷新
									layer.close(index);
								}
							},
							error : function(XMLHttpRequest, data) {
								console.log(data.msg);
							}
						});
			        }); 
				});
				//拒单
				$that.children('td:last-child').children('a[data-opt=refuse]').on('click', function() {
					var orderId= $(this).data('name');
					layer.confirm('确认要拒绝吗？', {
			            btn : [ '确定', '取消' ]//按钮
			        }, function(index) {
			            $.ajax({
							async : true, //是否异步  
							cache : false, //是否使用缓存  
							type : 'post', //请求方式,post  
							data : {
								id : orderId,
								state:-2//表示拒单
							},
							dataType : "json", //数据传输格式  
							url : '/merchant/order/updateState', //请求链接  
							success : function(data) {
								if(data.status ==1) {
									layer.msg("接单成功");
									location.reload(); //刷新
									layer.close(index);
								}
							},
							error : function(XMLHttpRequest, data) {
								console.log(data.msg);
							}
						});
			        }); 
				});
			});
		},
	});
	$('#search').on('click', function() {
		paging.get({
			orderNumber:$('#order-history-from #orderNumber').val(),
			userName:$('#order-history-from #userName').val(),
			state:$("#order-history-from #state").val(),
			addDate:$('#order-history-from #addDate').val()
		});
	});
	//获取所有选择的列
	$('#accept_order').on('click', function() {
		var ids = '';
		$('#content').children('tr').each(function() {
			var $that = $(this);
			var $cbx = $that.children('td').eq(0).children('input[type=checkbox]')[0].checked;
			if($cbx) {
				var n = $that.children('td:last-child').children('a[data-opt=edit]').data('name');
				ids += n + ',';
			}
		});
		if(ids==''||ids==null){
			layer.msg('请先选择需要订单', {icon: 5,time:2000});
			return;
		}
		ids=ids.substring(0,ids.length-1);
		layer.confirm('确认要接单吗？', {
            btn : [ '确定', '取消' ]//按钮
        }, function(index) {
    		$.ajax({
    			async : true, //是否异步  
    			cache : false, //是否使用缓存  
    			type : 'post', //请求方式,post  
    			data : {
    				ids : ids,
    				state:2
    			},
    			dataType : "json", //数据传输格式  
    			url : '/merchant/order/updateProductBatch', //请求链接  
    			success : function(data) {
    				if(data.status ==1) {
    					layer.msg("接单成功");
    					location.reload(); //刷新
    					layer.close(index);
    				}
    			},
    			error : function(XMLHttpRequest, data) {
    				console.log(data.msg);
    			}
    		});
        }); 
	});
	$('#refuse_order').on('click', function() {
		var ids = '';
		$('#content').children('tr').each(function() {
			var $that = $(this);
			var $cbx = $that.children('td').eq(0).children('input[type=checkbox]')[0].checked;
			if($cbx) {
				var n = $that.children('td:last-child').children('a[data-opt=edit]').data('name');
				ids += n + ',';
			}
		});
		if(ids==''||ids==null){
			layer.msg('请选择订单', {icon: 5,time:2000});
			return;
		}
		ids=ids.substring(0,ids.length-1);
		layer.confirm('确认要拒单吗？', {
            btn : [ '确定', '取消' ]//按钮
        }, function(index) {
    		$.ajax({
    			async : true, //是否异步  
    			cache : false, //是否使用缓存  
    			type : 'post', //请求方式,post  
    			data : {
    				ids : ids,
    				state:-2
    			},
    			dataType : "json", //数据传输格式  
    			url : '/merchant/order/updateProductBatch', //请求链接  
    			success : function(data) {
    				if(data.status ==1) {
    					layer.msg("拒单成功");
    					location.reload(); //刷新
    					layer.close(index);
    				}
    			},
    			error : function(XMLHttpRequest, data) {
    				console.log(data.msg);
    			}
    		});
        }); 
	});
});
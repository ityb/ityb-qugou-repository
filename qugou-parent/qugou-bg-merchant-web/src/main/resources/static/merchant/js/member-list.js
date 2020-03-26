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
        url: 'http://www.qugou.com/merchant/member/list?v=' + new Date().getTime(), //地址
		elem: '#content', //内容容器
		params: { //发送到服务端的参数
			userName:$('#memberList #userName').val(),
			collectionDate:$('#memberList #collectionDate').val()
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
			//绑定所有编辑按钮事件，绑定删除按钮						
			$('#content').children('tr').each(function() {
				var $that = $(this);
				$that.children('td:last-child').children('a[data-opt=edit]').on('click', function() {
					/*if(addBoxIndex !== -1)
						return;*/
					$.get('/merchant/member/editEmail', {email:$(this).data('name')}, function(form) {
						addBoxIndex = layer.open({
							type: 1,
							title: '编辑邮件',
							content: form,
							btn: ['保存', '取消'],
							shade: false,
							offset: ['0px', '18%'],
							area: ['680px', '400px'],
							zIndex: 19950924,
							maxmin: true,
							yes: function(index) {
								//触发表单的提交事件
								$('#email_form_edit').find('button[lay-filter=edit]').click();
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
									var emailContent=$("#email_form_edit #eamilInfo").val();
									var email=$("#email_form_edit #email").val();
									var emailTitle=$("#email_form_edit #emailTitle").val();
									$.ajax({
										async : true, //是否异步  
										cache : false, //是否使用缓存  
										type : 'post', //请求方式,post  
										data : {
											emailContent : emailContent,
											email:email,
											emailTitle:emailTitle
										},
										dataType : "json", //数据传输格式  
										url : "/merchant/member/emailSend", //请求链接  
										success : function(data) {
											if (data.status ==1 ) {
												layerTips.msg('发送成功');
												layerTips.close(index);
												location.reload(); //刷新
											} else {
												layerTips.msg('发送失败');
											}
										},
										error : function(XMLHttpRequest, data) {
											console.log(data.msg);
										}
									});
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
		},
	});
	$('#search').on('click', function() {
		paging.get({
			userName:$('#memberList #userName').val(),
			collectionDate:$('#memberList #collectionDate').val()
		})
	});
	
	//获取所有选择的列
	$('#edit_batch_email').on('click', function() {
		var ids = '';
		$('#content').children('tr').each(function() {
			var $that = $(this);
			var $cbx = $that.children('td').eq(0).children('input[type=checkbox]')[0].checked;
			if($cbx) {
				var n = $that.children('td:last-child').children('a[data-opt=edit]').data('name');
				if(n!=''&&n!=null){
					ids += n + ',';
				}
			}
		});
		if(ids==''||ids==null){
			layer.msg('请先选择需要发送邮件的会员', {icon: 5,time:2000});
			return;
		}
		ids=ids.substring(0,ids.length-1);
		//本表单通过ajax加载 --以模板的形式，当然你也可以直接写在页面上读取
		$.get('/merchant/member/editEmail', {email:ids}, function(form) {
			addBoxIndex = layer.open({
				type: 1,
				title: '编辑邮件',
				content: form,
				btn: ['保存', '取消'],
				shade: false,
				offset: ['0px', '18%'],
				area: ['680px', '400px'],
				zIndex: 19950924,
				maxmin: true,
				yes: function(index) {
					//触发表单的提交事件
					$('#email_form_edit').find('button[lay-filter=edit]').click();
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
						var emailContent=$("#email_form_edit #eamilInfo").val();
						var email=$("#email_form_edit #email").val();
						var emailTitle=$("#email_form_edit #emailTitle").val();
						$.ajax({
							async : true, //是否异步  
							cache : false, //是否使用缓存  
							type : 'post', //请求方式,post  
							data : {
								emailContent : emailContent,
								email:email,
								emailTitle:emailTitle
							},
							dataType : "json", //数据传输格式  
							url : "/merchant/member/emailSend", //请求链接  
							success : function(data) {
								if (data.status ==1 ) {
									layerTips.msg('发送成功');
									layerTips.close(index);
									location.reload(); //刷新
								} else {
									layerTips.msg('发送失败');
								}
							},
							error : function(XMLHttpRequest, data) {
								console.log(data.msg);
							}
						});
						return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。									
					});
				},
				end: function() {
					addBoxIndex = -1;
				}
			});
		}); 
	});
	
	//获取所有选择的列
	$('#edit_all_email').on('click', function() {
		//本表单通过ajax加载 --以模板的形式，当然你也可以直接写在页面上读取
		$.get('/merchant/member/editEmail', function(form) {
			addBoxIndex = layer.open({
				type: 1,
				title: '编辑邮件',
				content: form,
				btn: ['保存', '取消'],
				shade: false,
				offset: ['0px', '18%'],
				area: ['680px', '400px'],
				zIndex: 19950924,
				maxmin: true,
				yes: function(index) {
					//触发表单的提交事件
					$('#email_form_edit').find('button[lay-filter=edit]').click();
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
						var emailContent=$("#email_form_edit #eamilInfo").val();
						var email=$("#email_form_edit #email").val();
						var emailTitle=$("#email_form_edit #emailTitle").val();
						$.ajax({
							async : true, //是否异步  
							cache : false, //是否使用缓存  
							type : 'post', //请求方式,post  
							data : {
								emailContent : emailContent,
								email:email,
								emailTitle:emailTitle
							},
							dataType : "json", //数据传输格式  
							url : "/merchant/member/emailSend", //请求链接  
							success : function(data) {
								if (data.status ==1 ) {
									layerTips.msg('发送成功');
									layerTips.close(index);
									location.reload(); //刷新
								} else {
									layerTips.msg('发送失败');
								}
							},
							error : function(XMLHttpRequest, data) {
								console.log(data.msg);
							}
						});
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
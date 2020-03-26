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
        url: 'http://www.qugou.com/merchant/stock/list-data?v=' + new Date().getTime(), //地址
		elem: '#content', //内容容器
		params: { //发送到服务端的参数
			name:$('#name').val(),
			number:$('#number').val(),
			addDate:$('#addDate').val() 
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
					if(addBoxIndex !== -1)
						return;
					//本表单通过ajax加载 --以模板的形式，当然你也可以直接写在页面上读取
					$.get('/merchant/stock/updateSpecification?id='+$(this).data('name'), null, function(form) {
						addBoxIndex = layer.open({
							type: 1,
							title: '添加商品规格',
							content: form,
							btn: ['保存', '取消'],
							shade: false,
							offset: ['50px', '18%'],
							area: ['705px', '400px'],
							zIndex: 19950924,
							maxmin: true,
							yes: function(index) {
								//触发表单的提交事件
								$('#specification_form_update').find('button[lay-filter=edit]').click();
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
									var specificationId=$("#specification_form_update input[name=specificationId]").val();
									if(specificationId==null||specificationId==''){
										layerTips.msg('更新失败');
										return;
									}
									$.ajax({
										async : true, //是否异步  
										cache : false, //是否使用缓存  
										type : 'post', //请求方式,post  
										data : {
											//这里是参数
											id:$("#specification_form_update input[name=specificationId]").val(),
											price:$("#specification_form_update input[name=price]").val(),
											stock:$("#specification_form_update input[name=stock]").val(),
											weight:$("#specification_form_update input[name=weight]").val()
										},
										dataType : "json", //数据传输格式  
										url : "/merchant/stock/updateSpecification", //请求链接  
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
				$that.children('td:last-child').children('a[data-opt=del]').on('click', function() {
					var specificationIdStr= $(this).data('name');
					layer.confirm('确认要删除吗？', {
			            btn : [ '确定', '取消' ]//按钮
			        }, function(index) {
			            $.ajax({
							async : true, //是否异步  
							cache : false, //是否使用缓存  
							type : 'post', //请求方式,post  
							data : {
								specificationId : specificationIdStr
							},
							dataType : "json", //数据传输格式  
							url : '/merchant/stock/deleteSpecification', //请求链接  
							success : function(data) {
								if(data.status ==1) {
									layer.msg("删除成功");
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
	//获取所有选择的列
	$('#export_ps_info').on('click', function() {
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
			layer.msg('请选择需要导出的商品', {icon: 5,time:2000});
			return;
		}
		ids=ids.substring(0,ids.length-1);
		location.href = "/merchant/stock/export?ids="+ids;
	});
	$('#export_ps_all').on('click', function() {
		location.href = "/merchant/stock/exportAll";
	});
	
	$('#search').on('click', function() {
		paging.get({
			name:$('#name').val(),
			number:$('#number').val(),
			addDate:$('#addDate').val() 
		})
	});
	var addBoxIndex = -1;
	$('#add').on('click', function() {
		if(addBoxIndex !== -1)
			return;
		//本表单通过ajax加载 --以模板的形式，当然你也可以直接写在页面上读取
		$.get('/merchant/stock/add', null, function(form) {
			addBoxIndex = layer.open({
				type: 1,
				title: '添加商品库存',
				content: form,
				btn: ['保存', '取消'],
				shade: false,
				offset: ['0px', '18%'],
				area: ['705px', '600px'],
				zIndex: 19950924,
				maxmin: true,
				yes: function(index) {
					//触发表单的提交事件
					$('#product_specification_form').find('button[lay-filter=edit]').click();
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
						//以json字符串的形式提交参数
						var paramJson="{";
						paramJson+="\"productNumber\":"+"\""+$("#product_specification_form input[name='productNumber']").val()+"\""+",";
						paramJson+="\"productName\":"+"\""+$("#product_specification_form input[name='productName']").val()+"\""+",";
							paramJson+="\"specificationList\":[";
						var specificationArray=$("#product_specification_add .layui-input-block");
						$.each(specificationArray,function(index,value){
							paramJson+="{\"productSpecificationName\":";
							paramJson+="\""+$(value).find("input[name=productSpecificationName]").val()+"\""+",";
							paramJson+="\"price\":";
							paramJson+="\""+$(value).find("input[name=price]").val()+"\""+",";
							paramJson+="\"stock\":";
							paramJson+="\""+$(value).find("input[name=stock]").val()+"\""+",";
							paramJson+="\"weight\":";
							paramJson+="\""+$(value).find("input[name=weight]").val()+"\""+"}";
							if(index!=specificationArray.length-1){
								paramJson+=",";
							}
						});
						paramJson+="]";
						paramJson+="}";
						//alert(paramJson);
						$.ajax({
							async : true, //是否异步  
							cache : false, //是否使用缓存  
							type : 'post', //请求方式,post  
							data : {
								content : paramJson
							},
							dataType : "json", //数据传输格式  
							url : "/merchant/stock/add", //请求链接  
							success : function(data) {
								if (data.status ==1 ) {
									layerTips.msg('添加成功');
									layerTips.close(index);
									location.reload(); //刷新
								} else {
									layerTips.msg('添加失败');
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
	$('#add_specification').on('click', function() {
		if(addBoxIndex !== -1)
			return;
		//本表单通过ajax加载 --以模板的形式，当然你也可以直接写在页面上读取
		$.get('/merchant/stock/addSpecification', null, function(form) {
			addBoxIndex = layer.open({
				type: 1,
				title: '添加商品规格',
				content: form,
				btn: ['保存', '取消'],
				shade: false,
				offset: ['50px', '18%'],
				area: ['705px', '400px'],
				zIndex: 19950924,
				maxmin: true,
				yes: function(index) {
					//触发表单的提交事件
					$('#specification_form').find('button[lay-filter=edit]').click();
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
						$.ajax({
							async : true, //是否异步  
							cache : false, //是否使用缓存  
							type : 'post', //请求方式,post  
							data : {
								//这里是参数
								name:$("#specification_form input[name=productSpecificationName]").val(),
								productId:$("#specification_form #productName").val(),
								price:$("#specification_form input[name=price]").val(),
								stock:$("#specification_form input[name=stock]").val(),
								weight:$("#specification_form input[name=weight]").val()
							},
							dataType : "json", //数据传输格式  
							url : "/merchant/stock/addSpecification", //请求链接  
							success : function(data) {
								if (data.status ==1 ) {
									layerTips.msg('添加成功');
									layerTips.close(index);
									location.reload(); //刷新
								} else {
									layerTips.msg('添加失败');
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
});
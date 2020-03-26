layui.config({
	base: 'http://www.qugou.com/merchant/layui/js/'
});
layui.use(['layedit','upload','form'], function(){
	  	var layedit = layui.layedit,
	  	 	upload = layui.upload,
	  	    form=layui.form;
			layedit.set({
				uploadImage: {
				url: '/merchant/info/upload' //接口url
			   ,type: 'post' //默认post
			}
		});
	  var editIndex=layedit.build('shop_desc'); //建立编辑器
	  //执行实例
	  var uploadInst = upload.render({
	    elem: '#shop_logo_upload' //绑定元素
	    ,url: '/merchant/info/upload' //上传接口
	    ,done: function(res){
	    	$("#pic_show").attr("src",res.data.src);
	    	$("#shopLogo").val(res.data.src);
	    }
	    ,error: function(){
	    }
	  });
	  $("#shop_submit").click(function(){
		  $.ajax({
				async : true, //是否异步  
				cache : false, //是否使用缓存  
				type : 'post', //请求方式,post  
				data : {
					id:$("#shopId").val(),
					startPrice:$("#startPrice").val(),
					shopLogo:$("#shopLogo").val(),
					shopDesc:layedit.getContent(editIndex)
				},
				dataType : "json", //数据传输格式  
				url : '/merchant/info/update', //请求链接  
				success : function(data) {
					if(data.status ==1) {
						layer.msg("修改成功");
					}else{
						layer.msg("修改失败");
						location.href = "/merchant/info/show";
					}
				},
				error : function(XMLHttpRequest, data) {
					console.log(data.msg);
				}
			});
	  });
});
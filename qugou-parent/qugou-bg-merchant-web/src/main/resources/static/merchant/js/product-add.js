layui.config({
	base: 'http://www.qugou.com/merchant/layui/js/'
});
layui.use(['layedit','upload','form'], function(){
	  	var layedit = layui.layedit,
	  	 	upload = layui.upload,
	  	    form=layui.form;
			layedit.set({
				uploadImage: {
				url: '/merchant/product/upload' //接口url
			   ,type: 'post' //默认post
			}
		});
	  var editIndex=layedit.build('product_desc'); //建立编辑器
	 form.on('select(product_form_productName)', function(data){
		var productId=$("#product_form #productId").val();
		 if(productId==null||productId=='' ){
			 var html='';
			 html+="<div class='layui-input-block' style='margin-top: 10px;'>";
			 html+="<input type='text' name='productSpecificationName' readonly='readonly' placeholder='规格名称'  autocomplete='off' class='layui-input' style='width: 120px;float: left;'>";
			 html+="<input type='text' name='price' readonly='readonly'  lay-verify='required' placeholder='商品单价（元）'  autocomplete='off' class='layui-input' style='width: 120px;float: left;margin-left: 10px;'>";
			 html+="<input type='text' name='weight' readonly='readonly' lay-verify='required' placeholder='商品净重（kg）'  autocomplete='off' class='layui-input' style='width: 120px;float: left;margin-left: 10px;'>";
			 html+="<input type='text' name='stock' readonly='readonly'  lay-verify='required' placeholder='商品库存（件）'  autocomplete='off' class='layui-input' style='width: 120px;float: left;margin-left: 10px;'>";
			 html+="<button id='specification_show_delete' type='button' class='layui-btn' style='float: left;margin-left: 5px;'>移除</button>";
			 html+="</div>";
			 $("#product_form #product_specification_show").html(html);
			 return;
		 }
		 $.ajax({
				async : true, //是否异步  
				cache : false, //是否使用缓存  
				type : 'post', //请求方式,post  
				data : {
					productId : $("#product_form #productId").val()
				},
				dataType : "json", //数据传输格式  
				url : '/merchant/product/getProductDetil?v=' + new Date().getTime(), //请求链接  
				success : function(data) {
				    if(data.status==1&&data.data!=null&&data.data!=''){
				    	layer.msg('该商品已经被添加', {icon: 5,time:2000});
				    	//选中下拉列表中的第一项
				    	$("#product_form #productId").siblings("div.layui-form-select").find("dd:first").click();
				    }else{
				    	$.ajax({
							async : true, //是否异步  
							cache : false, //是否使用缓存  
							type : 'post', //请求方式,post  
							data : {
								id : $("#product_form #productId").val()
							},
							dataType : "json", //数据传输格式  
							url : '/merchant/stock/querySpecificationByPoduct?v=' + new Date().getTime(), //请求链接  
							success : function(data) {
								var html='';
								var list=$(data.data);
								list.each(function(index,value){
									//在这里补充属性
									html+="<div class='layui-input-block' style='margin-top: 10px;'>";
									html+="<input type='hidden' name='productSpecificationId' value="+value.productSpecificationId+">";
									html+="<input readonly='readonly'  type='text' value="+value.productSpecificationName+" lay-verify='required' maxlength='10' name='productSpecificationName' placeholder='规格名称' autocomplete='off' class='layui-input' style='width: 120px;float: left;'>";
									html+="<input readonly='readonly'  type='text' value="+value.price+" lay-verify='required' lay-verify='positive_number' min='0.01' name='price' placeholder='商品单价（元）' lay-verify='number' autocomplete='off' class='layui-input' style='width: 120px;float: left;margin-left: 10px;'>";
									html+="<input readonly='readonly'  type='text' value="+value.weight+" lay-verify='required' lay-verify='positive_number' min='0.01' name='weight' placeholder='商品净重（kg）' lay-verify='number' autocomplete='off' class='layui-input' style='width: 120px;float: left;margin-left: 10px;'>";
									html+="<input readonly='readonly'  type='text' value="+value.stock+" lay-verify='required' lay-verify='positive_number' min='1' name='stock' placeholder='库存量（件）' lay-verify='number' autocomplete='off' class='layui-input' style='width: 120px;float: left;margin-left: 10px;'>";
									html+="<button id='specification_show_delete'  type='button' class='layui-btn' style='float: left;margin-left: 5px;'>移除</button>";
									html+="</div>";
								});
								$("#product_form #product_specification_show").html(html);
							},
							error : function(XMLHttpRequest, data) {
								console.log(data.msg);
							}
						});
				    }
				},
				error : function(XMLHttpRequest, data) {
					console.log(data.msg);
				}
			});
	});
	 $(document ).on( "click", "#product_form #specification_show_delete", function() {
		if($("#product_form #specification_show_delete").length==1){
			return;
		}
	    $(this).parent().remove();
	});
	//多文件列表示例
	  var picListView = $('#pciShowList')
	  ,uploadListIns = upload.render({
	    elem: '#picList'
	    ,url: '/merchant/product/upload'
	    ,accept: 'file'
	    ,multiple: true
	    ,auto: false
	    ,bindAction: '#picListAction'
	    ,choose: function(obj){   
	      var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
	      //读取本地文件
	      obj.preview(function(index, file, result){
	        var tr = $(['<tr id="upload-'+ index +'">'
	          ,'<td width="92px">'+'<img src="'+ result +'" alt="'+ file.name +'" class="layui-upload-img">'+'</td>'
	          ,'<td>'+ file.name +'</td>'
	          ,'<td>'+ (file.size/1014).toFixed(1) +'kb</td>'
	          ,'<td>等待上传</td>'
	          ,'<td>'
	          ,'<button class="layui-btn layui-btn-mini layui-btn-danger pic-delete">删除</button>'
	          ,'</td>'
	        ,'</tr>'].join(''));
	        //删除
	        tr.find('.pic-delete').on('click', function(){
	          delete files[index]; //删除对应的文件
	          tr.remove();
	          uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
	        });
	        picListView.append(tr);
	      });
	    }
	    ,done: function(res, index, upload){
	      if(res.code == 0){ //上传成功
	    	 //上传成功的回调
	    	  var pics= $("#productPic").val();
	    	  pics+=res.data.src+",";
	    	  $("#productPic").val(pics);
	        var tr = picListView.find('tr#upload-'+ index)
	        ,tds = tr.children();
	        tds.eq(3).html('<span style="color: #5FB878;">上传成功</span>');
	        tds.eq(4).html(''); //清空操作
	        return delete this.files[index]; //删除文件队列已经上传成功的文件 
	      }
	      this.error(index, upload);
	    }
	    ,error: function(index, upload){
	      var tr = picListView.find('tr#upload-'+ index)
	      ,tds = tr.children();
	      tds.eq(3).html('<span style="color: #FF5722;">上传失败</span>');
	     /*  tds.eq(3).find('.demo-reload').removeClass('layui-hide'); //显示重传 */
	    }
	  });
	//进行提交处理
		$("#product_submit").click(function(){
			//计算
			var specifications='';
			$("#product_specification_show input[name='productSpecificationId']").each(function(index,value){
				specifications+=$(value).val()+",";
			});
			specifications=specifications.substring(0,specifications.length-1);
			var picUrl=$("#productPic").val();
			if(picUrl==null||picUrl==''){
				layer.msg('请上传商品图片', {icon: 5,time:2000});
				return;
			}
			picUrl=picUrl.substring(0,picUrl.length-1);
			//使用ajax提交
		   $.ajax({
				async : true, //是否异步  
				cache : false, //是否使用缓存  
				type : 'post', //请求方式,post  
				data : {
					productId:$("#productId").val(),
					title:$("#title").val(),
					sellPoint:$("#sellPoint").val(),
					merchantCategoryId:$("#merchantCategoryId").val(),
					shopCategoryId:$("#shopCategoryId").val(),
					picUrl:picUrl,
					productDesc:layedit.getContent(editIndex),
					specifications:specifications
				},
				dataType : "json", //数据传输格式  
				url : '/merchant/product/add', //请求链接  
				success : function(data) {
					if(data.status ==1) {
						layer.msg("添加成功");
						location.href = "/merchant/product/list";
					}
				},
				error : function(XMLHttpRequest, data) {
					console.log(data.msg);
				}
			});
		});
		$("#back").click(function(){
			location.href = "/merchant/product/list";
		});
		$("#product_reset").click(function(){
			 var html='';
			 html+="<div class='layui-input-block' style='margin-top: 10px;'>";
			 html+="<input type='text' name='productSpecificationName' readonly='readonly' placeholder='规格名称'  autocomplete='off' class='layui-input' style='width: 120px;float: left;'>";
			 html+="<input type='text' name='price' readonly='readonly'  lay-verify='required' placeholder='商品单价（元）'  autocomplete='off' class='layui-input' style='width: 120px;float: left;margin-left: 10px;'>";
			 html+="<input type='text' name='weight' readonly='readonly' lay-verify='required' placeholder='商品净重（kg）'  autocomplete='off' class='layui-input' style='width: 120px;float: left;margin-left: 10px;'>";
			 html+="<input type='text' name='stock' readonly='readonly'  lay-verify='required' placeholder='商品库存（件）'  autocomplete='off' class='layui-input' style='width: 120px;float: left;margin-left: 10px;'>";
			 html+="<button id='specification_show_delete' type='button' class='layui-btn' style='float: left;margin-left: 5px;'>移除</button>";
			 html+="</div>";
			 $("#product_form #product_specification_show").html(html);
		});
});

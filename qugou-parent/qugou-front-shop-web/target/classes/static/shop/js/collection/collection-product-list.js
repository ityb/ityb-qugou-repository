$(function(){
	$(".collection-product-find-button").click(function(){
		var collectionId=$(this).attr("title");
		ds.dialog.confirm("是否取消收藏？",function(){
			//进行ajax提交
			 $.ajax({
					async : true, //是否异步  
					cache : false, //是否使用缓存  
					type : 'post', //请求方式,post  
					data : {
						id:collectionId
					},
					dataType : "json", //数据传输格式  
					url : '/shop/collection/product/delete?v=' + new Date().getTime(), //请求链接  
					success : function(data) {
						if(data.status==1){//表示登录成功，进行跳转
							window.location.href="/shop/collection/product/list";
						}
					},
					error : function(XMLHttpRequest, data) {
						console.log(data.msg);
					}
		     });
		},function(){
			//alert("逗比，为什么不回到首页？")
		});
	});
});

$(function(){
	function getParameter(name) { 
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); 
		var r = window.location.search.substr(1).match(reg); 
		if (r!=null) return unescape(r[2]); return null;
	}
	//init
	$(function(){
		var totalRecords = $("#total_record").val();
		var totalPage = ((totalRecords-1)/50)+1;
		var pageNo = getParameter('pageIndex');
		if(!pageNo){
			pageNo = 1;
		}
		//初始化分页控件
		//有些参数是可选的，比如lang，若不传有默认值
		kkpager.init({
			pno : pageNo,
			//总页码
			total : totalPage,
			//总数据条数
			totalRecords : totalRecords,
			//链接前部
			hrefFormer : '/shop/collection/product/list',
			//链接尾部
			hrefLatter : '',
			getLink : function(n){
				var url=this.hrefFormer + this.hrefLatter + "?pageIndex="+n;
				return url;
			},
			lang : {
				prePageText : '上一页',
				nextPageText : '下一页',
				totalPageBeforeText : '共',
				totalPageAfterText : '页',
				totalRecordsAfterText : '条数据',
				gopageBeforeText : '转到',
				gopageButtonOkText : '确定',
				gopageAfterText : '页',
				buttonTipBeforeText : '第',
				buttonTipAfterText : '页'
			}
		});
		//生成
		kkpager.generPageHtml();
	});
});
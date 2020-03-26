$(function(){
	//显示分数
    $(".show_number p").each(function(index, element) {
      var num=$(this).attr("tip");
      var www=num*2*16;//
      $(this).css("width",www);
      $(this).parent(".atar_Show").siblings("span").text(num+"分");
    });
    $(document).on('mouseenter', '.pl-img-show', function() {
		$(this).css("border","2px solid #009688");
    });
    $(document).on('mouseleave', '.pl-img-show', function() {
    	$(this).css("border","1px solid #e2e2e2");
    });
    $(document).on('click', '.pl-img-show', function() {
    	var imgSrc=$(this).find("img").attr("src");
    	var plImgDiv=$(this).parent().parent().find(".pl-img-detail-show");
    	plImgDiv.addClass("pl-img-detail-show-class");
    	var img="<img src='"+imgSrc+"'>";
    	plImgDiv.empty();
    	plImgDiv.append(img);
    });
    $(".merchant-show-box").mouseenter(function(){
    	$(this).css("background-color","#F5F5F5")
    });
    $(".merchant-show-box").mouseleave(function(){
    	$(this).css("background-color","");
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
			hrefFormer : '/shop/search/shop',
			//链接尾部
			hrefLatter : '',
			getLink : function(n){
				var keyWord=$(".search-keyword").val();
				var url=this.hrefFormer + this.hrefLatter + "?pageIndex="+n;
				if(keyWord!=null && keyWord!=''){
					url+="&shopName="+keyWord;
				}
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

$(function(){
	$("#not_evaluation").click(function(){
		window.location.href='/shop/evaluation/notEvaluation/list';
	});
	$("#finish_evaluation").click(function(){
		window.location.href='/shop/evaluation/finishEvaluation/list';
	});
	$(".evaluation-find-button").click(function(){
		var id=$(this).attr("title");
		if(id==null||id==''){
			return;
		}
		window.location.href="/shop/evaluation/getEvaluation?id="+id;
	})
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
			hrefFormer : '/shop/evaluation/finishEvaluation/list',
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
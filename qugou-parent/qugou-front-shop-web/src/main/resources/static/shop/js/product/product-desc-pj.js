/**
 *商品-描述-评价.js 	
 */
$(document).ready(function(){
	/**
	 * 商品描述点击事件
	 */
	$(".nav-item-desc").click(function(){
		$(this).css("background-color","#009688");
		$(this).css("color","#fff");
		$(".pj-show-box").css("display","none");
		$(".desc-show-box").css("display","block");
		$(".nav-item-pj").css("background-color","#fff");
		$(".nav-item-pj").css("color","black");
	});
	$(".nav-item-pj").click(function(){
		$(this).css("background-color","#009688");
		$(this).css("color","#fff");
		$(".desc-show-box").css("display","none");
		$(".pj-show-box").css("display","block");
		$(".nav-item-desc").css("background-color","#fff");
		$(".nav-item-desc").css("color","black");
	});
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
});

var data=null;
$.ajax({
	async : true, //是否异步  
	cache : false, //是否使用缓存  
	type : 'post', //请求方式,post  
	data : {
		productId:$("#product-detail-product-id").val()
	},
	dataType : "json", //数据传输格式  
	url : '/shop/evaluation/queryEvaluation?v=' + new Date().getTime(), //请求链接  
	success : function(result) {
		if(result.status==1){//表示登录成功，进行跳转
			data=result.data;
			$(".lj-pj-number").text(data.length);
			var nums = 5; //每页出现的数量
			  var pages = Math.ceil(data.length/nums); //得到总页数
			  var thisDate = function(curr){
			      //此处只是演示，实际场景通常是返回已经当前页已经分组好的数据
			  var str = '', last = curr*nums - 1;
			  last = last >= data.length ? (data.length-1) : last;
			  for(var i = (curr*nums - nums); i <= last; i++){
			  	str+="<div class='pl-detail-show'>";
			  	str+="<div class='first-line-show'>";
			  	str+="<div class='pl-head-show'>";
			  	str+="<div class='head-show'>";
			  	str+="<img src='"+data[i].evaluationUserLogo+"'>";
			  	str+="</div>";
			  	var userqz=data[i].evaluationUser.substring(0,1);
			  	var userzz=data[i].evaluationUser.substring(data[i].evaluationUser.length-1,data[i].evaluationUser.length);
			  	str+="<div class='user-name-show'>"+userqz+"****"+userzz+"</div>";
				str+="</div>";
				str+="<div class='pl-gg-show'>规格："+data[i].productSpecificationName+"</div>";
				str+="<div class='pl-pf-show'>评分："+data[i].fraction+"分</div>"
				str+="<div class='pl-time-show'>"+data[i].evaluationTime+"</div>"
				str+="</div>";
				str+="<div class='pl-cetent-show'>"+data[i].evaluationContent;
				str+="</div>";
				if(data[i].evaluationPic!=null && data[i].evaluationPic!=''){
					str+="<div class='pl-img-show-box'>";
					var ulrs=$(data[i].evaluationPic.split(","));
					for(var j=0;j<ulrs.length;j++){
						str+="<div class='pl-img-show'>";
						str+="<img src='"+ulrs[j]+"'/>";
						str+="</div>";
					}
					str+="</div>";
					str+="<div class='pl-img-detail-show'></div>";
				}
				if(data[i].evaluationReplyer!=null&&data[i].evaluationReplyer!=''
					&&data[i].evaluationReplyContent!=null&&data[i].evaluationReplyContent!=''){
					str+="<div class='relay-user-show'>"+data[i].evaluationReplyer+" 回复：</div>";
				}
				if(data[i].evaluationReplyContent!=null&&data[i].evaluationReplyContent!=''){
					str+="<div class='relay-centent-show'>"+data[i].evaluationReplyContent;
					str+="</div>";
				}
				str+="</div>";
			    }
			      return str;
			  };
			  	//调用分页
			laypage({
				cont: "pl_detail_page",
				pages: pages,
				jump: function(obj){
			      $("#pl_detail_list").html(thisDate(obj.curr));
				}
			});
		}else{
		}
	},
	error : function(XMLHttpRequest, data) {
		console.log(data.msg);
	}
});
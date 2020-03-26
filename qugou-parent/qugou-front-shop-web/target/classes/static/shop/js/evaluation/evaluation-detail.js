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
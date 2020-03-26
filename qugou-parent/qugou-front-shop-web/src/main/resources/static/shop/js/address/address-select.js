//打开弹出框的低吗
$(function(){
	$('.check-button').click(function(){
		$('.address-theme-popover-mask').fadeIn(100);
		$('.address-theme-popover').slideDown(200);
		$(document).bind('mousewheel', function(event, delta) { return false; });
	});
	//关闭弹出层
	$('.address-theme-poptit .address-close').click(function(){
		$('.address-theme-popover-mask').fadeOut(100);
		$('.address-theme-popover').slideUp(200);
		$(document).unbind('mousewheel');
	});
	$(".address-conten-box input[name=customerName]").blur(function(){
		var $tip=$(this).parent().find(".address-tip");
		if($(this).val()==null||$(this).val()==''){
			$tip.css("color","#FF0036");
			$tip.text("收货人不能为空");
			$("#isError").val(0);
		}
		$tip.css("color","silver");
		$tip.text("请填写真实姓名");
		$("#isError").val(1);
	});
	$(".address-conten-box input[name=customerAddress]").blur(function(){
		var $tip=$(this).parent().find(".address-tip");
		if($(this).val()==null||$(this).val()==''){
			$tip.css("color","#FF0036");
			$tip.text("详细地址不能为空");
			$("#isError").val(0);
		}
		$tip.css("color","silver");
		$tip.text("为了方便配送，请填写详细地址");
		$("#isError").val(1);
	});
	$(".address-conten-box input[name=customerPhone]").blur(function(){
		var $tip=$(this).parent().find(".address-tip");
		var phone_reg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
		var phone=$(this).val();
		if(!phone_reg.test(phone)){
			$tip.css("color","#FF0036");
			$tip.text("手机号码不正确");
			$("#isError").val(0);
		}
		$tip.css("color","silver");
		$tip.text("重要，请输入11位数字的有效的电话号码");
		$("#isError").val(1);
	});
	$(".address-select-box select[name=city]").change(function(){
		var city=$.cookie("customer_city_key");
		var select_province=$("select[name=province] option:checked").text();
		var select_city=$("select[name=city] option:checked").text();
		if(select_city==city){
			$(".address-select-error").text("");
			$("#isError").val(1);
		}else if(select_city!=city&&select_province==city){
			$(".address-select-error").text("");
			$("#isError").val(1);
		}else{
			$(".address-select-error").text("重要提示：您选择的地区与您所在的地区不相符，影响送货，请重新选择地区");
			$("#isError").val(0);
		}
	});
	$(".address-button-box").click(function(){
		$(".address-conten-box input[name=customerName]").blur();
		$(".address-conten-box input[name=customerPhone]").blur();
		$(".address-select-box select[name=city]").change();
		if($("#isError").val()==0){
			return;
		}else{
			var province=$(".address-select-box select[name=province] option:checked").text();
			province=province==null?"":province;
			var city=$(".address-select-box select[name=city] option:checked").text();
			city=city==null?"":city;
			var area=$(".address-select-box select[name=area] option:checked").text();
			area=area==null?"":area;
			var town=$(".address-select-box select[name=town] option:checked").text();
			town=town==null?"":town;
			var detail_address=$(".address-conten-box input[name=customerAddress]").val();
			
			var address=province+city+area+town+detail_address;
			var phone=$(".address-conten-box input[name=customerPhone]").val();
			var user=$(".address-conten-box input[name=customerName]").val();
			//进行其他操作
			var html="<div class='check-line-box'>" +
					"<div class='check-radio-box'>" +
					"<input type='radio' name='address' checked value=''/>" +
					"</div>" +
					"<div class='check-font-style-1'>" +
					"<span class='check-address'>"+address+"</span><span class='check-phone'>"+phone+"</span>" +
					"<span class='check-user'>"+user+"</span></div>" +
					"</div>";
			$("#address-show-box").append(html);
			$(".pay-address").text(address);
			$(".pay-phone").text(phone);
			$(".pay-user").text(user);
			$('.address-theme-poptit .address-close').click();
		}
	});
});
$(document).ready(function(){
	var $town = $('#address_select select[name="town"]');
    var townFormat = function(info){
    $town.hide().empty();
    if(info['code']%1e4&&info['code']<7e6){	//是否为“区”且不是港澳台地区
    	$.ajax({
    		url:'http://passer-by.com/data_location/town/'+info['code']+'.json',
    		dataType:'json',
    		success:function(town){
    			$town.show();
    			for(i in town){
    					$town.append('<option value="'+i+'">'+town[i]+'</option>');
    			}
    		}
    	});
    }
    };
    $('#address_select').citys({
        onChange:function(info){
        	townFormat(info);
        }
    },function(api){
        var info = api.getInfo();
        townFormat(info);
    });
});
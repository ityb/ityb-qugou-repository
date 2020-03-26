$(document).ready(function(){
	var $town = $('#area_select select[name="town"]');
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
    $('#area_select').citys({
        onChange:function(info){
        	townFormat(info);
        }
    },function(api){
        var info = api.getInfo();
        townFormat(info);
    });
});
//点击区域选择
$("#area_btn").click(function(){
	var province=$("#area_select select[name=province] option:checked").text();
	province=province==null?"":province;
	var city=$("#area_select select[name=city] option:checked").text();
	city=city==null?"":city;
	var area=$("#area_select select[name=area] option:checked").text();
	area=area==null?"":area;
	var town=$("#area_select select[name=town] option:checked").text();
	town=town==null?"":town;
	var detail_address=$("#detail_address").val();
	//进行跳转
	window.location.href="/shop/index/areaSelect?province="+province+"&city="+city+"&district="+area+"&street="+town+"&streetNumber="+detail_address;
});
//===================================================================================================

/**
 * 这里是表单提交
 */
function area_form_submit() {
	//表单隐藏
	$("#areaModal").modal("hide");
}
/**
 * 以下是弹出框的代码
 */
function area_popup() {
    $("#areaModal").modal("show")
}
$(".top-address").on("click", area_popup),
$(".globalLogin").on("click",
function() {
    area_form_submit("login-form-tips");
}),
function() {
    var e = [];
    $(".modal").on("show.bs.modal",
    function() {
        for (var s = 0; e.length > s; s++) e[s] && (e[s].modal("hide"), e[s] = null);
        e.push($(this));
        var o = $(this),
        a = o.find(".modal-dialog"),
        t = $('<div style="display:table; width:100%; height:100%;"></div>');
        t.html('<div style="vertical-align:middle; display:table-cell;"></div>'),
        t.children("div").html(a),
        o.html(t)
    });
};
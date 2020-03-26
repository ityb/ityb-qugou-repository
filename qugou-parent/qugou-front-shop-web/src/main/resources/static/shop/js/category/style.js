$(function() {
	$('.containor').on('mouseenter', function() {
		$(".sildeshow-box").hide();
		$(".nav_right").removeClass('hide');
	}).on('mouseleave', function() {
		$(".sildeshow-box").show();
		$(".nav_right").addClass('hide');
		$(".sub").addClass('hide');
	}).on('mouseenter', 'li', function(e) {
		var li_data = $(this).attr('data-id');
		$(".sub").addClass('hide');
		$('.sub[data-id="' + li_data + '"]').removeClass('hide');
	})
})
//已经通过票，初次就显示结果
$(function(){
	skillbarAnimate();
});

//结果动画
function skillbarAnimate(){
	$('.skillbar').each(function(){
		$(this).find('.skillbar-bar').animate({
			width:$(this).attr('data-percent')
		},2000);
	});
}
//随机颜色
function randomColor(){
	var num=parseInt(Math.random()*(16777210+1),10);
    var colorNum=num.toString(16);
    return colorNum;
}

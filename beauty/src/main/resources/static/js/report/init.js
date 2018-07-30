$(function() {
    //报告列表
    $.post('/report/list',{'myFlg':true}, function(data) {
		$('#reportListTmp').tmpl(data).appendTo('#reportList');
	});
    
});


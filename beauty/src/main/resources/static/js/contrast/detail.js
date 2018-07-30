$(function() {
	
	$('#consult').on('click',function(){
		$.post('/statistics/consult');
		location.href="https://www11.53kf.com/m.php?cid=72159345&style=6&arg=10159345";
	});
	
	
	apiready = function(){
        api.parseTapmode();
    }
    var tab = new auiTab({
        element:document.getElementById("tab"),
    },function(ret){
        if(ret){
            $(".c-contrast-container .contrast-tab-info").hide();
            $('.c-contrast-container .contrast-tab-info'+ret.index+'').show();
        }
    });
    
    var pid1 = $('#title1').attr('data-id');
    var pid2 = $('#title2').attr('data-id');
    
    $.post('/contrast/label/'+pid1+'/'+pid2, function(data) {
    	var labels1 = data[pid1];
    	var labels2 = data[pid2];
    	var labelName1 ='';
    	var labelName2 ='';
    	$.each(labels1,function(i,n){
    		labelName1+=' '+n.labelName;
    	});
    	$.each(labels2,function(i,n){
    		labelName2+=' '+n.labelName;
    	});
    	
		$('#label1').text(labelName1);
		$('#label2').text(labelName2);
	});
    
    //安全分析
    $.post('/ingredient/safeAnalyze',{'productId':pid1}, function(data) {
    	var html = "";
    	$.each(data,function(i,n){
    		html+='<div class=" c-text-color-3 c-border-radius aui-margin-t-10"> ';
    		html+='		<p class="aui-text-center">'+n.key+':'+n.value+'种</p>    ';
    		html+='</div>                                                        ';
    	});
    	$('#safe1').html(html);
    });
    
    $.post('/ingredient/safeAnalyze',{'productId':pid2}, function(data) {
    	var html = "";
    	$.each(data,function(i,n){
    		html+='<div class=" c-text-color-3 c-border-radius aui-margin-t-10"> ';
    		html+='		<p class="aui-text-center">'+n.key+':'+n.value+'种</p>    ';
    		html+='</div>                                                        ';
    	});
    	$('#safe2').html(html);
    });
    
    
    //功效分析
    $.post('/ingredient/effectAnalyze',{'productId':pid1}, function(data) {
    	var html = "";
    	$.each(data,function(i,n){
    		html+='<div class=" c-text-color-3 c-border-radius aui-margin-t-10"> ';
    		html+='		<p class="aui-text-center">'+n.key+':'+n.value+'种</p>    ';
    		html+='</div>                                                        ';
    	});
    	$('#effect1').html(html);
    });
    $.post('/ingredient/effectAnalyze',{'productId':pid2}, function(data) {
    	var html = "";
    	$.each(data,function(i,n){
    		html+='<div class=" c-text-color-3 c-border-radius aui-margin-t-10"> ';
    		html+='		<p class="aui-text-center">'+n.key+':'+n.value+'种</p>    ';
    		html+='</div>                                                        ';
    	});
    	$('#effect2').html(html);
    });

    
    //肤质匹配
    $.post('/product/match',{'productId':pid1}, function(data) {
    	 var score = data.data;
		 if(score!=null && score>0){
			var html="";
			for (var i = 1; i <= score; i++) {
				html+="<i class=\"icon hufuiconfont hufuicon-xing1 star1\"></i>";
			}
			var remain = 5-score;
			for (var i = 1; i <= remain; i++) {
				html+="<i class=\"icon hufuiconfont hufuicon-xing star\"></i>";
			}
			html+='<p class="scoreDesc">'+score+'分</p>';
			$('#score1').html(html);
		 }
    	
    });
    
    $.post('/product/match',{'productId':pid2}, function(data) {
    	var score = data.data;
		 if(score!=null && score>0){
			var html="";
			for (var i = 1; i <= score; i++) {
				html+="<i class=\"icon hufuiconfont hufuicon-xing1 star1\"></i>";
			}
			var remain = 5-score;
			for (var i = 1; i <= remain; i++) {
				html+="<i class=\"icon hufuiconfont hufuicon-xing star\"></i>";
			}
			html+='<p class="scoreDesc">'+score+'分</p>';
			$('#score2').html(html);
		 }
    });
    
    //成分数量
    $.post('/ingredient/count',{'productId':pid1}, function(data) {
    	var count = data.count;
    	$('#count1').text('总成分：'+count+'种');
    });
    
    $.post('/ingredient/count',{'productId':pid2}, function(data) {
    	var count = data.count;
    	$('#count2').text('总成分：'+count+'种');
    });
    
    
});
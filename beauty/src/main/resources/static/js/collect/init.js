$(function() {
	$.post('/collect/listData', function(data) {
		$('#listTmp').tmpl(data).appendTo('#list');
		$('.aui-col-xs-12').each(function(){
			var item =$(this);
			var productId=item.attr('data-id');
			//标签
			$.post('/product/showLabelAjax',{'id':productId}, function(data) {
				var labelsArr = data.labels.split(' ');
				 var length = 3;
				 if(labelsArr.length<=3){
					 length = labelsArr.length;
				 }
				 var html="";
				 for(var i=0;i<length;i++){
					 html+="<span class='labelItem' >"+labelsArr[i]+"</span>"
				 }
				 item.find(".label").html(html);
//				item.find(".label").text(data.labels+" ");
			});
			//安全成分个数
			$.post('/ingredient/ingredientAnalyze',{'productId':productId}, function(data) {
				var score = data.layer1/(data.layer1+data.layer2+data.layer3)*5;
				 score = score.toFixed(1);
				 var cls = 'cr';
				 if(score>=4){
					 cls = 'cg'; 
				 }else if(score>=3){
					 cls = 'cy'; 
				 }
				 item.find(".safe").text("安全"+score+"分").addClass(cls);
			});
			$.post('/product/match',{'productId':productId}, function(data) {
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
//					item.find(".grade").html(html);
					item.find(".notTest").addClass('grade').removeClass('notTest').html(html);
					
				 }
			});
		});
	});
	
	
});
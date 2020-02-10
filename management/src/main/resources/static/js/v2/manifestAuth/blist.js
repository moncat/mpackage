var mid = getId()
$(function() {
	
	getData(0);
	
	$('.layui-tab-title li').on('click', function() {
		var index = $(this).data('index');
		getData(index);
	});
	
	
	$('.month button').on('click', function() {
		var barId=$(this).parent().data('bar');
		var id = $(this).data('id');
		$(this).addClass('btn-primary');
		$(this).siblings().removeClass('btn-primary');
		var bar = way.get(barId);
		if(bar !=undefined){
			bar(echarts.init($('#'+barId).get(0)),bar,id);
		}
	});
	
	
	ajaxPage("/manifestData/mp/"+mid,"pPage","pListTmp","pList");
	ajaxPage("/manifestData/mb/"+mid,"bPage","bListTmp","bList");
	ajaxPage("/manifestData/me/"+mid,"ePage","eListTmp","eList");
	ajaxPage("/manifestData/mc/"+mid,"cPage","cListTmp","cList");
	ajaxPage("/manifestData/mi/"+mid,"iPage","iListTmp","iList");
	
	$(document).on('click','.showMoreE',function(){
		var  id = $(this).attr('data-id');
		l2('/product/tab3/'+id,'1051px','680px');
	});
	$(document).on('click','.showMoreB',function(){
		var  id = $(this).attr('data-id');
		l2('/product/tab2/'+id,'1051px','680px');
	});
	$(document).on('click','.showMoreI',function(){
		var  id = $(this).attr('data-id');
		l2('/ingredient/showMore2/'+id,'1051px','680px');
	});
})

function getData(flg){
	if(flg ==0){
		var pie211 = echarts.init($('#pie211').get(0));
		var pie213 = echarts.init($('#pie213').get(0));
		pie211.showLoading();
		pie213.showLoading();
	}else if(flg ==2){
		var pie231 = echarts.init($('#pie231').get(0));
		var cn233 = echarts.init($('#cn233').get(0));
		pie231.showLoading();
		cn233.showLoading();
	}else if(flg ==3){
		var pie241 = echarts.init($('#pie241').get(0));
		var bar242 = echarts.init($('#bar242').get(0));
		pie241.showLoading();
		bar242.showLoading();
	}else if(flg ==4){
		var pie251 = echarts.init($('#pie251').get(0));
		var line252 = echarts.init($('#line252').get(0));
		pie251.showLoading();
		line252.showLoading();
	}
 		
	$.post('/manifestAuth/result/'+mid+'/2',function(data){
		var list = data.list;
		var arr = ["211","213","231","233","241","242","251","252"];
		for(var i=0 ; i<list.length ; i++){
			var one = list[i];
			var keyId = one.keyId;
			removeArray(arr, keyId);
			var jsondata = JSON.parse(one.jsondata);
			if(one.keyId==200 && flg == 0){
				baseData('b200',jsondata);
			}else if(one.keyId==211 && flg == 0){
				pie(pie211,jsondata);
			}else if(one.keyId==212 && flg == 0){
				process($('#top212'),jsondata);
			}else if(one.keyId==213 && flg == 0){
				pie(pie213,jsondata);
			}else if(one.keyId==214 && flg == 0){
				var tmp = {'jsondata':jsondata}
				$('#top214').empty();
				$('#topTmp').tmpl(tmp).appendTo('#top214');	
			}else if(one.keyId==230 && flg == 2){
				way.set("b230", jsondata);
			}else if(one.keyId==231 && flg == 2){
				pie(pie231,jsondata);			
			}else if(one.keyId==232 && flg == 2){
				var tmp = {'jsondata':jsondata}
				$('#top232').empty();
				$('#topTmp').tmpl(tmp).appendTo('#top232');				
			}else if(one.keyId==233 && flg == 2){
				cn(cn233,jsondata);
			}else if(one.keyId==241 && flg == 3){
				pie(pie241,jsondata);	
			}else if(one.keyId==242 && flg == 3){
				way.set("bar242", jsondata);
				bar(bar242,jsondata,1);
			}else if(one.keyId==251 && flg == 4){
				pie(pie251,jsondata);
			}else if(one.keyId==252 && flg == 4){
				line(line252,jsondata);
			}
		}
		if(flg ==0){
			if(arr.indexOf("211")>=0){pie211.hideLoading(); $("#pie211").text("暂无数据"); }
			if(arr.indexOf("213")>=0){pie213.hideLoading(); $("#pie213").text("暂无数据"); }
		}else if(flg ==2){
			if(arr.indexOf("231")>=0){pie231.hideLoading(); $("#pie231").text("暂无数据"); }
			if(arr.indexOf("233")>=0){ cn233.hideLoading(); $("#cn233").text("暂无数据"); }
		}else if(flg ==3){
			if(arr.indexOf("241")>=0){pie241.hideLoading(); $("#pie241").text("暂无数据"); }
			if(arr.indexOf("242")>=0){bar242.hideLoading(); $("#bar242").text("暂无数据");  }  
		}else if(flg ==4){
			if(arr.indexOf("251")>=0){pie251.hideLoading(); $("#pie251").text("暂无数据"); }
			if(arr.indexOf("252")>=0){ine252.hideLoading(); $("#line252").text("暂无数据"); }
		}
		
		
	})
	
}





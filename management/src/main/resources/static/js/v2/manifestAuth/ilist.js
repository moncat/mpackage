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
		var pie411 = echarts.init($('#pie411').get(0));
		var pie412 = echarts.init($('#pie412').get(0));
		pie411.showLoading();
		pie412.showLoading();
	}else if(flg ==1){
		var pie421 = echarts.init($('#pie421').get(0));
		var pie422 = echarts.init($('#pie422').get(0));
		pie421.showLoading();
		pie422.showLoading();
	}else if(flg ==2){
		var pie431 = echarts.init($('#pie431').get(0));
		var cn433 = echarts.init($('#cn433').get(0));
		pie431.showLoading();
		cn433.showLoading();
	}else if(flg ==3){
		var pie441 = echarts.init($('#pie441').get(0));
		var bar442 = echarts.init($('#bar442').get(0));
		pie441.showLoading();
		bar442.showLoading();
	}

	$.post('/manifestAuth/result/'+mid+'/4',function(data){
		var list = data.list;
		var arr = ["411", "412", "421", "422", "431", "433", "441", "442"];
		for(var i=0 ; i<list.length ; i++){
			var one = list[i];
			var keyId = one.keyId;
			removeArray(arr, keyId);
			var jsondata = JSON.parse(one.jsondata);
			if(one.keyId==400 && flg == 0){
				baseData('i400',jsondata);
			}else if(one.keyId==411 && flg == 0){
				pie(pie411,jsondata);
			}else if(one.keyId==412 && flg == 0){
				pie(pie412,jsondata);
			}else if(one.keyId==421 && flg == 1){
				pieBrand(pie421,jsondata);
			}else if(one.keyId==422 && flg == 1){
				pie(pie422,jsondata);
			}else if(one.keyId==423 && flg == 1){
				var tmp = {'jsondata':jsondata}
				$('#top423').empty();
				$('#topTmp').tmpl(tmp).appendTo('#top423');	
			}else if(one.keyId==430 && flg == 2){
				baseData('i430',jsondata);
			}else if(one.keyId==431 && flg == 2){
				pie(pie431,jsondata);			
			}else if(one.keyId==432 && flg == 2){
				var tmp = {'jsondata':jsondata}
				$('#top432').empty();
				$('#topTmp').tmpl(tmp).appendTo('#top432');				
			}else if(one.keyId==433 && flg == 2){
				cn(cn433,jsondata);
			}else if(one.keyId==441 && flg == 3){
				pie(pie441,jsondata);	
			}else if(one.keyId==442 && flg == 3){
				way.set("bar442", jsondata);
				bar(bar442,jsondata,1);
			}
		}
		
		if(flg ==0){
			if(arr.indexOf("411")>=0){pie411.hideLoading(); $("#pie411").text("暂无数据"); }
			if(arr.indexOf("412")>=0){pie412.hideLoading(); $("#pie412").text("暂无数据"); }
		}else if(flg ==1){
			if(arr.indexOf("421")>=0){pie421.hideLoading(); $("#pie421").text("暂无数据"); }
			if(arr.indexOf("422")>=0){pie422.hideLoading(); $("#pie422").text("暂无数据"); }
		}else if(flg ==2){
			if(arr.indexOf("431")>=0){pie431.hideLoading(); $("#pie431").text("暂无数据"); }
			if(arr.indexOf("433")>=0){ cn433.hideLoading();  $("#cn433").text("暂无数据");  }  
		}else if(flg ==3){
			if(arr.indexOf("441")>=0){pie441.hideLoading(); $("#pie441").text("暂无数据"); }
			if(arr.indexOf("442")>=0){bar442.hideLoading(); $("#bar442").text("暂无数据"); }
		}
	})
	
}



var mid = getId();
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
		l2('/enterprise/index/'+id,'1051px','680px');
	});
	$(document).on('click','.showMoreB',function(){
		var  id = $(this).attr('data-id');
		l2('/brand/index/'+id,'1051px','680px');
	});
	$(document).on('click','.showMoreI',function(){
		var  id = $(this).attr('data-id');
		l2('/ingredient/index/'+id,'1051px','680px');
	});
	$(document).on('click','.showMoreP',function(){
		var  id = $(this).attr('data-id');
		l2('/mall/addMore/'+id,'800px','517px','设置产品链接');	
	});
	
});

function getData(flg){
	if(flg ==0){
		var pie111 = echarts.init($('#pie111').get(0));
		var bar112 = echarts.init($('#bar112').get(0));
		pie111.showLoading();
		bar112.showLoading();
	}else if(flg ==1){
		var pie121 = echarts.init($('#pie121').get(0));
		var pie122 = echarts.init($('#pie122').get(0));
		pie121.showLoading();
		pie122.showLoading();
	}else if(flg ==2){
		var pie131 = echarts.init($('#pie131').get(0));
		var cn133 = echarts.init($('#cn133').get(0));
		pie131.showLoading();
		cn133.showLoading();
	}else if(flg ==3){
		var pie141 = echarts.init($('#pie141').get(0));
		var bar142 = echarts.init($('#bar142').get(0));
		pie141.showLoading();
		bar142.showLoading();
	}else if(flg ==4){
		var pie151 = echarts.init($('#pie151').get(0));
		var line152 = echarts.init($('#line152').get(0));
		pie151.showLoading();
		line152.showLoading();
	}
	$.post('/manifestAuth/result/'+mid+'/1',function(data){
		var list = data.list;
		var arr = ["111", "112", "121", "122", "131", "133", "141", "142", "151", "152"];
		for(var i=0 ; i<list.length ; i++){
			var one = list[i];
			var keyId = one.keyId;
			removeArray(arr, keyId);
			var jsondata = JSON.parse(one.jsondata);
			if(one.keyId==100 && flg == 0){				
				baseData('p100',jsondata);
			}else if(one.keyId==111 && flg == 0){
				pie(pie111,jsondata);
			}else if(one.keyId==112 && flg == 0){
				way.set("bar112", jsondata);
				bar(bar112,jsondata,1);
			}else if(one.keyId==121 && flg == 1){
				pieBrand(pie121,jsondata);				
			}else if(one.keyId==122 && flg == 1){
				pie(pie122,jsondata);			
			}else if(one.keyId==123 && flg == 1){
				var tmp = {'jsondata':jsondata}
				$('#top123').empty();
				$('#topTmp').tmpl(tmp).appendTo('#top123');				
			}else if(one.keyId==130 && flg == 2){
				baseData('p130',jsondata);
			}else if(one.keyId==131 && flg == 2){
				pie(pie131,jsondata);
			}else if(one.keyId==132 && flg == 2){
				var tmp = {'jsondata':jsondata}
				$('#top132').empty();
				$('#topTmp').tmpl(tmp).appendTo('#top132');	
			}else if(one.keyId==133 && flg == 2){
				cn(cn133,jsondata);
			}else if(one.keyId==141 && flg == 3){
				pie(pie141,jsondata);
			}else if(one.keyId==142 && flg == 3){
				way.set("bar142", jsondata);
				bar(bar142,jsondata,1);
			}else if(one.keyId==151 && flg == 4){
				pie(pie151,jsondata);
			}else if(one.keyId==152 && flg == 4){
				line(line152,jsondata);
			}
		}
		if(flg ==0){
			if(arr.indexOf("111")>=0){pie111.hideLoading(); $("#pie111").text("暂无数据"); }
			if(arr.indexOf("112")>=0){bar112.hideLoading(); $("#bar112").text("暂无数据"); }
		}else if(flg ==1){
			if(arr.indexOf("121")>=0){pie121.hideLoading(); $("#pie121").text("暂无数据"); }
			if(arr.indexOf("122")>=0){pie122.hideLoading(); $("#pie122").text("暂无数据"); }
		}else if(flg ==2){
			if(arr.indexOf("131")>=0){pie131.hideLoading(); $("#pie131").text("暂无数据"); }
			if(arr.indexOf("133")>=0){cn133.hideLoading();  $("#cn133").text("暂无数据");  }  
		}else if(flg ==3){
			if(arr.indexOf("141")>=0){pie141.hideLoading(); $("#pie141").text("暂无数据"); }
			if(arr.indexOf("142")>=0){bar142.hideLoading(); $("#bar142").text("暂无数据"); }
		}else if(flg ==4){
			if(arr.indexOf("151")>=0){pie151.hideLoading(); $("#pie151").text("暂无数据"); }
			if(arr.indexOf("152")>=0){line152.hideLoading();$("#line152").text("暂无数据"); }
		}
		
	})
}


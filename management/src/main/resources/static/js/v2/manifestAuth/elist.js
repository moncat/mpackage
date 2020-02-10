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
		var pie311 = echarts.init($('#pie311').get(0));
		var cn313 = echarts.init($('#cn313').get(0));
		pie311.showLoading();
		cn313.showLoading();
	}else if(flg ==1){
		var pie321 = echarts.init($('#pie321').get(0));
		var pie322 = echarts.init($('#pie322').get(0));
		pie321.showLoading();
		pie322.showLoading();
	}else if(flg ==3){
		var pie341 = echarts.init($('#pie341').get(0));
		var bar342 = echarts.init($('#bar342').get(0));
		pie341.showLoading();
		bar342.showLoading();
	}
	$.post('/manifestAuth/result/'+mid+'/3',function(data){
		var list = data.list;
		var arr = ["311","313","321","322","341","342"];
		for(var i=0 ; i<list.length ; i++){
			var one = list[i];
			var keyId = one.keyId;
			removeArray(arr, keyId);
			var jsondata = JSON.parse(one.jsondata);
			if(one.keyId==300 && flg == 0){
				baseData('e300',jsondata);
			}else if(one.keyId==311 && flg == 0){
				pie(pie311,jsondata);
			}else if(one.keyId==312 && flg == 0){
				var tmp = {'jsondata':jsondata}
				$('#top312').empty();
				$('#topTmp').tmpl(tmp).appendTo('#top312');
			}else if(one.keyId==313 && flg == 0){
				cn(cn313,jsondata);
				topcn("top314",jsondata);
			}else if(one.keyId==321 && flg == 1){
				pieBrand(pie321,jsondata);
			}else if(one.keyId==322 && flg == 1){
				pie(pie322,jsondata);
			}else if(one.keyId==323 && flg == 1){
				var tmp = {'jsondata':jsondata}
				$('#top323').empty();
				$('#topTmp').tmpl(tmp).appendTo('#top323');
			}else if(one.keyId==341 && flg == 3){
				pie(pie341,jsondata);	
			}else if(one.keyId==342 && flg == 3){
				way.set("bar342", jsondata);
				bar(bar342,jsondata,1);
			}
		}
		if(flg ==0){
			if(arr.indexOf("311")>=0){pie311.hideLoading(); $("#pie311").text("暂无数据"); }
			if(arr.indexOf("313")>=0){ cn313.hideLoading(); $("#cn313").text("暂无数据"); }
		}else if(flg ==1){
			if(arr.indexOf("321")>=0){pie321.hideLoading(); $("#pie321").text("暂无数据"); }
			if(arr.indexOf("322")>=0){pie322.hideLoading(); $("#pie322").text("暂无数据"); }
		}else if(flg ==3){
			if(arr.indexOf("341")>=0){pie341.hideLoading(); $("#pie341").text("暂无数据"); }
			if(arr.indexOf("342")>=0){bar342.hideLoading(); $("#bar342").text("暂无数据");  }  
		} 
	})
	
}

function topcn(id,arr){
	var html ='';
	if(arr.length>0){
		arr.sort(function(a,b){
			return b.value > a.value;
		});	
		var count = 0;
		for(var i = 0; i<arr.length;i++ ){
			count +=arr[i].value; 
		}
		for(var i = 0; i<arr.length;i++ ){
			html+="<tr><td>"+(i+1)+"</td><td>"+arr[i].name+"</td><td>"+arr[i].value+"</td><td>"+(arr[i].value*100/count).toFixed(2)+"%</td></tr>"
		}
		$("#"+id).html(html);
	}

}
 
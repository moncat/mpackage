$(function() {
	var str = hls("get", "contrastProduct");
	if(str != false){
		var arr = JSON.parse(str)
		$.each(arr,function(i,n){
			make(i,n);
		});
		$(document).on('click','.icon_del',function(){
			var tagNow = $(this).data("tag");
			var id = $(this).data("id");
			var flg = false;
			$("td").each(function(){
				var tag = $(this).data("tag");
				if(tag==tagNow){
					$(this).remove();
					flg = true;
				}
			})
			if(flg){
				var str = hls("get", "contrastProduct");
				if(str != false){
					var arr = JSON.parse(str)
					removeArray(arr,id);
					hlsJson("set", "contrastProduct", arr);
				}
			}
		});
	}
	
	$('#search').on('input', function() {
		var key = $(this).val();
		if(key ==""){
			return;
		}
		$.post('/contrast/poption?key='+key,function(data){
			var html = ""
			$.each(data,function(i,n){
				html+='<li data-id="'+n.id+'" style="cursor: pointer;">'+n.productName+'</li>'
			});
			$('#option').html(html);
			$('#option').on('click','li',function(){
				var id = $(this).data('id');
				var arr = new Array();
				var str = hls("get", "contrastProduct");
				if(str != false){
					arr = JSON.parse(str);
				}
				var flg = false;
				for(j = 0; j < arr.length; j++) {
					var oneId = arr[j];
					if(id == oneId){
						flg = true;
						continue;
					}			 
				}
				if(flg){
					layer.msg("该产品已添加",{icon:2,time:1000});
				}else{
					make(id,id);
					arr.push(id);
					hls("set", "contrastProduct", JSON.stringify(arr));
					layer.msg("添加完成",{icon:1,time:1000});
					
				}
			});
		})
	});
	
});

function make(i,id){
	$.post('/contrast/oneP?id='+id,function(data){
		var p = data.pvo;
		var iList = data.iList;
	//	console.log(JSON.stringify(data) );
		$('#name').append('<td data-tag='+i+' class="pro-con1"><i class="iconfont icon_del" data-id='+id+'  data-tag='+i+' >&#xe622;</i><div class="pro-name">'+p.productName+'</div> </td>')
		$('#price').append('<td data-tag='+i+'>'+operNull(p.jdPrice)+'</td>')
		$('#catagory').append('<td data-tag='+i+'>'+operNull(p.categoryName)+'</td>')
		$('#brand').append('<td data-tag='+i+'>'+operNull(p.productBrandName)+'</td>')
		$('#confirmDate').append('<td data-tag='+i+'>'+p.confirmDate+'</td>')
		$('#applySn').append('<td data-tag='+i+'>'+p.applySn+'</td>')
		if(p.isChina ==0){
			$('#isChina').append('<td data-tag='+i+'>进口备案</td>')
		}else{
			$('#isChina').append('<td data-tag='+i+'>国产备案</td>')
		}
		$('#ename').append('<td data-tag='+i+'>'+p.enterpriseName+'</td>')
		
		var penames = "";
		$.each(p.enterpriseList, function(i, n){
			  penames+='<li>'+n.enterpriseName+'</li>';
		});
		
		$('#pename').append('<td data-tag='+i+'><ul class="list">'+penames+'</ul></td>')
		$('#iCount').append('<td data-tag='+i+'>'+iList.length+'</td>')
		$('#iScore').append('<td data-tag='+i+'><div id="star'+i+'" data-score="'+p.iscore+'" ></div></td>')
		setScore(i);
		$('#essenceList').append('<td data-tag='+i+'><ul class="list">'+getArr(p.essenceList)+'</ul></td>')
		$('#corrosionList').append('<td data-tag='+i+'><ul class="list">'+getArr(p.corrosionList)+'</ul></td>')
		$('#riskList').append('<td data-tag='+i+'><ul class="list">'+getArr(p.riskList)+'</ul></td>')
		$('#cleanList').append('<td data-tag='+i+'><ul class="list">'+getArr(p.cleanList)+'</ul></td>')
		$('#activeList').append('<td data-tag='+i+'><ul class="list">'+getArr(p.activeList)+'</ul></td>')
		
		var is = "";
		$.each(iList, function(i, n){
			  is+='<li>'+n.name+'</li>';
		});
		$('#iList').append('<td data-tag='+i+'><ul class="list">'+is+'</ul></td>')
		
	})
}

function setScore(i){
	var data = $('#star'+i).data('score');
	layui.use(['rate'], function() {
		var rate = layui.rate;
		rate.render({
			elem: '#star'+i,
			value: data,
			text: true,
			half: true,
			readonly: true,
			theme: '#333333',
			setText: function(value) { //自定义文本的回调
				var arrs = {};
				this.span.text(arrs[value] || (value + "分"));
			}
		})
	});
}
function getArr(list){
	var item="";
	$.each(list, function(i, n){
		item+='<li>'+n+'</li>';
	});
	return item;
}

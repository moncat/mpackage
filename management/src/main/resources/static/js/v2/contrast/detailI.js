$(function() {
	var str = hls("get", "contrastIngredient");
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
				var str = hls("get", "contrastIngredient");
				if(str != false){
					var arr = JSON.parse(str)
					removeArray(arr,id);
					hlsJson("set", "contrastIngredient", arr);
				}
			}
			
		});
	}
	
	$('#search').on('input', function() {
		var key = $(this).val();
		if(key ==""){
			return;
		}
		$.post('/contrast/ioption?key='+key,function(data){
			var html = ""
			$.each(data,function(i,n){
				html+='<li data-id="'+n.id+'" style="cursor: pointer;">'+n.name+'</li>'
			});
			$('#option').html(html);
			$('#option').on('click','li',function(){
				var id = $(this).data('id');
				var arr = new Array();
				var str = hls("get", "contrastIngredient");
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
					layer.msg("该成分已添加",{icon:2,time:1000});
				}else{
					make(id,id);
					arr.push(id);
					hls("set", "contrastIngredient", JSON.stringify(arr));
					layer.msg("添加完成",{icon:1,time:1000});
				}
			});
		})
	});
	
});

function make(i,id){
	$.post('/contrast/oneI?id='+id,function(data){
		var ivo = data.ivo;
		$('#name').append('<td data-tag='+i+' class="pro-con1"><i class="iconfont icon_del" data-id='+id+'  data-tag='+i+' >&#xe622;</i><div>'+ivo.name+'</div> </td>')
		$('#securityRisks').append('<td data-tag='+i+'>'+operNull(ivo.securityRisks)+'</td>');
		$('#casNum').append('<td data-tag='+i+'>'+operNull(ivo.casNum)+'</td>');
		$('#activeIngredient').append('<td data-tag='+i+'>'+operInfo(ivo.activeIngredient)+'</td>');
		$('#blainRisk').append('<td data-tag='+i+'>'+operInfo(ivo.blainRisk)+'</td>');
		$('#aims').append('<td data-tag='+i+'>'+operNull(ivo.aims)+'</td>');
		$('#description').append('<td data-tag='+i+'>'+operNull(ivo.description)+'</td>');
		$('#pnum').append('<td data-tag='+i+'>'+operNull(ivo.pnum)+'</td>');
		$('#eenum').append('<td data-tag='+i+'>'+operNull(ivo.eenum)+'</td>');
		$('#bnum').append('<td data-tag='+i+'>'+operNull(ivo.bnum)+'</td>');
		
		$('#catagory').append('<td data-tag='+i+'><div class="catagory'+i+'" style="height: 250px;"></div></td>');
		var cValues = data.cValues;
		if(cValues.length>0){
			var cNames = data.cNames;
			var max =0;
			for(var j=0;j<cValues.length;j++){
				if(cValues[j]>max){
					max = cValues[j];
				}
			}
			var cArr=[];
			for(var j=0;j<cNames.length;j++){
				cArr.push({"name":cNames[j],"max":max})
			}
			radar(".catagory"+i,cArr,cValues);
		}else{
			$('.catagory'+i).html("-");
		}
	})
}
 
function getArr(list){
	var item="";
	$.each(list, function(i, n){
		item+='<li>'+n+'</li>';
	});
	return item;
}


function operInfo(str) {
	if(str == "null" || str == null){
		return "-";
	}
	if(str==0){
		return "无";
	}
	if(str==1){
		return "有";
	}
	return str;
}

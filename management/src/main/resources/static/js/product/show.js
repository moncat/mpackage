$(function(){
	
	var id = $('#id').val();
	$.post('/product/spec?id='+id,function(data){
		var specList=data.specList;		
		way.set("specList", specList );
	});
	
	
	//品牌
	$.post('/product/brand?id='+id,function(data){
	   var brand=data.brand;	
	   if(brand != null){
		   var html=template('brandTmp',brand);
		   $(".brand").html(html);
	   }
	});
		
	
	$.post('/product/comment?id='+id,function(data){
		var cs=data.cs;
		var commentList=data.commentList;
		way.set("cs",cs);
		var html = "";
		$.each(commentList,function(i,obj){
			html+='<tr class="text-c">';
			html+='<td>'+obj.source+'</td>';
			html+='<td>'+obj.datetime+'</td>';
			html+='<td>'+obj.userNickName+'</td>';
			html+='<td>'+obj.detail+'</td>';
			html+='</tr>';
		});
		$('#commentList').append(html);
	});
	
	$.post('/product/pic?id='+id,function(data){
		var one=data.one;		
		var productImageList=data.productImageList;		
		var jdImageList=data.jdImageList;		
		var tmallImageList=data.tmallImageList;	
		var imgHtml = '';
		if(one.bevolMid != null && one.bevolMid != "" ){
			imgHtml += '<img style="height:300px;" src="'+productImageList[0].bevolUrl+'" />';
		}else{
			imgHtml += '<div class="text-c mt-10 f-18">';
			var showTip = '';
			$.each(productImageList,function(i,n){
				if(n.imageType == 1){
					showTip = '平面图';
				}
				if(n.imageType == 2){
					showTip = '立体图';
				}
				if(n.imageType == 3){
					showTip = '商标证、授权书';
				}
				imgHtml += '<a class="btn-link" href="'+n.imageUrl+'" target="_blank">查看'+showTip+'</a><br/>';
				imgHtml += '<a class="btn-link" href="'+n.downloadUrl+'" target="_blank">下载'+showTip+'</a><br/>';
			});
			imgHtml += '</div>';
		}
		$('.defaultImg').html(imgHtml);
		
		
		var jdHtml = '';
		$.each(jdImageList,function(i,one){
			jdHtml +='<div class="col-md-2 mt-5"><a><img class="jdThumb" src="https://img12.360buyimg.com/n4/'+one.jdUrl+'" /></a></div>'
		});
		$('.jdImg').html(jdHtml);
		var tmallHtml = '';
		$.each(tmallImageList,function(i,one){
			tmallHtml +='<div class="col-md-2 mt-5"><a><img class="tmallThumb" src="'+one.tmallUrl+'" /></a></div>'
		});
		$('.tmallImg').html(tmallHtml);
	});
	
	$('body').on('click','.jdThumb',function(){
		var url=$(this).attr('src');
		url=url.replace('/n4','/n1');
		view(url);
	});
	
	
	$('body').on('click','.tmallThumb',function(){
		var url=$(this).attr('src');
		url=url.replace('60x60','430x430');
		view(url);
	});
	
	$('body').on('click','#relieve',function(){
		var relieve = $(this);
		var bid=relieve.attr('data-id');
		var pid=id;
		
		layer.confirm('是否解除关联？', {
			  btn: ['是','否'] //按钮
			}, function(index){
				layer.close(index);
				$.post('/brand/relieve?id='+bid+'&pid='+pid,function(data){
					layer.alert(data.desc);
					relieve.parent().remove();
					layer.msg('解除成功', {icon: 1});
				});
			}, function(index){
				layer.msg('取消解除', {icon: 1});
			});
		

	});
	
	
});

function view(imgUrl){
  layer.open({
	  type: 1 //Page层类型
	  ,area: ['450px', '480px']
	  ,title: '大图查看'
	  ,shade: 0.6 //遮罩透明度
	  ,anim: 1 //0-6的动画形式，-1不开启
	  ,content: '<img src="'+imgUrl+'"></img>'
	});    
	  
}
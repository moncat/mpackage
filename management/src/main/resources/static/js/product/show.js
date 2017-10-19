$(function(){
	
	var id = $('#id').val();
	$.post('/product/spec?id='+id,function(data){
		var specList=data.specList;		
		way.set("specList", specList );
	});
	$.post('/product/comment?id='+id,function(data){
		var cs=data.cs;
		var commentList=data.commentList;
		way.set("cs",cs);
//		way.set("commentList", commentList);
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
	
});
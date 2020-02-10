$(function(){
	var id = $('#eid').text();
	$.post('/enterprise/product?id='+id,function(data){
		var productList=data.productList;
		var html = "";
		$.each(productList,function(i,obj){
			var index = i+1;
			html+='<tr class="text-c">';
			html+='<td>'+index+'</td>';
			html+='<td><a class="btn-link"  href="/product/show/'+obj.productId+'" target="_blank" >'+obj.productName+'</a></td>';
			html+='</tr>';
		});
		$('#productList').append(html);
	});
});
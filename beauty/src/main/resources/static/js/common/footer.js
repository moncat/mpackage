$(function(){
	$('#footer .aui-bar-tab-item').each(function(i){
		$(this).on('click',function(){
			switch(i){
				case 0:
					location.href="/";
					break;
				case 1:
					$.post('/statistics/consult');
					location.href="https://www11.53kf.com/m.php?cid=72159345&style=6&arg=10159345";
					break;
				case 2:
					$.post('/plan/flag',function(data){
						if(data.code ==200){
							location.href='/plan/init';
						}else{
							location.href='/question/init';				
						}
					});
					break;
				case 3:
					location.href="/center/mine";
					break;
				default:
			}
		});
			
	});
});
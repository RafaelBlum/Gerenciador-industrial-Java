$().ready(function(){
	
	
	
	
	
	
	
	
	
	
	
	
	
					
$('#btn-vermelho').click(function(){
	$('#cor').css('color', 'green');
	$('#btn-vermelho').css('margin-top', '20px');
	
	$('#ball').each(function(i){
		$(this).css('background', 'red');
	});
});
					
});
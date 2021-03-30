var arrayIdsElementsPage = new Array;
var idundefined = 'idundefined';
var classTypeString = 'java.lang.String';
var classTypeLong = 'java.lang.Long';
var classTypeDate = 'java.util.Date';
var classTypeBoolean = 'java.lang.Boolean';
var classTypeBigDecimal = 'java.math.BigDecimal';

/*CARREGA UM ARRAY GLOBAL COM OS IDS DE TODOS OS COMPONENTES DAS PÁGINAS 
 * PARA TER FACILIDADES EM OBTENÇÃO DE VALORES DOS COMPONENTES BEM COMO TRABALHAR COM AJAX.
 * */

function carregarIdElementosPagina() {
	 arrayIdsElementsPage = new Array;
	 for (form = 0 ; form <= document.forms.length; form++){
		 var formAtual = document.forms[form];
		 if (formAtual != undefined) {
			 for (i = 0; i< document.forms[form].elements.length; i++){
				 if(document.forms[form].elements[i].id != '') {
					 arrayIdsElementsPage[i] = document.forms[form].elements[i].id;
				 }
			 }	
		 }
	 }
}

/**
 * Retorno o valor do id do componente dentro do documento html passando como
 * parametro a descrição do id declarada em jsf
 * 
 * @param id
 */
function getValorElementPorId(id) {
	 carregarIdElementosPagina();
	 for (i = 0; i< arrayIdsElementsPage.length; i++){
		 var valor =  ""+arrayIdsElementsPage[i];
		 if (valor.indexOf(id) > -1) {
			return valor;
	}
 }	
	 return idundefined;
}



function logout(contextPath) {
	
	var post = 'invalidar_session';
	
	$.ajax({
		type:"POST",
		url: post
	}).always(function(resposta) { 
		document.location = contextPath + '/j_spring_security_logout';
	});
	
}

//context == http://localhost:8085/projetojsfprimefaces
//pagina == /publico/login
function invalidateSession(context, pagina) {
	document.location = (context + pagina + ".jsf");
}

function redirecionaIncioPage(context){
	pagina = "/restrito/principal" + ".jsf";	
	document.location = context + pagina;
}

function redirecionaDetalhesDoc(context){
	pagina = "/detalhes/detalhesDoc" + ".jsf";	
	document.location = context + pagina;
}

function redirecionaListaCompras(context){
	pagina = "/cadastro/compras/listar_requisicoes" + ".jsf";	
	document.location = context + pagina;
}



function validarSenhaLogin() {

	//CAPTURA OS DADOS DO USUARIO
	j_username = document.getElementById("j_username").value;
	j_password = document.getElementById("j_password").value;
	

	//TESTA CADA VARIÁVEL E CASO FALSO, RETORNA FALSO E NÃO ENVIA O FORMULÁRIO
	if(j_username === null || j_username.trim() === ''){
		alert("Informe o login!!");
		$('#j_username').focus();
		return false;
	}
	
	if(j_password === null || j_password.trim() === ''){
		alert("Informe a Senha!!");
		$('#j_password').focus();
		return false;
	}
	
	//CASO TUDO ESTEJA CORRETO, ENVIA FORMULARIO	
	return true;
}

function abrirMenupop() {
	$("#menupop").show('slow').mouseleave(function() {
		fecharMenupop();
	});
}

function fecharMenupop() {
	if ($("#menupop").is(":visible")){
		$("#menupop").hide("slow");
	}
}

/*AULA 153
 *menuGer */

/*TESTE DE UNIDADE*****************************************************************/
function initMenuSetor() {
	$(document).ready(function() {
		  $('#col_dir').hide();
		  $('#col_esq').css("width", "100%");
		  
		  $('#buttonAdd').click(function() {
		  	if ($('#col_dir').is(':visible')) {
		  	   ocultarMenuSetor();
		  	} else {
		  	  $('#col_dir').show();
		  	  $('#col_dir').animate({"rigth":"0px"}, "slow");
		  	  $('#col_esq').css("width", "80%").fadeIn(100);
		  	}
		  });
		});
	}

/*TESTE DE UNIDADE*****************************************************************/
function ocultarMenuSetor() {	
	  $('#col_dir').animate({"left":"-200px"}, "slow", function() {

	  	$('#col_esq').animate({"width": "100%"}, "slow").fadeIn(3);
	
	  }).fadeOut(5); 
}


function initTamplete() {
	$(document).ready(function() {
		  $('#menupop').hide();
		  $('#barramenu').hide();
		  $('#barramenu').css("left", "-200px");
		  $('#iniciarmenu').click(function() {
		  	if ($('#barramenu').is(':visible')) {
		  	  ocultarMenu();
		  	} else {
		  	  $('#barramenu').show();
		  	  $('#barramenu').animate({"left":"0px"}, "slow");	
		  	}
		  });
		});
	}

function ocultarMenu() {  
	  $('#barramenu').animate({"left":"-200px"}, "slow", function() {
	  	$('#barramenu').hide();
	  });
}

function gerenciaTeclaEnter() {
	
	$(document).ready(function() {
		$('input').keypress(function(e) {
			var code = null;
			code = (e.keyCode ? e.keyCode : e.which);
			return (code === 13) ? false : true;

		});

		$('input[type=text]').keydown(function(e) {
			// Obter o próximo índice do elemento de entrada de texto
			var next_idx = $('input[type=text]').index(this) + 1;

			// Obter o número de elemento de entrada de texto em um documento html
			var tot_idx = $('body').find('input[type=text]').length;

			// Entra na tecla no código ASCII
			if (e.keyCode === 13) {
				if (tot_idx === next_idx)
					// Vá para o primeiro elemento de texto
					$('input[type=text]:eq(0)').focus();
				else
					// Vá para o elemento de entrada de texto seguinte
					$('input[type=text]:eq(' + next_idx + ')').focus();
			}
		});
	});
	
}

function redirecionaPagina(context, pagina){
	pagina = pagina + ".jsf";	
	document.location = context + pagina;
}

function localeData_pt_br() {
	PrimeFaces.locales['pt'] = {
		closeText : 'Fechar',
		prevText : 'Anterior',
		nextText : 'Próximo',
		currentText : 'Começo',
		monthNames : [ 'Janeiro', 'Fevereiro', 'Marcio', 'Abril', 'Maio',
				'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro',
				'Dezembro' ],
		monthNamesShort : [ 'Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul',
				'Ago', 'Set', 'Out', 'Nov', 'Dez' ],
		dayNames : [ 'Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta',
				'Sexta', 'Sábado' ],
		dayNamesShort : [ 'Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb' ],
		dayNamesMin : [ 'D', 'S', 'T', 'Q', 'Q', 'S', 'S' ],
		weekHeader : 'Semana',
		firstDay : 0,
		isRTL : false,
		showMonthAfterYear : false,
		yearSuffix : '',
		timeOnlyTitle : 'São Horas',
		timeText : 'Tempo',
		hourText : 'Hora',
		minuteText : 'Minuto',
		secondText : 'Segundo',
		ampm : false,
		month : 'Mês',
		week : 'Semana',
		day : 'Dia',
		allDayText : 'Todo o Dia'
	};

}


function getValorElementPorIdJQuery(id) {
	var id = getValorElementPorId(id);
	if (id.trim() != idundefined) {
		 return PrimeFaces.escapeClientId(id);
	}
	
	 return idundefined;
}

function permitNumber(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if (unicode != 8 && unicode != 9) {
		if (unicode < 48 || unicode > 57) {
			return false;
		}
	}
}

function validarCampoPesquisa(valor){
	if(valor != undefined && valor.value != undefined){
		if(valor.value.trim() === ''){
			valor.value = '';
		}else{
			valor.value = valor.value.trim();
		}
	}
}


function addFocoCampo(campo) {
	var id = getValorElementPorId(campo);
	if (id != undefined){
		document.getElementById(getValorElementPorId(id)).focus();
	}
}


function addMascaraPesquisa(elemento) {
	var id = getValorElementPorIdJQuery('valorPesquisa');
	var vals = elemento.split("*");
	var campoBanco = vals[0];
	var typeCampo = vals[1];
	
	$(id).unmask();
	$(id).unbind("keypress"); 
	$(id).unbind("keyup");
	$(id).unbind("focus");
	$(id).val('');
	if (id != idundefined) {
		jQuery(function($) {
			if (typeCampo === classTypeLong) {
				$(id).keypress(permitNumber);
			}
			else if (typeCampo === classTypeBigDecimal) {	
				$(id).maskMoney({precision:4, decimal:",", thousands:"."}); 
			}
			else if (typeCampo === classTypeDate) {
				$(id).mask('99/99/9999');
			}
			else {
				$(id).unmask();
				$(id).unbind("keypress");
				$(id).unbind("keyup");
				$(id).unbind("focus");
				$(id).val('');
			}
			addFocoAoCampo("valorPesquisa");
		});
	}
}

function validaDescricao(descricao) {
	if (descricao === ' ' || descricao.trim() === '') {
		alert('TESTE VALIDAÇÃO');
	}
	 else {
		return descricao;
	}
}

function pesquisarUserDestinoPerderFocoDialog(id) {
	if (id != '') {
	 $("#usr_destinoMsgDialog").val('');
	 $("#loginDestinoMsgDialog").val('');
	 $.get("buscarUsuarioDestinoMsg?codEntidade=" + id, function(resposta) {
	        if (resposta.trim() != ''){
	        	var entidade = JSON.parse(resposta);
	        	
	        	$("#usr_destinoMsgDialog").val(entidade.ent_codigo);
	        	$("#loginDestinoMsgDialog").val(validaDescricao(entidade.ent_login));
	        }
	   });
	}
}

function handleTransfer(e) {

}

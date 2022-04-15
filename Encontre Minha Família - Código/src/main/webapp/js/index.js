let contexto;

$(document).ready(function() {
	$.getScript("login/login.js");	
});

let armazenaContexto = (CONTEXTO) => {
	contexto = CONTEXTO;
	sessionStorage.setItem('contexto', JSON.stringify(CONTEXTO));
}

let obtemContexto = () => {
	contexto = JSON.parse(sessionStorage.getItem('contexto'));
}

let deletarMorador;
let validarMorador;

/* Exibe Load Screen para as requisicoes REST globais. */
function adicionaLoader() {
	$('.content').prepend('<div class="carregando" style="justify-content: center;text-align: center; margin-left: 150px; margin-top: 300px; height: 50%"><img src="imagens/carregando.gif" /></div>');
	$('.carregando').fadeIn();
 }
 
 /* Esconde Load Screen para as requisicoes REST globais.*/
 function removeLoader() {
	$('.carregando').fadeOut().remove();
 }
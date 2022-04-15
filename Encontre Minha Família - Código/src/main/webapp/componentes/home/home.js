(() => {

	$(document).ready(function() {

		$('body').load('componentes/home/home.html', () => {

			obtemContexto();

			controlaRenderizacao();
			inicializaUsuario();
			inicializaMenu($('.content'));
			verificaUltimoAcesso();
			logout();
		});

	});

	/* Remove os componentes aos quais o usuario nao possui permissao de acesso. */
	const controlaRenderizacao = () => {
		let autorizacaoPerfil = 0;

		let usuario = contexto.usuario.tipoUsuario == 'contribuidor' ? contexto.usuario.contribuidor.permissoes : contexto.usuario.administrador.permissoes;
		
		$.each($('[lvl-acesso]'), function(index, el) {
			
			autorizacaoPerfil = $.grep(usuario, function(e) { 
				return e.nome == $(el).attr('lvl-acesso'); 
			}).length;
			
			if(autorizacaoPerfil == 0){
				$(this).remove();
			}
			
			autorizacaoPerfil = 0;
		});
	}

	const inicializaUsuario = () => {
		$('#nome-usuario').text('Olá, ' + contexto.usuario.nome);
	}

	const inicializaMenu = (area) => {

		$('.menu-link').click(function() {

			sessionStorage.setItem('paginaAtual', $(this).attr('alvo'));

			switch ($(this).attr('alvo')) {
				case 'meus_cadastros':
					$(area).load('componentes/gerenciar-cadastros/gerenciar-cadastros.html');
					$.getScript('componentes/gerenciar-cadastros/gerenciar-cadastros.js');
					break;
				case 'cadastro_morador':
					$(area).load('componentes/cadastro-morador/cadastro-morador.html');
					$.getScript('componentes/cadastro-morador/cadastro-morador.js');
					break;
				case 'perfil':
				$(area).load('componentes/perfil-usuario/perfil-usuario.html');
				$.getScript('componentes/perfil-usuario/perfil-usuario.js');
					break;
				case 'cadastrar_admin':
				$(area).load('componentes/cadastrar-admin/cadastrar-admin.html');
				$.getScript('componentes/cadastrar-admin/cadastrar-admin.js');
					break;	
				case 'gerenciar_usuario':
				$(area).load('componentes/gerenciar-usuario/gerenciar-usuario.html');
				$.getScript('componentes/gerenciar-usuario/gerenciar-usuario.js');
					break;	
				case 'gerenciar_feedback':
				$(area).load('componentes/gerenciar-feedback/gerenciar-feedback.html');
				$.getScript('componentes/gerenciar-feedback/gerenciar-feedback.js');
					break;	
				case 'feedback':
					$(area).load('componentes/feedback/feedback.html');
					$.getScript('componentes/feedback/feedback.js');
						break;	
				case 'dashboard':
					$(area).load('componentes/dashboard/dashboard.html');
					$.getScript('componentes/dashboard/dashboard.js');
						break;							
				default:
					$(area).load('componentes/home/boas-vindas.html');
					break;
			}
		});
	}

	const verificaUltimoAcesso = () => {
		let paginaAtual = sessionStorage.getItem('paginaAtual');

		if (paginaAtual != null) {
			$('[alvo="' + paginaAtual + '"]').click();

		} else {
			$('[alvo="home"]').click();
		}
	}

	const logout = () => {
		$(".sair").off('click').on('click', () => {
			
			$.ajax({
					url: "ServletLogin?action=sair",
					type: "POST",
				})
				.done(function(request, response) {
					sessionStorage.clear();
					$.getScript("login/login.js")
					//$.getScript(request.urlRedirecionamento);
				})
				.fail(function(request, response) {
					swal("Ops! Alguma coisa deu errada! Aguarde um instante antes de tentar novamente!");
				})
				.always({});
		})
	}
})();
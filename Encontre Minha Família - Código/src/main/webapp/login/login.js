(() => {

	$(function() {
		$('body').load('login/login.html', () => {
			verificaUsuarioLogado()
			acionaBotoes();
			validaForm();
		});
	});

	let verificaUsuarioLogado = () => {

		obtemContexto()

		if (contexto != null) {
			$.getScript(contexto.urlRedirecionamento);
		}
	}

	const acionaBotoes = () => {
		$("#cadastroLogin").off('click').on('click', () => {
			$.getScript('cadastro-login/cadastro-login.js');
		})

		$("#recuperaSenha").off('click').on('click', () => {
			$.getScript('recupera-senha/recupera-senha.js');
		})
	}

	const validaForm = () => {

		$("#form-login").validate({
			submitHandler: () => redireciona(),
			rules: {
				inputUsuario: {
					required: true
				},
				inputSenha: {
					required: true
				}
			},
			messages: {
				inputUsuario: {
					required: "O campo tem que ser preenchido"
				},
				inputSenha: {
					required: "O campo tem que ser preenchido"
				}
			}
		});
	}

	const redireciona = () => {

		let usuario = {
			login: $("#inputUsuario").val(),
			senha: $("#inputSenha").val()
		};

		$.ajax({
				url: "ServletLogin?action=logar",
				type: "POST",
				data: {
					usuario: JSON.stringify(usuario)
				}
			})
			.done((request, response) => {
				armazenaContexto(request);
				$.getScript(request.urlRedirecionamento);

			})
			.fail((request, response) => {
				tratarCodigoErro(request);
			})
			.always({});
	}

	const tratarCodigoErro = (request) => {
		$("#divConteudo").html('<div class="alert alert-danger alert-dismissible">' 
			+ '<a href="" class="close" data-dismiss="alert" aria-label="close">&times;</a> ' 
			+ 	request.responseText + ' </div>');
	}

})();
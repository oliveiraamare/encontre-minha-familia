(() => {
	
	$(function() {
		$('#container').load('cadastro-login/cadastro-login.html', () => {
			retornaPagina();
			validaForm();
		});
	});

	const retornaPagina = () => {
		$("#voltar").off('click').on('click', () => {
			$.getScript('login/login.js');
		})
	}

	const validarCampos = () => {
		let regex_validation = /^([a-z]){1,}([a-z0-9._-]){1,}([@]){1}([a-z]){2,}([.]){1}([a-z]){2,}([.]?){1}([a-z]?){2,}$/i;
		return regex_validation.test($("#email").val());
	}

	const validaForm = () => {

		$("#form-cadastro-login").validate({
			submitHandler: () => cadastraUsuario(),
			rules: {
				nome: {
					required: true
				},
				usuario: {
					required: true
				},
				email: {
					required: true
				},
				senha: {
					required: true
				}
			},
			messages: {
				nome: {
					required: "O campo tem que ser preenchido"
				},
				usuario: {
					required: "O campo tem que ser preenchido"
				},
				cidade: {
					required: "O campo tem que ser preenchido"
				},
				estado: {
					required: "O campo tem que ser preenchido"
				},
				email: {
					required: "O campo tem que ser preenchido"
				},
				senha: {
					required: "O campo tem que ser preenchido"
				}
			}
		});
	}

	const cadastraUsuario = () => {

		if (validarCampos()) {

			let usuario = {
				nome: $("#nome").val(),
				email: $("#email").val(),
				login: $("#usuario").val(),
				senha: $("#senha").val()
			};

			let contribuidor = {
				cidade: $("#cidade").val(),
				estado: $("#estado").val()
			}

			$.ajax({
					cache: false,
					url: "ServletUsuarioController?action=cadastrar",
					type: "POST",
					data: {
						usuario: JSON.stringify(usuario),
						contribuidor: JSON.stringify(contribuidor)
					}
				})
				.done(function(request, response) {
					armazenaContexto(request);
					$.getScript(request.urlRedirecionamento);

				})
				.fail(function(request, response) {
					swal("Ops! Alguma coisa deu errada! Aguarde um instante antes de tentar novamente!");
				})
				.always({});

		} else {
			alert('Insira um e-mail válido!')
		}
	}
})();
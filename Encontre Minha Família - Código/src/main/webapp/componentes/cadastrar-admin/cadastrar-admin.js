(() => {
	$(function() {
		mask();
		resetarForm();
		validaForm();
	});

	const mask = () => {
		$('#contato').mask('(00)00000-0000');
	}

	const resetarForm = () => {
		$("#limpar").off('click').on('click', () => {
			$('input').val('');
		});
	}

	const validarCampos = () => {
		let regex_validation = /^([a-z]){1,}([a-z0-9._-]){1,}([@]){1}([a-z]){2,}([.]){1}([a-z]){2,}([.]?){1}([a-z]?){2,}$/i;
		return regex_validation.test($("#email").val());
	}

	const validaForm = () => {

		$("#form-cadastro-admin").validate({
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
				},
				contato: {
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
				},
				contato: {
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

			let administrador = {
				contato: $("#contato").val()
			}
			
			$.ajax({
					cache: false,
					url: "ServletAdministradorController?action=cadastrar",
					type: "POST",
					data: {
						usuario: JSON.stringify(usuario),
						administrador: JSON.stringify(administrador)
					}
				})
				.done(function(request, response) {
					alert(request);
					$('input').val('');
				})
				.fail(function(request, response) {
					$("#divConteudo").html('<div class="alert alert-danger alert-dismissible">' +
						'<a href="" class="close" data-dismiss="alert" aria-label="close">&times;</a> ' +
						request.responseText + ' </div>');
				})
				.always({});

		} else {
			alert('Insira um e-mail válido!')
		}
	}
})();
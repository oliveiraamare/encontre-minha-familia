(() => {
	$(document).ready(function() {
		preencherDados();
		validaForm();
		deletar();
	});

	const preencherDados = () => {
  		$("#nome").val(contexto.usuario.nome);
		$("#login").val(contexto.usuario.login);
		$("#email").val(contexto.usuario.email);
		$("#login").val(contexto.usuario.login);

		if(contexto.usuario.tipoUsuario == 'admin') {
			$("#contato").val(contexto.usuario.administrador.contato);
			$("#contrib").hide();
		} else {
			$("#estado").val(contexto.usuario.contribuidor.estado);
			$("#cidade").val(contexto.usuario.contribuidor.cidade);
			$("#adm").hide();
		}
	}

	const validaForm = () => {

		$("#form-editar-perfil").validate({
			submitHandler: () => atualizarUsuario(),
			rules: {
				nome: { required: true },
				usuario: { required: true },
				contato: { required: true }
			},
			messages: {
				nome: { required: "O campo tem que ser preenchido" },
				usuario: { required: "O campo tem que ser preenchido" },
				contato: { required: "O campo tem que ser preenchido" }
			}
		});
	}

	const atualizarUsuario = () => {

		let usuario = {
			id: contexto.usuario.id,
			login: contexto.usuario.login,
			nome: $("#nome").val(),
			email: $("#email").val()
		}

		if (contexto.usuario.tipoUsuario == 'contribuidor') {
			atualizarContribuidor(usuario);
		} else {
			atualizarAdmin(usuario);
		}
			
	}

	const atualizarAdmin = (usuario) => {

		let administrador = {
			contato: $("#contato").val()
		}

		$.ajax({
				url: "ServletAdministradorController?action=atualizar",
				type: "POST",
				data: {
					usuario: JSON.stringify(usuario),
					administrador: JSON.stringify(administrador)
				},
			})
			.done(function(request, response) {
				swal("Dados atualizados com sucesso!");

				armazenaContexto(request);
				$('#nome-usuario').text(contexto.usuario.nome);

				preencherDados();
			})
			.fail(function(request, response) {
				swal("Ops! Alguma coisa deu errada! Aguarde um instante antes de tentar novamente!");
			})
			.always({});
	}

	const atualizarContribuidor = (usuario) => {

		let contribuidor = {
			cidade: $("#cidade").val(),
			estado: $("#estado").val()
		}

		$.ajax({
				url: "ServletUsuarioController?action=atualizar",
				type: "POST",
				data: {
					usuario: JSON.stringify(usuario),
					contribuidor: JSON.stringify(contribuidor)
				},
			})
			.done(function(request, response) {
				swal("Dados atualizados com sucesso!");

				armazenaContexto(request);
				$('#nome-usuario').text(contexto.usuario.nome);
				preencherDados();
			})
			.fail(function(request, response) {
				swal("Ops! Alguma coisa deu errada! Aguarde um instante antes de tentar novamente!");
			})
			.always({});
	}


	const deletar = () => {

		$("#deletar").off('click').on('click', () => {
			$('#divModalMensagem').modal('show');
			
			$("#confimarDeletar").off('click').on('click', () => {

				let usuario = {
					id: contexto.usuario.id
				};

				$.ajax({
					url: "ServletUsuarioController?action=deletar",
					type: "POST",
					data: {
							usuario: JSON.stringify(usuario)
					}
				})
				.done(function(request, response) {
					sessionStorage.clear();
					$.getScript(request.urlRedirecionamento);
				})
				.fail(function(request, response) {
					swal("Ops! Alguma coisa deu errada! Aguarde um instante antes de tentar novamente!");
				})
				.always({});
			});
		});		
	}
})();
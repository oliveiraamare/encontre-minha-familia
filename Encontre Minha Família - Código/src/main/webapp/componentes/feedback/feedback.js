(() => {
	$(document).ready(function() {
		validaForm();
		resetarForm();
	});

	const resetarForm = () => {
		$("#limpar").off('click').on('click', () => {

			$('input[type=text], textarea').val('');
			$('select').find('option').prop("selected", false);
		});
	}

	const validaForm = () => {

		$("#form-feedback").validate({
			submitHandler: () => cadastraFeedback(),
			rules: {
				experiencia: {
					required: true
				},
				facilidade: {
					required: true
				}
			},
			messages: {
				experiencia: {
					required: "Uma opção deve ser escolhida"
				},
				facilidade: {
					required: "Uma opção deve ser escolhida"
				}
			}
		});
	}

	const cadastraFeedback = () => {

		let feedback = {
			usuarioID: contexto.usuario.id,
			experiencia: $("#experiencia").val(),
			importancia: $("#importancia").val(),
			facilidade: $("#facilidade").val(),
			sugestao: $("#sugestao").val()
		}

		$.ajax({
				cache: false,
				url: "ServletFeedbackController?action=cadastrar",
				type: "POST",
				data: {
					feedback: JSON.stringify(feedback)
				}
			})
			.done(function(request, response) {
				swal(request);

				$('input[type=text], textarea').val('');
				$('select').find('option').prop("selected", false);
			})
			.fail(function(request, response) {
				swal("Ops! Alguma coisa deu errada! Aguarde um instante antes de tentar novamente!");
			})
			.always({});
	}
})();
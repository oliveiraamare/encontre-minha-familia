(() => {
	
   let fileUser;
	let extensao_imagem;
	let imageStr;
	let preview;

	$(document).ready(function() {
		resetarForm();
		carregarImagem();
		validaForm();
	});

	const resetarForm = () => {
		$("#limpar").off('click').on('click', () => {
			$('input').val('');
			$('input[type=text], textarea').val('');
			$('select').find('option').prop("selected", false);
			$("#fotoEmBase64").attr("src", "imagens/morador-photo.png");
		});
	}

	const carregarImagem = () => {
		$('#imagemMorador').on('input', function() {
			pegaDadosImagem();
			visualizaImg()
		});
	}

	const pegaDadosImagem = async () => {
		let image = document.getElementById("imagemMorador").files[0];
		imageStr = await fileToBase64(image);
		base64MimeType(imageStr);
	}

	const fileToBase64 = async (file) =>
		new Promise((resolve, reject) => {
			const reader = new FileReader()
			reader.readAsDataURL(file)
			reader.onload = () => resolve(reader.result)
			reader.onerror = (e) => reject(e)
		})


	function base64MimeType(encoded) {
		var result = null;

		if (typeof encoded !== 'string') {
			return result;
		}

		var mime = encoded.match(/data:([a-zA-Z0-9]+\/[a-zA-Z0-9-.+]+).*,.*/);

		if (mime && mime.length) {
			result = mime[1];
		}

		extensao_imagem = result;
		imagem = encoded
	}

	const visualizaImg = () => {

		preview = document.getElementById("fotoEmBase64");
		fileUser = document.getElementById("imagemMorador").files[0];

		let reader = new FileReader();
		reader.onloadend = function() {
			preview.src = reader.result; /*Carrega a foto na tela*/
		};

		if (fileUser) {
			reader.readAsDataURL(fileUser); /*Preview da imagem*/
		} else {
			preview.src = '';
		}
	}

	const validaForm = () => {

		$("#form-cadastro-morador").validate({
			submitHandler: () => cadastraMorador(),
			rules: {
				genero: {
					required: true
				},
				aparencia: {
					required: true
				},
				situacao: {
					required: true
				},
				bairro: {
					required: true
				},
				cidade_visto: {
					required: true
				},
				estado_visto: {
					required: true
				},
				contato: {
					required: true
				},
				local_visto: {
					required: true
				},
				referencia: {
					required: true
				}
			},
			messages: {
				genero: {
					required: "O campo tem que ser preenchido"
				},
				aparencia: {
					required: "O campo tem que ser preenchido"
				},
				situacao: {
					required: "Uma opção deve ser escolhida"
				},
				bairro: {
					required: "O campo tem que ser preenchido"
				},
				cidade_visto: {
					required: "O campo tem que ser preenchido"
				},
				estado_visto: {
					required: "O campo tem que ser preenchido"
				},
				contato: {
					required: "O campo tem que ser preenchido"
				},
				local_visto: {
					required: "O campo tem que ser preenchido"
				},
				referencia: {
					required: "O campo tem que ser preenchido"
				}
			}
		});
	}

	const cadastraMorador = () => {

		let nome = $("#nome").val() != '' || null ? $("#nome").val() : 'Desconhecido';

		let morador = {
			usuarioId: contexto.usuario.id,
			nome: nome,
			idade: $("#idade").val(),
			genero: $("#genero").val(),
			situacao: $("#situacao").val(),
			cidade: $("#cidade").val(),
			estado: $("#estado").val(),
			contato: $("#contato").val(),
			aparencia: $("#aparencia").val()
		}

		let imagem = {
			imagem: imageStr,
			extensaoImagem: extensao_imagem
		}

		let imagemFinal = JSON.stringify(imageStr === undefined ? imagemPadrao() : imagem);

		let ultimaLocalidade = {
			estado: $("#estado").val(),
			cidade: $("#cidade_visto").val(),
			localVisto: $("#local_visto").val(),
			bairro: $("#bairro").val(),
			pontoReferencia: $("#referencia").val()
		}

		$.ajax({
				url: "ServletMoradorController?action=cadastrar",
				type: "POST",
				data: {
					morador: JSON.stringify(morador),
					imagem: imagemFinal,
					ultimaLocalidade: JSON.stringify(ultimaLocalidade)
				}
			})
			.done(function(request, response) {
				swal(request);

				$('input').val('');
				$('input[type=text], textarea').val('');
				$('select').find('option').prop("selected", false);
				$("#fotoEmBase64").attr("src", "imagens/morador-photo.png");

				preview = '';
			})
			.fail(function(request, response) {
				swal("Ops! Alguma coisa deu errada! Aguarde um instante antes de tentar novamente!");
			})
			.always({});
	}

	let imagemPadrao = () => {
		let imagem = {
			imagem: "data:image/png;base64,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",
			extensaoImagem: "image/png"
		}

		return imagem;
	}
})();
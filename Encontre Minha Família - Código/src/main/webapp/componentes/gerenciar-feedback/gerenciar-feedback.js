(() => {
	
	$(document).ready(function() {
		$.when(
			$.getScript('compartilhado/datatable.js')
		).done(() => {
			buscaFeedback();
		});
	});

	const buscaFeedback = () => {

		adicionaLoader();

		$.ajax({
				cache: false,
				url: "ServletFeedbackController?action=buscarChart",
				type: "POST",
			})
			.done(function(request, response) {
				removeLoader();

				chartExperiencia(request);
				chartFacilidade(request);
			})
			.fail(function(request, response) {
				swal("Ops! Alguma coisa deu errada! Aguarde um instante antes de tentar novamente!");
			})
			.always({});
	}

	const chartExperiencia = (request) => {

		new Chart("experiencia", {
			type: "pie",
			data: {
				labels: ["Ótima", "Boa", "Ruim", "Péssima"],
				datasets: [{
					backgroundColor: [
						"#b91d47",
						"#00aba9",
						"#2b5797",
						"#e8c3b9",
						"#1e7145"
					],
					data: [request.experiencia.otima, request.experiencia.boa, request.experiencia.ruim, request.experiencia.pessima]
				}]
			},
			options: {
				title: {
					display: true,
					fontSize: 20,
					fontStyle: 'Helvetica Neue',
					text: "Classificação dos usuários sobre a experiência utilizando a plataforma"
				}
			}
		});
	}

	const chartFacilidade = (request) => {
		new Chart("facilidade", {
			type: "pie",
			data: {
				labels: ["Ótima", "Boa", "Ruim", "Péssima"],
				datasets: [{
					backgroundColor: [
						"#b91d47",
						"#00aba9",
						"#2b5797",
						"#e8c3b9",
						"#1e7145"
					],
					data: [request.facilidade.otima, request.facilidade.boa, request.facilidade.ruim, request.facilidade.pessima]
				}]
			},
			options: {
				title: {
					display: true,
					fontSize: 30,
					fontStyle: 'Helvetica',
					text: "Classificação dos usuários sobre a facilidade de uso da plataforma"
				}
			}
		});
	}
})();
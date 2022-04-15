(() => {
	
	$(document).ready(function() {
		buscaUsuarios();
	});

	const buscaUsuarios = () => {

		let morador = {
			usuarioId: contexto.usuario.id
		}

		adicionaLoader();

		$.ajax({
			cache: false,
			url: "ServletMoradorController?action=buscarAll",
			type: "POST",
			data: {	morador: JSON.stringify(morador) }
		})
		.done(function(request, response) {
			montarCards(request);
			removeLoader();
		})
		.fail(function(request, response) {
			swal("Ops! Alguma coisa deu errada! Aguarde um instante antes de tentar novamente!");
		})
		.always({});
	}

	let montarCards = (request) => {
		let moradores = request.listaMoradores;

			$('#pagination-container').pagination({
				dataSource: moradores,
				pageSize: 6,
				size: 87, // size of list input
				pageShow: 6, // 6 page-item per page
				page: 1, // current page (default)
				limit: 10, // current limit show perpage (default),
				
				callback: function(data, pagination) {
					template(moradores);
					ativaBuscaCard(moradores);
				}
			})
	}

	let ativaBuscaCard = (data) => {
	
		$("#btnProcurar").off('click').on('click', () => {		
			
			if($('#buscar').val()) {					
				
				let morador = data.filter( element => (element.nome).toUpperCase() == $('#buscar').val().toUpperCase());
	
				if(typeof morador !== 'undefined' && morador.length > 0) {
					template(morador);
				} else {
					swal("Nome não encontrado!");
					template(data);
				}
			}	else {
				template(data);
			}
		});	
		
	}

	var template = function(data) {
		let div = '<div class="card-deck">';
					
		data.forEach(element => {

			div +=  
				`<div class="col-xs-12 col-sm-6 col-md-4">
					<div class="image-flip" ontouchstart="this.classList.toggle( hover);">
						<div class="mainflip">
							<div class="frontside">
								<div class="card align-items-stretch">
									<div class="card-body text-center">	
										<p>
											<img class="img-thumbnail" src="${element.imagem.imagem}" alt="card image">
										</p>  
										
										<h4 class="card-title">${element.nome}, ${element.idade} anos</h4>
										
										<p class="card-text" style="text-align: justify; text-justify: inter-word;">Minha última localização conhecida foi no bairro <b><strong>${element.ultimalocalidade.bairro}</strong></b>, localizado na cidade <b><strong>${element.ultimalocalidade.cidade}</strong></b> do estado <b><strong>${element.ultimalocalidade.estado}</strong></b>.</p>

										<p class="card-text" style="text-align: justify; text-justify: inter-word;">Fui visto no local <b><strong>${element.ultimalocalidade.localVisto}</strong></b>, tendo como ponto de referência:<b><strong>${element.ultimalocalidade.pontoReferencia}</strong></b>.</p>
									</div>
								</div>
							</div> 

							<div class="backside">
								<div class="card"> 
									<div class="card-body text-center mt-4"> 
										<h4 class="card-title">Mais sobre mim</h4>
										<ol style="list-style-type: none; padding: 0; margin: 0; text-align: left;">
											<li><strong>Minha situação atual:</strong> ${element.situacao}.</li>
											<li><strong>Minha aparência atual é:</strong> ${element.aparencia}.</li>
											<li><strong>Meu gênero é:</strong> ${element.genero}.</li>
											<li><strong>Meu estado de origem é:</strong> ${element.estado}.</li>
											<li><strong>Forma que possam entrar em contato comigo:</strong> ${element.contato}.</li>
										    <li><strong>Minha cidade de origem é:</strong> ${element.cidade}.</li>
										</ol>
									</div>
								</div>
							</div> 
						</div>
					</div>
				</div>`	
		});

		div += '</div>';

		$('#cards-container').html(div);
	}
})();
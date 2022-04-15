(() => {
	
	$(document).ready(function() {
		buscaUsuarios();
	});

	const buscaUsuarios = () => {
alert()
		let morador = {
			usuarioId: contexto.usuario.id
		}

		adicionaLoader();

		$.ajax({
			cache: false,
			url: "ServletMoradorController?action=buscar",
			type: "POST",
			data: {	morador: JSON.stringify(morador) }
		})
		.done(function(request, response) {
			console.log(request, response);

			let moradores = request.listaMoradores;

			if(!jQuery.isEmptyObject(moradores)){
			
				$('#pagination-container').pagination({
					dataSource: moradores,
					pageSize: 1,
					page: 1, // current page (default)

					callback: function(data, pagination) {					
						template(data);
					}
				})

				removeLoader();
				
			} else {
				removeLoader();
				$('#noData').show();
			}
		})
		.fail(function(request, response) {
			console.log(request, response);
		})
		.always({});
	}

	var template = function(data) {
		let div = '<div class="card-deck" style="padding-bottom: 30px">';
		console.log("template: "+ data)
		data.forEach(element => {

			div += 
			`<div class="card justify-content-center" id="${element.id}">
				<img src="${element.imagem.imagem}" width="100%" height="300vw" style="object-fit: cover;" />
				<hr>

				<form id="form-cadastro-morador">

				<div class="card-header"> 
					Informações sobre o Morador
				</div>
				<div class="form-row">
					<div class="form-group col-md-4">
						<label for="nome">Nome / Apelido: </label>
						<input id="nome" name="nome" type="text" class="form-control" value="${element.nome}">
					</div>
					<div class="form-group col-md-4">
						<label for="idade">Idade:</label>
						<input id="idade" name="idade" type="text" class="form-control" value="${element.idade}">
					</div>
					<div class="form-group col-md-4">
					<label for="genero">Gênero:</label>
						<select id="genero" name="genero" class="form-control">
							<option selected value="${element.genero}">${element.genero}</option>
							<option value="feminino">Feminino</option>
							<option value="masculino">Masculino</option>
						</select>
					</div>
					
				</div>

				<div class="form-row">
					<div class="form-group col-md-4">
						<label for="situacao">Situação atual:</label>
						<select id="situacao" name="situacao" class="form-control">
							<option selected value="${element.situacao}">${element.situacao}</option>
							<option value="em_situacao_rua">Em situação de rua</option>
							<option value="utiliza_abrigo">Faz uso de Abrigos</option>
							<option value="desconhecido">Desconhecido</option>
						</select>
					</div>
					<div class="form-group col-md-4">
						<label for="cidade">Cidade</label>
						<input id="cidade" name="cidade" type="text" class="form-control" value="${element.cidade}">
					</div>
					<div class="form-group col-md-4">
						<label for="estado">Estado:</label>
						<input id="estado" name="estado" type="text" class="form-control" value="${element.estado}">
					</div>
				</div>

				<div class="form-row">
					<div class="form-group col-md-12">
						<label for="contato">Forma de contato:</label>
						<textarea id="contato" name="contato" type="text" class="form-control">${element.contato}</textarea>
					</div>
				</div>

				<div class="form-row">
					<div class="form-group col-md-12">
						<label for="aparencia">Descrição da aparência atual:</label>
						<textarea id="aparencia" name="aparencia" type="text" class="form-control">${element.aparencia}</textarea>
					</div>
				</div>

				<div class="card-header"> 
					Informações sobre o último paradeiro conhecido 
				</div>
				<div class="form-row">
					<div class="form-group col-md-4">
						<label for="bairro">Bairro:</label>
						<input id="bairro" name="bairro" type="text" class="form-control" value="${element.ultimalocalidade.bairro}">
					</div>
					<div class="form-group col-md-4">
						<label for="cidade_visto">Cidade:</label>
						<input id="cidade_visto" name="cidade_visto" type="text" class="form-control" value="${element.ultimalocalidade.cidade}">
					</div>
					<div class="form-group col-md-4">
						<label for="estado_visto">Estado:</label>
						<input id="estado_visto" name="estado_visto" type="text" class="form-control" value="${element.ultimalocalidade.estado}">
					</div>
				</div>

				<div class="form-row">
					<div class="form-group col-md-12">
						<label for="local_visto">Descrição do úlltimo local onde foi visto:</label>
						<textarea id="local_visto" name="local_visto" type="text" class="form-control" rows="5">${element.ultimalocalidade.localVisto}</textarea>
					</div>
				</div>
				
				<div class="form-row">
					<div class="form-group col-md-12">
						<label for="referencia">Ponto de referência:</label>
						<textarea id="referencia" name="referencia" type="text" class="form-control">${element.ultimalocalidade.pontoReferencia}</textarea>
					</div>
				</div>

				<div style="display: flex; justify-content: space-evenly;">
			
					<button onclick="validarMorador(${element.id});" type="submit" id="${element.id}" style="border:none; background:none; color:blue; float:left"> 
						Salvar edição 
					</button>
					<button onclick="deletarMorador(${element.id});" id="${element.id}" style="border:none;  background:none; color:red; float: right;"> 
							Deletar
					</button>
				</div>
				
			</form>
			</div>`
		});

		div += '</div>';

		$('#cards-container').html(div);
	}

	deletarMorador = function(id) {
		console.log('deu certo', id);

		let morador = {
			id: id
		}

		$.ajax({
			cache: false,
			url: "ServletMoradorController?action=deletar",
			type: "POST",
			data: {	morador: JSON.stringify(morador) }
		})
		.done(function(request, response) {
			console.log(request, response);
			alert(request);
			buscaUsuarios();
		})
		.fail(function(request, response) {
			console.log(request, response);
		})
		.always({});
	}

	validarMorador = function(id) {

		$("#form-cadastro-morador").validate({
			submitHandler: () => editarMorador(id),
			rules: {
				genero: { required: true },
				aparencia: { required: true },
				situacao: { required: true },
				bairro: { required: true },
				cidade_visto: { required: true },
				estado_visto: { required: true },
				contato: { required: true },
				local_visto: { required: true },
				referencia: { required: true },
			},
			messages: {
				genero: { required: "O campo tem que ser preenchido" },
				aparencia: { required: "O campo tem que ser preenchido" },
				situacao: { required: "O campo tem que ser preenchido" },
				bairro: { required: "O campo tem que ser preenchido" },
				cidade_visto: { required: "O campo tem que ser preenchido" },
				estado_visto: { required: "O campo tem que ser preenchido" },
				contato: { required: "O campo tem que ser preenchido" },
				local_visto: { required: "O campo tem que ser preenchido" },
				referencia: { required: "O campo tem que ser preenchido" },
			}
		});
	}

	const editarMorador = (id) => {

		console.log($("#estado_visto-" + id).val())
		
		let nome = $("#nome").val() != '' || null ? $("#nome").val() : 'Desconhecido';

		console.log(nome);

		let morador = {
			id: id,
			nome: nome,
			idade: $("#idade").val(),
			genero: $("#genero").val(),
			situacao: $("#situacao").val(),
			cidade: $("#cidade").val(),
			estado: $("#estado").val(),
			contato: $("#contato").val(),
			aparencia: $("#aparencia").val()
		}

		let ultimaLocalidade = {
			estado: $("#estado_visto").val(),
			cidade: $("#cidade_visto").val(),
			localVisto: $("#local_visto").val(),
			bairro: $("#bairro").val(),
			pontoReferencia: $("#referencia").val()
		}
	
		$.ajax({
				cache: false,
				url: "ServletMoradorController?action=atualizar",
				type: "POST",
				data: {
					morador: JSON.stringify(morador),
					ultimaLocalidade: JSON.stringify(ultimaLocalidade)
				}
			})
			.done(function(request, response) {
				console.log(request, response);
				alert(request);
				buscaUsuarios();
			})
			.fail(function(request, response) {
				console.log(request, response);
			})
			.always({});
	}
})();
(() => {

   $(document).ready(function() { 
		$.when(
	    	$.getScript('compartilhado/datatable.js')
	  	).done(()=>{
			buscaUsuarios();
	});

      
   });

   const buscaUsuarios = () => {
      $.ajax({  
         cache: false,
         url: "ServletAdministradorController?action=consultarAcessos",  
         type: "POST",  
        }) 
        .done(function(request, response) {
        carregaTabela(request.listaUsuarios)
        })
        .fail(function(request, response) {
        swal("Ops! Alguma coisa deu errada! Aguarde um instante antes de tentar novamente!");
        })
        .always({ }); 
   }
   
   
   const carregaTabela = (data) => {
  
        dataTable(
            '#table-gerenciar-usuarios',
            data,
            [ 
                { className: "text-center", defaultContent: '', orderable: false, searchable: false},
                { name: "login", data: "login", defaultContent: ''},
                { name: "email", data: "email", defaultContent: ''},
                { name: "nome", data: "nome", defaultContent: ''},
                { name: "tipoUsuario", data: "tipoUsuario", defaultContent: ''},
                { name: "ultimoAcesso", data: "acesso.ultimoAcesso", defaultContent: ''},
                { name: "dataCriacao", data: "acesso.dataCriacao", defaultContent: ''},
                { name: "ativo", data: "ativo", defaultContent: ''}
            ],
            [
                {
                    targets: [ 0 ],
                    createdCell: function (td, cellData, rowData, row, col) {
                        var a = "<a href='#'><span>" + (rowData.ativo == true ? 'Desabilitar Usuário' : 'Habilitar Usuário') + "</span></a>";
                        $(td).html(a);

                        $(td).find('a').click((e) => {
                            if(rowData.ativo == true){  
                               desabilitaUsuario(rowData.id);
                            } else {
                                habilitaUsuario(rowData.id);
                            }
                        });
		          }
                },
                { 
                    targets: [ 1 ],
                    title:'<span>Login</span>'
                },
                {
                    targets: [ 2 ],
                    title:'<span>E-mail</span>'
                },
                {
                    targets: [ 3 ],
                    title:'<span>Nome</span>'
                },
                {
                    targets: [ 4 ],
                    title:'<span>Tipo do Usuário</span>'
                },
                {
                    targets: [ 5 ],
                    title:'<span>Último Acesso</span>'
                },
                {
                    targets: [ 6 ],
                    title:'<span>Data de Criação</span>'
                },
                {
                    targets: [ 7 ],
                    title:'<span>Ativo</span>'
                }
            ]
        );
    }    

    const habilitaUsuario = (id) => {
        let usuario = {
            id: id
          }

        $.ajax({  
            cache: false,
            url: "ServletAdministradorController?action=habilitar",  
            type: "POST",  
            data: {
                usuario: JSON.stringify(usuario)
            }
        }) 
        .done(function(request, response) {
          //buscaUsuarios();
            carregaTabela(request.listaUsuarios);        
        })
        .fail(function(request, response) {
            swal("Ops! Alguma coisa deu errada! Aguarde um instante antes de tentar novamente!");
        })
        .always({ }); 
    }

    const desabilitaUsuario = (id) => {
        let usuario = {
            id: id
          }

        $.ajax({  
            cache: false,
            url: "ServletAdministradorController?action=desabilitar",  
            type: "POST",  
            data: {
                usuario: JSON.stringify(usuario)
            }
        }) 
        .done(function(request, response) {
          //buscaUsuarios();
            carregaTabela(request.listaUsuarios);        
        })
        .fail(function(request, response) {
            swal("Ops! Alguma coisa deu errada! Aguarde um instante antes de tentar novamente!");
        })
        .always({ }); 
    }
})();
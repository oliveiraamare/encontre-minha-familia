(() => {
  $(document).ready(function() { 

	$('#container').load('recupera-senha/recupera-senha.html', () => {
      console.log("recupera-senha")
		acionaBotoes();
      validaForm();          
    });


  });   

	const acionaBotoes = () => {
		$("#voltar").off('click').on('click', () => {
				$.getScript('login/login.js');	
		})
	}
	
  const validaForm = () => {

    $("#form-recupera-senha").validate({
      submitHandler: () => cadastraUsuario(),
      rules: {
        nome: { required: true },
        usuario: { required: true },
        email: { required: true }
      },
      messages:{
        nome: { required: "O campo tem que ser preenchido" },
        usuario: { required: "O campo tem que ser preenchido" },
        email: { required: "O campo tem que ser preenchido" }
      }
    });
  }  

  const cadastraUsuario = () => {

    let usuario = {
      nome: $("#nome").val(),
      email: $("#email").val(),
      usuario: $("#usuario").val()
    }
    
    console.log(usuario)

    $.ajax({  
        url: "ServletRecuperaSenha",  
        type: "POST",  
        data: JSON.stringify(usuario)
    }) 
    .done(function(request, response) {
      console.log('done ServletRecuperaSenha')
        console.log(request, response);
      
        $("#formulario").html('<div style="padding-bottom: 25px"><h4 class="justify-content-center">Sua senha é: </h4>' + request.usuario.senha + '</div>'
        + '<div class="form-group col-md-4"><input id="voltar" type="submit" value="Voltar" class="btn btn-secondary btn-lg" /></div>')

        
      
      })
      .fail(function(request, response) {
        console.log(request, response);
      })
      .always({ }); 
  }
})();
$(document).ready(function(){
	
	$("#endereco\\.cep").change(function(){
		var cepInformado = $("#endereco\\.cep").val().replace();
		
		console.log("Cep capturado " + cepInformado);
		
		$.ajax({
			type: "GET",
			url: "http://viacep.com.br/ws/" + cepInformado +"/json/",
			dataType: "jsonp",
			contentType: "application/json; charset=utf-8",
			success: function (retorno) {
				if (retorno != null) {
					
					if (retorno.erro) {
						alert("CEP não localizado");	
					} else {
						//Se foi tudo bem atribui os valores
						$("#endereco\\.endereco").val(retorno.logradouro);
						$("#endereco\\.complemento").val(retorno.complemento);
						$("#endereco\\.bairro").val(retorno.bairro);
						$("#endereco\\.municipio").val(retorno.localidade);
						$("#endereco\\.uf").val(retorno.uf);
						M.updateTextFields();
					}
				}
			},
			error: function() {
				alert("CEP fora do formato");		
			}
		});				
	});			
});	

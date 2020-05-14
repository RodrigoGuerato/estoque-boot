$(document).ready(function() {
	$('.sidenav').sidenav();
	$(".dropdown-trigger").dropdown();	 
	$('.tabs').tabs();
	$('.modal').modal();
	$('.datepicker').datepicker({format : "dd/mm/yyyy"});
	$('.collapsible').collapsible();
	$('select').formSelect();
});

//mascaras para dados e inputs
$(".fmt-cnpj").mask("00.000.000/0000-00");
$(".fmt-cpf").mask("000.000.000-00");
$(".fmt-cep").mask("00000-000");
$(".datepicker").mask("00/00/0000");
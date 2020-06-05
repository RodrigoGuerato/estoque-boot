$(document).ready(function() {
	$('.sidenav').sidenav();
	$(".dropdown-trigger").dropdown();	 
	$('.tabs').tabs();
	$('.modal').modal();
	$('.collapsible').collapsible();
	$('select').formSelect();
	$('.datepicker').datepicker({
		format : "dd/mm/yyyy",
		i18n: {
			months: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
			monthsShort: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
			weekdays: ['Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sabádo'],
			weekdaysShort: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab'],
			weekdaysAbbrev: ['D', 'S', 'T', 'Q', 'Q', 'S', 'S'],
			today: 'Hoje',
			clear: 'Limpar',
			cancel: 'Sair',
			done: 'Confirmar',
			labelMonthNext: 'Próximo mês',
			labelMonthPrev: 'Mês anterior',
			labelMonthSelect: 'Selecione um mês',
			labelYearSelect: 'Selecione um ano',
			selectMonths: true,
			selectYears: 15,
		}
	});
});

//mascaras para dados e inputs
$(".fmt-cnpj").mask("00.000.000/0000-00");
$(".fmt-cpf").mask("000.000.000-00");
$(".fmt-cep").mask("00000-000");
$(".datepicker").mask("00/00/0000");
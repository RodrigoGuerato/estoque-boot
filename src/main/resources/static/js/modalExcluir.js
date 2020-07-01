$(".modal-excluir").click(function() {
	
    var button    = $(this);
    var descricao = button.data('descr');
    var modal     = $("#modalExcluir");

    modal.find('.modal-content p').html('Deseja realmente excluir: <strong>'  + descricao + '</strong> ?');

    $('#btnModalSim').click(function(){
        $(button).find('form').submit();
    });
    
    modal.modal('open');
});
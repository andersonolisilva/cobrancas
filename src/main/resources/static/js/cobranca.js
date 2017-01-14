$('#confirmacaoExclusaoModal').on('show.bs.modal', function (event) {
  var button = $(event.relatedTarget); // Button that triggered the modal
  var codigoTitulo = button.data('codigo'); // Extract info from data-* attributes
  var descricaoTitulo = button.data('descricao');
  // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
  // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
  var modal = $(this);
  var form = modal.find('form');
  var action = form.data('url-base');
  if(!action.endsWith('/')){
	  action += '/';
  }
  form.attr('action', action + codigoTitulo);
  
  modal.find('.modal-body span').html('Tem certeza que deseja excluir o título <strong>'+descricaoTitulo+'</strong>?');
});


$(function(){
	$('[rel="tooltip"]').tooltip();
});
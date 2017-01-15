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
	$('.js-currency').maskMoney({decimal:',', thousands:'.', allowZero:true});
	$('.js-atualizar-status').on('click',function(event){
		//console.log('clicou');
		event.preventDefault(); //Desativa o comportamento padrão do link via get
		var botaoReceber = $(event.currentTarget);
		var urlReceber = botaoReceber.attr('href');
		//console.log('urlReceber',urlReceber);
		var response = $.ajax({
			url: urlReceber,
			type: 'PUT'
		});
		
		response.done(function(e){
			var codigoTitulo = botaoReceber.data('codigo');
			$('[data-role='+codigoTitulo+']').html('<span class="label label-success">'+e+'</span>');
			botaoReceber.hide();
		});
		
		response.fail(function(e){
			console.log(e);
			alert('Erro recebendo cobrança.');
		});
		
	});
});


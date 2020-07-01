package br.com.fapen.estoque.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fapen.estoque.enums.StatusEnum;
import br.com.fapen.estoque.forms.ItemRecebimentoForm;
import br.com.fapen.estoque.forms.RecebimentoFiltroForm;
import br.com.fapen.estoque.forms.RecebimentoForm;
import br.com.fapen.estoque.models.ItemNotaFiscal;
import br.com.fapen.estoque.models.ItemPedidoCompra;
import br.com.fapen.estoque.models.NotaFiscal;
import br.com.fapen.estoque.models.Produto;
import br.com.fapen.estoque.repositories.NotaFiscalRepository;
import br.com.fapen.estoque.repositories.Paginacao;
import br.com.fapen.estoque.repositories.ProdutoRepository;

@Service
public class RecebimentoService {

	@Autowired
	private NotaFiscalRepository notaFiscalRep;

	@Autowired
	private PedidoCompraService pedidoService;

	@Autowired
	private ProdutoRepository produtoRep;

	public void carregarItensPedido(RecebimentoForm recebimentoForm) {

		recebimentoForm.getItens().clear();
		if (recebimentoForm.getPedido() != null) {
			for (ItemPedidoCompra itemPed : recebimentoForm.getPedido().getItens()) {
				ItemRecebimentoForm itemReceb = new ItemRecebimentoForm();
				itemReceb.setProduto(itemPed.getProduto());
				itemReceb.setPrecoUnitario(itemPed.getPrecoUnitario());
				itemReceb.setQuantidade(itemPed.getQuantidade());
				itemReceb.setValorTotal(itemPed.getPrecoTotal());
				recebimentoForm.getItens().add(itemReceb);
			}
		}
	}

	@Transactional
	public void entrarEstoque(RecebimentoForm recebimentoForm) {

		NotaFiscal nota = criaObjeto(recebimentoForm);

		// Salva o Recebimento
		notaFiscalRep.save(nota);

		// Altera o Status do Pedido
		pedidoService.alteraStatus(nota.getPedido(), StatusEnum.RECEBIDO);

		// Atualiza o Saldo do Produto
		for (ItemNotaFiscal itemNfe : nota.getItensNotaFiscal()) {
			Produto prod = itemNfe.getProduto();

			prod.somaSaldo(itemNfe.getQuantidade());
			produtoRep.save(prod);
		}

		// chama outro seriço de Movimento...
		// faz outra operacao
		// manda email para o supervisor
	}

	private NotaFiscal criaObjeto(RecebimentoForm recebimentoForm) {
		NotaFiscal nfe = new NotaFiscal();

		// Corpo
		nfe.setId(recebimentoForm.getId());
		nfe.setSerieNotaFiscal(recebimentoForm.getSerieNotaFiscal());
		nfe.setNumeroNotaFiscal(recebimentoForm.getNumeroNotaFiscal());
		nfe.setDataNotaFiscal(recebimentoForm.getDataNotaFiscal());
		nfe.setDataRecebimento(recebimentoForm.getDataRecebimento());
		nfe.setPedido(recebimentoForm.getPedido());

		// Itens
		for (ItemRecebimentoForm itemReceb : recebimentoForm.getItens()) {
			ItemNotaFiscal itemNfe = new ItemNotaFiscal();

			itemNfe.setNotaFiscal(nfe);
			itemNfe.setPrecoUnitario(itemReceb.getPrecoUnitario());
			itemNfe.setProduto(itemReceb.getProduto());
			itemNfe.setQuantidade(itemReceb.getQuantidade());
			itemNfe.setValorTotal(itemReceb.getValorTotal());

			nfe.getItensNotaFiscal().add(itemNfe);
		}

		return nfe;
	}

	public Page<NotaFiscal> listar(RecebimentoFiltroForm filtroForm) {

		if (filtroForm.isNovoFiltro()) {
			filtroForm.setPagina(1);
		}
		Pageable paginacao = Paginacao.getPaginacao(filtroForm.getPagina());

		switch (filtroForm.getTipoFiltro()) {
		case "RS":
			return notaFiscalRep.findByPedidoFornecedorRazaoSocialContainingIgnoreCase(filtroForm.getRazaoSocial(),
					paginacao);
		case "DT":
			return notaFiscalRep.findByDataRecebimentoBetween(filtroForm.getDataInicial(), filtroForm.getDataFinal(),
					paginacao);
		default:
			return notaFiscalRep.findAll(paginacao);
		}
	}

	public NotaFiscal findById(Long id) {
		return notaFiscalRep.findById(id).get();
	}

	@Transactional
	public void estornar(Long id) {
		NotaFiscal nfe = this.findById(id);

		for (ItemNotaFiscal itemNfe : nfe.getItensNotaFiscal()) {
			Produto prod = itemNfe.getProduto();
			prod.subtraiSaldo(itemNfe.getQuantidade());
			produtoRep.save(prod);
		}

		pedidoService.alteraStatus(nfe.getPedido(), StatusEnum.EM_DIGITACAO);
		notaFiscalRep.delete(nfe);
	}

}

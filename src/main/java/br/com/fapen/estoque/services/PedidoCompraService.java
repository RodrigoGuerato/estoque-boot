package br.com.fapen.estoque.services;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fapen.estoque.enums.StatusEnum;
import br.com.fapen.estoque.forms.PedidoCompraForm;
import br.com.fapen.estoque.forms.PedidoFiltroForm;
import br.com.fapen.estoque.models.ItemPedidoCompra;
import br.com.fapen.estoque.models.PedidoCompra;
import br.com.fapen.estoque.reports.PedidoCompraReport;
import br.com.fapen.estoque.repositories.Paginacao;
import br.com.fapen.estoque.repositories.PedidoCompraRepository;

@Service
public class PedidoCompraService {

	@Autowired
	private PedidoCompraRepository repPedidos;

	@Autowired
	private PedidoCompraReport relImpressaoPedido;

	public void calcularTotal(PedidoCompra pedido) {
		BigDecimal total = BigDecimal.ZERO;
		for (ItemPedidoCompra item : pedido.getItens()) {
			BigDecimal qtde = new BigDecimal(item.getQuantidade());
			BigDecimal totalItem = item.getPrecoUnitario().multiply(qtde);
			total = total.add(totalItem);
		}
		pedido.setValorTotal(total);
	}

	public void salvar(PedidoCompra pedido) {
		repPedidos.save(pedido);
	}

	public void salvar(PedidoCompraForm pedidoCompraForm) {
		pedidoCompraForm.getPedidoCompra().getItens().clear();
		for (ItemPedidoCompra item : pedidoCompraForm.getItensPedidoCompra()) {
			item.setPedido(pedidoCompraForm.getPedidoCompra());
			pedidoCompraForm.getPedidoCompra().getItens().add(item);
		}
		this.calcularTotal(pedidoCompraForm.getPedidoCompra());
		repPedidos.save(pedidoCompraForm.getPedidoCompra());
	}

	public List<PedidoCompra> listar() {
		return repPedidos.findAll();
	}

	public Page<PedidoCompra> listar(PageRequest paginacao) {
		return repPedidos.findAll(paginacao);
	}

	public Page<PedidoCompra> listar(PedidoFiltroForm filtro) {

		if (filtro.isNovoFiltro()) {
			filtro.setPagina(1);
		}
		Pageable paginacao = Paginacao.getPaginacao(filtro.getPagina());

		if ("RS".equals(filtro.getTipoFiltro())) {
			return repPedidos.findByFornecedorRazaoSocialContainingIgnoreCase(filtro.getRazaoSocial(), paginacao);
		} else if ("ST".equals(filtro.getTipoFiltro())) {
			return repPedidos.findByStatus(filtro.getStatus(), paginacao);
		} else if ("DT".equals(filtro.getTipoFiltro())) {
			return repPedidos.findByDataEntregaBetween(filtro.getEntregaInicial(), filtro.getEntregaFinal(), paginacao);
		} else {
			return repPedidos.findAll(paginacao);
		}
	}

	public PedidoCompra findById(Long id) {
		Optional<PedidoCompra> optPedido = repPedidos.findById(id);
		if (optPedido.isEmpty()) {
			return null;
		}
		return optPedido.get();
	}

	public void excluir(PedidoCompra pedido) {
		repPedidos.delete(pedido);
	}

	public ByteArrayInputStream gerarPdf(PedidoCompra pedCompra) {
		return relImpressaoPedido.gerarPDF(pedCompra);

	}

	public List<PedidoCompra> listarEmDigitacao() {
		return repPedidos.findByStatus(StatusEnum.EM_DIGITACAO);
	}

	public PedidoCompra alteraStatus(PedidoCompra pedido, StatusEnum status) {
		pedido.setStatus(status);
		return repPedidos.save(pedido);
	}

}

package br.com.fapen.estoque.forms;

import java.util.ArrayList;
import java.util.List;

import br.com.fapen.estoque.models.ItemPedidoCompra;
import br.com.fapen.estoque.models.PedidoCompra;

public class PedidoCompraForm {

	private PedidoCompra pedidoCompra;
	private List<ItemPedidoCompra> itensPedidoCompra = new ArrayList<ItemPedidoCompra>();

	public PedidoCompraForm() {
	}

	public PedidoCompraForm(PedidoCompra pedido) {
		this.pedidoCompra = pedido;
		this.itensPedidoCompra = pedido.getItens();
	}

	public PedidoCompra getPedidoCompra() {
		return pedidoCompra;
	}

	public void setPedidoCompra(PedidoCompra pedidoCompra) {
		this.pedidoCompra = pedidoCompra;
	}

	public List<ItemPedidoCompra> getItensPedidoCompra() {
		return itensPedidoCompra;
	}

	public void setItensPedidoCompra(List<ItemPedidoCompra> itensPedidoCompra) {
		this.itensPedidoCompra = itensPedidoCompra;
	}
}

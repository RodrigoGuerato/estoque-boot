package br.com.fapen.estoque.forms;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.fapen.estoque.models.PedidoCompra;

public class RecebimentoForm {

	private Long id;
	private Long serieNotaFiscal;
	private Long numeroNotaFiscal;

	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dataNotaFiscal;

	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dataRecebimento;

	private PedidoCompra pedido;
	
	private List<ItemRecebimentoForm> itens = new ArrayList<ItemRecebimentoForm>();

	public RecebimentoForm() {
		this.dataRecebimento = LocalDate.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSerieNotaFiscal() {
		return serieNotaFiscal;
	}

	public void setSerieNotaFiscal(Long serieNotaFiscal) {
		this.serieNotaFiscal = serieNotaFiscal;
	}

	public Long getNumeroNotaFiscal() {
		return numeroNotaFiscal;
	}

	public void setNumeroNotaFiscal(Long numeroNotaFiscal) {
		this.numeroNotaFiscal = numeroNotaFiscal;
	}

	public LocalDate getDataNotaFiscal() {
		return dataNotaFiscal;
	}

	public void setDataNotaFiscal(LocalDate dataNotaFiscal) {
		this.dataNotaFiscal = dataNotaFiscal;
	}

	public LocalDate getDataRecebimento() {
		return dataRecebimento;
	}

	public void setDataRecebimento(LocalDate dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}

	public PedidoCompra getPedido() {
		return pedido;
	}

	public void setPedido(PedidoCompra pedido) {
		this.pedido = pedido;
	}

	public List<ItemRecebimentoForm> getItens() {
		return itens;
	}

	public void setItens(List<ItemRecebimentoForm> itens) {
		this.itens = itens;
	}

}

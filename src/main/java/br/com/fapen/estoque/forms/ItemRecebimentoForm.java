package br.com.fapen.estoque.forms;

import java.math.BigDecimal;

import br.com.fapen.estoque.models.Produto;

public class ItemRecebimentoForm {

	private Boolean confirmado;
	private Produto produto;
	private Double quantidade;
	private BigDecimal precoUnitario;
	private BigDecimal valorTotal;

	public ItemRecebimentoForm() {
		this.confirmado = false;
	}

	public Boolean getConfirmado() {
		return confirmado;
	}

	public void setConfirmado(Boolean confirmado) {
		this.confirmado = confirmado;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getPrecoUnitario() {
		return precoUnitario;
	}

	public void setPrecoUnitario(BigDecimal precoUnitario) {
		this.precoUnitario = precoUnitario;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
}

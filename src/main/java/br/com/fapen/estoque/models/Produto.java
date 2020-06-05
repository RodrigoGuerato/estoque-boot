package br.com.fapen.estoque.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity(name = "t_produto")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String descricao;

	@Column(name = "custo_unitario")
	private BigDecimal custoUnitario;

	@Column(name = "preco_venda")
	private BigDecimal precoVenda;

	@Column(length = 10)
	private String categoria;

	@Column(name = "saldo_atual")
	private Double saldoAtual;

	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "data_validade")
	private LocalDate validade;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getCustoUnitario() {
		return custoUnitario;
	}

	public void setCustoUnitario(BigDecimal custoUnitario) {
		this.custoUnitario = custoUnitario;
	}

	public BigDecimal getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(BigDecimal precoVenda) {
		this.precoVenda = precoVenda;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Double getSaldoAtual() {
		return saldoAtual;
	}

	public void setSaldoAtual(Double saldoAtual) {
		this.saldoAtual = saldoAtual;
	}

	public LocalDate getValidade() {
		return validade;
	}

	public void setValidade(LocalDate validade) {
		this.validade = validade;
	}

	public void somaSaldo(Double quantidade) {
		if (this.saldoAtual == null) {
			this.saldoAtual = 0d;
		}
		this.saldoAtual += quantidade;
	}

	public void subtraiSaldo(Double quantidade) {
		if (this.saldoAtual == null) {
			this.saldoAtual = 0d;
		}
		this.saldoAtual -= quantidade;
	}
}

package br.com.fapen.estoque.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity(name = "t_nota_fiscal")
public class NotaFiscal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long serieNotaFiscal;
	private Long numeroNotaFiscal;

	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dataNotaFiscal;

	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dataRecebimento;

	@ManyToOne
	private PedidoCompra pedido;

	@OneToMany(mappedBy = "notaFiscal", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItemNotaFiscal> itensNotaFiscal = new ArrayList<ItemNotaFiscal>();

	public List<ItemNotaFiscal> getItensNotaFiscal() {
		return itensNotaFiscal;
	}

	public void setItensNotaFiscal(List<ItemNotaFiscal> itensNotaFiscal) {
		this.itensNotaFiscal = itensNotaFiscal;
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

}

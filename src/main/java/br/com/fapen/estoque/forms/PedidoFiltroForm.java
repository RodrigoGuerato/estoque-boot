package br.com.fapen.estoque.forms;

import java.time.LocalDate;

import br.com.fapen.estoque.enums.StatusEnum;

public class PedidoFiltroForm {

	private String razaoSocial;
	private StatusEnum status;
	private LocalDate entregaInicial;
	private LocalDate entregaFinal;
	private String tipoFiltro;
	private Integer pagina;
	private boolean novoFiltro;

	public PedidoFiltroForm() {
		this.pagina = 1;
		this.novoFiltro = false;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public LocalDate getEntregaInicial() {
		return entregaInicial;
	}

	public void setEntregaInicial(LocalDate entregaInicial) {
		this.entregaInicial = entregaInicial;
	}

	public LocalDate getEntregaFinal() {
		return entregaFinal;
	}

	public void setEntregaFinal(LocalDate entregaFinal) {
		this.entregaFinal = entregaFinal;
	}

	public String getTipoFiltro() {
		return tipoFiltro;
	}

	public void setTipoFiltro(String tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}

	public Integer getPagina() {
		return pagina;
	}

	public void setPagina(Integer pagina) {
		this.pagina = pagina;
	}

	public boolean isNovoFiltro() {
		return novoFiltro;
	}

	public void setNovoFiltro(boolean novoFiltro) {
		this.novoFiltro = novoFiltro;
	}
}

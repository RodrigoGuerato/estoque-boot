package br.com.fapen.estoque.forms;

import java.time.LocalDate;

public class RecebimentoFiltroForm {

	private String razaoSocial;
	private LocalDate dataInicial;
	private LocalDate dataFinal;
	private String tipoFiltro;
	private Integer pagina;
	private boolean novoFiltro;

	public RecebimentoFiltroForm() {
		this.tipoFiltro = "";
		this.pagina = 1;
		this.novoFiltro = false;
		this.dataInicial = LocalDate.now().minusDays(10);
		this.dataFinal = LocalDate.now();
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public LocalDate getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(LocalDate dataInicial) {
		this.dataInicial = dataInicial;
	}

	public LocalDate getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(LocalDate dataFinal) {
		this.dataFinal = dataFinal;
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

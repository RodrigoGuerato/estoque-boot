package br.com.fapen.estoque.forms;

public class AlteraSenhaForm {

	private Long idUsuario;
	private String novaSenha;
	private String novaSenhaConfirma;

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getNovaSenhaConfirma() {
		return novaSenhaConfirma;
	}

	public void setNovaSenhaConfirma(String novaSenhaConfirma) {
		this.novaSenhaConfirma = novaSenhaConfirma;
	}

}

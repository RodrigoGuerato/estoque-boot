package br.com.fapen.estoque.forms;

import java.util.ArrayList;
import java.util.List;

import br.com.fapen.estoque.models.Perfil;
import br.com.fapen.estoque.models.Usuario;

public class UsuarioForm {

	private Usuario usuario;
	private boolean inclusao;
	private List<Perfil> listaPerfil = new ArrayList<Perfil>();

	public UsuarioForm() {
		this.inclusao = true;
	}
	
	public UsuarioForm(Usuario usuario) {
		this.inclusao = true;
		this.usuario = usuario;
		
		for (Perfil p : usuario.getAuthorities()) {
			this.listaPerfil.add(p);
		}		
	}	

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isInclusao() {
		return inclusao;
	}

	public void setInclusao(boolean inclusao) {
		this.inclusao = inclusao;
	}

	public List<Perfil> getListaPerfil() {
		return listaPerfil;
	}

	public void setListaPerfil(List<Perfil> listaPerfil) {
		this.listaPerfil = listaPerfil;
	}

}

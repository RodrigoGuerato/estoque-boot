package br.com.fapen.estoque.services;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.fapen.estoque.forms.UsuarioForm;
import br.com.fapen.estoque.models.Perfil;
import br.com.fapen.estoque.models.Usuario;
import br.com.fapen.estoque.repositories.PerfilRepository;
import br.com.fapen.estoque.repositories.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRep;

	@Autowired
	private PerfilRepository perfilRep;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuarioBuscado = usuarioRep.findByUsername(username);
		return usuarioBuscado;
	}

	public Usuario loadUserByEmail(String email) {
		return usuarioRep.findByEmail(email);
	}

	public Usuario loadUserByHash(String hash) {
		return usuarioRep.findByHash(hash);
	}

	public void salvar(UsuarioForm formulario) {
		if (formulario.isInclusao()) {
			// Criptografa a senha
			String senhaLimpa = formulario.getUsuario().getPassword();
			formulario.getUsuario().setPassword(this.criptoSenha(senhaLimpa));

			// Monta o hash unico com (login + email + senha)
			formulario.getUsuario().setHash(this.geraHashUsuario(formulario.getUsuario()));
		}

		formulario.getUsuario().getAuthorities().clear();
		for (Perfil p : formulario.getListaPerfil()) {
			if (p.getAuthority() != null) {
				formulario.getUsuario().getAuthorities().add(p);
			}
		}
		usuarioRep.save(formulario.getUsuario());
	}

	public Usuario alterarSenha(Long idUsuario, String novaSenha) {
		Usuario u = usuarioRep.findById(idUsuario).get();
		String novaSenhaCripto = this.criptoSenha(novaSenha);
		u.setPassword(novaSenhaCripto);
		String novoHash = this.geraHashUsuario(u);
		u.setHash(novoHash);

		usuarioRep.alterarSenha(novaSenhaCripto, novoHash, idUsuario);
		return u;
	}

	private String geraHashUsuario(Usuario usuario) {
		String dadosUsuario = usuario.getUsername() + usuario.getEmail() + usuario.getPassword();
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		BigInteger hash = new BigInteger(1, md.digest(dadosUsuario.getBytes()));
		return hash.toString(16);
	}

	private String criptoSenha(String senhaLimpa) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(senhaLimpa);
	}

	public boolean userExists(String login) {
		Usuario usu = usuarioRep.findByUsername(login);
		return (usu != null);
	}

	public void addAdminUser() {
		Perfil perfilAdmin = new Perfil("ROLE_ADMIN", "Administrador");
		perfilRep.save(perfilAdmin);

		Usuario usuarioAdmin = new Usuario();
		usuarioAdmin.setUsername("admin");
		usuarioAdmin.setNomeCompleto("Administrador");
		usuarioAdmin.setEmail("rodrigo.guerato@hotmail.com");
		usuarioAdmin.setPassword(this.criptoSenha("1234"));
		usuarioAdmin.setHash(this.geraHashUsuario(usuarioAdmin));
		usuarioAdmin.getAuthorities().add(perfilAdmin);
		usuarioRep.save(usuarioAdmin);
	}

}

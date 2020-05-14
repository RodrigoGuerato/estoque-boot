package br.com.fapen.estoque.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.fapen.estoque.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	public Usuario findByUsername(String username);
	public Usuario findByEmail(String email);
	public Usuario findByHash(String hash);
	public Page<Usuario> findByNomeCompletoContainingIgnoreCase(String nomeCompleto, Pageable paginacao);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE t_usuario SET senha = :senha, hash = :hash WHERE id = :id", nativeQuery = true)
	public void alterarSenha(@Param("senha") String novaSenha, @Param("hash") String hash, @Param("id") Long id);
}
package br.com.fapen.estoque.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fapen.estoque.models.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, String> {

	Perfil findByAuthority(String perfil);
	Page<Perfil> findByDescricaoContainingIgnoreCase(String descricao, Pageable paginacao);
}

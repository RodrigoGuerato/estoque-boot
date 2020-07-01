package br.com.fapen.estoque.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fapen.estoque.models.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	public Page<Produto> findByDescricaoContainingIgnoreCase(String descricao, Pageable paginacao);

}

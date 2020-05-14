package br.com.fapen.estoque.repositories;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fapen.estoque.enums.StatusEnum;
import br.com.fapen.estoque.models.PedidoCompra;

public interface PedidoCompraRepository extends JpaRepository<PedidoCompra, Long> {
	
	public Page<PedidoCompra> findByFornecedor_razaoSocialContainingIgnoreCase(String razaoSocial, Pageable paginacao);
	public Page<PedidoCompra> findByStatus(StatusEnum status, Pageable paginacao);
	public Page<PedidoCompra> findByDataEntregaBetween(LocalDate dataInicial, LocalDate DataFinal, Pageable paginacao);

}

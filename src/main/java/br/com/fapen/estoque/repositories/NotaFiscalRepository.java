package br.com.fapen.estoque.repositories;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fapen.estoque.models.NotaFiscal;

public interface NotaFiscalRepository extends JpaRepository<NotaFiscal, Long> {

	public Page<NotaFiscal> findByPedidoFornecedorRazaoSocialContainingIgnoreCase(String razaoSocial,
			Pageable paginacao);

	public Page<NotaFiscal> findByDataRecebimentoBetween(LocalDate dataInicial, LocalDate DataFinal,
			Pageable paginacao);

}

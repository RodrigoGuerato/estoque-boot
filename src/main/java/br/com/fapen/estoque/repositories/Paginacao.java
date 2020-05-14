package br.com.fapen.estoque.repositories;

import org.springframework.data.domain.PageRequest;

public class Paginacao {

	private static final Integer NUMERO_REGISTRO_POR_PAGINA = 5;

	public static PageRequest getPaginacao(Integer pagina) {

		if (pagina <= 0) {
			pagina = 1;
		}

		return PageRequest.of(pagina - 1, NUMERO_REGISTRO_POR_PAGINA);
	}

}

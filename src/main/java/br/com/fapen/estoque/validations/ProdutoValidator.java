package br.com.fapen.estoque.validations;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.fapen.estoque.models.Produto;

@Component
public class ProdutoValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {

		return Produto.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "descricao", "campo.obrigatorio");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "custoUnitario", "campo.obrigatorio");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "precoVenda", "campo.obrigatorio");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "categoria", "campo.obrigatorio");

		Produto produtoEmValidacao = (Produto) target;

		if (produtoEmValidacao.getCustoUnitario() != null) {
			if (produtoEmValidacao.getCustoUnitario().compareTo(BigDecimal.ZERO) == 0) {
				errors.rejectValue("custoUnitario", "campo.obrigatorio");
			}
		}

		if (produtoEmValidacao.getPrecoVenda() != null) {
			if (produtoEmValidacao.getPrecoVenda().compareTo(BigDecimal.ZERO) == 0) {
				errors.rejectValue("precoVenda", "campo.obrigatorio");
			}
		}

		if (produtoEmValidacao.getCustoUnitario() != null && produtoEmValidacao.getPrecoVenda() != null) {

			// Valida o Preco de custo maior que o preco de venda
			if (produtoEmValidacao.getCustoUnitario().compareTo(produtoEmValidacao.getPrecoVenda()) == 1) {
				errors.rejectValue("custoUnitario", "custo.maior.venda");
			}

			// Valida se o preco de venda esta menor que a margem minima
			BigDecimal precoMinimo = produtoEmValidacao.getCustoUnitario().multiply(BigDecimal.valueOf(1.30));
			if (produtoEmValidacao.getPrecoVenda().compareTo(precoMinimo) == -1) {
				errors.rejectValue("precoVenda", "precoVenda.abaixo.minimo");
			}
		}

		// Validacao das Datas
		if (produtoEmValidacao.getValidade() != null) {
			LocalDate dataLimite = LocalDate.now().plusYears(5);			
			if (produtoEmValidacao.getValidade().isBefore(dataLimite)) {
				errors.rejectValue("validade", "fora.validade");
			}			
		} else {
			if (produtoEmValidacao.getCategoria().toUpperCase().equals("ALIMENTO")) {
				errors.rejectValue("validade", "campo.obrigatorio");
			}
		}

	}

}

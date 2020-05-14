package br.com.fapen.estoque.validations;

import java.math.BigDecimal;
import java.util.Collections;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.fapen.estoque.forms.PedidoCompraForm;
import br.com.fapen.estoque.models.ItemPedidoCompra;

@Component
public class PedidoCompraFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return PedidoCompraForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pedidoCompra.fornecedor", "campo.obrigatorio");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pedidoCompra.dataEntrega", "campo.obrigatorio");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pedidoCompra.condicaoPagamento", "campo.obrigatorio");

		PedidoCompraForm formulario = (PedidoCompraForm) target;
		if (formulario.getItensPedidoCompra().isEmpty()) {
			errors.rejectValue("itensPedidoCompra", "lista.vazia");
		}

		for (int i = 0; i < formulario.getItensPedidoCompra().size(); i++) {
			ItemPedidoCompra itemPedido = formulario.getItensPedidoCompra().get(i);
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itensPedidoCompra[" + i + "].produto", "campo.obrigatorio");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itensPedidoCompra[" + i + "].quantidade", "campo.obrigatorio");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itensPedidoCompra[" + i + "].precoUnitario", "campo.obrigatorio");

			if (itemPedido.getQuantidade() != null && itemPedido.getQuantidade().compareTo(0d) == 0) {
				errors.rejectValue("itensPedidoCompra[" + i + "].quantidade", "campo.obrigatorio");
			}
			if (itemPedido.getPrecoUnitario() != null && itemPedido.getPrecoUnitario().compareTo(BigDecimal.ZERO) == 0) {
				errors.rejectValue("itensPedidoCompra[" + i + "].precoUnitario", "campo.obrigatorio");
			}
		}
	}

}

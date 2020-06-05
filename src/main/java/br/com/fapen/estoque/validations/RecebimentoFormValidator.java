package br.com.fapen.estoque.validations;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.fapen.estoque.forms.ItemRecebimentoForm;
import br.com.fapen.estoque.forms.RecebimentoForm;

@Component
public class RecebimentoFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return RecebimentoForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		// Basico
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "serieNotaFiscal", "campo.obrigatorio");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numeroNotaFiscal", "campo.obrigatorio");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dataNotaFiscal", "campo.obrigatorio");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dataRecebimento", "campo.obrigatorio");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pedido", "campo.obrigatorio");

		// Especifico
		RecebimentoForm formulario = (RecebimentoForm) target;
		if (formulario.getItens().isEmpty()) {
			errors.rejectValue("itens", "lista.vazia");
		}

		for (int i = 0; i < formulario.getItens().size(); i++) {
			ItemRecebimentoForm itemReceb = formulario.getItens().get(i);

			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itens[" + i + "].quantidade", "campo.obrigatorio");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itens[" + i + "].precoUnitario", "campo.obrigatorio");

			if (itemReceb.getQuantidade() != null && itemReceb.getQuantidade().compareTo(0d) == 0) {
				errors.rejectValue("itens[" + i + "].quantidade", "campo.obrigatorio");
			}
			if (itemReceb.getPrecoUnitario() != null && itemReceb.getPrecoUnitario().compareTo(BigDecimal.ZERO) == 0) {
				errors.rejectValue("itens[" + i + "].precoUnitario", "campo.obrigatorio");
			}
		}

	}

}

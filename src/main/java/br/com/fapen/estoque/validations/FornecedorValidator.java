package br.com.fapen.estoque.validations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.validation.CNPJValidator;
import br.com.fapen.estoque.models.Fornecedor;
import br.com.fapen.estoque.repositories.FornecedorRepository;

@Component
public class FornecedorValidator implements Validator {
	
	private CNPJValidator validadorDeCnpj = new CNPJValidator();
	
	@Autowired
	private FornecedorRepository fornecedorRep;	
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Fornecedor.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Fornecedor fornecedorQueEstamosValidando = (Fornecedor) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "razaoSocial", "campo.obrigatorio");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nomeFantasia", "campo.obrigatorio");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cnpj", "campo.obrigatorio");
		
		List<ValidationMessage> validationMessages = validadorDeCnpj.invalidMessagesFor(fornecedorQueEstamosValidando.getCnpj());		 
		if ( !validationMessages.isEmpty() ) {
			errors.rejectValue("cnpj", "cnpj.invalido");
		}		
		
		Fornecedor fornecedorIgual = fornecedorRep.findByCnpj(fornecedorQueEstamosValidando.getCnpj());		
		if (fornecedorIgual != null && !fornecedorIgual.getId().equals(fornecedorQueEstamosValidando.getId()))   {
			errors.rejectValue("cnpj", "campo.duplicado");
		}	
	}
}

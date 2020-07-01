package br.com.fapen.estoque.validations;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.fapen.estoque.models.Perfil;

@Component
public class PerfilValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Perfil.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "authority", "campo.obrigatorio");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "descricao", "campo.obrigatorio");

		Perfil perfilSendoValidado = (Perfil) target;

		if (perfilSendoValidado.getAuthority() != null && perfilSendoValidado.getAuthority().length() > 0) {
			if (!perfilSendoValidado.getAuthority().substring(0, 5).equals("ROLE_")) {
				errors.rejectValue("authority", "campo.perfil");
			}
		}

	}

}

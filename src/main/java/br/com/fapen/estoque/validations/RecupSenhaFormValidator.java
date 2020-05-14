package br.com.fapen.estoque.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.fapen.estoque.forms.RecupSenhaForm;
import br.com.fapen.estoque.models.Usuario;
import br.com.fapen.estoque.repositories.UsuarioRepository;

@Component
public class RecupSenhaFormValidator implements Validator {

	@Autowired
	private UsuarioRepository usuarioRep;

	@Override
	public boolean supports(Class<?> clazz) {
		return RecupSenhaForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "campo.obrigatorio");
		RecupSenhaForm formSendoValidado = (RecupSenhaForm) target;

		Usuario usu = usuarioRep.findByEmail(formSendoValidado.getEmail());
		if (usu == null) {
			errors.rejectValue("email", "usuario.nao.localizado");
		}
	}

}

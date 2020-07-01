package br.com.fapen.estoque.validations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.fapen.estoque.forms.UsuarioForm;
import br.com.fapen.estoque.models.Usuario;
import br.com.fapen.estoque.repositories.UsuarioRepository;

@Component
public class UsuarioFormValidator implements Validator {

	private CPFValidator validadorDeCpf = new CPFValidator();
	private Usuario usuarioPesquisa;

	@Autowired
	private UsuarioRepository usuarioRep;

	@Override
	public boolean supports(Class<?> clazz) {
		return UsuarioForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "usuario.username", "campo.obrigatorio");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "usuario.nomeCompleto", "campo.obrigatorio");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "usuario.dataNascimento", "campo.obrigatorio");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "usuario.cpf", "campo.obrigatorio");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "usuario.email", "campo.obrigatorio");

		UsuarioForm usuarioSendoValidado = (UsuarioForm) target;

		List<ValidationMessage> validationMessages = validadorDeCpf
				.invalidMessagesFor(usuarioSendoValidado.getUsuario().getCpf());
		if (!validationMessages.isEmpty()) {
			errors.rejectValue("usuario.cpf", "cpf.invalido");
		}

		if (usuarioSendoValidado.isInclusao()) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "usuario.password", "campo.obrigatorio");

			//Usuario duplicado
			usuarioPesquisa = usuarioRep.findByUsername(usuarioSendoValidado.getUsuario().getUsername());
			if (usuarioPesquisa != null) {
				errors.rejectValue("usuario.username", "usuario.duplicado");
			}
			
			//email duplicado
			usuarioPesquisa = usuarioRep.findByEmail(usuarioSendoValidado.getUsuario().getEmail());
			if (usuarioPesquisa != null) {
				errors.rejectValue("usuario.email", "usuario.duplicado");
			}
		}
	}
}

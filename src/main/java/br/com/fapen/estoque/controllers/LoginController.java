package br.com.fapen.estoque.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fapen.estoque.forms.AlteraSenhaForm;
import br.com.fapen.estoque.forms.RecupSenhaForm;
import br.com.fapen.estoque.models.Usuario;
import br.com.fapen.estoque.services.EmailService;
import br.com.fapen.estoque.services.UsuarioService;
import br.com.fapen.estoque.validations.AlteraSenhaFormValidator;
import br.com.fapen.estoque.validations.RecupSenhaFormValidator;

@Controller
public class LoginController {

	@Autowired
	private RecupSenhaFormValidator formValidator;
	
	@Autowired
	private AlteraSenhaFormValidator alteraSenhaValidator;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private EmailService emailService;

	@InitBinder("recupSenhaForm")
	public void initBinderRecup(WebDataBinder binder) {
		binder.setValidator(formValidator);
	}
	
	@InitBinder("alteraSenhaForm")
	public void initBinderAltera(WebDataBinder binder) {
		binder.setValidator(alteraSenhaValidator);
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET, name = "loginUrl")
	public String login() {
		return "login/login";
	}

	@RequestMapping(value = "/recuperarSenha", method = RequestMethod.GET, name = "recupSenhaFormUrl")
	public String recuperaSenhaForm(RecupSenhaForm recupSenhaForm) {
		return "login/recupSenha";
	}

	@RequestMapping(value = "/recuperarSenha", method = RequestMethod.POST, name = "recupSenhaUrl")
	public String verificaEmailLogin(@Valid RecupSenhaForm recupSenhaForm, BindingResult resultado,
			HttpServletRequest request) {
		if (resultado.hasErrors()) {
			return recuperaSenhaForm(recupSenhaForm);
		}
		System.out.println(request.getContextPath());

		// envia um email com o link para mudança de senha
		Usuario user = usuarioService.loadUserByEmail(recupSenhaForm.getEmail());
		emailService.enviarEmailRecupSenha(request, user);
		return "login/msgEmailSenha";
	}

	@RequestMapping(value = "/recuperarSenha/alterarSenha", method = RequestMethod.GET, name = "alteraSenhaCheckUrl")
	public ModelAndView alteraSenhaCheck(@RequestParam(defaultValue = "") String token) {
		Usuario usuarioAltSenha = usuarioService.loadUserByHash(token);

		if (usuarioAltSenha == null) {
			return new ModelAndView("login/erro");
		}
		AlteraSenhaForm formulario = new AlteraSenhaForm();
		formulario.setIdUsuario(usuarioAltSenha.getId());
		return formAlterarSenha(formulario);
	}

	public ModelAndView formAlterarSenha(AlteraSenhaForm alteraSenhaForm) {
		ModelAndView mav = new ModelAndView("login/alteraSenha");
		mav.addObject(alteraSenhaForm);
		return mav;
	}

	@RequestMapping(value = "/recuperarSenha/alterarSenha", method = RequestMethod.POST, name = "alteraSenhaUrl")
	public ModelAndView alterarSenhaUsuario(@Valid AlteraSenhaForm alteraSenhaForm, BindingResult resultado,
			RedirectAttributes atributos) {
		
		if (resultado.hasErrors()) {
			return formAlterarSenha(alteraSenhaForm);
		}
		usuarioService.alterarSenha(alteraSenhaForm.getIdUsuario(), alteraSenhaForm.getNovaSenha());
		atributos.addFlashAttribute("msgSenha", "Senha alterada com sucesso !");
		return new ModelAndView("redirect:/login");
	}

}

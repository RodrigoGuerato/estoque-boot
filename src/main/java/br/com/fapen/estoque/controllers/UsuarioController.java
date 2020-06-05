package br.com.fapen.estoque.controllers;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fapen.estoque.forms.UsuarioForm;
import br.com.fapen.estoque.models.Usuario;
import br.com.fapen.estoque.repositories.Paginacao;
import br.com.fapen.estoque.repositories.PerfilRepository;
import br.com.fapen.estoque.repositories.UsuarioRepository;
import br.com.fapen.estoque.services.ArquivoService;
import br.com.fapen.estoque.services.UsuarioService;
import br.com.fapen.estoque.validations.UsuarioFormValidator;

@Controller
@RequestMapping(value = "/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRep;

	@Autowired
	private PerfilRepository perfilRep;

	@Autowired
	private UsuarioFormValidator usuarioFormValidator;

	@Autowired
	private ArquivoService gravadorDeArquivos;
	
	@Autowired
	private UsuarioService servicoDeUsuario;

	@InitBinder("usuarioForm")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(usuarioFormValidator);
	}

	@RequestMapping(value = "/novo", method = RequestMethod.GET, name = "novoUsuarioUrl")
	public ModelAndView formulario(UsuarioForm usuarioForm) {
		ModelAndView mav = new ModelAndView("usuario/form");
		mav.addObject("listaDePerfis", perfilRep.findAll());
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST, name = "salvarUsuarioUrl")
	public ModelAndView salvarNoBanco(@Valid UsuarioForm usuarioForm, BindingResult resultado,
			RedirectAttributes atributos) {

		if (resultado.hasErrors()) {
			return formulario(usuarioForm);
		}
		
		servicoDeUsuario.salvar(usuarioForm);
		atributos.addFlashAttribute("mensagemStatus", "Usuário salvo com sucesso !");
		return new ModelAndView("redirect:/usuarios");
	}

	@RequestMapping(method = RequestMethod.GET, name = "listarUsuarioUrl")
	public ModelAndView listar(@RequestParam(defaultValue = "1") Integer pagina,
			@RequestParam(defaultValue = "") String busca) {

		ModelAndView mav = new ModelAndView("usuario/lista");
		Page<Usuario> dados;

		if (busca.equals("")) {
			dados = usuarioRep.findAll(Paginacao.getPaginacao(pagina));
		} else {
			dados = usuarioRep.findByNomeCompletoContainingIgnoreCase(busca, Paginacao.getPaginacao(pagina));
		}

		mav.addObject("listaPaginada", dados);
		mav.addObject("busca", busca);
		return mav;
	}

	@RequestMapping(value = "{id}/edit", name = "alterarUsuarioUrl")
	public ModelAndView alterar(@PathVariable Long id, Model model) {

		Usuario usuarioEncontrado = usuarioRep.findById(id).get();

		UsuarioForm formulario = new UsuarioForm(usuarioEncontrado);
		formulario.setInclusao(false);

		model.addAttribute(formulario);
		return formulario(formulario);
	}

	@RequestMapping(value = "/{id}", name = "detalharUsuarioUrl")
	public ModelAndView detalhar(@PathVariable Long id) {

		Usuario usuarioVindoDoBanco = usuarioRep.findById(id).get();
		ModelAndView mav = new ModelAndView("usuario/detalhe");
		mav.addObject("registro", usuarioVindoDoBanco);
		return mav;
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.POST, name = "excluirUsuarioUrl")
	public String excluir(@PathVariable Long id, RedirectAttributes atributos) {

		Usuario usuarioEncontrado = usuarioRep.findById(id).get();

		if (usuarioEncontrado.getUsername().trim().equalsIgnoreCase("admin")) {
			atributos.addFlashAttribute("mensagemStatus", "Usuário administrador não pode ser excluido !");
			return "redirect:/usuarios";
		}

		usuarioRep.delete(usuarioEncontrado);
		atributos.addFlashAttribute("mensagemStatus", "Usuário excluido com sucesso !");
		return "redirect:/usuarios";
	}

	@RequestMapping(value = "/perfil", method = RequestMethod.GET, name = "perfilUsuarioUrl")
	public ModelAndView perfil(Principal principal) {

		ModelAndView mav = new ModelAndView("usuario/perfil");
		Usuario usuarioLogado = usuarioRep.findByUsername(principal.getName());
		mav.addObject("usuario", usuarioLogado);

		return mav;
	}

	@RequestMapping(value = "/perfil", method = RequestMethod.POST, name = "alterFotoPerfilUrl")
	public String alterarFotoPerfil(MultipartFile foto, Principal principal) {

		String caminhoDaFoto = gravadorDeArquivos.salvarEmDisco(foto);
		Usuario usuarioEncontrado = usuarioRep.findByUsername(principal.getName());
		usuarioEncontrado.setCaminhoFoto(caminhoDaFoto);
		usuarioRep.save(usuarioEncontrado);

		// Atualiza os dados do usuario logado
		Authentication authentication = new UsernamePasswordAuthenticationToken(usuarioEncontrado,
				usuarioEncontrado.getPassword(), usuarioEncontrado.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return "redirect:/usuarios/perfil";
	}
}

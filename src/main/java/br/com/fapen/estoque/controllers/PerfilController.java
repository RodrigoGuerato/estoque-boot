package br.com.fapen.estoque.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fapen.estoque.models.Perfil;
import br.com.fapen.estoque.repositories.Paginacao;
import br.com.fapen.estoque.repositories.PerfilRepository;
import br.com.fapen.estoque.validations.PerfilValidator;

@Controller
@RequestMapping(value = "/perfis")
public class PerfilController {

	@Autowired
	private PerfilRepository perfilRep;

	@Autowired
	private PerfilValidator perfilValidador;

	@InitBinder("perfil")
	protected void init(WebDataBinder binder) {
		binder.setValidator(perfilValidador);
	}

	@RequestMapping(value = "/novo", method = RequestMethod.GET, name = "novoPerfilUrl")
	public String formulario(Perfil perfil) {
		return "perfil/form";
	}

	@RequestMapping(method = RequestMethod.POST, name = "salvarPerfilUrl")
	public String salvarNoBanco(@Valid Perfil perfilVindoDoForm, BindingResult resultadoValidacao,
			RedirectAttributes atributos) {

		if (resultadoValidacao.hasErrors()) {
			return formulario(perfilVindoDoForm);
		}

		perfilRep.save(perfilVindoDoForm);
		atributos.addFlashAttribute("mensagemStatus", "Perfil salvo com sucesso !");
		return "redirect:/perfis";
	}

	@RequestMapping(method = RequestMethod.GET, name = "listarPerfilUrl")
	public ModelAndView listar(@RequestParam(defaultValue = "1") Integer pagina,
			@RequestParam(defaultValue = "") String busca) {

		ModelAndView mav = new ModelAndView("perfil/lista");
		Page<Perfil> dados;

		if (busca.equals("")) {
			dados = perfilRep.findAll(Paginacao.getPaginacao(pagina));
		} else {
			dados = perfilRep.findByDescricaoContainingIgnoreCase(busca, Paginacao.getPaginacao(pagina));
		}

		mav.addObject("listaPaginada", dados);
		mav.addObject("busca", busca);
		return mav;
	}

	@RequestMapping(value = "{id}/edit", name = "alterarPerfilUrl")
	public String alterar(@PathVariable String id, Model model) {

		Perfil perfilEncontrado = perfilRep.getOne(id);
		model.addAttribute(perfilEncontrado);
		return formulario(perfilEncontrado);
	}

	@RequestMapping(value = "/{id}", name = "detalharPerfilUrl")
	public ModelAndView detalhar(@PathVariable String id) {

		Perfil perfilVindoDoBanco = perfilRep.getOne(id);
		ModelAndView mav = new ModelAndView("perfil/detalhe");
		mav.addObject("registro", perfilVindoDoBanco);
		return mav;
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.POST, name = "excluirPerfilUrl")
	public String excluir(@PathVariable String id, RedirectAttributes atributos) {

		Perfil perfilEncontrado = perfilRep.getOne(id);
		perfilRep.delete(perfilEncontrado);
		atributos.addFlashAttribute("mensagemStatus", "Perfil excluido com sucesso !");
		return "redirect:/perfis";
	}

}

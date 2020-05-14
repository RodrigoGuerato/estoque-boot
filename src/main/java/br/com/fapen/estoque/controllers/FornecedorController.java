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

import br.com.fapen.estoque.models.Fornecedor;
import br.com.fapen.estoque.repositories.FornecedorRepository;
import br.com.fapen.estoque.repositories.Paginacao;
import br.com.fapen.estoque.validations.FornecedorValidator;

@Controller
@RequestMapping(value = "/fornecedores")
public class FornecedorController {

	@Autowired
	private FornecedorRepository fornecedorRep;

	@Autowired
	private FornecedorValidator fornecedorValidador;

	@InitBinder("fornecedor")
	protected void init(WebDataBinder binder) {
		binder.setValidator(fornecedorValidador);
	}

	@RequestMapping(value = "/novo", method = RequestMethod.GET, name = "novoFornecedorUrl")
	public String formulario(Fornecedor fornecedor) {
		return "fornecedor/form";
	}

	@RequestMapping(method = RequestMethod.POST, name = "salvarFornecedorUrl")
	public String salvarNoBanco(@Valid Fornecedor fornecedorVindoDoForm, BindingResult resultadoValidacao,
			RedirectAttributes atributos) {

		if (resultadoValidacao.hasErrors()) {
			return formulario(fornecedorVindoDoForm);
		}

		fornecedorRep.save(fornecedorVindoDoForm);
		atributos.addFlashAttribute("mensagemStatus", "Fornecedor salvo com sucesso !");
		return "redirect:/fornecedores";
	}

	@RequestMapping(method = RequestMethod.GET, name = "listarFornecedorUrl")
	public ModelAndView listar(@RequestParam(defaultValue = "1") Integer pagina,
			@RequestParam(defaultValue = "") String busca) {

		ModelAndView mav = new ModelAndView("fornecedor/lista");
		Page<Fornecedor> dados;

		if (busca.equals("")) {
			dados = fornecedorRep.findAll(Paginacao.getPaginacao(pagina));
		} else {
			dados = fornecedorRep.findByRazaoSocialContainingIgnoreCase(busca, Paginacao.getPaginacao(pagina));
		}

		mav.addObject("listaPaginada", dados);
		mav.addObject("busca", busca);
		return mav;
	}

	@RequestMapping(value = "{id}/edit", name = "alterarFornecedorUrl")
	public String alterar(@PathVariable Long id, Model model) {

		Fornecedor fornecedorEncontrado = fornecedorRep.getOne(id);
		model.addAttribute(fornecedorEncontrado);
		return formulario(fornecedorEncontrado);
	}

	@RequestMapping(value = "/{id}", name = "detalharFornecedorUrl")
	public ModelAndView detalhar(@PathVariable Long id) {

		Fornecedor fornecedorVindoDoBanco = fornecedorRep.getOne(id);
		ModelAndView mav = new ModelAndView("fornecedor/detalhe");
		mav.addObject("fornecedor", fornecedorVindoDoBanco);
		return mav;
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.POST, name = "excluirFornecedorUrl")
	public String excluir(@PathVariable Long id, RedirectAttributes atributos) {

		Fornecedor fornecedorEncontrado = fornecedorRep.getOne(id);
		fornecedorRep.delete(fornecedorEncontrado);
		atributos.addFlashAttribute("mensagemStatus", "Fornecedor excluido com sucesso !");
		return "redirect:/fornecedores";
	}

}

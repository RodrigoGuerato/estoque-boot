package br.com.fapen.estoque.controllers;

import java.io.ByteArrayInputStream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fapen.estoque.enums.CondicaoPagtoEnum;
import br.com.fapen.estoque.enums.StatusEnum;
import br.com.fapen.estoque.forms.PedidoCompraForm;
import br.com.fapen.estoque.forms.PedidoFiltroForm;
import br.com.fapen.estoque.models.ItemPedidoCompra;
import br.com.fapen.estoque.models.PedidoCompra;
import br.com.fapen.estoque.repositories.FornecedorRepository;
import br.com.fapen.estoque.repositories.ProdutoRepository;
import br.com.fapen.estoque.services.PedidoCompraService;
import br.com.fapen.estoque.validations.PedidoCompraFormValidator;

@Controller
@RequestMapping(value = "/pedidos")
public class PedidoCompraController {

	@Autowired
	private PedidoCompraService servicoDePedidos;

	@Autowired
	private FornecedorRepository fornecedorRep;

	@Autowired
	private ProdutoRepository produtoRep;

	@Autowired
	private PedidoCompraFormValidator validadorDoForm;

	@InitBinder("pedidoCompraForm")
	public void initBinderForm(WebDataBinder binder) {
		binder.setValidator(validadorDoForm);
	}

	@RequestMapping(value = "/novo", method = RequestMethod.GET, name = "novoPedidoCompraUrl")
	public ModelAndView formulario(PedidoCompraForm pedidoCompraForm) {
		ModelAndView mav = new ModelAndView("pedido/form");
		mav.addObject("listaDeFornecedores", fornecedorRep.findAll());
		mav.addObject("listaDeCondicaoPagto", CondicaoPagtoEnum.values());
		mav.addObject("listaDeProdutos", produtoRep.findAll());
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST, name = "salvarPedidoCompraUrl")
	public ModelAndView salvarNoBanco(@Valid PedidoCompraForm pedidoCompraForm, BindingResult resultado,
			RedirectAttributes atributos) {
		if (resultado.hasErrors()) {
			return formulario(pedidoCompraForm);
		}
		servicoDePedidos.salvar(pedidoCompraForm);
		ModelAndView mav = new ModelAndView("redirect:/pedidos");
		atributos.addFlashAttribute("mensagemStatus", "Pedido salvo com sucesso !");
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, name = "listarPedidoCompraUrl")
	public ModelAndView listar(PedidoFiltroForm filtroForm) {

		ModelAndView mav = new ModelAndView("pedido/lista");
		Page<PedidoCompra> dados = servicoDePedidos.listar(filtroForm);
		mav.addObject("listaPaginada", dados);
		mav.addObject("pedidoFiltroForm", filtroForm);
		mav.addObject("listaDeStatus", StatusEnum.values());
		return mav;
	}

	@RequestMapping(value = "{id}/edit", method = RequestMethod.GET, name = "alterarPedidoCompraUrl")
	public ModelAndView alterar(@PathVariable Long id, Model model, RedirectAttributes atributos) {
		PedidoCompra registro = servicoDePedidos.findById(id);
		
		if (registro.getStatus() != StatusEnum.EM_DIGITACAO) {
			ModelAndView mav = new ModelAndView("redirect:/pedidos");
			atributos.addFlashAttribute("mensagemStatus", "Operação não permitida, pois o pedido já foi Recebido/Cancelado");
			return mav;
		}	
		
		PedidoCompraForm formulario = new PedidoCompraForm(registro);
		model.addAttribute(formulario);
		return formulario(formulario);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET, name = "detalharPedidoUrl")
	public ModelAndView detalhes(@PathVariable Long id) {
		PedidoCompra registro = servicoDePedidos.findById(id);
		ModelAndView mav = new ModelAndView("pedido/detalhe");
		mav.addObject("registro", registro);
		return mav;
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.POST, name = "excluirPedidoCompraUrl")
	public String deletePedido(@PathVariable Long id, RedirectAttributes atributos) {
		PedidoCompra pedido = servicoDePedidos.findById(id);
		servicoDePedidos.excluir(pedido);
		atributos.addFlashAttribute("mensagemStatus", "Pedido excluido com sucesso !");
		return "redirect:/pedidos";
	}

	@RequestMapping(value = "/{id}/pdf", method = RequestMethod.GET, name = "geraPdfPedidoUrl", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> imprimir(@PathVariable Long id) {
		PedidoCompra pedCompra = servicoDePedidos.findById(id);
		ByteArrayInputStream pdfEmMemoria = servicoDePedidos.gerarPdf(pedCompra);
		HttpHeaders cabecalho = new HttpHeaders();
		cabecalho.add("Content-Disposition", "inline; filename=pedido_" + id + ".pdf");
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).headers(cabecalho)
				.body(new InputStreamResource(pdfEmMemoria));
	}

	/*
	 * Metodos de tratamento dos itens
	 */
	@RequestMapping(value = "/novoItem", method = RequestMethod.POST)
	public ModelAndView novoItemForm(@ModelAttribute("pedidoCompraForm") PedidoCompraForm pedidoCompraForm) {
		pedidoCompraForm.getItensPedidoCompra().add(new ItemPedidoCompra());
		ModelAndView mav = new ModelAndView("pedido/form-item");
		mav.addObject("listaDeProdutos", produtoRep.findAll());
		return mav;
	}

	@RequestMapping(value = "/deletaItem/{linha}", method = RequestMethod.POST)
	public ModelAndView deletaItemForm(@PathVariable int linha, PedidoCompraForm pedidoCompraForm) {
		pedidoCompraForm.getItensPedidoCompra().remove(linha);
		ModelAndView mav = new ModelAndView("pedido/form-item");
		mav.addObject("listaDeProdutos", produtoRep.findAll());
		return mav;
	}
}

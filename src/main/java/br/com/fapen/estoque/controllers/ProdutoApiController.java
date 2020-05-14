package br.com.fapen.estoque.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fapen.estoque.models.Produto;
import br.com.fapen.estoque.repositories.ProdutoRepository;
import br.com.fapen.estoque.validations.ProdutoValidator;

@RestController
@RequestMapping(value = "/api/produtos")
public class ProdutoApiController {

	@Autowired
	private ProdutoRepository repProdutos;

	@Autowired
	private ProdutoValidator produtoValidator;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(produtoValidator);
	}

	@GetMapping
	public List<Produto> listar() {
		return repProdutos.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
		Optional<Produto> prod = repProdutos.findById(id);
		if (prod.isEmpty()) {
			return new ResponseEntity<Produto>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Produto>(prod.get(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Object> incluir(@Valid @RequestBody Produto produto) {
		repProdutos.save(produto);
		return new ResponseEntity<Object>(produto, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Produto> alterar(@PathVariable Long id, @RequestBody Produto produto) {
		Produto prod = repProdutos.findById(id).get();
		if (prod == null) {
			return new ResponseEntity<Produto>(HttpStatus.NOT_FOUND);
		}
		repProdutos.save(produto);
		return new ResponseEntity<Produto>(produto, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Produto> excluir(@PathVariable Long id) {
		Produto prod = repProdutos.findById(id).get();
		if (prod == null) {
			return new ResponseEntity<Produto>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Produto>(HttpStatus.OK);
	}
}

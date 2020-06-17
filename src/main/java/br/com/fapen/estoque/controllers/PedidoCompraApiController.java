package br.com.fapen.estoque.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fapen.estoque.models.ItemPedidoCompra;
import br.com.fapen.estoque.models.PedidoCompra;
import br.com.fapen.estoque.services.PedidoCompraService;

@RestController
@RequestMapping(value = "/api/pedidos")
public class PedidoCompraApiController {
	
	@Autowired
	private PedidoCompraService pedidoService;
	
	@GetMapping
	public List<PedidoCompra> listar( ) {
		return pedidoService.listar();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PedidoCompra> buscarPorId(@PathVariable Long id) {
		PedidoCompra registro = pedidoService.findById(id);
		if (registro == null) {
			return new ResponseEntity<PedidoCompra>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<PedidoCompra>(registro, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Object> incluir(@Valid @RequestBody PedidoCompra pedido) {
		
		for (ItemPedidoCompra item : pedido.getItens()) {
			item.setPedido(pedido);
		}
		
		pedidoService.salvar(pedido);
		return new ResponseEntity<Object>(pedido, HttpStatus.CREATED);
	}	

}

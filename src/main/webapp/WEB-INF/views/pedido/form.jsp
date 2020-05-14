<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="pt-br">
	<%@ include file="../base/header.jsp"%>
	<body>
		<%@ include file="../base/navbar.jsp"%>
	
		<div class="container">			
			<br>			
			<f:form action="${s:mvcUrl('salvarPedidoCompraUrl').build()}" method="post" modelAttribute="pedidoCompraForm" >
			
				<f:hidden path="pedidoCompra.id"/>
			
				<div class="row">
					<div class="col s12">
					
						<div class="card">
							<div class="card-content">
								<span class="card-title center-align">Dados do Pedido - Nº: ${pedidoCompraForm.pedidoCompra.id}</span>
								
								<div class="row">
									<div class="input-field col s12">
										<f:select path="pedidoCompra.fornecedor" cssClass="validate">
											<f:option value="">Selecione</f:option>
											<f:options items="${listaDeFornecedores}" itemValue="id" itemLabel="razaoSocial"/>
										</f:select>												
										<label for="pedidoCompra.fornecedor">Fornecedor</label>
										<f:errors path="pedidoCompra.fornecedor" cssClass="helper-text" />
									</div>
								</div>	
								<div class="row">
									<div class="input-field col s3">
										<f:input path="pedidoCompra.dataEntrega" cssClass="validate datepicker" />
										<label for="pedidoCompra.dataEntrega">Entrega</label>
										<f:errors path="pedidoCompra.dataEntrega" cssClass="helper-text" />
									</div>
									<div class="input-field col s9">
										<f:select path="pedidoCompra.condicaoPagamento" cssClass="validate">
											<f:option value="">Selecione</f:option>
											<f:options items="${listaDeCondicaoPagto}" itemLabel="displayValue"/>
										</f:select>												
										<label for="pedidoCompra.condicaoPagamento">Condição de Pagamento</label>
										<f:errors path="pedidoCompra.condicaoPagamento" cssClass="helper-text" />
									</div>
								</div>	
								<div class="row">
									<div class="input-field col s12">
										<f:textarea path="pedidoCompra.observacao" cssClass="validate materialize-textarea" />
										<label for="pedidoCompra.observacao">Observações</label>
										<f:errors path="pedidoCompra.observacao" cssClass="helper-text" />
									</div>
								</div>	
								
								<span class="card-title center-align">
									Itens
									<button id="btnNovoItem" type="button" class="btn-floating btn-small waves-effect waves-light right" title="Novo Item">
										<i class="material-icons">add</i>
									</button>									
								</span>
								<div class="row">
									<div class="col s12">
										<f:errors path="itensPedidoCompra" />
										<div id="dadosItens">
											<c:forEach items="${pedidoCompraForm.itensPedidoCompra}" var="itemPedido" varStatus="status">
											
												<div class="card">
													<div class="card-content">
														<f:hidden path="itensPedidoCompra[${status.index}].id"/>
														<f:hidden path="itensPedidoCompra[${status.index}].pedido"/>
														<div class="row">
															<div class="input-field col s4">
																<f:select path="itensPedidoCompra[${status.index}].produto" cssClass="validate">
																	<f:option value="">Selecione</f:option>
																	<f:options items="${listaDeProdutos}" itemValue="id" itemLabel="descricao"/>
																</f:select>												
																<label class="active" for="itensPedidoCompra[${status.index}].produto">Produto</label>
																<f:errors path="itensPedidoCompra[${status.index}].produto" cssClass="helper-text" />
															</div>
															
															<div class="input-field col s3">
																<f:input type="number" path="itensPedidoCompra[${status.index}].quantidade" cssClass="validate" />
																<label class="active" for="itensPedidoCompra[${status.index}].quantidade">Quantidade</label>
																<f:errors path="itensPedidoCompra[${status.index}].quantidade" cssClass="helper-text" />
															</div>
															
															<div class="input-field col s3">
																<f:input readonly="true" type="number" path="itensPedidoCompra[${status.index}].precoUnitario" cssClass="validate" />
																<label class="active" for="itensPedidoCompra[${status.index}].precoUnitario">Valor Unitário</label>
																<f:errors path="itensPedidoCompra[${status.index}].precoUnitario" cssClass="helper-text" />
															</div>
															
															<div class="input-field col s2">
																<button class="btn-small red deletaItem" title="excluir" type="button" value="${status.index}">
																	<i class="material-icons">delete</i>
																</button>
															</div>
														</div>													
													</div>
												</div>
												
											</c:forEach>
										</div>
									</div>
								</div>
							</div>							
							<div class="card-action">
								<input class="btn-small green" type="submit" value="Salvar">
								<a href="${s:mvcUrl('listarPedidoCompraUrl').build()}" class="btn-small orange">voltar</a>
							</div>
						</div>
					
					</div>				
				</div>			
				
			</f:form>
		</div>
			
		<%@ include file="../base/scripts.jsp"%>
		<script type="text/javascript">
			function montaGradeItens(retorno) {
				var cHtml = $(retorno).find("#dadosRegistro").html();
				$("#dadosItens").html(cHtml);
				$("select").formSelect();
			}
			function erroAjax(dadosErro) {
				console.log("ERRO" + retornoErro);
			}
			$("body").on("click", ".deletaItem", function(event) {
				event.preventDefault();
				$.ajax({
					type : "POST",
					data : $("form").serialize(),
					url : "/pedidos/deletaItem/" + $(this).val(),
					success : montaGradeItens,
					error : erroAjax
				});						
			});	
			$("body").on("click", "#btnNovoItem", function(event) {
				event.preventDefault();
				$.ajax({
					type : "POST",
					data : $("form").serialize(),
					url : "/pedidos/novoItem",
					success : montaGradeItens,
					error : erroAjax
				});					
			});
			
			$("body").on("change", "select[name$='produto']", function(event) {
				var elementoProduto = $(this);
				var elementoPrecoUnitario = $(this).closest(".row").find("input[name$='precoUnitario']");
				
				$.ajax({
					type : "GET",
					url : "/api/produtos/" + elementoProduto.val(),
					dataType: "json",
					contentType: "application/json; charset=utf-8",
					success : function(dados) {
						elementoPrecoUnitario.val(dados.custoUnitario);
					},
					error : erroAjax
				});						
			});		
		</script>		
	</body>
</html>

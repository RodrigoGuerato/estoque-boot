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
			<f:form action="${s:mvcUrl('salvarRecebimentoUrl').build()}" method="POST" modelAttribute="recebimentoForm">
				<f:hidden path="id"/>
				
				<div class="row">
					<div class="col s12">
						<div class="card">
							<div class="card-content">
								<span class="card-title center-align">Dados do Recebimento</span>
								
								<div class="row">
									<div class="input-field col s3">
										<label for="serieNotaFiscal">Série NF</label>
										<f:input path="serieNotaFiscal" type="number" cssClass="validate" />
										<f:errors path="serieNotaFiscal" cssClass="helper-text" />
									</div>								
									<div class="input-field col s3">
										<label for="numeroNotaFiscal">Número NF</label>
										<f:input path="numeroNotaFiscal" type="number" cssClass="validate" />
										<f:errors path="numeroNotaFiscal" cssClass="helper-text" />
									</div>								
									<div class="input-field col s3">
										<label for="dataNotaFiscal">Data NF</label>
										<f:input path="dataNotaFiscal" cssClass="validate datepicker" />
										<f:errors path="dataNotaFiscal" cssClass="helper-text" />
									</div>								
									<div class="input-field col s3">
										<label for="dataRecebimento">Data Recebimento</label>
										<f:input path="dataRecebimento" cssClass="validate datepicker" />
										<f:errors path="dataRecebimento" cssClass="helper-text" />
									</div>								
								</div>
								
								<div class="row">
									<div class="input-field col s12">
										<f:select path="pedido" cssClass="validate">
											<f:option value="">Selecione</f:option>
											<f:options items="${listaPedidos}" itemLabel="texto" />
										</f:select>										
										<label for="pedido">Pedido de Compra</label>
										<f:errors path="pedido" cssClass="helper-text" />
									</div>
								</div>
								
								<f:errors path="itens" cssClass="helper-text" />
								<span class="card-title center-align">Itens</span>
								
								<div class="row">
									<div class="col s12">
										<div id="dadosTabela">
											<table class="responsive-table">
											    <thead>
												    <tr>
													    <th style="width:400px;">Produto</th>
													    <th>Quantidade</th>
													    <th>Valor Unitário</th>
													    <th>Valor Total</th>									
												    </tr>											
											    </thead>
											    <tbody>
											    	<c:forEach items="${recebimentoForm.itens}" var="itemReceb" varStatus="status" >
											    		<tr>
											    			<f:hidden path="itens[${status.index}].produto"/>
											    			<f:hidden path="itens[${status.index}].valorTotal"/>
											    		
											    			<td>${itemReceb.produto.descricao}</td>
											    			<td>
											    				<div class="input-field">
											    					<f:input path="itens[${status.index}].quantidade" type="number" step="1" />
											    					<f:errors path="itens[${status.index}].quantidade" cssClass="helper-text" />
											    				</div>
											    			</td>
											    			<td>
											    				<div class="input-field">
											    					<f:input path="itens[${status.index}].precoUnitario" type="number" step="0.01" />
											    					<f:errors path="itens[${status.index}].precoUnitario" cssClass="helper-text" />
											    				</div>		    				
											    			</td>
											    			<td class="right-align vlrTotal">
											    				<fmt:formatNumber value="${itemReceb.valorTotal}" type="currency"/>
											    			</td>
											    		</tr>
											    	</c:forEach>		
											    </tbody>
										    </table>									
										
										</div>									
									</div>								
								</div>			
								
							</div>
							<div class="card-action">
								<input class="btn-small green" type="submit" value="Confirmar">
								<a href="${s:mvcUrl('listarRecebimentoUrl').build()}" class="btn-small orange">voltar</a>
							</div>
						</div>					
					</div>				
				</div>			
			</f:form>				
		</div>
	
		<%@ include file="../base/scripts.jsp"%>
		<script type="text/javascript">
			//+-------------------------------------------------+
			//| Funcao para atualizar a tabela obtida pelo Ajax |
			//+-------------------------------------------------+
			function atualizaTabelaItens(dadosRetorno) {
				var cHtml = $(dadosRetorno).find("#dadosRegistro").html();
				$("#dadosTabela").html(cHtml);
				$("select").formSelect();
			}
			
			//+---------------------------------------------+
			//| Funcao executada quando ocorre erro no Ajax |
			//+---------------------------------------------+
			function erroAjax(dadosErro) {
				console.log("Deu Pane " + dadosErro);
			}
			
			//+--------------------------------------------------------------------+
			//| Funcao que calcula o total da linha e ajusta os campos necessarios |
			//+--------------------------------------------------------------------+
			function calculaValorTotal(elementoLinha) {
				var elQuantidade    = elementoLinha.find("input[name$='quantidade']");
				var elPrecoUnitario = elementoLinha.find("input[name$='precoUnitario']");
				var elValorTotal    = elementoLinha.find("input[name$='valorTotal']");
				var valorCalculado  = elQuantidade.val() * elPrecoUnitario.val();
				
				elValorTotal.val(valorCalculado);
				elementoLinha.find(".vlrTotal").html("R$&nbsp;" + valorCalculado.toFixed(2).replace(".",",")); 
			}
			
			//+------------------------------------------------------------------------+
			//| Quando alterar o Pedido, buscar os itens e carregar a tabela, via AJAX |
			//+------------------------------------------------------------------------+
			$("body").on("change", "#pedido", function(event) {
				$.ajax({
					type : "POST",
					data : $("form").serialize(),
					url : "${s:mvcUrl('carregaItensRecebUrl').build()}",
					success : atualizaTabelaItens,
					error : erroAjax					
				});				
			});
			
			//+------------------------------------------------------------------+
			//| Atualizar o preco total de acordo com a quantidade/precoUnitario |
			//+------------------------------------------------------------------+
			//| Qualquer elemento input que o name termina com "quantidade"      |
			//+------------------------------------------------------------------+
			$("body").on("change", "input[name$='quantidade']", function(event) {
				var elementoLinha = $(this).closest("tr");
				calculaValorTotal(elementoLinha);
			});
			$("body").on("change", "input[name$='precoUnitario']", function(event) {
				var elementoLinha = $(this).closest("tr");
				calculaValorTotal(elementoLinha);
			});
		
		</script>
	</body>
</html>

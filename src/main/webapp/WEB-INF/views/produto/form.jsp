<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<html lang="pt-br">
	<%@ include file="../base/header.jsp"%>
	<body>
		<%@ include file="../base/navbar.jsp"%>
	
		<div class="container">			
			<br>			
			<f:form action="${s:mvcUrl('salvarProdutoUrl').build()}" method="post" modelAttribute="produto">
				<f:hidden path="id"/>
				
				<div class="row">
					<div class="col s12">
						
						<div class="card">
							<div class="card-content">
								<span class="card-title center-align">Dados do Produto</span>
							
								<div class="row">
									<div class="input-field col s12">
										<label for="descricao">Descrição</label>
										<f:input path="descricao" cssClass="validate" />
										<f:errors path="descricao" cssClass="helper-text" />
									</div>
								</div>	
								
								<div class="row">
									<div class="input-field col s6">
										<label for="custoUnitario">Custo Unitário</label>
										<f:input path="custoUnitario" cssClass="validate" type="number" step="0.01" min="0" />
										<f:errors path="custoUnitario" cssClass="helper-text" />
									</div>
									<div class="input-field col s6">
										<label for="precoVenda">Preço de Venda</label>
										<f:input path="precoVenda" cssClass="validate" type="number" step="0.01" min="0" />
										<f:errors path="precoVenda" cssClass="helper-text" />
									</div>						
								</div>
								
								<div class="row">
									<div class="input-field col s9">
										<label for="categoria">Categoria</label>
										<f:input path="categoria" cssClass="validate" maxlength="10" />
										<f:errors path="categoria" cssClass="helper-text" />
									</div>	
									
									<div class="input-field col s3">
										<label for="validade">Validade</label>
										<f:input path="validade" cssClass="validate datepicker"/>
										<f:errors path="validade" cssClass="helper-text" />
									</div>
																
								</div>					
							
							</div>
							
							<div class="card-action">
								<input class="btn-small green" type="submit" value="Salvar">
								<a href="${s:mvcUrl('listarProdutoUrl').build()}" class="btn-small orange">voltar</a>
							</div>						
						
						</div>					
					
					</div>
				</div>
			</f:form>
		</div>
	
		<%@ include file="../base/scripts.jsp"%>
		
		<script src="/js/fornecedor/form.js"></script>
	</body>
</html>

<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<html>
	<%@ include file="../base/header.jsp"%>
	<body>
		<%@ include file="../base/navbar.jsp"%>
	
		<div class="container">			
			<br>			
			<f:form action="${s:mvcUrl('salvarFornecedorUrl').build()}" method="post" modelAttribute="fornecedor">
				<f:hidden path="id"/>
				
				<div class="row">
					<div class="col s12">
						<div class="card">
							<div class="card-content">
								<span class="card-title center-align">Dados do Fornecedor</span>
								
								<div class="row">
									<div class="input-field col s12">
										<f:input path="razaoSocial" cssClass="validate" />
										<f:errors path="razaoSocial" cssClass="helper-text" />
										<label class="active" for="razaoSocial">Razão Social</label>
									</div>
								</div>
								
								<div class="row">
									<div class="input-field col s12">
										<label for="nomeFantasia">Nome Fantasia</label>
										<f:input path="nomeFantasia" cssClass="validate" />
										<f:errors path="nomeFantasia" cssClass="helper-text" />
									</div>
								</div>
								
								<div class="row">
									<div class="input-field col s4">
										<label for="cnpj">CNPJ</label>
										<f:input path="cnpj" cssClass="validate fmt-cnpj" maxlength="14" />
										<f:errors path="cnpj" cssClass="helper-text" />
									</div>
									<div class="input-field col s8">
										<label for="nomeResponsavel">Responsável</label>
										<f:input path="nomeResponsavel" cssClass="validate" />
										<f:errors path="nomeResponsavel" cssClass="helper-text" />
									</div>
								</div>
								
								<div class="row">
									<div class="input-field col s2">
										<label for="cep">CEP</label>
										<f:input path="endereco.cep" cssClass="validate fmt-cep" />
										<f:errors path="endereco.cep" cssClass="helper-text" />
									</div>
								</div>

								<div class="row">
									<div class="input-field col s12">
										<label for="endereco">Endereço</label>
										<f:input path="endereco.endereco" cssClass="validate" />
										<f:errors path="endereco.endereco" cssClass="helper-text" />
									</div>
								</div>

								<div class="row">
									<div class="input-field col s2">
										<label for="numero">Número</label>
										<f:input path="endereco.numero" cssClass="validate" />
										<f:errors path="endereco.numero" cssClass="helper-text" />
									</div>
									<div class="input-field col s5">
										<label for="complemento">Complemento</label>
										<f:input path="endereco.complemento" cssClass="validate" />
										<f:errors path="endereco.complemento" cssClass="helper-text" />
									</div>
									<div class="input-field col s5">
										<label for="bairro">Bairro</label>
										<f:input path="endereco.bairro" cssClass="validate" />
										<f:errors path="endereco.bairro" cssClass="helper-text" />
									</div>
								</div>

								<div class="row">
									<div class="input-field col s8">
										<label for="municipio">Município</label>
										<f:input path="endereco.municipio" cssClass="validate" />
										<f:errors path="endereco.municipio" cssClass="helper-text" />
									</div>
									<div class="input-field col s4">
										<label for="uf">UF</label>
										<f:input path="endereco.uf" cssClass="validate" />
										<f:errors path="endereco.uf" cssClass="helper-text" />
									</div>
								</div>
								
							</div>
								
							<div class="card-action">
								<input class="btn-small green" type="submit" value="Salvar">
								<a href="${s:mvcUrl('listarFornecedorUrl').build()}" class="btn-small orange">voltar</a>
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

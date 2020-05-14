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
			<f:form action="${s:mvcUrl('salvarPerfilUrl').build()}" method="post" modelAttribute="perfil">
				<div class="row">
					<div class="col s12">
						<div class="card">
							<div class="card-content">
								<span class="card-title center-align">Dados do Perfil</span>
								
								<div class="row">
									<div class="input-field col s12">
										<label for="authority">Nome</label>
										<f:input path="authority" cssClass="validate" />
										<f:errors path="authority" cssClass="helper-text" />
									</div>								
								</div>
								
								<div class="row">
									<div class="input-field col s12">
										<label for="descricao">Descrição</label>
										<f:input path="descricao" cssClass="validate" />
										<f:errors path="descricao" cssClass="helper-text" />
									</div>
								</div>								
							</div>
								
							<div class="card-action">
								<input class="btn-small green" type="submit" value="Salvar">
								<a href="${s:mvcUrl('listarPerfilUrl').build()}" class="btn-small orange">voltar</a>
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

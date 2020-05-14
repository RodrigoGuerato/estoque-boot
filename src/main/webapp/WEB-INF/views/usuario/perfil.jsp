<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<html lang="pt-br">
	<%@ include file="../base/header.jsp"%>
	<body>
		<%@ include file="../base/navbar.jsp"%>
	
		<div class="container">
			<br>
			
			<div class="row">
				<div class="col s12">
					<div class="card">
						<div class="card-content">
							<span class="card-title">Dados do Perfil</span>
							
							<div class="row">
								<div class="col s12">
									<fmt:parseDate value="${usuario.dataNascimento}" pattern="yyyy-MM-dd" var="parsedDate" type="date" />
									<ul>
										<li>Login: ${usuario.username}</li>
										<li>Nome: ${usuario.nomeCompleto}</li>
										<li>Nascimento: <fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy"/> </li>
									</ul>								
								</div>
							</div>							
							<div class="row">
								<div class="col s12">
									<img src="${pageContext.request.contextPath}/${usuario.caminhoFoto}" width="300px"  />
								</div>
							</div>
							
							<div class="card-action">
								<div class="row">
									<div class="col s12">
										<f:form action="${s:mvcUrl('alterFotoPerfilUrl').build()}" method="POST" enctype="multipart/form-data">
											<div class="file-field input-field">
												<div class="btn">
											        <span>Foto</span>
											        <input type="file" name="foto">												
												</div>	
												<div class="file-path-wrapper">
													<input class="file-path validate" type="text">
												</div>										
											</div>
											<input class="btn-small green" type="submit" value="Alterar Foto">		
										</f:form>									
									</div>
								</div>								
							</div>
						</div>					
					</div>				
				</div>			
			</div>
		
		</div>
	
		<%@ include file="../base/scripts.jsp"%>
	</body>
</html>
<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html lang="pt-br">
	<%@ include file="base/header.jsp"%>
	<body>
		<%@ include file="base/navbar.jsp"%>
	
		<div class="container">
			
			<sec:authorize access="isAuthenticated()">
				<sec:authentication property="principal" var="usuarioPrincipal" />
				<div class="row">
					<div class="col s12">
						<h1>Estou sendo exibido porque o usuário esta logado</h1>
						<p>Usuario Logado: ${usuarioPrincipal.nomeCompleto}</p>
								
					</div>
				</div>
			</sec:authorize>
			
			
		
		</div>
	
		<%@ include file="base/scripts.jsp"%>
	</body>
</html>
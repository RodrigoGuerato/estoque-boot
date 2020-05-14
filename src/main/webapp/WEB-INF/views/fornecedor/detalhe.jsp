<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<html>
	<%@ include file="../base/header.jsp" %>

<body>
	<%@ include file="../base/navbar.jsp" %>
	<div class="container">
		<div>
			<h4>Dados do Fornecedor</h4>
		</div>
		<div>
			<ul>
				<li>Id: ${fornecedor.id}</li>
				<li>Razão Social: ${fornecedor.razaoSocial}</li>
				<li>Nome Fantasia: ${fornecedor.nomeFantasia}</li>
				<li class="fmt-cnpj">CNPJ: ${fornecedor.cnpj}</li>
			</ul>
		</div>
		<div>
			<a class="btn btn-warning" href="${s:mvcUrl('listarFornecedorUrl').build()}">voltar</a>
		</div>
	</div>
	<%@ include file="../base/scripts.jsp" %>
</body>
</html>
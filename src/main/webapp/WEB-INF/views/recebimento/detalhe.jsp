<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
	<%@ include file="../base/header.jsp" %>

<body>
	<%@ include file="../base/navbar.jsp" %>
	<div class="container">
		<div class="row">
			<div class="col s12">
				<h5>Recebimento Nº ${registro.id}</h5>
			</div>
		</div>
		<div class="row">
			<div class="col s12">
				<fmt:parseDate value="${registro.dataNotaFiscal}" pattern="yyyy-MM-dd" var="dtaNotaFiscal" type="date" />
				<fmt:parseDate value="${registro.dataRecebimento}" pattern="yyyy-MM-dd" var="dtaRecebimento" type="date" />
				<ul>
					<li><strong>Série NF:</strong> ${registro.serieNotaFiscal}</li>
					<li><strong>Núumero NF:</strong> ${registro.numeroNotaFiscal}</li>
					<li><Strong>Data NF:</Strong> <fmt:formatDate value="${dtaNotaFiscal}" pattern="dd/MM/yyyy" /></li>
					<li><Strong>Número Pedido:</Strong> ${registro.pedido.id}</li>
					<li><Strong>Fornecedor:</Strong> ${registro.pedido.fornecedor.razaoSocial}</li>
					<li><Strong>Recebido em:</Strong> <fmt:formatDate value="${dtaRecebimento}" pattern="dd/MM/yyyy" /></li>
				</ul>			
			</div>		
		</div>		
		<div class="row">
			<div class="col s12 responsive-table">
				<table>
					<thead>
						<tr>
							<th>Produto</th>
							<th>Descrição</th>
							<th>Valor Unit.</th>
							<th>Qtde.</th>
							<th>Total</th>
						</tr>						
					</thead>
					<tbody>
						<c:forEach items="${registro.itensNotaFiscal}" var="itemNF">
							<tr>
								<td>${itemNF.produto.id}</td>
								<td>${itemNF.produto.descricao}</td>
								<td><fmt:formatNumber value="${itemNF.precoUnitario}" type="currency"/></td>
								<td>${itemNF.quantidade}</td>
								<td><fmt:formatNumber value="${itemNF.valorTotal}" type="currency"/></td>
							</tr>						
						</c:forEach>						
					</tbody>				
				</table>
			</div>
		</div>
		
		<div>
			<a class="btn btn-warning" href="${s:mvcUrl('listarRecebimentoUrl').build()}">voltar</a>
		</div>
	</div>
	<%@ include file="../base/scripts.jsp" %>
</body>
</html>
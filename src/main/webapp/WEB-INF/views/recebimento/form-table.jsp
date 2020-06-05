<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<f:form action="#" method="POST" modelAttribute="recebimentoForm">
	<div id="dadosRegistro">
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
</f:form>

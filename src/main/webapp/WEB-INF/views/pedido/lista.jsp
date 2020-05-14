<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
	<%@ include file="../base/header.jsp" %>
	
	<body>
		<%@ include file="../base/navbar.jsp" %>
		
		<div class="container">
			<br>

			<c:if test="${mensagemStatus != null}">
				<div class="green">
					<p>${mensagemStatus}</p>
				</div>
			</c:if>
			
			<div class="row">
				<div class="input-field col s6">
					<h5>Pedidos de Compra</h5>
				</div>
				<div class="input-field col s3 offset-s3">
					<a class="btn-floating btn-large waves-effect waves-light red right" title="novo" href="${s:mvcUrl('novoPedidoCompraUrl').build()}" >
						<i class="material-icons">add</i>
					</a>
				</div>
			</div>	
			
			<div class="row">
				<div class="col s12">
					<ul class="collapsible">
						<li>
							<div class="collapsible-header">
								<i class="material-icons">filter_list</i>Filtros
							</div>
							<div class="collapsible-body">
								<f:form method="GET" modelAttribute="pedidoFiltroForm" id="formFiltro">
									<f:hidden path="pagina"/>
									<f:hidden path="novoFiltro"/>
									
									<div class="row">
										<div class="input-field col s4">
											<label>
												<f:radiobutton path="tipoFiltro" value="RS" />
												<span>Razão Social</span>
											</label>
										</div>	
										<div class="input-field col s8">
											<label class="active" for="razaoSocial">Razão Social</label>
											<f:input path="razaoSocial" cssClass="validate"/>
										</div>
									</div>
									<div class="row">
										<div class="input-field col s4">
											<label>
												<f:radiobutton path="tipoFiltro" value="ST" />
												<span>Status</span>
											</label>
										</div>	
										<div class="input-field col s8">
											<label class="active" for="status">Status</label>
											<f:select path="status">
												<f:options items="${listaDeStatus}" itemLabel="displayValue" />
											</f:select>
										</div>
									</div>
									<div class="row">
										<div class="input-field col s4">
											<label>
												<f:radiobutton path="tipoFiltro" value="DT" />
												<span>Data de Entrega</span>
											</label>
										</div>	
										<div class="input-field col s4">
											<label class="active" for="entregaInicial">Entrega (Inicial)</label>
											<f:input path="entregaInicial" cssClass="validate datepicker"/>
										</div>	
										<div class="input-field col s4">
											<label class="active" for="entregaFinal">Entrega (Final)</label>
											<f:input path="entregaFinal" cssClass="validate datepicker"/>
										</div>	
									</div>
									<div class="row">
										<div class="col s12">
											<button class="btn-small right" type="button" id="btnFiltro">Pesquisar</button>
										</div>
									</div>
								</f:form>
							</div>
						</li>
					</ul>				
				</div>			
			</div>		
			
			<c:if test="${!listaPagina.isEmpty()}">
				<div class="row">
					<div class="responsive-table col s12">
						<table>
							<thead>
								<tr>
									<th>Nº</th>
									<th>Fornecedor</th>
									<th>Entrega</th>
									<th>Condição Pagto.</th>
									<th>Valor</th>
									<th>Status</th>
									<th class="center-align">Ações</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${listaPaginada.content}" var="registro" >
									<tr>
										<fmt:parseDate value="${registro.dataEntrega}" pattern="yyyy-MM-dd" var="dataModificada" type="date" />
										<td>${registro.id}</td>
										<td>${registro.fornecedor.razaoSocial}</td>
										<td><fmt:formatDate value="${dataModificada}" pattern="dd/MM/yyyy" /></td>
										<td>${registro.condicaoPagamento.displayValue}</td>
										<td><fmt:formatNumber value="${registro.valorTotal}" type="currency"/></td>
										<td>${registro.status.displayValue}</td>
										<td class="center-align">
											<a class="btn-small green" title="alterar" href="${s:mvcUrl('alterarPedidoCompraUrl').arg(0, registro.id).build()}"><i class="material-icons">edit</i></a>
											<a class="btn-small" title="detalhes" href="${s:mvcUrl('detalharPedidoUrl').arg(0, registro.id).build()}"><i class="material-icons">more_horiz</i></a>
											<button class="btn-small red modal-excluir" title="excluir" type="button" data-target="modalExcluir" data-descr="${registro.id}"  >
												<i class="material-icons">delete</i>
												<f:form action="${s:mvcUrl('excluirPedidoCompraUrl').arg(0, registro.id).build()}" method="post">
												</f:form>
											</button>
											<a href="${s:mvcUrl('geraPdfPedidoUrl').arg(0, registro.id).build()}" target="__blank"  class="btn-small deep-purple" title="imprimir" type="button">
												<i class="material-icons">print</i>
											</a>
										</td>
									</tr>			
								</c:forEach>		
							</tbody>	
						</table>
					</div>
				</div>
				
				<c:if test="${listaPaginada.hasContent()}">   
					<div class="row">
						<div class="col s12">
					        <ul class="pagination">
					            <li class="${listaPaginada.hasPrevious() ? '' : 'disabled'}">
					            	<c:if test="${listaPaginada.hasPrevious()}">
				            			<a href="#" class="paginacao" data-pagina="${listaPaginada.getNumber()}"><i class="material-icons">chevron_left</i></a>
					            	</c:if>
					            	<c:if test="${!listaPaginada.hasPrevious()}">
					            		<a href="#" class="paginacao"><i class="material-icons">chevron_left</i></a>
					            	</c:if>
					            </li>
					            
					            <c:forEach var="cont" begin="0" end="${listaPaginada.getTotalPages()-1}"    >
						            <li class="${cont==listaPaginada.getNumber() ? 'active' : ''}">
						            	<a href="#" class="paginacao" data-pagina="${cont+1}">${cont+1}</a>
						            </li>
					            </c:forEach>
					            
					            <li class="${listaPaginada.hasNext() ? '' : 'disabled'}" >
					            	<c:if test="${listaPaginada.hasNext()}">
					            		<a href="#" class="paginacao" data-pagina="${listaPaginada.getNumber()+2}" rel="next"><i class="material-icons">chevron_right</i></a>
					            	</c:if>				            	
					            	<c:if test="${!listaPaginada.hasNext()}">
					            		<a href="#" class="paginacao" rel="next"><i class="material-icons">chevron_right</i></a>
					            	</c:if>
					            </li>
					        </ul>				
						</div>
					</div>
				</c:if>
				
			</c:if>
		</div>
		<%@ include file="../base/modalExcluir.jsp" %>	
	</body>
	<%@ include file="../base/scripts.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".paginacao").on("click", function(e) {
				e.preventDefault();
				var pagina = $(this).data("pagina");
				
				//Muda o valor da pagina no formulario
				$("#pagina").val(pagina);
				$("#novoFiltro").val("false");
				$("#formFiltro").submit();
			});
			
			$("#btnFiltro").on("click", function(e) {
				e.preventDefault();
				$("#novoFiltro").val("true");
				$("#formFiltro").submit();
			});			
		});	
	</script>	
</html>
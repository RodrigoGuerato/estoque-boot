<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<nav>
	<div class="nav-wrapper grey darken-3">
		<a href="${s:mvcUrl('homeUrl').build()}" class="brand-logo">Gestão Estoque</a> 
		<a href="#" data-target="mobile-demo" class="sidenav-trigger"><i class="material-icons">menu</i></a>
		<ul class="right hide-on-med-and-down">
	        <li><a href="${s:mvcUrl('listarFornecedorUrl').build()}">Fornecedores</a></li>
	        <li><a href="${s:mvcUrl('listarProdutoUrl').build()}">Produtos</a></li>
	        <li><a href="${s:mvcUrl('listarPedidoCompraUrl').build()}">Pedidos de Compra</a></li>
	        <li><a href="${s:mvcUrl('listarRecebimentoUrl').build()}">Recebimento</a></li>
	      	
	      	<sec:authorize access="!isAuthenticated()">
	      		<li><a href="${s:mvcUrl('loginUrl').build()} ">Entrar</a></li>
	      	</sec:authorize>
	      	
	      	<sec:authorize access="isAuthenticated()">
	      		<sec:authentication property="principal" var="user"/>
	      		
				<ul id="dropUsuario" class="dropdown-content">
					<li><a href="${s:mvcUrl('perfilUsuarioUrl').build()}">Detalhes</a></li>
					
					<sec:authorize access="hasRole('ROLE_ADMIN')" >
						<li role="separator" class="divider"></li>
						<li><a href="${s:mvcUrl('listarPerfilUrl').build()}">Perfis de Acesso</a></li>
						<li><a href="${s:mvcUrl('listarUsuarioUrl').build()}">Usuários</a></li>
					</sec:authorize>
					
					<li class="divider"></li>
					<li><a href="${pageContext.request.contextPath}/logout">Sair</a></li>					
				</ul>
				
				<li>
					<a class="dropdown-trigger valign-wrapper" href="#!" data-target="dropUsuario">
					
						<c:choose>
							<c:when test="${user.caminhoFoto != null}">
								<img class="img-perfil" src="${pageContext.request.contextPath}/${user.caminhoFoto}" />
							</c:when>
							<c:otherwise>
								${user.nomeCompleto}
							</c:otherwise>
						</c:choose>
						
						<i class="material-icons right">arrow_drop_down</i>
					</a>
				</li>	      		
	      	
	      	</sec:authorize>	
		</ul>
	</div>
</nav>

<ul class="sidenav" id="mobile-demo">
	<li><a href="${s:mvcUrl('listarFornecedorUrl').build()}">Fornecedores</a></li>
	<li><a href="${s:mvcUrl('listarProdutoUrl').build()}">Produtos</a></li>
	<li><a href="${s:mvcUrl('listarPedidoCompraUrl').build()}">Pedidos de Compra</a></li>
	<li><a href="${s:mvcUrl('listarRecebimentoUrl').build()}">Recebimento</a></li>
	      	
	<sec:authorize access="!isAuthenticated()">
		<li><a href="${s:mvcUrl('loginUrl').build()} ">Entrar</a></li>
	</sec:authorize>
	
	<sec:authorize access="isAuthenticated()">
		<sec:authentication property="principal" var="user"/>
		
		
		<sec:authorize access="hasRole('ROLE_ADMIN')" >
			<li role="separator" class="divider"></li>
			<li><a href="#">${user.nomeCompleto}</a></li>
			<li><a href="${s:mvcUrl('listarPerfilUrl').build()}">Perfis de Acesso</a></li>
			<li><a href="${s:mvcUrl('listarUsuarioUrl').build()}">Usuários</a></li>
		</sec:authorize>
		<li class="divider"></li>
		<li><a href="${pageContext.request.contextPath}/logout">Sair</a></li>	
	</sec:authorize>
	
	
	
	
		
</ul>
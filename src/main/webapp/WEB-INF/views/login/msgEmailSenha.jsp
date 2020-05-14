<!DOCTYPE html>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<html>
	<%@ include file="../base/header.jsp"%>

	<body>
		<div class="container"> 
			<div class="row">
				<div class="col s8 offset-s2">
					<div class="card">
						<div class="card-content">
							<span class="card-title"><strong>Recuperação de Senha</strong></span>
							<div class="row">
								<div class="col s12">
									<h6>Foi enviado um link no seu email, com instruções para a correta alteração da senha</h6>
								</div>
							</div>
							<div class="row">
								<div class="col s12">
									<a href="${s:mvcUrl('loginUrl').build()}">voltar para o login</a>
								</div>
							</div>
						</div>
					</div>
				</div>			
			</div>
		</div>
	</body>	
	<%@ include file="../base/scripts.jsp" %>
</html>
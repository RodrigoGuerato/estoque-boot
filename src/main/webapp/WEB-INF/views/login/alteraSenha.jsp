<!DOCTYPE html>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<html>
	<%@ include file="../base/header.jsp"%>

	<body>
		<div class="container">
			<f:form action="${s:mvcUrl('alteraSenhaUrl').build()}" method="POST" modelAttribute="alteraSenhaForm">
				<f:hidden path="idUsuario"/>
				<div class="row">
					<div class="col s6 offset-s3">
						<div class="card">
							<div class="card-content">
								<span class="card-title">Alterar Senha</span>
								<div class="row">
									<div class="input-field col s12">
										<label for="novaSenha">Informe a Senha</label>
										<f:input path="novaSenha" cssClass="validate" type="password" />
										<f:errors path="novaSenha" cssClass="helper-text" />
									</div>
								</div>								
								<div class="row">
									<div class="input-field col s12">
										<label for="novaSenhaConfirma">Confirme a Senha</label>
										<f:input path="novaSenhaConfirma" cssClass="validate" type="password" />
										<f:errors path="novaSenhaConfirma" cssClass="helper-text" />
									</div>
								</div>								
							</div>
							<div class="card-action">
								<button class="btn waves-effect waves-light" type="submit">
									Alterar <i class="material-icons right">send</i>
								</button>
								</div>						
						</div>
					</div>			
				</div>
			</f:form>
		</div>
		
		
	</body>	
	<%@ include file="../base/scripts.jsp" %>
</html>
<!DOCTYPE html>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<html>
	<%@ include file="../base/header.jsp"%>

	<body>
		<div class="container"> 
			
			<f:form action="${s:mvcUrl('recupSenhaUrl').build()}" method="POST" modelAttribute="recupSenhaForm">
				<div class="row">
					<div class="col s6 offset-s3">
						<div class="card">
							<div class="card-content">
								<span class="card-title">Recuperar Senha</span>
								
								<div class="row">
									<div class="input-field col s12">
										<label for="email">Informe o seu email</label>
										<f:input path="email" cssClass="validate" />
										<f:errors path="email" cssClass="helper-text" />
									</div>
								</div>								
								
							</div>
							<div class="card-action">
								<button class="btn waves-effect waves-light" type="submit">
									Enviar <i class="material-icons right">send</i>
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
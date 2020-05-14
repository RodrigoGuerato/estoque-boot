package br.com.fapen.estoque.services;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import br.com.fapen.estoque.models.Usuario;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private ArquivoService arquivoService;

	public void enviarEmailTexto(String emailPara, String assunto) {
		SimpleMailMessage objMensagem = new SimpleMailMessage();

		objMensagem.setTo(emailPara);
		objMensagem.setSubject(assunto);
		objMensagem.setText("Email de teste!!!");

		try {
			emailSender.send(objMensagem);
			System.out.println("Email enviado com sucesso !");
		} catch (Exception e) {
			System.out.println("Erro no email --> " + e.getMessage());
		}
	}

	public void enviarEmailHtml(String emailPara, String assunto, String conteudoMensagem) {
		try {
			MimeMessage objMensagemHtml = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(objMensagemHtml, true);

			helper.setTo(emailPara);
			helper.setSubject(assunto);
			helper.setText(conteudoMensagem, true);

			emailSender.send(objMensagemHtml);
			System.out.println("Email enviado com sucesso !");
		} catch (Exception e) {
			System.out.println("Erro no email --> " + e.getMessage());
		}
	}

	public void enviarEmailRecupSenha(HttpServletRequest request, Usuario user) {
		try {
			String assunto = "Recuperação de Senha - Estoque";
			String conteudo = arquivoService.loadTemplateEmail("recupSenhaTemplate.html");
			String linkRecuperacao = request.getRequestURL().toString() + "/alterarSenha?token=" + user.getHash();
			
			conteudo = conteudo.replace("${nomeCompleto}", user.getNomeCompleto());
			conteudo = conteudo.replace("${link}", linkRecuperacao);
			
			this.enviarEmailHtml(user.getEmail(), assunto, conteudo);
		} catch (Exception e) {
			System.out.println("Erro no envio do email --> " + e.getMessage());
		}
	}
}

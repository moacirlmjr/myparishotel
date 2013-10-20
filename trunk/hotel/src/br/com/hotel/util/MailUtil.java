package br.com.hotel.util;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

public class MailUtil {

	private static final String ACOOUNT_USER = "danyllowagneralbuquerque";
	private static final String MAIL_USER = "danyllowagneralbuquerque@mail.com";
	private static final String MAIL_PWD = "wagner27";
	private static final String MAIL_TITLE = "Relatório de Ocupacao";
	private static final String MAIL_OWNER = "MyParis Hotel";

	@SuppressWarnings("deprecation")
	public void enviaEmailSimples() throws EmailException {

		SimpleEmail email = new SimpleEmail();

		try {
			email.setDebug(true);
			email.setHostName("smtp.gmail.com");
			email.setAuthentication(MailUtil.ACOOUNT_USER, MailUtil.MAIL_PWD);
			email.setSSL(true);
			email.addTo("danyllo_wagner@hotmail.com"); // pode ser qualquer email
			email.setFrom(MailUtil.MAIL_USER); // será passado o email que você fará
											// a autenticação
			email.setSubject(MailUtil.MAIL_TITLE + " - " + MailUtil.MAIL_OWNER);
			email.setMsg("Teste de envio de email");
			email.setMsg("oioioi" + "\n" + "oioioi");
			email.send();

		} catch (EmailException e) {

			System.out.println(e.getMessage());

		}
	}

	
	@SuppressWarnings("deprecation")
	public void enviaEmailComAnexo() throws EmailException {

		// cria o anexo 1.
		EmailAttachment anexo1 = new EmailAttachment();
		anexo1.setPath("teste/teste.txt"); // caminho do arquivo
											// (RAIZ_PROJETO/teste/teste.txt)
		anexo1.setDisposition(EmailAttachment.ATTACHMENT);
		anexo1.setDescription("Exemplo de arquivo anexo");
		anexo1.setName("teste.txt");

		// cria o anexo 2.
		EmailAttachment anexo2 = new EmailAttachment();
		anexo2.setPath("teste/teste2.jsp"); // caminho do arquivo
											// (RAIZ_PROJETO/teste/teste2.jsp)
		anexo2.setDisposition(EmailAttachment.ATTACHMENT);
		anexo2.setDescription("Exemplo de arquivo anexo");
		anexo2.setName("teste2.jsp");

		// configura o email
		MultiPartEmail email = new MultiPartEmail();
		email.setHostName("smtp.gmail.com"); // o servidor SMTP para envio do
												// e-mail
		email.addTo("teste@gmail.com", "Guilherme"); // destinatário
		email.setFrom("teste@gmail.com", "Eu"); // remetente
		email.setSubject("Teste -> Email com anexos"); // assunto do e-mail
		email.setMsg("Teste de Email utilizando commons-email"); // conteudo do
																	// e-mail
		email.setAuthentication("teste", "xxxxx");
		email.setSmtpPort(465);
		email.setSSL(true);
		email.setTLS(true);

		// adiciona arquivo(s) anexo(s)
		email.attach(anexo1);
		email.attach(anexo2);
		// envia o email
		email.send();
	}

	
	@SuppressWarnings("deprecation")
	public void enviaEmailFormatoHtml() throws EmailException,
			MalformedURLException {

		HtmlEmail email = new HtmlEmail();

		// adiciona uma imagem ao corpo da mensagem e retorna seu id
		URL url = new URL("http://www.apache.org/images/asf_logo_wide.gif");
		String cid = email.embed(url, "Apache logo");

		// configura a mensagem para o formato HTML
		email.setHtmlMsg("<html>Logo do Apache - <img ></html>");

		// configure uma mensagem alternativa caso o servidor não suporte HTML
		email.setTextMsg("Seu servidor de e-mail não suporta mensagem HTML");

		email.setHostName("smtp.gmail.com"); // o servidor SMTP para envio do
												// e-mail
		email.addTo("teste@gmail.com", "Guilherme"); // destinatário
		email.setFrom("teste@gmail.com", "Eu"); // remetente
		email.setSubject("Teste -> Html Email"); // assunto do e-mail
		email.setMsg("Teste de Email HTML utilizando commons-email"); // conteudo
																		// do
																		// e-mail
		email.setAuthentication("teste", "xxxxx");
		email.setSmtpPort(465);
		email.setSSL(true);
		email.setTLS(true);
		// envia email
		email.send();
	}

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws MalformedURLException,
			EmailException {
		
		MailUtil mu = new MailUtil();
		mu.enviaEmailSimples();
	

	}
}
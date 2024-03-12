package me.designpattern.code.composition.facade;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
	private final EmailSettings emailSettings;

	public EmailSender(EmailSettings emailSettings) {
		this.emailSettings = emailSettings;
	}

	public void sendEmail(EmailMessage emailMessage) {
		String to = "wannidev0928@github.com";
		String from = "someone1234@github.com";

		Properties properties = System.getProperties();
		properties.setProperty("mail.stmp.host", emailSettings.getHost());

		Session session = Session.getDefaultInstance(properties);

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(emailMessage.getFrom()));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailMessage.getTo()));
			message.setSubject(emailMessage.getSubject());
			message.setText(emailMessage.getText());

			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}

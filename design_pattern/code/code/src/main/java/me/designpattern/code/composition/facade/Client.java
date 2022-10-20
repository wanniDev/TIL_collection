package me.designpattern.code.composition.facade;

public class Client {
	public static void main(String[] args) {
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFrom("wannidev0928@testmail.com");
		emailMessage.setTo("someone@testmail.com");
		emailMessage.setSubject("제목");
		emailMessage.setText("내용");
		
		EmailSettings emailSettings = new EmailSettings();
		emailSettings.setHost("127.0.0.1");
		EmailSender emailSender = new EmailSender(emailSettings);
		emailSender.sendEmail(emailMessage);
	}
}

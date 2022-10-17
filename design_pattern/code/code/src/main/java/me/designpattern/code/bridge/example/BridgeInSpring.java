package me.designpattern.code.bridge.example;

public class BridgeInSpring {
	public static void main(String[] args) {
		MailSender mailSender = new JavaMailSenderImpl();
		PlatformTransactionManager platformTransactionManager = new JdbcTransactionManager();
	}
}

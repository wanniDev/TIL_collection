package me.designpattern.code.composition.adapter.tutorial;

import me.designpattern.code.composition.adapter.tutorial.security.LoginHandler;
import me.designpattern.code.composition.adapter.tutorial.security.UserDetailsService;

public class App {
	public static void main(String[] args) {
		AccountService accountService = new AccountService();
		UserDetailsService userDetailsService = new AccountUserDetailsService(accountService);
		LoginHandler loginHandler = new LoginHandler(userDetailsService);
		String result = loginHandler.login("wannidev", "wannidev");
		System.out.println(result);

	}
}

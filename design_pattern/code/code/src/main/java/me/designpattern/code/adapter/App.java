package me.designpattern.code.adapter;

import me.designpattern.code.adapter.security.LoginHandler;
import me.designpattern.code.adapter.security.UserDetailsService;

public class App {
	public static void main(String[] args) {
		AccountService accountService = new AccountService();
		UserDetailsService userDetailsService = new AccountUserDetailsService(accountService);
		LoginHandler loginHandler = new LoginHandler(userDetailsService);
		String result = loginHandler.login("wannidev", "wannidev");
		System.out.println(result);

	}
}

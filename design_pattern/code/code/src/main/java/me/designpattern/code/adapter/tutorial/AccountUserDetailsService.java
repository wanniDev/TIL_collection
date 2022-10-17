package me.designpattern.code.adapter.tutorial;

import me.designpattern.code.adapter.tutorial.security.UserDetails;
import me.designpattern.code.adapter.tutorial.security.UserDetailsService;

public class AccountUserDetailsService implements UserDetailsService {

	private final AccountService accountService;

	public AccountUserDetailsService(AccountService accountService) {
		this.accountService = accountService;
	}

	@Override
	public UserDetails loadUser(String username) {
		Account account = accountService.findAccountByUsername(username);
		return new AccountUserDetails(account);
	}
}

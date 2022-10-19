package me.designpattern.code.composition.adapter.tutorial;

import me.designpattern.code.composition.adapter.tutorial.security.UserDetails;

public class AccountUserDetails implements UserDetails {

	private final Account account;

	public AccountUserDetails(Account account) {
		this.account = account;
	}

	@Override
	public String getUsername() {
		return this.account.getName();
	}

	@Override
	public String getPassword() {
		return this.account.getPassword();
	}
}

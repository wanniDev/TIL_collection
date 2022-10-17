package me.designpattern.code.adapter.tutorial.security;

public interface UserDetailsService {
	UserDetails loadUser(String username);
}

package me.designpattern.code.adapter.security;

public interface UserDetailsService {
	UserDetails loadUser(String username);
}

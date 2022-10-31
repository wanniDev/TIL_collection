package me.designpattern.code.composition.adapter.tutorial.security;

public interface UserDetailsService {
	UserDetails loadUser(String username);
}

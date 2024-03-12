package me.designpattern.code.behavior.responsibilitychain.example;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
			.authorizeRequests()
			.mvcMatchers("/", "/login", "/sign-up", "/check-email-token", "/email-login",
				"/login-link", "/h2-console").permitAll()
			.mvcMatchers(HttpMethod.GET,  "/profile/*").permitAll()
			.anyRequest().authenticated()

			.and()
			.formLogin()
			.loginPage("/login").permitAll()

			.and()
			.logout()
			.logoutSuccessUrl("/")

			.and()
			.build();
	}
}

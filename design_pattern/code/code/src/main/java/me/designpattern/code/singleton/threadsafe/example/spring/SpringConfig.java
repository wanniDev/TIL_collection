package me.designpattern.code.singleton.threadsafe.example.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
	@Bean
	public TestBean testBean() {
		return new TestBean();
	}
}

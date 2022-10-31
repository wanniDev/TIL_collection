package me.designpattern.code.behavior.interpreter.examle;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
public class SampleService implements ApplicationRunner {

	@Value("#{7 + 15}")
	private String value;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println(value);
	}
}

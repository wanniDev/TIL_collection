package me.designpattern.code.singleton.threadsafe.example.spring;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringExample {
	private static final ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
	public static TestBean getTestBean() {
		return applicationContext.getBean("testBean", TestBean.class);
	}
}

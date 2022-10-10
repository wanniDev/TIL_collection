package me.designpattern.code.factorymethod.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import me.designpattern.code.singleton.threadsafe.example.spring.SpringConfig;
import me.designpattern.code.singleton.threadsafe.example.spring.TestBean;

public class SpringBeanFactoryEx {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext xmlFactory = new ClassPathXmlApplicationContext(
			"config.xml");
		TestBean testBean = xmlFactory.getBean("testBean", TestBean.class);
		AnnotationConfigApplicationContext javaFactory = new AnnotationConfigApplicationContext(
			SpringConfig.class);
		TestBean javaFactoryBean = javaFactory.getBean("testBean", TestBean.class);
	}
}

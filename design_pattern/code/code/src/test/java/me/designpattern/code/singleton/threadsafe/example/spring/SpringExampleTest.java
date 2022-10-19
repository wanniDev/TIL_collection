package me.designpattern.code.singleton.threadsafe.example.spring;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import me.designpattern.code.creational.singleton.threadsafe.example.spring.SpringExample;
import me.designpattern.code.creational.singleton.threadsafe.example.spring.TestBean;

@SpringBootTest
class SpringExampleTest {
	@Test
	@DisplayName("스프링 컨테이너는 기본적으로 빈을 싱글톤 스코프로 관리한다.")
	void isSingletonBean() {
		TestBean testBean1 = SpringExample.getTestBean();
		TestBean testBean2 = SpringExample.getTestBean();

		assertThat(testBean1).isSameAs(testBean2);
	}
}
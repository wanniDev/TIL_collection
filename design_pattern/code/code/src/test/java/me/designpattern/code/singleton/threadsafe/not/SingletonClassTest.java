package me.designpattern.code.singleton.threadsafe.not;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SingletonClassTest {
	@Test
	@DisplayName("기본적인 싱글톤 패턴은 단일 스레드 환경에서는 싱글톤을 보장한다.")
	void checkSingleton() {
		SingletonClass singletonClass1 = SingletonClass.getInstance();
		SingletonClass singletonClass2 = SingletonClass.getInstance();

		assertThat(singletonClass1).isSameAs(singletonClass2);
	}
}
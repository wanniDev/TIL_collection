package me.designpattern.code.singleton.threadsafe.safe.eager;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SingletonEnumTest {
	@Test
	@DisplayName("enum을 활용하면, 좀 더 단순하고 안전하게 싱글톤을 보장할 수 있다.")
	void isSingleton() {
		SingletonEnum instance1 = SingletonEnum.INSTANCE;
		SingletonEnum instance2 = SingletonEnum.INSTANCE;

		assertThat(instance1).isSameAs(instance2);
	}
}
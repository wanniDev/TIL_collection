package me.designpattern.code.singleton.threadsafe.humanerror;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import me.designpattern.code.singleton.threadsafe.safe.eager.SingletonEnum;
import me.designpattern.code.singleton.threadsafe.safe.lazy.LazyHolderSingleton;

class PossibleErrorTest {
	@Test
	@DisplayName("싱글톤 패턴으로 생성한 인스턴스와 리플렉션으로 생성한 객체는 싱글톤을 보장하지 못한다.")
	void notSingletonWithReflection() {
		LazyHolderSingleton singletonInstance = LazyHolderSingleton.getInstance();
		LazyHolderSingleton reflectedInstance = PossibleError.getInstanceWithReflection();

		assertThat(singletonInstance).isNotSameAs(reflectedInstance);
	}

	@Test
	@DisplayName("싱글톤 패턴으로 생성된 객체를 직렬화할 경우, 싱글톤을 보장하지 못한다.")
	void notSingletonWithSerialized() {
		LazyHolderSingleton singletonInstance = LazyHolderSingleton.getInstance();
		LazyHolderSingleton deserializedInstance = PossibleError.getDeserializedInstance();

		assertThat(singletonInstance).isNotSameAs(deserializedInstance);
	}

	@Test
	@DisplayName("Enum은 리플렉션 생성자체가 불가능하기 때문에, 리플렉션으로 인해 싱글톤이 깨지는 것을 컴파일 타임에서 막을 수 있다.")
	void cannotReflectWithEnum() {
		assertThrows(RuntimeException.class, PossibleError::getEnumInstanceWithReflection);
	}
}
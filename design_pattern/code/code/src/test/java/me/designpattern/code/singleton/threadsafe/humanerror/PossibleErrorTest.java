package me.designpattern.code.singleton.threadsafe.humanerror;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import me.designpattern.code.singleton.threadsafe.safe.lazy.LazyHolderSingleton;

class PossibleErrorTest {
	@Test
	@DisplayName("싱글톤 패턴으로 생성한 인스턴스와 리플렉션으로 생성한 객체는 싱글톤을 보장하지 못한다.")
	void notSingletonWithReflection() {
		LazyHolderSingleton singletonInstance = LazyHolderSingleton.getInstance();
		LazyHolderSingleton reflectedInstance = PossibleError.getInstanceWithReflection();

		assertThat(singletonInstance).isNotSameAs(reflectedInstance);
	}
}
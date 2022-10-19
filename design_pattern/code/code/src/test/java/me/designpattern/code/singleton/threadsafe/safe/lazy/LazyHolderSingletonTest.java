package me.designpattern.code.singleton.threadsafe.safe.lazy;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import me.designpattern.code.creational.singleton.threadsafe.safe.lazy.LazyHolderSingleton;

class LazyHolderSingletonTest {
	@Test
	@DisplayName("좀더 개선된 LazyHolding 기법의 싱글톤")
	void lazyHoldingSingleton() {
		LazyHolderSingleton lazyHolderSingleton1 = LazyHolderSingleton.getInstance();
		LazyHolderSingleton lazyHolderSingleton2 = LazyHolderSingleton.getInstance();

		assertThat(lazyHolderSingleton1).isSameAs(lazyHolderSingleton2);
	}
}
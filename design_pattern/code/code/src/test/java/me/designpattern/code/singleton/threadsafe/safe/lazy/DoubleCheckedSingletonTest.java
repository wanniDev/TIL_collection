package me.designpattern.code.singleton.threadsafe.safe.lazy;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import me.designpattern.code.singleton.threadsafe.safe.lazy.DoubleCheckedSingleton;

class DoubleCheckedSingletonTest {
	@Test
	@DisplayName("null 체크를 2번하여, 불필요한 락을 걸지 않게 해준다.")
	void doubleCheckedSingleton() {
		DoubleCheckedSingleton doubleCheckedSingleton1 = DoubleCheckedSingleton.getInstance();
		DoubleCheckedSingleton doubleCheckedSingleton2 = DoubleCheckedSingleton.getInstance();
		assertThat(doubleCheckedSingleton1).isSameAs(doubleCheckedSingleton2);
	}
}
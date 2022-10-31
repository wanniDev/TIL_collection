package me.designpattern.code.composition.decorator.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponseWrapper;

public class DecoratorInJava {
	public static void main(String[] args) {
		ArrayList list = new ArrayList<>();
		list.add(new Test());

		// 기존 콜렉션에 타입을 체크하는 기능을 추가
		List tests = Collections.checkedList(list, Test.class);

		list.add(new Test2());
		tests.add(new Test2());

		Collections.synchronizedList(list);
		Collections.unmodifiableList(list);

		// 서블릿 요청/응답 데코레이터 지원
		HttpServletRequestWrapper httpServletRequestWrapper;
		HttpServletResponseWrapper httpServletResponseWrapper;
	}
}

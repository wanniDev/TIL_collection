package me.designpattern.code.behavior.interpreter;

import java.util.ArrayDeque;

public class PostfixNotation {

	private final String expression;

	public PostfixNotation(String expression) {
		this.expression = expression;
	}

	public static void main(String[] args) {
		/**
		 * infix : 1 + 2 - 5
		 * postfix : 1 2 + 5 -
		 */
		PostfixNotation postfixNotation = new PostfixNotation("123+-");
		// TODO 123+- -> 15- -> -4
		postfixNotation.calculate();
	}

	private void calculate() {
		ArrayDeque<Integer> numbers = new ArrayDeque<>();
		for (char c : this.expression.toCharArray()) {
			switch (c) {
				case '+' -> numbers.push(numbers.pop() + numbers.pop());
				case '-' -> {
					int right = numbers.pop();
					int left = numbers.pop();
					numbers.push(left - right);
				}
				default -> numbers.push(Integer.parseInt(String.valueOf(c)));
			}
		}
		System.out.println(numbers.pop());
	}
}

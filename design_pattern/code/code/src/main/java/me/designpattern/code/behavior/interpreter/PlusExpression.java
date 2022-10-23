package me.designpattern.code.behavior.interpreter;

import java.util.Map;

public class PlusExpression implements PostfixExpression {
	private final PostfixExpression left, right;

	public PlusExpression(PostfixExpression left, PostfixExpression right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public int interpret(Map<Character, Integer> context) {
		return left.interpret(context) + right.interpret(context);
	}
}

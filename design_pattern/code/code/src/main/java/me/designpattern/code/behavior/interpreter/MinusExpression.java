package me.designpattern.code.behavior.interpreter;

import java.util.Map;

public class MinusExpression implements PostfixExpression {

	private final PostfixExpression left, right;

	public MinusExpression(PostfixExpression left, PostfixExpression right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public int interpret(Map<Character, Integer> context) {
		return left.interpret(context) - right.interpret(context);
	}
}

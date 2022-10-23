package me.designpattern.code.behavior.interpreter.examle;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class InterpreterInSpring {
	public static void main(String[] args) {
		Book book = new Book("spring");
		ExpressionParser parser = new SpelExpressionParser();
		Expression expression = parser.parseExpression("name");
		System.out.println(expression.getValue(book));
	}
}

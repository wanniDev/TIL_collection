package me.designpattern.code.behavior.command;

public class MyApp {

	public static void main(String[] args) {
		Button button = new Button(new LightOnCommand(new Light()));
		button.press();
	}
}

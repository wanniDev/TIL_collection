package me.designpattern.code.behavior.command;

public class Button {
	private final Command command;

	public Button(Command command) {
		this.command = command;
	}

	public void press() {
		command.execute();
	}

	public static void main(String[] args) {
	}
}

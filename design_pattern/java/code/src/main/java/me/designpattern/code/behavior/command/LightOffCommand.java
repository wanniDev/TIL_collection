package me.designpattern.code.behavior.command;

public class LightOffCommand implements Command {
	private final Light light;

	public LightOffCommand(Light light) {
		this.light = light;
	}

	@Override
	public void execute() {
		light.off();
	}
}

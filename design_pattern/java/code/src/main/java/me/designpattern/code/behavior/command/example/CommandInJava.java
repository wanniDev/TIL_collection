package me.designpattern.code.behavior.command.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.designpattern.code.behavior.command.Game;
import me.designpattern.code.behavior.command.Light;

public class CommandInJava {
	public static void main(String[] args) {
		Light light = new Light();
		Game game = new Game();
		ExecutorService executorService = Executors.newFixedThreadPool(4);
		executorService.submit(light::on);
		executorService.submit(game::start);
		executorService.shutdown();
	}
}

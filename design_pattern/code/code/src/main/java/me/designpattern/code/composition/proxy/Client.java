package me.designpattern.code.composition.proxy;

public class Client {
	public static void main(String[] args) {
		GameService gameService = new GameService();
		gameService.startGame();
	}
}

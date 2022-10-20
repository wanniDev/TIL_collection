package me.designpattern.code.composition.proxy;

public class DefaultGameService implements GameService {
	@Override
	public void startGame() {
		System.out.println("Welcome to game");
	}
}

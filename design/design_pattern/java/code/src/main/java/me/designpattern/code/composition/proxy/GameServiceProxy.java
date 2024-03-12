package me.designpattern.code.composition.proxy;

public class GameServiceProxy implements GameService {

	private GameService gameService;

	public GameServiceProxy(GameService gameService) {
		this.gameService = gameService;
	}

	@Override
	public void startGame() {
		if (this.gameService == null)
			this.gameService = new DefaultGameService();

		long before = System.currentTimeMillis();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		gameService.startGame();
		System.out.println(System.currentTimeMillis() - before);
	}
}

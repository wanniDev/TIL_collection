package me.designpattern.code.composition.proxy.example;

import java.lang.reflect.Proxy;

import me.designpattern.code.composition.proxy.DefaultGameService;
import me.designpattern.code.composition.proxy.GameService;

public class ProxyInJava {
	private void dynamicProxy() {
		GameService gameServiceProxy = getGameServiceProxy(new DefaultGameService());
		gameServiceProxy.startGame();
	}

	private GameService getGameServiceProxy(GameService target) {
		return (GameService)Proxy.newProxyInstance(this.getClass().getClassLoader(),
			new Class[]{GameService.class}, (proxy, method, args) -> {
				System.out.println("Hi Dynamic proxy");
				method.invoke(target, args);
				System.out.println("Bye Dynamic proxy");
				return null;
			});
	}

	public static void main(String[] args) {
		ProxyInJava proxyInJava = new ProxyInJava();
		proxyInJava.dynamicProxy();
	}
}

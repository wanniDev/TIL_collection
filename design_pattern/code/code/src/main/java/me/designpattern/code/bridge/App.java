package me.designpattern.code.bridge;

public class App {
	public static void main(String[] args) {
		Champion ahri = new Ahri(new KDA());
		ahri.move();
		ahri.skillQ();
		ahri.skillW();
		ahri.skillE();
		ahri.skillR();
	}
}

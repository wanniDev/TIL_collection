package me.designpattern.code.composition.flyweight;

public class Client {
	public static void main(String[] args) {
		FontFactory factory = new FontFactory();
		Font font = factory.getFont("nanum:12");
		Character c1 = new Character('h', "white", font);
		Character c2 = new Character('h', "black", font);
		Character c3 = new Character('h', "yellow", font);
	}
}

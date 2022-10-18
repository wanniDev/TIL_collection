package me.designpattern.code.composit;

public class Client {
	public static void main(String[] args) {
		Item doranBlade = new Item("도란검", 400);
		Item potion = new Item("물약", 35);

		Bag bag = new Bag();
		bag.add(doranBlade);
		bag.add(potion);

		Client client = new Client();
		client.printPrice(doranBlade);
		client.printPrice(bag);
	}

	private void printPrice(Item item) {
		System.out.println(item.getPrice());
	}

	private void printPrice(Bag bag) {
		System.out.println(bag.getComponents());
	}
}

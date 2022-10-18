package me.designpattern.code.composit;

public class Item implements Component  {
	private String name;
	private int price;

	public Item() {
	}

	public Item(String name, int price) {
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int getPrice() {
		return price;
	}
}

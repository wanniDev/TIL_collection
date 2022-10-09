package me.designpattern.code.factorymethod;

public class DefaultProduct extends Product {
	public DefaultProduct(String name, String brand) {
		setName(name + " product");
		setBrand(brand + " brand");
	}
}

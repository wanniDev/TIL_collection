package me.designpattern.code.factorymethod;

public class FancyProductFactory implements ProductFactory {
	@Override
	public Product createProduct(String name, String brand) {
		return new FancyProduct();
	}
}

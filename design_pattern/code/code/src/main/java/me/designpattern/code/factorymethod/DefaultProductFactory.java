package me.designpattern.code.factorymethod;

public class DefaultProductFactory implements ProductFactory {

	@Override
	public Product createProduct(String name, String brand) {
		return new DefaultProduct(name, brand);
	}
}

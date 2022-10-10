package me.designpattern.code.factorymethod.productfactory;

public class FancyProductFactory implements ProductFactory {
	@Override
	public Product createProduct(String name, String brand) {
		return new FancyProduct();
	}
}

package me.designpattern.code.creational.factorymethod.productfactory;

public class FancyProductFactory implements ProductFactory {
	@Override
	public Product createProduct(String name, String brand) {
		return new FancyProduct();
	}
}

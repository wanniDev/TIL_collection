package me.designpattern.code.creational.factorymethod.productfactory;

public class NormalProductFactory implements ProductFactory {

	@Override
	public Product createProduct(String name, String brand) {
		return new NormalProduct();
	}
}

package me.designpattern.code.factorymethod.productfactory;

public class NormalProductFactory implements ProductFactory {

	@Override
	public Product createProduct(String name, String brand) {
		return new NormalProduct();
	}
}

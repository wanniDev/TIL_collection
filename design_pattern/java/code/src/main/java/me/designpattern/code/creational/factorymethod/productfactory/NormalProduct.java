package me.designpattern.code.creational.factorymethod.productfactory;

public class NormalProduct extends Product {
	public NormalProduct() {
		setName("Normal Product");
		setBrand("Normal Brand");
	}

	public NormalProduct(String name, String brand) {
		setName(name + " product");
		setBrand(brand + " brand");
	}
}

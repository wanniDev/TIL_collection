package me.designpattern.code.factorymethod;

public class NormalProduct extends Product {
	public NormalProduct() {
	}

	public NormalProduct(String name, String brand) {
		setName(name + " product");
		setBrand(brand + " brand");
	}
}

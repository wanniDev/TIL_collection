package me.designpattern.code.factorymethod;

public class Client {

	public void order(String name, String brand) {
		Product product = new DefaultProductFactory().orderProduct(name, brand);
		System.out.println(System.out.format("brand %s name %s order!", product.getBrand(), product.getName()));
	}
}

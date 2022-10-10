package me.designpattern.code.factorymethod.productfactory;

public class Client {

	public void order(ProductFactory productFactory, String name, String brand) {
		Product product = productFactory.orderProduct(name, brand);
		System.out.println(System.out.format("brand %s name %s order!", product.getBrand(), product.getName()));
	}
}

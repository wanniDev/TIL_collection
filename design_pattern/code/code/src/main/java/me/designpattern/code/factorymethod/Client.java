package me.designpattern.code.factorymethod;

public class Client {

	public void order(String name, String brand) {
		processOrder(new NormalProductFactory(), name, brand);
	}

	private void processOrder(ProductFactory productFactory, String name, String brand) {
		Product product = productFactory.orderProduct(name, brand);
		System.out.println(System.out.format("brand %s name %s order!", product.getBrand(), product.getName()));
	}
}

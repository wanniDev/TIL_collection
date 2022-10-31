package me.designpattern.code.creational.factorymethod.productfactory;

import org.springframework.util.StringUtils;

public interface ProductFactory {
	default Product orderProduct(String name, String brand) {
		validate(name, brand);
		return createProduct(name, brand);
	}

	Product createProduct(String name, String brand);

	private void validate(String name, String brand) {
		if (StringUtils.hasText(name) && StringUtils.hasText(brand)) {
			throw new IllegalArgumentException("Write product name and brand");
		}
	}
}

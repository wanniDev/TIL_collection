package me.designpattern.code.creational.abstractfactory.carfactory;

public class NormalCarFactory extends DefaultCarFactory {

	private final CarPartsFactory carPartsFactory;

	public NormalCarFactory(CarPartsFactory carPartsFactory) {
		this.carPartsFactory = carPartsFactory;
	}

	@Override
	Car createProduct() {
		Car car = new NormalCar();
		car.setEngine(carPartsFactory.createEngine());
		car.setWheel(carPartsFactory.createWheel());
		return car;
	}
}

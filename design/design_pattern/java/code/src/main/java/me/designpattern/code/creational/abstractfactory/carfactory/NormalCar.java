package me.designpattern.code.creational.abstractfactory.carfactory;

public class NormalCar implements Car {
	private Engine engine;
	private Wheel wheel;

	@Override
	public void setEngine(Engine engine) {
		this.engine = engine;
	}

	@Override
	public void setWheel(Wheel wheel) {
		this.wheel = wheel;
	}
}

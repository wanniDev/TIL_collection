package me.designpattern.code.abstractfactory.carfactory;

public class NormalCarPartsFactory implements CarPartsFactory {
	@Override
	public Engine createEngine() {
		return new NormalEngine();
	}

	@Override
	public Wheel createWheel() {
		return new NormalWheel();
	}
}

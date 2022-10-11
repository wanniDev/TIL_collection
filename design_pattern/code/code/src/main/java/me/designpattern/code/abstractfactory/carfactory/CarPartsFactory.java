package me.designpattern.code.abstractfactory.carfactory;

public interface CarPartsFactory {
	Engine createEngine();
	Wheel createWheel();
}

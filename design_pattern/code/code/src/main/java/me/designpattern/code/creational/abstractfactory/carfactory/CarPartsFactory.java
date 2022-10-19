package me.designpattern.code.creational.abstractfactory.carfactory;

public interface CarPartsFactory {
	Engine createEngine();
	Wheel createWheel();
}

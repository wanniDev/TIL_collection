package me.designpattern.code.behavior.visitor;

public class Square implements Shape {
    @Override
    public void accept(Device device) {
        device.print(this);
    }
}

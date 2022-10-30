package me.designpattern.code.behavior.visitor;

public class Phone implements Device {
    @Override
    public void print(Circle circle) {
        System.out.println("circle phone");
    }

    @Override
    public void print(Rectangle rectangle) {
        System.out.println("rectangle phone");
    }

    @Override
    public void print(Triangle triangle) {
        System.out.println("triangle phone");
    }

    @Override
    public void print(Square square) {
        System.out.println("square phone");
    }
}

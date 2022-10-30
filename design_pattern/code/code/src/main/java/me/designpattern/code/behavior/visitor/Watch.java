package me.designpattern.code.behavior.visitor;

public class Watch implements Device {
    @Override
    public void print(Circle circle) {
        System.out.println("circle watch");
    }

    @Override
    public void print(Rectangle rectangle) {
        System.out.println("rectangle watch");
    }

    @Override
    public void print(Triangle triangle) {
        System.out.println("triangle watch");
    }

    @Override
    public void print(Square square) {
        System.out.println("square watch");
    }
}

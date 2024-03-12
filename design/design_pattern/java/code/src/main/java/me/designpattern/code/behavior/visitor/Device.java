package me.designpattern.code.behavior.visitor;

public interface Device {
    void print(Circle circle);

    void print(Rectangle rectangle);

    void print(Triangle triangle);

    void print(Square square);
}

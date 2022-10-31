package me.designpattern.code.behavior.strategy;

public class Normal implements Speed {
    @Override
    public void blueLight() {
        System.out.println("Normal blue...");
    }

    @Override
    public void redLight() {
        System.out.println("Normal red...");
    }
}

package me.designpattern.code.behavior.strategy;

public class Fast implements Speed {
    @Override
    public void blueLight() {
        System.out.println("Fast Blue...");
    }

    @Override
    public void redLight() {
        System.out.println("Fast Red...");
    }
}

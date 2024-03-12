package me.designpattern.code.behavior.strategy;

public class Client {
    public static void main(String[] args) {
        BlueLightRedLight normalLight = new BlueLightRedLight();
        normalLight.redLight(new Normal());
        normalLight.blueLight(new Fast());

    }
}

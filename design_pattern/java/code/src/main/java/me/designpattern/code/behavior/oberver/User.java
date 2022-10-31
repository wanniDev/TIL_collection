package me.designpattern.code.behavior.oberver;

public class User implements Subscriber {

    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void handleMessage(String message) {
        System.out.println(message);
    }
}

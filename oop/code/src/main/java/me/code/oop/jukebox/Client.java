package me.code.oop.jukebox;

public class Client {
  public void init() {
    DIFactory.initFactory();
    start();
  }

  private void start() {
    DIFactory.jukeBox().play();
  }
}

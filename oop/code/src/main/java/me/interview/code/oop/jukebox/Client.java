package me.interview.code.oop.jukebox;

public class Client {
  public void init() {
    DIFactory.initFactory();
  }

  public void start() {
    DIFactory.jukeBox().play();
  }
}

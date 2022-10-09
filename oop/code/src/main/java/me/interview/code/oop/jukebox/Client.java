package me.interview.code.oop.jukebox;

public class Client {
  public void init() {

    final JukeBox jukeBox = DIFactory.jukeBox();
    final Player<?> player = DIFactory.player();

    jukeBox.play();
  }
}

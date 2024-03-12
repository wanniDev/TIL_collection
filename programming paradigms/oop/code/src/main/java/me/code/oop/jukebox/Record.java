package me.code.oop.jukebox;

import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Record implements Playable {
  private final Queue<Song> songs;

  public Record() {
    this.songs = new LinkedBlockingQueue<Song>();
  }

  public void play() {

  }
}

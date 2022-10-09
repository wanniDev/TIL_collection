package me.interview.code.oop.jukebox;

import java.util.Collection;

public class Record {
  private final Collection<Song> songs;

  public Record(Collection<Song> songs) {
    this.songs = songs;
  }
}

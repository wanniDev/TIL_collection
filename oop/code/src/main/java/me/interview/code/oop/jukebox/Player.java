package me.interview.code.oop.jukebox;

public interface Player<T> {
  void next();
  void prev();
  void add(T t);
  void remove(T t);
}

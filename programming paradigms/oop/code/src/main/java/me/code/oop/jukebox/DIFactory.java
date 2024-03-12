package me.code.oop.jukebox;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import sun.reflect.ReflectionFactory;

public final class DIFactory {

  private static final Map<Class<?>, Object> diMap = new ConcurrentHashMap<>();
  private DIFactory() {
    initFactory();
  }

  private static class LazyHolder {
    private static final DIFactory INSTANCE = new DIFactory();
  }

  public static DIFactory getInstance() {
    return LazyHolder.INSTANCE;
  }

  public static void initFactory() {
    getInstance();
    diMap.putIfAbsent(JukeBox.class, new RecordJukeBox());
    diMap.putIfAbsent(Player.class, new RecordPlayer());
    diMap.putIfAbsent(Playable.class, new Record());
  }

  public static JukeBox jukeBox() {
    return (JukeBox) diMap.get(JukeBox.class);
  }

  public static Player<?> player() {
    return (Player<?>) diMap.get(Player.class);
  }

  private static Playable playable() {
    return (Playable) diMap.get(Playable.class);
  }
}

package me.designpattern.code.behavior.oberver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatServer {
    private final Map<String, List<Subscriber>> subscribers = new HashMap<>();

    public void register(String subject, Subscriber subscriber) {
        if (this.subscribers.containsKey(subject))
            subscribers.get(subject).add(subscriber);
        else {
            List<Subscriber> list = new ArrayList<>();
            list.add(subscriber);
            subscribers.put(subject, list);
        }
    }

    public void unregister(String subject, Subscriber subscriber) {
        if (this.subscribers.containsKey(subject))
            this.subscribers.get(subject).remove(subscriber);
    }

    public void sendMessage(User user, String subject, String message) {
        if (this.subscribers.containsKey(subject))
            this.subscribers.get(subject).forEach(s -> s.handleMessage(user.getName() + ": " + message));
    }
}

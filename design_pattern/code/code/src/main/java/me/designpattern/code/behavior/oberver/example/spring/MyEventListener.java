package me.designpattern.code.behavior.oberver.example.spring;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MyEventListener implements ApplicationListener<MyEvent> {

    @Override
    public void onApplicationEvent(MyEvent event) {
        System.out.println(event.getSource());
        System.out.println(event.getMessage());
    }
}

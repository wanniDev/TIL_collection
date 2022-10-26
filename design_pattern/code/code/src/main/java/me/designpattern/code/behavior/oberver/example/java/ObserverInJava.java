package me.designpattern.code.behavior.oberver.example.java;

import java.util.Observable;
import java.util.Observer;

public class ObserverInJava {
    static class User implements Observer {
        @Override
        public void update(Observable o, Object arg) {
            System.out.println(arg);
        }
    }

    /**
     * setChanged 를 통해 변경을 하지 않으면, 알림이 전송이 안된다.
     * 그런데, 멀티 스레드 환경에서는 변경감지를 순서대로 감지하고 조작하기는 힘들기 때문에,
     * Observer, Observable 은 사용성이 떨어지고, 유지보수 효율도 떨어져서, Deprecated 됨.
     * java.beans 의 PropertyChangeListener 와 PropertyChangeSupport 를 활용 하는걸 추천한다.
     */
    static class Subject extends Observable {
        public void add(String message) {
            setChanged();
            notifyObservers(message);
        }
    }
}

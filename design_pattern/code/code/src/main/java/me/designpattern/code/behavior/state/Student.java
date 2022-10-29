package me.designpattern.code.behavior.state;

import java.util.HashSet;
import java.util.Set;

public class Student {
    private String name;

    private final Set<OnlineCourse> onlineCourses = new HashSet<>();

    public Student(String name) {
        this.name = name;
    }

    public boolean isAvailable(OnlineCourse onlineCourse) {
        return this.onlineCourses.contains(onlineCourse);
    }

    public void addPrivate(OnlineCourse onlineCourse) {
        this.onlineCourses.add(onlineCourse);
    }
}

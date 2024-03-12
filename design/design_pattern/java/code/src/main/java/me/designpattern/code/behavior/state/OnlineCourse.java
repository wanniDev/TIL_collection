package me.designpattern.code.behavior.state;

import java.util.List;

public class OnlineCourse {
    private State state;

    private List<Student> students;

    private List<String> reviews;

    public void addStudent(Student student) {
        this.state.addStudent(student);
    }

    public void addReview(String review, Student student) {
        this.state.addReview(review, student);
    }

    public List<Student> getStudents() {
        return students;
    }

    public State getState() {
        return state;
    }

    public List<String> getReviews() {
        return reviews;
    }

    public void changeState(State state) {
        this.state = state;
    }
}

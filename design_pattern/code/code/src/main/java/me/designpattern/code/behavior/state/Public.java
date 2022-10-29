package me.designpattern.code.behavior.state;

public class Public implements State {
    private final OnlineCourse onlineCourse;

    public Public(OnlineCourse onlineCourse) {
        this.onlineCourse = onlineCourse;
    }

    @Override
    public void addReview(String review, Student student) {
        this.onlineCourse.getReviews().add(review);
    }

    @Override
    public void addStudent(Student student) {
        this.onlineCourse.getStudents().add(student);
        if (this.onlineCourse.getStudents().size() > 1)
            this.onlineCourse.changeState(new Private(this.onlineCourse));
    }
}

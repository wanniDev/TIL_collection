package me.designpattern.code.behavior.state;

public class Private implements State {

    private final OnlineCourse onlineCourse;

    public Private(OnlineCourse onlineCourse) {
        this.onlineCourse = onlineCourse;
    }

    @Override
    public void addReview(String review, Student student) {
        if (isContains(student))
            this.onlineCourse.getReviews().add(review);
        else
            throw new UnsupportedOperationException("프라이빗이라 안됨");
    }

    private boolean isContains(Student student) {
        return this.onlineCourse.getStudents().contains(student);
    }

    @Override
    public void addStudent(Student student) {
        if (student.isAvailable(this.onlineCourse)) {
            this.onlineCourse.addStudent(student);
        } else {
            throw new UnsupportedOperationException("프라이빗 코스 수강불가");
        }
    }
}

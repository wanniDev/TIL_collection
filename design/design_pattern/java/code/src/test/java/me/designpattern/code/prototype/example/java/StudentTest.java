package me.designpattern.code.prototype.example.java;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import me.designpattern.code.creational.prototype.example.java.Student;

class StudentTest {
	@Test
	@DisplayName("Array")
	void collectionPrototype() {
		Student student = new Student("name1");
		Student student1 = new Student("name2");
		List<Student> students = new ArrayList<>();
		students.add(student);
		students.add(student1);

		List<Student> list = new ArrayList<>(students);
	}
}
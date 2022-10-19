package me.designpattern.code.creational.prototype.example.java;

import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		Student student = new Student("name1");
		Student student1 = new Student("name2");
		List<Student> students = new ArrayList<>();
		students.add(student);
		students.add(student1);

		List<Student> list = new ArrayList<>(students);
	}
}

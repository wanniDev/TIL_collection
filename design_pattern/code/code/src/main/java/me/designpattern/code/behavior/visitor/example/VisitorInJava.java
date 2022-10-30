package me.designpattern.code.behavior.visitor.example;

import java.io.IOException;
import java.nio.file.Path;

public class VisitorInJava {
    public static void main(String[] args) throws IOException {
        Path startingDirectory = Path.of("/Users/choehyeongjung");
        SearchFileVisitor searchFileVisitor = new SearchFileVisitor("Triangle.java", startingDirectory);
    }
}

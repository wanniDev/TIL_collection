package me.designpattern.code.behavior.command.example;

import java.time.LocalDateTime;
import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import me.designpattern.code.behavior.command.Command;

public class CommandInSpring {
	private final DataSource dataSource;

	public CommandInSpring(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void add(Command command) {
		SimpleJdbcInsert insert = new SimpleJdbcInsert(dataSource)
			.withTableName("command")
			.usingGeneratedKeyColumns("id");

		HashMap<String, Object> data = new HashMap<>();
		data.put("name", command.getClass().getSimpleName());
		data.put("when", LocalDateTime.now());
		insert.execute(data);
	}
}

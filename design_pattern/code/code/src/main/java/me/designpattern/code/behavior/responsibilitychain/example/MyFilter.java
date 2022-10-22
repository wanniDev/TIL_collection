package me.designpattern.code.behavior.responsibilitychain.example;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MyFilter implements Filter {
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws
		IOException,
		ServletException {
		System.out.println("필터를 처리하기 전에");
		chain.doFilter(servletRequest, servletResponse);
		System.out.println("필터를처리하고 돌아오는 와중에...");
	}
}

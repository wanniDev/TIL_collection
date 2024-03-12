package me.designpattern.code.behavior.responsibilitychain.example;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CoRInJava {
	public static void main(String[] args) {
		Filter filter = new Filter() {
			@Override
			public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
				FilterChain chain) throws IOException, ServletException {
				// TODO 전처리
				chain.doFilter(servletRequest, servletResponse); // 다음 필터로 전달
				// TODO 후처리 (필터를 다 거치고 다시 응답으로 돌아올때)
			}
		};
	}
}

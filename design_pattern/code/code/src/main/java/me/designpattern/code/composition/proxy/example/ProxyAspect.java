package me.designpattern.code.composition.proxy.example;

import org.springframework.stereotype.Component;

@Aspect
@Component
public class ProxyAspect {
	@Around("bean(gameService)")
	public void timestamp(ProceedingJoinPoint point) throws Throwable {
		long before = System.currentTimeMillis();
		point.proceed();
		System.out.println(System.currentTimeMillis() - before);
	}
}

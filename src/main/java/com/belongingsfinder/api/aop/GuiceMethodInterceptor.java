package com.belongingsfinder.api.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * @author Finbarr
 * 
 */
public class GuiceMethodInterceptor implements MethodInterceptor {

	@Inject
	static Injector injector;

	private final Class<? extends MethodInterceptor> delegate;

	public GuiceMethodInterceptor(Class<? extends MethodInterceptor> delegate) {
		this.delegate = delegate;
	}

	public Object invoke(MethodInvocation invocation) throws Throwable {
		return GuiceMethodInterceptor.injector.getInstance(delegate).invoke(invocation);
	}

}

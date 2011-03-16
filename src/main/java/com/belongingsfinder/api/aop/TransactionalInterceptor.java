package com.belongingsfinder.api.aop;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Finbarr
 * 
 */
@Singleton
public class TransactionalInterceptor implements MethodInterceptor {

	private final ThreadLocal<EntityManager> local;

	@Inject
	public TransactionalInterceptor(ThreadLocal<EntityManager> local) {
		this.local = local;
	}

	public Object invoke(MethodInvocation invocation) throws Throwable {
		// see EntityManagerThreadLocal - uses a lazy Provider of EntityManager
		EntityManager entityManager = local.get();
		// obtain a transaction
		EntityTransaction t = entityManager.getTransaction();
		Object result;
		try {
			// start transaction
			t.begin();
			// allow method invocation to proceed
			result = invocation.proceed();
		} finally {
			// close the transaction via rollback or commit
			if (t.isActive()) {
				if (t.getRollbackOnly()) {
					t.rollback();
				} else {
					t.commit();
				}
			}
			// remove the entitymanager
			// entityManager.close();
			local.remove();
		}
		return result;
	}

}

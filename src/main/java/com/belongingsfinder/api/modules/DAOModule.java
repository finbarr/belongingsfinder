package com.belongingsfinder.api.modules;

import java.lang.annotation.Annotation;

import javax.persistence.EntityManager;

import com.belongingsfinder.api.annotations.Transactional;
import com.belongingsfinder.api.annotations.Unwrapped;
import com.belongingsfinder.api.aop.GuiceMethodInterceptor;
import com.belongingsfinder.api.aop.TransactionalInterceptor;
import com.belongingsfinder.api.dao.EventWrappedModelDAO;
import com.belongingsfinder.api.dao.ModelDAO;
import com.belongingsfinder.api.dao.jpa.EntityManagerProvider;
import com.belongingsfinder.api.dao.jpa.EntityManagerThreadLocal;
import com.belongingsfinder.api.dao.jpa.JPAModelDAO;
import com.belongingsfinder.api.model.BelongingModel;
import com.belongingsfinder.api.model.CategoryModel;
import com.belongingsfinder.api.model.Model;
import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.PrivateModule;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matchers;
import com.google.inject.util.Types;

/**
 * @author finbarr
 * 
 */
public class DAOModule extends AbstractModule {

	@SuppressWarnings("unchecked")
	public static <T extends Model<T>, A extends Annotation> Key<ModelDAO<T>> annotatedModelDAO(Class<T> t, Class<A> a) {
		return (Key<ModelDAO<T>>) Key.get(Types.newParameterizedType(ModelDAO.class, t), a);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Model<T>> Key<ModelDAO<T>> modelDAO(Class<T> t) {
		return (Key<ModelDAO<T>>) Key.get(Types.newParameterizedType(ModelDAO.class, t));
	}

	@SuppressWarnings("unchecked")
	public static <T extends Model<T>, S extends ModelDAO<T>> Key<ModelDAO<T>> specificModelDAO(Class<T> t, Class<S> s) {
		return (Key<ModelDAO<T>>) Key.get(Types.newParameterizedType(s, t));
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void configure() {
		bindModelDAOs(BelongingModel.class, JPAModelDAO.class);
		bindModelDAOs(CategoryModel.class, JPAModelDAO.class);
		bind(new TypeLiteral<ThreadLocal<EntityManager>>() {
		}).to(EntityManagerThreadLocal.class);
		bind(EntityManager.class).toProvider(EntityManagerProvider.class);
		requestStaticInjection(GuiceMethodInterceptor.class);
		bindInterceptor(Matchers.any(), Matchers.annotatedWith(Transactional.class), new GuiceMethodInterceptor(
				TransactionalInterceptor.class));
	}

	@SuppressWarnings("unchecked")
	private <T extends Model<T>, M extends ModelDAO<T>> void bindModelDAOs(final Class<T> t, final Class<M> m) {
		install(new PrivateModule() {
			@Override
			protected void configure() {
				bind((Key<Class<T>>) Key.get(Types.newParameterizedType(Class.class, t))).toInstance(t);
				bind(DAOModule.annotatedModelDAO(t, Unwrapped.class)).to(DAOModule.specificModelDAO(t, m)).in(
						Singleton.class);
				expose(DAOModule.annotatedModelDAO(t, Unwrapped.class));
				bind(DAOModule.modelDAO(t)).to(DAOModule.specificModelDAO(t, EventWrappedModelDAO.class)).in(
						Singleton.class);
				expose(DAOModule.modelDAO(t));
			}
		});
	}

}

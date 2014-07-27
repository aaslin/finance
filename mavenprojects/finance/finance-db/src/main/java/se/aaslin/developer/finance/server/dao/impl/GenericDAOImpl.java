package se.aaslin.developer.finance.server.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import se.aaslin.developer.finance.server.dao.GenericDAO;

public abstract class GenericDAOImpl<PK, E> implements GenericDAO<PK, E>{
	
	private final Class<E> type;
	
	@SuppressWarnings("unchecked")
	public GenericDAOImpl() {
		type = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}

	@Override
	public void create(E e) {
		getEntityManager().persist(e);
	}

	@Override
	public void delete(E e) {
		getEntityManager().remove(e);
	}

	@Override
	public E findById(PK id) {
		return getEntityManager().find(type, id);
	}
	
	@Override
	public List<E> list() {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<E> query = cb.createQuery(type);
		
		Root<E> entity = query.from(type);
		query.select(entity);
		
		return getEntityManager().createQuery(query).getResultList();
	}
	
	@Override
	public E merge(E e) {
		return getEntityManager().merge(e);
	}

	protected abstract EntityManager getEntityManager();
	
	protected E getSingelOrNull(CriteriaQuery<E> query) {
		try {
			return getEntityManager().createQuery(query).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}

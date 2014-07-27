package se.aaslin.developer.finance.server.dao;

import java.util.List;

public interface GenericDAO <PK, E> {
	
	void create(E e);
	
	void delete(E e);
	
	E findById(PK id);
	
	E merge(E e);
	
	List<E> list();
}

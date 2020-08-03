package com.abirami.dao;

import java.util.List;

public interface GenericDao {

	<T> List<T> getAll(final Class<T> clazz);
	
	<T> T get(final Class<T> type, final int id);
	
	<T> int save(final T object);
	
	//<T> void saveOrUpdate(final T object);
}

package com.abirami.dao;

import java.util.List;

public interface GenericDao {

	<T> List<T> getAll(final Class<T> clazz);
	
	<T> T get(final Class<T> clazz, final int id);
	
	<T> int save(final T object);
	
	<T> List<T> getListOfIds(final Class<T> clazz, final List<Integer> ids);
	
	<T> List<T> getAllByForeignKey(final Class<T> clazz, final String foreignKeyQuery, final int id);
	
	<T, S> List<T> getAllByInRange(final Class<T> clazz, final String keyQuery, final S min, final S max);
	
	//<T> void saveOrUpdate(final T object);
}

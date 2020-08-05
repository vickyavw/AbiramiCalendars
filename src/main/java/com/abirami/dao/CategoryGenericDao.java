package com.abirami.dao;

import java.util.List;

import com.abirami.model.Category;

public interface CategoryGenericDao {

	List<Category> getAll();
	
	Category get(final int id);
	
	int save(final Category category);
	
	List<Category> getListOfIds(final List<Integer> ids);
	/*
	<T> List<Category> getAllByQueryParam(final String queryParam, final T value);
	
	//Making it generic to support any value in range
	<T> List<Category> getAllByInRange(final String keyQuery, final T min, final T max);
	
	<Category> void saveOrUpdate(final Category object);*/
}

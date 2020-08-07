package com.abirami.dao.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Session;

import com.abirami.dao.CategoryGenericDao;
import com.abirami.dao.HibernateConfig;
import com.abirami.model.Category;
import com.abirami.model.CategoryDTO;
import com.abirami.model.PaginatedCategoriesApiResponse;

public class CategoryGenericDaoImpl implements CategoryGenericDao {

	@Override
	public PaginatedCategoriesApiResponse getAll() {
		Session session = null;
		List<Category> categories = null;
		PaginatedCategoriesApiResponse resp = new PaginatedCategoriesApiResponse();
		try {
			session = HibernateConfig.getSessionFactory().openSession();
			session.beginTransaction();
			categories = session.createQuery("from Category").list();
			resp.setCategories(categories.stream().map(e -> new CategoryDTO(e)).collect(Collectors.toList()));
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if(null != session)
				session.close();
		}
		return resp;
	}

	@Override
	public Category get(int id) {
		Session session = null;
		Category category = null;
		try {
			session = HibernateConfig.getSessionFactory().openSession();
			session.beginTransaction();
			category = session.get(Category.class,id);
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if(null != session)
				session.close();
		}
		return category;
	}

	@Override
	public int save(Category category) {
		Session session = null;
		int categoryId = 0;
		try {
			session = HibernateConfig.getSessionFactory().openSession();
			session.beginTransaction();
			categoryId = (int) session.save(category);
			session.getTransaction().commit();
		}
		catch(Exception e) {
			session.getTransaction().rollback();
			throw e;
		}
		finally {
			if(null != session)
				session.close();
		}
		return categoryId;
	}

	@Override
	public List<Category> getListOfIds(List<Integer> ids) {
		Session session = null;
		List<Category> category = null;
		try {
			session = HibernateConfig.getSessionFactory().openSession();
			session.beginTransaction();
			category = session.byMultipleIds(Category.class).multiLoad(ids);
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if(null != session)
				session.close();
		}
		return category;
	}

}

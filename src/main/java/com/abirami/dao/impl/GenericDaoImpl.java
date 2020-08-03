package com.abirami.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.abirami.dao.GenericDao;
import com.abirami.dao.HibernateConfig;

public class GenericDaoImpl implements GenericDao {
	
	@Override
	public <T> List<T> getAll(final Class<T> clazz) {
		Session session = null;
		List<T> genericItems = null;
		try {
			session = HibernateConfig.getSessionFactory().openSession();
			session.beginTransaction();
			genericItems = session.createQuery("from " + clazz.getName()).list();
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if(null != session)
				session.close();
		}
		return genericItems;
	}

	@Override
	public <T> T get(final Class<T> type, final int id) {
		Session session = null;
		T genericItem = null;
		try {
			session = HibernateConfig.getSessionFactory().openSession();
			session.beginTransaction();
			genericItem = session.get(type,id);
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if(null != session)
				session.close();
		}
		return genericItem;
	}

	@Override
	public <T> int save(final T object) {
		Session session = null;
		int genericItemId = 0;
		try {
			session = HibernateConfig.getSessionFactory().openSession();
			session.beginTransaction();
			genericItemId = (int) session.save(object);
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
		return genericItemId;
	}

}

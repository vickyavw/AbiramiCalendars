package com.abirami.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.abirami.dao.FormatGenericDao;
import com.abirami.dao.HibernateConfig;
import com.abirami.model.Format;
import com.abirami.model.FormatDTO;

public class FormatGenericDaoImpl implements FormatGenericDao {

	@Override
	public List<FormatDTO> getAll() {
		Session session = null;
		List<Format> response = new ArrayList<Format>();
		try {
			session = HibernateConfig.getSessionFactory().openSession();
			session.beginTransaction();
			
			response = session.createQuery("from Format").list();
			
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if(null != session)
				session.close();
			else {
            	throw new HibernateException("Not able to establish DB connection");
            }
		}
		return response.stream().map(e -> new FormatDTO(e)).collect(Collectors.toList());
	}

	@Override
	public Format get(int id) {
		Session session = null;
		Format response = new Format();
		try {
			session = HibernateConfig.getSessionFactory().openSession();
			session.beginTransaction();
			
			response = session.get(Format.class,id);
			
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if(null != session)
				session.close();
			else {
            	throw new HibernateException("Not able to establish DB connection");
            }
		}
		return response;
	}

}

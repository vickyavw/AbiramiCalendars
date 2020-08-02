package com.abirami.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.abirami.dao.HibernateConfig;
import com.abirami.dao.ProductDao;
import com.abirami.model.Product;

public class ProductDaoImpl implements ProductDao {
	
	@Override
	public List<Product> getProducts() {
		Session session = null;
		List<Product> products = null;
		try {
			session = HibernateConfig.getSessionFactory().openSession();
			session.beginTransaction();
			products = session.createQuery("from Product", Product.class).list();
			session.getTransaction().commit();
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			session.close();
		}
		return products;
	}

	@Override
	public Product getProduct(int productId) {
		Session session = null;
		Product product = null;
		try {
			session = HibernateConfig.getSessionFactory().openSession();
			session.beginTransaction();
			product = session.get(Product.class,productId);
			session.getTransaction().commit();
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			session.close();
		}
		return product;
	}

	@Override
	public int addProduct(Product product) {
		Session session = null;
		int productId = 0;
		try {
			session = HibernateConfig.getSessionFactory().openSession();
			session.beginTransaction();
			productId = (int) session.save(product);
			session.getTransaction().commit();
		}
		catch(Exception e) {
			session.getTransaction().rollback();
			throw e;
		}
		finally {
			session.close();
		}
		return productId;
	}

}

package com.abirami.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.abirami.dao.HibernateConfig;
import com.abirami.dao.ProductGenericDao;
import com.abirami.model.Product;

public class ProductGenericDaoImpl implements ProductGenericDao {
	
	@Override
	public List<Product> getAll() {
		Session session = null;
		List<Product> products = null;
		try {
			session = HibernateConfig.getSessionFactory().openSession();
			session.beginTransaction();
			products = session.createQuery("from Product order by displayName asc").list();
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if(null != session)
				session.close();
		}
		return products;
	}

	@Override
	public Product get(int id) {
		Session session = null;
		Product product = null;
		try {
			session = HibernateConfig.getSessionFactory().openSession();
			session.beginTransaction();
			product = session.get(Product.class,id);
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if(null != session)
				session.close();
		}
		return product;
	}

	@Override
	public int save(Product product) {
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
			if(null != session)
				session.close();
		}
		return productId;
	}

	@Override
	public List<Product> getListOfIds(List<Integer> ids) {
		Session session = null;
		List<Product> products = null;
		try {
			session = HibernateConfig.getSessionFactory().openSession();
			session.beginTransaction();
			products = session.byMultipleIds(Product.class).multiLoad(ids);
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if(null != session)
				session.close();
		}
		return products;
	}

	@Override
	public <T> List<Product> getAllByQueryParams(List<String> queryParam, List<T> value) {
		Session session = null;
		List<Product> products = null;
		try {
			Conjunction conjunction = Restrictions.conjunction();
			for(int i=0;i<queryParam.size();i++) {
				conjunction.add(Restrictions.eq(queryParam.get(i), value.get(i)));
			}
			session = HibernateConfig.getSessionFactory().openSession();
			session.beginTransaction();
			products = session.createCriteria(Product.class)
								.add(conjunction)
								.addOrder(Order.asc("displayName"))
								.list();
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if(null != session)
				session.close();
		}
		return products;
	}

	@Override
	public <T> List<Product> getAllInRange(String productType, String keyQuery, T min, T max) {
		Session session = null;
		List<Product> products = null;
		try {
			Conjunction conjunction = Restrictions.conjunction();
			conjunction.add(Restrictions.between(keyQuery, min, max));
			if(StringUtils.isNotBlank(productType))
				conjunction.add(Restrictions.eq("productType", productType));
			
			session = HibernateConfig.getSessionFactory().openSession();
			session.beginTransaction();
			products = session.createCriteria(Product.class)
								.add(conjunction)
								.addOrder(Order.asc("displayName"))
								.list();
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if(null != session)
				session.close();
		}
		return products;
	}

	@Override
	public List<Product> getRelatedProducts(Product product, int expectedCount) {
		Session session = null;
		List<Product> products = null;
		try {
			Conjunction conjunction = Restrictions.conjunction();
			conjunction.add(Restrictions.ne("productId", product.getProductId()));
			if(StringUtils.isNotBlank(product.getProductType()))
				conjunction.add(Restrictions.eq("productType", product.getProductType()));
			if(null != product.getCategory() && null != product.getCategory().getCategoryId())
				conjunction.add(Restrictions.eq("category.categoryId", product.getCategory().getCategoryId()));
			
			session = HibernateConfig.getSessionFactory().openSession();
			session.beginTransaction();
			products = session.createCriteria(Product.class)
								.add(conjunction)
								.addOrder(Order.desc("productId"))
								.setMaxResults(expectedCount)
								.list();
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if(null != session)
				session.close();
		}
		return products;
	}

}

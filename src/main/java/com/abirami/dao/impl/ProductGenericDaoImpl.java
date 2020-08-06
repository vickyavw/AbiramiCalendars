package com.abirami.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.abirami.dao.HibernateConfig;
import com.abirami.dao.ProductGenericDao;
import com.abirami.model.Product;
import com.abirami.model.ProductsApiResponse;

public class ProductGenericDaoImpl implements ProductGenericDao {
	
	@Override
	public ProductsApiResponse getAll(String sortBy, String sortDirection, Integer pageSize, Integer pageNumber) {
		Session session = null;
		List<Product> products = null;
		ProductsApiResponse response = new ProductsApiResponse();
		try {
			session = HibernateConfig.getSessionFactory().openSession();
			session.beginTransaction();
			
			products = session.createQuery("from Product order by "+sortBy+" "+sortDirection)
									.setFirstResult((pageNumber-1) * pageSize)
									.setMaxResults(pageSize)
									.list();
			
			response.setResultSize(getTotalRecords(null));
			response.setPageNumber(pageNumber);
			response.setPageSize(pageSize);
			response.setProducts(products);
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if(null != session)
				session.close();
		}
		return response;
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
	public <T> ProductsApiResponse getAllByQueryParams(List<String> queryParam, List<T> value, String sortBy, String sortDirection, Integer pageSize, Integer pageNumber) {
		Session session = null;
		ProductsApiResponse response = new ProductsApiResponse();
		List<Product> products = null;
		String productType = null;
		try {
			Conjunction conjunction = Restrictions.conjunction();
			for(int i=0;i<queryParam.size();i++) {
				conjunction.add(Restrictions.eq(queryParam.get(i), value.get(i)));
				if(queryParam.get(i).equals("productType"))
					productType = (String) value.get(i);
			}
			session = HibernateConfig.getSessionFactory().openSession();
			session.beginTransaction();
			products = session.createCriteria(Product.class)
								.add(conjunction)
								.addOrder("desc".equals(sortDirection)?Order.desc(sortBy):Order.asc(sortBy))
								.setFirstResult((pageNumber-1) * pageSize)
								.setMaxResults(pageSize)
								.list();
			
			response.setResultSize(getTotalRecords(productType));
			response.setPageNumber(pageNumber);
			response.setPageSize(pageSize);
			response.setProducts(products);
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if(null != session)
				session.close();
		}
		return response;
	}

	@Override
	public <T> ProductsApiResponse getAllInRange(String productType, String keyQuery, T min, T max, String sortBy, String sortDirection, Integer pageSize, Integer pageNumber) {
		Session session = null;
		ProductsApiResponse response = new ProductsApiResponse();
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
								.addOrder("desc".equals(sortDirection)?Order.desc(sortBy):Order.asc(sortBy))
								.setFirstResult((pageNumber-1) * pageSize)
								.setMaxResults(pageSize)
								.list();
			
			response.setResultSize(getTotalRecords(productType));
			response.setPageNumber(pageNumber);
			response.setPageSize(pageSize);
			response.setProducts(products);
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if(null != session)
				session.close();
		}
		return response;
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
	
	private int getTotalRecords(String productType) {
		Integer rowCount = 0;
		Session session = null;
		try {
			session = HibernateConfig.getSessionFactory().openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Product.class);
			if(StringUtils.isNotBlank(productType))
				criteria.add(Restrictions.eq("productType", productType));
			criteria.setProjection(Projections.rowCount());
            
            List records = criteria.list();
            if (records!=null) {
                rowCount = ((Long) records.get(0)).intValue();
                System.out.println("Total Results:" + rowCount);
            }
             
            session.getTransaction().commit();
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if(null != session)
				session.close();
		}
		return rowCount;
	}

}

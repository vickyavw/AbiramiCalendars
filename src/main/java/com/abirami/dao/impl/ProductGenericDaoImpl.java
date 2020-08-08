package com.abirami.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.abirami.dao.HibernateConfig;
import com.abirami.dao.ProductGenericDao;
import com.abirami.model.PaginatedProductsApiResponse;
import com.abirami.model.Product;
import com.abirami.model.ProductDTO;

public class ProductGenericDaoImpl implements ProductGenericDao {
	
	@Override
	public PaginatedProductsApiResponse getAll(String sortBy, String sortDirection, Integer pageSize, Integer pageNumber) {
		Session session = null;
		List<Product> products = null;
		PaginatedProductsApiResponse response = new PaginatedProductsApiResponse();
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
			response.setProducts(products.stream().map(e -> new ProductDTO(e)).collect(Collectors.toList()));
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
	public ProductDTO get(int id) {
		Session session = null;
		Product product = null;
		try {
			session = HibernateConfig.getSessionFactory().openSession();
			session.beginTransaction();
			product = session.get(Product.class,id);
			product.setCategoryName(product.getCategory().getCategoryName());
			product.setFormatName(product.getFormat().getFormatName());
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if(null != session)
				session.close();
		}
		return new ProductDTO(product);
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
	public PaginatedProductsApiResponse getAllByQueryParams(String productType, 
			Map<String, Map<String, Object>> queryParamsMap, 
			String sortBy,  
			String sortDirection, 
			Integer pageSize, 
			Integer pageNumber) {

		Session session = null;
		PaginatedProductsApiResponse response = new PaginatedProductsApiResponse();
		List<Product> products = null;
		try {
			Conjunction conjunction = Restrictions.conjunction();
			if(StringUtils.isNotEmpty(productType)) {
				conjunction.add(Restrictions.eq("productType", productType));
			}
			for (Entry<String, Map<String, Object>> entry : queryParamsMap.entrySet()) {
				if("exactMatch".equals(entry.getKey())){
					//adding equal criteria
					for( Entry<String, Object> entryList :entry.getValue().entrySet()) {
						conjunction.add(Restrictions.eq(entryList.getKey(), entryList.getValue()));
					}
				}
				else if("partialMatch".equals(entry.getKey())){
					//adding equal criteria
					for( Entry<String, Object> entryList :entry.getValue().entrySet()) {
						conjunction.add(Restrictions.like(entryList.getKey(), entryList.getValue()));
					}
				}
				else if("range".equals(entry.getKey())){
					//adding equal criteria
					for( Entry<String, Object> entryList :entry.getValue().entrySet()) {
						List ranges = (List) entryList.getValue();
						conjunction.add(Restrictions.between(entryList.getKey(), ranges.get(0), ranges.get(1)));
					}
				}
				else if("in".equals(entry.getKey())){
					//adding equal criteria
					for( Entry<String, Object> entryList :entry.getValue().entrySet()) {
						conjunction.add(Restrictions.in(entryList.getKey(), entryList.getValue()));
					}
				}
				
			}
			session = HibernateConfig.getSessionFactory().openSession();
			session.beginTransaction();
			products = session.createCriteria(Product.class)
								.add(conjunction)
								.addOrder("desc".equals(sortDirection)?Order.desc(sortBy):Order.asc(sortBy))
								.setFirstResult((pageNumber-1) * pageSize)
								.setMaxResults(pageSize)
								.list();
			
			response.setResultSize(getTotalRecords(conjunction));
			response.setPageNumber(pageNumber);
			response.setPageSize(pageSize);
			response.setProducts(products.stream().map(e -> new ProductDTO(e)).collect(Collectors.toList()));
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
	public <T> PaginatedProductsApiResponse getAllInRange(String productType, String keyQuery, T min, T max, String sortBy, String sortDirection, Integer pageSize, Integer pageNumber) {
		Session session = null;
		PaginatedProductsApiResponse response = new PaginatedProductsApiResponse();
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
			
			response.setResultSize(getTotalRecords(conjunction));
			response.setPageNumber(pageNumber);
			response.setPageSize(pageSize);
			response.setProducts(products.stream().map(e -> new ProductDTO(e)).collect(Collectors.toList()));
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
	public List<ProductDTO> getRelatedProducts(ProductDTO product, int expectedCount) {
		Session session = null;
		List<Product> products = null;
		try {
			Conjunction conjunction = Restrictions.conjunction();
			conjunction.add(Restrictions.ne("productId", product.getProductId()));
			if(StringUtils.isNotBlank(product.getProductType()))
				conjunction.add(Restrictions.eq("productType", product.getProductType()));
			if(null != product.getFormatId())
				conjunction.add(Restrictions.eq("format.formatId", product.getFormatId()));
			if(null != product.getCategoryId())
				conjunction.add(Restrictions.eq("category.categoryId", product.getCategoryId()));
			
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
		return products.stream().map(e -> new ProductDTO(e)).collect(Collectors.toList());
	}
	
	private int getTotalRecords(Conjunction conjunction) {
		Integer rowCount = 0;
		Session session = null;
		try {
			session = HibernateConfig.getSessionFactory().openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Product.class);
			if(null != conjunction)
				criteria.add(conjunction);
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

package com.abirami.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.abirami.dao.HibernateConfig;
import com.abirami.dao.ItemDao;
import com.abirami.model.Item;

public class ItemDaoImpl implements ItemDao {
	
	@Override
	public List<Item> getItems() {
		Session session = null;
		List<Item> items = null;
		try {
			session = HibernateConfig.getSessionFactory().openSession();
			session.beginTransaction();
			items = session.createQuery("from Item", Item.class).list();
			session.getTransaction().commit();
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			session.close();
		}
		return items;
	}

	@Override
	public Item getItem(int itemId) {
		Session session = null;
		Item item = null;
		try {
			session = HibernateConfig.getSessionFactory().openSession();
			session.beginTransaction();
			item = session.get(Item.class,itemId);
			session.getTransaction().commit();
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			session.close();
		}
		return item;
	}

	@Override
	public int addItem(Item item) {
		Session session = null;
		int itemId = 0;
		try {
			session = HibernateConfig.getSessionFactory().openSession();
			session.beginTransaction();
			itemId = (int) session.save(item);
			session.getTransaction().commit();
		}
		catch(Exception e) {
			session.getTransaction().rollback();
			throw e;
		}
		finally {
			session.close();
		}
		return itemId;
	}

}

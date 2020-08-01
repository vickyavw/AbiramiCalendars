package com.abirami.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.abirami.dao.HibernateConfig;
import com.abirami.dao.ItemDao;
import com.abirami.model.Item;

public class ItemDaoImpl implements ItemDao {

	@Override
	public List<Item> getItems() {
		List<Item> items = null;
		Session session = HibernateConfig.getSessionFactory().openSession();
		session.beginTransaction();
		items = session.createQuery("from Item", Item.class).list();
		session.getTransaction().commit();
		session.close();
		return items;
	}

	@Override
	public Item getItem(int itemId) {
		Item item = null;
		Session session = HibernateConfig.getSessionFactory().openSession();
		session.beginTransaction();
		item = session.get(Item.class,itemId);
		session.getTransaction().commit();
		session.close();
		return item;
	}

	@Override
	public int addItem(Item item) {
		Session session = HibernateConfig.getSessionFactory().openSession();
		session.beginTransaction();
		int itemId = (int) session.save(item);
		session.getTransaction().commit();
		session.close();
		return itemId;
	}

}
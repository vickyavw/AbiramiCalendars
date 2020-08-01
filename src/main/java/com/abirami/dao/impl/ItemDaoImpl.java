package com.abirami.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.abirami.dao.HibernateConfig;
import com.abirami.dao.ItemDao;
import com.abirami.model.Item;

@Repository("itemDao")
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

}

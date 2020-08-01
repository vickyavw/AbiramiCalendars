package com.abirami.dao;

import java.util.List;

import com.abirami.model.Item;

public interface ItemDao {

	List<Item> getItems();
	
	Item getItem(int itemId);
}

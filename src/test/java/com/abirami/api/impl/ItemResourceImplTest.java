/**
 * 
 */
package com.abirami.api.impl;

import org.junit.Test;

import com.abirami.model.Item;

/**
 * @author vicky
 *
 */
public class ItemResourceImplTest {
	@Test
	public void test_addItem() {
		Item item = new Item();
		item.setDisplayName("Calendar2");
		item.setDisplayName("Test Calendar 2");
		new ItemResourceImpl().addItem(item);
	}
}

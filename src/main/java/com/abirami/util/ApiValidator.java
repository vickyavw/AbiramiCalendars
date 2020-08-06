package com.abirami.util;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;

import com.abirami.model.Product;

public class ApiValidator {

	public static boolean isValidSortAttr(String sortBy, String sortOrder) {
		
		if(StringUtils.isEmpty(sortOrder) || !(sortOrder.equalsIgnoreCase("desc") || sortOrder.equalsIgnoreCase("asc"))){
			return false;
		}
		for (Field field : Product.class.getDeclaredFields()) {
			if(field.getName().equals(sortBy))
				return true;
		}
		return false;
	}
}

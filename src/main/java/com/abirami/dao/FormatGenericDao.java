package com.abirami.dao;

import java.util.List;

import com.abirami.model.Format;
import com.abirami.model.FormatDTO;

public interface FormatGenericDao {

	List<FormatDTO> getAll();
	
	Format get(final int id);
	
}

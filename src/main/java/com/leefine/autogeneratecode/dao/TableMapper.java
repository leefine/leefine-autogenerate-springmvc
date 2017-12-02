package com.leefine.autogeneratecode.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TableMapper extends SuperMapper {
	public List<Map<String, Object>> getTable(@Param("tableName") String tableName);
}

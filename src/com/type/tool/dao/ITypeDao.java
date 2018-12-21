package com.type.tool.dao;

import java.sql.Connection;
import java.util.List;

import com.fy.sqlparam.param.ISqlParameter;
import com.fy.toolhelper.db.IBaseDao;
import com.type.tool.entity.Type;

public interface ITypeDao extends IBaseDao<Type> {

	public List<Type> getTypes(Connection connection, ISqlParameter parameter)
			throws Exception;

	public Long getCountByIDAndEquip(Connection connection, Long id)
			throws Exception;

	public Long getCountByIDAndMaint(Connection connection, Long id)
			throws Exception;

	public Long getCountByName(Connection connection, String name) throws Exception;
}

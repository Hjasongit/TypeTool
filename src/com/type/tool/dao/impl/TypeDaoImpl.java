package com.type.tool.dao.impl;

import java.sql.Connection;
import java.util.List;

import com.fy.sqlparam.map.ISqlMapResult;
import com.fy.sqlparam.map.config.FieldMapMeta;
import com.fy.sqlparam.map.config.MapMetaConfig;
import com.fy.sqlparam.param.ISqlParameter;
import com.fy.toolhelper.db.BaseDaoImpl;
import com.type.tool.dao.ITypeDao;
import com.type.tool.entity.Type;

public class TypeDaoImpl extends BaseDaoImpl<Type> implements ITypeDao {

	@Override
	public List<Type> getTypes(Connection connection, ISqlParameter parameter)
			throws Exception {
		String sql = "SELECT c.*" + PH_BASE_TABLES + PH_DYNAMIC_JOIN_TABLES
				+ "WHERE" + PH_CONDITIONS + "GROUP BY c.id" + PH_ORDER_BY
				+ PH_LIMIT;
		ISqlMapResult mapResult = this.formatSql(sql, parameter);
		return this.listEntitiesBySql(connection, mapResult.getSql(), "c",
				mapResult.getArgObjs());
	}
	
	@Override
	public Long getCountByName(Connection connection, String name)
			throws Exception {
		String sql = "SELECT COUNT(c.id) FROM catagory c WHERE c.catagory_name= ?";
		return this.getCountBySql(connection, sql, name);
	}

	@Override
	public Long getCountByIDAndEquip(Connection connection, Long id)
			throws Exception {
		/*
		 * String sql = "SELECT c.*" + PH_BASE_TABLES + PH_DYNAMIC_JOIN_TABLES +
		 * "WHERE" + PH_CONDITIONS + "GROUP BY c.id" + PH_ORDER_BY + PH_LIMIT;
		 */
		String sql = "SELECT COUNT(c.id) FROM catagory c, equipment e WHERE c.id = e.catagory_id AND c.id = ?";
		return this.getCountBySql(connection, sql, id);
	}

	@Override
	public Long getCountByIDAndMaint(Connection connection, Long id)
			throws Exception {
		/*
		 * String sql = "SELECT c.*" + PH_BASE_TABLES + PH_DYNAMIC_JOIN_TABLES +
		 * "WHERE" + PH_CONDITIONS + "GROUP BY c.id" + PH_ORDER_BY + PH_LIMIT;
		 */
		String sql = "SELECT COUNT(c.id) FROM catagory c, maintainer_catagory m WHERE c.id = m.catagory_id AND c.id = ?";
		return this.getCountBySql(connection, sql, id);
	}

	@MapMetaConfig(baseTables = "FROM catagory c", fields = {
			@FieldMapMeta(name = "id", value = "c.id"),
			@FieldMapMeta(name = "catagory_name", value = "c.catagory_name") }, joinTables = {

	})
	public TypeDaoImpl() throws Exception {
		super();
	}
}

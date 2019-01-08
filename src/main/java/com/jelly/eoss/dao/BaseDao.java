package com.jelly.eoss.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class BaseDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public BaseDao(){
		
	}
	
	/******************************** Start MyBatis ********************************/
	public int myInsert(String statement){
		return this.sqlSessionTemplate.insert(statement);
	}
	
	public int myInsert(String statement, Object param){
		return this.sqlSessionTemplate.insert(statement, param);
	}
	
	public int myUpdate(String statement){
		return this.sqlSessionTemplate.update(statement);
	}
	
	public int myUpdate(String statement, Object param){
		return this.sqlSessionTemplate.update(statement, param);
	}
	
	public int myDelete(String statement){
		return this.sqlSessionTemplate.delete(statement);
	}
	
	public int myDelete(String statement, Object param){
		return this.sqlSessionTemplate.delete(statement, param);
	}
	
	public <E> List<E> mySelectList(String statement){
		return this.sqlSessionTemplate.selectList(statement);
	}
	
	public <E> List<E> mySelectList(String statement, Object param){
		return this.sqlSessionTemplate.selectList(statement, param);
	}

	public <T> T mySelectOne(String statement){
		return this.sqlSessionTemplate.selectOne(statement);
	}
	
	public <T> T mySelectOne(String statement, Object param){
		return this.sqlSessionTemplate.selectOne(statement, param);
	}
	
	/******************************** Start JdbcTemplate ********************************/
	public void jdExecute(String sql){
		this.jdbcTemplate.execute(sql);
	}
	
	/*
	 * 数据库的批量操作请使用此方法，以保证高性能
	 */
	public int[] jdBatchUpdate(String sql, List<Object[]> batchParams){
		return this.jdbcTemplate.batchUpdate(sql, batchParams);
	}
	
	public int jdInsert(String sql){
		return this.jdbcTemplate.update(sql);
	}
	
	public int jdInsert(String sql, Object... params){
		return this.jdbcTemplate.update(sql, params);
	}
	
	public int jdUpdate(String sql){
		return this.jdbcTemplate.update(sql);
	}
	
	public int jdUpdate(String sql, Object... params){
		return this.jdbcTemplate.update(sql, params);
	}
	
	public int jdDelete(String sql){
		return this.jdbcTemplate.update(sql);
	}
	
	public int jdDelete(String sql, Object... params){
		return this.jdbcTemplate.update(sql, params);
	}
	
	public int jdQueryForInt(String sql){
		return this.jdbcTemplate.queryForObject(sql, Integer.class);
	}
	
	public int jdQueryForInt(String sql, Object... params){
		return this.jdbcTemplate.queryForObject(sql, Integer.class, params);
	}
	
	public long jdQueryForLong(String sql){
		return this.jdbcTemplate.queryForObject(sql, Long.class);
	}
	
	public long jdQueryForLong(String sql, Object... params){
		return this.jdbcTemplate.queryForObject(sql, Long.class, params);
	}
	
	public Map<String, Object> jdQueryForMap(String sql){
		return this.jdbcTemplate.queryForMap(sql);
	}
	
	public Map<String, Object> jdQueryForMap(String sql, Object... params){
		return this.jdbcTemplate.queryForMap(sql, params);
	}
	
	public List<Map<String, Object>> jdQueryForList(String sql){
		return this.jdbcTemplate.queryForList(sql);
	}
	
	public List<Map<String, Object>> jdQueryForList(String sql, Object... params){
		return this.jdbcTemplate.queryForList(sql, params);
	}

	/******************************** Getter And Setter ********************************/
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
}

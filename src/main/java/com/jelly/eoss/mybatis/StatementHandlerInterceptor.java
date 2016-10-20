package com.jelly.eoss.mybatis;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;

import com.jelly.eoss.mybatis.dialect.Dialect;

@Intercepts({ @Signature(type=StatementHandler.class, method="prepare", args={ Connection.class }) })
public class StatementHandlerInterceptor implements Interceptor {
	private Logger logger = Logger.getLogger(this.getClass());
	private Dialect dialect;

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		logger.debug("mybatis StatementHandlerInterceptor Interceptor start....");
		
		StatementHandler statementHandler = (StatementHandler)invocation.getTarget();
		if(statementHandler instanceof RoutingStatementHandler){
			this.process((RoutingStatementHandler)statementHandler);
		}

		return invocation.proceed();
	}
	
	private void process(RoutingStatementHandler routingStatementHandler) throws Exception{
		//获取BaseStatementHandler
		Field delegateField = RoutingStatementHandler.class.getDeclaredField("delegate");
		delegateField.setAccessible(true);
		StatementHandler statementHandler = (StatementHandler)delegateField.get(routingStatementHandler);
		delegateField.setAccessible(false);
		if(!(statementHandler instanceof BaseStatementHandler)){
			return;
		}
		BaseStatementHandler baseStatementHandler = (BaseStatementHandler)statementHandler;
		
		//从BaseStatementHandler中获取RowBounds的一个引用，取出start和limit
		//但此处的RowBounds并不是最终处理ResultSet时，使用的RowBounds引用
		Field rowBoundsField = BaseStatementHandler.class.getDeclaredField("rowBounds");
		rowBoundsField.setAccessible(true);
		RowBounds rowBounds = (RowBounds) rowBoundsField.get(baseStatementHandler);
		rowBoundsField.setAccessible(false);

		if (rowBounds == null || rowBounds == RowBounds.DEFAULT) {
			return;
		}
		
		int start = rowBounds.getOffset();
		int limit = rowBounds.getLimit();
				
		BoundSql boundSql = baseStatementHandler.getBoundSql();
		String originalSql = boundSql.getSql();
//		logger.debug("originalSql>>>");
//		logger.debug(originalSql);
		
		String newSql = this.dialect.getPageSql(originalSql, start, limit);
		
		//重新设置新的SQL
		Field Field = BoundSql.class.getDeclaredField("sql");
		Field.setAccessible(true);
		Field.set(boundSql, newSql);
		Field.setAccessible(false);

//		logger.debug("newSql>>>");
//		logger.debug(boundSql.getSql());
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties arg0) {

	}

	public Dialect getDialect() {
		return dialect;
	}

	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}

}

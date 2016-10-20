package com.jelly.eoss.mybatis;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.Properties;

import org.apache.ibatis.executor.resultset.FastResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;

@Intercepts({ @Signature(type=ResultSetHandler.class, method="handleResultSets", args={ Statement.class }) })
public class ResultSetHandlerInterceptor implements Interceptor {
	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		logger.debug("mybatis ResultSetHandler Interceptor start....");
		
		FastResultSetHandler fastResultSetHandler = (FastResultSetHandler)invocation.getTarget();
		this.process(fastResultSetHandler);

		return invocation.proceed();
	}
	
	private void process(FastResultSetHandler fastResultSetHandler) throws Exception{
		/**
		 * 因为在StatementHandlerInterceptor拦截器中，我们已经给SQL加入了分页<br/>
		 * 此处拿到的ResustSet已经是我们想要的当前页的数据，所以RowBounds已经没有任何用处了<br/>
		 * 如果不设置成新RowBounds.DEFAULT，用户自定义的RowBounds将改变ResultSet结果集的处理，返回的数据会不正确<br/>
		 * 将FastResultSetHandler中的RowBounds，设置成新的RowBounds.DEFAULT即可
		 */
		Field rowBoundsField = fastResultSetHandler.getClass().getDeclaredField("rowBounds");
		rowBoundsField.setAccessible(true);
		rowBoundsField.set(fastResultSetHandler, RowBounds.DEFAULT);
		rowBoundsField.setAccessible(false);
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties arg0) {

	}

}

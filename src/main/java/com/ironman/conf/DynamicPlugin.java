package com.ironman.conf;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: ZJW
 * @Description:作者：本杰明警官 链接：http://www.jianshu.com/p/2222257f96d3
 * 來源：简书
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 * @Date: Created in 11:53 2017/11/29 0029
 **/
@Intercepts({
		@Signature(type = Executor.class, method = "update", args = {
				MappedStatement.class, Object.class}),
		@Signature(type = Executor.class, method = "query", args = {
				MappedStatement.class, Object.class, RowBounds.class,
				ResultHandler.class})})
public class DynamicPlugin implements Interceptor {

	private static final Logger logger = Logger.getLogger(DynamicPlugin.class);

	private static final String REGEX = ".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020.*";

	private static final Map<String, String> cacheMap = new ConcurrentHashMap<String, String>();


	@Override
	public Object intercept(Invocation invocation) throws Throwable{
		boolean synchronizationActive = TransactionSynchronizationManager.isSynchronizationActive();
		if(!synchronizationActive){
			Object[] objects = invocation.getArgs();
			MappedStatement ms = (MappedStatement) objects[0];

			String dbtype = null;

			if((dbtype = cacheMap.get(ms.getId())) == null){
				//读方法
				if(ms.getSqlCommandType().equals(SqlCommandType.SELECT)){
					//!selectKey 为自增id查询主键(SELECT LAST_INSERT_ID() )方法，使用主库
					if(ms.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)){
						dbtype = DBContextHolder.DB_MASTER;
					} else {
						BoundSql boundSql = ms.getSqlSource().getBoundSql(objects[1]);
						String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("[\\t\\n\\r]", " ");
						if(sql.matches(REGEX)){
							dbtype = DBContextHolder.DB_MASTER;
						} else {
							dbtype = DBContextHolder.DB_SLAVE;
						}
					}
				} else {
					dbtype = DBContextHolder.DB_MASTER;
				}
				logger.warn("设置方法:" + ms.getId() + " " + dbtype + " " + ms.getSqlCommandType().name());
				cacheMap.put(ms.getId(), dbtype);
			}
			DBContextHolder.setDbType(dbtype);
		}

		try {
			return invocation.proceed();
		} catch(Exception e){
			//统一异常处理
			throw new RuntimeException("mybatis exception!!!", e);
		}

	}

	@Override
	public Object plugin(Object o){
		if(o instanceof Executor){
			return Plugin.wrap(o, this);
		} else {
			return o;
		}
	}

	@Override
	public void setProperties(Properties properties){

	}
}

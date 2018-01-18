package com.ironman.conf;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;

/**
 * @Author: ZJW
 * @Description: 作者：本杰明警官 链接：http://www.jianshu.com/p/2222257f96d3
 * 來源：简书
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 * @Date: Created in 11:48 2017/11/29 0029
 **/
public class DynamicDataSourceTransactionManager extends DataSourceTransactionManager {

	@Override
	protected void doBegin(Object transaction, TransactionDefinition definition){
		//设置数据源
		boolean readOnly = definition.isReadOnly();
		if(readOnly){
			DBContextHolder.setDbType(DBContextHolder.DB_SLAVE);
		} else {
			DBContextHolder.setDbType(DBContextHolder.DB_MASTER);
		}
		super.doBegin(transaction, definition);
	}

	@Override
	protected void doCleanupAfterCompletion(Object transaction){
		super.doCleanupAfterCompletion(transaction);
		DBContextHolder.clearDBType();
	}
}

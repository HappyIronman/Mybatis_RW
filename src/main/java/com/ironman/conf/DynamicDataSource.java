package com.ironman.conf;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


/**
 * @Author: ZJW
 * @Description:
 * @Date: Created in 11:58 2017/11/28 0028
 **/
public class DynamicDataSource extends AbstractRoutingDataSource {
	private Object masterDataSource; //写数据源

	private Object slaveDataSource; //读数据源

	@Override
	public void afterPropertiesSet(){
		if (this.masterDataSource == null) {
			throw new IllegalArgumentException("Property 'writeDataSource' is required");
		}
		setDefaultTargetDataSource(masterDataSource);
		Map<Object, Object> targetDataSources = new HashMap<Object,Object>();
		targetDataSources.put(DBContextHolder.DB_MASTER, masterDataSource);
		if(slaveDataSource != null) {
			targetDataSources.put(DBContextHolder.DB_SLAVE, slaveDataSource);
		}
		setTargetDataSources(targetDataSources);
		super.afterPropertiesSet();
	}

	@Override
	public Logger getParentLogger(){
		return null;
	}

	/**
	 * override determineCurrentLookupKey
	 * <p>
	 * Title: determineCurrentLookupKey
	 * </p>
	 * <p>
	 * Description: 自动查找datasource
	 * </p>
	 *
	 * @return
	 */
	@Override
	protected Object determineCurrentLookupKey(){
		String dataSource=DBContextHolder.getDbType();
		logger.info(dataSource);
		return dataSource;
	}


	public Object getMasterDataSource(){
		return masterDataSource;
	}

	public void setMasterDataSource(Object masterDataSource){
		this.masterDataSource = masterDataSource;
	}

	public Object getSlaveDataSource(){
		return slaveDataSource;
	}

	public void setSlaveDataSource(Object slaveDataSource){
		this.slaveDataSource = slaveDataSource;
	}
}

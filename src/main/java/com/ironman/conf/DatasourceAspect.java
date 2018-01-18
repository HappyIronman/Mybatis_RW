package com.ironman.conf;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * @Author: ZJW
 * @Description:
 * @Date: Created in 13:28 2017/11/28 0028
 **/
@Aspect
public class DatasourceAspect {

	private Logger logger = Logger.getLogger(DatasourceAspect.class);

	@Before("(execution(* insert*(..)) || execution(* save*(..)) ||" +
			"execution(* update*(..)) || execution(* delete*(..)) ) && " +
			"within(com.ironman.mapper.*)")
	public void setDBMaster(){
		logger.info("master");
		DBContextHolder.setDbType(DBContextHolder.DB_MASTER);
	}


	@Before("(execution(* select*(..)) || execution(* get*(..))) && within(org.mybatis.spring.*.*)")
	public void setDBSlave(){
		logger.info("slave");
		DBContextHolder.setDbType(DBContextHolder.DB_SLAVE);
	}

}

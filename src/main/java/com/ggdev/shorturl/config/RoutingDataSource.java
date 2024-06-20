package com.ggdev.shorturl.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class RoutingDataSource extends AbstractRoutingDataSource {
	
	public RoutingDataSource(List<DataSource> slaves) {
		
		super.setDefaultTargetDataSource(slaves.get(0));
		Map<Object, Object> slavesDataSource = IntStream.range(0, slaves.size()).boxed().collect(Collectors.toMap(n -> "slave-"+n, slaves::get));
	 	super.setTargetDataSources(slavesDataSource);
	}
	
	@Override
	protected Object determineCurrentLookupKey() {
		String res = "slave-0";
		String transactionName = "";
		
		
		if(StringUtils.isNotEmpty(TransactionSynchronizationManager.getCurrentTransactionName())) {
			transactionName = TransactionSynchronizationManager.getCurrentTransactionName();
		}

		//log.info("transactionName : {}", transactionName);
		if(transactionName.contains("mariadb")) { //mariadb
			res = "slave-0";
		}
		
		return res;
	}
}
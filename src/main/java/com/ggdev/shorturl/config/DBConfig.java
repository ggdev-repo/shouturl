package com.ggdev.shorturl.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@PropertySource("classpath:/application.yml")
public class DBConfig {

	@Autowired
	private ApplicationContext applicationContext;
	
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.mariadb.hikari")
	public HikariConfig hikariSlaveConfig0() {
		return new HikariConfig();
	}

	@Bean(name="slaveDataSources")
	public List<DataSource> slaveDataSources() {
		List<DataSource> res = new ArrayList<>();
		res.add(new HikariDataSource(hikariSlaveConfig0()));
		return res;
	}
	
	
	@Bean(name="routingDataSource")
	@DependsOn({"slaveDataSources"})
	public DataSource routingDataSource(@Qualifier("slaveDataSources") List<DataSource> slaveDataSources) {
		return new RoutingDataSource(slaveDataSources);
		
	}
	
	@Bean("proxyDataSource")
	@DependsOn("routingDataSource")
	public LazyConnectionDataSourceProxy dataSource(@Qualifier("routingDataSource") DataSource routingDataSource) {
		return new LazyConnectionDataSourceProxy(routingDataSource);
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(@Qualifier("proxyDataSource") DataSource proxyDataSource) {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(proxyDataSource);
		return transactionManager;
	}


	@Bean(name="sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("proxyDataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource);
		factoryBean.setMapperLocations(applicationContext.getResources("classpath:/mappers/*Mapper.xml"));
		factoryBean.setTypeAliasesPackage("com.ggdev.shorturl.dto");
		factoryBean.setConfiguration(mybatisConfg());
		return factoryBean.getObject();
	}

	@Bean
	public SqlSessionTemplate sqlSession(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
	@Bean
	@ConfigurationProperties(prefix = "mybatis.configuration")
	public org.apache.ibatis.session.Configuration mybatisConfg() {
		return new org.apache.ibatis.session.Configuration();
	}

}
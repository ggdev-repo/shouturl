package com.ggdev.shorturl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class SpringAsyncConfig {

	@Bean(name = "AsyncUpdateClickLog")
	public Executor AsyncUpdateClickLog()
	{
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(100);
		taskExecutor.setMaxPoolSize(10000);
		taskExecutor.setQueueCapacity(10000);
		taskExecutor.setThreadNamePrefix("AsyncUpdateClickLog-");
		taskExecutor.initialize();
		return taskExecutor;
	}

}
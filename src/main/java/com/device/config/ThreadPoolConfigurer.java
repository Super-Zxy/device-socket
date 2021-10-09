package com.device.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author zxy
 * @date 2021/10/1 19:33
 * @description
 */
@Configuration
public class ThreadPoolConfigurer {

	@Bean(name = "clientTaskPool")
	public ThreadPoolTaskExecutor clientTaskPool() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5);
		executor.setKeepAliveSeconds(60);
		executor.setMaxPoolSize(Integer.MAX_VALUE);
		executor.setThreadNamePrefix("clientTaskPool");
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.initialize();
		return executor;
	}

	@Bean(name = "clientMessageTaskPool")
	public ThreadPoolTaskExecutor clientMessageTaskPool() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5);
		executor.setKeepAliveSeconds(60);
		executor.setMaxPoolSize(Integer.MAX_VALUE);
		executor.setThreadNamePrefix("clientMessageTaskPool");
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.initialize();
		return executor;
	}
}

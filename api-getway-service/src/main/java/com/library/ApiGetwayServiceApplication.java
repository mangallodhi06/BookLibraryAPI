package com.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.library.filters.ErrorFilter;
import com.library.filters.PostFilter;
import com.library.filters.PreFilter;
import com.library.filters.RouteFilter;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class ApiGetwayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGetwayServiceApplication.class, args);
	}

	@Bean
	public PreFilter preFilter() {
		return new PreFilter();
	}

	@Bean
	public PostFilter postFilter() {
		return new PostFilter();
	}

	@Bean
	public ErrorFilter errorFilter() {
		return new ErrorFilter();
	}

	@Bean
	public RouteFilter routeFilter() {
		return new RouteFilter();
	}
}

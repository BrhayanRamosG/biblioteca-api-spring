package com.brag.biblioteca.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.brag.biblioteca.filter.JwtFilter;

@Configuration
public class FilterConfig {
	private final JwtFilter jwtFilter;

	public FilterConfig(JwtFilter jwtFilter) {
		this.jwtFilter = jwtFilter;
	}

	@Bean
	public FilterRegistrationBean<JwtFilter> registrationBean() {
		FilterRegistrationBean<JwtFilter> bean = new FilterRegistrationBean<>();
		bean.setFilter(jwtFilter);
		bean.addUrlPatterns("/api/*");
		return bean;
	}
}

package com.springboot.zuul.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.springboot.zuul.filters.ErrorFilter;
import com.springboot.zuul.filters.PostFilter;
import com.springboot.zuul.filters.PreFilter;
import com.springboot.zuul.filters.RouteFilter;


@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class ZuulServiceApplication {

	
	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ZuulServiceApplication.class, args);
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
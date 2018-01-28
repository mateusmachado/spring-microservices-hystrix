package com.cloud;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@SpringBootApplication
@RestController
@EnableHystrix
@EnableHystrixDashboard
public class SpringMicroservicesHystrixApplication {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Autowired
	RestTemplate restTemplate;

	@SuppressWarnings("unchecked")
	@RequestMapping("/startClient")
	@HystrixCommand(fallbackMethod = "failover")
	public List<String> client() {
		return this.restTemplate.getForObject("http://localhost:8888/service", List.class);
	}

	public List<String> failover() {
		return Arrays.asList("Default1", "Default2");
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringMicroservicesHystrixApplication.class, args);
	}

}

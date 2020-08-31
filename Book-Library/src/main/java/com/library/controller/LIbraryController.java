package com.library.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Date;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.library.model.Book;
import com.library.model.Suscriber;
import com.library.services.RemoteCallService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class LIbraryController {

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	private LoadBalancerClient loadBalancer; 
	
	@Autowired
	RemoteCallService remoteService;
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@RequestMapping("/library/all")
	@HystrixCommand(fallbackMethod = "getDataFallBack")
	public String  all() {
		ServiceInstance serviceInstance=loadBalancer.choose("Suscriber-Service");
		String baseUrl=serviceInstance.getUri().toString();
		baseUrl=baseUrl+"/allSuscribers";
		HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      HttpEntity <String> entity = new HttpEntity<String>(headers);
	      
	      return restTemplate.exchange(baseUrl, HttpMethod.GET, entity, String.class).getBody();
	}
	
	
	@RequestMapping("/library/suscriber/{s_name}")
	public String  findBySuscriber(@PathVariable String s_name) {
		ServiceInstance serviceInstance=loadBalancer.choose("Suscriber-Service");
		String baseUrl=serviceInstance.getUri().toString();
		
		baseUrl=baseUrl+"/suscriber/";
		HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      HttpEntity <String> entity = new HttpEntity<String>(headers);
	      
	      return restTemplate.exchange(baseUrl+s_name, HttpMethod.GET, entity, String.class).getBody();
	}
	
	@RequestMapping("/library/addSuscriber")
	public String addSuscriber(@ModelAttribute Suscriber suscriber) {
		ServiceInstance serviceInstance=loadBalancer.choose("Suscriber-Service");
		String baseUrl=serviceInstance.getUri().toString();
		baseUrl=baseUrl+"/suscriber/";
		HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      HttpEntity <Suscriber> entity = new HttpEntity<Suscriber>(suscriber,headers);
	      
	      return restTemplate.exchange("http://localhost:8081/suscriber/", HttpMethod.GET, entity, String.class).getBody();
	}
	
	
	@RequestMapping("/library/allBook")
	public List<Book> findAllBooksbyFeign() {
		return remoteService.getBookData();
	}
	
	@RequestMapping("/library/book/{b_id}")
	public  ResponseEntity<Book> findByBookIdByFeign(@PathVariable String b_id) {
		
		List<ServiceInstance> instances = discoveryClient.getInstances("api-getway-zuul");
		ServiceInstance serviceInstance = instances.get(0);

		String baseUrl = serviceInstance.getUri().toString();

		baseUrl = baseUrl + "/book/";

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Book> response = null;
		try {
			response = restTemplate.exchange(baseUrl+b_id, HttpMethod.GET, getHeaders(), Book.class);
			System.out.println("Data ::::"+response.getBody());
			System.out.println(response.getHeaders());
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return	response;
		
	}
	private static HttpEntity<?> getHeaders() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return new HttpEntity<>(headers);
	}
	
	public ResponseEntity<String> getDataFallBack() {
		System.out.println("CIRCUIT BREAKER ENABLED!!! No Response From Book Service at this moment. " +
                " Service will be back shortly - " + new Date());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("CIRCUIT BREAKER ENABLED!!! No Response From Book Service at this moment. " +
                " Service will be back shortly - " + new Date());
		
	}
	
}

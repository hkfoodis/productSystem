package org.zerock.shoppingcart.service;

import org.springframework.stereotype.Service;

import org.zerock.shoppingcart.domain.Product;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.zerock.shoppingcart.model.ProductInfo;

@Service
public class WebProductService {
	
	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;
	
	protected String serviceUrl;
	
	protected Logger logger = Logger.getLogger(WebProductService.class
			.getName());
	
	public WebProductService(String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl
				: "http://" + serviceUrl;
	}
	
	/*public PaginationResult<ProductInfo> productList(){
		PaginationResult<ProductInfo> results = null;
		
		results = restTemplate.getForObject
	}*/
	
	public List<ProductInfo> productList(){
		ProductInfo[] catalog = null;
		
		catalog = restTemplate.getForObject(serviceUrl + "/productList", ProductInfo[].class);
		
		List<ProductInfo> results = Arrays.asList(catalog);
		
		return results;
	}
	
	public ProductInfo product() {
		ProductInfo productResult = null;
		
		productResult = restTemplate.getForObject(serviceUrl + "/product/{product_code}", ProductInfo.class);
		
		return productResult;
	}
	
	public void deleteProduct() {
		restTemplate.delete(serviceUrl + "/product/{product_code}");
	}
	
	public String add(String addend1, String addend2) {
		return restTemplate.getForObject(serviceUrl + "/add?addend1={addend1}&addend2={addend2}", String.class, addend1, addend2);
	}
}

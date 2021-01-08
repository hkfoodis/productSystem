package org.zerock.shoppingcart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

import org.zerock.shoppingcart.repository.AccountRepository;
import org.zerock.shoppingcart.controller.WebProductController;
import org.zerock.shoppingcart.service.WebProductService;
import org.zerock.shoppingcart.config.WebSecurityConfig;
import org.zerock.shoppingcart.config.WebConfiguration;
import org.zerock.shoppingcart.controller.HomeController;
import org.zerock.shoppingcart.service.UserDetailsServiceImpl;

@EnableDiscoveryClient
@SpringBootApplication
@Import(ShopWebConfiguration.class)
@ComponentScan(useDefaultFilters = false)
public class ShopWebApplication {
    
	@Autowired
	protected AccountRepository accountRepository;
	
	public static final String PRODUCT_SERVICE_URL = "http://product-service";
	
    public static void main(String[] args) {
    	SpringApplication.run(ShopWebApplication.class, args);
    }
    
    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
    	return new RestTemplate();
    }
    
    @Bean
    public WebProductService productService() {
    	return new WebProductService(PRODUCT_SERVICE_URL);
    }
    
    @Bean
	public WebProductController productController() {
		return new WebProductController(productService());
	}
    
    @Bean
	public HomeController homeController() {
		return new HomeController();
	}
    
    @Bean
    public AccountRepository accountRepository() {
    	return new AccountRepository();
    }
    
    @Bean
    public WebConfiguration webconfiguration() {
    	return new WebConfiguration();
    }
    
    @Bean
    public WebSecurityConfig websecurityConfig() {
    	return new WebSecurityConfig();
    }
    
    @Bean
    public UserDetailsServiceImpl userDetailsService() {
    	return new UserDetailsServiceImpl();
    }

}

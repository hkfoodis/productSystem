package org.zerock.shoppingcart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.zerock.shoppingcart.repository.ProductRepository;

@EnableDiscoveryClient
@SpringBootApplication

@Import(ShopProductConfiguration.class)
public class ShopProductApplication {

	@Autowired
	protected ProductRepository productRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(ShopProductApplication.class, args);
	}
}

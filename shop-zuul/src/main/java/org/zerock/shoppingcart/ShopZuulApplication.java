package org.zerock.shoppingcart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
@Import(TomcatWebServerCustomizer.class)
@ComponentScan(useDefaultFilters = false)
public class ShopZuulApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ShopZuulApplication.class, args);
	}
}

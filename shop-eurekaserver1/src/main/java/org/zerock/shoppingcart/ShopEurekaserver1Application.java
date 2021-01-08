package org.zerock.shoppingcart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class ShopEurekaserver1Application {

	public static void main(String[] args) {
		SpringApplication.run(ShopEurekaserver1Application.class, args);
	}

}

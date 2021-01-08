package org.zerock.shoppingcart.controller;

import java.util.List;

import org.zerock.shoppingcart.domain.Product;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.shoppingcart.repository.ProductRepository;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.zerock.shoppingcart.pagination.PaginationResult;
import org.zerock.shoppingcart.model.ProductInfo;

@RestController
@Transactional
@AllArgsConstructor
public class MainController {

	@Autowired
	protected ProductRepository productRepository;
	
	@InitBinder
	public void myInitBinder(WebDataBinder dataBinder) {
	   Object target = dataBinder.getTarget();
	   if (target == null) {
	      return;
	   }
	   System.out.println("Target=" + target); 
	}
	
	@RequestMapping("/productList")
	public List<ProductInfo> listProductHandler(){
				
		String likeName = " ";
		List<ProductInfo> productlist = productRepository.queryProducts(likeName);
		
		return productlist;
	}
	
	@RequestMapping("/add")
	public String doAdd(@RequestParam(defaultValue="0") String addend1,
			@RequestParam(defaultValue="0") String addend2) {

		int augend1 = Integer.valueOf(addend1);
		int augend2 = Integer.valueOf(addend2);
		int sum = augend1 + augend2;

		return "{\"addend1\":\"" + addend1 + "\", \"addend2\":\"" + addend2 + "\", \"sum\": \"" + sum + "\"}";
	}
}

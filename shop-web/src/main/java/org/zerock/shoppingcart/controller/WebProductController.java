package org.zerock.shoppingcart.controller;


import org.zerock.shoppingcart.model.ProductInfo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.zerock.shoppingcart.service.WebProductService;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Transactional
public class WebProductController {

	@Autowired
	protected WebProductService webproductService; 

	public WebProductController(WebProductService productService) {
		this.webproductService = productService;
	}
	
	@InitBinder
	public void myInitBinder(WebDataBinder dataBinder) {
	   Object target = dataBinder.getTarget();
	   if (target == null) {
	      return;
	   }
	   System.out.println("Target=" + target); 
	}
	
	// GET: Show Login Page
	@RequestMapping(value = { "/admin/login" }, method = RequestMethod.GET)
	public String login(Model model) {
	 
	   return "login";
	}
	
	@RequestMapping(value = { "/admin/accountInfo" }, method = RequestMethod.GET)
	public String accountInfo(Model model) {
	 
	   UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	   System.out.println(userDetails.getPassword());
	   System.out.println(userDetails.getUsername());
	   System.out.println(userDetails.isEnabled());
	 
	   model.addAttribute("userDetails", userDetails);
	   return "accountInfo";
	}
	
	@RequestMapping("/add")
	public String doAdd(@RequestParam(defaultValue="0") String addend1,
			@RequestParam(defaultValue="0") String addend2,
			Model model) {

		String sum = webproductService.add(addend1, addend2);

		model.addAttribute("json", sum);

		return "sum";
	}
	
	/*@RequestMapping({ "/productList" })
	public String listProductHandler(Model model, //
	      @RequestParam(value = "name", defaultValue = "") String likeName,
	      @RequestParam(value = "page", defaultValue = "1") int page) {
	   final int maxResult = 5;
	   final int maxNavigationPage = 10;
	 
	   PaginationResult<ProductInfo> result = webproductService.productList(page, //
	         maxResult, maxNavigationPage, likeName);
	 
	   model.addAttribute("paginationProducts", result);
	   return "productList";
	}*/
	
	@RequestMapping({ "/productlist" })
	public String listProductHandler(Model model) {    
		/*final int maxResult = 5;
	    final int maxNavigationPage = 10;*/
	    List<ProductInfo> result = webproductService.productList();
	 		
	    model.addAttribute("paginationProducts", result);
		return "productList";
	}
	
	@RequestMapping(value = "/product/{product_code}", method = RequestMethod.GET)
	public @ResponseBody String Product(@PathVariable String product_code, Model model) {
		ProductInfo result = null;
		
		result = webproductService.product();
		model.addAttribute("paginationProducts", result);
		
		return "productList";
	}
	
	@RequestMapping(value = "/product/{product_code}", method = RequestMethod.POST)
	public @ResponseBody void saveProduct(@PathVariable String product_code) {
		
		
	}
	
	@RequestMapping(value = "/product/{product_code}", method = RequestMethod.DELETE)
	public @ResponseBody void productdelete(@PathVariable String product_code) {
		
		webproductService.deleteProduct();
	}
}

package com.model2.mvc.web.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@RestController
@RequestMapping("/product/*")
public class ProductRestController {

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method 备泅x
	
	//积己磊 皋家靛
	public ProductRestController() {
		// TODO Auto-generated constructor stub
		System.out.println(this.getClass());
	}
	
	
	@RequestMapping(value = "json/getProduct/{prodNo}", method = RequestMethod.GET)
	public Product getProduct( @PathVariable int prodNo)throws Exception{
		
		System.out.println("/product/getProduct : GET");
		
		//Business Logic 
		return productService.getProduct(prodNo);
		
	}
	
	@RequestMapping(value = "json/updateProduct/{prodNo}", method = RequestMethod.POST)
	public Product updateProduct(@RequestBody Product product,@PathVariable int prodNo)throws Exception{
		
		System.out.println("/product/json/updateProduct : POST");
		//Business Logic
		System.out.println("::"+product);
		Product voidProduct = productService.getProduct(prodNo);
		voidProduct.setProdName(product.getProdName());
		voidProduct.setPrice(product.getPrice());
		
		productService.updateProduct(voidProduct);
		
		return voidProduct;
			
	}
	
}

package com.model2.mvc.web.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@Controller
@RequestMapping("/product/*")
public class ProductController {

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method 구현x
	
	//생성자 메소드
	public ProductController() {
		// TODO Auto-generated constructor stub
		System.out.println(this.getClass());
	}
	
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	
	//@RequestMapping("/addProductView.do")
	
	//public String addProductView( @ModelAttribute("product")Product product)throws Exception{
		
	@RequestMapping(value = "addProduct", method = RequestMethod.GET)
	public String addProduct() throws Exception {	
		
		System.out.println("/product/addProductView : GET");
		//Business Logic
		
		
		return "forward:/product/addProductView.jsp";
		
	}
	
	
	@RequestMapping(value = "addProduct", method = RequestMethod.POST)
	public String addProduct( @ModelAttribute("product")Product product) throws Exception{
		
		System.out.println("/product/addProduct : POST");
		//Business Logic
		productService.addProduct(product);
		System.out.println(product);
		return "forward:/product/addProduct.jsp";
	}
	
	@RequestMapping(value = "getProduct", method = RequestMethod.GET)
	public String getProduct(@RequestParam("prodNo")int prodNo, @RequestParam("menu") String menu, Model model)throws Exception{
		
		System.out.println("/product/getProduct : GET");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		//Model 과 view 연결
		model.addAttribute("product",product);
		
		if (menu.equals("manage")) {
			
			return "forward:/product/updateProduct?";
		}else {
			return "forward:/product/getProduct.jsp";
		}
	}
	
	@RequestMapping( value = "updateProduct", method = RequestMethod.GET)
	public String updateProduct( @RequestParam("prodNo")int prodNo, Model model)throws Exception{
		
		System.out.println("/product/updateProductView : GET");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		//Model 과 view 연결
		model.addAttribute("product",product);
		
		
		return "forward:/product/updateProductView.jsp";
		
	}
	
	@RequestMapping(value = "updateProduct", method = RequestMethod.POST)
	public String updateProduct( @ModelAttribute("product") Product product)throws Exception{
		
		System.out.println("/product/updateProduct : POST");
		//Business Logic
		productService.updateProduct(product);
		
		
		return "redirect:/product/getProduct?prodNo="+product.getProdNo()+"&menu=search";
	}
	
	@RequestMapping(value = "listProduct")
	public String listProduct( @ModelAttribute("search") Search search ,@RequestParam("menu") String menu,Model model)throws Exception{
		
		System.out.println("/product/listProduct : GET/POST");
		
		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		// 나는 여기에 setCurrntPage를 해준 미친놈입니다....
		search.setPageSize(pageSize);
		System.out.println("end :: "+search.getEndRowNum());
		System.out.println("start :: "+search.getStartRowNum());
		
		// Business Logic
		Map<String, Object> map = productService.getProductList(search);
		
		Page resultPage = new Page(search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit,pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search",search);
		model.addAttribute("menu", menu);
		System.out.println(search);
		
		return "forward:/product/listProduct.jsp";
	}
	
	
	
	
}

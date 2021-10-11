package com.services.eCommerceRestful.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomePageController {
	
	@GetMapping("/")
	public String showHomePage() {
		String home = "<h1> Ecommerce Restful Web Services </h1>" + " <h2> Welcome </h2>";
		
		return home;
	}

}

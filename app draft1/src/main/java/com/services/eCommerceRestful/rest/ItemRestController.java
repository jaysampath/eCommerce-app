package com.services.eCommerceRestful.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.services.eCommerceRestful.entity.Item;
import com.services.eCommerceRestful.service.EcommerceService;

@RestController
@RequestMapping("/item")
@CrossOrigin
public class ItemRestController {
	
	@Autowired
	private EcommerceService eCommerceService;
	
	@GetMapping("/items")
	public List<Item> getItems(){
		List<Item> items = eCommerceService.getAllItems();
		return items;
	}
	
	@PostMapping("/items")
	public Item AddNewItem(@RequestBody Item item) {
		item.setItemId(0);
		eCommerceService.addNewItem(item);
		return item;
	}
	
	@PutMapping("/items")
	public Item updateItem(@RequestBody Item item) {
		eCommerceService.updateItem(item);
		return item;
	}
	
	@GetMapping("/items/{itemId}")
	public Item GetItem(@PathVariable int itemId) {
		Item item = eCommerceService.getItem(itemId);
		return item;
	}
	
	@GetMapping("/names/categories")
	public List<String> getAllCategoryNames(){
		List<String> categoryNames = eCommerceService.getAllCategories();
		return categoryNames;
	}
	
	@GetMapping("/names/subcategory/{categoryName}")
	public List<String> getSubCategoryNames(@PathVariable String categoryName ){
		List<String> subCategoryNames = eCommerceService.getSubCategoryNames(categoryName);
		return subCategoryNames;
	}
	
	@GetMapping("/items/{categoryName}/{subCategoryName}")
	public List<Item> getItemsBySubCategory(@PathVariable String categoryName, @PathVariable String subCategoryName){
		List<Item> items = eCommerceService.getItemsBySubCategory(categoryName, subCategoryName);
		return items;
	}

}

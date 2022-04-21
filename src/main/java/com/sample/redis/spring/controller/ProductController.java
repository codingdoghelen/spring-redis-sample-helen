package com.sample.redis.spring.controller;

import com.sample.redis.spring.entity.Product;
import com.sample.redis.spring.repository.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sample.redis.spring.repository.ProductDao.HASH_KEY;

@RestController
@RequestMapping("/product")
//@EnableCaching
public class ProductController {

	@Autowired
	private ProductDao dao;

	@PostMapping
	public Product save(@RequestBody Product product) {
		return dao.save(product);
	}

	@GetMapping
	public List<Product> getAllProducts() {
		return dao.findAll();
	}

	@GetMapping("/{id}")
	public Product findProduct(@PathVariable int id) {
		return dao.findProductById(id);
	}
	@DeleteMapping("/{id}")
	public String remove(@PathVariable int id)   {
		return dao.deleteProduct(id);
	}


}

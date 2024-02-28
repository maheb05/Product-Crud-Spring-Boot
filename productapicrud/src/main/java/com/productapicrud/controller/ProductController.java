package com.productapicrud.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.productapicrud.Repository.ProductRepository;
import com.productapicrud.model.Product;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Transactional
@Slf4j
public class ProductController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	private ProductRepository productRepository;
	public ProductController() {
		
	}
	@Autowired
	public ProductController(ProductRepository productRepository){
		this.productRepository = productRepository;
	}
	
	@PostMapping("/createProduct")
	public ResponseEntity<Product> createProduct(@RequestBody Product product) {
	    Product savedProduct = productRepository.save(product);
	    return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
	}

	
	@GetMapping("/allProducts")
	public ResponseEntity<List<Product>> getAllProducts(){
		logger.info("inside get all products api");
		ArrayList<Product> list = new ArrayList<>();
		productRepository.findAll().forEach(list::add);
		return new ResponseEntity<List<Product>>(list,HttpStatus.OK);
	}
	
	@GetMapping("/allProducts/{productId}")
	public ResponseEntity<?> getProductById(@PathVariable long productId){
		logger.info("inside get products by id api");
		Optional<Product> product = productRepository.findById(productId);
		if(product.isPresent()) {
			return new ResponseEntity<>(product.get(),HttpStatus.FOUND);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Record found with this "+productId+" Id");
		}
	}
	
	@PutMapping("/allProducts")
	public String updateProduct(@RequestBody Product product) {
		Optional<Product> particularProduct = productRepository.findById(product.getProductId());
		if(particularProduct.isPresent()) {
			Product existingProduct = particularProduct.get();
			existingProduct.setProductName(product.getProductName());
			existingProduct.setProductPrice(product.getProductPrice());
			existingProduct.setProductReview(product.getProductReview());
			productRepository.save(existingProduct);
			return "Product Details Altered Successfully";
		}else {
			return "No Product found with id "+product.getProductId()+",Please check your id";
		}
	}
	
	@DeleteMapping("/allProducts/{productId}")
	public ResponseEntity<String> deleteProductById(@PathVariable long productId) {
		Optional<Product> product = productRepository.findById(productId);
		if(product.isPresent()) {
			productRepository.deleteById(productId);
			return ResponseEntity.ok("Product with Id "+productId+" successfully deleted");
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("product with Id "+productId+" not found please check");
		}
	}
	
	@DeleteMapping("/allProducts")
	public ResponseEntity<String> deleteAlll(){
		long count = productRepository.count();
		if(count > 0) {
			productRepository.deleteAll();
			return ResponseEntity.ok("Successfully deleted all records");
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Records Found");
		}
	}
	
	@GetMapping("/review/{productReview}")
	public ResponseEntity<List<Product>> getProductByReview(@PathVariable String productReview){
		List<Product> products = productRepository.findAll();
		List<Product> productsByReview = products.stream()
										.filter(product -> product.getProductReview().equalsIgnoreCase(productReview))
										.collect(Collectors.toList());
		if(!productsByReview.isEmpty()) {
			return ResponseEntity.ok(productsByReview);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	@GetMapping("/price/{productPrice}")
	public ResponseEntity<List<Product>> getProductByPrice(@PathVariable long productPrice){
		List<Product> products = productRepository.findAll();
		List<Product> productsByPrice = products.stream()
				.filter(product -> product.getProductPrice() >= productPrice)
				.collect(Collectors.toList());
		if(!productsByPrice.isEmpty()) {
			return ResponseEntity.ok(productsByPrice);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	@GetMapping("/name/{productName}")
	public ResponseEntity<List<Product>> getProductByName(@PathVariable String productName){
		List<Product> products = productRepository.findAll();
		List<Product> productsByName = products.stream()
				.filter(product -> product.getProductName().equalsIgnoreCase(productName))
				.collect(Collectors.toList());
		if(!productsByName.isEmpty()) {
			return ResponseEntity.ok(productsByName);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	@GetMapping("/skip/{productId}")
	public ResponseEntity<List<Product>> skipProduct(@PathVariable long productId){
		List<Product> products = productRepository.findAll();
		List<Product> unSkippedProduct = products.stream().filter(product -> product.getProductId() != productId).collect(Collectors.toList());
		if(!unSkippedProduct.isEmpty()) {
			return ResponseEntity.ok(unSkippedProduct);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
}

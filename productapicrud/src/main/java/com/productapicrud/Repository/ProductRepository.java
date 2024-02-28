package com.productapicrud.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.productapicrud.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	//List<Product> findByProductReview(String productReview);
}

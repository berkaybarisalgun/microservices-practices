package com.berkaybarisalgun.productservice.repository;

import com.berkaybarisalgun.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product,String> {
}

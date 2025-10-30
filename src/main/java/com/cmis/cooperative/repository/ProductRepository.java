package com.cmis.cooperative.repository;

import com.cmis.cooperative.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Optional<Product> findByNameIgnoreCase(String productName);

    Optional<Product> findByPublicId(UUID publicId);
}

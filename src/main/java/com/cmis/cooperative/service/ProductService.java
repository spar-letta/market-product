package com.cmis.cooperative.service;

import com.cmis.cooperative.model.Product;
import com.cmis.cooperative.model.dataType.ProductStatus;
import com.cmis.cooperative.model.dto.LikeResponse;
import com.cmis.cooperative.model.dto.ProductRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public interface ProductService {
    Product createProduct(ProductRequestDto productRequestDto, Authentication loggedInUser);

    Page<Product> getProducts(String searchParam, LocalDate startDate, LocalDate endDate, ProductStatus status, Pageable pageable);

    Product updateProduct(UUID publicId, ProductRequestDto productRequestDto, Authentication loggedInUser);

    Product getProduct(UUID publicId, Authentication loggedInUser);

    void deleteProduct(UUID publicId, Authentication loggedInUser);

    void makeLike(UUID productPublicId, Authentication loggedInUser);

    LikeResponse getProductLike(UUID publicId, Authentication loggedInUser);
}

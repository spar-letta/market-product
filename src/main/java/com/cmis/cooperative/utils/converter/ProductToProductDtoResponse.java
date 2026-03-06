package com.cmis.cooperative.utils.converter;

import com.cmis.cooperative.model.Product;
import com.cmis.cooperative.model.response.ProductDtoResponse;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.time.LocalDate;
import java.util.UUID;

@Component
public class ProductToProductDtoResponse implements Converter<Product, ProductDtoResponse> {

    @Override
    public ProductDtoResponse convert(Product product) {
        if (product == null) {
            return null;
        }

        UUID publicId = product.getPublicId();

        LocalDate dateCreated = product.getDateCreated().toLocalDate();
        Time timeCreated = Time.valueOf(product.getDateCreated().toLocalTime());

        String ownerFirstName = product.getOwnedBY() != null
                ? product.getOwnedBY().getFirstName()
                : null;

        return ProductDtoResponse.builder()
                .publicId(publicId)
                .dateCreated(dateCreated)
                .timeCreated(timeCreated)
                .ownerFirstName(ownerFirstName)
                .itemName(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}

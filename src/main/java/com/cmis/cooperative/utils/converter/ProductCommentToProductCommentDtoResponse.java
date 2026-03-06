package com.cmis.cooperative.utils.converter;

import com.cmis.cooperative.model.ProductComment;
import com.cmis.cooperative.model.response.ProductCommentDtoResponse;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.time.LocalDate;
import java.util.UUID;

@Component
public class ProductCommentToProductCommentDtoResponse implements Converter<ProductComment, ProductCommentDtoResponse> {

    @Override
    public ProductCommentDtoResponse convert(ProductComment productComment) {
        if (productComment == null) {
            return null;
        }

        UUID publicId = productComment.getPublicId();

        LocalDate dateCreated = productComment.getDateCreated().toLocalDate();
        Time timeCreated = Time.valueOf(productComment.getDateCreated().toLocalTime());

        String ownerFirstName = productComment.getCreatedBy() != null
                ? productComment.getCreatedBy().getFirstName()
                : null;

        String parentComment = productComment.getParentComment() != null
                ? productComment.getParentComment().getComment()
                : null;

        String parentCommentOwner = productComment.getParentComment() != null
                ? productComment.getParentComment().getCreatedBy().getFirstName()
                : null;

        return ProductCommentDtoResponse.builder()
                .publicId(publicId)
                .createdDate(dateCreated)
                .timeCreated(timeCreated)
                .createdByName(ownerFirstName)
                .comment(productComment.getComment())
                .parentCommentText(parentComment)
                .parentCommentOwnerName(parentCommentOwner)
                .build();
    }
}

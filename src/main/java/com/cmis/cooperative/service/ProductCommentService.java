package com.cmis.cooperative.service;

import com.cmis.cooperative.model.ProductComment;
import com.cmis.cooperative.model.dto.CommentRequestDto;
import com.cmis.cooperative.model.response.ProductCommentDtoResponse;
import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface ProductCommentService {
    ProductCommentDtoResponse createProductComment(UUID productId, CommentRequestDto commentRequestDto,
                                                   Authentication loggedInUser);
}

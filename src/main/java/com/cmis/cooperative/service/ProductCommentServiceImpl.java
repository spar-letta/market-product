package com.cmis.cooperative.service;

import com.cmis.cooperative.model.Product;
import com.cmis.cooperative.model.ProductComment;
import com.cmis.cooperative.model.dto.CommentRequestDto;
import com.cmis.cooperative.model.response.ProductCommentDtoResponse;
import com.cmis.cooperative.model.vo.User;
import com.cmis.cooperative.repository.ProductCommentRepository;
import com.cmis.cooperative.repository.UserRepository;
import com.cmis.cooperative.utils.ThrowError;
import com.cmis.cooperative.utils.converter.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductCommentServiceImpl implements ProductCommentService{

    private final ProductCommentRepository productCommentRepository;
    private final UserRepository userRepository;
    private final ThrowError throwError;
    private final ProductService productService;
    private final Converter<ProductComment, ProductCommentDtoResponse> converter;

    @Override
    public ProductCommentDtoResponse createProductComment(UUID productId, CommentRequestDto commentRequestDto,
                                               Authentication loggedInUser) {
        User user = validateUserUsername(loggedInUser.getName());

        Product product = productService.getProduct(productId, loggedInUser);

        ProductComment productComment = new ProductComment();
        productComment.setCreatedBy(user);
        productComment.setComment(commentRequestDto.comment());
        productComment.setProduct(product);

        if (commentRequestDto.commentParentPublicId() != null){
            ProductComment parentProductComment = productCommentRepository.findByPublicId(commentRequestDto.commentParentPublicId()).orElse(null);
            if (parentProductComment == null){
                throwError.throwException(String.format("main comment does not exit"));
            }
            productComment.setParentComment(parentProductComment);
        }
        ProductComment saveProductComment = productCommentRepository.save(productComment);

        ProductCommentDtoResponse productCommentDtoResponse = converter.convert(saveProductComment);

        return productCommentDtoResponse;
    }





    public User validateUserUsername(String username) {
        User user = userRepository.findByUserName(username).orElse(null);
        if (user == null) {
            throwError.throwException(String.format("User does not exit"));
        }
        return user;
    }
}

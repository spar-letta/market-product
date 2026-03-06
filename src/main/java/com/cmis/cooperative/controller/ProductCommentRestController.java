package com.cmis.cooperative.controller;

import com.cmis.cooperative.docs.Examples;
import com.cmis.cooperative.model.ProductComment;
import com.cmis.cooperative.model.dto.CommentRequestDto;
import com.cmis.cooperative.model.response.ProductCommentDtoResponse;
import com.cmis.cooperative.service.ProductCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/productComments")
@RestController
@AllArgsConstructor
@Tag(name = "productComments")
public class ProductCommentRestController {

    private final ProductCommentService productCommentService;

    @PostMapping("/{productId}")
    @PreAuthorize("hasAuthority('CREATE_COMMENT')")
    @Operation(summary = "create product comment", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(example = "")))
    }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(examples = {
            @ExampleObject(name = "create product comment", value = Examples.PRODUCT_DETAILS_REQUEST)})))
    public ProductCommentDtoResponse createProductComment(@PathVariable(name = "productId") UUID productId,
                                                          @Valid @RequestBody CommentRequestDto commentRequestDto,
                                                          @Parameter(hidden = true) Authentication loggedInUser) {
        return productCommentService.createProductComment(productId, commentRequestDto, loggedInUser);
    }
}

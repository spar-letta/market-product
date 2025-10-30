package com.cmis.cooperative.controller;

import com.cmis.cooperative.docs.Examples;
import com.cmis.cooperative.model.Product;
import com.cmis.cooperative.model.dataType.ProductStatus;
import com.cmis.cooperative.model.dto.LikeResponse;
import com.cmis.cooperative.model.dto.ProductRequestDto;
import com.cmis.cooperative.service.ProductService;
import com.cmis.cooperative.views.BaseView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RequestMapping("/products")
@RestController
@AllArgsConstructor
@Tag(name = "products")
public class ProductRestController {
    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_PRODUCT')")
    @JsonView(BaseView.ProductView.class)
    @Operation(summary = "create product details", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(example = "")))
    }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(examples = {
            @ExampleObject(name = "create product details", value = Examples.PRODUCT_DETAILS_REQUEST)})))
    public Product createProduct(@RequestBody ProductRequestDto productRequestDto,
                                 @Parameter(hidden = true) Authentication loggedInUser) {
        return productService.createProduct(productRequestDto, loggedInUser);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_PRODUCTS')")
    @Operation(summary = "Read all products", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(example = "")))})
    @JsonView(BaseView.ProductView.class)
    public Page<Product> getAllProducts(
            @RequestParam(name = "searchParam", required = false) String searchParam,
            @RequestParam(name = "status", required = false) ProductStatus status,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") @Parameter(example = "dd-MM-yyyy") LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") @Parameter(example = "dd-MM-yyyy") LocalDate endDate,
            @Parameter(hidden = true) Pageable pageable) {
        return productService.getProducts(searchParam, startDate, endDate, status, pageable);
    }

    @PutMapping("/{publicId}")
    @PreAuthorize("hasAuthority('UPDATE_PRODUCT')")
    @JsonView(BaseView.ProductView.class)
    @Operation(summary = "update product details", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(example = "")))
    }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(examples = {
            @ExampleObject(name = "update product details", value = Examples.PRODUCT_DETAILS_REQUEST)})))
    public Product updateProduct(@PathVariable UUID publicId,
                                 @RequestBody ProductRequestDto productRequestDto,
                                 @Parameter(hidden = true) Authentication loggedInUser) {
        return productService.updateProduct(publicId, productRequestDto, loggedInUser);
    }

    @GetMapping("/{publicId}")
    @PreAuthorize("hasAuthority('READ_PRODUCTS')")
    @Operation(summary = "Read product", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(example = "")))})
    @JsonView(BaseView.ProductView.class)
    public Product getProduct(@PathVariable(name = "publicId") UUID publicId,
                              @Parameter(hidden = true) Authentication loggedInUser) {
        return productService.getProduct(publicId, loggedInUser);
    }

    @DeleteMapping("/{publicId}")
    @PreAuthorize("hasAuthority('DELETE_PRODUCT')")
    @JsonView(BaseView.ProductView.class)
    @Operation(summary = "delete product details", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(example = "")))})
    public void deleteProduct(@PathVariable UUID publicId,
                              @Parameter(hidden = true) Authentication loggedInUser) {
        productService.deleteProduct(publicId, loggedInUser);
    }

    @PostMapping("/{publicId}/makeLike")
    @PreAuthorize("hasAuthority('CREATE_LIKE')")
    @JsonView(BaseView.ProductView.class)
    @Operation(summary = "create like", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(example = "")))
    }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(examples = {
            @ExampleObject(name = "create product details", value = Examples.PRODUCT_DETAILS_REQUEST)})))
    public void makeLike(@PathVariable(name = "publicId") UUID productPublicId,
                         @Parameter(hidden = true) Authentication loggedInUser) {
        productService.makeLike(productPublicId, loggedInUser);
    }

    @GetMapping("/{publicId}/makeLike")
    @PreAuthorize("hasAuthority('READ_LIKES')")
    @Operation(summary = "Read product like", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(example = "")))})
    @JsonView(BaseView.ProductView.class)
    public LikeResponse getProductLike(@PathVariable(name = "publicId") UUID publicId,
                                       @Parameter(hidden = true) Authentication loggedInUser) {
        return productService.getProductLike(publicId, loggedInUser);
    }
}

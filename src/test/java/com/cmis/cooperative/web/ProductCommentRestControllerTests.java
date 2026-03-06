package com.cmis.cooperative.web;

import com.cmis.cooperative.ProductApplicationTests;
import com.cmis.cooperative.model.dto.CommentRequestDto;
import com.cmis.cooperative.model.dto.ProductRequestDto;
import io.restassured.http.ContentType;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static io.restassured.RestAssured.given;

public class ProductCommentRestControllerTests extends ProductApplicationTests {

    @Test
    public void testCreateProductAndComment() {
        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .name("Bathroom Mirror")
                .description("Test Product description")
                .price(new BigDecimal(240.00))
                .ownerPublicId(UUID.fromString("2d7c44c0-3e3c-415d-b4e3-feb799f41e04"))
                .build();

        String productPublicId = given()
                .contentType(ContentType.JSON)
                .auth()
                .oauth2(accessToken)
                .body(productRequestDto).log().all()
                .post("/products")
                .then().log().all()
                .statusCode(200)
                .extract().path("publicId");

        CommentRequestDto commentRequestDto = CommentRequestDto
                .builder()
                .comment("This my first comment")
                .build();

        given()
                .contentType(ContentType.JSON)
                .auth()
                .oauth2(accessToken)
                .body(commentRequestDto).log().all()
                .post("/productComments/{productId}", productPublicId)
                .then().log().all()
                .statusCode(200);
    }
}

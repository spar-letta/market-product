package com.cmis.cooperative.web;

import com.cmis.cooperative.ProductApplicationTests;
import com.cmis.cooperative.model.dto.ProductRequestDto;
import io.restassured.http.ContentType;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class ProductRestControllerTests extends ProductApplicationTests {

    @Test
    public void testCreateProduct() {
        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .name("Bathroom Mirror")
                .description("Test Product description")
                .price(new BigDecimal(240.00))
                .build();

        given()
                .contentType(ContentType.JSON)
                .auth()
                .oauth2(accessToken)
                .body(productRequestDto).log().all()
                .post("/products")
                .then().log().all()
                .statusCode(200);

        given()
                .contentType(ContentType.JSON)
                .auth()
                .oauth2(accessToken)
                .body(productRequestDto)
                .post("/products")
                .then()
                .statusCode(400);

        given()
                .auth()
                .oauth2(accessToken)
                .get("/products")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    public void testCreateProductAndDoCRUD() {
        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .name("Tea cup")
                .description("Tea Cup description")
                .price(new BigDecimal(210.00))
                .build();

        String productPublicId = given()
                .contentType(ContentType.JSON)
                .auth()
                .oauth2(accessToken)
                .body(productRequestDto)
                .post("/products")
                .then().log().all()
                .statusCode(200)
                .extract().path("publicId");


        productRequestDto = ProductRequestDto.builder()
                .name("Tea cup modified")
                .description("Tea Cup description modified")
                .price(new BigDecimal(110))
                .build();

        given()
                .contentType(ContentType.JSON)
                .auth()
                .oauth2(accessToken)
                .body(productRequestDto)
                .put("/products/{publicId}", UUID.fromString(productPublicId))
                .then().log().all()
                .statusCode(200)
                .body("name", equalToIgnoringCase(productRequestDto.name()))
                .body("description", equalToIgnoringCase(productRequestDto.description()))
                .body("price", equalTo(110));

        given()
                .auth()
                .oauth2(accessToken)
                .get("/products/{productPublicId}", UUID.fromString(productPublicId))
                .then().log().all()
                .statusCode(200);
    }

    @Test
    public void testCreateProductAndAddLike() {
        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .name("Laptop charger")
                .description("laptop description")
                .price(new BigDecimal(6900))
                .build();

        String productPublicId = given()
                .contentType(ContentType.JSON)
                .auth()
                .oauth2(accessToken)
                .body(productRequestDto)
                .post("/products")
                .then()
                .statusCode(200)
                .extract().path("publicId");

        given()
                .auth()
                .oauth2(accessToken)
                .get("/products/{productPublicId}", UUID.fromString(productPublicId))
                .then().log().all()
                .statusCode(200);


        given()
                .contentType(ContentType.JSON)
                .auth()
                .oauth2(accessToken)
                .post("/products/{publicId}/makeLike", UUID.fromString(productPublicId))
                .then()
                .statusCode(200);

        given()
                .auth()
                .oauth2(accessToken)
                .get("/products/{publicId}/makeLike", UUID.fromString(productPublicId))
                .then().log().all()
                .statusCode(200)
                .body("isLiked", equalTo(true))
                .body("count", greaterThanOrEqualTo(1));
    }

}

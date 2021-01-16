package simpleTests;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import model.SingleUserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;


public class SimpleTests {

    @BeforeEach
    void beforeEach() {
        baseURI = "https://reqres.in";
    }

    @Test
    public void mainPageTest() {
        given()
                .when()
                .get(baseURI)
                .then()
                .statusCode(200);
    }

    @Test
    public void getUserNameTest() {
        given()
                .when()
                .get(baseURI + "/api/users/2")
                .then()
                .log().body()
                .statusCode(200)
                .body("data.first_name", is("Janet"));
    }

    @Test
    public void listResourceTest() {
        given().when()
                .get(baseURI + "/api/unknown")
                .then().log().body()
                .statusCode(200)
                .body("per_page", is(6));
    }

    @Test
    public void singleResourceTest() {
        given().when()
                .get(baseURI + "/api/unknown/2")
                .then()
                .log().body()
                .spec(specResource);
    }

    @Test
    public void singleUserAsModelTest() {
       SingleUserModel singleUserValue = given().when()
                .get(baseURI + "/api/unknown/2")
                .then()
                .log().body()
                .statusCode(200)
                .extract().as(SingleUserModel.class);



        System.out.println(singleUserValue.toString() + "---- Model");


    }

    private final ResponseSpecification specResource = new ResponseSpecBuilder()
            .expectBody("data.id", is(2))
            .expectBody("data.name", is("fuchsia rose"))
            .expectBody("data.year", is(2001))
            .expectBody("data.color", is("#C74375"))
            .expectBody("data.pantone_value", is("17-2031"))
            .expectStatusCode(200)
            .build();
}
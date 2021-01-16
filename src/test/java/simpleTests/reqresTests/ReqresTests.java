package simpleTests.reqresTests;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static Helpers.ApiHelpers.getToken;
import static Utils.FileUtils.readStringFromFile;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;


public class ReqresTests {
    @BeforeEach
    void beforeEach() {
        RestAssured.baseURI = "https://reqres.in";
    }

    //    POST
    @Test
    public void successLoginTest() {
        String data = readStringFromFile("src/test/resources/user_creat_login.json");

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("/api/login")
                .then()
                .statusCode(200)
                .log().body()
                .body("token", is(notNullValue()));
    }

    @Test
    public void successGetUserToken() {
        String userToken = getToken();
        System.out.println(userToken);
    }

//    GET

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


    private final ResponseSpecification specResource = new ResponseSpecBuilder()
            .expectBody("data.id", is(2))
            .expectBody("data.name", is("fuchsia rose"))
            .expectBody("data.year", is(2001))
            .expectBody("data.color", is("#C74375"))
            .expectBody("data.pantone_value", is("17-2031"))
            .expectStatusCode(200)
            .build();

    //    PUT
    @Test
    public void updateUserInfoTest() {
        String data = readStringFromFile("src/test/resources/user_update.json");

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .put("/api/users/2")
                .then()
                .statusCode(200)
                .log().body()
                .body("name", is(notNullValue()));

    }


}


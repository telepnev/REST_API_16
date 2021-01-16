package Helpers;

import io.restassured.http.ContentType;

import static Utils.FileUtils.readStringFromFile;
import static io.restassured.RestAssured.given;


public class ApiHelpers {

    public static String getToken() {
        String data = readStringFromFile("src/test/resources/user_creat_login.json");
        return given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("/api/login")
                .then()
                .statusCode(200)
                .log().body()
                .extract()
                .path("token");
    }
}

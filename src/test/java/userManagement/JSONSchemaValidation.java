package userManagement;

import core.StatusCode;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class JSONSchemaValidation {

    @Test
    public void jsonSchemaValidation() {
        File schema = new File(System.getProperty("user.dir")+"/resources/ExpectedSchema.json");
        given()
                .header("x-api-key", "reqres-free-v1")
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .assertThat()
                .statusCode(StatusCode.SUCCESS.code)
                .body(JsonSchemaValidator.matchesJsonSchema(schema));


    }
}

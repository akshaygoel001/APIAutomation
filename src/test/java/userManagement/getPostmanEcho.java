package userManagement;

import core.StatusCode;
import io.restassured.response.Response;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;
import utils.JSONReader;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class getPostmanEcho {

    @Test(description = "Validate the status code for GET users endpoint")
    public void validateResponseBodyGetBasicAuth() {

        Response response = given()
                .auth()
                .basic("postman", "password")
                .when()
                .get("https://postman-echo.com/basic-auth"); //RestAssured

        int actualStatusCode = response.statusCode();  //RestAssured
        assertEquals(actualStatusCode, StatusCode.SUCCESS.code); //Testng
        System.out.println(response.body().asString());

    }

    @Test()
    public void validateResponseBodyGetDigestAuth() {

        Response resp = given()
                .auth()
                .digest("postman", "password")
                .when()
                .get("https://postman-echo.com/digest-auth"); //RestAssured

        int actualStatusCode = resp.statusCode();  //RestAssured
        assertEquals(actualStatusCode, StatusCode.SUCCESS.code); //Testng
        System.out.println(resp.body().asString());
    }

    @Test(groups = "SmokeSuite")
    public void validateWithTestDataFromJson() throws IOException, ParseException {
        String username = JSONReader.getTestData("username");
        String password = JSONReader.getTestData("password");
        System.out.println("username from json is: " + username + "\npassword from json is: " + password);
        Response resp = given()
                .auth()
                .basic(username, password)
                .when()
                .get("https://postman-echo.com/basic-auth"); //RestAssured

        int actualStatusCode = resp.statusCode();  //RestAssured
        assertEquals(actualStatusCode, 201); //Testng
        System.out.println("validateWithTestDataFromJson executed successfully");
    }
}

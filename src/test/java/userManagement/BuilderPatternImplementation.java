package userManagement;

import core.StatusCode;
import core.ContentType;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;
import utils.JSONReader;
import utils.PropertyReader;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class BuilderPatternImplementation {

    private RequestSpecification requestSpec;
    private ResponseSpecification responseSpecification;


    @Test
    public void testRestAssuredNormalApproach() throws IOException, ParseException {
        String endpointB = JSONReader.getTestData("endpointB");
        String queryParamB = JSONReader.getTestData("queryParamB");
        RestAssured.baseURI = PropertyReader.propertyReader("config.properties","serverB");
        Response response = given().
                contentType(ContentType.APPLICATION_JSON.type)
                .queryParam("userId",queryParamB)
                .when()
                .get(endpointB)
                .then()
                .assertThat()
                .statusCode(StatusCode.SUCCESS.code)
                .extract().response();
        System.out.println(response.getBody().asString());
    }

    @Test
    public void testRestAssuredBuilderPattern() throws IOException, ParseException {
        String endpointB = JSONReader.getTestData("endpointB");
        String queryParamB = JSONReader.getTestData("queryParamB");
        requestSpec = getRequestSpec(queryParamB, ContentType.APPLICATION_JSON.type);

        Response response = given()
                .spec(requestSpec)
                .when()
                .get(endpointB)
                .then()
                .spec(getResponseSpecification(StatusCode.SUCCESS.code,ContentType.APPLICATION_JSON.type))
                .extract().response();
        System.out.println(response.getBody().asString());
    }

    private RequestSpecification getRequestSpec(String queryParam, String contentType){
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(PropertyReader.propertyReader("config.properties","serverB"))
                .setContentType(contentType)
                .addQueryParam("userId", queryParam)
                .build();
        return requestSpec;
    }

    private ResponseSpecification getResponseSpecification(int statusCode, String contentType) {
        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .expectContentType(contentType)
                .build();

        return responseSpecification;

    }


}

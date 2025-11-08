package userManagement;

import core.ContentType;
import core.StatusCode;
import io.restassured.response.Response;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;
import pojo.CityRequest;
import pojo.PostRequestBody;
import utils.JSONReader;
import utils.PropertyReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.put;
import static org.testng.AssertJUnit.assertEquals;

public class postUsers {

    private static FileInputStream fileInputStreamMethod(String requestBodyFile){
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(
                    new File(System.getProperty("user.dir") +
                            "/resources/testData/"+requestBodyFile));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return fileInputStream;
    }

    @Test(description = "validatePostWithString")
    public void validatePostWithString() {

        Response response = given().
                 header("x-api-key", JSONReader.getTestData("header"))
                .header("Content-Type", ContentType.APPLICATION_JSON.type)
                .body("{\"name\":\"aks\",\"job\":\"QA Auto\"}")
                .when()
                .post(PropertyReader.propertyReader("config.properties","server") + JSONReader.getTestData("endpoint"))
                .then()
                .extract().response();
        assertEquals(response.getStatusCode(), StatusCode.CREATED.code);
        System.out.println(response.getBody().asString());
        System.out.println("validatePostWithString executed successfully");
    }

    @Test
    public void validatePutWithString() {

        Response response = given().
                header("x-api-key", "reqres-free-v1")
                .header("Content-Type", "application/json")
                .body("{\"name\":\"akshay\",\"job\":\"QA Automation\"}")
                .when()
                .put("https://reqres.in/api/users/431")
                .then()
                .extract().response();
        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        System.out.println(response.getBody().asString());
        System.out.println("validatePutWithString executed successfully");
    }

    @Test
    public void validatePatchWithString() {

        Response response = given().
                header("x-api-key", "reqres-free-v1")
                .header("Content-Type", "application/json")
                .body("{\"name\":\"akkkk\"}")
                .when()
                .patch("https://reqres.in/api/users/431")
                .then()
                .extract().response();
        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        System.out.println(response.getBody().asString());
        System.out.println("validatePatchWithString executed successfully");
    }

    @Test
    public void validatePostWithJSONFile() throws IOException {

        Response response = given().
                header("x-api-key", "reqres-free-v1")
                .header("Content-Type", "application/json")
                .body(IOUtils.toString(fileInputStreamMethod("postRequestData.json")))
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .extract().response();
        assertEquals(response.getStatusCode(), StatusCode.CREATED.code);
        System.out.println(response.getBody().asString());
        System.out.println("validatePostWithJSONFile executed successfully");
    }

    @Test
    public void validatePatchWithJSONFile() throws IOException {

        Response response = given().
                header("x-api-key", "reqres-free-v1")
                .header("Content-Type", "application/json")
                .body(IOUtils.toString(fileInputStreamMethod("patchRequestData.json")))
                .when()
                .patch("https://reqres.in/api/users/431")
                .then()
                .extract().response();
        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        System.out.println(response.getBody().asString());
        System.out.println("validatePatchWithJSONFile executed successfully");
    }

    @Test
    public void validatePutWithJSONFile() throws IOException {

        Response response = given().
                header("x-api-key", "reqres-free-v1")
                .header("Content-Type", "application/json")
                .body(IOUtils.toString(fileInputStreamMethod("putRequestData.json")))
                .when()
                .put("https://reqres.in/api/users/431")
                .then()
                .extract().response();
        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        System.out.println(response.getBody().asString());
        System.out.println("validatePutWithJSONFile executed successfully");
    }

    @Test
    public void validatePostWithPOJO() throws IOException {
        PostRequestBody postRequest = new PostRequestBody();
        postRequest.setJob("QA");
        postRequest.setName("morpheus");
        Response response = given().
                header("x-api-key", "reqres-free-v1")
                .header("Content-Type", "application/json")
                .body(postRequest)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .extract().response();
        assertEquals(response.getStatusCode(), StatusCode.CREATED.code);
        System.out.println(response.getBody().asString());
        System.out.println("validatePostWithPOJO executed successfully");
    }

    @Test
    public void validatePostWithPOJOList() throws IOException {
        List<String> languagesList = new ArrayList<>();
        languagesList.add("Java");
        languagesList.add("Python");
        PostRequestBody postRequest = new PostRequestBody();
        postRequest.setJob("QA");
        postRequest.setName("morpheus");
        postRequest.setLanguages(languagesList);
        Response response = given().
                header("x-api-key", "reqres-free-v1")
                .header("Content-Type", "application/json")
                .body(postRequest)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .extract().response();
        assertEquals(response.getStatusCode(), StatusCode.CREATED.code);
        System.out.println(response.getBody().asString());
        System.out.println("validatePostWithPOJOList executed successfully");
    }

    @Test
    public void validatePutWithPOJO(){
        PostRequestBody putRequest = new PostRequestBody();
        putRequest.setJob("QA automation");
        putRequest.setName("akshay");
        Response response = given().
                header("x-api-key", "reqres-free-v1")
                .header("Content-Type", "application/json")
                .body(putRequest)
                .when()
                .put("https://reqres.in/api/users/396")
                .then()
                .extract().response();
        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        System.out.println(response.getBody().asString());
        System.out.println("validatePutWithPOJO executed successfully");
    }

    @Test
    public void validatePatchWithPOJO(){
        PostRequestBody patchRequest = new PostRequestBody();
        patchRequest.setJob("Test architect");
        Response response = given().
                header("x-api-key", "reqres-free-v1")
                .header("Content-Type", "application/json")
                .body(patchRequest)
                .when()
                .patch("https://reqres.in/api/users/396")
                .then()
                .extract().response();
        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        System.out.println(response.getBody().asString());
        System.out.println("validatePatchWithJSONFile executed successfully");
    }

    @Test
    public void validatePostWithPOJOListObject() throws IOException {
        List<String> languagesList = new ArrayList<>();
        languagesList.add("Java");
        languagesList.add("Python");
        CityRequest cityRequest1 = new CityRequest();
        cityRequest1.setName("Bangalore");
        cityRequest1.setTemprature("30");
        CityRequest cityRequest2 = new CityRequest();
        cityRequest2.setName("Delhi");
        cityRequest2.setTemprature("35");
        List<CityRequest> city = new ArrayList();
        city.add(cityRequest1);
        city.add(cityRequest2);
        PostRequestBody postRequest = new PostRequestBody();
        postRequest.setJob("QA");
        postRequest.setName("morpheus");
        postRequest.setLanguages(languagesList);
        postRequest.setCity(city);

        Response response = given().
                header("x-api-key", "reqres-free-v1")
                .header("Content-Type", "application/json")
                .body(postRequest)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .extract().response();
        assertEquals(response.getStatusCode(), StatusCode.CREATED.code);
        System.out.println(response.getBody().asString());
        System.out.println("validatePostWithPOJOListObject executed successfully");
    }

    @Test
    public void validatePatchWithResponsePOJO(){
        PostRequestBody patchRequest = new PostRequestBody();
        patchRequest.setJob("Test architect");
        Response response = given().
                header("x-api-key", "reqres-free-v1")
                .header("Content-Type", "application/json")
                .body(patchRequest)
                .when()
                .patch("https://reqres.in/api/users/431")
                .then()
                .extract().response();
        PostRequestBody responseBody = response.as(PostRequestBody.class);
        System.out.println("Job: "+responseBody.getJob());
        System.out.println("Updated At: "+responseBody.getUpdatedAt());
        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        assertEquals(responseBody.getJob(), "Test architect");
        System.out.println(response.getBody().asString());
        System.out.println("validatePatchWithResponsePOJO executed successfully");
    }

    @Test
    public void validatePostWithResponsePOJOListObject() throws IOException {
        List<String> languagesList = new ArrayList<>();
        languagesList.add("Java");
        languagesList.add("Python");
        CityRequest cityRequest1 = new CityRequest();
        cityRequest1.setName("Bangalore");
        cityRequest1.setTemprature("30");
        CityRequest cityRequest2 = new CityRequest();
        cityRequest2.setName("Delhi");
        cityRequest2.setTemprature("35");
        List<CityRequest> city = new ArrayList();
        city.add(cityRequest1);
        city.add(cityRequest2);
        PostRequestBody postRequest = new PostRequestBody();
        postRequest.setJob("QA");
        postRequest.setName("morpheus");
        postRequest.setLanguages(languagesList);
        postRequest.setCity(city);

        Response response = given().
                header("x-api-key", "reqres-free-v1")
                .header("Content-Type", "application/json")
                .body(postRequest)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .extract().response();
        PostRequestBody responseBody = response.as(PostRequestBody.class);
        System.out.println(responseBody.getCity().get(0).getName());
        System.out.println(responseBody.getCity().get(0).getTemprature());
        System.out.println(responseBody.getLanguages());
        assertEquals(response.getStatusCode(), StatusCode.CREATED.code);
        System.out.println(response.getBody().asString());
        System.out.println("validatePostWithPOJOListObject executed successfully");
    }
}

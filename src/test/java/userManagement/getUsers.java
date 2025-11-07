package userManagement;

import core.BaseTest;
import core.StatusCode;
import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.ExtentReport;
import utils.JSONReader;
import utils.PropertyReader;
import utils.SoftAssertionUtil;

import java.io.IOException;
import java.util.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

public class getUsers extends BaseTest {

    @Test
    public void getUserData() {

        given()
                .header("x-api-key", "reqres-free-v1")
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .assertThat()
                .statusCode(StatusCode.SUCCESS.code);
    }

    @Test
    public void validateResponseBody() {

        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/1")
                .then()
                .assertThat()
                .statusCode(StatusCode.SUCCESS.code)
                .body(not(isEmptyString()))
                .body("title", equalTo("delectus aut autem"))
                .body("userId", equalTo(1));
    }

    @Test
    public void validateResponseHasItems() {
        // Set base URI for the API
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Send a GET request and store the response in a variable
        Response response = given()
                .when()
                .get("/posts")
                .then()
                .statusCode(StatusCode.SUCCESS.code)
                .extract()
                .response();

        // Use Hamcrest to check that the response body contains specific items
        assertThat(response.jsonPath().getList("title"), hasItems("eum et est occaecati", "qui est esse"));
    }

    @Test
    public void validateResponseHasSize() {
        // Set base URI for the API
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Send a GET request and store the response in a variable
        Response response = given()
                .when()
                .get("/comments")
                .then()
                .extract()
                .response();

        // Use Hamcrest to check that the response body has a specific size
        assertThat(response.jsonPath().getList(""), hasSize(500));
    }

    @Test
    public void validateListContainsItems() {
        // Set base URI for the API
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Send a GET request and store the response in a variable
        Response response = given()
                .when()
                .get("/users")
                .then()
                .extract()
                .response();

        // Use Hamcrest to check that the response body contains specific items
        List<String> expectedNames = Arrays.asList("Leanne Graham", "Ervin Howell", "Clementine Bauch");
        assertThat(response.jsonPath().getList("name"), hasItems(expectedNames.toArray(new String[0])));
    }

    @Test
    public void validateListContainsInOrder() {
        // Set base URI for the API
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Send a GET request and store the response in a variable
        Response response = given()
                .when()
                .get("/comments?postId=1")
                .then()
                .extract()
                .response();

        // Use Hamcrest to check that the response body contains specific items in a specific order
        List<String> expectedEmails = Arrays.asList("Eliseo@gardner.biz", "Jayne_Kuhic@sydney.com", "Nikita@garfield.biz", "Lew@alysha.tv", "Hayden@althea.biz");
        assertThat(response.jsonPath().getList("email"), contains(expectedEmails.toArray()));
    }

    @Test
    public void testGetUsersWithQueryParameters() {
        RestAssured.baseURI = "https://reqres.in";
        Response response = given()
                .header("x-api-key", "reqres-free-v1")
                .queryParam("page", 2)
                .when()
                .get("/api/users")
                .then()
                .statusCode(StatusCode.SUCCESS.code)
                .extract()
                .response();

        // Assert that the response contains 6 users
        response.then().body("data", hasSize(6));

        // Assert that the first user in the list has the correct values
        response.then().body("data[0].id", is(7));
        response.then().body("data[0].email", equalTo("michael.lawson@reqres.in"));
        response.then().body("data[0].first_name", is("Michael"));
        response.then().body("data[0].last_name", is("Lawson"));
        response.then().body("data[0].avatar", is("https://reqres.in/img/faces/7-image.jpg"));
    }

    @Test(description = "Validate the status code for GET users endpoint")
    public void validateStatusCodeGetUser() {

        //System.out.println("*****************" + BaseTestHelper.propertyReader("qaBaseUrl"));


        //System.out.println("URI is ******:" + uri);

        Response resp =
                given()
                        .queryParam("page", 2)
                        .when()
                        .get("https://reqres.in"); //RestAssured

        int actualStatusCode = resp.statusCode();  //RestAssured
        assertEquals(actualStatusCode, StatusCode.SUCCESS.code); //Testng
    }

    @Test
    public void testGetUsersWithMultipleQueryParams() {
        Response response =
                given()
                        .header("x-api-key", "reqres-free-v1")
                        .queryParam("page", 2)
                        .queryParam("per_page", 3)
                        .queryParam("rtqsdr", 4)
                        .when()
                        .get("https://reqres.in/api/users")
                        .then()
                        .statusCode(StatusCode.SUCCESS.code)
                        .extract()
                        .response();
    }

    @Test(enabled = false, description = "Validate the status code for GET users endpoint")
    public void validateResponseBodyGetPathParam() {

        String raceSeasonValue = "2017";
        Response resp = given()
                .pathParam("raceSeason", raceSeasonValue)
                .when()
                .get("http://ergast.com/api/f1/{raceSeason}/circuits.json"); //RestAssured

        int actualStatusCode = resp.statusCode();  //RestAssured
        assertEquals(actualStatusCode, StatusCode.SUCCESS.code); //Testng
        System.out.println(resp.body().asString());

    }

    @Test(enabled = false)
    public void testCreateUserWithFormParam() {
        RestAssured.baseURI = "https://reqres.in/api";
        Response response = given()
                .header("x-api-key", "reqres-free-v1")
                .contentType("application/x-www-form-urlencoded")
                .formParam("name", "John Doe")
                .formParam("job", "Developer")
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .extract()
                .response();

        // Assert that the response contains the correct name and job values
        response.then().body("name", equalTo("John Doe"));
        response.then().body("job", equalTo("Developer"));
    }

    @Test
    public void testGetUserListWithHeader() {
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(StatusCode.SUCCESS.code);
        System.out.println("testGetUserListWithHeader Executed Successfully");
    }

    @Test
    public void testWithTwoHeaders() {
        given()
                .header("Authorization", "bearer ywtefdu13tx4fdub1t3ygdxuy3gnx1iuwdheni1u3y4gfuy1t3bx")
                .header("Content-Type", "application/json")
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(StatusCode.SUCCESS.code);
        System.out.println("testWithTwoHeaders Executed Successfully");
    }

    @Test
    public void testMultipleHeaderWithMap() {
        // Set base URI for the API
        RestAssured.baseURI = "https://reqres.in/api";

        // Create a Map to hold headers
        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer werxhqb98rpaxn39848xrunpaw3489ruxnpa98w4rxn");

        // Send a GET request with headers
        given()
                .headers(headers)
                .when()
                .get("/users?page=2")
                .then()
                .statusCode(StatusCode.SUCCESS.code)
                .body("page", equalTo(2))
                .body("data[0].first_name", equalTo("Michael"))
                .body("data[0].last_name", equalTo("Lawson"));
    }

    @Test
    public void testFetchHeaders() {
        Response response = given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .extract().response();

        Headers headers = response.getHeaders();
        for (Header header : headers) {
            if (header.getName().contains("Server")) {
                System.out.println(header.getName() + " : " + header.getValue());
                assertEquals(header.getValue(), "cloudflare");
                System.out.println("testFetchHeaders Executed Successfully");
            }
        }
    }

    @Test
    public void testUseCookies() {
        Cookie cookies = new Cookie.Builder("cookie1", "cookie1")
                .setComment("using cookie key")
                .build();
        RestAssured.baseURI = "https://reqres.in";
        given()
                .header("x-api-key", "reqres-free-v1")
                .cookie(cookies)
                .when()
                .get("/api/users?page=2")
                .then()
                .statusCode(StatusCode.SUCCESS.code);
        System.out.println("testUseCookies executed successfully");
    }

    @Test
    public void testFetchCookies() {

        RestAssured.baseURI = "https://reqres.in";
        Response response = given()
                .header("x-api-key", "reqres-free-v1")
                .when()
                .get("/api/users?page=2")
                .then()
                .statusCode(StatusCode.SUCCESS.code)
                .extract().response();
        Map<String, String> cookies = response.getCookies();
        System.out.println(cookies);
        System.out.println("testFetchCookies executed successfully");
    }

    @Test(description = "validate 204 for Delete user", groups = {"RegressionSuite", "B_User"})
    public void verifyStatusCodeDelete() {
        ExtentReport.extentlog =
                ExtentReport.extentreport.
                        startTest("verifyStatusCodeDelete", "Validate 204 status code for DELETE Method");
        Response resp = given()
                .header("x-api-key", "reqres-free-v1")
                .when()
                .delete("https://reqres.in/api/users/2");
        assertEquals(resp.getStatusCode(), StatusCode.NO_CONTENT.code);
        System.out.println("verifyStatusCodeDelete executed successfully");

    }


    @Test(groups = "RegressionSuite")
    public void validateUserDataFromProptiesFile() {
        ExtentReport.extentlog = ExtentReport.extentreport.
                startTest("validateUserDataFromProptiesFile","Validate 200 status code for GET Method");
        given()
                .header("x-api-key", "reqres-free-v1")
                .when()
                .get(PropertyReader.propertyReader("config.properties", "server"))
                .then()
                .assertThat()
                .statusCode(StatusCode.SUCCESS.code);
        System.out.println("validateUserDataFromProptiesFile executed successfully");

    }

    @Test
    public void validateUserDataFromPropties_TestData() throws IOException, ParseException {
        String endpoint = JSONReader.getTestData("endpoint");

        Response response = given()
                .header("x-api-key", "reqres-free-v1")
                .when()
                .get(PropertyReader.propertyReader("config.properties", "server") + endpoint)
                .then()
                .assertThat()
                .statusCode(StatusCode.SUCCESS.code)
                .extract().response();
        System.out.println(response.body().asString());
        System.out.println("validateUserDataFromPropties_TestData executed successfully");

    }

    @Test
    public void hardAssertion() {
        System.out.println("hardAssert");
        Assert.assertTrue(false);
        System.out.println("hardAssert");

    }

    @Test
    public void softAssertion() {
        System.out.println("softAssert");
        SoftAssertionUtil.assertTrue(false,"");
        SoftAssertionUtil.assertTrue(true,"");
        SoftAssertionUtil.assertAll();
    }

    @Test
    public void validateWithSoftAssertUtil() {
        RestAssured.baseURI = "https://reqres.in/api";
        Response response = given()
                .queryParam("page", 2)
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract()
                .response();

        SoftAssertionUtil.assertEquals(response.getStatusCode(), StatusCode.NO_CONTENT.code, "Status code is not 200");
        SoftAssertionUtil.assertAll();
        System.out.println("validateWithSoftAssertUtil executed successfully");
    }

    @DataProvider(name = "testdata")
    public Object[][] testData() {
        return new Object[][]{
                {"1", "John"},
                {"2", "Jane"},
                {"3", "Bob"}
        };
    }

    @Test(dataProvider = "testdata")
    @Parameters({"id", "name"})
    public void testEndpoint(String id, String name) {
        given()
                .header("x-api-key", "reqres-free-v1")
                .param("id", id)
                .param("name", name)
                .when()
                .get("https://reqres.in/api/users")
                .then()
                .statusCode(200);
    }


    @Test
    public void getJSONData() throws IOException, ParseException {
        System.out.println(JSONReader.getJSONArrayData("languages", 0));

        JSONArray contact = JSONReader.getJSONArrayData("contact");
        Iterator<String> iterator = contact.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }


}

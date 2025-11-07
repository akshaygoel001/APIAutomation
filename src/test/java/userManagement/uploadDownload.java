package userManagement;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class uploadDownload {

    @Test
    public void FileUploadExample(){
        File file = new File("resources/demo.txt");
        RestAssured.baseURI = "";
        Response response = given()
                .multiPart("file",file)
                .when()
                .post("https://postman-echo.com/post");
        System.out.println(response.getStatusCode());
    }
}

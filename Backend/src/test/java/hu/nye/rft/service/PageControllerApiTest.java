package hu.nye.rft.service;

import static io.restassured.RestAssured.request;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;


public class PageControllerApiTest {

    private static final String BASE_URL = "http://localhost:8080";

    private static final String USER_NAME = "userName";


    @Test
    public void When_GetSubjectsPageIsRequested_Expect_StatusOk() {
        Response response = request("get", BASE_URL + "/addsubjects/" + USER_NAME);

        response.then()
                .statusCode(200);
    }

    @Test
    public void When_LoginPageIsRequested_Expect_StatusOk() {
        Response response = request("get", BASE_URL + "/login");

        response.then()
                .statusCode(200);
    }



    @Test
    public void When_TakeSubjectsIsRequested_StatusOK() {
        Response response = request("get", BASE_URL + "/takesubject/" + USER_NAME);

        response.then()
                .statusCode(200);
    }
}
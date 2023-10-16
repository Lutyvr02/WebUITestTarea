package Tarea;

import config.Configuration;
import factoryRequest.IRequest;
import factoryRequest.RequestInfo;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CreateUser implements IRequest {
    @Override
    public Response send(RequestInfo requestInfo) {
        Response response=given().body(requestInfo.getBody()).log().all().
                when().post(requestInfo.getUrl());

        response.then().log().all();
        return response;
    }
}
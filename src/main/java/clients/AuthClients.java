package clients;

import static io.restassured.RestAssured.*;

import endpoints.AuthEndpoint;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.request.AuthRequest;

public class AuthClients {

    public Response auth(AuthRequest request) {

        return given()
                .contentType(ContentType.JSON)
                .body(request)
               .when()
                .post(AuthEndpoint.AUTH);

    }

}
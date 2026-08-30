package clients;

import static io.restassured.RestAssured.*;

import config.APIConfig;
import endpoints.AuthEndpoint;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.request.AuthRequest;

public class AuthClients {
	
	private RequestSpecification requestSpec;
	
	public AuthClients()
	{
		requestSpec = APIConfig.getRequestSpec();
	}
	
    public Response auth(AuthRequest request) {

        return given()
        		.spec(requestSpec)
                .body(request)
               .when()
                .post(AuthEndpoint.AUTH);

    }

}
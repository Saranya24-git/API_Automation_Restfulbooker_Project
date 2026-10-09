package clients;

import static io.restassured.RestAssured.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import config.APIConfig;
import endpoints.AuthEndpoint;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.request.AuthRequest;

public class AuthClients {
	
	private RequestSpecification requestSpec;
	private static final Logger logger =
	        LogManager.getLogger(GetBookingClient.class);
	public AuthClients()
	{
		requestSpec = APIConfig.getRequestSpec();
	}
	
    public Response auth(AuthRequest request) {

    	logger.info(
	            "Authenticating the request");
    	
        return given()
        		.spec(requestSpec)
                .body(request)
               .when()
                .post(AuthEndpoint.AUTH);

    }

}
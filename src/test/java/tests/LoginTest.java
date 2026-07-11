package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import endpoints.AuthEndpoint;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import models.request.AuthRequest;

public class LoginTest extends BaseTest
{
	@Test
	public void verifyUserCanLoginSuccessfully() {
	AuthRequest auth = new AuthRequest("admin",
            "password123");
	
	Response response=

	       given()

	            .contentType(ContentType.JSON)

	            .body(auth)

	        .when()

	            .post(AuthEndpoint.AUTH)

	        .then()

	            .extract()

	            .response();
	
	 System.out.println(response.getStatusCode());
     System.out.println(response.asPrettyString());

	        Assert.assertEquals(response.statusCode(),200);

	        Assert.assertNotNull(response.jsonPath().getString("token"));
	       
	}
}
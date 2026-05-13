package api.tests;

//import static org.testng.Assert.assertEquals;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetUsersTest
{
	@Test
	public void getUsersTest()
	{
		Response response = RestAssured
				.given()
				.when()
				.get("https://jsonplaceholder.typicode.com/users");
				
		System.out.println("Response Body");
		System.out.println(response.getBody().asPrettyString());
		
		System.out.println("Response status code :" + response.getStatusCode());
		
		Assert.assertEquals(response.getStatusCode(),200);
		
		System.out.println("API test passed");
	}
}
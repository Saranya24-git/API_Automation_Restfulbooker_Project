package tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import clients.AuthClients;
import io.restassured.response.Response;
import models.request.AuthRequest;

public class LoginTest extends BaseTest
{
	@Test(enabled=false)
	public void TC01_verifyUserCanLoginSuccessfully() {
	AuthRequest auth = new AuthRequest("admin",
            "password123");
	
	Response response= new AuthClients().auth(auth);
		System.out.println(response.getStatusCode());
		System.out.println(response.asPrettyString());
		Assert.assertEquals(response.statusCode(),200);
        Assert.assertNotNull(response.jsonPath().getString("token"));	       
	}
	
	@Test(enabled=false)
	public void TC02_loginwithInvalidUsername()
	{
		AuthRequest auth = new AuthRequest("asdf",
	            "password123");
		Response response= new AuthClients().auth(auth);
			       
				System.out.println(response.getStatusCode());
				System.out.println(response.asPrettyString());
				Assert.assertEquals(response.statusCode(),200);
		        Assert.assertEquals( response.jsonPath().getString("reason"),
		                "Bad credentials");	
	}
	@Test(enabled=false)
	public void TC03_loginwithInvalidPassword()
	{
		AuthRequest auth = new AuthRequest("admin",
	            "aadsf");
		Response response= new AuthClients().auth(auth);
			       
				System.out.println(response.getStatusCode());
				System.out.println(response.asPrettyString());
				Assert.assertEquals(response.statusCode(),200);
		        Assert.assertEquals( response.jsonPath().getString("reason"),
		                "Bad credentials");	
	}
	
	@Test(enabled=false)
	public void TC04_loginwithInvalidUsernameAndPassword()
	{
		AuthRequest auth = new AuthRequest("asdf",
	            "aadsf");
		Response response= new AuthClients().auth(auth);
			       
				System.out.println(response.getStatusCode());
				System.out.println(response.asPrettyString());
				Assert.assertEquals(response.statusCode(),200);
		        Assert.assertEquals( response.jsonPath().getString("reason"),
		                "Bad credentials");	
	}
	
	@Test(enabled=false)
	public void TC05_loginMissingUsername()
	{
		AuthRequest auth = new AuthRequest(null,null);
		Map<String, Object> request = new HashMap<>();
		request.put("password", "password123");
		Response response= new AuthClients().auth(auth);
			       
				System.out.println(response.getStatusCode());
				System.out.println(response.asPrettyString());
				Assert.assertEquals(response.statusCode(),200);
		        Assert.assertEquals(response.jsonPath().getString("reason"),
		                "Bad credentials");	
	}
	
	@Test(enabled=false)
	public void TC06_loginMissingPassword()
	{
		AuthRequest auth = new AuthRequest(null,null);
		Map<String, Object> request = new HashMap<>();
		request.put("username", "admin");
		Response response= new AuthClients().auth(auth);
			       
				System.out.println(response.getStatusCode());
				System.out.println(response.asPrettyString());
				Assert.assertEquals(response.statusCode(),200);
		        Assert.assertEquals(response.jsonPath().getString("reason"),
		                "Bad credentials");	
	}
	
}
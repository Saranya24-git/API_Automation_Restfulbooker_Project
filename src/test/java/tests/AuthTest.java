package tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import annotations.TestDataSheet;
import base.BaseTest;
import clients.AuthClients;
import constants.APIConstants;
import dataProviders.TestDataProvider;
import datamodels.AuthData;
import io.restassured.response.Response;
import models.request.AuthRequest;

@TestDataSheet(sheetName = "Auth",  model=AuthData.class)

public class AuthTest extends BaseTest
{
	@Test(enabled=true, dataProvider = "TestData", dataProviderClass = TestDataProvider.class)
	public void TC01_verifyUserCanLoginSuccessfully(AuthData data) {
	AuthRequest auth = new AuthRequest(data.getUsername(),data.getPassword());
	Response response= new AuthClients().auth(auth);
		System.out.println(response.getStatusCode());
		System.out.println(response.asPrettyString());
		Assert.assertEquals(response.statusCode(),200);
        Assert.assertNotNull(response.jsonPath().getString("token"));	       
	}
	
	@Test(enabled=true, dataProvider = "TestData", dataProviderClass = TestDataProvider.class)
	public void TC02_loginwithInvalidUsername(AuthData data)
	{
		AuthRequest auth = new AuthRequest(data.getUsername(), data.getPassword());
		Response response= new AuthClients().auth(auth);			       
		System.out.println(response.getStatusCode());
		System.out.println(response.asPrettyString());
		Assert.assertEquals(response.statusCode(),200);
		Assert.assertEquals(response.jsonPath().getString("reason"), APIConstants.BAD_CREDENTIALS);	
	}
	
	@Test(enabled=true, dataProvider = "TestData", dataProviderClass = TestDataProvider.class)
	public void TC03_loginwithInvalidPassword(AuthData data)
	{
		AuthRequest auth = new AuthRequest(data.getUsername(),data.getPassword());
		Response response= new AuthClients().auth(auth);
		System.out.println(response.getStatusCode());
		System.out.println(response.asPrettyString());
		Assert.assertEquals(response.statusCode(),200);
		Assert.assertEquals( response.jsonPath().getString("reason"), APIConstants.BAD_CREDENTIALS);	
	}
	
	@Test(enabled=true, dataProvider = "TestData", dataProviderClass = TestDataProvider.class)
	public void TC04_loginwithInvalidUsernameAndPassword(AuthData data)
	{
		AuthRequest auth = new AuthRequest(data.getUsername(),data.getPassword());
		Response response= new AuthClients().auth(auth);
		System.out.println(response.getStatusCode());
		System.out.println(response.asPrettyString());
		Assert.assertEquals(response.statusCode(),200);
		Assert.assertEquals( response.jsonPath().getString("reason"),APIConstants.BAD_CREDENTIALS);	
	}
	
	@Test(enabled=true)
	public void TC05_loginMissingUsername()
	{
		AuthRequest auth = new AuthRequest(null,null);
		Map<String, Object> request = new HashMap<>();
		request.put("password", "password123");
		Response response= new AuthClients().auth(auth);
		System.out.println(response.getStatusCode());
		System.out.println(response.asPrettyString());
		Assert.assertEquals(response.statusCode(),200);
		Assert.assertEquals(response.jsonPath().getString("reason"),APIConstants.BAD_CREDENTIALS);	
	}
	
	@Test(enabled=true)
	public void TC06_loginMissingPassword()
	{
		AuthRequest auth = new AuthRequest(null,null);
		Map<String, Object> request = new HashMap<>();
		request.put("username", "admin");
		Response response= new AuthClients().auth(auth);
		System.out.println(response.getStatusCode());
		System.out.println(response.asPrettyString());
		Assert.assertEquals(response.statusCode(),200);
		Assert.assertEquals(response.jsonPath().getString("reason"),APIConstants.BAD_CREDENTIALS);	
	}
	
	@Test(enabled=true, dataProvider = "TestData", dataProviderClass = TestDataProvider.class)
	public void TC07_ValidateResponseHeaders(AuthData data)
	{
		AuthRequest auth = new AuthRequest(data.getUsername(),data.getPassword());
		Response response= new AuthClients().auth(auth);
		Assert.assertEquals(response.statusCode(), 200);
		 // Content-Type
        Assert.assertEquals(response.getHeader("Content-Type"),APIConstants.HEADER_CONTENT_TYPE_JSON);

        // Server
        Assert.assertNotNull(response.getHeader("Server"));

        // Date
        Assert.assertNotNull(response.getHeader("Date"));

        // Content-Length
        Assert.assertTrue(
                Integer.parseInt(response.getHeader("Content-Length")) > 0);

        System.out.println(response.getHeaders());
	}
	
}
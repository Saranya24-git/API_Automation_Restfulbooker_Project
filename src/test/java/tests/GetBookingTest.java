package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import clients.GetBookingClient;
import constants.APIConstants;
import io.restassured.response.Response;
import models.request.GetBookingRequest;
import models.response.GetBookingResponse;

public class GetBookingTest extends BaseTest
{
	@Test(enabled=false)
	public void TC22_GetExistingBookingWithValidID()
	{
		GetBookingRequest request = new GetBookingRequest("1");
		GetBookingClient getBookingClient = new GetBookingClient();
		Response response = getBookingClient.getBooking(request);
		GetBookingResponse bookingResponse = response.as(GetBookingResponse.class);
		System.out.println(response.asPrettyString());
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(response.getContentType(),APIConstants.HEADER_CONTENT_TYPE_JSON);
		Assert.assertNotNull(bookingResponse);
		Assert.assertNotNull(bookingResponse.getFirstname());
		Assert.assertNotNull(bookingResponse.getLastname());
		Assert.assertNotNull(bookingResponse.getTotalprice());
		Assert.assertNotNull(bookingResponse.getDepositpaid());
		Assert.assertNotNull(bookingResponse.getBookingdates());
	}
	
	@Test(enabled=false)
	public void TC23_GetExistingBookingWithInvalidID()
	{
		GetBookingRequest request = new GetBookingRequest("asdf");
		GetBookingClient getBookingClient = new GetBookingClient();
		Response response = getBookingClient.getBooking(request);
		System.out.println(response.asPrettyString());
		Assert.assertEquals(response.statusCode(), APIConstants.STATUS_CODE_NOT_FOUND);
		Assert.assertEquals(response.asString(), APIConstants.NOT_FOUND);		
	}
	
	@Test(enabled=false)
	public void TC24_GetExistingBookingWithNonExistingBookingID()
	{
		GetBookingRequest request = new GetBookingRequest("999999");
		GetBookingClient getBookingClient = new GetBookingClient();
		Response response = getBookingClient.getBooking(request);
		System.out.println(response.asPrettyString());
		Assert.assertEquals(response.statusCode(), 404);
		Assert.assertEquals(response.asString(), APIConstants.NOT_FOUND);	
	
	}
}
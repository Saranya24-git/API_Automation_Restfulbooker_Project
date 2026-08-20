package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import annotations.TestDataSheet;
import base.BaseTest;
import clients.GetBookingClient;
import constants.APIConstants;
import dataProviders.TestDataProvider;
import datamodels.GetBookingData;
import io.restassured.response.Response;
import models.request.GetBookingRequest;
import models.response.GetBookingResponse;

@TestDataSheet(sheetName="GetBooking", model=GetBookingData.class)

public class GetBookingTest extends BaseTest
{
	@Test(enabled=true, dataProvider = "TestData", dataProviderClass = TestDataProvider.class)
	public void TC22_GetExistingBookingWithValidID(GetBookingData data)
	{
		GetBookingRequest request = new GetBookingRequest(data.getBookingID());
		GetBookingClient getBookingClient = new GetBookingClient();
		Response response = getBookingClient.getBooking(request);
		System.out.println(response.asPrettyString());
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(response.getContentType(),APIConstants.HEADER_CONTENT_TYPE_JSON);
		GetBookingResponse bookingResponse = response.as(GetBookingResponse.class);		
		Assert.assertNotNull(bookingResponse);
		Assert.assertNotNull(bookingResponse.getFirstname());
		Assert.assertNotNull(bookingResponse.getLastname());
		Assert.assertNotNull(bookingResponse.getTotalprice());
		Assert.assertNotNull(bookingResponse.getDepositpaid());
		Assert.assertNotNull(bookingResponse.getBookingdates());
	}
	
	@Test(enabled=true, dataProvider = "TestData", dataProviderClass = TestDataProvider.class)
	public void TC23_GetExistingBookingWithInvalidID(GetBookingData data)
	{
		GetBookingRequest request = new GetBookingRequest(data.getBookingID());
		GetBookingClient getBookingClient = new GetBookingClient();
		Response response = getBookingClient.getBooking(request);
		System.out.println(response.asPrettyString());
		Assert.assertEquals(response.statusCode(), APIConstants.STATUS_CODE_NOT_FOUND);
		Assert.assertEquals(response.asString(), APIConstants.NOT_FOUND);		
	}
	
	@Test(enabled=true, dataProvider = "TestData", dataProviderClass = TestDataProvider.class)
	public void TC24_GetExistingBookingWithNonExistingBookingID(GetBookingData data)
	{
		GetBookingRequest request = new GetBookingRequest(data.getBookingID());
		GetBookingClient getBookingClient = new GetBookingClient();
		Response response = getBookingClient.getBooking(request);
		System.out.println(response.asPrettyString());
		Assert.assertEquals(response.statusCode(), 404);
		Assert.assertEquals(response.asString(), APIConstants.NOT_FOUND);	
	
	}
}
package tests;

import java.time.LocalDate;

import org.testng.Assert;
import org.testng.annotations.Test;

import annotations.TestDataSheet;
import clients.AuthClients;
import clients.GetBookingClient;
import clients.UpdateBookingClient;
import dataProviders.TestDataProvider;
import datamodels.UpdateBookingData;
import io.restassured.response.Response;
import models.request.AuthRequest;
import models.request.BookingDates;
import models.request.GetBookingRequest;
import models.request.UpdateBookingRequest;
import models.response.GetBookingResponse;
import models.response.UpdateBookingResponse;
import utils.ConfigReader;

@TestDataSheet(sheetName="UpdateBooking", model=UpdateBookingData.class)
public class UpdateBookingTest 
{
	@Test(enabled=false, dataProvider = "TestData", dataProviderClass = TestDataProvider.class)
	public void TC25_UpdateBookingWithValidData(UpdateBookingData data)
	{
		/*	For a valid update:
		  	1. Authenticate with valid username/password
        	↓
			2. Get valid token
        	↓
			3. Get existing booking using booking ID
        	↓
			4. Build valid update request
        	↓
			5. PUT /booking/{id} + valid token
        	↓
			6. Validate successful update*/
		// 1. Authenticate
		String username = ConfigReader.get("username");
		String password = ConfigReader.get("password");
		AuthRequest auth = new AuthRequest(username, password);
		Response authResponse = new AuthClients().auth(auth);
		String token = authResponse.jsonPath().getString("token");
		Assert.assertNotNull(token);
		 // 2. Retrieve existing booking
		GetBookingRequest request = new GetBookingRequest(data.getBookingID());
		GetBookingClient getBookingClient = new GetBookingClient();
	    Response getResponse = getBookingClient.getBooking(request);
	    Assert.assertEquals(getResponse.statusCode(),200);
	    GetBookingResponse existingBooking = getResponse.as(GetBookingResponse.class);
	    // 3. Create complete update request
	    UpdateBookingRequest updaterequest = new UpdateBookingRequest();
	    // Existing values that are NOT being changed
	    updaterequest.setLastname(existingBooking.getLastname());
	    updaterequest.setDepositpaid(existingBooking.getDepositpaid());
	    // Values that ARE being changed
	    updaterequest.setfirstname(data.getFirstname());
	    updaterequest.setTotalprice(data.getTotalprice());
	    updaterequest.setAdditionalneeds(data.getAdditionalneeds());
	    // 4. Booking dates
	    BookingDates bookingDates = new BookingDates();
	    bookingDates.setCheckin(LocalDate.parse(existingBooking.getBookingdates().getCheckin()));
	    bookingDates.setCheckout(data.getCheckout());
	    updaterequest.setBookingdates(bookingDates);
	    // 5. Send PUT
	    UpdateBookingClient updateBookingClient = new UpdateBookingClient();
	    Response updateResponse = updateBookingClient.updateBooking(data.getBookingID(),token,updaterequest);
	    // 6. Validate response
	    Assert.assertEquals(updateResponse.statusCode(),200);
	    System.out.println(updateResponse.asPrettyString());
	    UpdateBookingResponse updatedBooking = updateResponse.as(UpdateBookingResponse.class);	    
	    // 7. Validate the four updated fields
	    Assert.assertEquals(updatedBooking.getFirstname(),data.getFirstname());
	    Assert.assertEquals(updatedBooking.getTotalprice(),data.getTotalprice());
	    Assert.assertEquals(updatedBooking.getBookingdates().getCheckout(),data.getCheckout().toString());
	    Assert.assertEquals(updatedBooking.getAdditionalneeds(),data.getAdditionalneeds());
	}
	
	@Test(enabled=false, dataProvider = "TestData", dataProviderClass = TestDataProvider.class)
	public void TC26_UpdateBookingWithInValidToken(UpdateBookingData data)
	{
		/*
		 * 1. Get existing booking
        	↓
			2. Build valid update request
        	↓
			3. PUT /booking/{id} + INVALID token
        	↓
			4. Validate authentication failure*/
		 // 1. Create a valid request for the update
		GetBookingRequest request = new GetBookingRequest(data.getBookingID());
		GetBookingClient getBookingClient = new GetBookingClient();
	    Response getResponse = getBookingClient.getBooking(request);
	    Assert.assertEquals(getResponse.statusCode(),200);
	    GetBookingResponse existingBooking = getResponse.as(GetBookingResponse.class);
	    UpdateBookingRequest updaterequest = new UpdateBookingRequest();
	    // Existing values that are NOT being changed
	    updaterequest.setLastname(existingBooking.getLastname());
	    updaterequest.setDepositpaid(existingBooking.getDepositpaid());
	    // Values that ARE being changed
	    updaterequest.setfirstname(data.getFirstname());
	    updaterequest.setTotalprice(data.getTotalprice());
	    updaterequest.setAdditionalneeds(data.getAdditionalneeds());
	    // 4. Booking dates
	    BookingDates bookingDates = new BookingDates();
	    bookingDates.setCheckin(LocalDate.parse(existingBooking.getBookingdates().getCheckin()));
	    bookingDates.setCheckout(data.getCheckout());
	    updaterequest.setBookingdates(bookingDates);
	    // 5. Deliberately use an invalid token
	    String invalidToken = "invalidToken123";
	    // 6. Send PUT
	    UpdateBookingClient updateBookingClient = new UpdateBookingClient();
	    Response updateResponse = updateBookingClient.updateBooking(data.getBookingID(),invalidToken,updaterequest);
	    // 7. Validate authentication failure
	    System.out.println("Status Code : " + updateResponse.statusCode());
	    System.out.println("Response Body: " + updateResponse.asString());

	    Assert.assertEquals(updateResponse.statusCode(), 403,"Expected authentication failure for invalid token");	    
	    
	}
	
	@Test(enabled=false, dataProvider = "TestData", dataProviderClass = TestDataProvider.class)
	public void TC27_UpdateBookingWithoutToken(UpdateBookingData data)
	{
		 // 1. Create a valid request for the update
		GetBookingRequest request = new GetBookingRequest(data.getBookingID());
		GetBookingClient getBookingClient = new GetBookingClient();
	    Response getResponse = getBookingClient.getBooking(request);
	    Assert.assertEquals(getResponse.statusCode(),200);
	    GetBookingResponse existingBooking = getResponse.as(GetBookingResponse.class);
	    UpdateBookingRequest updaterequest = new UpdateBookingRequest();
	    // Existing values that are NOT being changed
	    updaterequest.setLastname(existingBooking.getLastname());
	    updaterequest.setDepositpaid(existingBooking.getDepositpaid());
	    // Values that ARE being changed
	    updaterequest.setfirstname(data.getFirstname());
	    updaterequest.setTotalprice(data.getTotalprice());
	    updaterequest.setAdditionalneeds(data.getAdditionalneeds());
	    // 4. Booking dates
	    BookingDates bookingDates = new BookingDates();
	    bookingDates.setCheckin(LocalDate.parse(existingBooking.getBookingdates().getCheckin()));
	    bookingDates.setCheckout(data.getCheckout());
	    updaterequest.setBookingdates(bookingDates);
	    // 6. Send PUT
	    UpdateBookingClient updateBookingClient = new UpdateBookingClient();
	    Response updateResponse = updateBookingClient.updateBooking(data.getBookingID(),updaterequest);
	    // 7. Validate authentication failure
	    System.out.println("Status Code : " + updateResponse.statusCode());
	    System.out.println("Response Body: " + updateResponse.asString());

	    Assert.assertEquals(updateResponse.statusCode(), 403,"Expected authentication failure for without token request");
	}
	
	@Test(enabled=true, dataProvider = "TestData", dataProviderClass = TestDataProvider.class)
	public void TC28_UpdateBookingWithInValidBookingID(UpdateBookingData data)
	{
				// 1. Authenticate
				String username = ConfigReader.get("username");
				String password = ConfigReader.get("password");
				AuthRequest auth = new AuthRequest(username, password);
				Response authResponse = new AuthClients().auth(auth);
				String token = authResponse.jsonPath().getString("token");
				Assert.assertNotNull(token);
				// 2. Create complete update request
			    UpdateBookingRequest updaterequest = new UpdateBookingRequest();
			    // 3. Existing values that are NOT being changed
			    updaterequest.setLastname("K");
			    updaterequest.setDepositpaid(true);
			    // 4. Values that ARE being changed
			    updaterequest.setfirstname(data.getFirstname());
			    updaterequest.setTotalprice(data.getTotalprice());
			    updaterequest.setAdditionalneeds(data.getAdditionalneeds());
			    // 5. Booking dates
			    BookingDates bookingDates = new BookingDates();
			    bookingDates.setCheckin(LocalDate.of(2026, 10, 5));
			    bookingDates.setCheckout(data.getCheckout());
			    updaterequest.setBookingdates(bookingDates);
			    // 6. Send PUT
			    UpdateBookingClient updateBookingClient = new UpdateBookingClient();
			    Response updateResponse = updateBookingClient.updateBooking(data.getBookingID(),token,updaterequest);
			    // 7. Validate response
			    Assert.assertEquals(updateResponse.statusCode(),405);
			    System.out.println(updateResponse.asPrettyString());
			    
	}
}

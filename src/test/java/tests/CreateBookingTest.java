package tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import annotations.TestDataSheet;
import clients.BookingClient;
import constants.APIConstants;
import dataProviders.TestDataProvider;
import datamodels.CreateBookingData;
import io.restassured.response.Response;
import models.request.BookingDates;
import models.request.BookingRequest;
import models.response.BookingResponse;

@TestDataSheet(sheetName = "CreateBooking",  model=CreateBookingData.class)

public class CreateBookingTest 
{
	@Test(enabled=true, dataProvider = "TestData", dataProviderClass = TestDataProvider.class)
	public void TC08_CreateBooking(CreateBookingData data)
	{
		BookingDates bookingDates = new BookingDates(data.getCheckin(),data.getCheckout());
		BookingRequest request = new BookingRequest(data.getFirstname(), data.getLastname(),data.getTotalprice(),data.isDepositpaid(),bookingDates,data.getAdditionalneeds());
		BookingClient bookingClient = new BookingClient();
		Response response = bookingClient.createBooking(request);
		System.out.println(response.asPrettyString());
	    Assert.assertEquals(response.statusCode(),APIConstants.STATUS_CODE_OK);    	
	}
	
	@Test(enabled=true, dataProvider = "TestData", dataProviderClass = TestDataProvider.class)
	public void TC09_VerifyBookingIDGenerated(CreateBookingData data)
	{
		BookingDates bookingDates = new BookingDates(data.getCheckin(),data.getCheckout());
		BookingRequest request = new BookingRequest(data.getFirstname(), data.getLastname(),data.getTotalprice(),data.isDepositpaid(),bookingDates,data.getAdditionalneeds());
		BookingClient bookingClient = new BookingClient();
		Response response = bookingClient.createBooking(request);
		BookingResponse bookingResponse = response.as(BookingResponse.class);
		System.out.println(response.asPrettyString());
		Assert.assertTrue(bookingResponse.getBookingid() > 0, APIConstants.BOOKING_ID_SHOULD_BE_GREATER_THAN_ZERO);		
	}
	
	@Test(enabled=true, dataProvider = "TestData", dataProviderClass = TestDataProvider.class)
	public void TC10_VerifyResponseBody(CreateBookingData data)
	{
		BookingDates bookingDates = new BookingDates(data.getCheckin(),data.getCheckout());
		BookingRequest request = new BookingRequest(data.getFirstname(), data.getLastname(),data.getTotalprice(),data.isDepositpaid(),bookingDates,data.getAdditionalneeds());
		BookingClient bookingClient = new BookingClient();
		Response response = bookingClient.createBooking(request);
		BookingResponse bookingResponse = response.as(BookingResponse.class);
		System.out.println(response.asPrettyString());
		Assert.assertEquals(bookingResponse.getBooking().getFirstname(),"Saranya");
		Assert.assertEquals(bookingResponse.getBooking().getLastname(),"K");
		Assert.assertEquals(bookingResponse.getBooking().getTotalprice(),2500);
		Assert.assertEquals(bookingResponse.getBooking().getDepositpaid(),true);
		Assert.assertEquals(bookingResponse.getBooking().getBookingdates().getCheckin(),bookingDates.getCheckin().toString());
		Assert.assertEquals(bookingResponse.getBooking().getBookingdates().getCheckout(),bookingDates.getCheckout().toString());
		Assert.assertEquals(bookingResponse.getBooking().getAdditionalneeds(),"Breakfast");		
	}
	
	@Test(enabled=true, dataProvider = "TestData", dataProviderClass = TestDataProvider.class)
	public void TC11_VerifyResponseHeaders(CreateBookingData data)
	{
		BookingDates bookingDates = new BookingDates(data.getCheckin(),data.getCheckout());
		BookingRequest request = new BookingRequest(data.getFirstname(), data.getLastname(),data.getTotalprice(),data.isDepositpaid(),bookingDates,data.getAdditionalneeds());
		BookingClient bookingClient = new BookingClient();
		Response response = bookingClient.createBooking(request);
		System.out.println(response.getHeaders());
		Assert.assertEquals(response.getHeader("Content-Type"),APIConstants.HEADER_CONTENT_TYPE_JSON);
		Assert.assertNotNull(response.getHeader("Server"));
		Assert.assertNotNull(response.getHeader("Date"));
		Assert.assertNotNull(response.getHeader("Content-Length"));
	}
	
	@Test(enabled=true, dataProvider = "TestData", dataProviderClass = TestDataProvider.class)
	public void TC12_CreateBookingWithEmptyFirstName(CreateBookingData data)
	{
		BookingDates bookingDates = new BookingDates(data.getCheckin(),data.getCheckout());
		BookingRequest request = new BookingRequest(data.getFirstname(), data.getLastname(),data.getTotalprice(),data.isDepositpaid(),bookingDates,data.getAdditionalneeds());
		BookingClient bookingClient = new BookingClient();
		Response response = bookingClient.createBooking(request);
		BookingResponse bookingResponse = response.as(BookingResponse.class);
		System.out.println(response.asPrettyString());
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(bookingResponse.getBooking().getFirstname(), "");		
	}
	
	@Test(enabled=true)
	public void TC13_CreateBookingWithoutFirstName_ShouldReturnServerError()
	{
		BookingDates bookingDates = new BookingDates("2026-08-01", "2026-08-10");
		BookingRequest request = new BookingRequest();
		request.setLastname("K");
		request.setTotalprice(2500);
		request.setDepositpaid(true);
		request.setBookingdates(bookingDates);
		request.setAdditionalneeds("Breakfast");
		BookingClient bookingClient = new BookingClient();
		Response response = bookingClient.createBooking(request);
		System.out.println(response.statusCode());
		System.out.println(response.getContentType());
		System.out.println(response.asString());
		/*"According to REST principles, I expected a 400 Bad Request for an invalid request. 
		 * However, Restful Booker returned a 500 Internal Server Error with a text/plain response. 
		 * My test validates the actual API behavior while documenting it as a server-side issue."*/
		Assert.assertEquals(response.statusCode(), 500);
		Assert.assertEquals(response.getContentType(),APIConstants.HEADER_CONTENT_TYPE_PLAIN);
		Assert.assertEquals(response.asString(),APIConstants.INTERNAL_SERVER_ERROR);
	}
	
	@Test(enabled=true, dataProvider = "TestData", dataProviderClass = TestDataProvider.class)
	public void TC14_CreateBookingWithEmptyLastName(CreateBookingData data)
	{
		BookingDates bookingDates = new BookingDates(data.getCheckin(),data.getCheckout());
		BookingRequest request = new BookingRequest(data.getFirstname(), data.getLastname(),data.getTotalprice(),data.isDepositpaid(),bookingDates,data.getAdditionalneeds());
		BookingClient bookingClient = new BookingClient();
		Response response = bookingClient.createBooking(request);
		BookingResponse bookingResponse = response.as(BookingResponse.class);
		System.out.println(response.asPrettyString());
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(bookingResponse.getBooking().getLastname(), "");
		
		
	}
	
	@Test(enabled=true)
	public void TC15_CreateBookingWithoutLastName_ShouldReturnServerError()
	{
		BookingDates bookingDates = new BookingDates("2026-08-01", "2026-08-10");
		BookingRequest request = new BookingRequest();
		request.setfirstname("Saranya");
		request.setTotalprice(2500);
		request.setDepositpaid(true);
		request.setBookingdates(bookingDates);
		request.setAdditionalneeds("Breakfast");
		BookingClient bookingClient = new BookingClient();
		Response response = bookingClient.createBooking(request);
		System.out.println(response.statusCode());
		System.out.println(response.getContentType());
		System.out.println(response.asString());
		/*"According to REST principles, I expected a 400 Bad Request for an invalid request. 
		 * However, Restful Booker returned a 500 Internal Server Error with a text/plain response. 
		 * My test validates the actual API behavior while documenting it as a server-side issue."*/
		Assert.assertEquals(response.statusCode(), 500);
		Assert.assertEquals(response.getContentType(),APIConstants.HEADER_CONTENT_TYPE_PLAIN);
		Assert.assertEquals(response.asString(),APIConstants.INTERNAL_SERVER_ERROR);
	}
	
	@Test(enabled=true)
	public void TC16_CreateBookingWithEmptyBookingDates()
	{
		Map<String, Object> bookingDates = new HashMap<>();
	    bookingDates.put("checkin", "");
	    bookingDates.put("checkout", "");
	    Map<String, Object> request = new HashMap<>();
	    request.put("firstname", "Saranya");
	    request.put("lastname", "K");
	    request.put("totalprice", 2500);
	    request.put("depositpaid", true);
	    request.put("bookingdates", bookingDates);
	    request.put("additionalneeds", "Breakfast");
		BookingClient bookingClient = new BookingClient();
		Response response = bookingClient.createBooking(request);
		BookingResponse bookingResponse = response.as(BookingResponse.class);
		System.out.println(response.asPrettyString());
		Assert.assertEquals(response.statusCode(), 200);
		/*"My test validates the API's actual behavior. 
		 * Although I would expect the API to reject empty dates with a 400 Bad Request, 
		 * Restful Booker accepts the request and returns 0NaN-aN-aN for the date fields. 
		 * I documented this as a defect or limitation of the demo API rather than forcing my test to expect ideal behavior."*/
		Assert.assertEquals(bookingResponse.getBooking().getBookingdates().getCheckin(), "0NaN-aN-aN");
		Assert.assertEquals(bookingResponse.getBooking().getBookingdates().getCheckout(), "0NaN-aN-aN");		
	}
	
	@Test(enabled=true)
	public void TC17_CreateBookingWithoutBookingDates_ShouldReturnServerError()
	{
		BookingRequest request = new BookingRequest();
		request.setfirstname("Saranya");
		request.setLastname("K");
		request.setTotalprice(2500);
		request.setDepositpaid(true);
		request.setAdditionalneeds("Breakfast");
		BookingClient bookingClient = new BookingClient();
		Response response = bookingClient.createBooking(request);
		System.out.println(response.statusCode());
		System.out.println(response.getContentType());
		System.out.println(response.asString());
		/*"According to REST principles, I expected a 400 Bad Request for an invalid request. 
		 * However, Restful Booker returned a 500 Internal Server Error with a text/plain response. 
		 * My test validates the actual API behavior while documenting it as a server-side issue."*/
		Assert.assertEquals(response.statusCode(), 500);
		Assert.assertEquals(response.getContentType(),APIConstants.HEADER_CONTENT_TYPE_PLAIN);
		Assert.assertEquals(response.asString(),APIConstants.INTERNAL_SERVER_ERROR);
	}
	
	@Test(enabled=true)
	public void TC18_CreateBookingWithEmptyRequestBody()
	{
		BookingClient bookingClient = new BookingClient();
		Response response = bookingClient.createBookingWithEmptyBody();
		System.out.println(response.statusCode());
	    System.out.println(response.getContentType());
	    System.out.println(response.asString());
		Assert.assertEquals(response.statusCode(), 500);
		Assert.assertEquals(response.getContentType(), APIConstants.HEADER_CONTENT_TYPE_PLAIN);
		Assert.assertEquals(response.asString(), APIConstants.INTERNAL_SERVER_ERROR);	
	}
	@Test(enabled=true)
	public void TC19_CreateBookingWithBlankRequestBody()
	{
		BookingClient bookingClient = new BookingClient();
		Response response = bookingClient.createBookingWithBlankBody();
		System.out.println(response.statusCode());
	    System.out.println(response.getContentType());
	    System.out.println(response.asString());
		Assert.assertEquals(response.statusCode(), 500);
		Assert.assertEquals(response.getContentType(), APIConstants.HEADER_CONTENT_TYPE_PLAIN);
		Assert.assertEquals(response.asString(), APIConstants.INTERNAL_SERVER_ERROR);	
	}
	
	@Test(enabled=true)
	public void TC20_CreateBookingWithInvalidDataTypes()
	{
		String requestBody =
				"{"
				+ "\"firstname\":\"Saranya\","
				+ "\"lastname\":\"K\","
				+ "\"totalprice\":\"abc\","
				+ "\"depositpaid\":\"true\","
				+ "\"bookingdates\":{"
				+     "\"checkin\":\"2026-08-01\","
				+     "\"checkout\":\"2026-08-10\""
				+ "},"
				+ "\"additionalneeds\":\"Breakfast\""
				+ "}";
		
		BookingClient bookingClient = new BookingClient();
	    Response response = bookingClient.createBookingWithRawBody(requestBody);
	    BookingResponse bookingResponse = response.as(BookingResponse.class);
		System.out.println(response.statusCode());
	    System.out.println(response.getContentType());
	    System.out.println(response.asPrettyString());

	    /*"I expected the API to reject invalid data types with a 400 Bad Request, 
	     * but Restful Booker is a demo API and accepts the request. 
	     * My test documents the observed behavior rather than assuming ideal validation. 
	     * In a production API, I would expect strict validation and appropriate error responses."*/
	    Assert.assertEquals(response.statusCode(), 200);
	    Assert.assertTrue(bookingResponse.getBookingid() > 0);
	    Assert.assertNull(bookingResponse.getBooking().getTotalprice());
	    Assert.assertTrue(bookingResponse.getBooking().getDepositpaid());	  
	}
	
	@Test(enabled=true)
	public void TC21_CreateBookingWithMalformedJSON()
	{
		String malformedJson =
				"{"
				+ "\"firstname\":\"Saranya\""
				+ "\"lastname\":\"K\""
				+ "\"totalprice\":\"abc\""
				+ "\"depositpaid\":\"true\""
				+ "\"bookingdates\":{"
				+     "\"checkin\":\"2026-08-01\""
				+     "\"checkout\":\"2026-08-10\""
				+ "}"
				+ "\"additionalneeds\":\"Breakfast\""
				+ "}";
		BookingClient bookingClient = new BookingClient();
	    Response response = bookingClient.createBookingWithRawBody(malformedJson);
		System.out.println(response.statusCode());
	    System.out.println(response.getContentType());
	    System.out.println(response.asPrettyString());
	    Assert.assertEquals(response.statusCode(), 400);
	    Assert.assertEquals(response.getContentType(), APIConstants.HEADER_CONTENT_TYPE_PLAIN);
	    Assert.assertEquals(response.asString(), APIConstants.BAD_REQUEST);
	    
	}
}
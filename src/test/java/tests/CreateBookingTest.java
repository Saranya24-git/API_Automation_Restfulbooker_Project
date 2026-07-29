package tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import clients.BookingClient;
import io.restassured.response.Response;
import models.request.BookingDates;
import models.request.BookingRequest;
import models.response.BookingResponse;

public class CreateBookingTest extends BaseTest
{
	@Test(enabled=false)
	public void TC08_CreateBooking()
	{
		BookingDates bookingDates = new BookingDates("2026-08-01","2026-08-10");
		BookingRequest request = new BookingRequest("Saranya", "K",2500,true,bookingDates,"Breakfast");
		BookingClient bookingClient = new BookingClient();
		Response response = bookingClient.createBooking(request);
		System.out.println(response.asPrettyString());
	    Assert.assertEquals(response.statusCode(),200);    	
	}
	
	@Test(enabled=false)
	public void TC09_VerifyBookingIDGenerated()
	{
		BookingDates bookingDates = new BookingDates("2026-08-01" , "2026-08-10");
		BookingRequest request = new BookingRequest("Saranya", "K",2500,true,bookingDates,"Breakfast");
		BookingClient bookingClient = new BookingClient();
		Response response = bookingClient.createBooking(request);
		BookingResponse bookingResponse = response.as(BookingResponse.class);
		System.out.println(response.asPrettyString());
		Assert.assertTrue(bookingResponse.getBookingid() > 0, "Booking ID should be greater than 0");		
	}
	
	@Test(enabled=false)
	public void TC10_VerifyResponseBody()
	{
		BookingDates bookingDates = new BookingDates("2026-08-01", "2026-08-10");
		BookingRequest request = new BookingRequest("Saranya", "K",2500,true,bookingDates,"Breakfast");
		BookingClient bookingClient = new BookingClient();
		Response response = bookingClient.createBooking(request);
		BookingResponse bookingResponse = response.as(BookingResponse.class);
		System.out.println(response.asPrettyString());
		Assert.assertEquals(bookingResponse.getBooking().getfirstname(),"Saranya");
		Assert.assertEquals(bookingResponse.getBooking().getLastname(),"K");
		Assert.assertEquals(bookingResponse.getBooking().getTotalprice(),2500);
		Assert.assertEquals(bookingResponse.getBooking().isDepositpaid(),true);
		Assert.assertEquals(bookingResponse.getBooking().getBookingdates().getCheckin(),bookingDates.getCheckin());
		Assert.assertEquals(bookingResponse.getBooking().getBookingdates().getCheckout(),bookingDates.getCheckout());
		Assert.assertEquals(bookingResponse.getBooking().getAdditionalneeds(),"Breakfast");		
	}
	
	@Test(enabled=false)
	public void TC11_VerifyResponseHeaders()
	{
		BookingDates bookingDates = new BookingDates("2026-08-01", "2026-08-10");
		BookingRequest request = new BookingRequest("Saranya", "K",2500,true,bookingDates,"Breakfast");
		BookingClient bookingClient = new BookingClient();
		Response response = bookingClient.createBooking(request);
		System.out.println(response.getHeaders());
		Assert.assertEquals(response.getHeader("Content-Type"),"application/json; charset=utf-8");
		Assert.assertNotNull(response.getHeader("Server"));
		Assert.assertNotNull(response.getHeader("Date"));
		Assert.assertNotNull(response.getHeader("Content-Length"));
	}
	
	@Test(enabled=false)
	public void TC12_CreateBookingWithEmptyFirstName()
	{
		BookingDates bookingDates = new BookingDates("2026-08-01", "2026-08-10");
		BookingRequest request = new BookingRequest("", "K",2500,true,bookingDates,"Breakfast");
		BookingClient bookingClient = new BookingClient();
		Response response = bookingClient.createBooking(request);
		BookingResponse bookingResponse = response.as(BookingResponse.class);
		System.out.println(response.asPrettyString());
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(bookingResponse.getBooking().getfirstname(), "");		
	}
	
	@Test(enabled=false)
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
		Assert.assertEquals(response.getContentType(),"text/plain; charset=utf-8");
		Assert.assertEquals(response.asString(),"Internal Server Error");
	}
	
	@Test(enabled=false)
	public void TC14_CreateBookingWithEmptyLastName()
	{
		BookingDates bookingDates = new BookingDates("2026-08-01", "2026-08-10");
		BookingRequest request = new BookingRequest("Saranya", "",2500,true,bookingDates,"Breakfast");
		BookingClient bookingClient = new BookingClient();
		Response response = bookingClient.createBooking(request);
		BookingResponse bookingResponse = response.as(BookingResponse.class);
		System.out.println(response.asPrettyString());
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(bookingResponse.getBooking().getLastname(), "");
		
		
	}
	
	@Test(enabled=false)
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
		Assert.assertEquals(response.getContentType(),"text/plain; charset=utf-8");
		Assert.assertEquals(response.asString(),"Internal Server Error");
	}
	
	@Test(enabled=false)
	public void TC16_CreateBookingWithEmptyBookingDates()
	{
		BookingDates bookingDates = new BookingDates("", "");
		BookingRequest request = new BookingRequest("Saranya", "K",2500,true,bookingDates,"Breakfast");
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
	
	@Test(enabled=false)
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
		Assert.assertEquals(response.getContentType(),"text/plain; charset=utf-8");
		Assert.assertEquals(response.asString(),"Internal Server Error");
	}
	
	@Test(enabled=false)
	public void TC18_CreateBookingWithEmptyRequestBody()
	{
		BookingClient bookingClient = new BookingClient();
		Response response = bookingClient.createBookingWithEmptyBody();
		System.out.println(response.statusCode());
	    System.out.println(response.getContentType());
	    System.out.println(response.asString());
		Assert.assertEquals(response.statusCode(), 500);
		Assert.assertEquals(response.getContentType(), "text/plain; charset=utf-8");
		Assert.assertEquals(response.asString(), "Internal Server Error");	
	}
	@Test(enabled=false)
	public void TC19_CreateBookingWithBlankRequestBody()
	{
		BookingClient bookingClient = new BookingClient();
		Response response = bookingClient.createBookingWithBlankBody();
		System.out.println(response.statusCode());
	    System.out.println(response.getContentType());
	    System.out.println(response.asString());
		Assert.assertEquals(response.statusCode(), 500);
		Assert.assertEquals(response.getContentType(), "text/plain; charset=utf-8");
		Assert.assertEquals(response.asString(), "Internal Server Error");	
	}
	
	@Test(enabled=false)
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
	    Assert.assertTrue(bookingResponse.getBooking().isDepositpaid());	  
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
	    Assert.assertEquals(response.getContentType(), "text/plain; charset=utf-8");
	    Assert.assertEquals(response.asString(), "Bad Request");
	    
	}
}
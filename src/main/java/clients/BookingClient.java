package clients;

import static io.restassured.RestAssured.given;

import java.util.Map;

import config.APIConfig;
import endpoints.BookingEndPoint;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.request.BookingRequest;

public class BookingClient
{
	  private RequestSpecification requestSpec;
	  
	public BookingClient() {
		requestSpec = APIConfig.getRequestSpec();
    }
	
	public Response createBooking(BookingRequest bookingRequest)
	{
		return given()
				.spec(requestSpec)
				.body(bookingRequest)
				.log().body()
			.when()
				.post(BookingEndPoint.CREATE_BOOKING);
		
	}
	
	public Response createBooking(Map<String, Object> request)
	{
	    return given()
	    		.spec(requestSpec)
	            .body(request)
	        .when()
	            .post(BookingEndPoint.CREATE_BOOKING);
	}
	
	public Response createBookingWithEmptyBody() {

	    return given()
	    		.spec(requestSpec)
	            .body("{}")
	         .when()
	            .post(BookingEndPoint.CREATE_BOOKING);
	}
	
	public Response createBookingWithBlankBody() {

	    return given()
	    		.spec(requestSpec)
	            .body("")
	         .when()
	            .post(BookingEndPoint.CREATE_BOOKING);
	}
	
	public Response createBookingWithRawBody(String requestBody)
	{
	    return given()
	    		.spec(requestSpec)
	            .body(requestBody)
	    .when()
	            .post(BookingEndPoint.CREATE_BOOKING);
	}
	
}
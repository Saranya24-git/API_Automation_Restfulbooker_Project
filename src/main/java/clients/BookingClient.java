package clients;

import static io.restassured.RestAssured.given;
import endpoints.BookingEndPoint;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.request.BookingRequest;

public class BookingClient
{
	public Response createBooking(BookingRequest bookingRequest)
	{
		return given()
				.contentType(ContentType.JSON)
				.body(bookingRequest)
			.when()
				.post(BookingEndPoint.CREATE_BOOKING);
		
	}
	
	public Response createBookingWithEmptyBody() {

	    return given()
	            .contentType(ContentType.JSON)
	            .body("{}")
	         .when()
	            .post(BookingEndPoint.CREATE_BOOKING);
	}
	
	public Response createBookingWithBlankBody() {

	    return given()
	            .contentType(ContentType.JSON)
	            .body("")
	         .when()
	            .post(BookingEndPoint.CREATE_BOOKING);
	}
	
	public Response createBookingWithRawBody(String requestBody)
	{
	    return given()
	            .contentType(ContentType.JSON)
	            .body(requestBody)
	    .when()
	            .post(BookingEndPoint.CREATE_BOOKING);
	}
	
}
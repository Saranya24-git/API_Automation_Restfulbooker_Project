package clients;

import static io.restassured.RestAssured.given;

import endpoints.GetBookingEndPoint;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.request.GetBookingRequest;

public class GetBookingClient
{
	public Response getBooking(GetBookingRequest request)
	{
		return given()
				.contentType(ContentType.JSON)
				.pathParam("id", request.getId())
			.when()
				.get(GetBookingEndPoint.GET_BOOKING);
		
	}
}
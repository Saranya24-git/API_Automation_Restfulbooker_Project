package clients;

import static io.restassured.RestAssured.given;

import config.APIConfig;
import endpoints.UpdateBookingEndPoint;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.request.UpdateBookingRequest;

public class UpdateBookingClient
{
	private RequestSpecification requestSpec;
	 
	public UpdateBookingClient() {
		requestSpec = APIConfig.getRequestSpec();
	}
	public Response updateBooking(String bookingId, String token, UpdateBookingRequest request)
	{
		 return given()
				.spec(requestSpec)
				.cookie("token", token)
				.pathParam("id", bookingId)
				 .body(request)
			.when()
				.put(UpdateBookingEndPoint.UPDATE_BOOKING);
		
	}
	public Response updateBooking(String bookingId, UpdateBookingRequest request)
	{
		 return given()
				.spec(requestSpec)
				.pathParam("id", bookingId)
				 .body(request)
			.when()
				.put(UpdateBookingEndPoint.UPDATE_BOOKING);
		
	}
}
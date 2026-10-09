package clients;

import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import config.APIConfig;
import endpoints.UpdateBookingEndPoint;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.request.UpdateBookingRequest;

public class UpdateBookingClient
{
	private RequestSpecification requestSpec;
	
	private static final Logger logger =
	        LogManager.getLogger(UpdateBookingClient.class);
	 
	public UpdateBookingClient() {
		requestSpec = APIConfig.getRequestSpec();
	}
	public Response updateBooking(String bookingId, String token, UpdateBookingRequest request)
	{
		 logger.info(
		            "Updating booking ID: {}",
		            bookingId);

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
		 logger.info(
		            "Updating booking ID: {}",
		            bookingId);

		 return given()
				.spec(requestSpec)
				.pathParam("id", bookingId)
				 .body(request)
			.when()
				.put(UpdateBookingEndPoint.UPDATE_BOOKING);
		
	}
}
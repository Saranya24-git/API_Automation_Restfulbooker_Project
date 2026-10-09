package clients;

import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import config.APIConfig;
import endpoints.GetBookingEndPoint;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.request.GetBookingRequest;

public class GetBookingClient
{
	private RequestSpecification requestSpec;
	private static final Logger logger =
	        LogManager.getLogger(GetBookingClient.class);
	public GetBookingClient() {
		requestSpec = APIConfig.getRequestSpec();
	}
	public Response getBooking(GetBookingRequest request)
	{
		logger.info(
	            "Sending GET /booking/{}",
	            request.getId());

		 return given()
				.spec(requestSpec)
				.pathParam("id", request.getId())
			.when()
				.get(GetBookingEndPoint.GET_BOOKING);
		
	}
}
package clients;

import static io.restassured.RestAssured.given;

import config.APIConfig;
import endpoints.GetBookingEndPoint;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.request.GetBookingRequest;

public class GetBookingClient
{
	private RequestSpecification requestSpec;
	 
	public GetBookingClient() {
		requestSpec = APIConfig.getRequestSpec();
	}
	public Response getBooking(GetBookingRequest request)
	{
		 return given()
				.spec(requestSpec)
				.pathParam("id", request.getId())
			.when()
				.get(GetBookingEndPoint.GET_BOOKING);
		
	}
}
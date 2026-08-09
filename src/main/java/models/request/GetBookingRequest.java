package models.request;

public class GetBookingRequest
{
	private String id;
	
	public GetBookingRequest(String id)
	{
		this.id=id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		id = id;
	}
	
}
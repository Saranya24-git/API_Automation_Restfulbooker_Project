package models.request;

public class BookingRequest
{
	private String firstname;
	private String lastname;
	private Integer totalprice;
	private Boolean depositpaid;
	private BookingDates bookingdates;
	private String additionalneeds;
	
	public BookingRequest()
	{
		
	}
	
	public BookingRequest(String firstname, String lastname,int totalprice,boolean depositpaid,BookingDates bookingdates,String additionalneeds)
	{
		this.firstname=firstname;
        this.lastname=lastname;
        this.totalprice=totalprice;
        this.depositpaid=depositpaid;
        this.bookingdates=bookingdates;
        this.additionalneeds=additionalneeds;
	}
	
	public String getfirstname() {
		return firstname;
	}
	
	public void setfirstname(String firstname)
	{
		this.firstname=firstname;
	}
	
	public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Integer totalprice) {
        this.totalprice = totalprice;
    }

    public Boolean isDepositpaid() {
        return depositpaid;
    }

    public void setDepositpaid(Boolean depositpaid) {
        this.depositpaid = depositpaid;
    }

    public BookingDates getBookingdates() {
        return bookingdates;
    }

    public void setBookingdates(BookingDates bookingdates) {
        this.bookingdates = bookingdates;
    }

    public String getAdditionalneeds() {
        return additionalneeds;
    }

    public void setAdditionalneeds(String additionalneeds) {
        this.additionalneeds = additionalneeds;
    }
	
}
package datamodels;

import java.time.LocalDate;

public class UpdateBookingData
{
	private String BookingID;
	private String firstname;
    private Integer  totalprice;
    private LocalDate checkout;
    private String additionalneeds;

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public Integer getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(Integer totalprice) {
		this.totalprice = totalprice;
	}
	

	public LocalDate getCheckout() {
		return checkout;
	}

	public void setCheckout(LocalDate checkout) {
		this.checkout = checkout;
	}

	public String getAdditionalneeds() {
		return additionalneeds;
	}

	public void setAdditionalneeds(String additionalneeds) {
		this.additionalneeds = additionalneeds;
	}

	public String getBookingID() {
		return BookingID;
	}

	public void setBookingID(String bookingID) {
		BookingID = bookingID;
	}
	
}
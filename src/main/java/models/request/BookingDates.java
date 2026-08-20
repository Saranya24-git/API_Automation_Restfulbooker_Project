package models.request;

import java.time.LocalDate;

public class BookingDates{
	
	private LocalDate checkin;
	private LocalDate checkout;
	
	public BookingDates()
	{
	}
	
	public BookingDates(String checkin, String checkout) {
	    this.checkin = LocalDate.parse(checkin);
	    this.checkout = LocalDate.parse(checkout);
	}
	
	public BookingDates(LocalDate checkin, LocalDate checkout)
	{
		this.checkin = checkin;
		this.checkout = checkout;
	}
	
	public LocalDate getCheckin() {
        return checkin;
    }

    public void setCheckin(LocalDate checkin) {
        this.checkin = checkin;
    }

    public LocalDate getCheckout() {
        return checkout;
    }

    public void setCheckout(LocalDate checkout) {
        this.checkout = checkout;
    }
	
}
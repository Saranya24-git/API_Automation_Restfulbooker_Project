package models.response;

public class BookingResponse
{
	private int bookingid;
	private BookingDataResponse booking;
	
	public BookingResponse() {
    }
	
	public int getBookingid() {
        return bookingid;
    }

    public void setBookingid(int bookingid) {
        this.bookingid = bookingid;
    }
	
    public BookingDataResponse getBooking() {
		return booking;
	}

	public void setBooking(BookingDataResponse booking) {
		this.booking = booking;
	}	

    

	   
}
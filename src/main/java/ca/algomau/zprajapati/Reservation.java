package ca.algomau.zprajapati;

import lombok.Data;

@Data
public class Reservation {
    private Long reservationId;
    private String reservationDate;
    private String reservationTime;
    private int numberOfGuests;
    private String phoneNumber;
	public Long getReservationId() {
		return reservationId;
	}
	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}
	public String getReservationDate() {
		return reservationDate;
	}
	public void setReservationDate(String reservationDate) {
		this.reservationDate = reservationDate;
	}
	public String getReservationTime() {
		return reservationTime;
	}
	public void setReservationTime(String reservationTime) {
		this.reservationTime = reservationTime;
	}
	public int getNumberOfGuests() {
		return numberOfGuests;
	}
	public void setNumberOfGuests(int numberOfGuests) {
		this.numberOfGuests = numberOfGuests;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	@Override
	public String toString() {
		return "Reservation [reservationId=" + reservationId + ", reservationDate=" + reservationDate
				+ ", reservationTime=" + reservationTime + ", numberOfGuests=" + numberOfGuests + ", phoneNumber="
				+ phoneNumber + "]";
	}
    
}

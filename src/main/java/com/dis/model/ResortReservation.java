package com.dis.model;


import java.util.Date;

public class ResortReservation extends CommonProp{
	private long rReservationNumber;
	private String roomType;
	private Date arrivalDate;
	private Date departureDate;
	private long guestID;

	public long getGuestID() {
		return guestID;
	}

	public void setGuestID(long guestID) {
		this.guestID = guestID;
	}

	public long getrReservationNumber() {
		return rReservationNumber;
	}

	public void setrReservationNumber(long rReservationNumber) {
		this.rReservationNumber = rReservationNumber;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}
    
}
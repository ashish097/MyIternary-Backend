package com.dis.model;

import java.util.Date;

public class DiningReservation extends CommonProp{
	private long dReservationNumber;
	private String diningType;
	private Date arrivalDate;
	private long guestID;
	
	public long getdReservationNumber() {
		return dReservationNumber;
	}
	public void setdReservationNumber(long dReservationNumber) {
		this.dReservationNumber = dReservationNumber;
	}
	public String getDiningType() {
		return diningType;
	}
	public void setDiningType(String diningType) {
		this.diningType = diningType;
	}
	public Date getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public long getGuestID() {
		return guestID;
	}
	public void setGuestID(long guestID) {
		this.guestID = guestID;
	}
	
}

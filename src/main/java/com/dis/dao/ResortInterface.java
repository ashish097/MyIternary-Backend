package com.dis.dao;

import java.util.List;

import com.dis.model.ResortReservation;


public interface ResortInterface {
	
	public ResortReservation addresort(ResortReservation resort, long guestID);

	public ResortReservation updateresort(ResortReservation resort, long resortBookingId);

	public ResortReservation cancelresort(long resortBookingId);

	public List<ResortReservation> getAllResortDetails(long guestID);
	
	public ResortReservation getResort(long rReservationNumber);

}

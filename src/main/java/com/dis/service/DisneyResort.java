package com.dis.service;

import java.util.List;

import com.dis.model.ResortReservation;


public interface DisneyResort {
	
	public ResortReservation bookResort(ResortReservation resort, long guestID);

	public ResortReservation updateBookResort(ResortReservation resort, long resortBookingId);

	public ResortReservation cancelBookResort(long resortBookingId);
    
	public ResortReservation getResort(long rReservationNumber);
	
	public List<ResortReservation> getAllResortDetails(long guestID);


}

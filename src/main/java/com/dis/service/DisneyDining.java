package com.dis.service;

import java.util.List;

import com.dis.model.DiningReservation;


public interface DisneyDining {
	
	public DiningReservation bookDining(DiningReservation dining, long guestID);

	public DiningReservation updateBookedDining(DiningReservation dining, long diningBookingId);

	public DiningReservation cancelBookedDining(long diningBookingId);
	
	public DiningReservation getDining(long dReservationNumber);

	public List<DiningReservation> getAllDiningDetails(long guestID);

}

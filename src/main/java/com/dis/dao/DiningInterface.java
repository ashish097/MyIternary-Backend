package com.dis.dao;

import java.util.List;

import com.dis.model.DiningReservation;


public interface DiningInterface {

	public DiningReservation adddining(DiningReservation dining, long guestID);

	public DiningReservation upadtedining(DiningReservation d, long diningBookingId);

	public DiningReservation canceldining(long diningBookingId);

	public List<DiningReservation> getAllDiningDetails(long guestID);

	public DiningReservation getDining(long dReservationNumber);

}

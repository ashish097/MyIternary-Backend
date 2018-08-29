package com.dis.service;



import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dis.model.DiningReservation;
import com.dis.dao.DiningInterface;



@Transactional
@Service
public class DisneyDiningImp implements DisneyDining{
	
	private static final Logger log = LogManager.getLogger("BookingServiceImpl.class");

	@Autowired
	DiningInterface bookingDAO;

	@Override
	public DiningReservation bookDining(DiningReservation dining, long diningBookingId) {
		log.debug("Debugging bookDining method");
		log.info("Executing bookDining method.");
		return bookingDAO.adddining(dining, diningBookingId);

	}

	@Override
	public DiningReservation updateBookedDining(DiningReservation dining, long diningBookingId) {
		log.debug("Debugging updateBookedDining method");
		log.info("Executing updateBookedDining method.");
		return bookingDAO.upadtedining(dining, diningBookingId);
	}

	@Override
	public DiningReservation cancelBookedDining(long diningBookingId) {
		log.debug("Debugging cancelBookedDining method");
		log.info("Executing cancelBookResort method.");
		return bookingDAO.canceldining(diningBookingId);
	}
	
	@Override
	public List<DiningReservation> getAllDiningDetails(long guestID) {
		log.debug("Debugging getAllDiningDetails method");
		log.info("Executing getAllDiningDetails method.");
		return bookingDAO.getAllDiningDetails(guestID);
	}


	@Override
	public DiningReservation getDining(long dReservationNumber) {
		log.debug("Debugging getDining method");
		log.info("Executing getDining method.");
		return bookingDAO.getDining(dReservationNumber);
	}
}

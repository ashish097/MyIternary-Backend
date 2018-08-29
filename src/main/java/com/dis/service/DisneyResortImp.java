package com.dis.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dis.dao.ResortInterface;
import com.dis.model.ResortReservation;



@Transactional
@Service
public class DisneyResortImp implements DisneyResort{
	
	private static final Logger log = LogManager.getLogger("BookingServiceImpl.class");

	@Autowired
	ResortInterface bookingDAO;

	@Override
	public ResortReservation bookResort(ResortReservation resort, long guestID) {
		log.debug("Debugging bookResort method");
		log.info("Executing bookResort method.");
		return bookingDAO.addresort(resort, guestID);
	}

	@Override
	public ResortReservation updateBookResort(ResortReservation resort, long resortBookingId) {
		log.debug("Debugging updateBookResort method");
		log.info("Executing updateBookResort method.");
		return bookingDAO.updateresort(resort, resortBookingId);
	}

	@Override
	public ResortReservation cancelBookResort(long resortBookingId) {
		log.debug("Debugging cancelBookResort method");
		log.info("Executing cancelBookResort method.");
		return bookingDAO.cancelresort(resortBookingId);
	}

	@Override
	public List<ResortReservation> getAllResortDetails(long guestID) {
		log.debug("Debugging getAllResortDetails method");
		log.info(" Executing getAllResortDetails method.");
		return bookingDAO.getAllResortDetails(guestID);
	}


	@Override
	public ResortReservation getResort(long rReservationNumber) {
		log.debug("Debugging getResort method");
		log.info("Executing getResort method.");
		return bookingDAO.getResort(rReservationNumber);
	}

}

package com.dis.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dis.dao.Guestnterface;
import com.dis.model.GuestProfile;




@Transactional
@Service
public class DisneyGuestImp implements DisneyGuest{
	
	
	private static final Logger log = LogManager.getLogger(DisneyGuestImp.class);

	@Autowired
	private Guestnterface guestDAO;

	@Override
	public GuestProfile registerGuest(GuestProfile guest) {
		log.info("Executing registerGuest method.");
		return guestDAO.adduser(guest);
	}

	@Override
	public GuestProfile validateGuest(String em, String pass) {
		log.info("Executing validateGuest method.");
		return guestDAO.validateuser(em, pass);
	}

	@Override
	public GuestProfile updateGuest(GuestProfile guest, long guestID) {
		log.info("Executing updateGuest method.");
		return guestDAO.updateuser(guest, guestID);
	}


}

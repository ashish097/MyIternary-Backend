package com.dis.dao;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dis.map.DisneyRowMapper;
import com.dis.model.GuestProfile;



@Repository
public class DisneyDaoGuest implements Guestnterface   {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DisneyDaoGuest.class);
	
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	
	
	
	Date date = new Date();
	java.sql.Date sqlDate = new java.sql.Date(date.getTime());
	
	
	private static final String SQL_INSERT_GUEST = "INSERT INTO guest_profile(email, first_name, last_name, address, phone, password, created_date, updated_date) "
			+ "values (?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String SQL_UPDATE_GUEST = "UPDATE guest_profile SET first_name=?, last_name=?, address=?, phone=?, updated_date=? "
			+ "WHERE guest_id=?";

	private static final String SQL_GET_GUEST_DETAILS = "SELECT * FROM guest_profile WHERE guest_id in(select max(guest_id) from guest_profile)";

	
	@Override
	public GuestProfile adduser(GuestProfile guest) {
		
		LOGGER.debug("Debugging registerGuest method");
		LOGGER.info("Executing Guest Registering .");
		try {
			
			guest.setCreatedDate(date);
			guest.setUpdatedDate(date);
			Object[] params = { guest.getEmail(), guest.getFirstName(), guest.getLastName(), guest.getAddress(),
					guest.getPhone(), guest.getPassword(), guest.getCreatedDate(), guest.getUpdatedDate() };
			
			jdbcTemplate.update(SQL_INSERT_GUEST, params);
			LOGGER.info("New Guest is registered.");
		} 
		catch (Exception e) 
		{
			LOGGER.warn("Registration Failed.Duplicate email", e);
			return null;
		}
		return jdbcTemplate.queryForObject(SQL_GET_GUEST_DETAILS, new DisneyRowMapper());
	}

	@Override
	public GuestProfile validateuser(String em, String pass) {
		
		LOGGER.debug("Debugging validateGuest method");
		LOGGER.info("validating guest with provided email and password");
        
		RowMapper<GuestProfile> rowmapper = new DisneyRowMapper();
		GuestProfile guest = null;
		try {
			
			guest = jdbcTemplate.queryForObject("SELECT * FROM guest_profile WHERE email = ? AND password= ? ",
					new Object[] { em,pass}, rowmapper);
			LOGGER.info("Guest with provided email and password is found");
		} 
		catch (Exception e)
		{
			
			LOGGER.warn("Validation Failed.Guest not found with given email and password.", e);
			return null;
		}
		return guest;
	}

	@Override
	public GuestProfile updateuser(GuestProfile guest, long guestID) {
		
		LOGGER.debug("Debugging updateGuest method");
		LOGGER.info("Executing Guest Update method .");
		
		try {
			guest.setGuestID(guestID);
			guest.setUpdatedDate(date);
			Object[] params = { guest.getFirstName(), guest.getLastName(), guest.getAddress(), guest.getPhone(),
					 guest.getUpdatedDate(), guest.getGuestID() };
			
			jdbcTemplate.update(SQL_UPDATE_GUEST, params);
			LOGGER.info("Guest is updated.");
		} 
		catch (Exception e)
		{
			LOGGER.warn("Updated Failed.",e);
			return null;
		}
		return jdbcTemplate.queryForObject(SQL_GET_GUEST_DETAILS, new DisneyRowMapper());
	}
	

}

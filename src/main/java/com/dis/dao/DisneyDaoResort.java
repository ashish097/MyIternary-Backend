package com.dis.dao;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.dis.map.DisneyRowMapper2;
import com.dis.model.ResortReservation;






@Repository
public class DisneyDaoResort implements ResortInterface {
	

	private static final Logger log = LogManager.getLogger("DisneyDaoResort.class");

	@Autowired
	private JdbcTemplate jdbcTemplate;


	Date date = new Date();
	java.sql.Date sqlDate = new java.sql.Date(date.getTime());

	
	private static final String SQL_BOOK_RESORT_BY_ID = "INSERT INTO resort_reservation(guest_id_fk, room_type, arrival_date, departure_date,"
			+ " no_of_people, status, created_date, updated_date) values (?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String SQL_GET_RESORT_DETAILS_BY_GUESTID = "SELECT * FROM resort_reservation WHERE guest_id_fk=?";

	private static final String SQL_GET_RESORT_DETAILS_BY_RESORT_BOOKING_ID = "SELECT * FROM resort_reservation WHERE r_reservation_number = ?";

	private static final String SQL_GET_RESORT_DETAILS_BY_GUEST_ID = "SELECT * FROM resort_reservation where r_reservation_number IN"
			+ "(select max(r_reservation_number) FROM resort_reservation WHERE guest_id_fk=?)";

	private static final String SQL_UPDATE_RESORT_RNO = "UPDATE resort_reservation SET room_type = ?, arrival_date=?, "
			+ "departure_date=?, no_of_people =?, updated_date=? WHERE r_reservation_number = ?";

	private static final String SQL_CANCEL_RESORT_BY_RESORT_ID = "UPDATE resort_reservation SET status=? WHERE r_reservation_number=?";

	
	private static final String SQL="select * from resort_reservation where r_reservation_number in(select max(r_reservation_number) FROM resort_reservation WHERE guest_id_fk= ?)";
	
	
	//------------------------------------------RESORT DAO-----------------------------------------------------//

	@Override
	public ResortReservation addresort(ResortReservation resort,long id) 
	{
		log.info("ResortResservation Started");
		log.debug("Debuging Resort reservation");
		
		try {
				resort.setGuestID(id);
				resort.setStatus("booked");
				resort.setCreatedDate(date);
				resort.setUpdatedDate(date);

				Object[] params = { resort.getGuestID(), resort.getRoomType(), resort.getArrivalDate(),
					resort.getDepartureDate(), resort.getNoOfPeople(), resort.getStatus(), resort.getCreatedDate(),
					resort.getUpdatedDate() };

			jdbcTemplate.update(SQL_BOOK_RESORT_BY_ID, params);
			log.info("Resort Booked");
		} 
		catch (DataAccessException e)
		{
			log.catching(e);
			log.warn("Resort booking failed. No guest found with guestID=" + id
					+ "DataAccessException occured in bookResort method" + e.getStackTrace());
			return null;
		}
		
		// Retrieving resort booking
		ResortReservation resort1 = jdbcTemplate.queryForObject(SQL,new Object[] {id},new DisneyRowMapper2());
		log.debug("query executed" + resort1);
		return resort1;
	}
	
	
	@Override
	public ResortReservation updateresort(ResortReservation resort, long id)
    {	
		log.info("Executing updateBookResort.");
		log.debug("Resort update debuging");
		
		try {
			
			resort.setrReservationNumber(id);
			resort.setUpdatedDate(date);
			
			Object[] params = { resort.getRoomType(), resort.getArrivalDate(), resort.getDepartureDate(),
					resort.getNoOfPeople(), resort.getUpdatedDate(), resort.getrReservationNumber() };
		
			jdbcTemplate.update(SQL_UPDATE_RESORT_RNO, params);
			log.info("Resort Updated.");
		} 
		catch (DataAccessException e)
		{
			log.catching(e);
			log.warn("updating booked resort failed. DataAccessException occured in updateBookResort method "
							+ e.getStackTrace());
			return null;
		}
		return getResortbyid(id);
	}
	
	
	@Override
	public ResortReservation cancelresort(long id) 
	{
		log.info("Executing cancelBookResort");
		log.debug("Debugging Cancellation resort");
		
		String status = "cancelled";
		try 
		{
			Object[] params = {status,id};
			jdbcTemplate.update(SQL_CANCEL_RESORT_BY_RESORT_ID, params);
			log.debug("resort cancelled");
		} 
		catch (Exception e) 
		{
			log.catching(e);
			log.warn("Cancelling Resort failed.Resort booking id not found");
			return null;
		}
		return getResortbyid(id);
	}
    
	
	@Override
	public ResortReservation getResort(long rReservationNumber) {
		
		log.info("Executing getDining method.");
		ResortReservation resort;
		try 
		{
			resort = jdbcTemplate.queryForObject(SQL_GET_RESORT_DETAILS_BY_RESORT_BOOKING_ID,
			new Object[] { rReservationNumber }, new DisneyRowMapper2());
			log.debug("query executed" + resort);
		} 
		catch (DataAccessException e)
		{
			log.catching(e);
			log.warn(
					"getting resort details by resort reservation id failed. DataAccessException occured in getResort method"
							+ e.getStackTrace());
			return null;
		}
		return resort;
	}
	
	
	public ResortReservation getResortbyGuestID(long id) 
	{
		ResortReservation resort;
		try {
			
			resort = jdbcTemplate.queryForObject(SQL_GET_RESORT_DETAILS_BY_GUEST_ID, new Object[] { id },
					new DisneyRowMapper2());
			log.info("Resort Entity Returned.");
		} 
		catch (DataAccessException e)
		{
			log.catching(e);
			log.warn("Getting Resort booked details failed. DataAccessException occured in bookResort method "
							+ e.getStackTrace());
			return null;
		}
		return resort;
	}
	
	
	@Override
	public List<ResortReservation> getAllResortDetails(long guestID) {
		
		log.info("Executing getAllResortDetails method.");
		
		List<ResortReservation> resort;
		try {
			
			resort = jdbcTemplate.query(SQL_GET_RESORT_DETAILS_BY_GUESTID, new Object[] { guestID },
					new DisneyRowMapper2());
			log.info("Resort Entities Returned.");
		} 
		catch (DataAccessException e) 
		{
			log.catching(e);
			log.warn(
					"getting all resort details by guest id failed. DataAccessException occured in getAllResortDetails method. "
							+ e.getStackTrace());
			return Collections.emptyList();
		}
		return resort;
	}

   
	public ResortReservation getResortbyid(long resortBookingId) 
	{
		ResortReservation resort=null;
		try 
		{
			resort = jdbcTemplate.queryForObject(SQL_GET_RESORT_DETAILS_BY_RESORT_BOOKING_ID,
					new Object[] { resortBookingId }, new DisneyRowMapper2());
			log.info("Resort Entity Returned.");
			return resort;
		} 
		catch(Exception e) 
		{
			log.catching(e);
			log.warn(
					"getting resort details failed.DataAccessException occured in getResortByResortBookingID."
							+ e.getStackTrace());
			return null;
		}
		
	} 

	
}







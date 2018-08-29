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
import com.dis.model.DiningReservation;
import com.dis.map.DisneyRowMapper3;




@Repository
public class DisneyDaoDining implements DiningInterface{
	
	private static final Logger log = LogManager.getLogger("DisneyDaoDining.class");

	@Autowired
	private JdbcTemplate jdbcTemplate;


	Date date = new Date();
	java.sql.Date sqlDate = new java.sql.Date(date.getTime());
	
	
	
	private static final String SQL_BOOK_DINING_BY_ID = "INSERT INTO dining_reservation(guest_id_fk, dining_type, arrival_date, no_of_people,"
			+ " status, created_date, updated_date) values (?, ?, ?, ?, ?, ?, ?)";

	private static final String SQL_GET_DINING_DETAILS_BY_GUESTID = "SELECT * FROM dining_reservation WHERE guest_id_fk=?";

	private static final String SQL_GET_DINING_BY_DINING_BOOKING_ID = "SELECT * FROM dining_reservation WHERE d_reservation_number = ?";

	private static final String SQL_GET_DINING_DETAILS_BY_GUEST_ID = "SELECT * FROM dining_reservation where d_reservation_number IN"
			+ "(select max(d_reservation_number) FROM dining_reservation WHERE guest_id_fk=?)";

	private static final String SQL_UPDATE_BOOKED_DINING_BY_DINING_BOOKED_ID = "UPDATE dining_reservation SET dining_type = ?, arrival_date=?,"
			+ "no_of_people =?, updated_date=? WHERE d_reservation_number = ?";

	private static final String SQL_CANCEL_DINING_BY_DINING_ID = "UPDATE dining_reservation SET status=? WHERE d_reservation_number=?";
	
	private static final String SQL="SELECT * FROM dining_reservation where d_reservation_number IN"
			+ "(select max(d_reservation_number) FROM dining_reservation WHERE guest_id_fk=?)";
	
	//----------------------DINING REGISTRATION DAO-------------------------------------------//

		@Override
		public DiningReservation adddining(DiningReservation dining, long id) 
		{
			log.info("Executing bookDining method.");
			log.debug("Bookingdining debuging");
			
			try {
				dining.setGuestID(id);
				dining.setStatus("booked");
				dining.setCreatedDate(date);
				dining.setUpdatedDate(date);

				Object[] params = { dining.getGuestID(), dining.getDiningType(), dining.getArrivalDate(),
						dining.getNoOfPeople(), dining.getStatus(), dining.getCreatedDate(), dining.getUpdatedDate() };

				jdbcTemplate.update(SQL_BOOK_DINING_BY_ID, params);
				log.info("Dining Booked.");
			} 
			catch (DataAccessException e) 
			{
				log.catching(e);
				log.warn("booked dining failed DataAccessException occured in bookDining method"
						+ e.getStackTrace());
				return null;
			}
			return getDining1(id);
		}
		
		
		
		@Override
		public DiningReservation upadtedining(DiningReservation dining, long id) 
		{
			log.info("Executing updateBookedDining.");
			log.debug("Debugging update dining");
			
			dining.setdReservationNumber(id);
			dining.setUpdatedDate(date);

			Object[] params = { dining.getDiningType(), dining.getArrivalDate(), dining.getNoOfPeople(),
					dining.getUpdatedDate(), dining.getdReservationNumber() };

			try {
				
				jdbcTemplate.update(SQL_UPDATE_BOOKED_DINING_BY_DINING_BOOKED_ID, params);
				log.info("BookingDAOImpl:Dining Updated.");
			} 
			catch (DataAccessException e) 
			{
				log.catching(e);
				log.warn("updating booked dining failed.DataAccessException occured in updateBookedDining method"
								+ e.getStackTrace());
				return null;
			}
			return getDining1(id);
					
		}
		
		@Override
		public DiningReservation getDining(long dReservationNumber) 
		{
			log.info("Executing getDining method.");
			log.debug("Debugging getdining dining");
			
			DiningReservation dining;
			try 
			{
				
				dining = jdbcTemplate.queryForObject(SQL_GET_DINING_BY_DINING_BOOKING_ID,
				new Object[] { dReservationNumber }, new DisneyRowMapper3());
				log.debug("query executed" + dining);
			} 
			catch (Exception e) 
			{
				log.catching(e);
				log.warn(
						"getting dining details by dining reservation id failed. DataAccessException occured in getDining method"
								+ e.getStackTrace());
				return null;
			}
			return dining;
		}
		
		@Override
		public DiningReservation canceldining(long id) {
			
			log.info("Executing cancelBookedDining");
			log.debug("Debuging cancel diniing");
			
			String status = "cancelled";
			try {
				
				Object[] params = {status,id};
				jdbcTemplate.update(SQL_CANCEL_DINING_BY_DINING_ID, params);
				log.debug("resort cancelled");
			} 
			catch (Exception e) 
			{
				log.catching(e);
				log.warn("Resort Not Cancelled.");
				return null;
			}
			return getDining1(id);
		}
		
		
		public DiningReservation getDiningbyGuestID(long guestID)
		{
			DiningReservation dining;
			try {
				
				dining = jdbcTemplate.queryForObject(SQL_GET_DINING_DETAILS_BY_GUEST_ID, new Object[] { guestID },
						new DisneyRowMapper3());
				log.info("Dining Entity by guestID is Returned.");
			} 
			catch (DataAccessException e) 
			{
				log.catching(e);
				log.warn(
						"getting dining detail by guest id failed.DataAccessException occured in getDiningbyGuestID method. "
								+ e.getStackTrace());
				return null;
			}
			return dining;
		}
		
		@Override
		public List<DiningReservation> getAllDiningDetails(long guestID) {
			
			log.info("Executing getAllDiningDetails method.");
			log.debug("Debugging dining");
			
			List<DiningReservation> dining;
			try {
				dining = jdbcTemplate.query(SQL_GET_DINING_DETAILS_BY_GUESTID, new Object[] { guestID },
						new DisneyRowMapper3());
				log.info("Dining Entity Returned.");
			} 
			catch (DataAccessException e) 
			{
				log.catching(e);
				log.warn(
						"getting all dining details by guest id failed. DataAccessException occured in getAllDiningDetails method. "
								+ e.getStackTrace());
				return Collections.emptyList();
			}
			return dining;
		}


	public DiningReservation getDining1(long id) {
		
		DiningReservation dining;
		try {
			
			dining = jdbcTemplate.queryForObject(SQL, new Object[] { id },
					new DisneyRowMapper3());
			log.info("Dining Entity by dining booked id is Returned.");
		} 
		catch (DataAccessException e) 
		{
			log.catching(e);
			log.warn(
					"getting dining detail failed.DataAccessException occured in getDiningByDiningBookingID method."
							+ e.getStackTrace());
			return null;
		}
		return dining;
	}			
	

}

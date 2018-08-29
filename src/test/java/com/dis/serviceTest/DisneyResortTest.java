package com.dis.serviceTest;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dis.dao.ResortInterface;
import com.dis.model.GuestProfile;
import com.dis.model.ResortReservation;
import com.dis.service.DisneyResortImp;



@RunWith(SpringRunner.class)
@SpringBootTest
public class DisneyResortTest {
	
	@Mock
	private ResortInterface bookingDAO;

	@InjectMocks
	private DisneyResortImp bookingService;;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	Date date = new Date();
	GuestProfile guest = new GuestProfile();
	
	@Test
	public void testBookResort() {
		guest.setGuestID(1);
		long guestID = guest.getGuestID();
		ResortReservation resort = new ResortReservation();
		resort.setRoomType("suit");
		resort.setNoOfPeople(1);
		resort.setArrivalDate(date);
		resort.setDepartureDate(date);

		when(bookingDAO.addresort(resort, guestID)).thenReturn(resort);
		ResortReservation bookedResortTestObj = bookingService.bookResort(resort, guestID);
		assertNotNull(bookedResortTestObj);
	}

	@Test
	public void testUpdateBookResort() {
		ResortReservation updatedResort = new ResortReservation();
		updatedResort.setRoomType("suit");
		updatedResort.setNoOfPeople(2);
		updatedResort.setArrivalDate(date);
		updatedResort.setDepartureDate(date);
		updatedResort.setrReservationNumber(500);
		long resortBookingId = updatedResort.getrReservationNumber();

		when(bookingDAO.updateresort(updatedResort, resortBookingId)).thenReturn(updatedResort);
		ResortReservation updatedResortTestObj = bookingService.updateBookResort(updatedResort, resortBookingId);
		assertNotNull(updatedResortTestObj);
	}

	@Test
	public void testCancelBookResort() {
		ResortReservation cancelResort = new ResortReservation();
		cancelResort.setrReservationNumber(101);
		long cancelResortID = cancelResort.getrReservationNumber();

		when(bookingDAO.cancelresort(cancelResortID)).thenReturn(cancelResort);
		ResortReservation cancelResortTestObj = bookingService.cancelBookResort(cancelResortID);
		assertNotNull(cancelResortTestObj);
	}
	
	
	@Test
	public void testGetAllResortDetails() {
		guest.setGuestID(1);
		long guestID = guest.getGuestID();
		List<ResortReservation> resortList = new ArrayList<>();
		ResortReservation resort = new ResortReservation();
		resort.setRoomType("suit");
		resort.setNoOfPeople(1);
		resort.setArrivalDate(date);
		resort.setDepartureDate(date);
		resortList.add(resort);
		when(bookingDAO.getAllResortDetails(guestID)).thenReturn(resortList);
		List<ResortReservation> bookedResortTestObj = bookingService.getAllResortDetails(guestID);
		assertNotNull(bookedResortTestObj);
	}

	
}

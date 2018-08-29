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

import com.dis.model.DiningReservation;
import com.dis.dao.DiningInterface;
import com.dis.model.GuestProfile;
import com.dis.service.DisneyDiningImp;



@RunWith(SpringRunner.class)
@SpringBootTest
public class DisneyDiningTest {
	
	@Mock
	private DiningInterface bookingDAO;

	@InjectMocks
	private DisneyDiningImp bookingService;;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	Date date = new Date();
	GuestProfile guest = new GuestProfile();
	
	@Test
	public void testBookDining() {
		guest.setGuestID(1);
		long guestID = guest.getGuestID();
		DiningReservation dining = new DiningReservation();
		dining.setDiningType("delux");
		dining.setNoOfPeople(1);
		dining.setArrivalDate(date);

		when(bookingDAO.adddining(dining, guestID)).thenReturn(dining);
		DiningReservation bookedDiningTestObj = bookingService.bookDining(dining, guestID);
		assertNotNull(bookedDiningTestObj);
	}

	@Test
	public void testUpdateBookDining() {
		DiningReservation updatedDining = new DiningReservation();
		updatedDining.setDiningType("breakbast");
		updatedDining.setNoOfPeople(2);
		updatedDining.setArrivalDate(date);
		updatedDining.setdReservationNumber(100);
		long diningBookingId = updatedDining.getdReservationNumber();

		when(bookingDAO.upadtedining(updatedDining, diningBookingId)).thenReturn(updatedDining);
		DiningReservation updatedDiningTestObj = bookingService.updateBookedDining(updatedDining, diningBookingId);
		assertNotNull(updatedDiningTestObj);
	}

	@Test
	public void testCancelBookDining() {
		DiningReservation cancelDining = new DiningReservation();
		cancelDining.setdReservationNumber(101);
		long cancelDiningID = cancelDining.getdReservationNumber();

		when(bookingDAO.canceldining(cancelDiningID)).thenReturn(cancelDining);
		DiningReservation cancelDiningTestObj = bookingService.cancelBookedDining(cancelDiningID);
		assertNotNull(cancelDiningTestObj);
	}

	

	@Test
	public void testGetAllDiningDetails() {
		guest.setGuestID(1);
		long guestID = guest.getGuestID();
		List<DiningReservation> diningList = new ArrayList<>();
		DiningReservation dining = new DiningReservation();
		dining.setDiningType("testdining");
		dining.setNoOfPeople(1);
		dining.setArrivalDate(date);
		diningList.add(dining);
		when(bookingDAO.getAllDiningDetails(guestID)).thenReturn(diningList);
		List<DiningReservation> bookedDiningTestObj = bookingService.getAllDiningDetails(guestID);
		assertNotNull(bookedDiningTestObj);
	}

}

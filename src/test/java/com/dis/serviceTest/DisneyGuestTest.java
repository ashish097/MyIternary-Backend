package com.dis.serviceTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dis.dao.Guestnterface;
import com.dis.model.GuestProfile;
import com.dis.service.DisneyGuestImp;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DisneyGuestTest 
{
	@Mock
	private Guestnterface guestDAO;

	@InjectMocks
	private DisneyGuestImp guestService;;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	GuestProfile guest = new GuestProfile();

	@Test
	public void testRegisterGuest() {
		guest.setEmail("test@.com");
		guest.setFirstName("ashish");
		guest.setLastName("sinha");
		guest.setAddress("bangalore");
		guest.setPhone("+918093401286");
		guest.setPassword("ashish");
		when(guestDAO.adduser(guest)).thenReturn(guest);

		GuestProfile guestTestObj = guestService.registerGuest(guest);
		assertNotNull(guestTestObj);
	}

	@Test
	public void testValidateGuest() {

		guest.setEmail("test@.com");
		guest.setFirstName("ashish");
		guest.setLastName("sinha");
		guest.setAddress("bangalore");
		guest.setPhone("+918093401286");
		guest.setPassword("ashish");
		guest.setGuestID(1);

		String em = "test@.com";
		String pass = "ashish";
		when(guestDAO.validateuser(em, pass)).thenReturn(guest);
		GuestProfile guestTestObj = guestService.validateGuest(em, pass);
		assertEquals(guestTestObj, guest);

	}

	@Test
	public void testUpdateGuest() {
		
		guest.setFirstName("ashish");
		guest.setLastName("sinha");
		guest.setAddress("bangalore");
		guest.setPhone("+918093401286");
		guest.setPassword("ashish");
		guest.setGuestID(1);

		when(guestDAO.updateuser(guest, guest.getGuestID())).thenReturn(guest);

		GuestProfile guestUpdatedTestObj = guestService.updateGuest(guest, 1);
		assertNotNull(guestUpdatedTestObj);
	}
}

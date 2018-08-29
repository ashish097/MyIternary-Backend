package com.dis.service;

import com.dis.model.GuestProfile;

public interface DisneyGuest {
	
	public GuestProfile registerGuest(GuestProfile guest);

	public GuestProfile validateGuest(String em, String pass);

	public GuestProfile updateGuest(GuestProfile guest, long guestID);

}

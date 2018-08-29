package com.dis.dao;

import com.dis.model.GuestProfile;

public interface Guestnterface {
	
	public GuestProfile adduser(GuestProfile guest);

	public GuestProfile validateuser(String em, String pass);

	public GuestProfile updateuser(GuestProfile guest, long guestID);

}

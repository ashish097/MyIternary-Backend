package com.dis.map;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.dis.model.GuestProfile;

public class DisneyRowMapper implements RowMapper<GuestProfile> {

	@Override
	public GuestProfile mapRow(ResultSet row, int rowNum) throws SQLException {
		GuestProfile g = new GuestProfile();

		g.setGuestID(row.getLong("guest_id"));
		g.setEmail(row.getString("email"));
		g.setFirstName(row.getString("first_name"));
		g.setLastName(row.getString("last_name"));
		g.setPhone(row.getString("phone"));
		g.setPassword(row.getString("password"));
		g.setAddress(row.getString("address"));
		g.setCreatedDate(row.getDate("created_date"));
		g.setUpdatedDate(row.getDate("created_date"));
		return g;
	}

    
}

package com.dis.contrl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dis.model.DiningReservation;
import com.dis.service.DisneyDining;
import com.dis.valid.GuestValidator;
import com.dis.model.GuestProfile;
import com.dis.model.ResortReservation;
import com.dis.service.DisneyGuest;
import com.dis.service.DisneyResort;
import com.dis.valid.ValidationError;



@RestController
@RequestMapping("/guest")
@CrossOrigin(origins = "*")
public class GuestController {
	
	private static final Logger log = LogManager.getLogger(GuestController.class);

	@Autowired
	private DisneyGuest guestService;

	@Autowired
	private DisneyResort bookingService;
	
	@Autowired
	private DisneyDining bookingServiced;

	@Autowired
	private ValidationError error;

	@Autowired
	private GuestValidator guestValidator;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(guestValidator);
	}

	@PostMapping("/register")
	public ResponseEntity<Object> registerGuest(@RequestBody @Validated GuestProfile guest) 
	{
		log.debug("Debugging createGuest method");
		log.info("Executing Creating Guest Method.");
		
		GuestProfile checkedGuest = guestService.registerGuest(guest);
		if (checkedGuest != null) {
			log.info("GuestController:New Guest is registered and Guest Entity is returned as response.");
			return new ResponseEntity<>(checkedGuest, HttpStatus.CREATED);
		}
		else
			log.warn("Guest with same email is already registered.");
		error.setErrorMessage("Registration failed. Duplicate email found");
		error.setStatus(400);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/login")
	public ResponseEntity<Object> loginGuest(@RequestBody GuestProfile guest)
	{
		log.debug("Debugging loginGuest");
		log.info("Executing Logging Guest.");
		
		GuestProfile validGuest;
		validGuest = guestService.validateGuest(guest.getEmail(), guest.getPassword());
		if (validGuest != null) {
			log.info("Guest is validated and Guest Entity is returned as response.");
			return new ResponseEntity<>(validGuest, HttpStatus.CREATED);
		} 
		else
		{
			log.warn("Invalid Guest.");
			error.setErrorMessage("Login failed. Invalid credentials.");
			error.setStatus(400);
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/update/{guestID}")
	public ResponseEntity<Object> updateGuest(@RequestBody GuestProfile guest, @PathVariable long guestID) 
	{
		log.debug("Debugging updateguest");
		log.info("Executing updateGuest Method.");
		GuestProfile checkedGuest = guestService.updateGuest(guest, guestID);
		if (checkedGuest != null) {
			log.info("GuestController:Guest is updated and Guest Entity is returned as response.");
			return new ResponseEntity<>(checkedGuest, HttpStatus.CREATED);
		} 
		else
			log.warn("GuestController:Updating Guest Details Failed.");
		error.setErrorMessage("update failed.");
		error.setStatus(400);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/view/{guestID}")
	public List<Object> viewItenarary(@PathVariable long guestID) {
		log.debug("Debugging viewguest");
		log.info("Viewing Guest Booking Details.");
		List<Object> list = new ArrayList<>();
		log.info("Viewing Guest Resort Booking Details.");
		List<ResortReservation> resort = bookingService.getAllResortDetails(guestID);
		log.info("Viewing Dinning Guest Booking Details.");
		List<DiningReservation> dining = bookingServiced.getAllDiningDetails(guestID);
		list.add(resort);
		list.add(dining);
		return list;

	}
	

}

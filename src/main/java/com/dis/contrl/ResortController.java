package com.dis.contrl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dis.model.DiningReservation;
import com.dis.service.DisneyDining;
import com.dis.model.ResortReservation;
import com.dis.service.DisneyResort;
import com.dis.valid.ValidationError;



@RestController
@RequestMapping("/booking")
@CrossOrigin(origins = "*")
public class ResortController {
	
	private static final Logger log = LogManager.getLogger("ResortController.class");

	@Autowired
	private DisneyResort bookingService;
	
	@Autowired
	private DisneyDining bookingServices;

	@Autowired
	private ValidationError error;

	@PostMapping("/resort/{guestID}")
	public ResponseEntity<Object> bookResort(@RequestBody ResortReservation resort, @PathVariable long guestID)
	{
		log.debug("Debugging bookResort");
		log.info("Executing Resort Booking for a Guest.");
		
		ResortReservation bookedResort = bookingService.bookResort(resort, guestID);
		if (bookedResort != null) 
		{
			log.info("Resort Booked for a Guest");
			return new ResponseEntity<>(bookedResort, HttpStatus.CREATED);
		} 
		else 
		{
			log.warn("Resort booking failed for a Guest.");
			error.setErrorMessage("Resort Booking failed. Guest not found");
			error.setStatus(400);
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/resort/get/{rReservationNumber}")
	public ResponseEntity<Object> getResort(@PathVariable long rReservationNumber) 
	{
		log.debug("Debugging getResort method");
		log.info("Executing get Resort details");
		ResortReservation getResort = bookingService.getResort(rReservationNumber);
		if (getResort != null) {
			log.info("Resort Booked for a Guest and bookedResort Entity returned.");
			return new ResponseEntity<>(getResort, HttpStatus.OK);
		} 
		else 
		{
			log.warn("Resort view failed for a Guest.");
			error.setErrorMessage("Getting Resort Details failed. Resort id not found");
			error.setStatus(400);
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/update/resort/{resortBookingId}")
	public ResponseEntity<Object> updateBookResort(@RequestBody ResortReservation resort, @PathVariable long resortBookingId) {
		log.debug("Debugging updateBookResort method");
		log.info("Executing updateBookResort for a Guest.");
		ResortReservation updateResort = bookingService.updateBookResort(resort, resortBookingId);
		if (updateResort != null) {
			log.info("Booked Resort for a Guest is updated.");
			return new ResponseEntity<>(updateResort, HttpStatus.CREATED);
		} else
		{
			log.warn("Resort update failed.Resort booking id not found");
			error.setErrorMessage("Resort update failed.Resort booking id not found");
			error.setStatus(400);
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/cancel/resort/{resortBookingId}")
	public ResponseEntity<Object> cancelBookResort(@PathVariable long resortBookingId) {
		log.debug("Debugging cancelBookResort method");
		log.info("Executing cancelBookResort for a Guest.");
		ResortReservation cancelResort = bookingService.cancelBookResort(resortBookingId);
		if (cancelResort != null) {
			log.info("BookingController:Booked Resort for a Guest is canceled.");
			return new ResponseEntity<>(cancelResort, HttpStatus.OK);
		} 
		else 
		{
			log.warn("BookingController:Resort cancellation failed.");
			error.setErrorMessage("Resort cancellation failed.");
			error.setStatus(400);
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/dining/{guestID}")
	public ResponseEntity<Object> bookDining(@RequestBody DiningReservation dining, @PathVariable long guestID) 
	{
		log.debug("Debugging bookDining method");
		log.info("Executing Dining Booking for a Guest.");
		
		DiningReservation bookDining = bookingServices.bookDining(dining, guestID);
		if (bookDining != null) {
			log.info("Dining Booked for a Guest.");
			return new ResponseEntity<>(bookDining, HttpStatus.CREATED);
		} 
		else
		{
			log.warn("Dining not booked for a Guest.");
			error.setErrorMessage("Dining Booking failed. Guest not found");
			error.setStatus(400);
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/dining/get/{dReservationNumber}")
	public ResponseEntity<Object> getDining(@PathVariable long dReservationNumber) 
	{
		log.debug("Debugging getDining method");
		log.info("Executing get Dining details");
		
		DiningReservation getDining = bookingServices.getDining(dReservationNumber);
		if (getDining != null) 
		{
			log.info("Resort Booked for a Guest and bookedResort Entity returned.");
			return new ResponseEntity<>(getDining, HttpStatus.OK);
		} 
		else 
		{
			log.warn("Resort booking failed for a Guest.");
			error.setErrorMessage("Getting Dining Details failed. Dining id not found");
			error.setStatus(400);
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/update/dining/{diningBookingId}")
	public ResponseEntity<Object> updateBookedDining(@RequestBody DiningReservation dining, @PathVariable long diningBookingId)
	{
		log.debug("Debugging updateBookedDining method");
		log.info("Updating Dining Booking for a Guest.");
		DiningReservation updateDining = bookingServices.updateBookedDining(dining, diningBookingId);
		if (updateDining != null) {
			log.info("Booked Resort for a Guest is updated.");
			return new ResponseEntity<>(updateDining, HttpStatus.CREATED);
		}
		else 
		{
			log.warn("Booked Dining for a Guest is not updated.");
			error.setErrorMessage("Dining update failed.");
			error.setStatus(400);
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/cancel/dining/{diningBookingId}")
	public ResponseEntity<Object> cancelBookedDining(@PathVariable long diningBookingId) 
	{
		log.debug("Debugging cancelBookedDining method");
		log.info("Cancelling Dining Booking for a Guest.");
		
		DiningReservation cancelDining = bookingServices.cancelBookedDining(diningBookingId);
		if (cancelDining != null) 
		{
			log.info("Cancelled Dining Booking for a Guest.");
			return new ResponseEntity<>(cancelDining, HttpStatus.OK);
		} 
		else 
		{
			log.error("Booked Dining for a Guest is not cancelled.");
			error.setErrorMessage("Dining cancellation failed.");
			error.setStatus(400);
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		}
	}

}

package com.hostmdy.hotel.service;

import java.util.Optional;

import com.hostmdy.hotel.domain.Amenities;

public interface AmenitiesService {
	
	Amenities saveAmenities(Amenities amenities);
	
	Amenities creteAmenities(Amenities amenities);
	
	Optional<Amenities> getAmenitiesById(Long amenitiesId);
	
	Optional<Amenities> getAmenitiesByRoomId(Long roomId);
	
}

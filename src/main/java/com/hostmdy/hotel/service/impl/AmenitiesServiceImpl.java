package com.hostmdy.hotel.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hostmdy.hotel.domain.Amenities;
import com.hostmdy.hotel.repository.AmenitiesRepository;
import com.hostmdy.hotel.service.AmenitiesService;

@Service
public class AmenitiesServiceImpl implements AmenitiesService{
	
	private final AmenitiesRepository amenitiesRepository;

	public AmenitiesServiceImpl(AmenitiesRepository amenitiesRepository) {
		super();
		this.amenitiesRepository = amenitiesRepository;
	}

	@Override
	public Amenities saveAmenities(Amenities amenities) {
		// TODO Auto-generated method stub
		return amenitiesRepository.save(amenities);
	}

	@Override
	public Amenities creteAmenities(Amenities amenities) {
		// TODO Auto-generated method stub
		return saveAmenities(amenities);
	}

	@Override
	public Optional<Amenities> getAmenitiesById(Long amenitiesId) {
		// TODO Auto-generated method stub
		return amenitiesRepository.findById(amenitiesId);
	}

	@Override
	public Optional<Amenities> getAmenitiesByRoomId(Long roomId) {
		// TODO Auto-generated method stub
		return amenitiesRepository.findByRoomId(roomId);
	}


}

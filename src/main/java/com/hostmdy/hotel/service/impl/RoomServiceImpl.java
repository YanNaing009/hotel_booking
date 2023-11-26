package com.hostmdy.hotel.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hostmdy.hotel.domain.Amenities;
import com.hostmdy.hotel.domain.Room;
import com.hostmdy.hotel.repository.AmenitiesRepository;
import com.hostmdy.hotel.repository.RoomRepository;
import com.hostmdy.hotel.service.RoomService;

@Service
public class RoomServiceImpl implements RoomService{
	
	private final RoomRepository roomRepository;
	private final AmenitiesRepository amenitiesRepository;

	public RoomServiceImpl(RoomRepository roomRepository, AmenitiesRepository amenitiesRepository) {
		super();
		this.roomRepository = roomRepository;
		this.amenitiesRepository = amenitiesRepository;
	}

	@Override
	public Room saveRoom(Room room) {
		// TODO Auto-generated method stub
		return roomRepository.save(room);
	}

	@Override
	public Room createRoom(Room room, Long amenitiesId) {
		// TODO Auto-generated method stub
		Optional<Amenities> amenitiesOpt = amenitiesRepository.findById(amenitiesId);
		if(amenitiesOpt.isEmpty()) {
			throw new NullPointerException("amenities id is not found!");
		}
		
		Amenities amenities = amenitiesOpt.get();
		room.setAmenities(amenities);
		amenities.getRoom().add(room);
		
		return saveRoom(room);
	}

	@Override
	public List<Room> getAllRoom() {
		// TODO Auto-generated method stub
		return (List<Room>) roomRepository.findAll();
	}

	@Override
	public Optional<Room> getRoomById(Long roomId) {
		// TODO Auto-generated method stub
		return roomRepository.findById(roomId);
	}

	@Override
	public List<Room> getRoomByName(String name) {
		// TODO Auto-generated method stub
		return roomRepository.findByName(name);
	}

	@Override
	public List<Room> getRoomByBedType(String bedType) {
		// TODO Auto-generated method stub
		return roomRepository.findByBedType(bedType);
	}

	@Override
	public Optional<Room> getRoomByAmeitiesId(Long ameitiesId) {
		// TODO Auto-generated method stub
		return roomRepository.findByAmenitiesId(ameitiesId);
	}

}

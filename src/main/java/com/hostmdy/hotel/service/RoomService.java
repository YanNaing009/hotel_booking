package com.hostmdy.hotel.service;

import java.util.List;
import java.util.Optional;

import com.hostmdy.hotel.domain.Room;

public interface RoomService {
	
	Room saveRoom(Room room);
	
	Room createRoom(Room room,Long amenitiesId);
	
	List<Room> getAllRoom();
	
	Optional<Room> getRoomById(Long roomId);
	
	List<Room> getRoomByName(String name);
	
	List<Room> getRoomByBedType(String bedType);
	
	Optional<Room> getRoomByAmeitiesId(Long ameitiesId);
	

}

package com.hostmdy.hotel.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.hostmdy.hotel.domain.Room;

public interface RoomRepository extends CrudRepository<Room,Long>{
	
	public List<Room> findByBedType(String bedType);
	
	public List<Room> findByName(String name);
	
	public Optional<Room> findByAmenitiesId(Long amenitiesId);

}

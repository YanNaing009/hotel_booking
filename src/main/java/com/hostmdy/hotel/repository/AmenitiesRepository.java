package com.hostmdy.hotel.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.hostmdy.hotel.domain.Amenities;
import com.hostmdy.hotel.domain.Room;

public interface AmenitiesRepository extends CrudRepository<Amenities, Long>{

	List<Amenities> findByRoom(Room room);
	
	Optional<Amenities> findByRoomId(Long roomId);

}

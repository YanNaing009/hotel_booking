package com.hostmdy.hotel.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.hostmdy.hotel.domain.OrderRoom;

public interface OrderRoomRepository extends CrudRepository<OrderRoom, Long>{
	List<OrderRoom> findByRoomId(Long roomId);
	
	List<OrderRoom> findByClientId(Long clientId);
}

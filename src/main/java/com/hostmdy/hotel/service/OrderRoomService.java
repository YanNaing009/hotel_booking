package com.hostmdy.hotel.service;

import java.util.List;
import java.util.Optional;

import com.hostmdy.hotel.domain.OrderRoom;

public interface OrderRoomService {
	
	OrderRoom saveOrderRoom(OrderRoom orderRoom);
	
	OrderRoom createOrderRoom(OrderRoom orderRoom,Long roomId,Long clientId);
	
	List<OrderRoom> getallOrderRoom();
	
	List<OrderRoom> getOrderRoomByRoomId(Long roomId);
	
	List<OrderRoom> getOrderRoomByClientId(Long clientId);
	
	Optional<OrderRoom> getOrderRoomById(Long orderId);
	
	OrderRoom createOrder(OrderRoom orderRoom);
	
	OrderRoom createOrderRoomWithClientId(OrderRoom orderRoom,Long clientId);
	
	void deleteById(Long orderId);
}

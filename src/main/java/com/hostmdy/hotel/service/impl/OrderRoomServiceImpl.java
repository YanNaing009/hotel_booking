package com.hostmdy.hotel.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hostmdy.hotel.domain.Client;
import com.hostmdy.hotel.domain.OrderRoom;
import com.hostmdy.hotel.domain.Room;
import com.hostmdy.hotel.repository.ClientRepository;
import com.hostmdy.hotel.repository.OrderRoomRepository;
import com.hostmdy.hotel.repository.RoomRepository;
import com.hostmdy.hotel.service.OrderRoomService;

@Service
public class OrderRoomServiceImpl implements OrderRoomService{
	
	private final OrderRoomRepository orderRoomRepository;
	private final RoomRepository roomRepository;
	private final ClientRepository clientRepository;

	public OrderRoomServiceImpl(RoomRepository roomRepository, ClientRepository clientRepository, OrderRoomRepository orderRoomRepository) {
		super();
		this.orderRoomRepository = orderRoomRepository;
		this.roomRepository = roomRepository;
		this.clientRepository = clientRepository;
	}

	@Override
	public OrderRoom saveOrderRoom(OrderRoom orderRoom) {
		// TODO Auto-generated method stub
		return orderRoomRepository.save(orderRoom);
	}

	@Override
	public OrderRoom createOrderRoom(OrderRoom orderRoom, Long roomId, Long clientId) {
		// TODO Auto-generated method stub
		Optional<Room> roomOpt = roomRepository.findById(roomId);
		if(roomOpt.isEmpty()) {
			throw new NullPointerException("Room is Empty!");
		}
		Optional<Client> clientOpt = clientRepository.findById(clientId);
		if(clientOpt.isEmpty()) {
			throw new NullPointerException("Client is Empty!");
		}
		
		Room room = roomOpt.get();
		orderRoom.setRoom(room);
		
		Client client = clientOpt.get();
		orderRoom.setClient(client);
		client.getOrderRooms().add(orderRoom);
		return saveOrderRoom(orderRoom);
	}

	@Override
	public List<OrderRoom> getallOrderRoom() {
		// TODO Auto-generated method stub
		return (List<OrderRoom>) orderRoomRepository.findAll();
	}

	@Override
	public List<OrderRoom> getOrderRoomByRoomId(Long roomId) {
		// TODO Auto-generated method stub
		return orderRoomRepository.findByRoomId(roomId);
	}

	@Override
	public List<OrderRoom> getOrderRoomByClientId(Long clientId) {
		// TODO Auto-generated method stub
		return orderRoomRepository.findByClientId(clientId);
	}

	@Override
	public OrderRoom createOrder(OrderRoom orderRoom) {
		// TODO Auto-generated method stub
		return saveOrderRoom(orderRoom);
	}

	@Override
	public OrderRoom createOrderRoomWithClientId(OrderRoom orderRoom, Long clientId) {
		// TODO Auto-generated method stub
		Optional<Client> clientOpt = clientRepository.findById(clientId);
		if(clientOpt.isEmpty()) {
			throw new NullPointerException();
		}
		Client client = clientOpt.get();
		orderRoom.setClient(client);
		return saveOrderRoom(orderRoom);
	}

	@Override
	public Optional<OrderRoom> getOrderRoomById(Long orderId) {
		// TODO Auto-generated method stub
		return orderRoomRepository.findById(orderId);
	}

	@Override
	public void deleteById(Long orderId) {
		// TODO Auto-generated method stub
		orderRoomRepository.deleteById(orderId);
	}

}

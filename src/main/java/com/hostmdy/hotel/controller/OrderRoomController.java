package com.hostmdy.hotel.controller;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hostmdy.hotel.domain.Client;
import com.hostmdy.hotel.domain.CreditCardInfo;
import com.hostmdy.hotel.domain.OrderRoom;
import com.hostmdy.hotel.domain.Room;
import com.hostmdy.hotel.service.ClientService;
import com.hostmdy.hotel.service.CreditCardInfoService;
import com.hostmdy.hotel.service.OrderRoomService;
import com.hostmdy.hotel.service.RoomService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/orderRoom")
public class OrderRoomController {
	
	private final OrderRoomService orderRoomService;
	
	private final RoomService roomService;
	
	private final ClientService clientService;
	
	private final CreditCardInfoService creditCardInfoService;
	
	public OrderRoomController(OrderRoomService orderRoomService, RoomService roomService, ClientService clientService, CreditCardInfoService creditCardInfoService) {
		super();
		this.orderRoomService = orderRoomService;
		this.roomService = roomService;
		this.clientService = clientService;
		this.creditCardInfoService = creditCardInfoService;
	}

	
	@PostMapping("/add")
	public String addOrderRoom(@ModelAttribute OrderRoom orderRoom,@RequestParam Long clientId,Model model,HttpSession session) {
		if(orderRoom.getChildren() == null) {
			orderRoom.setChildren(0);
		}
		OrderRoom order = new OrderRoom();
		if(clientId == 0) {
			order = orderRoomService.createOrder(orderRoom);
		}else {
			order = orderRoomService.createOrderRoomWithClientId(orderRoom, clientId);
		}	
		
		List<Room> rooms = roomService.getAllRoom();
		
		String username = (String) session.getAttribute("userName");
		Client client = (Client) session.getAttribute("client");
		model.addAttribute("username", username);
		model.addAttribute("client", client);
		
		model.addAttribute("orderRoom", order);
		model.addAttribute("rooms", rooms);
		return "orderRoom/select-room";
	}
	
	@GetMapping("/{roomId}/{orderId}/payment")
	public String Payment(@PathVariable Long roomId,@PathVariable Long orderId,Model model,HttpSession session) {
		Optional<OrderRoom> orderOpt = orderRoomService.getOrderRoomById(orderId);
		if(orderOpt.isEmpty()) {
			throw new NullPointerException("OrderId is empty!");
		}
		Optional<Room> roomOpt = roomService.getRoomById(roomId);
		if(roomOpt.isEmpty()) {
			throw new NullPointerException("RoomId is empty!");
		}
		Room room = roomOpt.get();
		OrderRoom order = orderOpt.get();
		Client client = order.getClient();
		Long clientId = (long) 0;
		if(client != null) {
			clientId = client.getId();
		}
		
		LocalDate checkInDate = order.getCheckIn();
		LocalDate checkOutDate = order.getCheckOut();
		
		String checkInMonth = checkInDate.getMonth().name();
		String checkOutMonth = checkOutDate.getMonth().name();
		
		int checkInDay = checkInDate.getDayOfMonth();
		int checkOutDay = checkOutDate.getDayOfMonth();
		
		int checkInYear = checkInDate.getYear();
		int checkOutYear = checkOutDate.getYear();
		
		String checkIn = checkInMonth.substring(0, 3)+" "+checkInDay+", "+checkInYear;
		String checkOut = checkOutMonth.substring(0, 3)+" "+checkOutDay+", "+checkOutYear;
		
		//change number of night
		Integer night = (int) Duration.between(checkInDate.atStartOfDay(), checkOutDate.atStartOfDay()).toDays();
	 	Integer price = room.getPrice();
	 	Long roomCharge = (long) (night * price);
	 	Long tax = (long) (3000 * night);
	 	Long total = roomCharge+ (3000 * night);
	 	order.setTotalCharge(total);
	 	orderRoomService.createOrder(order);
	 	
	 	String username = (String) session.getAttribute("userName");
	 	
	 	if(session.getAttribute("emailCheck") != null) {
		 	if((boolean) session.getAttribute("emailCheck")) {
		 		model.addAttribute("checkEmail", 0);
		 		session.setAttribute("emailCheck", null);
		 	}else {
		 		model.addAttribute("checkEmail", 1);
		 	}
	 	}
	 	
	 	Optional<CreditCardInfo> cardInfo = creditCardInfoService.getCreditCardInfoByClientId(clientId);
	 	if(cardInfo.isPresent()) {
		 	CreditCardInfo creditCardInfo = cardInfo.get();
		 	model.addAttribute("creditCardInfo", creditCardInfo);
	 	}else {
			model.addAttribute("creditCardInfo", 0);
		}
	 	
	 	
		model.addAttribute("username", username);
		model.addAttribute("client", client);
		
		model.addAttribute("orderRoom", order);
		model.addAttribute("room", room);
		model.addAttribute("checkIn", checkIn);
		model.addAttribute("checkOut", checkOut);
		model.addAttribute("night", night);
		model.addAttribute("roomCharge", roomCharge);
		model.addAttribute("tax", tax);
		model.addAttribute("total", total);
		model.addAttribute("clientId", clientId);
		return "orderRoom/payment-page";
	}
	
	@PostMapping("/{orderId}/{roomId}/finish")
	public String finish(@ModelAttribute Client client,@ModelAttribute CreditCardInfo cardInfo,@PathVariable Long orderId,@PathVariable Long roomId,HttpServletRequest request) {
		String email = client.getEmail();
		Optional<Client> clientOpt = clientService.getClientByEmail(email);
		HttpSession session = request.getSession();
		if(clientOpt.isPresent()) {
			session.setAttribute("emailCheck", true);
			return "redirect:/orderRoom/"+roomId+"/"+orderId+"/payment";
		}
		
		Long clientId = (long) 0;
		
		if(client.getEmail() == null) {
			Client clientSession = (Client) session.getAttribute("client");
			client.setId(clientSession.getId());
			client.setEmail(clientSession.getEmail());
			client.setPassword(clientSession.getPassword());
			client.setRole(clientSession.getRole());
			Client createdClient = clientService.updateClient(client);
			clientId = createdClient.getId();
		}else {
			client.setRole("user");
			Client createdClient = clientService.createClient(client);
			clientId = createdClient.getId();
		}
		
		
		
		creditCardInfoService.createCreditCardInfo(cardInfo, clientId);
		
		Optional<OrderRoom> orderOpt = orderRoomService.getOrderRoomById(orderId);
		if(orderOpt.isEmpty()) {
			throw new NullPointerException("OrderId is null");
		}
		OrderRoom orderRoom = orderOpt.get();
		orderRoomService.createOrderRoom(orderRoom, roomId, clientId);
		return "redirect:/";
	}
	
	@GetMapping("/checkOrder")
	public String checkOrder(HttpSession session,Model model) {
		Client client = (Client) session.getAttribute("client");
		Long clientId = client.getId();
		
		
		List<OrderRoom> deleteOrder = orderRoomService.getallOrderRoom();
		for (OrderRoom orderRoom : deleteOrder) {
			if(orderRoom.getClient() == null || orderRoom.getRoom() == null) {
				orderRoomService.deleteById(orderRoom.getId());
			}
		}
		if(client.getRole() == "admin") {
			List<OrderRoom> orderRoomOpt = orderRoomService.getallOrderRoom();
			if(orderRoomOpt.isEmpty()) {
				model.addAttribute("order", 0);
			}else {
				model.addAttribute("order", 1);
			}
			
			model.addAttribute("oderList", orderRoomOpt);
			model.addAttribute("admin", 1);
		} else {
			List<OrderRoom> orderRoomOpt = orderRoomService.getOrderRoomByClientId(clientId);
			if(orderRoomOpt.isEmpty()) {
				model.addAttribute("order", 0);
			}else {
				model.addAttribute("order", 1);
			}
			model.addAttribute("oderList", orderRoomOpt);
			
		}
		
		LocalDate today = LocalDate.now();
		model.addAttribute("today", today);
		model.addAttribute("client", client);
		String userName = (String) session.getAttribute("userName");
		model.addAttribute("username", userName);
		
		return "orderRoom/check-order-room";
	}
	
}

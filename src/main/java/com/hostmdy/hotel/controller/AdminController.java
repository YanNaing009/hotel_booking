package com.hostmdy.hotel.controller;

import java.io.IOException;
import java.time.LocalDate;
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
import org.springframework.web.multipart.MultipartFile;

import com.hostmdy.hotel.domain.Amenities;
import com.hostmdy.hotel.domain.OrderRoom;
import com.hostmdy.hotel.domain.Room;
import com.hostmdy.hotel.service.AmenitiesService;
import com.hostmdy.hotel.service.ImageService;
import com.hostmdy.hotel.service.OrderRoomService;
import com.hostmdy.hotel.service.RoomService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	private final RoomService roomService;
	private final OrderRoomService orderRoomService;
	private final AmenitiesService amenitiesService;
	private final ImageService imageService;
	
	public AdminController(RoomService roomService, OrderRoomService orderRoomService, AmenitiesService amenitiesService, ImageService imageService) {
		super();
		this.roomService = roomService;
		this.orderRoomService = orderRoomService;
		this.amenitiesService = amenitiesService;
		this.imageService = imageService;
	}

	@GetMapping("/admin-dashboard")
	public String showUpdateRooms(HttpSession session,Model model) {
		String username = (String) session.getAttribute("userName");
		List<Room> rooms = roomService.getAllRoom();
		List<OrderRoom> orderRooms = orderRoomService.getallOrderRoom();
		for (OrderRoom orderRoom : orderRooms) {
			if(orderRoom.getClient() == null || orderRoom.getRoom() == null) {
				orderRoomService.deleteById(orderRoom.getId());
			}
		}
		boolean isOrder = false;
		List<OrderRoom> allOrder = orderRoomService.getallOrderRoom();
		for (OrderRoom orderRoom : allOrder) {
			if(orderRoom.getCheckIn().isAfter(LocalDate.now().minusDays(1))) {
				isOrder = true;
			}
		}
		
		model.addAttribute("isOrder", isOrder);
		model.addAttribute("rooms", rooms);
		model.addAttribute("username", username);
		return "admin/admin-update";
	}
	
	@GetMapping("/{roomId}/update-form")
	public String showUpdateForm(@PathVariable Long roomId,Model model) {
		Optional<Room> roomOpt = roomService.getRoomById(roomId);
		if(roomOpt.isEmpty()) {
			throw new NullPointerException("RoomId is null");
		}
		
		Room room = roomOpt.get();
		
		model.addAttribute("room", room);
		return "admin/update-form";
	}
	
	@PostMapping("/update")
	public String updateRoom(@ModelAttribute Room room,@ModelAttribute Amenities amenities,@RequestParam MultipartFile imagefile) throws IOException {
		Long roomid = room.getId();
		if(!imagefile.isEmpty()) {
			room.setImage(imageService.saveRoomImage(imagefile));
			
		}
		
		System.out.println(amenities);
		amenitiesService.creteAmenities(amenities);
		System.out.println("amenities success");
		System.out.println(room);
		roomService.createRoom(room,amenities.getId());
		System.out.println("Success");
		return "redirect:/admin/admin-dashboard";
	}
	
	@GetMapping("/new")
	public String createNewRoom(Model model) {
		Room room = new Room();
		model.addAttribute("room", room);
		return "admin/create-form";
	}
}

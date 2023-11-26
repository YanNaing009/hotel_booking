package com.hostmdy.hotel.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hostmdy.hotel.domain.Room;
import com.hostmdy.hotel.service.ImageService;
import com.hostmdy.hotel.service.RoomService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/image")
public class ImageController {
	private final ImageService imageService;
	private final RoomService roomService;
	
	public ImageController(ImageService imageService, RoomService roomService) {
		super();
		this.imageService = imageService;
		this.roomService = roomService;
	}
	
	@GetMapping("/room/{roomId}/show")
	public void showRoomImage(@PathVariable Long roomId,HttpServletResponse response) {
		Optional<Room> roomOpt = roomService.getRoomById(roomId);
		if(roomOpt.isEmpty()) {
			throw new RuntimeException("Room is not found!");
		}
		Room room = roomOpt.get();
		
		byte[] imageBytes = new byte[room.getImage().length];
		int i = 0;
		for(final byte b : room.getImage()) {
			imageBytes[i++] = b;
		}
		InputStream ls = new ByteArrayInputStream(imageBytes);
		response.setContentType("image/jpeg");
		try {
			IOUtils.copy(ls, response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

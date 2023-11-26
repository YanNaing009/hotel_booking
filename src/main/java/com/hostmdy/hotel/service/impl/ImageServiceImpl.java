package com.hostmdy.hotel.service.impl;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hostmdy.hotel.domain.Room;
import com.hostmdy.hotel.repository.RoomRepository;
import com.hostmdy.hotel.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService{
	
	private final RoomRepository roomRepository;

	public ImageServiceImpl(RoomRepository roomRepository) {
		super();
		this.roomRepository = roomRepository;
	}

	@Override
	public Byte[] saveRoomImage(MultipartFile imageFile) throws IOException {
		// TODO Auto-generated method stub
		
		Byte[] imageBytes = new Byte[imageFile.getBytes().length];
		int i = 0;
		for(final Byte b : imageFile.getBytes()) {
			imageBytes[i++] = b;
		}
		
		return imageBytes;
	}
	
}

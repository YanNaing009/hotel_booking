package com.hostmdy.hotel.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

	Byte[] saveRoomImage(MultipartFile imageFile) throws IOException;
}

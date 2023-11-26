package com.hostmdy.hotel.bootstrap;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.hostmdy.hotel.crypto.PasswordEncoder;
import com.hostmdy.hotel.domain.Amenities;
import com.hostmdy.hotel.domain.Client;
import com.hostmdy.hotel.domain.OrderRoom;
import com.hostmdy.hotel.domain.Room;
import com.hostmdy.hotel.repository.AmenitiesRepository;
import com.hostmdy.hotel.repository.ClientRepository;
import com.hostmdy.hotel.repository.RoomRepository;

@Component
public class HotelBootstrap implements ApplicationListener<ContextRefreshedEvent>{
	
	@Autowired
	public RoomRepository roomRepository;
	
	@Autowired
	public AmenitiesRepository amenitiesRepository;
	
	@Autowired
	public ClientRepository clientRepository;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO Auto-generated method stub
		try {
			clientRepository.saveAll(create());
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private List<Client> create() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		Room room1 = new Room();
		room1.setName("Normal Deluxe Double");
		room1.setBedType("Twin");
		room1.setRoomSize(36.6);
		room1.setInformation("City View");
		room1.setPrice(50000);
		
		Amenities amenities1 = new Amenities();
		amenities1.setGeneral("43” LED TV, Safe, Flashlight, Iron and ironing board, Wooden clothes brush, Shoe horn, Shoe polish cloth, Alarm clock, Emergency escape route, Scale, Slippers");
		amenities1.setBathRoom("Bathroom amenities, Disposable toothbrushes and toothpaste, Razor, Bathrobe, Bathtub, Shower, Hair dryer");
		amenities1.setOther("Cable Satellite TV channels, Coffee and tea maker, Four bottles of complimentary mineral water daily, Voicemail Service, Stationary");
		
		amenities1.getRoom().add(room1);
		room1.setAmenities(amenities1);

		room1.setImage(saveImage("src/main/resources/image/bedRoom.jpg"));
		
		Room room2 = new Room();
		room2.setName("Deluxe Twin");
		room2.setBedType("Two Single");
		room2.setRoomSize(36.6);
		room2.setInformation("City View");
		room2.setPrice(50000);
		
		Amenities amenities2 = new Amenities();
		amenities2.setGeneral("43” LED TV, Safe, Flashlight, Iron and ironing board, Wooden clothes brush, Shoe horn, Shoe polish cloth, Alarm clock, Mini Bar, Emergency escape route, Scale, Slippers");
		amenities2.setBathRoom("Bathroom amenities, Disposable toothbrushes and toothpaste, Razor, Bathrobe, Bathtub, Shower, Hair dryer");
		amenities2.setOther("Cable Satellite TV channels, Coffee and tea maker, Four bottles of complimentary mineral water daily, Voicemail Service, Stationary");
		
		amenities2.getRoom().add(room2);
		room2.setAmenities(amenities2);
		
		room2.setImage(saveImage("src/main/resources/image/twoSingle.jpg"));
		
		List<Amenities> amenitie = new ArrayList<>();
		amenitie.add(amenities1);
		amenitie.add(amenities2);
		
		amenitiesRepository.saveAll(amenitie);
		
		Client client1 = new Client();
		client1.setFirstName("Yan");
		client1.setLastName("Naing");
		client1.setEmail("yannaing@gmail.com");
		client1.setRole("admin");
		client1.setPassword(passwordEncode("yannaing"));
		client1.setContact("09123456789");
		
		Client client2 = new Client();
		client2.setFirstName("Kyaw");
		client2.setLastName("Kyaw");
		client2.setEmail("kyaw@gmail.com");
		client2.setRole("user");
		client2.setPassword(passwordEncode("1234"));
		client2.setContact("09123456789");
		
		
		OrderRoom orderRoom1 = new OrderRoom();
		orderRoom1.setAdult(2);
		orderRoom1.setChildren(1);
		orderRoom1.setCheckIn(LocalDate.of(2023, 11, 2));
		orderRoom1.setCheckOut(LocalDate.of(2023, 11, 5));
		orderRoom1.setTotalCharge((long) 150000);
		orderRoom1.setClient(client2);
		client2.getOrderRooms().add(orderRoom1);
		orderRoom1.setRoom(room2);
		room2.getOrderRooms().add(orderRoom1);
		
		List<Client> clients = new ArrayList<>();
		clients.add(client1);
		clients.add(client2);
		
		
		
		return clients;
	}
	
	
	
	
	//image bootstrap
	private Byte[] saveImage(String imageFilePath) throws IOException {
		FileInputStream inputFile = new FileInputStream(new File(imageFilePath));
		
		BufferedInputStream bufferedInputStream = new BufferedInputStream(inputFile);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[4000];
		int readByteNo = 0;
		while ((readByteNo = bufferedInputStream.read(buffer,0,buffer.length)) != -1) {
			baos.write(buffer, 0, readByteNo);
		}
		byte[] responseArray = baos.toByteArray();
		
		Byte[] imageBytes = new Byte[responseArray.length];
		int i = 0;
		for(final Byte b : responseArray) {
			imageBytes[i++] = b;
		}
		return imageBytes;
	}
	
	//password encode
	private String passwordEncode(String rawpassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
		String password = PasswordEncoder.encode(rawpassword);
		return password;
	}
}

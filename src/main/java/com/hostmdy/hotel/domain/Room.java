package com.hostmdy.hotel.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String bedType;
	private Integer price;
	
	@Lob
	private Byte[] image;
	
	private String information;
	private Double roomSize;
	
	@ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
	@JoinColumn(name = "amenities_id")
	private Amenities amenities;
	
	@OneToMany(mappedBy = "room",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<OrderRoom> orderRooms = new ArrayList<>();
	
	public Room() {}

	public Room(String name, String bedType, Integer price, Byte[] image, String information, Double roomSize,
			Amenities amenities, List<OrderRoom> orderRooms) {
		super();
		this.name = name;
		this.bedType = bedType;
		this.price = price;
		this.image = image;
		this.information = information;
		this.roomSize = roomSize;
		this.amenities = amenities;
		this.orderRooms = orderRooms;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBedType() {
		return bedType;
	}

	public void setBedType(String bedType) {
		this.bedType = bedType;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Byte[] getImage() {
		return image;
	}

	public void setImage(Byte[] image) {
		this.image = image;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public Double getRoomSize() {
		return roomSize;
	}

	public void setRoomSize(Double roomSize) {
		this.roomSize = roomSize;
	}

	public Amenities getAmenities() {
		return amenities;
	}

	public void setAmenities(Amenities amenities) {
		this.amenities = amenities;
	}

	public List<OrderRoom> getOrderRooms() {
		return orderRooms;
	}

	public void setOrderRooms(List<OrderRoom> orderRooms) {
		this.orderRooms = orderRooms;
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", name=" + name + ", bedType=" + bedType + ", price=" + price + ", image="
				+ Arrays.toString(image) + ", information=" + information + ", roomSize=" + roomSize + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bedType == null) ? 0 : bedType.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + Arrays.hashCode(image);
		result = prime * result + ((information == null) ? 0 : information.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((roomSize == null) ? 0 : roomSize.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Room other = (Room) obj;
		if (bedType == null) {
			if (other.bedType != null)
				return false;
		} else if (!bedType.equals(other.bedType))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (!Arrays.equals(image, other.image))
			return false;
		if (information == null) {
			if (other.information != null)
				return false;
		} else if (!information.equals(other.information))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (roomSize == null) {
			if (other.roomSize != null)
				return false;
		} else if (!roomSize.equals(other.roomSize))
			return false;
		return true;
	}

	
}

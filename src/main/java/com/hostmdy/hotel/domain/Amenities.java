package com.hostmdy.hotel.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;

@Entity
public class Amenities {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Lob
	private String general;
	
	@Lob
	private String bathRoom;
	
	@Lob
	private String other;
	
	@OneToMany(mappedBy = "amenities",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<Room> room = new ArrayList<>();
	
	public Amenities() {}



	public Amenities(String general, String bathRoom, String other, List<Room> room) {
		super();
		this.general = general;
		this.bathRoom = bathRoom;
		this.other = other;
		this.room = room;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGeneral() {
		return general;
	}

	public void setGeneral(String general) {
		this.general = general;
	}

	public String getBathRoom() {
		return bathRoom;
	}

	public void setBathRoom(String bathRoom) {
		this.bathRoom = bathRoom;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public List<Room> getRoom() {
		return room;
	}

	public void setRoom(List<Room> room) {
		this.room = room;
	}
	
	@Override
	public String toString() {
		return "Amenities [id=" + id + ", general=" + general + ", bathRoom=" + bathRoom + ", other=" + other + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bathRoom == null) ? 0 : bathRoom.hashCode());
		result = prime * result + ((general == null) ? 0 : general.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((other == null) ? 0 : other.hashCode());
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
		Amenities other = (Amenities) obj;
		if (bathRoom == null) {
			if (other.bathRoom != null)
				return false;
		} else if (!bathRoom.equals(other.bathRoom))
			return false;
		if (general == null) {
			if (other.general != null)
				return false;
		} else if (!general.equals(other.general))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (this.other == null) {
			if (other.other != null)
				return false;
		} else if (!this.other.equals(other.other))
			return false;
		return true;
	}
	
	
}

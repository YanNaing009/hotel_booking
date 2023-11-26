package com.hostmdy.hotel.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.hostmdy.hotel.domain.Client;

public interface ClientRepository extends CrudRepository<Client, Long>{
	
	Optional<Client> findByEmail(String email);
	
	List<Client> findByRole(String role);
}

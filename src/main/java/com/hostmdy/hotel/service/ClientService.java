package com.hostmdy.hotel.service;

import java.util.List;
import java.util.Optional;

import com.hostmdy.hotel.domain.Client;

public interface ClientService {
	Client saveClient(Client client);
	
	Client createClient(Client client);
	
	Client updateClient(Client client);
	
	Optional<Client> getClientById(Long clientId);
	
	List<Client> getAllClient();
	
	Optional<Client> getClientByEmail(String email);
	
	List<Client> getClientByRole(String role);
	
}

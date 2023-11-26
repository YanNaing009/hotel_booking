package com.hostmdy.hotel.service.impl;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hostmdy.hotel.crypto.PasswordEncoder;
import com.hostmdy.hotel.domain.Client;
import com.hostmdy.hotel.repository.ClientRepository;
import com.hostmdy.hotel.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService{
	
	private final ClientRepository clientRepository;

	public ClientServiceImpl(ClientRepository clientRepository) {
		super();
		this.clientRepository = clientRepository;
	}

	@Override
	public Client saveClient(Client client) {
		// TODO Auto-generated method stub
		return clientRepository.save(client);
	}

	@Override
	public Client createClient(Client client) {
		// TODO Auto-generated method stub
		try {
			String password = PasswordEncoder.encode(client.getPassword());
			client.setPassword(password);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client.setRole("user");
		return saveClient(client);
	}

	@Override
	public Optional<Client> getClientById(Long clientId) {
		// TODO Auto-generated method stub
		return clientRepository.findById(clientId);
	}

	@Override
	public List<Client> getAllClient() {
		// TODO Auto-generated method stub
		return (List<Client>) clientRepository.findAll();
	}

	@Override
	public Optional<Client> getClientByEmail(String email) {
		// TODO Auto-generated method stub
		return clientRepository.findByEmail(email);
	}

	@Override
	public List<Client> getClientByRole(String role) {
		// TODO Auto-generated method stub
		return clientRepository.findByRole(role);
	}

	@Override
	public Client updateClient(Client client) {
		
		return saveClient(client);
	}

}

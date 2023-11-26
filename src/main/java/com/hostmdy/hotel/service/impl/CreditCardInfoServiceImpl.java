package com.hostmdy.hotel.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hostmdy.hotel.domain.Client;
import com.hostmdy.hotel.domain.CreditCardInfo;
import com.hostmdy.hotel.repository.ClientRepository;
import com.hostmdy.hotel.repository.CreditCardInfoRepository;
import com.hostmdy.hotel.service.CreditCardInfoService;

@Service
public class CreditCardInfoServiceImpl implements CreditCardInfoService{
	
	private final CreditCardInfoRepository creditCardInfoRepository;
	private final ClientRepository clientRepository;

	public CreditCardInfoServiceImpl(CreditCardInfoRepository creditCardInfoRepository,
			ClientRepository clientRepository) {
		super();
		this.creditCardInfoRepository = creditCardInfoRepository;
		this.clientRepository = clientRepository;
	}

	@Override
	public CreditCardInfo saveCreditCardInfo(CreditCardInfo creditCardInfo) {
		// TODO Auto-generated method stub
		return creditCardInfoRepository.save(creditCardInfo);
	}

	@Override
	public CreditCardInfo createCreditCardInfo(CreditCardInfo creditCardInfo, Long clientId) {
		// TODO Auto-generated method stub
		Optional<Client> clientOpt = clientRepository.findById(clientId);
		
		if(clientOpt.isEmpty()) {
			throw new NullPointerException("Client is Empty!");
		}
		
		Client client = clientOpt.get();
		creditCardInfo.setClient(client);
		return saveCreditCardInfo(creditCardInfo);
	}

	@Override
	public Optional<CreditCardInfo> getCreditCardInfoByClientId(Long clientId) {
		// TODO Auto-generated method stub
		return creditCardInfoRepository.findByClientId(clientId);
	}

}

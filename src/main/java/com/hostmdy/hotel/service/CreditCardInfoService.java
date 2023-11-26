package com.hostmdy.hotel.service;

import java.util.Optional;

import com.hostmdy.hotel.domain.CreditCardInfo;

public interface CreditCardInfoService {
	
	CreditCardInfo saveCreditCardInfo(CreditCardInfo creditCardInfo);
	
	CreditCardInfo createCreditCardInfo(CreditCardInfo creditCardInfo,Long clientId);
	
	Optional<CreditCardInfo> getCreditCardInfoByClientId(Long clientId);
}

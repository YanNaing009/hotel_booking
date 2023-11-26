package com.hostmdy.hotel.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.hostmdy.hotel.domain.CreditCardInfo;

public interface CreditCardInfoRepository extends CrudRepository<CreditCardInfo, Long>{
	Optional<CreditCardInfo> findByClientId(Long clientId);
}

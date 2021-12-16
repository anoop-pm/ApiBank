package com.bank.banktransaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.banktransaction.model.User;
import com.bank.banktransaction.repository.BankRepository;

@Service
@Transactional
public class BankService {
	
	@Autowired
	private BankRepository bankRepository;
	
    public void saveuser(User user) {
    	bankRepository.save(user);
    

}
}

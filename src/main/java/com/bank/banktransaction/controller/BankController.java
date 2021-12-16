package com.bank.banktransaction.controller;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.banktransaction.model.User;

import com.bank.banktransaction.service.BankService;

@RestController
@RequestMapping("/bankapi")
public class BankController {
	


	
    @Autowired
    private BankService service;
		
		// create user
		@PostMapping("/reg")
		public User createUser(@RequestBody User user) {
			service.saveuser(user);
			return user;
		}

}

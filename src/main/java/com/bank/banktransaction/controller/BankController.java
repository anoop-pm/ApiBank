package com.bank.banktransaction.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.banktransaction.model.AddAmount;
import com.bank.banktransaction.model.TransactionDetails;
import com.bank.banktransaction.model.User;
import com.bank.banktransaction.repository.addamountupdateRepository;
//import com.bank.banktransaction.repository.addamountupdateRepository;
import com.bank.banktransaction.service.BankService;

@RestController
@RequestMapping("/bankapi")
public class BankController {
	
//	 private static final Logger logger = LogManager.getLogger(BankController.class);
//	
//

	
    @Autowired
    private BankService service;
    
	@Autowired
	private addamountupdateRepository balanceRepostory;
	

	
	
		
		// Register a User
		@PostMapping("/user")
		public User createUser(@RequestBody User user) {
			service.saveuser(user);
//			logger.debug("Created A user",user);

			return user;
		}
		
		// Add UserAccount
		@PostMapping("/add")
		public AddAmount add(@RequestBody AddAmount amount) {
			service.addamount(amount);
		
			return amount;
		}
		
//		
//		@PutMapping("/balance/{balance}/{userid}")
//		public ResponseEntity<String> updatePriceByName(@PathVariable int balance, @PathVariable int userid) {
//			return new ResponseEntity<String>(balanceRepostory.updatebalance(balance, userid)+balance+"Credited .", HttpStatus.OK);
//		}
		
//		@PutMapping("/deposit")
//		public ResponseEntity<String> updatePriceByName(@PathVariable int balance, @PathVariable int userid) {
//			return new ResponseEntity<String>(balanceRepostory.updatebalance(balance, userid)+balance+"Credited .", HttpStatus.OK);
//		}
		
		@PutMapping("/deposit")
		public String deposit(@RequestBody AddAmount amount) {
			service.deposit(amount) ;
			System.out.println("Credited");
			return "Credited :- "+amount.getBalance();
		}
		
		
		
		}


		
		

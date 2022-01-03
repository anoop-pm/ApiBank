package com.bank.banktransaction.controller;



import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.bank.banktransaction.model.AddAccount;
import com.bank.banktransaction.model.AddAmount;
import com.bank.banktransaction.model.TransactionDetails;
import com.bank.banktransaction.model.User;
import com.bank.banktransaction.repository.GetamountbalanceRepository;
import com.bank.banktransaction.repository.findUserid;
import com.bank.banktransaction.repository.findaccountnumberRepository;
//import com.bank.banktransaction.repository.addamountupdateRepository;
import com.bank.banktransaction.service.BankService;

@RestController
@RequestMapping("/bankapi")
public class BankController {
	
//	 private static final Logger logger = LogManager.getLogger(BankController.class);
//	
//
	
	@Autowired
	private findUserid finduserid;

	@Autowired
	private	findaccountnumberRepository accontno;
    @Autowired
    private BankService service;
    
    @Autowired
	private GetamountbalanceRepository balancerepository;
	
		
		// Register a User
		@PostMapping("/user")
		public ResponseEntity<String> createUser(@Valid @RequestBody User user) {
			
			String savedUser = service.saveuser(user);
			return new ResponseEntity<String>(savedUser, HttpStatus.CREATED);
		}
		
		// Add UserAccount
		@PostMapping("/useraccount")
		public ResponseEntity<String> add(@Valid @RequestBody AddAmount amount) {
			
				String Depositreport=service.addamount(amount);
			
			return new ResponseEntity<String>(Depositreport, HttpStatus.CREATED);
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
		
		String result=	service.deposit(amount) ;
			
			return result;
		
			}
		
		
		
		
		
//		@PutMapping("/deposit")
//		 ResponseEntity<String> deposit(@RequestBody AddAmount amount) {
//
//			service.deposit(amount) ;
//			System.out.println("Credited");
//			return ResponseEntity.ok("User is valid");
//		}
		
		
		@PostMapping("/addaccount")
		public ResponseEntity<String> add(@Valid @RequestBody AddAccount addAccount) {
			
			
			String result = service.addAccount(addAccount);
		
			return new ResponseEntity<String>(result, HttpStatus.CREATED);
		}
		
		
		@PutMapping("/transferamount")
		public String tranfer(@RequestBody TransactionDetails amounttransfer) {
		
			
			try {
				service.creditamount(amounttransfer) ;
			Thread.sleep(7000); 
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
		   String report=service.messagereader();
			return report;
		
			}
		
		
		@GetMapping("/checkbalance")
		public ResponseEntity<String> balancec(@Valid @RequestBody AddAmount amount) {
			
			int b=service.balancechek(amount);
			
			String report=null;
			if (b==0)
					{
				report="not valid account number";
					}
			else {
				report=String.valueOf(b);
			}

			return new ResponseEntity<String>(String.valueOf(report), HttpStatus.CREATED);
		}
		
		
		}


		
		


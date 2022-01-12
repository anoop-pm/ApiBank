package com.bank.banktransaction.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.json.JSONObject;
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
import com.bank.banktransaction.repository.FindUserdetails;
import com.bank.banktransaction.repository.GetamountbalanceRepository;
import com.bank.banktransaction.repository.findUserid;
import com.bank.banktransaction.repository.findaccountnumberRepository;
//import com.bank.banktransaction.repository.addamountupdateRepository;
import com.bank.banktransaction.service.BankService;

@RestController
@RequestMapping("/bankapi")
public class BankController {

	@Autowired
	private findUserid finduserid;

	@Autowired
	private findaccountnumberRepository accontno;
	@Autowired
	private BankService service;

	@Autowired
	private GetamountbalanceRepository balancerepository;
	@Autowired
	private FindUserdetails findusers;

	// Register a User
	@PostMapping("/user")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {

		Object savedUser = service.saveuser(user);

		return new ResponseEntity<Object>(savedUser, HttpStatus.CREATED);
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
	public ResponseEntity<Object> deposit(@Valid @RequestBody AddAmount amount) {

		Object result = service.deposit(amount);

		return new ResponseEntity<Object>(result, HttpStatus.CREATED);

	}

//		@PutMapping("/deposit")
//		 ResponseEntity<String> deposit(@RequestBody AddAmount amount) {
//
//			service.deposit(amount) ;
//			System.out.println("Credited");
//			return ResponseEntity.ok("User is valid");
//		}

	// Add new Accounts to transfer
	@PostMapping("/addaccount")
	public ResponseEntity<Object> addaccount(@Valid @RequestBody AddAccount addAccount) {

		Object savedaccount = service.addAccount(addAccount);

		return new ResponseEntity<Object>(savedaccount, HttpStatus.CREATED);
	}

	// Transfer Amount to other account
	@PutMapping("/transferamount")
	public ResponseEntity<Object> tranfer(@Valid @RequestBody TransactionDetails amounttransfer) {

		try {
			service.creditamount(amounttransfer);
			Thread.sleep(7000);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String report = service.messagereader();
		String status = service.statusread();
		Map<String, String> map = new HashMap<>();
		map.put("Status", status);
		map.put("Message", report);
		return new ResponseEntity<Object>(map, HttpStatus.CREATED);

	}

	@GetMapping("/checkbalance")
	public ResponseEntity balancec(@Valid @RequestBody AddAmount amount) {

		int b = service.balancechek(amount);
		Map<String, String> map = new HashMap<>();

		if (b == 0) {
			map.put("Account Number ", "Not Found ,Please check your Account Number");
			map.put("Status", "404 ");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
		} else {
			map.put("Your Account balance is", String.valueOf(b));
			map.put("Status", "200 ");
			return ResponseEntity.status(HttpStatus.OK).body(map);

		}

	}

}

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

import com.bank.banktransaction.constants.BankConstant;
import com.bank.banktransaction.model.AddAccount;
import com.bank.banktransaction.model.AddAmount;
import com.bank.banktransaction.model.TransactionDetails;
import com.bank.banktransaction.model.User;
import com.bank.banktransaction.repository.FindUserdetails;
import com.bank.banktransaction.repository.GetamountbalanceRepository;
import com.bank.banktransaction.repository.FindUserid;
import com.bank.banktransaction.repository.FindAccountnumberRepository;
//import com.bank.banktransaction.repository.addamountupdateRepository;
import com.bank.banktransaction.service.BankService;

@RestController
@RequestMapping("/bankapi")
public class BankController {

	@Autowired
	private FindUserid finduserid;

	@Autowired
	private FindAccountnumberRepository accontno;
	@Autowired
	private BankService service;

	@Autowired
	private GetamountbalanceRepository balancerepository;
	@Autowired
	private FindUserdetails findusers;

	// Register a User
	
	/**URL POST:http://localhost:8080/bankapi/user */
	@PostMapping("/user")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {

		Object savedUser = service.saveuser(user);

		return new ResponseEntity<Object>(savedUser, HttpStatus.CREATED);
	}

	// Deposit Amount to UserAccount
	/**URL PUT:http://localhost:8080/bankapi/deposit */
	@PutMapping("/deposit")
	public ResponseEntity<Object> deposit(@Valid @RequestBody AddAmount amount) {

		Object result = service.deposit(amount);

		return new ResponseEntity<Object>(result, HttpStatus.CREATED);

	}

	// Add new Accounts to transfer
	/**URL POST:http://localhost:8080/bankapi/addaccount */
	@PostMapping("/addaccount")
	public ResponseEntity<Object> addaccount(@Valid @RequestBody AddAccount addAccount) {

		Object savedaccount = service.addAccount(addAccount);

		return new ResponseEntity<Object>(savedaccount, HttpStatus.CREATED);
	}

	// Transfer Amount to other account
	/**URL PUT:http://localhost:8080/bankapi/transferamount */
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
		map.put(BankConstant.status, status);
		map.put(BankConstant.messages, report);
		return new ResponseEntity<Object>(map, HttpStatus.CREATED);

	}

	// Checking Balance
	/**URL GET:http://localhost:8080/bankapi/checkbalance */
	@GetMapping("/checkbalance")
	public ResponseEntity balancec(@Valid @RequestBody AddAmount amount) {

		int b = service.balancechek(amount);
		Map<String, String> map = new HashMap<>();

		if (b == 0) {
			map.put(BankConstant.accountno, BankConstant.notfoundaccountnumber);
			map.put(BankConstant.status, BankConstant.error);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
		} else {
			map.put(BankConstant.messages, BankConstant.balance + String.valueOf(b));
			map.put(BankConstant.status, BankConstant.ok);
			return ResponseEntity.status(HttpStatus.OK).body(map);

		}

	}

}

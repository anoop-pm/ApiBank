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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bank.banktransaction.constants.BankConstant;
import com.bank.banktransaction.model.AddAccount;
import com.bank.banktransaction.model.AddAmount;
import com.bank.banktransaction.model.TransactionDetails;
import com.bank.banktransaction.model.User;
import com.bank.banktransaction.repository.FindUserdetails;
import com.bank.banktransaction.repository.GetamountbalanceRepository;
import com.bank.banktransaction.repository.FindUserid;
import com.bank.banktransaction.repository.BankRepository;
import com.bank.banktransaction.repository.FindAccountnumberRepository;
//import com.bank.banktransaction.repository.addamountupdateRepository;
import com.bank.banktransaction.service.BankService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping("/bankapi")
@Api(value="Banking Solutions", description="Operations for Transfer money One Account to Another Account")
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
	
	@Autowired
	private BankRepository bank;
	
	
	@ApiOperation(value = "View a list of available employees", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@GetMapping("/alluser")
	public List<User> getAllUsers() {
		return bank.findAll();
	}

	// Register a User
	
	/**URL POST:http://localhost:8080/bankapi/user */
	@PostMapping("/user")
	@ApiOperation(value = "Register A User")
	public ResponseEntity<Object> createUser(@ApiParam(value = "Registe User object store in database table", required = true) @Valid @RequestBody User user) {

		Object savedUser = service.saveuser(user);

		return new ResponseEntity<Object>(savedUser, HttpStatus.CREATED);
	}

	// Deposit Amount to UserAccount
	/**URL PUT:http://localhost:8080/bankapi/deposit */
	@PutMapping("/deposit")
	@ApiOperation(value = "Deposit Amount")
	public ResponseEntity<Object> deposit( @ApiParam(value = "Update Balnce and store in database table", required = true)@Valid @RequestBody AddAmount amount) {

		Object result = service.deposit(amount);

		return new ResponseEntity<Object>(result, HttpStatus.CREATED);

	}


	
	// Add new Accounts to transfer
	/**URL POST:http://localhost:8080/bankapi/addaccount */
	@PostMapping("/addaccount")
	@ApiOperation(value = "Add a Transfer account")
	public ResponseEntity<Object> addaccount(@ApiParam(value = "AddAccount object store in database table", required = true) @Valid @RequestBody AddAccount addAccount) {

		Object savedaccount = service.addAccount(addAccount);

		return new ResponseEntity<Object>(savedaccount, HttpStatus.CREATED);
	}

	// Transfer Amount to other account
	/**URL PUT:http://localhost:8080/bankapi/transferamount */
	@PutMapping("/transferamount")
	@ApiOperation(value = " Transfer Amount")
	public ResponseEntity<Object> tranfer(@ApiParam(value = "Transfer Amount for validation", required = true) @Valid @RequestBody TransactionDetails amounttransfer) {

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
	@ApiOperation(value = "Balance Check")
	public ResponseEntity balancec(@ApiParam(value = "Balance Check", required = true) @Valid @RequestBody AddAmount amount) {

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

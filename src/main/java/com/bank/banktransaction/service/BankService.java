package com.bank.banktransaction.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bank.banktransaction.model.AddAmount;
import com.bank.banktransaction.model.TransactionDetails;
import com.bank.banktransaction.model.User;
import com.bank.banktransaction.repository.BankRepository;
import com.bank.banktransaction.repository.addamountRepository;
import com.bank.banktransaction.repository.addamountupdateRepository;
import com.bank.banktransaction.repository.transactionRepository;


@Service
@Transactional
public class BankService {
	
	@Autowired
	private BankRepository bankRepository;
	
	@Autowired
	private addamountRepository amountRepostory;
	
	@Autowired
	private addamountupdateRepository balanceRepostory;
	
	@Autowired
	private transactionRepository transactionrepostory;
		
	
    public void saveuser(User user) {
    	bankRepository.save(user);
    

}
    
    public void addamount(AddAmount addAmount) {
    	amountRepostory.save(addAmount);
    

}
    
    
//    public void transaction(TransactionDetails transaction) {
//    	
//    	
//    	transactionrepostory.save(transaction);
//    
//
//}
    
    public void deposit(AddAmount addAmount) {
balanceRepostory.updatebalance(addAmount.getBalance(),addAmount.getUserid()) ;
//transaction.setUserid(addAmount.getUserid());
//transaction.setAccountnumber(addAmount.getAccountnumber());
//transaction.setReceiveraccount(addAmount.getAccountnumber());
//transaction.setAmount(addAmount.getBalance());
//transaction.setDetails("Credit");
// TransactionDetails transaction
//Timestamp timestamp=new Timestamp(System.currentTimeMillis());
//String instanceNow3 = timestamp.toString();
//transaction.setTime(instanceNow3);
//transactionrepostory.save(transaction);
    }
//	public void getUserById(long id) {
//		Optional<AddAmount> optional = amountRepostory.findById(id);
//		AddAmount addamount = null;
//		if (optional.isPresent()) {
//			addamount = optional.get();
//		} else {
//			throw new RuntimeException(" AddAmount not found for id :: " + id);
//		}
//		
//}
	}
package com.bank.banktransaction.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bank.banktransaction.constants.BankConstant;
import com.bank.banktransaction.model.AddAccount;
import com.bank.banktransaction.model.AddAmount;
import com.bank.banktransaction.model.TransactionDetails;
import com.bank.banktransaction.model.User;
import com.bank.banktransaction.repository.BankRepository;
import com.bank.banktransaction.repository.addAccountRepositorry;
import com.bank.banktransaction.repository.addamountRepository;
import com.bank.banktransaction.repository.addamountupdateRepository;
import com.bank.banktransaction.repository.findUserid;
import com.bank.banktransaction.repository.findaccountnumberRepository;
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
	
	
	@Autowired
	private addAccountRepositorry accountrepository;
	
	
	@Autowired
	private findaccountnumberRepository findaccountnumber;
	
	@Autowired
	private findUserid finduser;
		
		
	
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
    	
  //validation

 int accountno=0;
 int userid=0;
try {
accountno=findaccountnumber.getaccountnumber(addAmount.getAccountnumber());
userid=finduser.getuserid(addAmount.getAccountnumber());
addAmount.setUserid(userid);
balanceRepostory.updatebalance(addAmount.getBalance(),addAmount.getAccountnumber(),userid) ;

System.out.println(userid);
}
catch (Exception e) {
	System.out.println("The Account Number Not Match"+e.getMessage());
	
	System.out.println(userid);
}

int getaccountno=addAmount.getAccountnumber();
if (accountno == getaccountno){
   TransactionDetails transaction = new TransactionDetails();
   //code to find user id
  transaction.setUserid(userid);
  transaction.setAccountnumber(addAmount.getAccountnumber());
  transaction.setReceiveraccount(addAmount.getAccountnumber());
  transaction.setBalance(addAmount.getBalance());

  
  transaction.setDetails(BankConstant.credit);//constant

  Timestamp timestamp=new Timestamp(System.currentTimeMillis());
  String Time = timestamp.toString();
  transaction.setTime(Time);
  transactionrepostory.save(transaction);
}
else {
	System.out.println("The Account Number Not Match");
}
	}
    
    public void addAccount(AddAccount addAccount) {
    	accountrepository.save(addAccount);
    

}
}
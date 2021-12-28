package com.bank.banktransaction.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
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
import com.bank.banktransaction.repository.GetamountbalanceRepository;
import com.bank.banktransaction.repository.addAccountRepositorry;
import com.bank.banktransaction.repository.addamountRepository;
import com.bank.banktransaction.repository.addamountupdateRepository;
import com.bank.banktransaction.repository.findUserid;
import com.bank.banktransaction.repository.findaccountnumberRepository;
import com.bank.banktransaction.repository.findreceiveraccnoRepository;
import com.bank.banktransaction.repository.transactionRepository;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;


@Service
@Transactional
public class BankService {
	
	@Autowired
	private BankRepository bankRepository;
	
	@Autowired
	private addamountRepository amountRepostory;
	
	@Autowired
	private addamountupdateRepository depositRepostory;
	
	@Autowired
	private transactionRepository transactionrepostory;
	
	
	@Autowired
	private addAccountRepositorry accountrepository;
	
	
	@Autowired
	private findaccountnumberRepository findaccountnumber;
	
	@Autowired
	private findUserid finduser;
	
	
	@Autowired
	private GetamountbalanceRepository balancerepository;
		

	
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
depositRepostory.updatebalance(addAmount.getDeposit(),addAmount.getAccountnumber(),userid) ;

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
  transaction.setAmount(addAmount.getDeposit());
  

  
  transaction.setDetails(BankConstant.credit);//constant
  
  
  
  

  Timestamp timestamp=new Timestamp(System.currentTimeMillis());
  String Time = timestamp.toString();
  transaction.setTime(Time);
  
 
  
  
 //kstreaam
  
  Properties properties = new Properties();

  // kafka bootstrap server
  properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
  properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
  properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
  // producer acks
  properties.setProperty(ProducerConfig.ACKS_CONFIG, "all"); // strongest producing guarantee
  properties.setProperty(ProducerConfig.RETRIES_CONFIG, "3");
  properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, "1");
  // leverage idempotent producer from Kafka 0.11 !
  properties.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true"); // ensure we don't push duplicates

  Producer<String, String> producer = new KafkaProducer<>(properties);

int abcd=addAmount.getAccountnumber();
Integer obj = new Integer(abcd);
String str4 = obj.toString();


int dep=addAmount.getDeposit();
Integer obj2 = new Integer(dep);
String str5 = obj2.toString();

      try {
          producer.send(bankTransaction(str4,str5));
          Thread.sleep(100);


      } catch (InterruptedException e) {

      }

  producer.close();

//

  
  //end
  
  
  
  transactionrepostory.save(transaction);
}
else {
	System.out.println("The Account Number Not Match");
}
	}




    
    public void addAccount(AddAccount addAccount) {
    		
    	accountrepository.save(addAccount);
    	
}
    
    
    public void creditamount(TransactionDetails transferamount) {
      	int sender=0;
      	int receiver=0;
      	int accountbalance=0;
    	try {
    
    	 sender=findaccountnumber.getaccountnumber(transferamount.getAccountnumber());
    	 receiver=findaccountnumber.getreceiveraccountnumber(transferamount.getReceiveraccount());
    	 accountbalance=balancerepository.getamount(transferamount.getAccountnumber());
    	
    	}
    	catch(Exception e) 
    	{
    		
    		System.out.println(e.getMessage());
    	}
    	
    	
    	int sendamount=transferamount.getAmount();
    	
    
    	
    	if((sender==transferamount.getAccountnumber()) && (receiver==transferamount.getReceiveraccount()) ) 
    	
    	{
    		System.out.println(sender+"r"+receiver+"b"+accountbalance+"a"+sendamount);
    	}
    	
    }
    
    public static ProducerRecord<String, String> bankTransaction(String accno,String amount) {
        // creates an empty json {}
        ObjectNode transaction = JsonNodeFactory.instance.objectNode();

      
        // Instant.now() is to get the current time using Java 8
        Instant now = Instant.now();

        // we write the data to the json document
        transaction.put("SenderAccountnumber", accno);
        transaction.put("ReceiverAccountnumber", accno);
        transaction.put("amount", amount);
        transaction.put("time", now.toString());
        return new ProducerRecord<>("bank-input", accno, transaction.toString());
    }
}
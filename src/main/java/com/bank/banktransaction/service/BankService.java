package com.bank.banktransaction.service;

import java.io.IOException;
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
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.MessagingException;
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
import com.bank.banktransaction.repository.FindUserdetails;
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
    private JavaMailSender javaMailSender;
	
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
	
	@Autowired
	private FindUserdetails findusers;
	
	
	@Autowired
	private findUserid finduserid;

	@Autowired
	private	findaccountnumberRepository accontno;
	

	
	
	
    public String saveuser(User user) {
    	
    	String emailid=null;
    	
    	String phoneno=null;
    	
    	int phone=0;
    	
    	String sendmail=user.getEmail().toString();
    	try {
    		
    		emailid=findusers.getEmailaddress(user.getEmail());
    		phoneno=findusers.getPhonenumber(user.getPhonenumber());
    		phone=Integer.parseInt(phoneno);
    		
    	}
    	catch(Exception e) {
    		

	
    	}   	
   	System.out.println(phoneno+"see"+user.getPhonenumber());
   	
   	
   	if(emailid==null && phoneno==null)
   	{
    	
    	bankRepository.save(user);

        return	"Registerd";
   	}
  
   	else if(phoneno != null){	
   		
   		return "Already Exist Phone no";
   
   	}
   	
   	else if(emailid!=null){
   		return "Already Exist Email";
   	}
   	else {
   		
   		return "";
   	}
    
    }
    
    public String addamount(AddAmount addAmount) {
    	
		int accnos=0;
		int userid=0;
		int useraccountid=0;
		try {
		userid=finduserid.getuserbyid(addAmount.getUserid());
		useraccountid=finduserid.getuserid(addAmount.getUserid());
		accnos=accontno.getaccountnumber(addAmount.getAccountnumber());
		}
		catch (Exception e) {
			System.out.println("The User Number Not Match"+e.getMessage());
			
			System.out.println(userid);
		}
			
		
		if(userid==addAmount.getUserid() && accnos!=addAmount.getAccountnumber() && useraccountid!=0 )
		{
			amountRepostory.save(addAmount);
		return "Amount Deposited"+addAmount.getDeposit()+ "Your account no"+addAmount.getAccountnumber();
		}
		else if(userid==0)
		{
			
			return "Userid Not Matched";
		}
		
		
		else if(accnos!=0){
			return "Accno not matched";
		}
		else {
			

			return "Userid Already have account";
		}
		
    	
    
}
    
    
//    public void transaction(TransactionDetails transaction) {
//    	
//    	
//    	transactionrepostory.save(transaction);
//    
//
//}
    
    public String deposit(AddAmount addAmount) {
    	
  //validation

 int accountno=0;
 int userid=0;
try {
accountno=findaccountnumber.getaccountnumber(addAmount.getAccountnumber());
userid=finduser.getuserid(addAmount.getAccountnumber());
addAmount.setUserid(userid);


System.out.println(userid);
}
catch (Exception e) {
	System.out.println("The Account Number Not Match"+e.getMessage());
	
	System.out.println(userid);
}

int getaccountno=addAmount.getAccountnumber();
if (accountno == getaccountno){
	
	depositRepostory.updatebalance(addAmount.getDeposit(),addAmount.getAccountnumber(),userid) ;
   TransactionDetails transaction = new TransactionDetails();
  
  transaction.setUserid(userid);
  transaction.setAccountnumber(addAmount.getAccountnumber());
  transaction.setReceiveraccount(addAmount.getAccountnumber());
  transaction.setAmount(addAmount.getDeposit());
  

  
  transaction.setDetails(BankConstant.credit);//constant
  
  
  
  

  Timestamp timestamp=new Timestamp(System.currentTimeMillis());
  String Time = timestamp.toString();
  transaction.setTime(Time);
  
  
  
  
  transactionrepostory.save(transaction);
  
  return "Deposit amount : "+addAmount.getDeposit();
}
else {
	return "Please Check Account Number";
}
	}




    
    public String addAccount(AddAccount addAccount) {
    	
    	int raccno=0;
    	try {
    		raccno=findaccountnumber.getreceiveraccountnumber(addAccount.getSenderaccountnumber());
    	


    		System.out.println(raccno);
    		}
    		catch (Exception e) {
    			System.out.println("Please check Account Nunmber"+e.getMessage());
    			
    			System.out.println(raccno);
    		}
    	
    		if(raccno == 0) {
    			
    	accountrepository.save(addAccount);
    	return "Add New Transaction Account";
    		}
    		else {
    			
    			return "Account number Already Exist";
    		}
    	
}
    
    
    public void creditamount(TransactionDetails transferamount) {
      	int sender=0;
      	int receiver=0;
    	int sendamount=transferamount.getAmount();
    	
    		
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

    		int abcd=transferamount.getAccountnumber();
    		Integer obj = new Integer(abcd);
    		String str4 = obj.toString();

    		
    		int dep=transferamount.getAmount();
    		Integer obj2 = new Integer(dep);
    		String str5 = obj2.toString();
    		
    		
    		int raccno=transferamount.getReceiveraccount();
    		Integer obj3 = new Integer(raccno);
    		String str6 = obj3.toString();

    		      try {
    		          producer.send(bankTransaction(str4,str5,str6));
    		          Thread.sleep(100);


    		      } catch (InterruptedException e) {

    		      }

    		  producer.close();

    		//

    		  
    		  //end
    		
    		
    	
    	
    
    	
    }
    
    public static ProducerRecord<String, String> bankTransaction(String accno,String amount,String raccno) {
        // creates an empty json {}
        ObjectNode transaction = JsonNodeFactory.instance.objectNode();

      
        // Instant.now() is to get the current time using Java 8
        Instant now = Instant.now();

        // we write the data to the json document
        transaction.put("SenderAccountnumber", accno);
        transaction.put("ReceiverAccountnumber", raccno);
        transaction.put("amount", amount);
        transaction.put("time", now.toString());
        return new ProducerRecord<>("kafka-testing1", accno, transaction.toString());
    }
    
    
    
    @KafkaListener(topics = "kafka-testing8")
    public void consume(String message) throws IOException {
    	
    	
    	
    	
    	
    	JSONObject json = new JSONObject(message);
        System.out.println(json.get("SenderAccountnumberb").toString());
        
        
        String reportfromtransaction = json.get("Reportb").toString();
        
        String validss =reportfromtransaction;
        
        String saccountno = json.get("SenderAccountnumberb").toString();

        int sendereccno=Integer.parseInt(saccountno);

        
        String raccountno = json.get("ReceiverAccountnumber").toString();
        

        int recivereccno=Integer.parseInt(raccountno);
        
        TransactionDetails transactionamount = new TransactionDetails();
        
        String amounttranfer = json.get("Amountbb").toString();
        
        int amounttranferint = Integer.parseInt(amounttranfer);
        
        
        
      
        	
        System.out.println(message );	
        
    	int accountbalance=0;
    	
    	int userid=0;
    	try {
    
    	 accountbalance=balancerepository.getamount(sendereccno);
    	 
    	 userid=finduser.getuserid(sendereccno);
    	
    	}
    	catch(Exception e) 
    	{
    		
    		System.out.println(e.getMessage());
    	}
    	int sumaccountbalance=accountbalance-amounttranferint;
    	 String valid ="valid";
        System.out.println(validss+sendereccno +" and "+ sumaccountbalance +"and" +accountbalance +valid);	
        
       
        int flag=0;
        
        
        if(validss==valid)
        	
        {
        	flag=1;
        	
        }
       
        

        
        
        
      
      //  depositRepostory.debitedbalance(amounttranferint,sendereccno);
        
        
       
       //Send mail
       
 String rep= sendereccno +" Debited " +amounttranferint+"rs Account balance is "+ accountbalance  +"Transaction Status  : ="+ reportfromtransaction ;
       
       
       
//       transactionreport(String.valueOf(sendereccno),raccountno,reportfromtransaction,amounttranfer,String.valueOf(sumaccountbalance) );
   
 
 
// find user id

 int userid2=0;
try {

userid2=finduser.getuserid(sendereccno);



System.out.println(userid2);
}





catch (Exception e) {
	System.out.println("The Account Number Not Match"+e.getMessage());
	
	System.out.println(userid);
}
 
String emailid2=null;

try {
	
	emailid2=findusers.getnewemail(userid2);
	
	
}
catch(Exception e) {
	
	System.out.println(e.getMessage());

}   	
 
 
 
 
 try {
		
	 sendEmail(rep,emailid2);
     //sendEmailWithAttachment();

 } catch (MessagingException e) {
     e.printStackTrace();
 }

 System.out.println("Done");
 
    }
    
    
    
    public String transactionreport() {
    	
    	
    	return "";
    	
    }
    
    public  void sendEmail(String rep,String mailid) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(mailid);

        msg.setSubject("Testing Transaction ");
        msg.setText(rep);

        javaMailSender.send(msg);

    }
    
    
    public String messagereader() {
    	
    	String message=transactionrepostory.message(1);
    	return message;
    }
    
    
    
    public int balancechek(AddAmount amount) {
    	
    	int balance=0;
    	try {
    	
    		balance=	balancerepository.getamount(amount.getAccountnumber());
    	}
    	catch(Exception e)
    	{
    	System.out.print(e.getMessage());
    	}
    	
    	return balance;
    

}
    
}
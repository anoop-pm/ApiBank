package com.bank.banktransaction.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;

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
import com.bank.banktransaction.model.TransactionLog;
import com.bank.banktransaction.model.User;
import com.bank.banktransaction.repository.BankRepository;
import com.bank.banktransaction.repository.FindUserdetails;
import com.bank.banktransaction.repository.GetamountbalanceRepository;
import com.bank.banktransaction.repository.LogTransactionRepository;
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
	private findaccountnumberRepository accontno;

	@Autowired
	private LogTransactionRepository logrepo;

//	service for register new User

	public Object saveuser(User user) {

		String emailid = null;

		String phoneno = null;
		int val = 0;
		int phone = 0;

		String valu = null;
		if (bankRepository.maxuserid() == null) {
			val = 0;
		} else {
			val = bankRepository.maxuserid();
		}
		{
			try {

				emailid = findusers.getEmailaddress(user.getEmail()); // update with method
				phoneno = findusers.getPhonenumber(user.getPhonenumber());
				phone = Integer.parseInt(phoneno);
			} catch (Exception e) {

			}
		}
		// Random User ID
		Random random = new Random();
		int x = random.nextInt(899) + 100;
		String newuserid = String.valueOf(val + 1) + String.valueOf(x);
		System.out.println(val + "value=" + newuserid);

		// Auto generate accountnumber

		int acc = random.nextInt(8999) + 1000;
		String newaccno = String.valueOf(val + 1) + String.valueOf(acc) + String.valueOf(val);

		Map<String, String> map = new HashMap<>();

		if (emailid == null && phoneno == null) {

			// Save User
			user.setName(user.getName());
			user.setDateofbirth(user.getDateofbirth());
			user.setAge(user.getAge());
			user.setAccounttype(user.getAccounttype());
			user.setAddress(user.getAddress());
			user.setPhonenumber(user.getPhonenumber());
			user.setEmail(user.getEmail());
			user.setUserid(Integer.parseInt(newuserid));

			// auto created user account
			AddAmount amount = new AddAmount();
			amount.setUserid(Integer.parseInt(newuserid));
			amount.setAccountnumber(Integer.parseInt(newaccno));
			amount.setDeposit(0);
			amountRepostory.save(amount);
			bankRepository.save(user);
			String messages = "Account Registered Successfully and Your account no is :" + newaccno + " "
					+ "And User Id Is :" + newuserid; //Constant
			map.put("Status", "200");
			map.put("Message", messages);
			return map;
		}

		else if (phoneno != null) {
			map.put("Status", "404");
			map.put("Message", "Already Exist Phone no");
			return map;

		}

		else if (emailid != null) {
			map.put("Status", "404");
			map.put("Message", "Already Exist Email");
			return map;

		} else {

			return "";
		}

	}

	// Deposit Service

	public Object deposit(AddAmount addAmount) {

		// validation

		int accountno = 0;
		int userid = 0;
		int newbalance = 0;
		try {
			accountno = findaccountnumber.getaccountnumber(addAmount.getAccountnumber());
			userid = finduser.getuserid(addAmount.getAccountnumber());
			newbalance = balancerepository.getamount(addAmount.getAccountnumber());

			System.out.println(userid);
		} catch (Exception e) {
			System.out.println("The Account Number Not Match" + e.getMessage());

		}

		int getaccountno = addAmount.getAccountnumber();
		Map<String, String> map = new HashMap<>();

		if (accountno == getaccountno) {
			int newamount = newbalance + addAmount.getDeposit();
			depositRepostory.updatebalance(addAmount.getDeposit(), addAmount.getAccountnumber(), userid);
			TransactionDetails transaction = new TransactionDetails();

			transaction.setUserid(userid);
			transaction.setSenderaccountnumber(addAmount.getAccountnumber());
			transaction.setReceiveraccountnumber(addAmount.getAccountnumber());
			transaction.setAmount(addAmount.getDeposit());

			transaction.setDetails(BankConstant.credit);// constant

			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			String Time = timestamp.toString();
			transaction.setTime(Time);
			transactionrepostory.save(transaction);

			map.put("Status", "200");

			String depositmessage = "Deposited Successfully and" + "Your Account Number is" + accountno
					+ " Your Current Balance is" + String.valueOf(newamount);

			map.put("message", depositmessage);

			return map;
		} else {
			map.put("Status", "404");
			map.put("Message", "Account Number Not Valid");
			return map;
		}
	}

//    Service for create add other Accounts

	public Object addAccount(AddAccount addAccount) {

		int raccno = 0;
		Map<String, String> map = new HashMap<>();
		try {
			raccno = findaccountnumber.getreceiveraccountnumber(addAccount.getReceiveraccountnumber());

			System.out.println(raccno);
		} catch (Exception e) {

		}

		if (raccno == 0) {

			map.put("Status", "200");
			String accountmessage = "Account added Successfully the account Number is "
					+ addAccount.getReceiveraccountnumber();
			map.put("Message", accountmessage);

			accountrepository.save(addAccount);
			return map;

		} else {
			map.put("Status", "404");
			map.put("Message", "Account number Already Exist");
			return map;
		}

	}

	public void creditamount(TransactionDetails transferamount) { 
		int sender = 0;
		int receiver = 0;
		int sendamount = transferamount.getAmount();

//    	Kafka Producer
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

		int abcd = transferamount.getSenderaccountnumber();
		Integer obj = new Integer(abcd);
		String str4 = obj.toString();

		int dep = transferamount.getAmount();
		Integer obj2 = new Integer(dep);
		String str5 = obj2.toString();

		int raccno = transferamount.getReceiveraccountnumber();
		Integer obj3 = new Integer(raccno);
		String str6 = obj3.toString();

		try {
			producer.send(bankTransaction(str4, str5, str6));
			Thread.sleep(100);

		} catch (InterruptedException e) {

		}

		producer.close();

	}

	public static ProducerRecord<String, String> bankTransaction(String accno, String amount, String raccno) {

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

	// Cunsume data

	@KafkaListener(topics = "kafka-testing8")
	public void consume(String message) throws IOException {

		JSONObject json = new JSONObject(message);
		System.out.println(json.get("SenderAccountnumberb").toString());

		String reportfromtransaction = json.get("Reportb").toString();

		String validss = reportfromtransaction;

		String saccountno = json.get("SenderAccountnumberb").toString();

		int sendereccno = Integer.parseInt(saccountno);

		String raccountno = json.get("ReceiverAccountnumber").toString();

		int recivereccno = Integer.parseInt(raccountno);

		TransactionDetails transactionamount = new TransactionDetails();

		String amounttranfer = json.get("Amountbb").toString();

		int amounttranferint = Integer.parseInt(amounttranfer);

//        Store logs

		TransactionLog tralog = new TransactionLog();
		tralog.setLogs(message);
		logrepo.save(tralog);

		int userid2 = 0;
		int accountbalance = 0;
		try {

			accountbalance = balancerepository.getamount(sendereccno);
			userid2 = finduser.getuserid(sendereccno);

		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
		int sumaccountbalance = accountbalance - amounttranferint;
		String valid = "valid";
		System.out.println(validss + sendereccno + " and " + sumaccountbalance + "and" + accountbalance + valid + "sdsa"
				+ userid2);

		String emailid2 = null;

		try {

			emailid2 = findusers.getnewemail(userid2);

		} catch (Exception e) {

			System.out.println(e.getMessage());

		}

		String rep = sendereccno + " Debited " + amounttranferint + "rs Account balance is " + accountbalance
				+ "Transaction Status  : =" + reportfromtransaction;

		System.out.println(rep + "Userid" + "Email" + "" + emailid2);

		if (validss.equals("Transfered"))

		{
			try {
				sendEmail(rep, emailid2);
				// sendEmailWithAttachment();

			} catch (MessagingException e) {
				e.printStackTrace();
			}

			System.out.println("Done");

		}
	}

	public void sendEmail(String rep, String mailid) {

		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(mailid);

		msg.setSubject("Testing Transaction ");
		msg.setText(rep);

		javaMailSender.send(msg);

	}

	public String messagereader() {

		String message = transactionrepostory.message(1);
		return message;
	}

	public String statusread() {

		String status = transactionrepostory.status(1);
		return status;
	}

	// Service for Balance Check
	public int balancechek(AddAmount amount) {

		int balance = 0;
		try {

			balance = balancerepository.getamount(amount.getAccountnumber());
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}

		return balance;

	}

}
package com.bank.banktransaction.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "useraccounts")
public class AddAmount {
	
	
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    
	  
	   @Column(name = "userid",  length = 20)
	    private int userid;
	   
	   @Column(name = "accountnumber",  length = 20)
	    private int accountnumber;
//	   
//	   @Column(name = "transactiontype",  length = 20)
//	    private String transactiontype;
//	   
	   @Column(name = "accountbalance",  length = 20)
	    private int balance;

	public AddAmount(int userid, int accountnumber, String transactiontype, int balance) {
		super();
		this.userid = userid;
		this.accountnumber = accountnumber;
//		this.transactiontype = transactiontype;
		this.balance = balance;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(int accountnumber) {
		this.accountnumber = accountnumber;
	}

//	public String getTransactiontype() {
//		return transactiontype;
//	}
//
//	public void setTransactiontype(String transactiontype) {
//		this.transactiontype = transactiontype;
//	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}
	   
	   
	   
	   

	




}

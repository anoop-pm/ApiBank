package com.bank.banktransaction.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
@Entity
@Table(name = "useraccounts")
public class AddAmount {
	
	
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    
//	  @NotBlank(message = "Userid is mandatory")
	   @Column(name = "userid", unique = true,  length = 20)
	    private int userid;
	  
//	  @NotBlank(message = "accountnumber is mandatory")
	   @Column(name = "accountnumber",  unique = true,  length = 200)
	    private int accountnumber;
//	   
//	   @Column(name = "transactiontype",  length = 20)
//	    private String transactiontype;
//	   
//	  @NotEmpty(message = "Deposit is mandatory")
	   @Column(name = "balance", unique = true,  length = 20)
	    private int balance;
public AddAmount( int userid,
		 int accountnumber,
		 int balance) {
	super();
	this.userid = userid;
	this.accountnumber = accountnumber;
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
public int getBalance() {
	return balance;
}
public void setBalance(int balance) {
	this.balance = balance;
}


	
	




}

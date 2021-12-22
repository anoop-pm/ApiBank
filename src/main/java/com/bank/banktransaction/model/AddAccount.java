package com.bank.banktransaction.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "accounts")
public class AddAccount {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
  
  
   
   @Column(name = "accountnumber", unique = true,  length = 200)
    private int accountnumber;
   
   @Column(name = "name",  length = 200)
   private String name;
   
   @Column(name = "bankname",  length = 200)
   private String bankname;
   
   @Column(name = "ifsccode",  length = 200)
   private String ifsccode;

public AddAccount(int accountnumber, String name, String bankname, String ifsccode) {
	super();
	this.accountnumber = accountnumber;
	this.name = name;
	this.bankname = bankname;
	this.ifsccode = ifsccode;
}

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public int getAccountnumber() {
	return accountnumber;
}

public void setAccountnumber(int accountnumber) {
	this.accountnumber = accountnumber;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getBankname() {
	return bankname;
}

public void setBankname(String bankname) {
	this.bankname = bankname;
}

public String getIfsccode() {
	return ifsccode;
}

public void setIfsccode(String ifsccode) {
	this.ifsccode = ifsccode;
}
   
   

}

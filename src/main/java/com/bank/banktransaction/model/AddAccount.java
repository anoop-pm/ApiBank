package com.bank.banktransaction.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "accounts")
public class AddAccount {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	@NotNull
   @Column(name = "receiveraccountnumber", unique = true,  length = 200)
    private int receiveraccountnumber;
   
	@NotNull
   @Column(name = "name",  length = 200)
   private String name;
	
	@NotNull
   @Column(name = "bankname",  length = 200)
   private String bankname;
   
	@NotNull
   @Column(name = "ifsccode",  length = 200)
   private String ifsccode;

public AddAccount() {
	super();
	// TODO Auto-generated constructor stub
}

public AddAccount(int receiveraccountnumber, String name, String bankname, String ifsccode) {
	super();
	this.receiveraccountnumber = receiveraccountnumber;
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

public int getReceiveraccountnumber() {
	return receiveraccountnumber;
}

public void setReceiveraccountnumber(int receiveraccountnumber) {
	this.receiveraccountnumber = receiveraccountnumber;
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

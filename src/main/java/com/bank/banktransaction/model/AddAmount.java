package com.bank.banktransaction.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
@Entity
@Table(name = "useraccounts")
public class AddAmount {
	
	
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    
//	  @NotBlank(message = "Userid is mandatory")
	   @Column(name = "userid",  length = 20)
	   @NotNull
	   @ApiModelProperty(notes = "The application-specific Auto Generated User ID")
	   private int userid;
	  
//	  @NotBlank(message = "accountnumber is mandatory")
	   	@Column(name = "accountnumber",   length = 200)
	   	@NotNull
	   	@ApiModelProperty(notes = "The application-specific Auto Generated Account Number")
	    private int accountnumber;
//	   
//	   @Column(name = "transactiontype",  length = 20)
//	    private String transactiontype;
//	   
//	  @NotEmpty(message = "Deposit is mandatory")
	   	@ApiModelProperty(notes = "The application-specific Deposit")
	   	@Column(name = "deposit",  length = 20)
	   	@NotNull
	    private int deposit;
	   	
	   	
	   	

public AddAmount() {
			super();
			// TODO Auto-generated constructor stub
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
public int getDeposit() {
	return deposit;
}
public void setDeposit(int deposit) {
	this.deposit = deposit;
}



	
	




}

package com.bank.banktransaction.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "transactionlogs")
public class TransactionLog {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
     
   @Column(name = "logs",  length = 220)
   @ApiModelProperty(notes = "The application-specific Transaction Logs Details")
    private String logs;

public TransactionLog() {
	super();
	// TODO Auto-generated constructor stub
}

public String getLogs() {
	return logs;
}

public void setLogs(String logs) {
	this.logs = logs;
}
   
   
   
}

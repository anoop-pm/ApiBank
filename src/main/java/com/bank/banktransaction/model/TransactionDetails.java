package com.bank.banktransaction.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;


@Entity
@Table(name = "transactions")
public class TransactionDetails {
	
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	     
	   @Column(name = "userid",  length = 20)
	    private int userid;
	   
	   @Column(name = "senderaccountnumber",  length = 20)
	   @ApiModelProperty(notes = "The application-specific Sender account number")
	    private int senderaccountnumber;
	   
	   @Column(name = "receiveraccountnumber",  length = 20)
	   @ApiModelProperty(notes = "The application-specific Receiver Account Number")
	    private int receiveraccountnumber;
	   
	   @Column(name = "amount",  length = 20)
	   @ApiModelProperty(notes = "The application-specific Receiver Amount")
	    private int amount;
	   
	   @Column(name = "details",  length = 20)
	   @ApiModelProperty(notes = "The application-specific Details")
	    private String details;
	     
	    @Column(name = "time",  length = 220)
	    @ApiModelProperty(notes = "The application-specific Instant Time")
	    private String time;


		public TransactionDetails() {
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

		

	

		public int getSenderaccountnumber() {
			return senderaccountnumber;
		}






		public void setSenderaccountnumber(int senderaccountnumber) {
			this.senderaccountnumber = senderaccountnumber;
		}






		public int getReceiveraccountnumber() {
			return receiveraccountnumber;
		}






		public void setReceiveraccountnumber(int receiveraccountnumber) {
			this.receiveraccountnumber = receiveraccountnumber;
		}



		public int getAmount() {
			return amount;
		}


		public void setAmount(int amount) {
			this.amount = amount;
		}



		public String getDetails() {
			return details;
		}

		public void setDetails(String details) {
			this.details = details;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

}

package com.bank.banktransaction.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bank.banktransaction.model.AddAmount;
@Repository
public interface addamountupdateRepository extends JpaRepository <AddAmount, Integer>
{
//
//	@Transactional
//	@Modifying
//	@Query("UPDATE useraccounts SET balance  = :balance WHERE userid = :userid")
//	Integer updatebalance(int balance, int userid);
//
	
	
	 @Transactional
	 @Modifying
		@Query("UPDATE AddAmount SET deposit   = deposit + :deposit,userid=:userid WHERE  accountnumber = :accountnumber")
		Integer updatebalance(int deposit,int accountnumber,int userid);
	 
	 
	 @Transactional
	 @Modifying
		@Query("UPDATE AddAmount SET deposit   = deposit - :amount WHERE  accountnumber = :accountnumber")
		Integer debitedbalance(int amount,int accountnumber);
	 
	

	
}




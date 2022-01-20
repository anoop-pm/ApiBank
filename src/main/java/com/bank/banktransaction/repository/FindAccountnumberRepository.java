package com.bank.banktransaction.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.bank.banktransaction.model.AddAmount;

public interface FindAccountnumberRepository extends JpaRepository <AddAmount, Integer>
{
//
//	@Transactional
//	@Modifying
//	@Query("UPDATE useraccounts SET balance  = :balance WHERE userid = :userid")
//	Integer updatebalance(int balance, int userid);
//
	@Query(value="select accountnumber from useraccounts u where u.accountnumber =:Anumber", nativeQuery=true)
	Integer getaccountnumber(@Param("Anumber") int Anumber);
	

	@Query(value="select receiveraccountnumber from accounts u where u.receiveraccountnumber =:Bnumber", nativeQuery=true)
	Integer getreceiveraccountnumber(@Param("Bnumber") int Bnumber);

	 
}
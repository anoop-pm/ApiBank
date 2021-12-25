package com.bank.banktransaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bank.banktransaction.model.AddAmount;

public interface findUserid extends JpaRepository <AddAmount, Integer>
{
//
//	@Transactional
//	@Modifying
//	@Query("UPDATE useraccounts SET balance  = :balance WHERE userid = :userid")
//	Integer updatebalance(int balance, int userid);
//
	@Query(value="select userid from useraccounts u where u.accountnumber =:Anumber", nativeQuery=true)
	Integer getuserid(@Param("Anumber") int Anumber);

	 
}

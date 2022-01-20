package com.bank.banktransaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bank.banktransaction.model.AddAmount;

public interface FindUserid extends JpaRepository <AddAmount, Integer>
{
//
//	@Transactional
//	@Modifying
//	@Query("UPDATE useraccounts SET balance  = :balance WHERE userid = :userid")
//	Integer updatebalance(int balance, int userid);
//
	@Query(value="select userid from useraccounts u where u.accountnumber =:Anumber", nativeQuery=true)
	Integer getuserid(@Param("Anumber") int Anumber);
	
	
	@Query(value="select id from users u where u.id =:uid", nativeQuery=true)
	Integer getuserbyid(@Param("uid") int uid);

	 
}

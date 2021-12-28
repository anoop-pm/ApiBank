package com.bank.banktransaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bank.banktransaction.model.AddAmount;

public interface GetamountbalanceRepository extends JpaRepository <AddAmount, Long>{
	
	@Query(value="select deposit from useraccounts u where u.accountnumber =:Bnumber", nativeQuery=true)
	Integer getamount(@Param("Bnumber") int Bnumber);

}

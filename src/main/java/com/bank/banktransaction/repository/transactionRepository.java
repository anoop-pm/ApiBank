package com.bank.banktransaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bank.banktransaction.model.TransactionDetails;


@Repository
public interface transactionRepository  extends JpaRepository <TransactionDetails, Long>{
	
	
	@Query(value="select message from messages u where u.mid =:messageidss", nativeQuery=true)
	String message(@Param("messageidss") int messageidss);
	
	
	@Query(value="select status from messages u where u.mid =:messageidssa", nativeQuery=true)
	String status(@Param("messageidssa") int messageidssa);

	
	
	
}

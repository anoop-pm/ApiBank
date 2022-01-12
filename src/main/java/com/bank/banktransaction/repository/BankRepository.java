package com.bank.banktransaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bank.banktransaction.model.User;
@Repository
public interface BankRepository extends JpaRepository <User, Long>{
	
	
	
	@Query(value="select max(id) from users", nativeQuery=true)
	Integer maxuserid();

}

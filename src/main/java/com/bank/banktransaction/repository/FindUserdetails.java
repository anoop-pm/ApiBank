package com.bank.banktransaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bank.banktransaction.model.User;

public interface FindUserdetails extends JpaRepository <User, Long>{
	
	
	@Query(value="select email from users u where u.email =:email", nativeQuery=true)
	String getEmailaddress(@Param("email") String email);
	
	
	@Query(value="select phonenumber from users u where u.phonenumber =:phone", nativeQuery=true)
	String getPhonenumber(@Param("phone") String phone);
	
	
	@Query(value="select email from users u where u.userid =:usersid", nativeQuery=true)
	String getnewemail(@Param("usersid") int usersid);
	

}
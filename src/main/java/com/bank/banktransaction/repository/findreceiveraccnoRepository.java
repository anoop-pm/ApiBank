package com.bank.banktransaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bank.banktransaction.model.AddAccount;


public interface findreceiveraccnoRepository extends JpaRepository <AddAccount, Integer>
{

	
}

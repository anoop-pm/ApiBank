package com.bank.banktransaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.banktransaction.model.TransactionDetails;


@Repository
public interface transactionRepository  extends JpaRepository <TransactionDetails, Long>{

}

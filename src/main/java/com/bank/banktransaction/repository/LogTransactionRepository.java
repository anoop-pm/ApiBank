package com.bank.banktransaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.banktransaction.model.TransactionLog;


public interface LogTransactionRepository extends JpaRepository <TransactionLog, Long> {

}

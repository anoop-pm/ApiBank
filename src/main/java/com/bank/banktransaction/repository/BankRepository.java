package com.bank.banktransaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.banktransaction.model.User;
@Repository
public interface BankRepository extends JpaRepository <User, Long>{

}

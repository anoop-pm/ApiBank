package com.bank.banktransaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.banktransaction.model.AddAccount;

@Repository
public interface AddAccountRepositorry extends JpaRepository <AddAccount, Long>{

}

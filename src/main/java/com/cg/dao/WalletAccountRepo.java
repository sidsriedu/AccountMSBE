package com.cg.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.entities.WalletAccount;

public interface WalletAccountRepo extends JpaRepository<WalletAccount, Integer> {

}
